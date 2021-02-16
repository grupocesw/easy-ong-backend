package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class StreetZipCodeDistrict implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@JoinTable(name = "street_zip_code_district_street", joinColumns = @JoinColumn(name = "street_id"), inverseJoinColumns = @JoinColumn(name = "street_zip_code_district_id"))
	private Set<Street> streets = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "street_zip_code_district_zip_code", joinColumns = @JoinColumn(name = "zip_code_id"), inverseJoinColumns = @JoinColumn(name = "street_zip_code_district_id"))
	private Set<ZipCode> zipCodes = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "street_zip_code_district_district", joinColumns = @JoinColumn(name = "district_id"), inverseJoinColumns = @JoinColumn(name = "street_zip_code_district_id"))
	private Set<District> districts = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(targetEntity=Address.class, mappedBy="streetZipCodeDistrict", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Address> addresses = new HashSet<>();

	public StreetZipCodeDistrict() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Street> getStreets() {
		return streets;
	}

	public Set<ZipCode> getZipCodes() {
		return zipCodes;
	}

	public Set<District> getDistricts() {
		return districts;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
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
		StreetZipCodeDistrict other = (StreetZipCodeDistrict) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
