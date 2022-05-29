package at.fhv.ec.infrastructure;

import at.fhv.ec.domain.model.user.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class HibernateUserRepository implements PanacheRepository<User> {
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(find("username", username).firstResult());
    }
}
