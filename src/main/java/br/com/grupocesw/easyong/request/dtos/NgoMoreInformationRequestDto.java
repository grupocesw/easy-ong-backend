package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NgoMoreInformationRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Information required")
	@Size(min = 3, max = 255, message = "Information must contain between 3 and 255 characters")
	private String information;

}