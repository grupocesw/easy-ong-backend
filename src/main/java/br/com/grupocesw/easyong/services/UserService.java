package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

	User create(User request);

	User retrieve(Long id);

	User update(Long id, User request);

//	User updateMe(User request);

	void delete(Long id);

	void existsOrThrowsException(Long id);

	List<User> findAll();

	Page<User> findCheckedAll(Pageable pageable);

	void favorite(Long userId, Long ngoId);

//	User getMe();

	JwtAuthenticationResponseDto login(User request);

    void changePassword(Long id, User request);

    void recoverPassword(User request);

	User confirmUserRecoverPassword(String token, User request);

    User findByUsernameOrThrowUserNotExistException(String username);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

	void enableUser(User user);

	Page<Ngo> getFavoriteNgos(Long userId, Pageable pageable);
}
