import java.util.*;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class FM_path extends RecursiveTask<int[][]> {
    private int[][] dist;
    private int num_vertexs;

    public FM_path(int[][] grid) {
        this.num_vertexs = grid.length;
        dist = new int[this.num_vertexs][this.num_vertexs];

        for(int i = 0; i < this.num_vertexs; ++i) {
            for(int j = 0; j < this.num_vertexs; ++j) {
                this.dist[i][j] = grid[i][j];
            }
        }
    }

    protected class helper extends RecursiveAction {
        private int[][] temp;
        private int size;
        private int midpoint;
        private int left;
        private int right;

        public helper(int[][] result, int s, int m, int l, int r) {
            this.temp = result;
            this.size = s;
            this.midpoint = m;
            this.left = l;
            this.right = r;
        }

        protected class helper2 extends RecursiveAction {
            private int[][] temp;
            private int from;
            private int midpoint;
            private int left;
            private int right;

            public helper2(int[][] result, int f, int m, int l, int r) {
                this.temp = result;
                this.from = f;
                this.midpoint = m;
                this.left = l;
                this.right = r;
            }

            protected boolean shorter(){
                return (temp[from][midpoint] + temp[midpoint][left] < temp[from][left]);
            }

            public void compute() {
                if(this.left == this.right) {
                    if(shorter()) {
                        temp[from][left] = temp[from][midpoint] + temp[midpoint][left];
                    }
                } else {
                    int mid = (right - left) / 2;
                    helper2 left_half = new helper2(temp, from, midpoint, left, mid);
                    helper2 right_half = new helper2(temp, from, midpoint, (mid + 1), right);
                    invokeAll(left_half, right_half);
                }
            }
        }

        public void compute(){
            if(this.left == this.right) {
                helper2 rowOP = new helper2(temp, left, midpoint, (0), (size - 1));
                invokeAll(rowOP);
            } else {
                int mid = (right - left) / 2;
                helper left_half = new helper(temp, size, midpoint, left, mid);
                helper right_half = new helper(temp, size, midpoint, (mid + 1), right);
                invokeAll(left_half, right_half);
            }
        }
    }

    public int[][] compute() {
        for(int i = 0; i < num_vertexs; ++i) {
            for(int j = 0; j < num_vertexs; ++j) {
                helper temp = new helper(dist, num_vertexs, i, (0), (num_vertexs - 1));
                invokeAll(temp);
            }
        }
        return dist;
    }
}
