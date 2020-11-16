import java.util.*;
import java.awt.geom.Point2D;
import java.nio.file.*;


public class Grafo{
    private static HashMap<String, Set<String>> map = new HashMap<>();

    public static void main(String args[]) {
        // Grafo g1_1 = Grafo.genErdosRenyi(30, 15);
        // g1_1.toGVFile("genErdos30", g1_1.toString());
        // Grafo g1_2 = Grafo.genErdosRenyi(100, 50);
        // g1_2.toGVFile("genErdos100", g1_2.toString());
        // Grafo g1_3 = Grafo.genErdosRenyi(500, 250);
        // g1_3.toGVFile("genErdos500", g1_3.toString());

        // Grafo g2_1 = Grafo.genGeografico(30, 0.5);
        // g2_1.toGVFile("genGeografico30", g2_1.toString());
        // Grafo g2_2 = Grafo.genGeografico(100, 0.5);
        // g2_2.toGVFile("genGeografico100", g2_2.toString());
        // Grafo g2_3 = Grafo.genGeografico(500, 0.5);
        // g2_3.toGVFile("genGeografico500", g2_3.toString());

        // Grafo g3_1 = Grafo.genGilbert(30, .8);
        // g3_1.toGVFile("genGilbert30", g3_1.toString());
        // Grafo g3_2 = Grafo.genGilbert(100, .8);
        // g3_2.toGVFile("genGilbert100", g3_2.toString());
        // Grafo g3_3 = Grafo.genGilbert(500, .8);
        // g3_3.toGVFile("genGilbert500", g3_3.toString());

        // Grafo g4_1 = Grafo.genBarabasiAlbert(30, 10);
        // g4_1.toGVFile("genBarabasi30", g4_1.toString());
        // Grafo g4_2 = Grafo.genBarabasiAlbert(100, 10);
        // g4_2.toGVFile("genBarabasi100", g4_2.toString());
        // Grafo g4_3 = Grafo.genBarabasiAlbert(500, 10);
        // g4_3.toGVFile("genBarabasi500", g4_3.toString());
    }

    public void addNode(String n) {
        map.put(n, new HashSet<String>());
    }

    public void addEdge(String a, String b) {
        if (!map.containsKey(a)) {
            addNode(a);
        }
        if (!map.containsKey(b)) {
            addNode(b);
        }
        map.get(a).add(b);
    }

    public Set<String> getNodes() {
        return map.keySet();
    }

    public String toString(){
        StringBuilder res = new StringBuilder("graph abstract {\n");

        for(String node : map.keySet()){
            res.append("\t" + node.toString() + " -- {");
            for(String edge : map.get(node)){
                res.append(edge.toString() + " ");
            }
            res.append("}\n");
        }
        res.append("}");

        return (res.toString());
    }

    public void toGVFile(String name, String graph){
        Path path = Paths.get(name + ".gv");
        try {
            Files.writeString(path, graph);
        } catch (Exception e) {
        }
    }

    public int valueFrequency(String valor){
        int frequency = 0;
        for (Set<String> conexiones : map.values()){
            if(conexiones.contains(valor)){frequency++;}
        }
        return frequency;

    }

    // Grafos solicitados
    // public static Grafo genErdosRenyi(int n, int m, boolean dirigido, boolean
    // auto);
    public static Grafo genErdosRenyi(int n, int m) {
        Random r = new Random();
        Grafo g = new Grafo();
        for (int i = 0; i < n; i++) {
            g.addNode("" + i);
        }
        int i = 0;
        int n1 = 0;
        int n2 = 0;
        while(i<m) {
            n1 = r.nextInt(n);
            n2 = r.nextInt(n);
            while(n1 == n2) {
                n2 = r.nextInt(n);
            }
            g.addEdge("" + n1, "" + n2);
            i++;
        }
        return g;
    }

    // public static Grafo genGilbert(int n, double p, boolean dirigido, boolean
    // auto);
    public static Grafo genGilbert(int n, double p) {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("Probabilidad entre 0 y 1");
        Random r = new Random();
        Grafo g = new Grafo();
        for (int i = 0; i < n; i++) {
            g.addNode("" + i);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (r.nextDouble() <= p) {
                    g.addEdge("" + i, "" + j);
                }
            }
        }
        return g;
    }

    // public static Grafo genGeografico(int n, double r, boolean dirigido, boolean
    // auto);
    public static Grafo genGeografico(int n, double d) {
        Random r = new Random();
        Grafo g = new Grafo();
        Point2D[] xy = new Point2D[n];
        for (int i = 0; i < n; i++) {
            g.addNode("" + i);
            xy[i] = new Point2D.Double(r.nextDouble(), r.nextDouble());
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i != j) {
                    if (xy[i].distance(xy[j]) < d) {
                        g.addEdge("" + i, "" + j);
                    }
                }
            }
        }
        return g;
    }

    // public static Grafo genBarabasiAlbert(int n, double d, boolean dirigido,
    // boolean auto);
    public static Grafo genBarabasiAlbert(int n, double d) {
        Grafo g = new Grafo();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            g.addNode("" + i);
        }
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i != j) {
                    if (map.get(""+i).contains(""+j) || map.get(""+j).contains(""+i)){
                    }
                    else{
                        g.addEdge("" + i, "" + j);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j){
                    if((map.get("" + i).size() + g.valueFrequency("" + i) ) < d && (map.get(""+j).size() + g.valueFrequency(""+ j)) < d){
                        if(r.nextDouble() <= (1-(map.get(""+i).size()/d))){
                            if (map.get(""+i).contains(""+j) || map.get(""+j).contains(""+i)){
                            }
                            else{
                                if (Collections.frequency(map.values(), "" + i) < 4 && Collections.frequency(map.values(), "" + j) < 4)
                                g.addEdge(""+j, ""+i);
                            }
                        }
                    }
                }
            }
        }
        return g;
    }

    public Grafo() {
    }

    

} 
    

