import ParallelProg.Assignment1_2.MatrixVectorMultiplication;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

public class Assignment1_2Test {

    final static int dimension = 3;
    final static int par = 4;

    @Test
    public void MatrixVectorMultiplicationTest() {
        // arrange
        double[][] matrix = MatrixVectorMultiplication.getTestMatrix(dimension);
        double[] vector = MatrixVectorMultiplication.getTestVector(dimension);
        double[] result = new double[dimension];

        ForkJoinPool pool = new ForkJoinPool(par);

        // act
        MatrixVectorMultiplication.doIt(pool, matrix, vector, result);
        double [] seqResult=  seqMatrixVectorMulitplication(matrix,vector);

        // assert
        Assert.assertArrayEquals(seqResult, result,0.001);

        System.out.println("Ausgangsmatrix");
        MatrixVectorMultiplication.printMatrix(matrix);
        System.out.println();
        System.out.println("Ausgangsvector");
        MatrixVectorMultiplication.printVector(vector);
        System.out.println();
        System.out.println("Ergebnisvector");
        MatrixVectorMultiplication.printVector(result);

    }

    private double[] seqMatrixVectorMulitplication(double[][] matrix, double[] vector) {

        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

}
