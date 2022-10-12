package net.ent.etrs.boutik.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = false, of = {"libelle","marque"})
@ToString(callSuper = true, of = {"libelle","prixHT", "poidsKg", "categorie", "marque"})
@NoArgsConstructor

@Entity
@Table(name = "PRODUIT", uniqueConstraints = @UniqueConstraint(name = "PRODUIT__LIBELLE_MARQUE__UK", columnNames = {"LIBELLE","MARQUE_ID"}))
public class Produit extends AbstractEntity{

    @Getter
    @Setter
    @NotBlank(message = "l'attribut libelle ne peut pas etre blanc")
    @Column(name = "LIBELLE", length = 255, nullable = false)
    private String libelle;

    @Getter
    @Setter
    @NotBlank(message = "l'attribut description ne peut pas etre blanc")
    @Lob
    @Column(name = "DESCRIPTION", length = 1000, nullable = false)
    private String description;

    @Getter
    @Setter
    @NotNull(message = "l'attribut prixHT ne peut pas etre null")
    @Positive(message = "La prix doit être positif")
    @Column(name = "PRIX_HT", nullable = false)
    private Float prixHT;

    @Getter
    @Setter
    @NotNull(message = "l'attribut poidsKg ne peut pas etre null")
    @Positive(message = "le poids doit être positif")
    @Column(name = "POIDS_KG", nullable = false)
    private Float poidsKg;

    @Getter
    @Setter
    @Lob
    @Column(name = "IMAGE", nullable = true)
    private byte[] image;

    @Getter
    @Setter
    @NotNull(message = "l'attribut categorie ne peut pas etre null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORIE_ID", foreignKey = @ForeignKey(name = "PRODUIT__CATEGORIE_ID__FK"))
    private Categorie categorie;

    @Getter
    @Setter
    @NotNull(message = "l'attribut marque ne peut pas etre null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MARQUE_ID", foreignKey = @ForeignKey(name = "PRODUIT__MARQUE_ID__FK"))
    private Marque marque;


}
