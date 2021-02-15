package br.com.grupocesw.easyong.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Street required")
	@Size(min = 3, max = 255, message = "Street must contain between 3 and 255 characters")
	@Column(name = "street", nullable = false, length = 255)
	private String street;

	@NotEmpty(message = "Number required")
	@Size(max = 5, message = "Number must contain a maximum of 5 digits")
	@Column(name = "number", nullable = false, length = 5)
	private Integer number;

	@NotEmpty(message = "ZipCode required")
	@Size(min = 8, max = 8, message = "Number must contain a maximum of 8 digits")
	@Column(name = "zip_code", nullable = false, length = 8)
	private Integer zipCode;

	@Size(max = 12, message = "Latitude must contain a maximum of 12 digits")
	@Column(name = "latitude", nullable = true, length = 12)
	private Double latitude;

	@Size(max = 12, message = "Longitude must contain a maximum of 12 digits")
	@Column(name = "longitude", nullable = true, length = 12)
	private Double longitude;

	@NotEmpty(message = "City required")
	@Size(max = 100, message = "City must contain a maximum of 100 characters")
	@Column(name = "city", nullable = false, length = 100)
	private String city;

	@NotEmpty(message = "State required")
	@Size(max = 100, message = "State must contain a maximum of 100 characters")
	@Column(name = "state", nullable = false, length = 100)
	private String state;

	@NotEmpty(message = "Country required")
	@Size(max = 100, message = "Country must contain a maximum of 100 characters")
	@Column(name = "country", nullable = false, length = 100)
	private String country;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ong_id", referencedColumnName = "id")
	private Ngo ngo;

	public Address() {}

	public Address(Long id, String street, Integer number, Integer zipCode, Double latitude, Double longitude,
			String city, String state, String country) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public Ngo getNgo() {
		return ngo;
	}
	
	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
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
