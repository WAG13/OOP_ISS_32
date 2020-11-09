package com.oop.task2.thomasalgorithm;

import com.oop.task2.model.TridiagonalMatrix;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TridiagonalMatrixSolverTest {

    public void checkResult (TridiagonalMatrix matrix, List<Double> result, double delta){

        List<Double> res = new ArrayList<>(result);
        List<Double> a = new ArrayList<>(matrix.getA());
        List<Double> b = new ArrayList<>(matrix.getB());
        List<Double> c = matrix.getC();
        List<Double> f = matrix.getF();

        a.add(0,0.0);
        b.add(0.0);
        res.add(0.0);

        for (int i = 0; i < f.size();i++){
            Double realF = res.get(i)*a.get(i) + res.get(i)*c.get(i) + res.get(i)*b.get(i);
            assertEquals(f.get(i), realF, delta);
        }

    }

    @Test
    void solveTridiagonalMatrix() throws Exception {
        //Given
        double delta = Math.pow(10,7);
        List<Double> a = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> c = new ArrayList<>(Arrays.asList(2.0, 2.0, 2.0, 2.0, 2.0));
        List<Double> b = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> f = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0));
        List<Double> expectedResult = new ArrayList<>(Arrays.asList(0.5, 0.0, 0.5, 0.0, 0.5));
        TridiagonalMatrix matrix = new TridiagonalMatrix(a, b, c, f);
        TridiagonalMatrixSolver tridiagonalMatrixSolver = new TridiagonalMatrixSolver();
        //When
        List<Double> result = tridiagonalMatrixSolver.solve(matrix);
        //Then
        
        checkResult(matrix,result,delta);
        
        Iterator<Double> it1 = expectedResult.iterator();
        Iterator<Double> it2 = result.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(), it2.next(), delta);
        }

    }

    @Test
    void solveTridiagonalMatrixFromFile() throws Exception {
         //Given
        double delta = Math.pow(10,7);
        TridiagonalMatrix matrix = new TridiagonalMatrix(new File("src\\test\\examples\\matrixExample.txt"));
        TridiagonalMatrixSolver tridiagonalMatrixSolver = new TridiagonalMatrixSolver();
        //When
        List<Double> result = tridiagonalMatrixSolver.solve(matrix);
        //Then
        checkResult(matrix,result,delta);
    }

}