package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class District implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Name required")
	@Size(min = 3, max = 255, message = "Name must contain between 3 and 255 characters")
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "districts")
	private Set<StreetZipCodeDistrict> streetZipCodeDistricts = new HashSet<>();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	public District() {}

	public District(Long id, String name, City city) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
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
	
	public Set<StreetZipCodeDistrict> getStreetZipCodeDistricts() {
		return streetZipCodeDistricts;
	}

	public void setStreetZipCodeDistricts(Set<StreetZipCodeDistrict> streetZipCodeDistricts) {
		this.streetZipCodeDistricts = streetZipCodeDistricts;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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
		District other = (District) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
