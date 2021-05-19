package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.Gender;
import lombok.Data;

@Data
public class UserResponseDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String username;
	private Set<SocialCause> causes;
	private Set<Role> roles;
	private PictureResponseDto picture;

	public UserResponseDto(User user) {
		id = user.getId();
		name = user.getPerson().getName();
		birthday = user.getPerson().getBirthday();
		gender = user.getPerson().getGender();
		username = user.getUsername();
		causes = user.getCauses();
		roles = user.getRoles();
		picture = new PictureResponseDto(user.getPicture());
	}
	
}
