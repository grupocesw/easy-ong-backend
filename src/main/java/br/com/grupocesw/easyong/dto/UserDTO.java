package br.com.grupocesw.easyong.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	private String username;
	private Set<SocialCause> causes = new HashSet<>();
	private Set<Role> roles = new HashSet<>();
	private PictureDTO picture;

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		birthday = user.getBirthday();
		gender = user.getGender();
		username = user.getUsername();
		causes = user.getCauses();
		roles = user.getRoles();
		picture = new PictureDTO(user.getPicture());
	}
}
