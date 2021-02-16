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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class StreetZipCodeDistrict implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="street_id")
	private Street street;
	
	@ManyToOne
	@JoinColumn(name="zip_code_id")
	private ZipCode zipCode;
	
	@ManyToOne
	@JoinColumn(name="district_id")
	private District district;
	
	@JsonIgnore
	@OneToMany(targetEntity=Address.class, mappedBy="streetZipCodeDistrict", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Address> addresses = new HashSet<>();

	public StreetZipCodeDistrict() {}
	
	public StreetZipCodeDistrict(Long id, Street street, ZipCode zipCode, District district) {
		super();
		this.id = id;
		this.street = street;
		this.zipCode = zipCode;
		this.district = district;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
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
