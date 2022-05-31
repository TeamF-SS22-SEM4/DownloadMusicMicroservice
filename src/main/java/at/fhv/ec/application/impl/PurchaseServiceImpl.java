package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.domain.model.user.User;
import at.fhv.ec.domain.model.user.UserId;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ec.infrastructure.HibernateUserRepository;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import at.fhv.ss22.ea.f.communication.dto.SongDTO;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class PurchaseServiceImpl implements PurchaseService {
    @Inject
    Logger logger;

    @Inject
    HibernateSongRepository songRepository;

    @Inject
    HibernateUserRepository userRepository;


    @Override
    public void receivePurchase(DigitalProductPurchasedDTO event) {
        logger.info("Received new event " + event);
        // Check if user exists
        Optional<User> userOpt = userRepository.findByUsername(event.getUsername());
        User user;

        if(userOpt.isEmpty()) {
            logger.info("Could not find user " + event.getUsername() + "\nCreating user...");
            user = User.create(new UserId(UUID.randomUUID()), event.getUsername());
            userRepository.persist(user);
        } else {
            logger.info("Found user " + event.getUsername());
            user = userOpt.get();
        }

        // Check if songs already existing
        for (SongDTO song : event.getPurchasedSongs()) {
            Optional<Song> songOpt = songRepository.findByTitleAndAlbum(song.getTitle(), event.getAlbumName());
            Song currentSong;

            if(songOpt.isEmpty()) {
                logger.info("Could not find song " + song.getTitle() + "\nCreating song....");
                currentSong = Song.create(new SongId(UUID.randomUUID()), event.getAlbumName(), song.getTitle());
                songRepository.persist(currentSong);
            } else {
                currentSong = songOpt.get();
            }

            // Add user to song
            try {
                currentSong.addUserAsSongOwner(user.getUserId());
            } catch (UnsupportedOperationException e) {
                logger.info("Error at adding user to song\n" + e.getMessage());
            }
        }
    }
}
