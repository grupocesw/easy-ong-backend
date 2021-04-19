package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.User;

@Service
public interface UserService {

	public List<User> findAll();

	public User create(User user);
	
	public User retrieve(Long id);

	public User update(Long id, User user);
	
	public void delete(Long id);
	
	public Page<User> findByAllChecked(Pageable pageable);
	
	public User getMe();
	
	public User getUserByUsername(String username);

	public User getOneChecked(Long id);
	
    public String login(String username, String password);

     public User register(User user);
     
     public Optional<User> findByUsername(String username);
     
 	public void favorite(Long ngoId);

	public void verify(String username, String code);
	
	public void reSendVerification(String username) throws Exception;
}
