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

public class StudentSetupController {

	@FXML
	private CheckBox studentIdCheckbox;
	@FXML
	private CheckBox nameCheckbox;
	@FXML
	private CheckBox addressCheckbox;
	@FXML
	private CheckBox phoneNbrCheckbox;
	
	@FXML
	private TextField studentIdTextfield;
	private int studentIdColumnIndex = 0;
	private int defaultStudentIdColumnIndex = 0;
	@FXML
	private TextField nameTextfield;
	private int nameColumnIndex = 1;
	private int defaultNameColumnIndex = 1;
	@FXML
	private TextField addressTextfield;
	private int addressColumnIndex = 2;
	private int defaultAddressColumnIndex = 2;
	@FXML
	private TextField phoneNbrTextfield;
	private int phoneNbrColumnIndex = 3;
	private int defaultPhoneNbrColumnIndex = 3;
	
	@FXML
	private TextField tableNameTextfield;
	
	@FXML
	private Label exampleLabel;

	@FXML
	private CheckBox studentIdIsTwelveDigits;
	
	private HashMap<Integer, String> studentIndexes = new HashMap<Integer, String>();
	
	private int numberOfColumnsUnwanted = 0;
	
	private Stage dialogStage;
	private boolean okClicked = false;
	
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		studentIndexes.put(studentIdColumnIndex, "pnr"); // DONT CHANGE NAMES
		studentIndexes.put(nameColumnIndex, "namn");
		studentIndexes.put(addressColumnIndex, "adress");
		studentIndexes.put(phoneNbrColumnIndex, "tfnnr");
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
    
    public HashMap<Integer, String> getStudentIndexes() {
    	return this.studentIndexes;
    }
    
    public String getTableName() {
    	String tableName = this.tableNameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Student";
    }
    
    @FXML
    private void handleSave() {
    	
    	if (isInputValid()) {
    		
    		studentIndexes = new HashMap<Integer, String>();
    		
    		studentIndexes.put(studentIdColumnIndex, "pnr"); // DONT CHANGE NAMES
    		studentIndexes.put(nameColumnIndex, "namn");
    		studentIndexes.put(addressColumnIndex, "adress");
    		studentIndexes.put(phoneNbrColumnIndex, "tfnnr");
    		studentIndexes.put(-1337, studentIdIsTwelveDigits.isSelected() + "");
    		
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
    		
    		studentIndexes = new HashMap<Integer, String>();
    		studentIndexes.put(studentIdColumnIndex, "pnr"); // DONT CHANGE NAMES
    		studentIndexes.put(nameColumnIndex, "namn");
    		studentIndexes.put(addressColumnIndex, "adress");
    		studentIndexes.put(phoneNbrColumnIndex, "tfnnr");
    		studentIndexes.put(-1337, studentIdIsTwelveDigits.isSelected() + "");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < studentIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + studentIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exampleLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(studentIdTextfield.getText()) != -1 || studentIdCheckbox.isSelected()) &&
    			(isNumber(nameTextfield.getText()) != -1 || nameCheckbox.isSelected()) &&
    			(isNumber(addressTextfield.getText()) != -1 || addressCheckbox.isSelected()) &&
    			(isNumber(phoneNbrTextfield.getText()) != -1 || phoneNbrCheckbox.isSelected())) {
    		
    		
    		studentIdColumnIndex = (studentIdCheckbox.isSelected()) ? -1
    				: (studentIdTextfield.getText().length() == 0) ? defaultStudentIdColumnIndex
    				: Integer.parseInt(studentIdTextfield.getText());
    		nameColumnIndex = (nameCheckbox.isSelected()) ? -2
    				: (nameTextfield.getText().length() == 0) ? defaultNameColumnIndex
    				: Integer.parseInt(nameTextfield.getText());
    		addressColumnIndex = (addressCheckbox.isSelected()) ? -3
    				: (addressTextfield.getText().length() == 0) ? defaultAddressColumnIndex
    				: Integer.parseInt(addressTextfield.getText());
    		phoneNbrColumnIndex = (phoneNbrCheckbox.isSelected()) ? -4
    				: (phoneNbrTextfield.getText().length() == 0) ? defaultPhoneNbrColumnIndex
    				: Integer.parseInt(phoneNbrTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (studentIdCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (nameCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (addressCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (phoneNbrCheckbox.isSelected()) numberOfColumnsUnwanted++;
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
			this.showAlert("Parsing error, check the numbers");
			return -1;
		}
		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
			this.showAlert("Value > Integer.MAX_VALUE");
			return -1;
		}

		return Integer.parseInt(toBeParsed);
	}
	
}
