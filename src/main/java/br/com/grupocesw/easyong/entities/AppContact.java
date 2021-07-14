package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.enums.AppReasonContact;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

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

	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	private String name;

	@Email(message = "Invalid email format")
	@Size(min = 3, max = 100, message = "Email must contain between 3 and 100 characters")
	private String email;

	@Builder.Default
	@Column(name = "reason")
	private AppReasonContact reason = AppReasonContact.OTHER;
	
	@NotEmpty(message="Message required")
	@Column(name = "message", nullable = false, columnDefinition = "TEXT")
	private String message;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

}
