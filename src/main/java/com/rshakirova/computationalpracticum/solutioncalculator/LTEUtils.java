package com.rshakirova.computationalpracticum.solutioncalculator;

import java.util.ArrayList;
import java.util.List;

import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.AnalyticalSolutionProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.NumericalSolutionProducer;

import static com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.SolutionProducer.NO_CALCULATION;

public class LTEUtils {

    public static List<Double> calculateLte(AnalyticalSolutionProducer analyticalSolutionProducer,
                                            NumericalSolutionProducer numericalSolutionProducer) throws Exception {
        if (!analyticalSolutionProducer.isCalculated() || !numericalSolutionProducer.isCalculated()) {
            throw new Exception(NO_CALCULATION);
        }
        var xSeries = numericalSolutionProducer.getXSeries();
        var exactYSeries =  analyticalSolutionProducer.getYSeries();
        int n = xSeries.size() - 1;
        double h = numericalSolutionProducer.getH();

        ArrayList<Double> lteSeries = new ArrayList<>();
        lteSeries.add(0.0);
        for(int i = 1; i < n + 1; i++) {
            lteSeries.add(Math.abs(exactYSeries.get(i) - numericalSolutionProducer.recurrentFunction(xSeries.get(i - 1), exactYSeries.get(i - 1), h)));
        }
        return lteSeries;
    }

}
