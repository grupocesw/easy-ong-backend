package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ZipCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Number required")
	@Size(min = 8, max = 8, message = "Number must contain 8 digits")
	@Column(name = "number", nullable = false, length = 8)
	private String number;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "zipCodes")
	private Set<StreetZipCodeDistrict> streetZipCodeDistricts = new HashSet<>();

	public ZipCode() {}

	public ZipCode(Long id, String number) {
		super();
		this.id = id;
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Set<StreetZipCodeDistrict> getStreetZipCodeDistricts() {
		return streetZipCodeDistricts;
	}

	public void setStreetZipCodeDistricts(Set<StreetZipCodeDistrict> streetZipCodeDistricts) {
		this.streetZipCodeDistricts = streetZipCodeDistricts;
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
		ZipCode other = (ZipCode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
