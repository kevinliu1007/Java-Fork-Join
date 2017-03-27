/**
 * Created by kevinliu1007 on 3/22/17.
 */
public class text {
    public static void main(String[] args){
        graph<Integer> g = new graph<Integer>();

        for(int i = 0; i < 10; ++i){
            g.add_edge(i);
        }

        g.init_map();

        for(int i = 0; i < 7; ++i) {
            g.add_vertex(i, (i+1), 100);
        }

        g.compute_path();
    }
}
