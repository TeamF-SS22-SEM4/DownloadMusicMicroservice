package at.fhv.ec.infrastructure;

import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class HibernateSongRepository implements PanacheRepository<Song> {
    public Optional<Song> findBySongId(SongId songId) {
        return Optional.ofNullable(find("songId", songId).firstResult());
    }
}
