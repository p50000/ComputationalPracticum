package com.rshakirova.computationalpracticum;

import java.io.IOException;

import com.github.javafx.charts.zooming.ZoomManager;
import com.rshakirova.computationalpracticum.display.DisplayedModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

import static com.rshakirova.computationalpracticum.SolutionApplication.isGteWindow;

public class SolutionController {
    DisplayedModel model;

    @FXML
    public TextField inputY0;

    @FXML
    public ToggleButton gteButton;

    @FXML
    public ToggleButton lteButton;

    @FXML
    public ToggleGroup showGroup;

    @FXML
    public TextField inputN0;

    @FXML
    public TextField inputN_gte;

    @FXML
    public ToggleButton functionButton;

    @FXML
    public Button CalculationButton;

    @FXML
    public CheckBox isAnalyticalOn;

    @FXML
    public CheckBox isEulerOn;

    @FXML
    public CheckBox isImprovedEulerOn;

    @FXML
    public CheckBox isRungerKuttaOn;

    @FXML
    public TextField inputX0;

    @FXML
    public TextField inputX;

    @FXML
    public TextField inputN;

    @FXML
    public LineChart<Number, Number> graphChart;

    @FXML
    public StackPane chartsPane;

    @FXML
    public void initialize() {
        model = new DisplayedModel();
        isGteWindow = false;
        graphChart.getData().addAll(model.getSeries());
    }

    @FXML
    public void setIsAnalyticalOn(ActionEvent actionEvent) {
        if (isAnalyticalOn.isSelected()) {
            model.getDisplayedIds().add(0);
        } else {
            model.getDisplayedIds().remove(0);
        }
        updateGraphs();
    }

    @FXML
    public void calculateSolution(ActionEvent actionEvent) {
        try {
            model.getSolution(Integer.parseInt(inputN.getText()), Double.parseDouble(inputX0.getText()), Double.parseDouble(inputY0.getText()), Double.parseDouble(inputX.getText()));
            updateGraphs();
        } catch (Exception e) {
            showError(e);
        }
    }

    @FXML
    public void setEuler(ActionEvent actionEvent) {
        if (isEulerOn.isSelected()) {
            model.getDisplayedIds().add(1);
        } else {
            model.getDisplayedIds().remove(1);
        }
        updateGraphs();
    }

    @FXML
    public void setImprovedEuler(ActionEvent actionEvent) {
        if (isImprovedEulerOn.isSelected()) {
            model.getDisplayedIds().add(2);
        } else {
            model.getDisplayedIds().remove(2);
        }
        updateGraphs();
    }

    @FXML
    public void setRungerKutta(ActionEvent actionEvent) {
        if (isRungerKuttaOn.isSelected()) {
            model.getDisplayedIds().add(3);
        } else {
            model.getDisplayedIds().remove(3);
        }
        updateGraphs();
    }

    @FXML
    public void showFunction(ActionEvent actionEvent) {
        updateGraphs();
    }

    @FXML
    public void showLte(ActionEvent actionEvent) {
        updateGraphs();
    }

    @FXML
    public void showGte(ActionEvent actionEvent) {
        updateGraphs();
    }

    @FXML
    public void calculateGte(ActionEvent actionEvent) {
        try {
            model.getGte(Integer.parseInt(inputN0.getText()), Integer.parseInt(inputN_gte.getText()));
            updateGraphs();
        } catch (Exception e) {
            showError(e);
        }
    }

    private void updateGraphs() {
        graphChart.getData().clear();;
        if (gteButton.isSelected()) {
            try {
                new ZoomManager<>(chartsPane, graphChart, model.getGteSeries());
            } catch (Exception e) {
                showError(e);
            }
            return;
        }

        if (functionButton.isSelected()) {
            new ZoomManager<>(chartsPane, graphChart, model.producerToSeries());
        } else {
            try {
                new ZoomManager<>(chartsPane, graphChart, model.getLteSeries());
            } catch (Exception e) {
                showError(e);
            }
        }
    }

    private void showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}