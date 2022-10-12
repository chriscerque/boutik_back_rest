package net.ent.etrs.boutik.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduit {

    private Long id;
    private String libelle;
    private Long marque;
    private Long categorie;
    private Float prix;
    private Float poids;
    private String description;
    private String image;
}
