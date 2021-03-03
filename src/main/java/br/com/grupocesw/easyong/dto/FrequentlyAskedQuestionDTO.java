package br.com.grupocesw.easyong.dto;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FrequentlyAskedQuestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String question;
	private String answer;

	public FrequentlyAskedQuestionDTO(FrequentlyAskedQuestion faq) {
		id = faq.getId();
		question = faq.getQuestion();
		answer = faq.getAnswer();
	}
}
