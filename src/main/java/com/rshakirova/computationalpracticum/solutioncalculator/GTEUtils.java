package com.rshakirova.computationalpracticum.solutioncalculator;

import java.util.ArrayList;
import java.util.List;

import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.AnalyticalSolutionProducer;
import com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.NumericalSolutionProducer;

import static com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.SolutionProducer.BIG_STEP_EXCEPTION;
import static com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.SolutionProducer.NO_CALCULATION;
import static com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer.SolutionProducer.WRONG_DOMAIN;

public class GTEUtils {
    public static List<List<Double>> calculateGte(AnalyticalSolutionProducer analyticalSolutionProducer,
                                            NumericalSolutionProducer numericalSolutionProducer,
                                            int n0, int n) throws Exception {
        if(n0 > n) throw new Exception(WRONG_DOMAIN);

        ArrayList<Double> gteSeries = new ArrayList<>();
        ArrayList<Double> xSeries = new ArrayList<>();
        if (!analyticalSolutionProducer.isCalculated() || !numericalSolutionProducer.isCalculated()) {
            throw new Exception(NO_CALCULATION);
        }
        double X = analyticalSolutionProducer.getX();
        double x0 = analyticalSolutionProducer.getXSeries().get(0);
        double y0 = analyticalSolutionProducer.getYSeries().get(0);
        for (int ni = n0; ni < n + 1; ni++) {
            xSeries.add((double) ni);
            double h = (X - x0)/ni;

            if (h > 1) {
                throw new Exception(BIG_STEP_EXCEPTION);
            }
            double currentX = x0;
            double currentY = y0;
            double maxLte = 0.0;
            for (int i = 1; i < ni; i++) {
                analyticalSolutionProducer.isAtDiscontinuityCheck(currentX);

                maxLte = Math.max(
                        maxLte,
                        Math.abs(analyticalSolutionProducer.exactF(currentX + h) - numericalSolutionProducer.recurrentFunction(currentX, currentY, h))
                );
                currentY = analyticalSolutionProducer.exactF(currentX + h);
                currentX += h;
            }
            gteSeries.add(maxLte);
        }
        return List.of(xSeries, gteSeries);
    }
}
