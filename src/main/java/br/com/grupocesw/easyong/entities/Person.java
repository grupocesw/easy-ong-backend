package br.com.grupocesw.easyong.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 100, message = "Name must contain between 3 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "birthday", nullable = true)
	private LocalDate birthday;

	@Column(name = "gender", nullable = true)
	private GenderEnum gender;
	
	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_id")
	private User user;
	
	public Person(Person person) {
		this.id = person.id;
		this.name = person.name;
		this.birthday = person.birthday;
		this.gender = person.gender;
	}

	public Person(String name, LocalDate birthday, GenderEnum gender) {
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
	}
	
	public Person(String name) {
		this.name = name;
	}
}
