package pl.codewise.internships;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MessageCache {

    private Cache<UniqueID, Message> cache;


    public MessageCache(long expirationTimeSeconds, long maximumSize) {
        cache = Caffeine
                .newBuilder()
                .expireAfterWrite(expirationTimeSeconds, TimeUnit.SECONDS)
                .maximumSize(maximumSize)
                .build();
    }

    public void put(Message msg) {
        cache.put(new UniqueID(), msg);
    }

    /**
     * @param count number of messages to return
     * @return null if cache is empty
     */
    public List<Message> viewHottestMessages(final int count) {

        AtomicReference<Map<UniqueID, Message>> result = null;
        cache.policy().eviction().ifPresent(eviction -> result.set(eviction.hottest(count)));
        if (result != null)
            return new ArrayList<Message>(result.get().values());
        return null;
    }

    public List<Message> viewAll() {
        return new ArrayList<>(cache.asMap().values());
    }
}
