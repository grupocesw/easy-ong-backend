package br.com.grupocesw.easyong.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = true, length = 5)
	private Integer number;
	
	@Size(max = 255, message = "Complement must contain a maximum of 255 digits")
	@Column(name = "complement", nullable = true, length = 255)
	private String complement;

	@Size(max = 12, message = "Latitude must contain a maximum of 12 digits")
	@Column(name = "latitude", nullable = true, length = 12)
	private String latitude;

	@Size(max = 12, message = "Longitude must contain a maximum of 12 digits")
	@Column(name = "longitude", nullable = true, length = 12)
	private String longitude;
	
	@JsonIgnore
	@OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
	private Ngo ngo;
	
	@ManyToOne
	@JoinColumn(name = "street_zip_code_district_id")
	private StreetZipCodeDistrict streetZipCodeDistrict;

	public Address() {}

	public Address(Long id, Integer number, StreetZipCodeDistrict streetZipCodeDistrict) {
		super();
		this.id = id;
		this.number = number;
		this.streetZipCodeDistrict = streetZipCodeDistrict;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Ngo getNgo() {
		return ngo;
	}
	
	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public StreetZipCodeDistrict getStreetZipCodeDistrict() {
		return streetZipCodeDistrict;
	}

	public void setStreetZipCodeDistrict(StreetZipCodeDistrict streetZipCodeDistrict) {
		this.streetZipCodeDistrict = streetZipCodeDistrict;
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
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
