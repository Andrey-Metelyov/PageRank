package pagerank;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.err.println(Arrays.toString(args));
        double[][] L = {
                {0, 1. / 2., 1. / 3., 0, 0, 0},
                {1. / 3., 0, 0, 0, 1. / 2., 0},
                {1. / 3., 1. / 2., 0, 1., 0, 1. / 2.},
                {1. / 3., 0, 1. / 3., 0, 1. / 2., 1. / 2.},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 1. / 3., 0, 0, 0}
        };
        method2(L);
    }

    private static void method2(double[][] L) {
        double[] r = new double[6];
        for (int i = 0; i < r.length; i++) {
            r[i] = 1.00;
            r[i] = 100 * r[i] / 6;
        }
        Matrix m = new Matrix(L);
//        m.print(0, 3);

        Matrix r0 = new Matrix(r, 1).transpose();
//        r0.print(0, 3);

        Matrix r1 = m.times(r0);
//        r1.print(0, 3);

        double[] res = r1.getColumnPackedCopy();
        for (double x : res) {
            System.out.println(new DecimalFormat("#0.000").format(x));
        }
        System.out.println();

//        System.out.println("prevR.minus(nextR).normInf():");
//        System.out.println(r0.minus(r1).normInf());

        for (int i = 0; i < 10; i++) {
            r0 = r1;
            r1 = m.times(r0);
        }

        res = r1.getColumnPackedCopy();
        for (double x : res) {
            System.out.println(new DecimalFormat("#0.000").format(x));
        }
        System.out.println();

//        System.out.println("prevR.minus(nextR).normInf():");
//        System.out.println(r0.minus(r1).normInf());

        while (r0.minus(r1).normInf() > 0.01) {
            r0 = r1;
            r1 = m.times(r0);
        }

        res = r1.getColumnPackedCopy();
        for (double x : res) {
            System.out.println(new DecimalFormat("#0.000").format(x));
        }

//        System.out.println("prevR.minus(nextR).normInf():");
//        System.out.println(r0.minus(r1).normInf());
    }

    private static void method1(double[][] L) {

        Matrix m = new Matrix(L);
        m.print(0, 3);
        EigenvalueDecomposition eigen = m.eig();
        final double[] realPart = eigen.getRealEigenvalues();
        System.err.println(Arrays.toString(realPart));
        Matrix evectors = eigen.getV();
//        evectors.print(0, 3);
        int index = -1;
        for (int i = 0; i < realPart.length; i++) {
            if (Math.abs(realPart[i] - 1) < 1e-8) {
                index = i;
                break;
            }
        }
        double[] principalEvector = evectors.transpose().getArray()[index];
        System.err.println(Arrays.toString(principalEvector));

        double sum = 0.00;
        for (double value : principalEvector) {
            sum += value;
        }

        double[] res = new double[principalEvector.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = 100 * principalEvector[i] / sum;
        }

        for (double x : res) {
            System.out.println(new DecimalFormat("#0.000").format(x));
        }
    }
}
