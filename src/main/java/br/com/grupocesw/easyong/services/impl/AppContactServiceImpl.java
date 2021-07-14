package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.AppContact;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.AppContactRepository;
import br.com.grupocesw.easyong.services.AppContactService;
import br.com.grupocesw.easyong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppContactServiceImpl implements AppContactService {

	private final AppContactRepository repository;
	private final UserService userService;

	@Override
	public AppContact create(AppContact request) {
		User currentUser = userService.getCurrentUserOrNull();
		log.info("Create contact message");

		if (currentUser != null) {
			log.info("Create contact message with user {}", currentUser.getPerson().getName());
			request.setName(currentUser.getPerson().getName());
			request.setEmail(currentUser.getUsername());
			request.setUser(currentUser);
		}

		return repository.save(request);
	}
	
	@Override
	public AppContact retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public void delete(Long id) {
		log.info("Delete appContact with id {}", id);

		repository.delete(retrieve(id));
	}

	@Override
	public Page<AppContact> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("AppContact not found. Id " + id);
	}

}
