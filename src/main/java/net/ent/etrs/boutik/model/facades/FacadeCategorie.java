package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoCategorie;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Categorie;
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
public class FacadeCategorie implements Serializable {
    @Inject
    private DaoCategorie daoCategorie;

    public Optional<Categorie> findById(Long id) throws BusinessException {
        try {
            return this.daoCategorie.find(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Set<Categorie> findAll() throws BusinessException {
        try {
            return IterableUtils.toList(this.daoCategorie.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Optional<Categorie> save(Categorie categorie) throws BusinessException {
        try {
            return this.daoCategorie.save(categorie);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public void delete(Long id) throws BusinessException {
        try {
            this.daoCategorie.delete(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public Integer count() throws BusinessException {
        try {
            return Math.toIntExact(this.daoCategorie.count());
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }

    public List<Categorie> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.daoCategorie.findAll(first, pageSize, sortBy, filterBy);
    }

    public int count(Map<String, FilterMeta> filterBy) {
        return this.daoCategorie.count(filterBy);
    }

    public boolean exist(Long id) throws BusinessException {
        try {
            return this.daoCategorie.exists(id);
        } catch (DaoException e) {
            throw new BusinessException(e);
        }
    }
}
