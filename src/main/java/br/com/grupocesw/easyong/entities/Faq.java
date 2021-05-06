package br.com.grupocesw.easyong.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "frequently_asked_questions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Faq {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Question required")
	@Size(min = 3, max = 255, message = "Question must contain between 3 and 255 characters")
	@Column(name = "question", nullable = false, length = 255)
	private String question;

	@NotEmpty(message = "Answer required")
	@Size(min = 3, max = 255, message = "Answer must contain between 3 and 255 characters")
	@Column(name = "answer", nullable = false, length = 255)
	private String answer;
}
