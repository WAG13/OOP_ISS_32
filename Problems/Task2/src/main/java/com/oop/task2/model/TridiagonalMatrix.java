package com.oop.task2.model;

import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class TridiagonalMatrix {
    private List<Double> a;
    private List<Double> b;
    private List<Double> c;
    private List<Double> f;

    public TridiagonalMatrix(List<Double> a, List<Double> b, List<Double> c, List<Double> f) throws Exception {
        checkMatrix(a,b,c,f);
    }

    public TridiagonalMatrix(TridiagonalMatrix tridiagonalMatrix){
        this.a = new ArrayList<>(tridiagonalMatrix.getA());
        this.b = new ArrayList<>(tridiagonalMatrix.getB());
        this.c = new ArrayList<>(tridiagonalMatrix.getC());
        this.f = new ArrayList<>(tridiagonalMatrix.getF());
    }

    public TridiagonalMatrix(File fileWithMatrix) throws Exception {
        List<Double> a = new ArrayList<>();
        List<Double> c = new ArrayList<>();
        List<Double> b = new ArrayList<>();
        List<Double> f = new ArrayList<>();

        Scanner myReader = new Scanner(fileWithMatrix);
        int i = 0;
        while (myReader.hasNextLine()) {
            String readedLine = myReader.nextLine();
            String[] splited = readedLine.split("\\s+");
            f.add(Double.parseDouble(splited[splited.length - 1]));
            if (i > 0) {
                a.add(Double.parseDouble(splited[0]));
                c.add(Double.parseDouble(splited[1]));
                if (myReader.hasNextLine()) {
                    b.add(Double.parseDouble(splited[2]));
                }
            } else {
                c.add(Double.parseDouble(splited[0]));
                b.add(Double.parseDouble(splited[1]));
            }

            i++;
        }
        myReader.close();

        checkMatrix(a,b,c,f);
    }

    private void checkMatrix(List<Double> a, List<Double> b, List<Double> c, List<Double> f) throws Exception {
        if(a == null || b == null || c == null)
            throw new Exception("Wrong input parameters");

        if(c.size() <= 1)
            throw new Exception("Wrong size of c");

        if(c.size() != f.size())
            throw new Exception("Wrong size of f");

        if(c.size() != (a.size() + 1))
            throw new Exception("Wrong size of a");

        if(c.size() != (b.size() + 1))
            throw new Exception("Wrong size of b");

        this.a = a;
        this.b = b;
        this.c = c;
        this.f = f;
    }
}
