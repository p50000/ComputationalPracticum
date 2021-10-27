package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

public abstract class NumericalSolutionProducer extends SolutionProducer {

    public abstract Double recurrentFunction(double x, double y, double h) throws Exception;

    @Override
    protected void calculateYSeries(int n, double y0, double h) throws Exception {
        ySeries.add(y0);
        for (int i = 1; i < n + 1; i++) {
            ySeries.add(recurrentFunction(xSeries.get(i - 1), ySeries.get(i - 1), h));
        }
    }
}
