package br.com.grupocesw.easyong.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ngo_more_informations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NgoMoreInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Information required")
	@Size(min = 3, max = 255, message = "Information must contain between 3 and 255 characters")
	@Column(name = "information", nullable = false, length = 255)
	private String information;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ngo_id")
	private Ngo ngo;
}
