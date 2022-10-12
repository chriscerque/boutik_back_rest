package net.ent.etrs.boutik.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoCategorie {

    private Long id;
    private String libelle;
    private Long pere;
}
