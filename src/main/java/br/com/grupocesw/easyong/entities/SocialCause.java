package br.com.grupocesw.easyong.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "social_causes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = "users")
@ToString(of = { "id", "name" })
public class SocialCause implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "causes", cascade = {
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.PERSIST,
        CascadeType.MERGE })
    private Set<User> users;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "causes", cascade = {
        CascadeType.DETACH,
        CascadeType.REFRESH,
        CascadeType.PERSIST,
        CascadeType.MERGE })
    private Set<Ngo> ngos;
}
