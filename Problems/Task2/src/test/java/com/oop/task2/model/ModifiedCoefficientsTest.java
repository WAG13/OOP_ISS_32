package com.oop.task2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModifiedCoefficientsTest {
    @Test
    void createModifiedCoefficients() {
        //Given
        Double[] expectedAlpha = {1.0, 2.0};
        Double[] expectedBeta = {3.0, 4.0};
        //When
        ModifiedCoefficients coefficients = new ModifiedCoefficients(expectedAlpha, expectedBeta);
        //Then
        assertEquals(expectedAlpha, coefficients.getAlpha());
        assertEquals(expectedBeta, coefficients.getBeta());
    }

    @Test
    void setNewAlphaModifiedCoefficients() {
        //Given
        Double[] expectedAlpha = {1.0, 2.0};
        Double[] expectedBeta = {3.0, 4.0};
        Double[] newAlpha = {8.0, 9.0};
        ModifiedCoefficients coefficients = new ModifiedCoefficients(expectedAlpha, expectedBeta);
        //When
        coefficients.setAlpha(newAlpha);
        //Then
        assertEquals(newAlpha, coefficients.getAlpha());
    }

    @Test
    void setNewBetaModifiedCoefficients() {
        //Given
        Double[] expectedAlpha = {1.0, 2.0};
        Double[] expectedBeta = {3.0, 4.0};
        Double[] newBeta = {5.0, 6.0};
        ModifiedCoefficients coefficients = new ModifiedCoefficients(expectedAlpha, expectedBeta);
        //When
        coefficients.setBeta(newBeta);
        //Then
        assertEquals(newBeta, coefficients.getBeta());
    }
}
