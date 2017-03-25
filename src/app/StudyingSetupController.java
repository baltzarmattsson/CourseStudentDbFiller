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

public class StudyingSetupController {

	@FXML
	private CheckBox courseCodeCheckbox;
	@FXML
	private CheckBox studentIdCheckbox;
	
	@FXML
	private TextField courseCodeTextbox;
	private int courseCodeColumnIndex = 0;
	private int defaultCourseCodeColumnIndex = 0;
	@FXML
	private TextField studentIdTextbox;
	private int studentIdColumnIndex = 1;
	private int defaultStudentIdColumnIndex = 1;
	
	@FXML
	private TextField tableNameTextfield;
	
	@FXML
	private Label exampleLabel;

	private HashMap<Integer, String> studyingIndexes = new HashMap<Integer, String>();
	
	private int numberOfColumnsUnwanted = 0;
	
	private Stage dialogStage;
	private boolean okClicked = false;

    @FXML
    private void initialize() {
		studyingIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
		studyingIndexes.put(studentIdColumnIndex, "pnr");
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
    	return okClicked;
    }
    
    public HashMap<Integer, String> getStudiedIndexes() {
    	return this.studyingIndexes;
    } 
    
    public String getTableName() {
    	String tableName = this.tableNameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Läser";
    }
    
	@FXML
	private void handleSave() {
		if (isInputValid()) {
			studyingIndexes = new HashMap<Integer, String>();
			studyingIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
			studyingIndexes.put(studentIdColumnIndex, "pnr");
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
    		
    		studyingIndexes = new HashMap<Integer, String>();
    		studyingIndexes.put(courseCodeColumnIndex, "knr"); // DONT CHANGE NAMES
    		studyingIndexes.put(studentIdColumnIndex, "pnr");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < studyingIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + studyingIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exampleLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((Utils.isNumber(courseCodeTextbox.getText(), this.dialogStage) != -1 || courseCodeCheckbox.isSelected()) &&
    			(Utils.isNumber(studentIdTextbox.getText(), this.dialogStage) != -1 || studentIdCheckbox.isSelected())) {
    		
    		courseCodeColumnIndex = (courseCodeCheckbox.isSelected()) ? -1
    				: (courseCodeTextbox.getText().length() == 0) ? defaultCourseCodeColumnIndex
    				: Integer.parseInt(courseCodeTextbox.getText());
    		studentIdColumnIndex = (studentIdCheckbox.isSelected()) ? -2
    				: (studentIdTextbox.getText().length() == 0) ? defaultStudentIdColumnIndex
    				: Integer.parseInt(studentIdTextbox.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (courseCodeCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (studentIdCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		return true;
    	} else {
    		return false;
    	}
    }
    
//    private int isNumber(String toBeParsed) {
//
//    	if (toBeParsed.length() == 0) {
//    		return -2;
//    	}
//
//		if (Utils.isNumber(toBeParsed) == false) {
//			this.showAlert("Parsing error, check the numbers");
//			return -1;
//		}
//		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
//			this.showAlert("Value > Integer.MAX_VALUE");
//			return -1;
//		}
//
//		return Integer.parseInt(toBeParsed);
//	}
//
//    private void showAlert(String displayText) {
//		Alert alert = new Alert(AlertType.WARNING);
//		alert.initOwner(dialogStage);
//		alert.setTitle("Number error");
//		alert.setHeaderText("Number error");
//		alert.setContentText(displayText);
//
//		alert.showAndWait();
//	}
        
}