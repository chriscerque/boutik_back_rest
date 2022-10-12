package net.ent.etrs.boutik.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoCommande {

    private Long id;
    private Long user;
    private String etat;
    private String createdAt;
    private Map<Long, Integer> produits;
}
