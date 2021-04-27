package br.com.grupocesw.easyong.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private GenderEnum gender;
	private String username;
	private Set<SocialCause> causes;
	private Set<Role> roles;
	private PictureDTO picture;

	public UserDTO(User user) {
		id = user.getId();
		name = user.getPerson().getName();
		birthday = user.getPerson().getBirthday();
		gender = user.getPerson().getGender();
		username = user.getUsername();
		causes = user.getCauses();
		roles = user.getRoles();
		picture = new PictureDTO(user.getPicture());
	}
}
