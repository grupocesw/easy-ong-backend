package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.services.RoleService;
import lombok.Data;

@Data
public class AuthChangePasswordRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final RoleService roleService;
    
	@NotEmpty(message = "Password required")
	@Size(min = 6, max = 100, message = "Password must contain between 6 and 100 characters")
	private String password;
	
    public User build() {
		User user = User.builder()
			.password(password)
			.build();
    	
		return user;
    }

}