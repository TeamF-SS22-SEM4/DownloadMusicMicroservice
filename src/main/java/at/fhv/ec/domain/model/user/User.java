package at.fhv.ec.domain.model.user;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @EmbeddedId
    private UserId userId;
    private String username;

    public static User create(UserId userId, String username) {
        return new User(userId, username);
    }

    protected User(){}

    private User(UserId userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
