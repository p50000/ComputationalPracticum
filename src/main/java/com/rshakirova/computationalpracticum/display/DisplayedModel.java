package com.rshakirova.computationalpracticum.display;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rshakirova.computationalpracticum.solutioncalculator.GTEUtils;
import com.rshakirova.computationalpracticum.solutioncalculator.LTEUtils;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.AnalyticalSolutionProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.EulerSolutionProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.ImprovedEulerProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.NumericalSolutionProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.RungeKuttaProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.SolutionProducer;
import javafx.scene.chart.XYChart;

public class DisplayedModel {
    ArrayList<SolutionProducer> solutions;

    ArrayList<List<Double>> gteSeries;
    List<Double> xForGte;

    Boolean isGteCalculated;

    ArrayList<XYChart.Series<Number, Number>> series;

    Set<Integer> displayedIds;

    public DisplayedModel() {
        this.solutions =
                new ArrayList<>();
        solutions.add(new AnalyticalSolutionProducer());
        solutions.add(new EulerSolutionProducer());
        solutions.add(new ImprovedEulerProducer());
        solutions.add(new RungeKuttaProducer());

        this.series = new ArrayList<>();
        addSeries("Exact solution");
        addSeries("Euler");
        addSeries("Improved Euler");
        addSeries("Runge-Kutta");
        displayedIds = new HashSet<>();

        this.gteSeries = new ArrayList<>();
        this.xForGte = new ArrayList<>();
        for (int i = 0; i < solutions.size(); i++) {
            gteSeries.add(new ArrayList<>());
        }
        isGteCalculated = false;
    }

    public ArrayList<XYChart.Series<Number, Number>> getSeries() {
        return series;
    }

    private void addSeries(String name) {
        series.add(new XYChart.Series<>());
        series.get(series.size() - 1).setName(name);
    }

    public List<XYChart.Series<Number, Number>> producerToSeries() {
        clearAllSeries();
        List<XYChart.Series<Number, Number>> returnSeries = new ArrayList<>();
        for(int id : displayedIds) {
            SolutionProducer producer = solutions.get(id);
            var ySeries = producer.getYSeries();
            var xSeries = solutions.get(0).getXSeries();
            fillSeries(returnSeries, xSeries, id, ySeries);
        }
        return returnSeries;
    }

    public List<XYChart.Series<Number, Number>> getLteSeries() throws Exception {
        clearAllSeries();
        List<XYChart.Series<Number, Number>> returnSeries = new ArrayList<>();
        for(int id : displayedIds) {
            if (id == 0) continue;
            var ySeries = LTEUtils.calculateLte((AnalyticalSolutionProducer) solutions.get(0), (NumericalSolutionProducer) solutions.get(id));
            var xSeries = solutions.get(0).getXSeries();
            fillSeries(returnSeries, xSeries, id, ySeries);
        }
        return returnSeries;
    }

    public List<XYChart.Series<Number, Number>> getGteSeries() throws Exception {
        clearAllSeries();
        List<XYChart.Series<Number, Number>> returnSeries = new ArrayList<>();
        if (!isGteCalculated) return returnSeries;
        for(int id : displayedIds) {
            if (id == 0) continue;
            var ySeries = gteSeries.get(id);
            fillSeries(returnSeries, xForGte, id, ySeries);
        }
        return returnSeries;
    }

    private void fillSeries(List<XYChart.Series<Number, Number>> returnSeries, List<Double> xSeries, int id, List<Double> ySeries) {
        for (int i = 0; i < xSeries.size(); i++) {
            series.get(id).getData().add(new XYChart.Data<>(xSeries.get(i), ySeries.get(i)));
        }
        returnSeries.add(series.get(id));
    }

    public void getSolution(int n, double x0, double y0, double X) throws Exception {
        isGteCalculated = false;
        for (var producer : solutions) {
            producer.calculateSolution(n, x0, y0, X);
        }
    }

    public void getGte(int n0, int n) throws Exception {
        for (int i = 1; i < solutions.size(); i++) {
            var series = GTEUtils.calculateGte((AnalyticalSolutionProducer) solutions.get(0), (NumericalSolutionProducer) solutions.get(i), n0, n);
            xForGte =  series.get(0);
            gteSeries.set(i, series.get(1));
        }
        isGteCalculated = true;
    }

    private void clearAllSeries() {
        for (var thisSeries : series) {
            thisSeries.getData().clear();
        }
    }

    public Set<Integer> getDisplayedIds() {
        return displayedIds;
    }
}
