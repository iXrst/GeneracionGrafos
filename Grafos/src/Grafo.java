import java.util.*;
import java.awt.geom.Point2D;
import java.nio.file.*;


public class Grafo{
    private static HashMap<String, Set<String>> map = new HashMap<>();


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
    

