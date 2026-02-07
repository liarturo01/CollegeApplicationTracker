package finalproject.collegeapplicationtracker;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a single college application transfer entry.
 * Stores all details regarding the specific application.
 */
public class CollegeApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String collegeName;
    private String address;
    private LocalDate applicationDate;
    private double cost;
    private String platform; // CommonApp, etc.
    private List<Recommender> recommenders;
    private LocalDate expectedDecisionDate;
    private boolean essayWritten;
    private boolean transcriptsSubmitted;

    /**
     * Full constructor for CollegeApplication.
     */
    public CollegeApplication(String collegeName, String address, LocalDate applicationDate,
                              double cost, String platform, List<Recommender> recommenders,
                              LocalDate expectedDecisionDate, boolean essayWritten,
                              boolean transcriptsSubmitted) {
        this.collegeName = collegeName;
        this.address = address;
        this.applicationDate = applicationDate;
        this.cost = cost;
        this.platform = platform;
        this.recommenders = recommenders;
        this.expectedDecisionDate = expectedDecisionDate;
        this.essayWritten = essayWritten;
        this.transcriptsSubmitted = transcriptsSubmitted;
    }

    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Formats the application data into a readable string.
     * @return Formatted string representation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("College: ").append(collegeName).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Platform: ").append(platform).append(" | Cost: $").append(cost).append("\n");
        sb.append("Applied On: ").append(applicationDate).append("\n");
        sb.append("Expected Decision: ").append(expectedDecisionDate).append("\n");
        sb.append("Status: [Essay: ").append(essayWritten ? "Done" : "None")
                .append("] [Transcripts: ").append(transcriptsSubmitted ? "Sent" : "None").append("]\n");

        sb.append("RECOMMENDERS:\n");
        if (recommenders == null || recommenders.isEmpty()) {
            sb.append("\tNone listed.\n");
        } else {
            for (Recommender r : recommenders) {
                sb.append(r.toString()).append("\n");
            }
        }
        sb.append("====================================\n");
        return sb.toString();
    }
}
