package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.Notification;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.NotificationType;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.NotificationRepository;
import br.com.grupocesw.easyong.services.NotificationService;
import br.com.grupocesw.easyong.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository repository;
	private final UserService userService;

	@Override
	public Notification create(Notification request) {
		log.info("Create notification title {}", request.getTitle());
		return repository.save(request);
	}

	@Override
	public Notification simpleCreate(NotificationType type, String title, String description, Set<User> users) {
		return create(
			Notification.builder()
				.type(type)
				.title(title)
				.description(description)
				.users(users)
				.build()
		);
	}

	@Override
	public Notification retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException("Notification", id));
	}

	@Override
	public void delete(Long id) {
		log.info("Delete notification with id {}", id);

		repository.delete(retrieve(id));
	}

	@Transactional
	@Override
	public void deleteByLoggedUser(Long id) {
		User currentUser = userService.getCurrentUser();
		log.info("Delete notification by username {}", currentUser.getUsername());

		repository.deleteByLoggedUser(currentUser.getId(), id);
	}

	@Transactional
	@Override
	public void deleteAllByLoggedUser() {
		User currentUser = userService.getCurrentUser();
		log.info("Delete all notifications by username {}", currentUser.getUsername());

		repository.deleteAllByLoggedUser(currentUser.getId());
	}

	@Override
	public boolean existsNotificationsByLoggedUser() {
		User currentUser = userService.getCurrentUser();
		log.info("Exists notifications by username {}", currentUser.getUsername());

		return repository.existsNotificationByUsers(currentUser);
	}

	@Override
	public Page<Notification> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Notification not found. Id " + id);
	}

	@Override
	public Page<Notification> getNotifications(Pageable pageable) {
		log.info("List notifications");
		return repository.findByUsersOrderByCreatedAtDesc(pageable, userService.getCurrentUser());
	}

}
