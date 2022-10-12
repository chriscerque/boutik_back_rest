package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoProduit;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.exceptions.BusinessException;
import org.apache.commons.collections4.IterableUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@Local
public class FacadeProduit implements Serializable {

    @Inject
    private DaoProduit daoProduit;

    public Optional<Produit> findById(Long id) throws BusinessException {
        try {
            return this.daoProduit.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Set<Produit> findAll() throws BusinessException {
        try {
            return IterableUtils.toList(this.daoProduit.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Optional<Produit> save(Produit produit) throws BusinessException {
        try {
            return this.daoProduit.save(produit);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.daoProduit.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Integer count() throws BusinessException {
        try {
            return Math.toIntExact(this.daoProduit.count());
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public int count(Map<String, FilterMeta> filterBy) {
        return this.daoProduit.count(filterBy);
    }

    public List<Produit> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.daoProduit.findAll(first, pageSize, sortBy, filterBy);
    }

    public boolean exist(Long id) throws BusinessException {
        try {
            return this.daoProduit.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
}
