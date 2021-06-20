package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;

@Service
public interface UserService {

	UserResponseDto create(UserCreateRequestDto request);
	
	UserResponseDto retrieve(Long id);

	UserResponseDto update(Long id, UserUpdateRequestDto request);
	
	UserResponseDto updateMe(UserUpdateRequestDto request);
	
	void delete(Long id);
	
	List<UserResponseDto> findAll();
	
	Page<UserResponseDto> findCheckedAll(Pageable pageable);
	
	void favorite(Long ngoId);
	
	UserResponseDto getMe();

	JwtAuthenticationResponseDto login(LoginRequestDto requestDto);
    
    void changePassword(UserPasswordRequestDto request);
     
    Optional<User> findByUsername(String username);
    
    Boolean existsByUsername(String username);

	void enableUser(User user);

	Page<NgoSlimResponseDto> getFavoriteNgos(Pageable pageable);
}
