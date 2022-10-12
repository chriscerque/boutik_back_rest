package net.ent.etrs.boutik.model.daos;

import net.ent.etrs.boutik.model.entities.Commande;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DaoCommande extends BaseDao<Commande, Serializable>{
    List<Commande> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
    
    int count(Map<String, FilterMeta> filterBy);
    
    Optional<Commande> findByIdWithProduits(Long id);
}
