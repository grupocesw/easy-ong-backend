package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String type;
	private String content;

	public ContactResponseDto(Contact contact) {
		id = contact.getId();
		type = contact.getType().name();
		content = contact.getContent();
	}
}
