import java.util.*;

public class graph<T> {
    private int[][] vertex;
    private ArrayList<T> edges;
    private int num_edges;
    private int num_vertex;
    private int[][] shortest_path;

    public graph() {
        this.vertex = null;
        this.edges = new ArrayList<T>();
        this.num_edges = 0;
        this.num_vertex = 0;
        this.shortest_path = null;
    }

    public void add_edge(T elem) {
        if(this.edges.contains(elem)) {
            return;
        }
        this.edges.add(elem);
        ++this.num_edges;
    }

    public void init_map() {
        this.vertex = new int[this.num_edges][this.num_edges];
        for(int i = 0; i < this.num_edges; ++i) {
            for(int j = 0; j < this.num_edges; ++j) {
                this.vertex[i][j] = 999999;
            }
        }
    }

    public void add_vertex(T src, T dest, int weight) {
        int y = this.edges.indexOf(src);
        int x = this.edges.indexOf(dest);
        this.vertex[y][x] = weight;
    }

    public void compute_path() {
        FM_path path = new FM_path(vertex);
        shortest_path = path.compute();
    }

    public int[][] FM(){
        return shortest_path;
    }
}
