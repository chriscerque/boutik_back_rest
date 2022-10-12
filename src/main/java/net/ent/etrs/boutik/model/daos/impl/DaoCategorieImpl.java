package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCategorie;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.Categorie;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DaoCategorieImpl extends JpaBaseDao<Categorie, Serializable> implements DaoCategorie {
    @Override
    public List<Categorie> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT c FROM Categorie c LEFT JOIN c.pere c2 WHERE 1=1 ";
    
        String libelle = null;
        String libellePere = null;
    
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
    
        if (filterBy.containsKey("pere.libelle")) {
            libellePere = (String) filterBy.get("pere.libelle").getFilterValue();
        }
    
        if (libelle != null) {
            sql += " AND LOWER(c.libelle) LIKE :libelle ";
        }
    
        if (libellePere != null) {
            sql += " AND LOWER(c2.libelle) LIKE :libellePere ";
        }
    
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, SortMeta> sort : sortBy.entrySet()) {
                sql += " c." + sort.getValue().getField() + " " + (sort.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY c.libelle ASC, c.pere.libelle ASC ";
        }
    
        TypedQuery<Categorie> q = this.em.createQuery(sql, Categorie.class);
    
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
    
        if (libellePere != null) {
            q.setParameter("libellePere", libellePere.toLowerCase()+"%");
        }
        
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT COUNT(c) FROM Categorie c WHERE 1=1 ";
    
        String libelle = null;
        String libellePere = null;
    
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
    
        if (filterBy.containsKey("pere.libelle")) {
            libellePere = (String) filterBy.get("pere.libelle").getFilterValue();
        }
    
        if (libelle != null) {
            sql += " AND LOWER(c.libelle) LIKE :libelle ";
        }
    
        if (libellePere != null) {
            sql += " AND LOWER(c.pere.libelle) LIKE :libellePere ";
        }
    
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);
    
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
    
        if (libellePere != null) {
            q.setParameter("libellePere", libellePere.toLowerCase()+"%");
        }
    
        return q.getSingleResult().intValue();
    }
}
