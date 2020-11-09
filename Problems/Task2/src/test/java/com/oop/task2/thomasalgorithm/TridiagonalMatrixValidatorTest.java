package com.oop.task2.thomasalgorithm;

import com.oop.task2.model.TridiagonalMatrix;
import com.oop.task2.thomasalgorithm.TridiagonalMatrixValidator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TridiagonalMatrixValidatorTest {

    @Test
    void matrixIsValid() throws Exception {
        //Given
        List<Double> validA = new ArrayList<>(Arrays.asList(1.0, 3.0, 1.0, 1.0));
        List<Double> validC = new ArrayList<>(Arrays.asList(2.0, 2.0, 4.0, 2.0, 3.0));
        List<Double> validB = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> validF = new ArrayList<>(Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0));
        //When
        TridiagonalMatrixValidator validator = new TridiagonalMatrixValidator();
        TridiagonalMatrix validMatrix = new TridiagonalMatrix(validA, validB, validC, validF);
        //Then
        assertTrue(validator.isMatrixValid(validMatrix));
    }

    @Test
    void matrixIsNotValid() throws Exception {
        //Given
        List<Double> validA = new ArrayList<>(Arrays.asList(1.0, 3.0, 1.0, 1.0));
        List<Double> validC = new ArrayList<>(Arrays.asList(2.0, 2.0, 4.0, 2.0, 3.0));
        List<Double> notValidB = new ArrayList<>(Arrays.asList(10.0, 10.0, 10.0, 10.0));
        List<Double> validF = new ArrayList<>(Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0));
        //When
        TridiagonalMatrixValidator validator = new TridiagonalMatrixValidator();
        TridiagonalMatrix notValidMatrix = new TridiagonalMatrix(validA, notValidB, validC, validF);
        //Then
        assertFalse(validator.isMatrixValid(notValidMatrix));
    }
}