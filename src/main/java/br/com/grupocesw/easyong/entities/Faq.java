package br.com.grupocesw.easyong.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.grupocesw.easyong.entities.validators.FaqValidator;
import lombok.*;

@Entity
@Table(name = "faqs")
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Faq {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "question", nullable = false)
	private String question;

	@Column(name = "answer", nullable = false)
	private String answer;

	public Faq(Long id, String question, String answer) {
		FaqValidator.validate(id, question, answer);

		this.id = id;
		this.question = question;
		this.answer = answer;
	}
}
