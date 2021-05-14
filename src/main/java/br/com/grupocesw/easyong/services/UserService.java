package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;

@Service
public interface UserService {

	public UserResponseDto create(UserCreateRequestDto request);
	
	public UserResponseDto retrieve(Long id);

	public UserResponseDto update(Long id, UserUpdateRequestDto request);
	
	public UserResponseDto updateMe(UserUpdateRequestDto request);
	
	public void delete(Long id);
	
	public List<UserResponseDto> findAll();
	
	public Page<UserResponseDto> findCheckedAll(Pageable pageable);
	
	public void favorite(Long ngoId);
	
	public UserResponseDto getMe();
	
    public String login(String username, String password);
    
    public void changePassword(UserPasswordRequestDto request);
     
    public Optional<User> findByUsername(String username);
    
    public Boolean existsByUsername(String username);

	public void enableUser(User user);

	public Page<NgoResponseDto> getFavoriteNgos(Pageable pageable);
}
