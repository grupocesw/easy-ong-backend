package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.entities.Person;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.Gender;
import br.com.grupocesw.easyong.services.RoleService;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RoleService roleService;
	
	@Email(message = "Invalid email format")
	@NotEmpty(message = "Username required")
	@Size(min = 3, max = 100, message = "Username must contain between 3 and 100 characters")
	private String username;

	@NotEmpty(message = "Password required")
	@Size(min = 6, max = 100, message = "Password must contain between 6 and 100 characters")
	private String password;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
    private String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
	
    private Gender gender;

	private Set<Long> causeIds;
    
    public User build() {
    	Person person = Person.builder()
			.name(name)
			.birthday(birthday)
			.gender(gender)
			.build();
		
		return User.builder()
			.username(username)
			.password(password)
			.person(person)
			.build();
    }

}