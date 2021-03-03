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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
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
@ToString
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
	@JoinColumn(name = "street_id")
	private Street street;
}
