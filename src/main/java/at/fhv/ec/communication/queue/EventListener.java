package at.fhv.ec.communication.queue;

import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;
import com.google.gson.Gson;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EventListener {
    // TODO: Implement EventListener
    private static final String PURCHASE_EVENT_QUEUE_NAME = "purchasedQueue";
    private static final Gson GSON = new Gson();

    @ConfigProperty(name = "redis.host")
    String redisHost;

    @ConfigProperty(name = "redis.port")
    int redisPort;

    @Scheduled(every = "5s")
    void receiveEvents() {
        JedisPool jedisPool = new JedisPool(redisHost, redisPort);

        try(Jedis jedis = jedisPool.getResource()) {
            List<String> events = jedis.brpop(0, PURCHASE_EVENT_QUEUE_NAME);

            for (String s : events) {
                if(!s.equalsIgnoreCase(PURCHASE_EVENT_QUEUE_NAME)) {
                    DigitalProductPurchasedDTO event = GSON.fromJson(s, DigitalProductPurchasedDTO.class);

                    // TODO: Process event

                    System.out.println(event);
                }
            }
        }
    }
}