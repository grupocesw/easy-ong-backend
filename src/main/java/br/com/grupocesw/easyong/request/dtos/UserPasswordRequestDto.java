package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	@NotEmpty(message = "Password required")
	@Size(min = 6, max = 100, message = "Password must contain between 6 and 100 characters")
	private String password;
	
    public User build() {
		return User.builder()
			.password(password)
			.build();
    }

}