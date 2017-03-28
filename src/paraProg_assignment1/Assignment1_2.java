package paraProg_assignment1;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Assignment1_2 {

	public static void main(String[] args) {

		int dim = 10000;
		double[][] myMatrix = MatrixVectorMultiplication.getTestMatrix(dim);
		double[] myVector = MatrixVectorMultiplication.getTestVector(dim);
		double[] result = new double[dim];

		MatrixVectorMultiplication.printVector(myVector);
		ForkJoinPool pool = new ForkJoinPool();
		MatrixVectorMultiplication.doIt(pool, myMatrix, myVector, result);

		MatrixVectorMultiplication.printVector(result);

	}

}

class MatrixVectorMultiplication extends RecursiveAction {

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

		if (length != 1) {
			int split = length >> 1;
			MatrixVectorMultiplication f1 = new MatrixVectorMultiplication(matrix, vector, result, startIndex, split);
			f1.fork();
			MatrixVectorMultiplication f2 = new MatrixVectorMultiplication(matrix, vector, result, startIndex + split,
					length - split);
			f2.compute();
			f1.join();
		} else {
			System.out.println("StartIndex: " + startIndex);
			double res = 0.0;
			for (int i = 0; i < vector.length; i++)
				res += (matrix[startIndex][i] * vector[startIndex]);
			result[startIndex] = res;
		}
	}

	public static void doIt(ForkJoinPool pool, final double[][] matrix, final double[] vector, final double[] result) {
		pool.invoke(new MatrixVectorMultiplication(matrix, vector, result, 0, vector.length));

	}

	public static double[][] getTestMatrix(int dim) {

		double[][] myMatrix = new double[dim][dim];

		for (int i = 0; i < myMatrix.length; i++)
			for (int j = 0; j < myMatrix.length; j++)
				myMatrix[i][j] = 1.0;

		return myMatrix;
	}

	public static double[] getTestVector(int dim) {

		double[] myVector = new double[dim];
		for (int i = 0; i < myVector.length; i++)
			myVector[i] = i;

		return myVector;
	}

	public static void printVector(double[] vector) {
		for (int i = 0; i < vector.length; ++i) {
			System.out.println(vector[i]);
		}
	}

}
