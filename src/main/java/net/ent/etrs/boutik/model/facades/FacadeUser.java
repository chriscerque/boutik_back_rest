package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoUser;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.User;
import org.apache.commons.collections4.IterableUtils;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@Local
public class FacadeUser implements Serializable {
    @Inject
    private DaoUser daoUser;

    public Optional<User> findById(Long id) throws Exception {
        try {
            return this.daoUser.find(id);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Set<User> findAll() throws Exception {
        try {
            return IterableUtils.toList(this.daoUser.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Optional<User> save(User user) throws Exception {
        try {
            return this.daoUser.save(user);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public void delete(User user) throws Exception {
        try {
            this.daoUser.delete(user.getId());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Integer count() throws Exception {
        try {
            return Math.toIntExact(this.daoUser.count());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Optional<User> findByLogin(String login) {
        return this.daoUser.findByLogin(login);
    }

    public Set<User> findAllClient() {
        return new LinkedHashSet<>(this.daoUser.findAllClient());
    }
}
