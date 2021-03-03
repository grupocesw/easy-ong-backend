package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "countries")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@NotEmpty(message = "Abbreviation required")
	@Size(min = 3, max = 3, message = "Abbreviation must contain 3 characters")
	@Column(name = "abbreviation", nullable = false, length = 3)
	private String abbreviation;
	
	@OneToMany(targetEntity = State.class, mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<State> states = new HashSet<>();
}
