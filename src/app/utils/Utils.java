package app.utils;

import app.MainApp;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Utils {

	private static String saveDir;

	public static int isNumber(String toBeParsed, Stage callerDialogStage) {

		if (toBeParsed.length() == 0) {
			return -2;
		}

		// Checking string for only digits
		char[] ca = toBeParsed.toCharArray();
		for (char c : ca) {
			if (!Character.isDigit(c)) {
				Utils.showAlert("Parsing error, check the numbers", callerDialogStage);
				return -1;
			}
		}

		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
			Utils.showAlert("Value > Integer.MAX_VALUE", callerDialogStage);
			return -1;
		}

		return Integer.parseInt(toBeParsed);
	}

	private static void showAlert(String displayText, Stage dialogStage) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.initOwner(dialogStage);
		alert.setTitle("Number error");
		alert.setHeaderText("Number error");
		alert.setContentText(displayText);

		alert.showAndWait();
	}

	public static boolean setSaveDir() {
		if (Utils.saveDir != null && Utils.saveDir.length() > 0)
			return true; // then dir is already set
		// this.saveDir = dir;
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose Save directory");

		try {
			File selectedDirectory = chooser.showDialog(MainApp.getMainApp().getPrimaryStage());
			if (selectedDirectory != null) {
				// return selectedDirectory.getAbsolutePath();
				Utils.saveDir = selectedDirectory.getAbsolutePath();
				System.out.println(Utils.saveDir);
				return true;
			} else {
				return false;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("fel");
			return false;
		}
	}

	public static String getSaveDir() {
		return Utils.saveDir;
	}

}
