package br.com.grupocesw.easyong.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "faqs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Faq implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "question", columnDefinition = "TEXT", nullable = false)
	private String question;

	@Column(name = "answer", columnDefinition = "TEXT", nullable = false)
	private String answer;

}
