package net.ent.etrs.boutik.model.daos;

import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.User;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DaoUser extends BaseDao<User, Serializable>{
    Optional<User> findByLogin(String login);
    
    List<User> findAllClient();
}
