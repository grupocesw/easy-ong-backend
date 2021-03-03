package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Title required")
	@Size(min = 3, max = 100, message = "Title must contain between 3 and 100 characters")
	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@NotEmpty(message = "Name required")
	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "notifications")
	private Set<User> users = new HashSet<>();
}
