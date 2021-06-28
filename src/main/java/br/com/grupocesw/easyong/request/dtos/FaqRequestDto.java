package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty(message = "Question required")
	@Size(min = 3, max = 255, message = "Question must contain between 3 and 255 characters")
	private String question;

	@NotNull
	@NotEmpty(message = "Answer required")
	@Size(min = 3, max = 255, message = "Answer must contain between 3 and 255 characters")
	private String answer;
}