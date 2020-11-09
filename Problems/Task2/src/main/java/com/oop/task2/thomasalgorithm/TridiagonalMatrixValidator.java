package com.oop.task2.thomasalgorithm;

import java.util.List;
import com.oop.task2.model.TridiagonalMatrix;

public class TridiagonalMatrixValidator {

    //Checks if matrix is diagonally dominant matrix
    public boolean isMatrixValid(TridiagonalMatrix matrix) {
        List<Double> modA = matrix.getA();
        List<Double> modC = matrix.getC();
        List<Double> modB = matrix.getB();
        listToAbsList(modA);
        listToAbsList(modC);
        listToAbsList(modB);

        if (modC.get(0) < modB.get(0) ||
                modC.get(modC.size() - 1) < modA.get(modA.size() - 1)) {
            return false;
        }

        for (int i = 1; i < modC.size() - 2; i++) {
            if (modC.get(i) < modA.get(i-1) + modB.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void listToAbsList(List<Double> list) {
        list.forEach(a -> Math.abs(a));
    }
}