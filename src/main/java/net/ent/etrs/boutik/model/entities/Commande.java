package net.ent.etrs.boutik.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "COMMANDE", uniqueConstraints = {
        @UniqueConstraint(name = "COMMANDE___CREATED_AT__USER_ID___UK", columnNames = {"CREATED_AT", "USER_ID"})
})
@ToString(callSuper = true, of = {"createdAt", "user", "prix", "etat"})
@EqualsAndHashCode(callSuper = false, of = {"createdAt", "user"})
public class Commande extends AbstractEntity {

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "COMMANDE__USER_ID__FK"))
    private User user;

    @Getter
    @Setter
    @NotNull
    @PastOrPresent
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ETAT", nullable = false, length = 20)
    private EtatCommande etat;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "COMMANDE_PRODUIT", joinColumns = @JoinColumn(name = "COMMANDE_ID", foreignKey = @ForeignKey(name = "COMMANDE_PRODUIT__COMMANDE_ID__FK",
            foreignKeyDefinition = "foreign key (PRODUIT_ID) references Produit (id) on delete cascade"))
    )
    @MapKeyJoinColumn(name = "PRODUIT_ID", foreignKey = @ForeignKey(name = "COMMANDE_PRODUIT__PRODUIT_ID__FK"))
    @Column(name = "QTE")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Cascade(value={CascadeType.ALL})
    private Map<Produit, Integer> produits = new HashMap<>();

    @Getter
    @NotNull
    @Column(name = "PRIX", nullable = false)
    private Float prix;

    public Map<Produit, Integer> getProduits() {
        return Collections.unmodifiableMap(this.produits);
    }

    public void addProduit(Produit produit, Integer qte) {
        if (qte < 1) {
            this.produits.remove(produit);
        } else {
            this.produits.put(produit, qte);
        }
        this.updatePrix();
    }

    private void updatePrix() {
        Float montant = 0f;
        for (Map.Entry<Produit, Integer> e : this.produits.entrySet()) {
            montant += e.getKey().getPrixHT() * e.getValue();
        }
        this.prix = montant;
    }
}
