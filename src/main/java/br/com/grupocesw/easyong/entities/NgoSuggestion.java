package br.com.grupocesw.easyong.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ngo_suggestions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NgoSuggestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Message required")
	@Column(name = "message", nullable = false, columnDefinition="TEXT")
	private String message;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
