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

public class CourseSetupController {

	@FXML
	private CheckBox codeCheckbox;
	@FXML
	private CheckBox nameCheckbox;
	@FXML
	private CheckBox pointsCheckbox;
	
	@FXML
	private TextField codeTextfield;
	private int codeColumnIndex = 0;
	private int defaultCodeColumnIndex = 0;
	@FXML
	private TextField nameTextfield;
	private int nameColumnIndex = 1;
	private int defaultNameColumnIndex = 1;
	@FXML
	private TextField pointsTextfield;
	private int pointsColumnIndex = 2;
	private int deafultPointsColumnIndex = 2;
	
	@FXML
	private TextField tableNameTextfield;
	
	@FXML
	private Label exampleLabel;
	
	private HashMap<Integer, String> courseIndexes = new HashMap<Integer, String>();
	
	private int numberOfColumnsUnwanted = 0;
	
	private Stage dialogStage;
	private boolean okClicked = false;
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		courseIndexes.put(codeColumnIndex, "knr"); // DONT CHANGE NAMES
		courseIndexes.put(nameColumnIndex, "namn");
		courseIndexes.put(pointsColumnIndex, "poäng");
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
    
    public HashMap<Integer, String> getCourseIndexes() {
    	return this.courseIndexes;
    }
    
    public String getTableName() {
    	String tableName = this.tableNameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Kurs";
    }
    
    @FXML
    private void handleSave() {
    	
    	if (isInputValid()) {
    		
    		courseIndexes = new HashMap<Integer, String>();
    		
    		courseIndexes.put(codeColumnIndex, "knr"); // DONT CHANGE NAMES
    		courseIndexes.put(nameColumnIndex, "namn");
    		courseIndexes.put(pointsColumnIndex, "poäng");
    		
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
    		
    		courseIndexes = new HashMap<Integer, String>();
    		courseIndexes.put(codeColumnIndex, "knr"); // DONT CHANGE NAMES
    		courseIndexes.put(nameColumnIndex, "namn");
    		courseIndexes.put(pointsColumnIndex, "poäng");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < courseIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + courseIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exampleLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(codeTextfield.getText()) != -1 || codeCheckbox.isSelected()) &&
    			(isNumber(nameTextfield.getText()) != -1 || nameCheckbox.isSelected()) &&
    			(isNumber(pointsTextfield.getText()) != -1 || pointsCheckbox.isSelected())) {
    		
    		codeColumnIndex = (codeCheckbox.isSelected()) ? -1
    				: (codeTextfield.getText().length() == 0) ? defaultCodeColumnIndex
    				: Integer.parseInt(codeTextfield.getText());
    		nameColumnIndex = (nameCheckbox.isSelected()) ? -2
    				: (nameTextfield.getText().length() == 0) ? defaultNameColumnIndex
    				: Integer.parseInt(nameTextfield.getText());
    		pointsColumnIndex = (pointsCheckbox.isSelected()) ? -3
    				: (pointsTextfield.getText().length() == 0) ? deafultPointsColumnIndex
    				: Integer.parseInt(pointsTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (codeCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (nameCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (pointsCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		return true;
    	} else {
    		return false;
    	}
    }
    
	private void showAlert(String displayText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(dialogStage);
		alert.setTitle("Number error");
		alert.setHeaderText("Number error");
		alert.setContentText(displayText);

		alert.showAndWait();
	}
	
	private int isNumber(String toBeParsed) {

		if (toBeParsed.length() == 0)
			return -2;

		if (Utils.isNumber(toBeParsed) == false) {
			this.showAlert("Parsing error, check numbers");
			return -1;
		}
		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
			this.showAlert("Value > Integer.MAX_VALUE");
			return -1;
		}

		return Integer.parseInt(toBeParsed);
	}

	
}