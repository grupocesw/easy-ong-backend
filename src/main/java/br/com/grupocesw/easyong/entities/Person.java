package br.com.grupocesw.easyong.entities;

import br.com.grupocesw.easyong.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "birthday")
	private LocalDate birthday;

	@Builder.Default
	@Column(name = "gender")
	private Gender gender = Gender.UNINFORMED;

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_id")
	@ToString.Exclude
	private User user;

}
