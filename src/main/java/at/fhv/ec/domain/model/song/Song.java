package at.fhv.ec.domain.model.song;

import at.fhv.ec.domain.model.user.UserId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Song {
    @EmbeddedId
    private SongId songId;
    private String albumName;
    private String title;

    @ElementCollection
    private List<UserId> users;

    public static Song create(SongId songId, String albumName, String title) {
        return new Song(songId, albumName, title);
    }

    protected Song(){}

    private Song(SongId songId, String albumName, String title) {
        this.songId = songId;
        this.albumName = albumName;
        this.title = title;
        this.users = new ArrayList<>();
    }

    public void addUserAsSongOwner(UserId userId) {
        if(users.contains(userId)) {
            throw new UnsupportedOperationException();
        }

        users.add(userId);
    }

    public SongId getSongId() {
        return songId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getTitle() {
        return title;
    }

    public List<UserId> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(albumName, song.albumName) && Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName, title);
    }

}
