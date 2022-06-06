package at.fhv.ec.application.api;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface DownloadSongService {
    byte[] downloadSong(String username, String albumName, String songName) throws NoSuchElementException;
}
