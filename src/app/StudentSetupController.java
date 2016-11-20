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
	private CheckBox pnrCheckbox;
	@FXML
	private CheckBox namnCheckbox;
	@FXML
	private CheckBox adressCheckbox;
	@FXML
	private CheckBox tfnnummerCheckbox;
	
	@FXML
	private TextField pnrTextfield;
	private int pnrIndex = 0;
	private int defaultPnrIndex = 0;
	@FXML
	private TextField namnTextfield;
	private int namnIndex = 1;
	private int defaultNamnIndex = 1;
	@FXML
	private TextField adressTextfield;
	private int adressIndex = 2;
	private int defaultAdressIndex = 2;
	@FXML
	private TextField tfnnummerTextfield;
	private int tfnnummerIndex = 3;
	private int defaultTfnnummerIndex = 3;
	
	@FXML
	private TextField tablenameTextfield;
	
	@FXML
	private Label exempelLabel;

	@FXML
	private CheckBox pnrIs12Digits;
	
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
		studentIndexes.put(pnrIndex, "pnr"); // DONT CHANGE NAMES
		studentIndexes.put(namnIndex, "namn");
		studentIndexes.put(adressIndex, "adress");
		studentIndexes.put(tfnnummerIndex, "tfnnr");
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
    	String tableName = this.tablenameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Student";
    }
    
    @FXML
    private void handleSpara() {
    	
    	if (isInputValid()) {
    		
    		studentIndexes = new HashMap<Integer, String>();
    		
    		studentIndexes.put(pnrIndex, "pnr"); // DONT CHANGE NAMES
    		studentIndexes.put(namnIndex, "namn");
    		studentIndexes.put(adressIndex, "adress");
    		studentIndexes.put(tfnnummerIndex, "tfnnr");
    		studentIndexes.put(-1337, pnrIs12Digits.isSelected() + "");
    		
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
    		
    		studentIndexes = new HashMap<Integer, String>();
    		studentIndexes.put(pnrIndex, "pnr"); // DONT CHANGE NAMES
    		studentIndexes.put(namnIndex, "namn");
    		studentIndexes.put(adressIndex, "adress");
    		studentIndexes.put(tfnnummerIndex, "tfnnr");
    		studentIndexes.put(-1337, pnrIs12Digits.isSelected() + "");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < studentIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + studentIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exempelLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(pnrTextfield.getText()) != -1 || pnrCheckbox.isSelected()) &&
    			(isNumber(namnTextfield.getText()) != -1 || namnCheckbox.isSelected()) &&
    			(isNumber(adressTextfield.getText()) != -1 || adressCheckbox.isSelected()) && 
    			(isNumber(tfnnummerTextfield.getText()) != -1 || tfnnummerCheckbox.isSelected())) {
    		
    		
    		pnrIndex = (pnrCheckbox.isSelected()) ? -1 
    				: (pnrTextfield.getText().length() == 0) ? defaultPnrIndex 
    				: Integer.parseInt(pnrTextfield.getText());
    		namnIndex = (namnCheckbox.isSelected()) ? -2 
    				: (namnTextfield.getText().length() == 0) ? defaultNamnIndex
    				: Integer.parseInt(namnTextfield.getText());
    		adressIndex = (adressCheckbox.isSelected()) ? -3 
    				: (adressTextfield.getText().length() == 0) ? defaultAdressIndex
    				: Integer.parseInt(adressTextfield.getText());
    		tfnnummerIndex = (tfnnummerCheckbox.isSelected()) ? -4 
    				: (tfnnummerTextfield.getText().length() == 0) ? defaultTfnnummerIndex
    				: Integer.parseInt(tfnnummerTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (pnrCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (namnCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (adressCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (tfnnummerCheckbox.isSelected()) numberOfColumnsUnwanted++;
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
