package pl.codewise.internships;

import java.util.UUID;

// TODO factory

/**
 * Generates random UUID upon creation
 */
public class UniqueID {
    private final UUID id;

    public UniqueID() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
