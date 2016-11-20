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

public class LäserSetupController {

	@FXML
	private CheckBox knrCheckbox;
	@FXML
	private CheckBox pnrCheckbox;
	
	@FXML
	private TextField knrTextfield;
	private int knrIndex = 0;
	private int defaultKnrIndex = 0;
	@FXML
	private TextField pnrTextfield;
	private int pnrIndex = 1;
	private int defaultPnrIndex = 1;
	
	@FXML
	private TextField tablenameTextfield;
	
	@FXML
	private Label exempelLabel;

	private HashMap<Integer, String> studyingIndexes = new HashMap<Integer, String>();
	
	private int numberOfColumnsUnwanted = 0;
	
	private Stage dialogStage;
	private boolean okClicked = false;
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
		studyingIndexes.put(knrIndex, "knr"); // DONT CHANGE NAMES
		studyingIndexes.put(pnrIndex, "pnr");
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
    	return this.studyingIndexes;
    } 
    
    public String getTableName() {
    	String tableName = this.tablenameTextfield.getText();
    	return (tableName.length() > 0) ? tableName : "Läser";
    }
    
	@FXML
	private void handleSpara() {
		if (isInputValid()) {
			studyingIndexes = new HashMap<Integer, String>();
			studyingIndexes.put(knrIndex, "knr"); // DONT CHANGE NAMES
			studyingIndexes.put(pnrIndex, "pnr");
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
    		
    		studyingIndexes = new HashMap<Integer, String>();
    		studyingIndexes.put(knrIndex, "knr"); // DONT CHANGE NAMES
    		studyingIndexes.put(pnrIndex, "pnr");
    	}

    	String colNamesString = "";
    	for (int i = 0; i < studyingIndexes.size() - numberOfColumnsUnwanted; i++) {
    		colNamesString += " | " + studyingIndexes.get(i);
    	}
    	colNamesString += " | ";
    	exempelLabel.setText(colNamesString);
    }
    
    private boolean isInputValid() {

    	if ((isNumber(knrTextfield.getText()) != -1 || knrCheckbox.isSelected()) &&
    			(isNumber(pnrTextfield.getText()) != -1 || pnrCheckbox.isSelected())) {
    		
    		knrIndex = (knrCheckbox.isSelected()) ? -1 
    				: (knrTextfield.getText().length() == 0) ? defaultKnrIndex
    				: Integer.parseInt(knrTextfield.getText());
    		pnrIndex = (pnrCheckbox.isSelected()) ? -2 
    				: (pnrTextfield.getText().length() == 0) ? defaultPnrIndex
    				: Integer.parseInt(pnrTextfield.getText());
    		
    		numberOfColumnsUnwanted = 0;
    		if (knrCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		if (pnrCheckbox.isSelected()) numberOfColumnsUnwanted++;
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private int isNumber(String toBeParsed) {

    	if (toBeParsed.length() == 0) {
    		return -2;
    	}
    	
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