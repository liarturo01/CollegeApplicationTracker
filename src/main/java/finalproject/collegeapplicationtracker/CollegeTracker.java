package finalproject.collegeapplicationtracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main GUI class for the College Transfer Application Tracker.
 * Extends JavaFX Application to render the interface.
 * * @author Arturo Li
 * @version 1.0
 */
public class CollegeTracker extends Application {

    private ApplicationManager manager = new ApplicationManager();
    private TextArea displayArea;

    // GUI Controls
    private TextField txtName, txtAddress, txtCost, txtPlatform;
    private DatePicker dateApplied, dateExpected;
    private CheckBox chkEssay, chkTranscript;

    // Recommender Controls
    private TextField txtRecName, txtRecEmail;
    private DatePicker dateRecRequested, dateRecDue;


    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage the primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("College Transfer Application Tracker");

        // --- Layout Containers ---
        BorderPane mainLayout = new BorderPane();
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(15));

        // --- Form Fields ---
        // College Info
        formGrid.add(new Label("College Name:"), 0, 0);
        txtName = new TextField();
        formGrid.add(txtName, 1, 0);

        formGrid.add(new Label("Address:"), 0, 1);
        txtAddress = new TextField();
        formGrid.add(txtAddress, 1, 1);

        formGrid.add(new Label("Platform (e.g., CommonApp):"), 0, 2);
        txtPlatform = new TextField();
        formGrid.add(txtPlatform, 1, 2);

        formGrid.add(new Label("Application Cost ($):"), 0, 3);
        txtCost = new TextField();
        formGrid.add(txtCost, 1, 3);

        // Dates
        formGrid.add(new Label("Date Applied:"), 0, 4);
        dateApplied = new DatePicker();
        formGrid.add(dateApplied, 1, 4);

        formGrid.add(new Label("Expected Decision:"), 0, 5);
        dateExpected = new DatePicker();
        formGrid.add(dateExpected, 1, 5);

        // Status
        chkEssay = new CheckBox("Essay Written?");
        chkTranscript = new CheckBox("Transcripts Submitted?");
        HBox statusBox = new HBox(10, chkEssay, chkTranscript);
        formGrid.add(statusBox, 1, 6);

        // --- Recommender Section (Titled Pane) ---
        GridPane recGrid = new GridPane();
        recGrid.setHgap(10);
        recGrid.setVgap(10);

        recGrid.add(new Label("Recommender's Name:"), 0, 0);
        txtRecName = new TextField();
        recGrid.add(txtRecName, 1, 0);

        recGrid.add(new Label("Recommender's Email:"), 0, 1);
        txtRecEmail = new TextField();
        recGrid.add(txtRecEmail, 1, 1);

        recGrid.add(new Label("Date Requested:"), 0, 2);
        dateRecRequested = new DatePicker();
        recGrid.add(dateRecRequested, 1, 2);

        recGrid.add(new Label("Date Due:"), 0, 3);
        dateRecDue = new DatePicker();
        recGrid.add(dateRecDue, 1, 3);

        TitledPane recPane = new TitledPane("Recommender Info", recGrid);
        recPane.setCollapsible(false);
        formGrid.add(recPane, 0, 7, 2, 1);

        // --- Action Buttons ---
        Button btnSave = new Button("Save Application");
        Button btnSearch = new Button("Search by College Name");
        Button btnDelete = new Button("Delete");
        Button btnShowAll = new Button("Show All");

        HBox buttonBox = new HBox(10, btnSave, btnSearch, btnDelete, btnShowAll);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        // --- Display Area ---
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(200);

        // --- Event Handling ---
        btnSave.setOnAction(e -> saveApplication());
        btnShowAll.setOnAction(e -> showAllApplications());
        btnDelete.setOnAction(e -> deleteApplication());
        btnSearch.setOnAction(e -> searchApplication());



        // --- Assembly ---
        mainLayout.setLeft(formGrid);
        mainLayout.setBottom(displayArea);
        mainLayout.setCenter(buttonBox); // Putting buttons in center to separate form and text area visually
        BorderPane.setMargin(buttonBox, new Insets(10));

        Scene scene = new Scene(mainLayout, 1000, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Gathers data from GUI fields, creates objects, and calls the manager to save.
     */
    private void saveApplication() {
        try {
            // 1. Create Recommender List
            List<Recommender> recList = new ArrayList<>();
            if (!txtRecName.getText().isEmpty()) {
                recList.add(new Recommender(
                        txtRecName.getText(),
                        txtRecEmail.getText(),
                        dateRecRequested.getValue(),
                        dateRecDue.getValue()
                ));
            }

            // 2. Create Application Object
            double cost = txtCost.getText().isEmpty() ? 0.0 : Double.parseDouble(txtCost.getText());

            CollegeApplication app = new CollegeApplication(
                    txtName.getText(),
                    txtAddress.getText(),
                    dateApplied.getValue(),
                    cost,
                    txtPlatform.getText(),
                    recList,
                    dateExpected.getValue(),
                    chkEssay.isSelected(),
                    chkTranscript.isSelected()
            );

            // 3. Save
            manager.addApplication(app);
            displayArea.setText("Application for " + app.getCollegeName() + " saved successfully!");
            clearFields();

        } catch (NumberFormatException e) {
            displayArea.setText("Error: Cost must be a number.");
        } catch (Exception e) {
            displayArea.setText("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Prompts user for a college name and filters the list.
     */
    private void searchApplication() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search");
        dialog.setHeaderText("Search for an Application");
        dialog.setContentText("Enter College Name:");

        dialog.showAndWait().ifPresent(name -> {
            String result = manager.searchByCollege(name);
            displayArea.setText(result);
        });
    }
    /**
     * Prompts user for a college name and deletes it.
     */
    private void deleteApplication() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Application");
        dialog.setHeaderText("Delete an Application");
        dialog.setContentText("Enter the Exact College Name to Delete:");

        dialog.showAndWait().ifPresent(name -> {
            boolean success = manager.deleteByCollege(name);
            if (success) {
                displayArea.setText("Successfully deleted application for: " + name);
                showAllApplications(); // Refresh list to show it's gone
            } else {
                displayArea.setText("Could not find an application for: " + name);
            }
        });
    }
    /**
     * Displays all currently stored applications.
     */
    private void showAllApplications() {
        displayArea.setText(manager.getAllApplicationsFormatted());
    }

    /**
     * Clears the input fields for a new entry.
     */
    private void clearFields() {
        txtName.clear(); txtAddress.clear(); txtCost.clear(); txtPlatform.clear();
        txtRecName.clear(); txtRecEmail.clear();
        dateApplied.setValue(null); dateExpected.setValue(null);
        dateRecRequested.setValue(null); dateRecDue.setValue(null);
        chkEssay.setSelected(false); chkTranscript.setSelected(false);
    }
}
