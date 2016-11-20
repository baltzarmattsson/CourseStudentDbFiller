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

public class KursSetupController {

	@FXML
	private CheckBox kodCheckbox;
	@FXML
	private CheckBox namnCheckbox;
	@FXML
	private CheckBox poängCheckbox;
	
	@FXML
	private TextField kodTextfield;
	private int kodIndex = 0;
	private int defaultkodIndex = 0;
	@FXML
	private TextField namnTextfield;
	private int namnIndex = 1;
	private int defaultNamnIndex = 1;
	@FXML
	private TextField poängTextfield;
	private int poängIndex = 2;
	private int defaultPoängIndex = 2;
	
	@FXML
	private TextField tablenameTextfield;
	
	@FXML
	private Label exempelLabel;
	
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
		courseIndexes.put(kodIndex, "knr"); // DONT CHANGE NAMES
		courseIndexes.put(namnIndex, "namn");
		courseIndexes.put(poängIndex, "poäng");
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
    	String tableName = this.tablenameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Kurs";
    }
    
    @FXML
    private void handleSpara() {
    	
    	if (isInputValid()) {
    		
    		courseIndexes = new HashMap<Integer, String>();
    		
    		courseIndexes.put(kodIndex, "knr"); // DONT CHANGE NAMES
    		courseIndexes.put(namnIndex, "namn");
    		courseIndexes.put(poängIndex, "poäng");
    		
    		okClicked = true;
    		dialogStage.close();
    		
    	}
    }
    
    @FXML
    private void handleAvbryt() {
    	dialogStage.close();
    }
    
    @FXML
    private void handleUppdateraExempel() {
    	exempelLabel.setText("");
    	
    	if (isInputValid()) {
    		
    		courseIndexes = new HashMap<Integer, String>();
    		courseIndexes.put(kodIndex, "knr"); // DONT CHANGE NAMES
    		courseIndexes.put(namnIndex, "namn");
    		courseIndexes.put(poängIndex, "poäng");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < courseIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + courseIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exempelLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(kodTextfield.getText()) != -1 || kodCheckbox.isSelected()) &&
    			(isNumber(namnTextfield.getText()) != -1 || namnCheckbox.isSelected()) &&
    			(isNumber(poängTextfield.getText()) != -1 || poängCheckbox.isSelected())) {
    		
    		kodIndex = (kodCheckbox.isSelected()) ? -1 
    				: (kodTextfield.getText().length() == 0) ? defaultkodIndex
    				: Integer.parseInt(kodTextfield.getText());
    		namnIndex = (namnCheckbox.isSelected()) ? -2 
    				: (namnTextfield.getText().length() == 0) ? defaultNamnIndex
    				: Integer.parseInt(namnTextfield.getText());
    		poängIndex = (poängCheckbox.isSelected()) ? -3 
    				: (poängTextfield.getText().length() == 0) ? defaultPoängIndex
    				: Integer.parseInt(poängTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (kodCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (namnCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (poängCheckbox.isSelected()) numberOfColumnsUnwanted++;
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
			this.showAlert("Parsing fel, kolla siffrorna");
			return -1;
		}
		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
			this.showAlert("Värde > Integer.MAX_VALUE");
			return -1;
		}

		return Integer.parseInt(toBeParsed);
	}

	
}