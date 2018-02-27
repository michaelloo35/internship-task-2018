package pl.codewise.internships;

import java.util.List;

import static java.util.Collections.emptyList;

public class Snapshot {

    private final List<Message> snapshot;

    public Snapshot(List<Message> snapshot) {
        this.snapshot = snapshot;
    }

    public List<Message> getSnapshot() {

        if (snapshot == null)
            return emptyList();

        return snapshot;
    }
}
