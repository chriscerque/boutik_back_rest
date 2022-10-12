package net.ent.etrs.boutik.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoCategorie;
import net.ent.etrs.boutik.utils.CDIUtils;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoCategorieConverter {

    private static FacadeCategorie facadeCategorie;

    static {
        facadeCategorie = CDIUtils.getBean(FacadeCategorie.class);
    }


    public static DtoCategorie toDto(Categorie categorie) {
        return DtoCategorie.builder()
                .id(categorie.getId())
                .libelle(categorie.getLibelle())
                .pere(categorie.getPere() != null ? categorie.getPere().getId() : null)
                .build();
    }

    public static Set<DtoCategorie> toDtoList(Set<Categorie> categories) {
        return categories.stream().map(DtoCategorieConverter::toDto).collect(Collectors.toSet());
    }

    public static Categorie toEntity(DtoCategorie dtoCategorie) throws Exception {
        Categorie c = new Categorie();
        c.setId(dtoCategorie.getId());
        c.setLibelle(dtoCategorie.getLibelle());
        if (dtoCategorie.getPere() != null) {
            c.setPere(facadeCategorie.findById(dtoCategorie.getPere()).orElseThrow(Exception::new));
        }
        return c;

    }
}
