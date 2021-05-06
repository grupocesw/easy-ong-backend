package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private GenderEnum gender;
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
