package net.ent.etrs.boutik.model.daos;

import net.ent.etrs.boutik.model.entities.Marque;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoMarque extends BaseDao<Marque, Serializable>{
    
    List<Marque> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);
    
    int count(Map<String, FilterMeta> filterBy);
}
