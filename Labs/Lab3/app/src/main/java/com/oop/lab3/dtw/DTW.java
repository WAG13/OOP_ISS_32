package com.oop.lab3.dtw;

/** Dynamic Time Warping algorithm **/
public final class DTW {

    public static class Result {

        private int[][] mWarpingPath;
        private double  mDistance;

        public Result(int[][] pWarpingPath, double pDistance) {
            this.mWarpingPath = pWarpingPath;
            this.mDistance    = pDistance;
        }

        /* Getters */
        public int[][] getWarpingPath() { return this.mWarpingPath; }
        public double     getDistance() { return this.mDistance;    }
    }

    public DTW() { }

    public DTW.Result compute(final float[] pSample, final float[] pTemplate) {

        final int lN = pSample.length;
        final int lM = pTemplate.length;

        if(lN == 0 || lM == 0) {
            return new DTW.Result(new int[][]{}, Double.NaN);
        }
        // Define the Scalar Qualifier.
        int lK = 1;
        // Allocate the Warping Path. (Math.max(N, M) <= K < (N + M).
        final int[][]    lWarpingPath  = new int[lN + lM][2];
        // Declare the Local Distances.
        final double[][] lL            = new double[lN][lM];
        // Declare the Global Distances.
        final double[][] lG            = new double[lN][lM];
        // Declare the MinBuffer.
        final double[]   lMinBuffer = new double[3];

        int i, j;
        for(i = 0; i < lN; i++) {
            final float lSample = pSample[i];
            for(j = 0; j < lM; j++) {
                // Calculate the Distance between the Sample and the Template for this Index.
                lL[i][j] = this.getDistanceBetween(lSample, pTemplate[j]);
            }
        }

        // Initialize the Global.
        lG[0][0] = lL[0][0];

        for(i = 1; i < lN; i++) {
            lG[i][0] = lL[i][0] + lG[i - 1][0];
        }

        for(j = 1; j < lM; j++) {
            lG[0][j] = lL[0][j] + lG[0][j - 1];
        }

        for (i = 1; i < lN; i++) {
            for (j = 1; j < lM; j++) {
                // Accumulate the path.
                lG[i][j] = (Math.min(Math.min(lG[i-1][j], lG[i-1][j-1]), lG[i][j-1])) + lL[i][j];
            }
        }

        i = lWarpingPath[lK - 1][0] = (lN - 1);
        j = lWarpingPath[lK - 1][1] = (lM - 1);

        // Whilst there are samples to process...
        while ((i + j) != 0) {

            if(i == 0) { j -= 1; }
            else if(j == 0) { i -= 1; }
            else {
                // Update the contents of the MinBuffer.
                lMinBuffer[0] = lG[i - 1][j];
                lMinBuffer[1] = lG[i][j - 1];
                lMinBuffer[2] = lG[i - 1][j - 1];
                // Calculate the Index of the Minimum.
                final int lMinimumIndex = this.getMinimumIndex(lMinBuffer);

                final boolean lMinIs0 = (lMinimumIndex == 0);
                final boolean lMinIs1 = (lMinimumIndex == 1);
                final boolean lMinIs2 = (lMinimumIndex == 2);

                i -= (lMinIs0 || lMinIs2) ? 1 : 0;
                j -= (lMinIs1 || lMinIs2) ? 1 : 0;
            }

            lK++;
            lWarpingPath[lK - 1][0] = i;
            lWarpingPath[lK - 1][1] = j;
        }

        // Return the Result. (Calculate the Warping Path and the Distance.)
        return new DTW.Result(this.reverse(lWarpingPath, lK), ((lG[lN - 1][lM - 1]) / lK));
    }

    /** Changes the order of the warping path, in increasing order. */
    private int[][] reverse(int[][] pPath, int pK) {

        int[][] lPath = new int[pK][2];
        for(int i = 0; i < pK; i++) {
            // Update the Path.
            System.arraycopy(pPath[pK - i - 1], 0, lPath[i], 0, 2);
        }
        return lPath;
    }

    /** Distance between two points. */
    protected double getDistanceBetween(double p1, double p2) {
        return Math.pow((p1 - p2), 2);
    }

    /** Finds the index of the minimum element from the given array. */
    protected final int getMinimumIndex(final double[] pArray) {
        int    lIndex = 0;
        double lValue = pArray[0];
        for(int i = 1; i < pArray.length; i++) {
            if (pArray[i] < lValue){
                lValue = pArray[i];
                lIndex = i;
            }
        }
        return lIndex;
    }

}

