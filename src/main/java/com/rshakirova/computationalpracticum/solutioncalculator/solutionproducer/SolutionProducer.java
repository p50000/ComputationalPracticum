package com.rshakirova.computationalpracticum.solutioncalculator.solutionproducer;

import java.util.ArrayList;
import java.util.List;

public abstract class SolutionProducer {

    public static final String BIG_STEP_EXCEPTION = "Cannot calculate, step is greater than 1";
    public static final String DISCONTINUITY_EXCEPTION = "The approximation includes the point of discontinuity";
    public static final String NEGATIVE_LOG_EXCEPTION = "Logarithm can't be negative at x = %f, y = %f";
    public static final String NO_CALCULATION = "Initial approximation function not calculated!";
    public static final String WRONG_DOMAIN = "N0 cannot be larger than N";

    public static final double EPS = 0.01;
    public static final int ONE = 1;

    protected List<Double> xSeries;

    protected List<Double> ySeries;

    protected String name;

    protected boolean isCalculated;

    protected List<Double> discontinuityPoints;
    protected double x0;
    protected double X;
    protected double y0;
    protected double N;
    protected double h;

    public SolutionProducer() {
        xSeries = new ArrayList<>();
        ySeries = new ArrayList<>();
        discontinuityPoints = List.of(0.0);
    }

    protected Double f(double x, double y) throws Exception {
        if ((x + y)/x < 0) {
            throw new Exception(String.format(NEGATIVE_LOG_EXCEPTION, x, y));
        }
        return (1 + y/x) * Math.log(1 + y/x) + y/x;
    };

    protected void fillXSeries(int n, double x0, double h) throws Exception {
        if (h > ONE) {
            throw new Exception(BIG_STEP_EXCEPTION);
        }

        if (discontinuityPoints.stream().anyMatch(point -> Math.abs(point - x0) < EPS)) {
            throw new Exception(DISCONTINUITY_EXCEPTION);
        }
        xSeries.add(x0);
        for (int i = 1; i < n + 1; i++) {
            xSeries.add(xSeries.get(i - 1) + h);
            Double thisX = xSeries.get(i);
            if (discontinuityPoints.stream().anyMatch(point -> Math.abs(point - thisX) < EPS)) {
                throw new Exception(DISCONTINUITY_EXCEPTION);
            }
        }
    }

    protected abstract void calculateYSeries(int n, double y0, double h) throws Exception;

    public void calculateSolution(int n, double x0, double y0, double X) throws Exception {
        this.N = n;
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;

        this.h = (X - x0)/n;
        if (h > ONE) {
            throw new Exception(BIG_STEP_EXCEPTION);
        }
        xSeries = new ArrayList<>();
        fillXSeries(n, x0, this.h);

        ySeries = new ArrayList<>();
        calculateYSeries(n, y0, this.h);

        isCalculated = true;
    }

    public String getName() {
        return name;
    }

    public List<Double> getXSeries() {
        return xSeries;
    }

    public List<Double> getYSeries() {
        return ySeries;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public double getX() {
        return X;
    }

    public double getH() {
        return h;
    }

    public void isAtDiscontinuityCheck(double x) throws Exception {
        if (discontinuityPoints.stream().anyMatch(point -> Math.abs(point - x) < EPS)) {
            throw new Exception(DISCONTINUITY_EXCEPTION);
        }
    }
}
