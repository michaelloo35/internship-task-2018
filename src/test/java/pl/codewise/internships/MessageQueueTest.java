package pl.codewise.internships;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageQueueTest {

    private static final int EXPIRATION_TIME_SECONDS = 2;
    private MessageQueue messageQue;

    @Before
    public void setUp() throws Exception {
        messageQue = new MessageQueueCache(new MessageCache(EXPIRATION_TIME_SECONDS, 2000));
    }

    @Test
    public void AllShouldExpire() throws InterruptedException {

        // given
        for (int i = 0; i < 1000; i++) {
            messageQue.add(new Message("userAgent" + i, (i % 412) + 100));
        }

        // when
        //
        Thread.sleep(3000);

        // then
        assertEquals(0, messageQue.numberOfErrorMessages());
        assertEquals(true, messageQue.snapshot().getSnapshot().isEmpty());
    }

    @Test
    public void shouldCountErrorCodes() {

        // given
        for (int i = 300; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));

        // when

        // then
        assertEquals(50, messageQue.numberOfErrorMessages());

    }

    @Test
    public void shouldReturn100Hottest() {

        // given
        for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));

        // when

        // then
        assertEquals(100, messageQue.snapshot().getSnapshot().size());

    }

    @Test
    public void shouldCountErrorCodesConcurrentMessageInsertion() {

        // given
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));
        });


        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));
        });

        // when

        thread1.run();
        thread2.run();

        // then

        assertEquals(2 * 50, messageQue.numberOfErrorMessages());
    }


    @Test
    public void shouldReturn100HottestConcurrentMessageInsertion() {

        // given

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 450; i++) messageQue.add(new Message("userAgent" + i, i));
        });

        // when
        thread1.run();
        thread2.run();
        thread3.run();

        // then
        assertEquals(100, messageQue.snapshot().getSnapshot().size());

    }

}