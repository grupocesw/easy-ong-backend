package br.com.grupocesw.easyong.dto;

import java.io.Serializable;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;

public class FrequentlyAskedQuestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String question;
	private String answer;

	public FrequentlyAskedQuestionDTO() {
	}

	public FrequentlyAskedQuestionDTO(FrequentlyAskedQuestion faq) {
		id = faq.getId();
		question = faq.getQuestion();
		answer = faq.getAnswer();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
