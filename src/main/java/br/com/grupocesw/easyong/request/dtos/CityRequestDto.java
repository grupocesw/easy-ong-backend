package br.com.grupocesw.easyong.request.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	private String name;

	@NotEmpty(message = "State required")
	private StateRequestDto state;
}