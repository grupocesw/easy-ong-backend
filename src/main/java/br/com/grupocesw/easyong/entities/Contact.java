package br.com.grupocesw.easyong.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.grupocesw.easyong.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Type required")
	@Column(name = "type", nullable = false)
	private ContactType type;

	@NotEmpty(message = "Content required")
	@Size(min = 3, max = 255, message = "Content must contain between 3 and 255 characters")
	@Column(name = "content", nullable = false)
	private String content;

	@JsonIgnore
	@ManyToMany(mappedBy = "contacts")
	private Set<Ngo> ngos;
}
