package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;

@Service
public interface UserService {

	User create(User request);
	
	User retrieve(Long id);

	User update(Long id, User request);

	User updateMe(User request);
	
	void delete(Long id);
	
	List<User> findAll();
	
	Page<User> findCheckedAll(Pageable pageable);

	void favorite(Long ngoId);
	
	User getMe();

	JwtAuthenticationResponseDto login(LoginRequestDto requestDto);
    
    void changePassword(UserPasswordRequestDto request);
     
    Optional<User> findByUsername(String username);
    
    Boolean existsByUsername(String username);

	void enableUser(User user);

	Page<Ngo> getFavoriteNgos(Pageable pageable);
}
