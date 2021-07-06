package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.grupocesw.easyong.enums.ContactType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Type required")
	private ContactType type;

	@NotEmpty(message = "Content required")
	@Size(min = 3, max = 255, message = "Content must contain between 3 and 255 characters")
	private String content;

}