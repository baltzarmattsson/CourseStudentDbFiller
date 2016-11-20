package app;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private AnchorPane rootLayout;
    
    private ViewController viewController;
    
    public MainApp() {
    }
    
    private static MainApp mainApp;
    
    public static MainApp getMainApp() {
    	return mainApp;
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DB Generator");

		initRootLayout();
	}

    /**
     * Initializes the root layout.
     */
	public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View.fxml"));
            rootLayout = (AnchorPane) loader.load();

            viewController = loader.getController();
            mainApp = this;

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}

	public HashMap<Integer, String> showSetupStudentDialog(ViewController viewController) {
		
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("StudentSetup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Student setup");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            StudentSetupController studsetcontroller = loader.getController();
            studsetcontroller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            viewController.studentTableName = studsetcontroller.getTableName();
            return studsetcontroller.getStudentIndexes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

	public HashMap<Integer, String> showSetupCourseDialog(ViewController viewController) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("KursSetup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Course setup");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            KursSetupController kurssetcontroller = loader.getController();
            kurssetcontroller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            viewController.courseTableName = kurssetcontroller.getTableName();
            return kurssetcontroller.getCourseIndexes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public HashMap<Integer, String> showSetupLästDialog(ViewController viewController) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("LästSetup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Läst setup");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LästSetupController lästcontroller = loader.getController();
            lästcontroller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            viewController.studiedTableName = lästcontroller.getTableName();
            return lästcontroller.getStudiedIndexes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public HashMap<Integer, String> showSetupLäserDialog(ViewController viewController) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("LäserSetup.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Läser setup");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LäserSetupController läsercontroller = loader.getController();
            läsercontroller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            viewController.studyingTableName = läsercontroller.getTableName();
            return läsercontroller.getStudiedIndexes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
}
