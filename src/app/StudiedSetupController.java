package app;

import java.util.HashMap;

import app.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StudiedSetupController {

	@FXML
	private CheckBox courseCodeCheckbox;
	@FXML
	private CheckBox studentIdCheckbox;
	@FXML
	private CheckBox gradeCheckbox;
	
	@FXML
	private TextField courseCodeTextfield;
	private int courseCodeColumnIndex = 0;
	private int defaultCourseCodeColumnIndex = 0;
	@FXML
	private TextField studentIdTextfield;
	private int studentIdColumnIndex = 1;
	private int defaultStudentIdColumnIndex = 1;
	@FXML
	private TextField gradeTextfield;
	private int gradeColumnIndex = 2;
	private int defaultGradeColumnIndex = 2;
	
	@FXML
	private TextField tableNameTextfield;
	
	@FXML
	private Label exampleLabel;

	private HashMap<Integer, String> studiedIndexes = new HashMap<Integer, String>();
	
	private int numberOfColumnsUnwanted = 0;
	
	private Stage dialogStage;
	private boolean okClicked = false;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		studiedIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
		studiedIndexes.put(studentIdColumnIndex, "pnr");
		studiedIndexes.put(gradeColumnIndex, "betyg");
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
    	return okClicked;
    }
    
    public HashMap<Integer, String> getStudiedIndexes() {
    	return this.studiedIndexes;
    } 
    
    public String getTableName() {
    	String tableName = this.tableNameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Läst";
    }
    
	@FXML
	private void handleSave() {
		if (isInputValid()) {
			studiedIndexes = new HashMap<Integer, String>();
			studiedIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
			studiedIndexes.put(studentIdColumnIndex, "pnr");
			studiedIndexes.put(gradeColumnIndex, "betyg");
			okClicked = true;
			dialogStage.close();
		}
	}
    
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    @FXML
    private void handleUpdateExample() {
    	exampleLabel.setText("");
    	
    	if (isInputValid()) {
    		
    		studiedIndexes = new HashMap<Integer, String>();
    		studiedIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
    		studiedIndexes.put(studentIdColumnIndex, "pnr");
    		studiedIndexes.put(gradeColumnIndex, "betyg");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < studiedIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + studiedIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exampleLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(courseCodeTextfield.getText()) != -1 || courseCodeCheckbox.isSelected()) &&
    			(isNumber(studentIdTextfield.getText()) != -1 || studentIdCheckbox.isSelected()) &&
    			(isNumber(gradeTextfield.getText()) != -1 || gradeCheckbox.isSelected())) {
    		
    		courseCodeColumnIndex = (courseCodeCheckbox.isSelected()) ? -1
    				: (courseCodeTextfield.getText().length() == 0) ? defaultCourseCodeColumnIndex
    				: Integer.parseInt(courseCodeTextfield.getText());
    		studentIdColumnIndex = (studentIdCheckbox.isSelected()) ? -2
    				: (studentIdTextfield.getText().length() == 0) ? defaultStudentIdColumnIndex
    				: Integer.parseInt(studentIdTextfield.getText());
    		gradeColumnIndex = (gradeCheckbox.isSelected()) ? -3
    				: (gradeTextfield.getText().length() == 0) ? defaultGradeColumnIndex
    				: Integer.parseInt(gradeTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (courseCodeCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (studentIdCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (gradeCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private int isNumber(String toBeParsed) {

		if (toBeParsed.length() == 0)
			return -2;

		if (Utils.isNumber(toBeParsed) == false) {
			this.showAlert("Parsing fel, kolla siffrorna");
			return -1;
		}
		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
			this.showAlert("Värde > Integer.MAX_VALUE");
			return -1;
		}

		return Integer.parseInt(toBeParsed);
	}
    
    private void showAlert(String displayText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(dialogStage);
		alert.setTitle("Number error");
		alert.setHeaderText("Number error");
		alert.setContentText(displayText);

		alert.showAndWait();
	}
    
    
    
    
    
    
    
    
}