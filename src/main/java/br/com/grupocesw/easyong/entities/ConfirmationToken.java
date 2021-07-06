package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "confirmation_tokens")
@Getter
@Setter
public class ConfirmationToken implements Serializable {

	private static final long TOKEN_EXPIRATION_TIME_IN_MINUTES = 15;

	@Id
	@SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmation_token_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
	private Long id;

	@Column(nullable = false)
	private String token;

	@Builder.Default
	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	@Column(nullable = false)
	private LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME_IN_MINUTES);;

	private LocalDateTime confirmedAt;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

}
