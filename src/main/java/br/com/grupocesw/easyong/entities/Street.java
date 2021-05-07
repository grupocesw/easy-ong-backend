package br.com.grupocesw.easyong.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "streets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Street {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@ManyToOne(targetEntity = ZipCode.class, cascade = CascadeType.ALL)
	private ZipCode zipCode;
	
	@JsonIgnore
	@ManyToOne(targetEntity = District.class, cascade = CascadeType.ALL)
	private District district;
	
	@JsonIgnore
	@OneToMany(targetEntity=Address.class, mappedBy="street", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Address> addresses;
}
