package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;

@Service
public interface UserService {

	public User create(User user);
	
	public User retrieve(Long id);

	public User update(Long id, User user);
	
	public User updateMe(User user);
	
	public void delete(Long id);
	
	public List<User> findAll();
	
	public Page<User> findCheckedAll(Pageable pageable);
	
	public User findById(Long id);
	
	public void favorite(Long ngoId);
	
	public User getMe();
	
    public String login(String username, String password);
    
    public User changePassword(User user);
     
    public Optional<User> findByUsername(String username);
    
    public Boolean existsByUsername(String username);

	public void enableUser(User user);
}
