package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.grupocesw.easyong.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "ngos")
public class Contact implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "type", nullable = false)
	private ContactType type;

	@Column(name = "content", nullable = false)
	private String content;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "contacts", cascade = {
		CascadeType.DETACH,
		CascadeType.REFRESH,
		CascadeType.PERSIST,
		CascadeType.MERGE })
	private Set<Ngo> ngos;
}
