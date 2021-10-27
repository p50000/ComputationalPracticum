package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

public class EulerSolutionProducer extends NumericalSolutionProducer {
    @Override
    public Double recurrentFunction(double x, double y, double h) throws Exception {
        return y + h * f(x, y);
    }
}
