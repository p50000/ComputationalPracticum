package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

public class ImprovedEulerProducer extends NumericalSolutionProducer {
    @Override
    public Double recurrentFunction(double x, double y, double h) throws Exception {
        return y + h*f(x + h/2, y + h/2 * f(x, y));
    }
}
