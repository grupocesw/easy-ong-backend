package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = { "id", "title" })
public class Notification implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Column(name = "type", nullable = false)
	private NotificationType type = NotificationType.INFORMATION;
	
	@NotEmpty(message = "Title required")
	@Size(min = 3, max = 100, message = "Title must contain between 3 and 100 characters")
	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture picture;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "notification_users", joinColumns = @JoinColumn(name = "notification_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;

}
