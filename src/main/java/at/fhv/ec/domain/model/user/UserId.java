package at.fhv.ec.domain.model.user;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserId implements Serializable {
    @Column
    @Type(type = "uuid-char")
    private UUID userId;

    public UserId(UUID userId) {
        this.userId = userId;
    }

    protected UserId() {
    }

    public UUID getUUID() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId1 = (UserId) o;
        return Objects.equals(userId, userId1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
