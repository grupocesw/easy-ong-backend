package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude="users")
@Getter
@Setter
@Builder
@ToString(exclude = "users")
public class Role implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
}
