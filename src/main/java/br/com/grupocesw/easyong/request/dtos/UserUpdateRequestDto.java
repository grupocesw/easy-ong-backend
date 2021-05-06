package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.entities.Person;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.GenderEnum;
import lombok.Data;

@Data
public class UserUpdateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
    private final String name;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate birthday;
	
    private final GenderEnum gender;
    
    private final Set<SocialCause> causes;
    
    public User build() {
    	Person person = Person.builder()
			.name(name)
			.birthday(birthday)
			.gender(gender)
			.build();
		
		User user = User.builder()
			.causes(causes)
			.person(person)
			.build();
    	
		return user;
    }

}