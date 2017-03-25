import java.util.*;
import java.util.Arrays.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;

public class matrix extends RecursiveAction {
    public int[][] first;
    public int[][] second;
    public int row;
    public int col;
    public int row2;
    public int col2;
    public int currR1;
    public int currR2;
    public int[][] temp;

    matrix (int[][] a, int[][] b, int i, int j, int k, int l, int left, int right, int[][] t) {
        first = a;
        second = b;
        row = i;
        col = j;
        row2 = k;
        col2 = l;
        currR1 = left;
        currR2 = right;
        temp = t;
    }

    class vertex_op extends RecursiveAction {
        public int[][] a;
        public int[][] b;
        public int row;
        public int col;
        public int row2;
        public int col2;
        public int currC1;
        public int currC2;
        public int currR;
        public int[][] temp;

        vertex_op(int[][] first, int[][] second, int i, int j, int k, int l, int left, int right, int CR, int[][] t) {
            a = first;
            b = second;
            row = i;
            col = j;
            row2 = k;
            col2 = l;
            temp = t;
            currC1 = left;
            currC2 = right;
            currR = CR;
        }

        @Override
        protected void compute() {
            if(currC1 == currC2) {
                for(int i = 0;i < this.col;i++) {
                    temp[currR][currC1] += a[currR][i] * b[i][currC1];
                }
            } else {
                vertex_op left = new vertex_op(this.a, this.b, this.row,
                        this.col, this.row2, this.col2, currC1, (currC1 + currC2)/2, currR, temp);
                vertex_op right = new vertex_op(this.a, this.b, this.row,
                        this.col, this.row2, this.col2, (currC1 + currC2)/2 + 1, currC2, currR, temp);
                invokeAll(left, right);
            }
        }
    }

    @Override
    protected void compute() {
        if(currR1 == currR2) {
            vertex_op op = new vertex_op(this.first, this.second, this.row, this.col,
                    this.row2, this.col2, 0, this.col2-1, currR1, this.temp);
            invokeAll(op);
        } else {
            matrix top = new matrix(this.first, this.second, this.row, this.col, this.row2, this.col2,
                    this.currR1, (this.currR1 + this.currR2)/2, this.temp);
            matrix botton = new matrix(this.first, this.second, this.row, this.col, this.row2, this.col2,
                    (this.currR1 + this.currR2)/2 + 1, this.currR2, this.temp);
            invokeAll(top, botton);
        }
    }
}

