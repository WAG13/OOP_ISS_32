package com.oop.task2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

class TridiagonalMatrixTest {
    private static final List<Double> expectedA = new ArrayList<>(Arrays.asList(1.0, 3.0, 1.0, 1.0));
    private static final List<Double> expectedB = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
    private static final List<Double> expectedC = new ArrayList<>(Arrays.asList(2.0, 2.0, 4.0, 2.0, 3.0));
    private static final List<Double> expectedF = new ArrayList<>(Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0));

    @Test
    void createMatrix() throws Exception {
        TridiagonalMatrix matrix = new TridiagonalMatrix(expectedA, expectedB, expectedC, expectedF);

        assertEquals(expectedA, matrix.getA());
        assertEquals(expectedB, matrix.getB());
        assertEquals(expectedC, matrix.getC());
        assertEquals(expectedF, matrix.getF());
    }

    @Test
    void createMatrixFromMatrix() throws Exception {
        TridiagonalMatrix expectedMatrix = new TridiagonalMatrix(expectedA, expectedB, expectedC, expectedF);
        TridiagonalMatrix matrix = new TridiagonalMatrix(expectedMatrix);

        assertEquals(expectedMatrix.getA(), matrix.getA());
        assertEquals(expectedMatrix.getB(), matrix.getB());
        assertEquals(expectedMatrix.getC(), matrix.getC());
        assertEquals(expectedMatrix.getF(), matrix.getF());
    }

    @Test
    void createMatrixFromFile() throws Exception {
        TridiagonalMatrix matrix = new TridiagonalMatrix(new File("src\\test\\examples\\matrixExample.txt"));

        assertEquals(expectedA, matrix.getA());
        assertEquals(expectedC, matrix.getC());
        assertEquals(expectedB, matrix.getB());
        assertEquals(expectedF, matrix.getF());
    }

    @Test
    void createMatrixFromNotExistingFile(){
        Throwable thrown = assertThrows(Exception.class, () -> {
            TridiagonalMatrix matrix = new TridiagonalMatrix(new File("src\\test\\example\\matrixExample.txt"));
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createWrongInputMatrix(){
        Throwable exception = assertThrows(Exception.class, () -> {
            TridiagonalMatrix matrix = new TridiagonalMatrix(null, expectedB, expectedC, expectedF);
        });
        assertEquals("Wrong input parameters", exception.getMessage());

    }

    @Test
    void createWrongAInputMatrix(){
        List<Double> a = new ArrayList<>(expectedA);
        a.add(2.5);
        Throwable exception = assertThrows(Exception.class, () -> {
            TridiagonalMatrix matrix = new TridiagonalMatrix(a, expectedB, expectedC, expectedF);
        });
        assertEquals("Wrong size of a", exception.getMessage());

    }

    @Test
    void createWrongBInputMatrix(){
        List<Double> b = new ArrayList<>(expectedB);
        b.add(2.5);
        Throwable exception = assertThrows(Exception.class, () -> {
            TridiagonalMatrix matrix = new TridiagonalMatrix(expectedA, b, expectedC, expectedF);
        });
        assertEquals("Wrong size of b", exception.getMessage());
    }

    @Test
    void createWrongFInputMatrix(){
        List<Double> f = new ArrayList<>(expectedF);
        f.add(2.5);
        Throwable exception = assertThrows(Exception.class, () -> {
            TridiagonalMatrix matrix = new TridiagonalMatrix(expectedA, expectedB, expectedC, f);
        });
        assertEquals("Wrong size of f", exception.getMessage());
    }

}
