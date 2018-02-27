package pl.codewise.internships;


public class MessageQueueCache implements MessageQueue {
    private static final int NUMBER_OF_MESSAGES = 100;
    private final MessageCache cache;

    public MessageQueueCache(MessageCache cache) {
        this.cache = cache;
    }

    @Override
    public void add(Message message) {
        cache.put(message);
    }

    @Override
    public Snapshot snapshot() {
        return new Snapshot(cache.viewHottestMessages(NUMBER_OF_MESSAGES));
    }

    @Override
    public long numberOfErrorMessages() {
        return cache.viewAll().stream()
                .filter(message -> isBetween(400, 500, message.getErrorCode()))
                .count();
    }

    /**
     * min inclusive, max exclusive
     *
     * @return true if value is between min and max, false if is not
     */
    private boolean isBetween(int min, int max, int value) {
        return value >= min && value < max;
    }
}
