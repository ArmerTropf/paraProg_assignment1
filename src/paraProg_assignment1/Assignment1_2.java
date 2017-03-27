package paraProg_assignment1_2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Assignment1_2 {

	public static void main(String[] args) {
		double[][] myMatrix = new double[2][2];
		myMatrix[0][0] = 1;
		myMatrix[0][1] = 2;
		myMatrix[1][0] = 3;
		myMatrix[1][1] = 4;

		double[] myVector = new double[2];
		myVector[0] = 1;
		myVector[1] = 2;

		// 1
		// 2
		// 1 2 1*1+2*2 = 5
		// 3 4 3*1+4*2 = 11

		double[] y = new double[myVector.length];

		// for (int i = 0; i < myVector.length; i++)
		// for (int j = 0; j < myMatrix[0].length; j++)
		// y[i] += myMatrix[i][j] * myVector[j];
		//
		// System.out.println(y[0]);
		// System.out.println(y[1]);

		//MatrixVectorMultiplication myMulti = new MatrixVectorMultiplication(myMatrix, myVector, y, 0, 0);
		MatrixVectorMultiplication myMulti = new MatrixVectorMultiplication(MatrixVectorMultiplication.getTestMatrix(2), MatrixVectorMultiplication.getTestVector(2), y, 0, 3);

		double[][] myTest = myMulti.getTestMatrix(2);
		System.out.println(myTest[0][0]);
		System.out.println(myTest[0][1]);
		System.out.println(myTest[1][0]);
		System.out.println(myTest[1][1]);

		// myMulti.compute();

		// ForkJoinPool myJoinPool = new ForkJoinPool();

	}

}

class MatrixVectorMultiplication extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	double[][] matrix;
	double[] vector;
	double[] result;
	int startIndex;
	int length;

	public MatrixVectorMultiplication(final double[][] matrix, final double[] vector, final double[] result,
			final int startIndex, final int length) {

		this.matrix = matrix;
		this.vector = vector;
		this.result = result;
		this.startIndex = startIndex;
		this.length = length;
	}

	@Override
	protected void compute() {

		for (int i = 0; i < vector.length; i++)
			for (int j = 0; j < matrix[0].length; j++) {
				result[i] += matrix[i][j] * vector[j];

			}

		System.out.println(result[0]);
		System.out.println(result[1]);
	}

	public static double[][] getTestMatrix(int dim) {

		double[][] myMatrix = new double[dim][dim];

		for (int i = 0; i < myMatrix.length; i++)
			for (int j = 0; j < myMatrix.length; j++)
				myMatrix[i][j] = (Math.random() * 100);

		return myMatrix;
	}

	public static double[] getTestVector(int dim) {

		double[] myVector = new double[dim];
		for (int i = 0; i < myVector.length; i++)
			myVector[i] = (Math.random() * 100);

		return myVector;
	}

	void doIt(ForkJoinPool pool, final double[][] matrix, final double[] vector, final double[] result) {

		 ForkJoinPool myJoinPool = new ForkJoinPool(2);
		 myJoinPool.submit(this);
	}

}
