package br.com.grupocesw.easyong.request.dtos;

import br.com.grupocesw.easyong.enums.AppReasonContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppContactRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private AppReasonContact reason;

	@NotEmpty(message = "Message required")
	private String message;

}