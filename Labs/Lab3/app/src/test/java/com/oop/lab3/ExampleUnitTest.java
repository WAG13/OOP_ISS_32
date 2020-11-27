package com.oop.lab3;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import com.oop.lab3.dtw.DTW;

import java.util.Arrays;
import java.util.List;

public class ExampleUnitTest {

    @Test
    public void testDTWEqualGraphs() {
        float[] first = {1, 2, 3, 4, 3, 2, 1, 1, 1, 2};
        float[] second = {1, 2, 3, 4, 3, 2, 1, 1, 1, 2};
        DTW dtw =  new DTW();
        double distance = dtw.compute(first, second).getDistance();
        System.out.println(dtw.compute(first, second).getDistance());
        Assert.assertEquals(0, distance, Math.pow(10,-10));
    }

    @Test
    public void testDTWParalellGraphs() {
        float[] first = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        float[] second = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        DTW dtw =  new DTW();
        double distance = dtw.compute(first, second).getDistance();
        Assert.assertEquals(81, distance, Math.pow(10,-10));
    }

    @Test
    public void testDTW() {
        float[] first = {1, 2, 3, 4, 3, 2, 1, 1, 1, 2};
        float[] second = {0, 1, 1, 2, 3, 4, 3, 2, 1, 1};
        DTW dtw =  new DTW();
        double distance = dtw.compute(first, second).getDistance();
        System.out.println(dtw.compute(first, second).getDistance());
        Assert.assertEquals(0.15384, distance, Math.pow(10,-5));
    }
    
    @Test
    public void testDTWDifferentLength() {
        float[] first = {1, 2, 3, 4, 3, 2, 1};
        float[] second = {1, 2, 3, 4, 3, 2, 1, 1, 1, 2};
        DTW dtw =  new DTW();
        double distance = dtw.compute(first, second).getDistance();
        Assert.assertEquals(0.1, distance, Math.pow(10,-10));
    }
}