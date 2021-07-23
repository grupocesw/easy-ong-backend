package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.Notification;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface NotificationService {

	Notification create(Notification request);

	Notification simpleCreate(NotificationType type, String title, String description, Set<User> users);

	Notification retrieve(Long id);

	void delete(Long id);

	void deleteByLoggedUser(Long id);

	void deleteAllByLoggedUser();

	boolean existsNotificationsByLoggedUser();

	void existsOrThrowsException(Long id);
	
	Page<Notification> findAll(Pageable pageable);

	Page<Notification> getNotifications(Pageable pageable);

}
