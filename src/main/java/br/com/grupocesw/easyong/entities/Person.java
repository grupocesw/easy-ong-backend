package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.grupocesw.easyong.enums.Gender;
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
	
//	public Person(Person person) {
//		this.id = person.id;
//		this.name = person.name;
//		this.birthday = person.birthday;
//		this.gender = person.gender;
//	}
//
//	public Person(String name, LocalDate birthday, Gender gender) {
//		this.name = name;
//		this.birthday = birthday;
//		this.gender = gender;
//	}
//
//	public Person(String name) {
//		this.name = name;
//	}
}
