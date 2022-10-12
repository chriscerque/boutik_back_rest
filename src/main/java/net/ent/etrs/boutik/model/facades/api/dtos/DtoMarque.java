package net.ent.etrs.boutik.model.facades.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoMarque {

    private Long id;
    private String libelle;
    private String logo;
}
