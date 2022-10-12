package net.ent.etrs.boutik.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UTILISATEUR", uniqueConstraints = {
        @UniqueConstraint(name = "UTILISATEUR__LOGIN__UK", columnNames = "LOGIN")
})
@ToString(callSuper = true, of = {"login","role"})
public class User extends AbstractEntity{

    @Getter @Setter
    @NotBlank
    @Column(name = "LOGIN", length = 100, nullable = false)
    private String login;
    
    @Getter @Setter
    @NotBlank
    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;
 
    @Getter @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 50)
    private Role role;
}
