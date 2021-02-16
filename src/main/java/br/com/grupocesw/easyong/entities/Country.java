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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
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

	public Country() {
	}

	public Country(Long id, String name, String abbreviation) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String setName(String name) {
		return name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public Set<State> getStates() {
		return states;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
