package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.entities.SocialCause;
import lombok.Data;

@Data
public class SocialCauseCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;

    public SocialCause build() {
    	return SocialCause.builder()
    		.id(id)	
    		.name(name)
			.build();
    }

}