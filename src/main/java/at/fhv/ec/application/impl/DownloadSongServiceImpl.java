package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.DownloadSongService;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.user.User;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ec.infrastructure.HibernateUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.NoSuchElementException;

@ApplicationScoped
public class DownloadSongServiceImpl implements DownloadSongService {
    @Inject
    HibernateUserRepository userRepository;

    @Inject
    HibernateSongRepository songRepository;

    @Override
    public byte[] downloadSong(String username, String albumName, String songName) throws NoSuchElementException {
        Song song = songRepository.findByTitleAndAlbum(songName, albumName).orElseThrow(NoSuchElementException::new);

        // Check if song was bought by user
        User user = userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        if(!song.getUsers().contains(user.getUserId())) {
            throw new UnsupportedOperationException();
        }

        // Get example mp3 from fileservice
        Client client = ClientBuilder.newClient();

        return client
                .target("http://filesystem-microservice:8080/fileservice-api/v1/files")
                .path("exampleFile")
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .get(byte[].class);
    }
}
