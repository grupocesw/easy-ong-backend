package br.com.grupocesw.easyong.response.dtos;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.Faq;
import lombok.Data;

@Data
public class FaqResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String question;
	private String answer;

	public FaqResponseDto(Faq faq) {
		id = faq.getId();
		question = faq.getQuestion();
		answer = faq.getAnswer();
	}
}
