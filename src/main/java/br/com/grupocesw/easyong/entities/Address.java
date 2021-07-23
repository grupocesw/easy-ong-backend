package br.com.grupocesw.easyong.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Address implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "number", length = 7, nullable = false)
	private String number;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "complement")
	private String complement;
	
	@Column(name = "zip_code", length = 8)
	private String zipCode;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "district", nullable = false)
	private String district;
	
	@JsonIgnore
	@ManyToOne(targetEntity = City.class)
	private City city;
	
	@JsonIgnore
	@OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
	private Ngo ngo;
}
