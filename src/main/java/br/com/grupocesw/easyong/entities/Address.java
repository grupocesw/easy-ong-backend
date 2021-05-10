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

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = true, length = 7)
	private Integer number;
	
	@Column(name = "street", nullable = true, length = 255)
	private String street;
	
	@Column(name = "complement", nullable = true, length = 255)
	private String complement;
	
	@Column(name = "zip_code", nullable = true, length = 8)
	private String zipCode;

	@Column(name = "latitude", nullable = true, length = 12)
	private String latitude;

	@Column(name = "longitude", nullable = true, length = 12)
	private String longitude;

	@Column(name = "district", nullable = true, length = 255)
	private String district;
	
	@JsonIgnore
	@ManyToOne(targetEntity = City.class)
	private City city;
	
	@JsonIgnore
	@OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
	private Ngo ngo;
}
