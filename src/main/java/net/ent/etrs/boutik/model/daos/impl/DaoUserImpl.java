package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCategorie;
import net.ent.etrs.boutik.model.daos.DaoUser;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Role;
import net.ent.etrs.boutik.model.entities.User;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DaoUserImpl extends JpaBaseDao<User, Serializable> implements DaoUser {
    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return Optional.of(this.em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<User> findAllClient() {
        return this.em.createQuery("SELECT u FROM User u WHERE u.role = :role ORDER BY u.login ASC")
                .setParameter("role", Role.USER)
                .getResultList();
    }
}
