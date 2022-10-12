package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCategorie;
import net.ent.etrs.boutik.model.daos.DaoMarque;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Marque;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DaoMarqueImpl extends JpaBaseDao<Marque, Serializable> implements DaoMarque {
    
    @Override
    public List<Marque> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        
        String sql = "SELECT m FROM Marque m WHERE 1=1 ";
        
        String libelle = null;
        
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
        
        if (libelle != null) {
            sql += " AND LOWER(m.libelle) LIKE :libelle ";
        }
        
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, SortMeta> sort : sortBy.entrySet()) {
                sql += " m." + sort.getValue().getField() + " " + (sort.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY m.libelle ASC ";
        }
    
        TypedQuery<Marque> q = this.em.createQuery(sql, Marque.class);
    
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
        
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT COUNT(m) FROM Marque m WHERE 1=1 ";
    
        String libelle = null;
    
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
    
        if (libelle != null) {
            sql += " AND LOWER(m.libelle) LIKE :libelle ";
        }
    
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);
    
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
    
        return q.getSingleResult().intValue();
    }
}
