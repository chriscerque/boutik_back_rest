package net.ent.etrs.boutik.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.entities.EtatCommande;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCommande;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import net.ent.etrs.boutik.model.facades.FacadeUser;
import net.ent.etrs.boutik.model.facades.api.dtos.DtoCommande;
import net.ent.etrs.boutik.utils.CDIUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoCommandeConverter {

    private static FacadeCommande facadeCommande;
    private static FacadeUser facadeUser;
    private static FacadeProduit facadeProduit;

    static {
        facadeCommande = CDIUtils.getBean(FacadeCommande.class);
        facadeUser = CDIUtils.getBean(FacadeUser.class);
        facadeProduit = CDIUtils.getBean(FacadeProduit.class);
    }


    public static DtoCommande toDto(Commande commande) {

//
//        Map<Produit, Integer> map = Map.copyOf(facadeCommande.findByIdWithProduits(commande.getId()));
//        //TODO sout
//        System.out.println("map" + map);
//        map.entrySet().forEach(System.out::println);
//        Map<Long, Integer> mapDto = new HashMap<>();
//        map.entrySet().stream().forEach(e -> mapDto.put(e.getKey().getId(), e.getValue()));

        return DtoCommande.builder()
                .id(commande.getId())
                .user(commande.getUser().getId())
                .etat(commande.getEtat().name())
                .createdAt(commande.getCreatedAt().format(DateTimeFormatter.ofPattern(Constantes.PATTERN_DATE_TIME)))
//                .produits(mapDto)
                .build();
    }

    public static DtoCommande toDtoWithProduits(Commande commande) {
////TODO sout
//        commande.getProduits().entrySet().forEach(System.out::println);
        Commande c = facadeCommande.findByIdWithProduits(commande.getId()).get();
        Map<Produit, Integer> map = Map.copyOf(c.getProduits());
////        //TODO sout
//        System.out.println("map" + map);
//        map.entrySet().forEach(System.out::println);


        Map<Long, Integer> mapDto = new HashMap<>();
        map.entrySet().stream().forEach(e -> mapDto.put(e.getKey().getId(), e.getValue()));

        return DtoCommande.builder()
                .id(c.getId())
                .user(c.getUser().getId())
                .etat(c.getEtat().name())
                .createdAt(c.getCreatedAt().format(DateTimeFormatter.ofPattern(Constantes.PATTERN_DATE_TIME)))
                .produits(mapDto)
                .build();
    }

    public static Set<DtoCommande> toDtoList(Set<Commande> commandes) {
        return commandes.stream().map(DtoCommandeConverter::toDto).collect(Collectors.toSet());
    }

    public static Commande toEntity(DtoCommande dtoCommande) throws Exception {
        try {


            Commande c = new Commande();
            c.setId(dtoCommande.getId());
            c.setUser(facadeUser.findById(dtoCommande.getUser()).orElseThrow(Exception::new));
            c.setEtat(EtatCommande.valueOf(dtoCommande.getEtat()));
            c.setCreatedAt(LocalDateTime.parse(dtoCommande.getCreatedAt(), DateTimeFormatter.ofPattern(Constantes.PATTERN_DATE_TIME)));

            dtoCommande.getProduits().entrySet().forEach(e -> {

                try {
                    c.addProduit(facadeProduit.findById(e.getKey()).orElseThrow(Exception::new), e.getValue());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            });

            return c;
        } catch (RuntimeException ex) {
            throw new Exception(ex);
        }
    }
}
