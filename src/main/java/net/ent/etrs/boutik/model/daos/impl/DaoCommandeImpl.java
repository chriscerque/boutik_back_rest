package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCommande;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DaoCommandeImpl extends JpaBaseDao<Commande, Serializable> implements DaoCommande {
    
    @Override
    public Optional<Commande> findByIdWithProduits(Long id) {
        try {
            return Optional.of(this.em.createQuery("SELECT c FROM Commande c LEFT JOIN FETCH c.produits WHERE c.id = :id", Commande.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<Commande> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        
        String sql = "SELECT c FROM Commande c WHERE 1=1 ";
        
        Float prix = null;
        User user = null;
        LocalDate createdAt = null;
        EtatCommande etat = null;
        
        if (filterBy.containsKey("prix")) {
            prix = Float.parseFloat((String) filterBy.get("prix").getFilterValue());
        }
        
        if (filterBy.containsKey("user")) {
            user = (User) filterBy.get("user").getFilterValue();
        }
        
        if (filterBy.containsKey("createdAt")) {
            createdAt = (LocalDate) filterBy.get("createdAt").getFilterValue();
        }
    
        if (filterBy.containsKey("etat")) {
            etat = (EtatCommande) filterBy.get("etat").getFilterValue();
        }
        
        if (prix != null) {
            sql += " AND c.prix = :prix ";
        }
        
        if (user != null) {
            sql += " AND c.user = :user ";
        }
        
        if (createdAt != null) {
            sql += " AND DATE(c.createdAt) = :createdAt ";
        }
    
        if (etat != null) {
            sql += " AND c.etat = :etat ";
        }
        
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, SortMeta> sort : sortBy.entrySet()) {
                sql += " c." + sort.getValue().getField() + " " + (sort.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY c.createdAt ASC ";
        }
        
        TypedQuery<Commande> q = this.em.createQuery(sql, Commande.class);
    
        if (prix != null) {
            q.setParameter("prix", prix);
        }
    
        if (user != null) {
            q.setParameter("user", user);
        }
    
        if (createdAt != null) {
            q.setParameter("createdAt", createdAt);
        }
    
        if (etat != null) {
            q.setParameter("etat", etat);
        }
        
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        
        return q.getResultList();
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT COUNT(c) FROM Commande c WHERE 1=1 ";
    
        Float prix = null;
        User user = null;
        LocalDate createdAt = null;
        EtatCommande etat = null;
    
        if (filterBy.containsKey("prix")) {
            prix = Float.parseFloat((String) filterBy.get("prix").getFilterValue());
        }
    
        if (filterBy.containsKey("user")) {
            user = (User) filterBy.get("user").getFilterValue();
        }
    
        if (filterBy.containsKey("createdAt")) {
            createdAt = (LocalDate) filterBy.get("createdAt").getFilterValue();
        }
    
        if (filterBy.containsKey("etat")) {
            etat = (EtatCommande) filterBy.get("etat").getFilterValue();
        }
    
        if (prix != null) {
            sql += " AND c.prix = :prix ";
        }
    
        if (user != null) {
            sql += " AND c.user = :user ";
        }
    
        if (createdAt != null) {
            sql += " AND DATE(c.createdAt) = :createdAt ";
        }
    
        if (etat != null) {
            sql += " AND c.etat = :etat ";
        }
    
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);
    
        if (prix != null) {
            q.setParameter("prix", prix);
        }
    
        if (user != null) {
            q.setParameter("user", user);
        }
    
        if (createdAt != null) {
            q.setParameter("createdAt", createdAt);
        }
    
        if (etat != null) {
            q.setParameter("etat", etat);
        }
    
        return q.getSingleResult().intValue();
    }
}
