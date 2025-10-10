package com.foodapp.service;

import java.util.List;


public class AnalyticsService<T extends Number> {

 
    public double calculateTotal(List<T> values) {
        double sum = 0;
        for (T v : values) {
            sum += v.doubleValue();
        }
        return sum;
    }

    // Calculate average
    public double calculateAverage(List<T> values) {
        if (values.isEmpty()) return 0;
        return calculateTotal(values) / values.size();
    }
}
