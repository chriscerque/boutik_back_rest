package net.ent.etrs.boutik.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoProduit;
import net.ent.etrs.boutik.utils.CDIUtils;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoProduitConverter {

    private static FacadeMarque facadeMarque;
    private static FacadeCategorie facadeCategorie;
    private static FacadeProduit facadeProduit;

    static {
        facadeMarque = CDIUtils.getBean(FacadeMarque.class);
        facadeCategorie = CDIUtils.getBean(FacadeCategorie.class);
        facadeProduit = CDIUtils.getBean(FacadeProduit.class);
    }


    public static DtoProduit toDto(Produit produit) {
        return DtoProduit.builder()
                .id(produit.getId())
                .libelle(produit.getLibelle())
                .marque(produit.getMarque().getId())
                .categorie(produit.getCategorie().getId())
                .description(produit.getDescription())
                .prix(produit.getPrixHT())
                .poids(produit.getPoidsKg())
                .image(produit.getImage() != null ? Base64.getEncoder().withoutPadding().encodeToString(produit.getImage()) : null)
                .build();
    }

    public static Set<DtoProduit> toDtoList(Set<Produit> produits) {
        return produits.stream().map(DtoProduitConverter::toDto).collect(Collectors.toSet());
    }

    public static Produit toEntity(DtoProduit dtoProduit) throws Exception {
        Produit p = new Produit();
        p.setId(dtoProduit.getId());
        p.setLibelle(dtoProduit.getLibelle());
        p.setMarque(facadeMarque.findById(dtoProduit.getMarque()).orElseThrow(Exception::new));
        p.setCategorie(facadeCategorie.findById(dtoProduit.getCategorie()).orElseThrow(Exception::new));
        p.setDescription(dtoProduit.getDescription());
        p.setPrixHT(dtoProduit.getPrix());
        p.setPoidsKg(dtoProduit.getPoids());

        if (dtoProduit.getImage() != null) {
            p.setImage(Base64.getDecoder().decode(dtoProduit.getImage()));
        }
        return p;

    }
}
