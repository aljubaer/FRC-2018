package Fertilizer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class myPanelController implements Initializable{
	private AEZ thisAEZ;
	Database db = new Database();
	
	@FXML
	private ComboBox<String> aez;
	@FXML
	private ComboBox<String> landType;
	@FXML
	private ComboBox<String>rabiManure, k1Manure, k2Manure;
	@FXML
	private ComboBox<String>rabi, k1, k2;
	@FXML
	private Spinner<Double> rabiRate, k1Rate, k2Rate;
	@FXML
	private Spinner<Double> rabiYield, k1Yield, k2Yield;
	@FXML
	private Spinner<Double> rabiN, k1N, k2N;
	@FXML
	private Spinner<Double> rabiP, k1P, k2P;
	@FXML
	private Spinner<Double> rabiK, k1K, k2K;
	@FXML
	private Spinner<Double> rabiResidue, k1Residue, k2Residue;
	@FXML
	private Button calc;
	@FXML
	private TableView<Row>tableInput, tableOutput, tablePartialBalanceSheet, tableBalanceSheet;
	@FXML
	private Label rabiCrop1, k1Crop1, k2Crop1, rabiCrop2, k1Crop2, k2Crop2, rabiCrop3, k1Crop3, k2Crop3, rabiCrop4, k1Crop4, k2Crop4;
	@FXML
	private Label avgRainfall, fertilityClass;
	@FXML
	private BarChart<String, Double> partialBalanceGraph, balanceGraph, cropBalanceGraph, cropPartialBalanceGraph;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		populateAEZ();
		populateAllManure();
		populateAllVariety();
		populateAllLandType();
		populateAllSpinner();
		//tableInput.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		colorIt();
		onAction(null);
		System.out.println("Done");
		
	}
	private void colorIt() {
		rabiN.getStyleClass().add("text-input2");
		rabiP.getStyleClass().add("combo-box-baseRed");
		rabiK.getStyleClass().add("combo-box-baseRed");
		rabiManure.getStyleClass().add("combo-box-baseRed");
		rabiRate.getStyleClass().add("combo-box-baseRed");
		rabiResidue.getStyleClass().add("combo-box-baseRed");
		rabi.getStyleClass().add("combo-box-baseRed");
	}
	@FXML
	private void onAction(ActionEvent e) {
	//	System.out.println("Hello");
		//double rabiNV = removeDoubleError(rabiN);
		//double k1NV = removeDoubleError(k1N);
		//System.out.println(rabiNV + " " + k1NV);
		//String s = removeStringError(rabi);
		//String s2 = removeStringError(k1);
		//System.out.println(s + " " + s2);
		takeInput();
		avgRainfall.setText(Double.toString(db.getAvgRainfall(thisAEZ.aezNo)));
		fertilityClass.setText(getSoilFertilityValue(db.getSoilFertility(thisAEZ.aezNo)));
		setTextToLabels();
		populateTable();
		createGraphs();
	}
	public String getSoilFertilityValue(String soilFertilityClass) {
		if(soilFertilityClass.equals("L"))return "Low";
		else if(soilFertilityClass.equals("M"))return "Medium";
		else return "High";
	}
	private void createGraphs() {
		partialBalanceGraph.setTitle("Partial Balance Graph");
		
		XYChart.Series seriesPartialNutrients1 = new XYChart.Series();
		XYChart.Series seriesPartialNutrients2 = new XYChart.Series();
		XYChart.Series seriesPartialNutrients3 = new XYChart.Series();
		seriesPartialNutrients1.setName("N");
		seriesPartialNutrients2.setName("P");
		seriesPartialNutrients3.setName("K");
		XYChart.Data pbgN = new XYChart.Data("N", thisAEZ.getTotalPartialBalanceForSpecificNutrient(0));
		
		
		seriesPartialNutrients1.getData().add(pbgN);
		seriesPartialNutrients2.getData().add(new XYChart.Data("P", thisAEZ.getTotalPartialBalanceForSpecificNutrient(1)));
		seriesPartialNutrients3.getData().add(new XYChart.Data("K", thisAEZ.getTotalPartialBalanceForSpecificNutrient(2)));
		partialBalanceGraph.getData().clear();
		partialBalanceGraph.getData().addAll(seriesPartialNutrients1, seriesPartialNutrients2, seriesPartialNutrients3);
	//	partialBalanceGraph.getStyleClass().addAll("chart", "chart-bar", "bar-legend-symbol", "bar-legend-symbol");
		balanceGraph.setTitle("Balance Graph");
		XYChart.Series seriesNutrients1 = new XYChart.Series();
		XYChart.Series seriesNutrients2 = new XYChart.Series();
		XYChart.Series seriesNutrients3 = new XYChart.Series();
		seriesNutrients1.setName("N");
		seriesNutrients2.setName("P");
		seriesNutrients3.setName("K");
		seriesNutrients1.getData().add(new XYChart.Data("N", thisAEZ.getTotalBalanceForSpecificNutrient(0)));
		seriesNutrients2.getData().add(new XYChart.Data("P", thisAEZ.getTotalBalanceForSpecificNutrient(1)));
		seriesNutrients3.getData().add(new XYChart.Data("K", thisAEZ.getTotalBalanceForSpecificNutrient(2)));
		balanceGraph.getData().clear();
		balanceGraph.getData().addAll(seriesNutrients1, seriesNutrients2, seriesNutrients3);
		HashMap<String, Integer> count = new HashMap();
		XYChart.Series seriesCropPartialNutrients [] = new XYChart.Series [3];
		for(int i = 0; i < 3; i++) {
			seriesCropPartialNutrients[i] = new XYChart.Series<>();
		}
		seriesCropPartialNutrients[0].setName("N");
		seriesCropPartialNutrients[1].setName("P");
		seriesCropPartialNutrients[2].setName("K");
		cropPartialBalanceGraph.setTitle("Crop Partial Balance Graph");
		cropPartialBalanceGraph.getData().clear();
		String [] season = {" (Rabi)", " (Kharif - 1)", " (Kharif - 2)"}; 
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				seriesCropPartialNutrients[i].getData().add(new XYChart.Data(thisAEZ.crops[j].name + season[j], thisAEZ.crops[j].getPartialInputOutputDifferenceForSpecificNutrient(i)));
			}
			cropPartialBalanceGraph.getData().add(seriesCropPartialNutrients[i]);
		}
		
		XYChart.Series seriesCropNutrients [] = new XYChart.Series [3];
		for(int i = 0; i < 3; i++) {
			seriesCropNutrients[i] = new XYChart.Series<>();
		}
		seriesCropNutrients[0].setName("N");
		seriesCropNutrients[1].setName("P");
		seriesCropNutrients[2].setName("K");
		cropBalanceGraph.setTitle("Crop Balance Graph");
		cropBalanceGraph.getData().clear();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				seriesCropNutrients[i].getData().add(new XYChart.Data(thisAEZ.crops[j].name + season[j], thisAEZ.crops[j].getInputOutputDifferenceForSpecificNutrient(i)));
			}
			cropBalanceGraph.getData().add(seriesCropNutrients[i]);
		}
		
		
		
	}
	private void setTextToLabels() {
		rabiCrop1.setText(thisAEZ.crops[0].name);
		k1Crop1.setText(thisAEZ.crops[1].name);
		k2Crop1.setText(thisAEZ.crops[2].name);
		
		rabiCrop2.setText(thisAEZ.crops[0].name);
		k1Crop2.setText(thisAEZ.crops[1].name);
		k2Crop2.setText(thisAEZ.crops[2].name);
		
		rabiCrop3.setText(thisAEZ.crops[0].name);
		k1Crop3.setText(thisAEZ.crops[1].name);
		k2Crop3.setText(thisAEZ.crops[2].name);
		
		rabiCrop4.setText(thisAEZ.crops[0].name);
		k1Crop4.setText(thisAEZ.crops[1].name);
		k2Crop4.setText(thisAEZ.crops[2].name);
	}
	private void populateTable() {
		String [] fertilizerInput = {"Fertilizer", "Manure", "Fixation (BNF)", "Deposition (rain)", "Sedimentation (flood)", "Irrigation", "Total"};
		String [] fertilizerOutput = {"Harvested Product", "Residues Removed", "Leeching", "Denitrification", "Volatilation", "Erosion", "Total"};
		String [] partialBalance = {"Fertilizer + Manure", "Harvest Product + Residues Removed", "Total"};
		String [] balanceSheet = {"Total Input", "Total Output", "Balance Sheet"};
		ObservableList<Row> dataInput = initializeInput(fertilizerInput);
		ObservableList<Row> dataOutput = initializeOutput(fertilizerOutput);
		ObservableList<Row> dataPartialBalanceSheet = initializePartialBalance(partialBalance);
		ObservableList<Row> dataBalanceSheet = initializeBalanceSheet(balanceSheet);
		
		TableColumn <Row, String> [] colInput = new TableColumn [13];
		TableColumn <Row, String> [] colOutput = new TableColumn [13];
		TableColumn <Row, String> [] colPartialBalance = new TableColumn [13];
		TableColumn <Row, String> [] colBalanceSheet = new TableColumn [13];
		String [] colName = {"Name", "N", "P", "K", "N", "P", "K","N", "P", "K","N", "P", "K" };
		String [] propertyName = {"name", "N1", "P1", "K1", "N2", "P2", "K2","N3", "P3", "K3","T1", "T2", "T3" };

		insertDataInTable(dataInput, tableInput);
		insertDataInTable(dataOutput, tableOutput);
		insertDataInTable(dataPartialBalanceSheet, tablePartialBalanceSheet);
		insertDataInTable(dataBalanceSheet, tableBalanceSheet);
		
		createColumns(colInput, colName, propertyName, 13);
		createColumns(colOutput, colName, propertyName, 13);
		createColumns(colPartialBalance, colName, propertyName, 13);
		createColumns(colBalanceSheet, colName, propertyName, 13);
		
		addStyleSheets(colInput);
		addStyleSheets(colOutput);
		addStyleSheets(colPartialBalance);
		addStyleSheets(colBalanceSheet);
		addColumnInTable(colInput, tableInput, 13);
		addColumnInTable(colOutput, tableOutput, 13);
		addColumnInTable(colPartialBalance, tablePartialBalanceSheet, 13);
		addColumnInTable(colBalanceSheet, tableBalanceSheet, 13);

	}
	private void addColumnInTable(TableColumn<Row, String>[] col, TableView<Row> table, int num) {
		for(int i = 0; i < num; i++) {
			table.getColumns().add(col[i]);
			//tableOutput.getColumns().add(colOutput[i]);
		}
	//	table.getStyleClass().add("fancytext");
	}
	private void insertDataInTable(ObservableList<Row> data, TableView<Row> table) {
		if(table == null) {
			System.out.println("NULL");
			return;
		}
		table.getColumns().clear();
		table.getItems().clear();
		table.setItems(data);
	}
	private void createColumns(TableColumn<Row, String>[] col, String[] colName, String[] propertyName, int number) {
		for(int i = 0; i < number; i++) {
			col[i] = new TableColumn<> (colName[i]);
			col[i].setMinWidth(63.2);
			col[i].setCellValueFactory(new PropertyValueFactory<Row, String>(propertyName[i]));
			col[i].getStyleClass().add("fancytext");

		}
		col[0].setMinWidth(190);
		col[number - 1].setMinWidth(63);
	}
	private void addStyleSheets(TableColumn<Row, String>[] col) {
		colorColumn(col, 0, 0, "lightblue");
		colorColumn(col, 1, 3, "red");
		colorColumn(col, 4, 6, "orange");
		colorColumn(col, 7, 9, "yellow");
		colorColumn(col, 10, 12, "grey");
	}
	private void colorColumn(TableColumn <Row, String> [] col, int start, int end, String color) {
		for(int i = start; i <= end; i++) {
			col[i].getStyleClass().add(color);
			col[i].getStyleClass().add("borderColor");
		}
	}
	private ObservableList<Row> initializeInput(String[] fertilizerInput) {
		System.out.println(thisAEZ.crops[2].input[0].amount + ", " + thisAEZ.crops[2].input[1].amount + ", " + thisAEZ.crops[2].input[2].amount);
		ObservableList<Row> data = FXCollections.observableArrayList(
				new Row (fertilizerInput[0], thisAEZ.crops[0].input[0].amount, thisAEZ.crops[0].input[1].amount, thisAEZ.crops[0].input[2].amount,
						thisAEZ.crops[1].input[0].amount, thisAEZ.crops[1].input[1].amount, thisAEZ.crops[1].input[2].amount,
						thisAEZ.crops[2].input[0].amount, thisAEZ.crops[2].input[1].amount, thisAEZ.crops[2].input[2].amount,
						thisAEZ.input.input[0].amount,thisAEZ.input.input[1].amount,thisAEZ.input.input[2].amount),
				
				new Row (fertilizerInput[1], thisAEZ.crops[0].input[0].manureAmount, thisAEZ.crops[0].input[1].manureAmount, thisAEZ.crops[0].input[2].manureAmount,
						thisAEZ.crops[1].input[0].manureAmount, thisAEZ.crops[1].input[1].manureAmount, thisAEZ.crops[1].input[2].manureAmount,
						thisAEZ.crops[2].input[0].manureAmount, thisAEZ.crops[2].input[1].manureAmount, thisAEZ.crops[2].input[2].manureAmount,
						thisAEZ.input.input[0].manureAmount,thisAEZ.input.input[1].manureAmount,thisAEZ.input.input[2].manureAmount), 
				
				new Row (fertilizerInput[2], thisAEZ.crops[0].input[0].bnf, thisAEZ.crops[0].input[1].bnf, thisAEZ.crops[0].input[2].bnf,
						thisAEZ.crops[1].input[0].bnf, thisAEZ.crops[1].input[1].bnf, thisAEZ.crops[1].input[2].bnf,
						thisAEZ.crops[2].input[0].bnf, thisAEZ.crops[2].input[1].bnf, thisAEZ.crops[2].input[2].bnf,
						thisAEZ.input.input[0].bnf,thisAEZ.input.input[1].bnf,thisAEZ.input.input[2].bnf),
				new Row (fertilizerInput[3], thisAEZ.crops[0].input[0].deposition, thisAEZ.crops[0].input[1].deposition, thisAEZ.crops[0].input[2].deposition,
						thisAEZ.crops[1].input[0].deposition, thisAEZ.crops[1].input[1].deposition, thisAEZ.crops[1].input[2].deposition,
						thisAEZ.crops[2].input[0].deposition, thisAEZ.crops[2].input[1].deposition, thisAEZ.crops[2].input[2].deposition,
						thisAEZ.input.input[0].deposition,thisAEZ.input.input[1].deposition,thisAEZ.input.input[2].deposition), 
				new Row (fertilizerInput[4], thisAEZ.crops[0].input[0].sedimentation, thisAEZ.crops[0].input[1].sedimentation, thisAEZ.crops[0].input[2].sedimentation,
						thisAEZ.crops[1].input[0].sedimentation, thisAEZ.crops[1].input[1].sedimentation, thisAEZ.crops[1].input[2].sedimentation,
						thisAEZ.crops[2].input[0].sedimentation, thisAEZ.crops[2].input[1].sedimentation, thisAEZ.crops[2].input[2].sedimentation,
						thisAEZ.input.input[0].sedimentation,thisAEZ.input.input[1].sedimentation,thisAEZ.input.input[2].sedimentation),
				new Row (fertilizerInput[5], thisAEZ.crops[0].input[0].irrigation, thisAEZ.crops[0].input[1].irrigation, thisAEZ.crops[0].input[2].irrigation,
						thisAEZ.crops[1].input[0].irrigation, thisAEZ.crops[1].input[1].irrigation, thisAEZ.crops[1].input[2].irrigation,
						thisAEZ.crops[2].input[0].irrigation, thisAEZ.crops[2].input[1].irrigation, thisAEZ.crops[2].input[2].irrigation,
						thisAEZ.input.input[0].irrigation,thisAEZ.input.input[1].irrigation,thisAEZ.input.input[2].irrigation),
				new Row (fertilizerInput[6], thisAEZ.crops[0].input[0].total, thisAEZ.crops[0].input[1].total, thisAEZ.crops[0].input[2].total,
						thisAEZ.crops[1].input[0].total, thisAEZ.crops[1].input[1].total, thisAEZ.crops[1].input[2].total,
						thisAEZ.crops[2].input[0].total, thisAEZ.crops[2].input[1].total, thisAEZ.crops[2].input[2].total,
						thisAEZ.input.input[0].total,thisAEZ.input.input[1].total,thisAEZ.input.input[2].total)
				);
		return data;
	}
	private ObservableList<Row> initializeOutput(String[] fertilizerOutput) {
		ObservableList<Row> data = FXCollections.observableArrayList(
				new Row (fertilizerOutput[0], thisAEZ.crops[0].output[0].harvestedProduct, thisAEZ.crops[0].output[1].harvestedProduct, thisAEZ.crops[0].output[2].harvestedProduct,
						thisAEZ.crops[1].output[0].harvestedProduct, thisAEZ.crops[1].output[1].harvestedProduct, thisAEZ.crops[1].output[2].harvestedProduct,
						thisAEZ.crops[2].output[0].harvestedProduct, thisAEZ.crops[2].output[1].harvestedProduct, thisAEZ.crops[2].output[2].harvestedProduct,
						thisAEZ.output.output[0].harvestedProduct,thisAEZ.output.output[1].harvestedProduct,thisAEZ.output.output[2].harvestedProduct),
				
				new Row (fertilizerOutput[1], thisAEZ.crops[0].output[0].residuesRemoved, thisAEZ.crops[0].output[1].residuesRemoved, thisAEZ.crops[0].output[2].residuesRemoved,
						thisAEZ.crops[1].output[0].residuesRemoved, thisAEZ.crops[1].output[1].residuesRemoved, thisAEZ.crops[1].output[2].residuesRemoved,
						thisAEZ.crops[2].output[0].residuesRemoved, thisAEZ.crops[2].output[1].residuesRemoved, thisAEZ.crops[2].output[2].residuesRemoved,
						thisAEZ.output.output[0].residuesRemoved,thisAEZ.output.output[1].residuesRemoved,thisAEZ.output.output[2].residuesRemoved),
				
				new Row (fertilizerOutput[2], thisAEZ.crops[0].output[0].leeching, thisAEZ.crops[0].output[1].leeching, thisAEZ.crops[0].output[2].leeching,
						thisAEZ.crops[1].output[0].leeching, thisAEZ.crops[1].output[1].leeching, thisAEZ.crops[1].output[2].leeching,
						thisAEZ.crops[2].output[0].leeching, thisAEZ.crops[2].output[1].leeching, thisAEZ.crops[2].output[2].leeching,
						thisAEZ.output.output[0].leeching,thisAEZ.output.output[1].leeching,thisAEZ.output.output[2].leeching),
				
				new Row (fertilizerOutput[3], thisAEZ.crops[0].output[0].denitrification, thisAEZ.crops[0].output[1].denitrification, thisAEZ.crops[0].output[2].denitrification,
						thisAEZ.crops[1].output[0].denitrification, thisAEZ.crops[1].output[1].denitrification, thisAEZ.crops[1].output[2].denitrification,
						thisAEZ.crops[2].output[0].denitrification, thisAEZ.crops[2].output[1].denitrification, thisAEZ.crops[2].output[2].denitrification,
						thisAEZ.output.output[0].denitrification,thisAEZ.output.output[1].denitrification,thisAEZ.output.output[2].denitrification),
				
				new Row (fertilizerOutput[4], thisAEZ.crops[0].output[0].volitilation, thisAEZ.crops[0].output[1].volitilation, thisAEZ.crops[0].output[2].volitilation,
						thisAEZ.crops[1].output[0].volitilation, thisAEZ.crops[1].output[1].volitilation, thisAEZ.crops[1].output[2].volitilation,
						thisAEZ.crops[2].output[0].volitilation, thisAEZ.crops[2].output[1].volitilation, thisAEZ.crops[2].output[2].volitilation,
						thisAEZ.output.output[0].volitilation,thisAEZ.output.output[1].volitilation,thisAEZ.output.output[2].volitilation),
				
				new Row (fertilizerOutput[5], thisAEZ.crops[0].output[0].erosion, thisAEZ.crops[0].output[1].erosion, thisAEZ.crops[0].output[2].erosion,
						thisAEZ.crops[1].output[0].erosion, thisAEZ.crops[1].output[1].erosion, thisAEZ.crops[1].output[2].erosion,
						thisAEZ.crops[2].output[0].erosion, thisAEZ.crops[2].output[1].erosion, thisAEZ.crops[2].output[2].erosion,
						thisAEZ.output.output[0].erosion,thisAEZ.output.output[1].erosion,thisAEZ.output.output[2].erosion),
				
				new Row (fertilizerOutput[6], thisAEZ.crops[0].output[0].total, thisAEZ.crops[0].output[1].total, thisAEZ.crops[0].output[2].total,
						thisAEZ.crops[1].output[0].total, thisAEZ.crops[1].output[1].total, thisAEZ.crops[1].output[2].total,
						thisAEZ.crops[2].output[0].total, thisAEZ.crops[2].output[1].total, thisAEZ.crops[2].output[2].total,
						thisAEZ.output.output[0].total,thisAEZ.output.output[1].total,thisAEZ.output.output[2].total)
				);
		return data;
	}
	private ObservableList<Row> initializePartialBalance(String[] partialBalance){
		ObservableList<Row> data = FXCollections.observableArrayList(
				new Row (partialBalance[0], thisAEZ.crops[0].totalAdditionForSpecificNutrient(0), thisAEZ.crops[0].totalAdditionForSpecificNutrient(1), thisAEZ.crops[0].totalAdditionForSpecificNutrient(2),
						thisAEZ.crops[1].totalAdditionForSpecificNutrient(0), thisAEZ.crops[1].totalAdditionForSpecificNutrient(1), thisAEZ.crops[1].totalAdditionForSpecificNutrient(2),
						thisAEZ.crops[2].totalAdditionForSpecificNutrient(0), thisAEZ.crops[2].totalAdditionForSpecificNutrient(1), thisAEZ.crops[2].totalAdditionForSpecificNutrient(2),
						thisAEZ.input.totalAdditionForSpecificNutrient(0),thisAEZ.input.totalAdditionForSpecificNutrient(1),thisAEZ.input.totalAdditionForSpecificNutrient(2)), 
				new Row (partialBalance[1], thisAEZ.crops[0].totalSubstractionForSpecificNutrient(0), thisAEZ.crops[0].totalSubstractionForSpecificNutrient(1), thisAEZ.crops[0].totalSubstractionForSpecificNutrient(2),
						thisAEZ.crops[1].totalSubstractionForSpecificNutrient(0), thisAEZ.crops[1].totalSubstractionForSpecificNutrient(1), thisAEZ.crops[1].totalSubstractionForSpecificNutrient(2),
						thisAEZ.crops[2].totalSubstractionForSpecificNutrient(0), thisAEZ.crops[2].totalSubstractionForSpecificNutrient(1), thisAEZ.crops[2].totalSubstractionForSpecificNutrient(2),
						thisAEZ.output.totalSubstractionForSpecificNutrient(0),thisAEZ.output.totalSubstractionForSpecificNutrient(1),thisAEZ.output.totalSubstractionForSpecificNutrient(2)),
				new Row (partialBalance[2], thisAEZ.crops[0].getPartialInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[0].getPartialInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[0].getPartialInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.crops[1].getPartialInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[1].getPartialInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[1].getPartialInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.crops[2].getPartialInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[2].getPartialInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[2].getPartialInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.getTotalPartialBalanceForSpecificNutrient(0),thisAEZ.getTotalPartialBalanceForSpecificNutrient(1),thisAEZ.getTotalPartialBalanceForSpecificNutrient(2))
				);
		return data;
	}
	private ObservableList<Row> initializeBalanceSheet(String[] balanceSheet){
		ObservableList<Row> data = FXCollections.observableArrayList(
				new Row (balanceSheet[0], thisAEZ.crops[0].input[0].total, thisAEZ.crops[0].input[1].total, thisAEZ.crops[0].input[2].total,
						thisAEZ.crops[1].input[0].total, thisAEZ.crops[1].input[1].total, thisAEZ.crops[1].input[2].total,
						thisAEZ.crops[2].input[0].total, thisAEZ.crops[2].input[1].total, thisAEZ.crops[2].input[2].total,
						thisAEZ.input.input[0].total,thisAEZ.input.input[1].total,thisAEZ.input.input[2].total),
				new Row (balanceSheet[1], thisAEZ.crops[0].output[0].total, thisAEZ.crops[0].output[1].total, thisAEZ.crops[0].output[2].total,
						thisAEZ.crops[1].output[0].total, thisAEZ.crops[1].output[1].total, thisAEZ.crops[1].output[2].total,
						thisAEZ.crops[2].output[0].total, thisAEZ.crops[2].output[1].total, thisAEZ.crops[2].output[2].total,
						thisAEZ.output.output[0].total,thisAEZ.output.output[1].total,thisAEZ.output.output[2].total),
				
				new Row (balanceSheet[2], thisAEZ.crops[0].getInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[0].getInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[0].getInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.crops[1].getInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[1].getInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[1].getInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.crops[2].getInputOutputDifferenceForSpecificNutrient(0), thisAEZ.crops[2].getInputOutputDifferenceForSpecificNutrient(1), thisAEZ.crops[2].getInputOutputDifferenceForSpecificNutrient(2),
						thisAEZ.getTotalBalanceForSpecificNutrient(0),thisAEZ.getTotalBalanceForSpecificNutrient(1),thisAEZ.getTotalBalanceForSpecificNutrient(2))
				);
		return data;
	}
	private void takeInput() {
		double [] [] val = new double [3][3];
		val[0][0] = removeDoubleError(rabiN);
		val[0][1] = removeDoubleError(rabiP); 
		val[0][2] = removeDoubleError(rabiK);
		
		val[1][0] = removeDoubleError(k1N);
		val[1][1] = removeDoubleError(k1P);
		val[1][2] = removeDoubleError(k1K);
		
		val[2][0] = removeDoubleError(k2N);
		val[2][1] = removeDoubleError(k2P);
		val[2][2] = removeDoubleError(k2K);
		System.out.println(val[2][0] + " " + val[2][1] + " " + val[2][2]);
		
		double [] res = new double[3];
		res[0] = removeDoubleError(rabiResidue);
		res[1] = removeDoubleError(k1Residue);
		res[2] = removeDoubleError(k2Residue);
		
		double [] yield = new double[3];
		yield[0] = removeDoubleError(rabiYield);
		yield[1] = removeDoubleError(k1Yield);
		yield[2] = removeDoubleError(k2Yield);
		
		double [] rate = new double[3];
		rate[0] = removeDoubleError(rabiRate);
		rate[1] = removeDoubleError(k1Rate);
		rate[2] = removeDoubleError(k2Rate);
		
		String [] crop = new String[3];
		crop[0] = removeStringError(rabi);
		crop[1] = removeStringError(k1);
		crop[2] = removeStringError(k2);
		
		String [] manure = new String[3];
		manure[0] = removeStringError(rabiManure);
		manure[1] = removeStringError(k1Manure);
		manure[2] = removeStringError(k2Manure);
		
		thisAEZ = null;
		thisAEZ = new AEZ(Integer.parseInt(check(removeStringError(aez))), removeStringError(landType), crop, yield, val, manure, rate, res);
	}
	private String check(String s) {
		if(s.equals("Fallow")) {
			return "1";
		}
		return s;
	}
	private double removeDoubleError(Spinner<Double> s) {
		double val = 0.0;
		try {
			val = s.getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			val = 0.0;
		}
		
		return val;
	}
	
	private String removeStringError(ComboBox<String> c)  {
		String val = "";
		try {
			val = c.getValue();
			if(val == null)val = "Fallow";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			val = "Fallow";
		}
		
		return val;
	}
	private void populateAEZ() {
		for(int i = 1; i <= 30; i++) {
			aez.getItems().add(Integer.toString(i));
		}
		//aez.getStyleClass().add("combo-box-baseRed");
	}
	private void populateAllManure() {
		ArrayList<String> manures = db.getAllManure();
		for(String manure : manures) {
			rabiManure.getItems().add(manure);
			k1Manure.getItems().add(manure);
			k2Manure.getItems().add(manure);
		}
		
	}
	private void populateAllVariety() {
		ArrayList<String> varieties = db.getAllVariety();
		for(String variety : varieties) {
			rabi.getItems().add(variety);
			k1.getItems().add(variety);
			k2.getItems().add(variety);
		}
		
	}
	private void populateAllLandType() {
		String [] landTypes = {"HL", "MHL", "MLL", "LL", "VLL"};
		for(String land : landTypes) {
			landType.getItems().add(land);
		}
	}
	
	private SpinnerValueFactory<Double> createValueRangeInfinity(){
		return new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000000.0, 0.0, 10.0);
	}
	private SpinnerValueFactory<Double> createValueRange100(){
		return new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100.0, 0.0, 1.0);
	}
	
	private void populateAllSpinner() {
		
        rabiRate.setValueFactory(createValueRangeInfinity());
        k1Rate.setValueFactory(createValueRangeInfinity());
        k2Rate.setValueFactory(createValueRangeInfinity());
        addActionListenerToSpinner(rabiRate);
        addActionListenerToSpinner(k1Rate);
        addActionListenerToSpinner(k2Rate);
        
        rabiYield.setValueFactory(createValueRangeInfinity());
        k1Yield.setValueFactory(createValueRangeInfinity());
        k2Yield.setValueFactory(createValueRangeInfinity());
        addActionListenerToSpinner(rabiYield);
        addActionListenerToSpinner(k1Yield);
        addActionListenerToSpinner(k2Yield);
        
        rabiN.setValueFactory(createValueRangeInfinity());
        k1N.setValueFactory(createValueRangeInfinity());
        k2N.setValueFactory(createValueRangeInfinity());
        addActionListenerToSpinner(rabiN);
        addActionListenerToSpinner(k1N);
        addActionListenerToSpinner(k2N);
        
        rabiP.setValueFactory(createValueRangeInfinity());
        k1P.setValueFactory(createValueRangeInfinity());
        k2P.setValueFactory(createValueRangeInfinity());
        addActionListenerToSpinner(rabiP);
        addActionListenerToSpinner(k1P);
        addActionListenerToSpinner(k2P);
        
        rabiK.setValueFactory(createValueRangeInfinity());
        k1K.setValueFactory(createValueRangeInfinity());
        k2K.setValueFactory(createValueRangeInfinity());
        addActionListenerToSpinner(rabiK);
        addActionListenerToSpinner(k1K);
        addActionListenerToSpinner(k2K);
        
        rabiResidue.setValueFactory(createValueRange100());
        k1Residue.setValueFactory(createValueRange100());
        k2Residue.setValueFactory(createValueRange100());
        addActionListenerToSpinner(rabiResidue);
        addActionListenerToSpinner(k1Residue);
        addActionListenerToSpinner(k2Residue);
	}
	private void addActionListenerToSpinner(Spinner<Double> spinner) {
		spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			  if (!newValue) {
			    spinner.increment(0); // won't change value, but will commit editor
			   // onAction(null);
			  }
			  
			});
		spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
			if (!"".equals(newValue)) {
				// System.out.println("spiinerrrr");
				// System.out.println("Hello World");
				spinner.commitValue();
				onAction(null);
			}
		});
		
	}
}

