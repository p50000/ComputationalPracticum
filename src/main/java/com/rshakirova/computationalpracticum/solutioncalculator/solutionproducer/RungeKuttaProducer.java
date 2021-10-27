package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

public class RungeKuttaProducer extends NumericalSolutionProducer {
    public Double k1(double x, double y) throws Exception {
        return f(x, y);
    }

    public Double k2(double x, double y, double h) throws Exception {
        return f(x + h/2, y + h * k1(x, y) / 2);
    }

    public Double k3(double x, double y, double h) throws Exception {
        return f(x + h/2, y + h * k2(x, y, h) / 2);
    }

    public Double k4(double x, double y, double h) throws Exception {
        return f(x + h, y + h * k3(x, y, h));
    }

    @Override
    public Double recurrentFunction(double x, double y, double h) throws Exception {
        return y + h/6 * (k1(x, y) + 2 * k2(x, y, h) + 2 * k3(x, y, h) + k4(x, y, h));
    }
}
