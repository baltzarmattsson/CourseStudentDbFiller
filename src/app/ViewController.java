package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JMenuItem;

import org.controlsfx.control.CheckComboBox;

import app.utils.Utils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Alert.AlertType;

public class ViewController {

	public String studentTableName = "Student";
	public String courseTableName = "Kurs";
	public String studyingTableName = "Läser";
	public String studiedTableName = "Läst";

	/*** Column indexes ***/

	public HashMap<Integer, String> studentIndexes;
	public HashMap<Integer, String> courseIndexes;
	public HashMap<Integer, String> studyingIndexes;
	public HashMap<Integer, String> studiedIndexes;

	/*** ***/

	@FXML
	private CheckComboBox<String> studentComboBox;
	@FXML
	private CheckComboBox<String> kursComboBox;
	@FXML
	private CheckComboBox<String> läserComboBox;
	@FXML
	private CheckComboBox<String> lästComboBox;

	@FXML
	private TextField studLäserFrån;
	private int studLäserFrånNummer;
	@FXML
	private TextField studLäserTill;
	private int studLäserTillNummer;

	@FXML
	private TextField studLästFrån;
	private int studLästFrånNummer;
	@FXML
	private TextField studLästTill;
	private int studLästTillNummer;

	@FXML
	private TextField numberOfStudentsWanted;
	private int numberOfStudentsWantedNummer;

	@FXML
	private TextField numberOfCoursesWanted;
	private int numberOfCoursesWantedNummer;

	@FXML
	private TextField läserDbNamnTextField;
	@FXML
	private TextField lästsAvDbNamnTextField;

	@FXML
	private TextArea codeTextArea;

	@FXML
	private CheckBox studentToFileChkBox;
	@FXML
	private CheckBox courseToFileChkBox;
	@FXML
	private CheckBox studiedToFileChkBox;
	@FXML
	private CheckBox studyingToFileChkBox;
//	private String saveDir = "";

	private ArrayList<String> firstNames = new ArrayList<String>();
	private ArrayList<String> lastNames = new ArrayList<String>();
	private ArrayList<String> addresses = new ArrayList<String>();
	private ArrayList<String> areaCodes = new ArrayList<String>();

	private ArrayList<String> courseNames = new ArrayList<String>();
	
	private HashMap<String, Integer> coursePoints = new HashMap<String, Integer>();
	private int maximumPointsReadPerSemester = 45;
	private boolean studentIdIs12Digits;
	
	private Set<String> studentIDs = new HashSet<String>();

	public ViewController() {
	}

	@FXML
	private void initialize() {

		ObservableList<String> studentColumns = FXCollections.observableArrayList();
		studentColumns.add(0, "pnr");
		studentColumns.add(1, "namn");
		studentColumns.add(2, "adress");
		studentColumns.add(3, "tfnnr");
		this.setComboBoxValues(studentComboBox, studentColumns);

		ObservableList<String> courseColumns = FXCollections.observableArrayList();
		courseColumns.add(0, "knr");
		courseColumns.add(1, "namn");
		courseColumns.add(2, "poäng");
		this.setComboBoxValues(kursComboBox, courseColumns);

		ObservableList<String> studyingColumns = FXCollections.observableArrayList();
		studyingColumns.add(0, "knr");
		studyingColumns.add(1, "pnr");
		this.setComboBoxValues(läserComboBox, studyingColumns);

		ObservableList<String> studiedColumns = FXCollections.observableArrayList();
		studiedColumns.add(0, "knr");
		studiedColumns.add(1, "pnr");
		studiedColumns.add(2, "betyg");
		this.setComboBoxValues(lästComboBox, studiedColumns);

		/*** Setting column indexes ***/
		// Student
		studentIndexes = new HashMap<Integer, String>();
		studentIndexes.put(0, "pnr"); // DONT CHANGE NAMES
		studentIndexes.put(1, "namn");
		studentIndexes.put(2, "adress");
		studentIndexes.put(3, "tfnnr");

		// Kurs
		courseIndexes = new HashMap<Integer, String>();
		courseIndexes.put(0, "knr"); // DONT CHANGE NAMES
		courseIndexes.put(1, "namn");
		courseIndexes.put(2, "poäng");

		// Läser
		studyingIndexes = new HashMap<Integer, String>();
		studyingIndexes.put(0, "knr"); // DONT CHANGE NAMES
		studyingIndexes.put(1, "pnr");

		// Läst
		studiedIndexes = new HashMap<Integer, String>();
		studiedIndexes.put(0, "knr"); // DONT CHANGE NAMES
		studiedIndexes.put(1, "pnr");
		studiedIndexes.put(2, "betyg");

		/** Area codes fillings **/
		areaCodes.add("040");
		areaCodes.add("0709");
		areaCodes.add("0706");
		areaCodes.add("0770");
		areaCodes.add("08");
		areaCodes.add("048");
		areaCodes.add("0763");

		BufferedReader reader = null;
		String s = "";
		try {
			reader = new BufferedReader(new FileReader(new File(getClass().getResource("text/firstNames.txt").getFile())));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(getClass().getResource("/text/firstNames.txt").getFile()), StandardCharsets.UTF_8));
			while ((s = reader.readLine()) != null) {
				firstNames.add(s);
			}

			reader = new BufferedReader(new FileReader(new File(getClass().getResource("text/lastnames.txt").getFile())));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(getClass().getResource("/text/lastnames.txt").getFile()), StandardCharsets.UTF_8));
			while ((s = reader.readLine()) != null) {
				lastNames.add(s);
			}

			reader = new BufferedReader(new FileReader(new File(getClass().getResource("text/streets.txt").getFile())));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(getClass().getResource("/text/streets.txt").getFile()), StandardCharsets.UTF_8));
			while ((s = reader.readLine()) != null) {
				addresses.add(s);
			}


			reader = new BufferedReader(new FileReader(new File(getClass().getResource("text/courseNames.txt").getFile())));
//			reader = new BufferedReader(new InputStreamReader(new FileInputStream(getClass().getResource("/text/courseNames.txt").getFile()), StandardCharsets.UTF_8));
			while ((s = reader.readLine()) != null) {
				courseNames.add(s);
			}

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void setComboBoxValues(CheckComboBox<String> box, ObservableList<String> list) {
		box.getItems().clear();
		box.getItems().addAll(list);
	}

	@FXML
	private void generateStudentButton() {
		
		studentIDs.clear();
		
		if (studentIndexes.get(-1337) != null) {
			studentIdIs12Digits = studentIndexes.get(-1337).equals("true");
			System.out.println(studentIndexes.get(-1337));
		} else {
			studentIdIs12Digits = false;
		}
		System.out.println(studentIdIs12Digits);

		StringBuilder query = new StringBuilder();
		String fileName = "";
		boolean firstLine = true;

		BufferedWriter bw = null;
		if ((numberOfStudentsWantedNummer = Utils.isNumber(numberOfStudentsWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1) {
			if (studentToFileChkBox.isSelected()) {
				fileName = Utils.getSaveDir() + File.separator + studentTableName + "Output.txt";
				try {
					bw = new BufferedWriter(new FileWriter(new File(fileName)));

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			query.append("insert into " + studentTableName + " values\n");

			Random r = new Random();
			int randomIndex = 0;
			int pnrCounter = 1;
			
			
			
			for (int times = 0; times < numberOfStudentsWantedNummer; times++) {

				query.append("(");

				for (int i = 0; i < studentComboBox.getCheckModel().getItemCount(); i++) {

					boolean columnIsChecked = studentComboBox.getCheckModel().isChecked(i);
					if (columnIsChecked) {
						query.append("'");
						switch (studentIndexes.get(i)) {
						case "pnr":
							if (studentIdIs12Digits == false) {
								String pnumber = "P" + pnrCounter++;
								query.append(pnumber);
								studentIDs.add(pnumber);
							} else {
								String year = r.nextInt(80) + 1930 + "";
								String month = r.nextInt(12) + 1 + "";
								String day = (Integer.parseInt(month) % 2 == 0) ? r.nextInt(30) + 1 + "" : r.nextInt(31) + 1 + "";
								String lastFour = r.nextInt(8999) + 1000 + "";
								
								if (month.length() == 1) month = "0" + month;
								if (day.length() == 1) day = "0" + day;

								System.out.println("year:" + year + ", month:" + month + ", day:" + day + ", last:" + lastFour);
								String pnumber = year + "" + month + "" + day + "" + lastFour;
								query.append(pnumber);
								studentIDs.add(pnumber);								
							}
							break;
						case "namn":
							randomIndex = r.nextInt(firstNames.size() - 1);
							query.append(firstNames.get(randomIndex) + " ");
							randomIndex = r.nextInt(lastNames.size() - 1);
							query.append(lastNames.get(randomIndex));
							break;
						case "adress":
							randomIndex = r.nextInt(addresses.size() - 1);
							query.append(addresses.get(randomIndex));
							break;
						case "tfnnr":
							randomIndex = r.nextInt(areaCodes.size() - 1);
							query.append(areaCodes.get(randomIndex) + "-" + r.nextInt(999999));
							break;
						}
						query.append("'");
					} else {
						query.append("null");
					}
					query.append(", ");

				}
				query.setLength(query.length() - 2);
				query.append("),\n");

				if (studentToFileChkBox.isSelected()) {
					try {
						if (firstLine == false)
							query.setLength(query.length() - 2);
						bw.write(query.toString());
						query.setLength(0);
						if (firstLine == false)
							query.append(",\n");
						firstLine = false;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			query.setLength(query.length() - 2);

		}
		if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (studentToFileChkBox.isSelected()) {
			codeTextArea.setText("Query skriven till:\n" + fileName);
		} else {
			codeTextArea.setText(query.toString());
		}

	}

	@FXML
	private void generateKursButton() {

		coursePoints.clear();
		
		StringBuilder query = new StringBuilder();
		String fileName = "";
		boolean firstLine = true;

		BufferedWriter bw = null;
		if ((numberOfCoursesWantedNummer = Utils.isNumber(numberOfCoursesWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1) {
			if (courseToFileChkBox.isSelected()) {
				
				coursePoints.clear();
				
				fileName = Utils.getSaveDir() + File.separator + courseTableName + "Output.txt";
				try {
					bw = new BufferedWriter(new FileWriter(new File(fileName)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			query.append("insert into " + courseTableName + " values\n");

			Random r = new Random();
			int randomIndex = 0;
			int courseCodeCounter = 1;
			
			String courseCode = "";
			int coursePointsNumber = 0;
			
			for (int times = 0; times < numberOfCoursesWantedNummer; times++) {

				query.append("(");

				for (int i = 0; i < kursComboBox.getCheckModel().getItemCount(); i++) {

					
					
					boolean columnIsChecked = kursComboBox.getCheckModel().isChecked(i);
					if (columnIsChecked) {
						switch (courseIndexes.get(i)) {
						case "knr":
							query.append("'");
							courseCode = "K" + courseCodeCounter++;
							query.append(courseCode);
							query.append("'");
							break;
						case "namn":
							query.append("'");
							randomIndex = r.nextInt(courseNames.size() - 1);
							String randomCourseName = courseNames.get(randomIndex);
							query.append(randomCourseName.replaceAll("['\"]*", ""));
							query.append("'");
							break;
						case "poäng":
							coursePointsNumber = r.nextInt(10) + 1;
							query.append(coursePointsNumber);
							break;
						}
					} else {
						query.append("null");
					}
					
					/** Adding what points each generated course has, for further studying-row generations **/
					coursePoints.put(courseCode, coursePointsNumber);
					/** **/
					
					query.append(", ");
				}
				query.setLength(query.length() - 2);
				query.append("),\n");

				if (courseToFileChkBox.isSelected()) {
					try {
						if (firstLine == false)
							query.setLength(query.length() - 2);
						bw.write(query.toString());
						query.setLength(0);
						if (firstLine == false)
							query.append(",\n");
						firstLine = false;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			query.setLength(query.length() - 2);

			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (courseToFileChkBox.isSelected()) {
				codeTextArea.setText("Query skriven till:\n" + fileName);
			} else {
				codeTextArea.setText(query.toString());
			}

		}

	}

	@FXML
	private void generateLäserButton() {
		
		if (coursePoints.size() <= 1) { //1 since they can all be "null"-key, or 0 if none has been generated
			this.showAlert("Generera kurser först (som har kurskod)");
			return;
		}

		StringBuilder query = new StringBuilder();
		String fileName = "";
		boolean firstLine = true;

		BufferedWriter bw = null;
		if (((studLäserFrånNummer = Utils.isNumber(studLäserFrån.getText(), MainApp.getMainApp().getPrimaryStage())) != -1)
				&& (studLäserTillNummer = Utils.isNumber(studLäserTill.getText(), MainApp.getMainApp().getPrimaryStage())) != -1
				&& (numberOfStudentsWantedNummer = Utils.isNumber(numberOfStudentsWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1
				&& (numberOfCoursesWantedNummer = Utils.isNumber(numberOfCoursesWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1) {

			if (studLäserFrånNummer > studLäserTillNummer) {
				showAlert("Från måste vara större än Till");
				return;
			}

			if (studyingToFileChkBox.isSelected()) {
				fileName = Utils.getSaveDir() + File.separator + studyingTableName + "Output.txt";
				try {
					bw = new BufferedWriter(new FileWriter(new File(fileName)));

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			query.append("insert into " + studyingTableName + " values\n");

			Random r = new Random();
			HashSet<String> set = null;
			
			for (String pnr : studentIDs) {

				set = new HashSet<String>();
				
				int pointsReadThisSemester = 0;
				String knr = "";

				int rnbr = r.nextInt(studLäserTillNummer) + studLäserFrånNummer;
				for (int i = 0; (i < rnbr); i++) {
					String randomCourseID = "K" + (r.nextInt(numberOfCoursesWantedNummer) + 1);
					pointsReadThisSemester += coursePoints.get(randomCourseID);
					if (pointsReadThisSemester <= maximumPointsReadPerSemester && r.nextInt(15) < 10) {
						set.add(pnr + "\t" + randomCourseID);
					}
				}

				for (String combo : set) {
					query.append("(");

					String[] split = combo.split("\t");

					for (int i = 0; i < läserComboBox.getCheckModel().getItemCount(); i++) {

						boolean columnIsChecked = läserComboBox.getCheckModel().isChecked(i);
						if (columnIsChecked) {
							switch (studyingIndexes.get(i)) {
							case "pnr":
								query.append("'" + split[0] + "'");
								break;
							case "knr":
								query.append("'" + split[1] + "'");
								break;
							}
						} else {
							query.append("null");
						}
						query.append(", ");
					}
					query.setLength(query.length() - 2);
					query.append("),\n");

					if (studyingToFileChkBox.isSelected()) {
						try {
							if (firstLine == false)
								query.setLength(query.length() - 2);
							bw.write(query.toString());
							query.setLength(0);
							if (firstLine == false)
								query.append(",\n");
							firstLine = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			query.setLength(query.length() - 2);
		}

		if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (studyingToFileChkBox.isSelected()) {
			codeTextArea.setText("Query skriven till:\n" + fileName);
		} else {
			codeTextArea.setText(query.toString());
		}

	}

	@FXML
	private void generateLästButton() {
		StringBuilder query = new StringBuilder();
		String fileName = "";
		boolean firstLine = true;

		BufferedWriter bw = null;
		if (((studLästFrånNummer = Utils.isNumber(studLästFrån.getText(), MainApp.getMainApp().getPrimaryStage())) != -1)
				&& (studLästTillNummer = Utils.isNumber(studLästTill.getText(), MainApp.getMainApp().getPrimaryStage())) != -1
				&& (numberOfStudentsWantedNummer = Utils.isNumber(numberOfStudentsWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1
				&& (numberOfCoursesWantedNummer = Utils.isNumber(numberOfCoursesWanted.getText(), MainApp.getMainApp().getPrimaryStage())) != -1) {

			if (studLästFrånNummer > studLästTillNummer) {
				showAlert("Från måste vara större än Till");
				return;
			}

			if (studiedToFileChkBox.isSelected()) {
				fileName = Utils.getSaveDir() + File.separator + studiedTableName + "Output.txt";
				try {
					bw = new BufferedWriter(new FileWriter(new File(fileName)));

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			query.append("insert into " + studiedTableName + " values\n");

			Random r = new Random();
//			int studentIndexCounter = 1;
			HashSet<String> set = null;
//			for (int times = 0; times < numberOfStudentsWantedNummer; times++) {
			for (String pnr : studentIDs) {

				set = new HashSet<String>();
//				String pnr = "P" + studentIndexCounter++;

				int rnbr = r.nextInt(studLästTillNummer) + studLästFrånNummer;
				for (int i = 0; i < rnbr; i++) {
					set.add(pnr + "\t" + "K" + (r.nextInt(numberOfCoursesWantedNummer) + 1));
				}

				for (String combo : set) {
					query.append("(");

					String[] split = combo.split("\t");

					for (int i = 0; i < lästComboBox.getCheckModel().getItemCount(); i++) {

						boolean columnIsChecked = lästComboBox.getCheckModel().isChecked(i);
						if (columnIsChecked) {
							switch (studiedIndexes.get(i)) {
							case "pnr":
								query.append("'" + split[0] + "'");
								break;
							case "knr":
								query.append("'" + split[1] + "'");
								break;
							case "betyg":
								query.append(r.nextInt(6));
								break;
							}
						} else {
							query.append("null");
						}
						query.append(", ");
					}
					query.setLength(query.length() - 2);
					query.append("),\n");

					if (studiedToFileChkBox.isSelected()) {
						try {
							if (firstLine == false)
								query.setLength(query.length() - 2);
							bw.write(query.toString());
							query.setLength(0);
							if (firstLine == false)
								query.append(",\n");
							firstLine = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
			query.setLength(query.length() - 2);
		}

		if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (studiedToFileChkBox.isSelected()) {
			codeTextArea.setText("Query skriven till:\n" + fileName);
		} else {
			codeTextArea.setText(query.toString());
		}
	}

//	private int isNumber(String toBeParsed) {
//
//		if (toBeParsed.length() == 0)
//			return -1;
//
//		if (Utils.isNumber(toBeParsed) == false) {
//			this.showAlert("Parsing fel, kolla siffrorna");
//			return -1;
//		}
//		if (Double.parseDouble(toBeParsed) > Integer.MAX_VALUE) {
//			this.showAlert("Värde > Integer.MAX_VALUE");
//			return -1;
//		}
//
//		return Integer.parseInt(toBeParsed);
//	}
//
	private void showAlert(String displayText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(MainApp.getMainApp().getPrimaryStage());
		alert.setTitle("Number error");
		alert.setHeaderText("Number error");
		alert.setContentText(displayText);

		alert.showAndWait();
	}

	@FXML
	private void setupStudent() {
		HashMap<Integer, String> returnValue = MainApp.getMainApp().showSetupStudentDialog(this);
		if (returnValue != null) {
			studentIndexes = returnValue;

			ObservableList<String> studentColumns = FXCollections.observableArrayList();
			int numberOfWantedColumns = 0;
			for (int key : studentIndexes.keySet())
				if (key >= 0)
					numberOfWantedColumns++; // If its < 0 we have marked it as
												// unwanted in the setup

			for (int i = 0; i < numberOfWantedColumns; i++) {
				studentColumns.add(i, studentIndexes.get(i));
			}

			this.setComboBoxValues(studentComboBox, studentColumns);
		}
	}

	@FXML
	private void setupCourse() {
		HashMap<Integer, String> returnValue = MainApp.getMainApp().showSetupCourseDialog(this);
		if (returnValue != null) {
			courseIndexes = returnValue;

			ObservableList<String> courseColumns = FXCollections.observableArrayList();
			int numberOfWantedColumns = 0;
			for (int key : courseIndexes.keySet())
				if (key >= 0)
					numberOfWantedColumns++; // If its < 0 we have marked it as
												// unwanted in the setup

			for (int i = 0; i < numberOfWantedColumns; i++) {
				courseColumns.add(i, courseIndexes.get(i));
			}

			this.setComboBoxValues(kursComboBox, courseColumns);
		}
	}

	@FXML
	private void setupLäser() {
		HashMap<Integer, String> returnValue = MainApp.getMainApp().showSetupLäserDialog(this);
		if (returnValue != null) {
			studyingIndexes = returnValue;

			ObservableList<String> studyingColumns = FXCollections.observableArrayList();
			int numberOfWantedColumns = 0;
			for (int key : studyingIndexes.keySet())
				if (key >= 0)
					numberOfWantedColumns++; // If its < 0 we have marked it as
												// unwanted in the setup

			for (int i = 0; i < numberOfWantedColumns; i++) {
				studyingColumns.add(i, studyingIndexes.get(i));
			}

			this.setComboBoxValues(läserComboBox, studyingColumns);
		}
	}

	@FXML
	private void setupLäst() {
		HashMap<Integer, String> returnValue = MainApp.getMainApp().showSetupLästDialog(this);
		if (returnValue != null) {
			studiedIndexes = returnValue;

			ObservableList<String> studiedColumns = FXCollections.observableArrayList();
			int numberOfWantedColumns = 0;
			for (int key : studiedIndexes.keySet())
				if (key >= 0)
					numberOfWantedColumns++; // If its < 0 we have marked it as
												// unwanted in the setup

			for (int i = 0; i < numberOfWantedColumns; i++) {
				studiedColumns.add(i, studiedIndexes.get(i));
			}

			this.setComboBoxValues(lästComboBox, studiedColumns);
		}
	}

	@FXML
	private void toFileStudent() {
		if (Utils.setSaveDir() == false)
			this.studentToFileChkBox.setSelected(false);
	}
	
	@FXML
	private void toFileCourse() {
		if (Utils.setSaveDir() == false)
			this.courseToFileChkBox.setSelected(false);
	}
	
	@FXML
	private void toFileStudying() {
		if (Utils.setSaveDir() == false)
			this.studyingToFileChkBox.setSelected(false);
	}
	
	@FXML
	private void toFileStudied() {
		if (Utils.setSaveDir() == false)
			this.studiedToFileChkBox.setSelected(false);
	}
	
	
//	public static String setSaveDir(String source) {
//		if (this.saveDir.length() > 0)
//			return; // then dir is already set
//		// this.saveDir = dir;
//		DirectoryChooser chooser = new DirectoryChooser();
//		chooser.setTitle("Välj sparmapp");
//
//		try {
//			File selectedDirectory = chooser.showDialog(MainApp.getMainApp().getPrimaryStage());
//			if (selectedDirectory != null) {
//				// return selectedDirectory.getAbsolutePath();
//				this.saveDir = selectedDirectory.getAbsolutePath();
//				System.out.println(this.saveDir);
//			} else {
//				switch (source) {
//				case "student": studentToFileChkBox.setSelected(false);
//					break;
//				case "course": courseToFileChkBox.setSelected(false);
//					break;
//				case "studying": studyingToFileChkBox.setSelected(false);
//					break;
//				case "studied": studiedToFileChkBox.setSelected(false);
//					break;
//				}
//			}
//		} catch (IllegalArgumentException e) {
//			System.out.println("fel");
//		}
//	}

}
