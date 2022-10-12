package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCategorie;
import net.ent.etrs.boutik.model.daos.DaoProduit;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.entities.Produit;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DaoProduitImpl extends JpaBaseDao<Produit, Serializable> implements DaoProduit {
    
    @Override
    public List<Produit> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT p FROM Produit p WHERE 1=1 ";
    
        String libelle = null;
        Float prixHT = null;
        Float poidsKg = null;
        Categorie categorie = null;
        Marque marque = null;
    
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
    
        if (filterBy.containsKey("prixHT")) {
            prixHT = Float.parseFloat((String) filterBy.get("prixHT").getFilterValue());
        }
    
        if (filterBy.containsKey("poidsKg")) {
            poidsKg = Float.parseFloat((String) filterBy.get("poidsKg").getFilterValue());
        }
    
        if (filterBy.containsKey("categorie")) {
            categorie = (Categorie) filterBy.get("categorie").getFilterValue();
        }
    
        if (filterBy.containsKey("marque")) {
            marque = (Marque) filterBy.get("marque").getFilterValue();
        }
    
        if (libelle != null) {
            sql += " AND LOWER(p.libelle) LIKE :libelle ";
        }
    
        if (prixHT != null) {
            sql += " AND p.prixHT = :prixHT ";
        }
    
        if (poidsKg != null) {
            sql += " AND p.poidsKg = :poidsKg ";
        }
    
        if (categorie != null) {
            sql += " AND p.categorie = :categorie ";
        }
    
        if (marque != null) {
            sql += " AND p.marque = :marque ";
        }
    
        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for(Map.Entry<String, SortMeta> sort : sortBy.entrySet()) {
                sql += " p." + sort.getValue().getField() + " " + (sort.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
            }
            sql = sql.substring(0, sql.length() -1);
        } else {
            sql += " ORDER BY p.libelle ASC ";
        }
    
        TypedQuery<Produit> q = this.em.createQuery(sql, Produit.class);
        
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
    
        if (prixHT != null) {
            q.setParameter("prixHT", prixHT);
        }
    
        if (poidsKg != null) {
            q.setParameter("poidsKg", poidsKg);
        }
    
        if (categorie != null) {
            q.setParameter("categorie", categorie);
        }
    
        if (marque != null) {
            q.setParameter("marque", marque);
        }
    
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
    
        return q.getResultList();
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
    
        String sql = "SELECT COUNT(p) FROM Produit p WHERE 1=1 ";
    
        String libelle = null;
        Float prixHT = null;
        Float poidsKg = null;
        Categorie categorie = null;
        Marque marque = null;
    
        if (filterBy.containsKey("libelle")) {
            libelle = (String) filterBy.get("libelle").getFilterValue();
        }
    
        if (filterBy.containsKey("prixHT")) {
            prixHT = Float.parseFloat((String) filterBy.get("prixHT").getFilterValue());
        }
    
        if (filterBy.containsKey("poidsKg")) {
            poidsKg = Float.parseFloat((String) filterBy.get("poidsKg").getFilterValue());
        }
    
        if (filterBy.containsKey("categorie")) {
            categorie = (Categorie) filterBy.get("categorie").getFilterValue();
        }
    
        if (filterBy.containsKey("marque")) {
            marque = (Marque) filterBy.get("marque").getFilterValue();
        }
    
        if (libelle != null) {
            sql += " AND LOWER(p.libelle) LIKE :libelle ";
        }
    
        if (prixHT != null) {
            sql += " AND p.prixHT = :prixHT ";
        }
    
        if (poidsKg != null) {
            sql += " AND p.poidsKg = :poidsKg ";
        }
    
        if (categorie != null) {
            sql += " AND p.categorie = :categorie ";
        }
    
        if (marque != null) {
            sql += " AND p.marque = :marque ";
        }
    
        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);
    
        if (libelle != null) {
            q.setParameter("libelle", libelle.toLowerCase()+"%");
        }
    
        if (prixHT != null) {
            q.setParameter("prixHT", prixHT);
        }
    
        if (poidsKg != null) {
            q.setParameter("poidsKg", poidsKg);
        }
    
        if (categorie != null) {
            q.setParameter("categorie", categorie);
        }
    
        if (marque != null) {
            q.setParameter("marque", marque);
        }
    
        return q.getSingleResult().intValue();
    }
}
