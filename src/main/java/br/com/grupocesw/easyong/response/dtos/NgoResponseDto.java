package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.User;
import lombok.Data;

@Data
public class NgoResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private boolean favorited;
	private PictureResponseDto picture;

	public NgoResponseDto(Ngo ngo) {
		id = ngo.getId();
		name = ngo.getName();
		description = ngo.getDescription();
		
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			
			User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (ngo.getUsers() != null) {
				for (User user: ngo.getUsers()) {
					if (authenticatedUser.getUsername().equals(user.getUsername()))
						favorited = true;
				}
			}			
		}

		if (!ngo.getPictures().isEmpty() && ngo.getPictures().size() > 0)
		    picture = new PictureResponseDto(
		    		ngo.getPictures().stream().findFirst().get()
		    	);
		else
			picture = new PictureResponseDto(new Picture());
	}
}
