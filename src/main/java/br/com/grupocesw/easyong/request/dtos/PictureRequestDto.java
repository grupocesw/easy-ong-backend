package br.com.grupocesw.easyong.request.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Url can't empty")
	private String url;

}