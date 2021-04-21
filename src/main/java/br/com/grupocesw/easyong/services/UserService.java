package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.payloads.UserRequest;

@Service
public interface UserService {

	public User create(UserRequest request);
	
	public User retrieve(Long id);

	public User update(Long id, UserRequest userRequest);
	
	public void delete(Long id);
	
	public List<User> findAll();
	
	public Page<User> findCheckedAll(Pageable pageable);
	
	public User findCheckedById(Long id);
	
	public Optional<User> findByUsernameOptional(String username);
	
	public void favorite(Long ngoId);
	
	public User getMe();
	
	public boolean enable(String username);
	
    public String login(String username, String password);
     
    public User findByUsername(String username);
    
    public Boolean existsByUsername(String username);
}
