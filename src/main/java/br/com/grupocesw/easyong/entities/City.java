package br.com.grupocesw.easyong.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "cities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@JsonIgnore
	@ManyToOne(targetEntity = State.class, cascade = CascadeType.ALL)
	private State state;

	@Override
	public String toString() {
		return String.format("%s - %s", name, state.getAbbreviation());
	}
}
