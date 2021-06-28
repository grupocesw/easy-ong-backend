package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String question;
	private String answer;

}
