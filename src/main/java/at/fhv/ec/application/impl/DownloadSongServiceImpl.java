package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.DownloadSongService;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.domain.model.user.User;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ec.infrastructure.HibernateUserRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class DownloadSongServiceImpl implements DownloadSongService {
    @Inject
    Logger logger;

    @Inject
    HibernateUserRepository userRepository;

    @Inject
    HibernateSongRepository songRepository;

    private static final String FILEPATH = "/files/mp3/example.mp3";

    @Override
    public byte[] downloadSong(String username, UUID songId) throws NoSuchElementException, IOException {
        Song song = songRepository.findBySongId(new SongId(songId)).orElseThrow(NoSuchElementException::new);

        // Check if song was bought by user
        User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        if(!song.getUsers().contains(user.getUserId())) {
            throw new UnsupportedOperationException();
        }


        // Generate byte array with song file
        File songFile = new File(FILEPATH);

        if(!songFile.exists()) {
            logger.info("File not found");
            throw new NoSuchElementException();
        }

        return Files.readAllBytes(songFile.toPath());
    }
}
