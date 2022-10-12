package net.ent.etrs.boutik.model.daos;

import net.ent.etrs.boutik.model.entities.Produit;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoProduit extends BaseDao<Produit, Serializable>{
    int count(Map<String, FilterMeta> filterBy);
    
    List<Produit> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
}
