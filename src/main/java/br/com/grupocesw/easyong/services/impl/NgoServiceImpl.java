package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.*;
import br.com.grupocesw.easyong.enums.NotificationType;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.NgoAlreadyExistsException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class NgoServiceImpl implements NgoService {

	private final NgoRepository repository;
	private final CityService cityService;
	private final SocialCauseService socialCauseService;
	private final UserService userService;
	private final NotificationService notificationService;

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public Ngo create(Ngo request) {
		log.info("Create ngo with name {}", request.getName());

		if (repository.existsByCnpj(request.getCnpj())) {
			log.warn("Ngo with cnpj {} already exists", request.getCnpj());

			throw new NgoAlreadyExistsException(
				String.format("Ngo with cnpj %s already exists", request.getCnpj()));
		}

		cityService.existsOrThrowsException(request.getAddress().getCity().getId());
		City city = cityService.retrieve(request.getAddress().getCity().getId());

		Set<SocialCause> causes = socialCauseService.findByIdIn(
			request.getCauses()
				.stream()
				.map(c -> c.getId())
				.collect(Collectors.toSet())
		);

		if (causes.size() < 1)
			throw new IllegalArgumentException("At least one cause required");

		if (request.getPictures() != null) {
			request.getPictures().stream()
				.forEach(obj -> {
					if (obj.getUrl().isEmpty())
						throw new IllegalArgumentException("URL can't empty");
				});
		}

		Address address = Address.builder()
			.number(request.getAddress().getNumber())
			.street(request.getAddress().getStreet())
			.complement(request.getAddress().getComplement())
			.zipCode(request.getAddress().getZipCode())
			.latitude(request.getAddress().getLatitude())
			.longitude(request.getAddress().getLongitude())
			.district(request.getAddress().getDistrict())
			.city(city)
			.build();

		Ngo ngo = Ngo.builder().name(request.getName())
			.cnpj(request.getCnpj())
			.description(request.getDescription())
			.activated(request.getActivated())
			.address(address)
			.contacts(request.getContacts().stream()
				.map(obj ->
					Contact.builder()
						.type(obj.getType())
						.content(obj.getContent())
						.build()
				).collect(Collectors.toSet()))
			.moreInformations(request.getMoreInformations().stream()
				.map(obj ->
					NgoMoreInformation.builder()
						.information(obj.getInformation())
						.build()
				).collect(Collectors.toSet()))
			.causes(causes)
			.pictures(request.getPictures().stream()
				.map(obj ->
					Picture.builder()
						.url(obj.getUrl())
						.build()
				).collect(Collectors.toSet()))
			.build();

		notificationService.simpleCreate(
			NotificationType.INFORMATION,
			"Nova ONG",
			String.format("A ONG \"%s\", com mesma causa que a sua foi criada.", ngo.getName()),
			userService.findAllBySocialCauses(ngo.getCauses()));

		return repository.save(ngo);
	}

	@Override
	public Ngo retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public Ngo update(Long id, Ngo request) throws Exception {
		log.info("Update ngo with name {}", request.getName());

		Ngo ngo = retrieve(id);
		ngo.setName(request.getName());
		ngo.setDescription(request.getDescription());
		ngo.setActivated(request.getActivated());

		if (!request.getCnpj().equals(ngo.getCnpj()))
			ngo.setCnpj(request.getCnpj());

		if (request.getContacts() != null && request.getContacts().size() > 0) {
			ngo.getContacts().clear();
			ngo.getContacts().addAll(
				request.getContacts().stream()
					.map(obj ->
						Contact.builder()
							.type(obj.getType())
							.content(obj.getContent())
							.build()
					).collect(Collectors.toSet())
			);
		}

		if (request.getAddress() != null) {
			ngo.getAddress().setNumber(request.getAddress().getNumber());
			ngo.getAddress().setStreet(request.getAddress().getStreet());
			ngo.getAddress().setComplement(request.getAddress().getComplement());
			ngo.getAddress().setZipCode(request.getAddress().getZipCode());
			ngo.getAddress().setLatitude(request.getAddress().getLatitude());
			ngo.getAddress().setLongitude(request.getAddress().getLongitude());
			ngo.getAddress().setDistrict(request.getAddress().getDistrict());

			if (request.getAddress().getCity() != null) {
				cityService.existsOrThrowsException(request.getAddress().getCity().getId());
				City city = cityService.retrieve(request.getAddress().getCity().getId());

				ngo.getAddress().setCity(city);
			}
		}

		if (request.getPictures() != null && request.getPictures().size() > 0) {
			ngo.getPictures().clear();
			ngo.getPictures().addAll(request.getPictures().stream()
				.map(obj ->
					Picture.builder()
						.url(obj.getUrl())
						.build()
				).collect(Collectors.toSet()));
		}

		if (request.getMoreInformations() != null && request.getMoreInformations().size() > 0) {
			ngo.getMoreInformations().clear();
			ngo.getMoreInformations().addAll(
				request.getMoreInformations().stream()
				.map(obj ->
					NgoMoreInformation.builder()
						.information(obj.getInformation())
						.build()
				).collect(Collectors.toSet())
			);
		}

		if (request.getCauses() != null && request.getCauses().size() > 0) {
			Set<SocialCause> causes = socialCauseService.findByIdIn(
					request.getCauses().stream().map(c -> c.getId())
							.collect(Collectors.toSet())
			);

			if (causes.size() < 1)
				throw new IllegalArgumentException("At least one cause required");

			ngo.getCauses().clear();
			ngo.getCauses().addAll(causes);
		}

		notificationService.simpleCreate(
			NotificationType.INFORMATION,
			"Atualização de ONG",
			String.format("A ONG \"%s\", com mesma causa que a sua foi atualizada.", ngo.getName()),
			userService.findAllBySocialCauses(ngo.getCauses()));

		return repository.save(ngo);
	}

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public void delete(Long id) {
		log.info("Delete ngo with id {}", id);

		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Ngo not found. Id " + id);
	}

	@Override
	@Cacheable(value = "ngos", key = "#pageable.pageSize")
	public Page<Ngo> findByActivated(Pageable pageable) {
		return repository.findByActivatedTrueOrderByName(pageable);
	}

	@Override
	public Page<Ngo> findByActivatedWithFilter(String filter, Pageable pageable) {
		return repository.findWithFilter(filter, pageable);
	}

	@Override
	public Page<Ngo> findSuggested(Pageable pageable) {
		log.info("Find suggested ngos");
		User currentUser = userService.getCurrentUserOrNull();

		if (currentUser != null) {
			Page<Ngo> ngos = repository.findSuggestedByLoggedUser(pageable, currentUser.getId());

			if (ngos.getTotalElements() >= pageable.getPageSize())
				return ngos;

			List<Ngo> suggestedNgos = Stream
				.concat(ngos.stream(), repository.findSuggested(pageable)
				.stream())
				.distinct()
				.collect(Collectors.toList());

			final int start = (int) pageable.getOffset();
			final int end = Math.min((start + pageable.getPageSize()), suggestedNgos.size());

			return new PageImpl<>(suggestedNgos.subList(start, end), pageable, suggestedNgos.size());
		}

		return repository.findSuggested(pageable);
	}

	@Transactional
	@Override
	public void favorite(Long ngoId) {
		User currentUser = userService.getCurrentUser();
		log.info("Username {} favorite Ngo id {}", currentUser.getUsername(), ngoId);

		Ngo ngo = repository.findNgoByNgoIdAndUserId(currentUser.getId(), retrieve(ngoId).getId()).orElse(null);

		if (ngo == null) {
			repository.saveFavoriteNgo(currentUser.getId(), ngoId);
		} else {
			repository.deleteFavoriteNgo(currentUser.getId(), ngoId);
		}
	}

	@Override
	public Page<Ngo> getFavoriteNgos(Pageable pageable) {
		log.info("List favorite Ngos");

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<Ngo> ngos = repository.getFavoriteNgosByUser(userService.getCurrentUser());

		if (ngos.size() < startItem) {
			ngos = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, ngos.size());
			ngos = ngos.subList(startItem, toIndex);
		}

		return new PageImpl<>(
			ngos,
			PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
			ngos.size()
		);
	}

	@Override
	public Page<Ngo> getFavoriteNgosWithFilter(String filter, Pageable pageable) {
		log.info("List favorite Ngos with filters");

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<Ngo> ngos = repository.getFavoriteNgosByUserAndFilter(filter, userService.getCurrentUser());

		if (ngos.size() < startItem) {
			ngos = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, ngos.size());
			ngos = ngos.subList(startItem, toIndex);
		}

		return new PageImpl<>(
			ngos,
			PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()),
			ngos.size()
		);
	}

	@Override
	public boolean existsFavoriteNgosByLoggedUser() {
		User currentUser = userService.getCurrentUser();
		log.info("Exists notifications by username {}", currentUser.getUsername());

		return repository.existsNgoByUsers(currentUser);
	}

}
