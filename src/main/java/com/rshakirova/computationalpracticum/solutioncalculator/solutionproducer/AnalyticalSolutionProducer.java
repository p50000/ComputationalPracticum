package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

public class AnalyticalSolutionProducer extends SolutionProducer {
    public static final String NO_SOLUTION = "The solution does not exist for these initial values";

    protected double constant;

    protected Double calculateConstant() throws Exception {
        if (x0 * y0 < 0 && Math.abs(y0) >= Math.abs(x0)) {
            throw new Exception(NO_SOLUTION);
        }
        if (x0 == 0) {
            return 1.0;
        }
        return Math.log(1 + y0/x0)/x0;
    }

    public Double exactF(double x) throws Exception {
        return x * (Math.exp(constant * x) - 1);
    }

    @Override
    protected void calculateYSeries(int n, double y0, double h) throws Exception {
        constant = calculateConstant();
        ySeries.add(y0);
        for (int i = 1; i < n + 1; i++) {
            ySeries.add(exactF(xSeries.get(i)));
        }
    }
}
