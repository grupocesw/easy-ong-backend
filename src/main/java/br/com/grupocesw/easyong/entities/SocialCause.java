package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Table(name = "social_causes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "users")
@ToString
public class SocialCause implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "causes", cascade = CascadeType.ALL)
    private Set<User> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "causes", cascade = CascadeType.ALL)
    private Set<Ngo> ngos;
}
