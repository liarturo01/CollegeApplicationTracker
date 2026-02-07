package finalproject.collegeapplicationtracker;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a person providing a recommendation.
 * Implements Serializable to allow file storage.
 */
public class Recommender implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private LocalDate dateRequested;
    private LocalDate dateRequired;

    /**
     * Constructor for Recommender.
     * @param name Name of the recommender.
     * @param email Email address.
     * @param dateRequested When the recommendation was asked for.
     * @param dateRequired When the recommendation is due.
     */
    public Recommender(String name, String email, LocalDate dateRequested, LocalDate dateRequired) {
        this.name = name;
        this.email = email;
        this.dateRequested = dateRequested;
        this.dateRequired = dateRequired;
    }

    @Override
    public String toString() {
        return String.format("\t- %s (%s) | Req: %s | Due: %s",
                name, email, dateRequested, dateRequired);
    }
}
