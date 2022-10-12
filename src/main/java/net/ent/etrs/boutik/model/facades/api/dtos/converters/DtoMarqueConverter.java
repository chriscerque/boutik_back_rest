package net.ent.etrs.boutik.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoMarque;
import net.ent.etrs.boutik.utils.CDIUtils;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoMarqueConverter {

    private static FacadeMarque facadeMarque;

    static {
        facadeMarque = CDIUtils.getBean(FacadeMarque.class);
    }


    public static DtoMarque toDto(Marque marque) {
        return DtoMarque.builder()
                .id(marque.getId())
                .libelle(marque.getLibelle())
                .logo(marque.getLogo() != null ? Base64.getEncoder().withoutPadding().encodeToString(marque.getLogo()) : null)
                .build();
    }

    public static Set<DtoMarque> toDtoList(Set<Marque> marques) {
        return marques.stream().map(DtoMarqueConverter::toDto).collect(Collectors.toSet());
    }

    public static Marque toEntity(DtoMarque dtoMarque) throws Exception {
        Marque m = new Marque();
        m.setId(dtoMarque.getId());
        m.setLibelle(dtoMarque.getLibelle());
        if (dtoMarque.getLogo() != null) {
            m.setLogo(Base64.getDecoder().decode(dtoMarque.getLogo()));
        }
        return m;

    }
}
