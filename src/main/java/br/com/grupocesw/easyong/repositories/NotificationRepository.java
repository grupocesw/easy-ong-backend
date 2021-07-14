package br.com.grupocesw.easyong.repositories;

import br.com.grupocesw.easyong.entities.Notification;
import br.com.grupocesw.easyong.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUsersOrderByCreatedAtDesc(Pageable pageable, User user);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM notification_users WHERE user_id = :userId AND notification_id = :notificationId", nativeQuery = true)
    void deleteByLoggedUser(@Param("userId") Long userId, @Param("notificationId") Long notificationId);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM notification_users WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByLoggedUser(@Param("userId") Long userId);

    boolean existsNotificationByUsers(User user);

}
