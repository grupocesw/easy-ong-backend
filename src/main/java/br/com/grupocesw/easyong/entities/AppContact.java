package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.enums.AppReasonContact;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "app_contacts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AppContact implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Column(name = "reason")
	private AppReasonContact reason = AppReasonContact.OTHER;
	
	@NotEmpty(message="Message required")
	@Column(name = "message", nullable = false, columnDefinition = "TEXT")
	private String message;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
