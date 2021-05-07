package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.enums.ContactTypeEmun;
import lombok.Data;

@Data
public class ContactCreateRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Type required")
	private ContactTypeEmun type;

	@NotEmpty(message = "Content required")
	private String content;

    public Contact build() {
    	return Contact.builder()
    		.type(type)
    		.content(content)
			.build();
    }

}