package finalproject.collegeapplicationtracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the list of applications and handles File I/O.
 * Reads and writes to a binary file named 'transfer_apps.dat'.
 */
public class ApplicationManager {
    private List<CollegeApplication> applications;
    private final String FILE_NAME = "transfer_apps.dat";

    public ApplicationManager() {
        this.applications = loadFromFile();
    }

    /**
     * Adds an application to the list and saves to file.
     * @param app The application to add.
     */
    public void addApplication(CollegeApplication app) {
        applications.add(app);
        saveToFile();
    }

    /**
     * Searches for applications containing the given name (case-insensitive).
     * @param name The college name (or partial name) to search for.
     * @return A formatted string of results.
     */
    public String searchByCollege(String name) {
        List<CollegeApplication> results = applications.stream()
                .filter(app -> app.getCollegeName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) return "No applications found matching: " + name;

        StringBuilder sb = new StringBuilder();
        for (CollegeApplication app : results) {
            sb.append(app.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Removes an application if the name matches.
     * @param name Name of the college to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteByCollege(String name) {
        // removeIf returns true if any elements were removed
        boolean removed = applications.removeIf(app ->
                app.getCollegeName().equalsIgnoreCase(name));

        if (removed) {
            saveToFile(); // Update the file immediately
        }
        return removed;
    }

    public String getAllApplicationsFormatted() {
        if (applications.isEmpty()) return "No applications tracked yet.";
        StringBuilder sb = new StringBuilder();
        for (CollegeApplication app : applications) {
            sb.append(app.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Saves the current list of applications to a binary file.
     */
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(applications);
        } catch (IOException e) {
            System.err.println("Could not save data: " + e.getMessage());
        }
    }

    /**
     * Loads the list of applications from the binary file.
     * @return A list of applications, or an empty list if file doesn't exist.
     */
    @SuppressWarnings("unchecked")
    private List<CollegeApplication> loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<CollegeApplication>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
