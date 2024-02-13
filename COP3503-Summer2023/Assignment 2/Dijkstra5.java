import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Dijkstra5 {
    private int verticesNumber;
    private int[] dis;
    private boolean[] visited;
    private Vertex[] vertices;
    private List<List<Edge>> store;

    public Dijkstra5(int verticesNumber) {
        this.verticesNumber = verticesNumber;
        this.dis = new int[verticesNumber + 1];
        this.visited = new boolean[verticesNumber + 1];
        this.vertices = new Vertex[verticesNumber + 1];
        this.store = new ArrayList<>(verticesNumber + 1);
        //initializes distances to maximum integer value
        for (int i = 0; i <= verticesNumber; i++) {
            store.add(new ArrayList<>());
            dis[i] = Integer.MAX_VALUE;
            vertices[i] = new Vertex(i, Integer.MAX_VALUE, -1);
        }
    }

    public void addEdge(int from, int to, int weight) {
        //adds edges in both directions
        store.get(from).add(new Edge(to, weight));
        store.get(to).add(new Edge(from, weight));
    }

    private void compute(int source) {
        //initializes the priority queue
        PriorityQueue<Vertex> pq = new PriorityQueue<>(verticesNumber, Comparator.comparingInt(v -> v.distance));
        pq.add(new Vertex(source, 0, -1));
        //sets source distance to -1
        dis[source] = -1;

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();
            int currentVertex = current.vertex;

            if (visited[currentVertex]) {
                continue;
            }

            visited[currentVertex] = true;

            for (Edge edge : store.get(currentVertex)) {
                int newDistance = current.distance + edge.weight;

                if (newDistance < dis[edge.to]) {
                    //updates the distance value
                    dis[edge.to] = newDistance;
                    //updates the parent value
                    vertices[edge.to].parent = currentVertex;
                    pq.add(new Vertex(edge.to, dis[edge.to], currentVertex));
                }
            }
        }
    }

    public int[] getDistances() {
        return dis;
    }

    public static void main(String[] args) throws IOException {
        //initializes input file
        File inputFile = new File("cop3503-asn2-input.txt");
        Scanner scan = new Scanner(inputFile);

        //takes number of vertices, source vertex, and number of edges from the file
        int verticesNumber = scan.nextInt();
        int source = scan.nextInt() - 1;
        int edgeNum = scan.nextInt();

        Dijkstra5 solve = new Dijkstra5(verticesNumber);

        //takes in vertex values from the input file and stores them
        for(int i = 0; i < edgeNum; i++) {
            int scan1 = scan.nextInt() - 1;
            int scan2 = scan.nextInt() - 1;
            int scan3 = scan.nextInt();

            solve.addEdge(scan1, scan2, scan3);
        }

        solve.compute(source);
        //stores distances of the edges
        int[] distances = solve.getDistances();

        for (int i = 0; i < verticesNumber; i++) {
            int distance = distances[i];
            int parent = solve.vertices[i].parent;
            System.out.println((i + 1) + " " + (distance == Integer.MAX_VALUE ? -1 : distance) + " " + (parent == -1 ? -1 : parent + 1));
        }
        //allows output to file
        BufferedWriter output = new BufferedWriter(new FileWriter("cop3503-ans2-output-Hull-Zachary.txt"));
        output.write(String.valueOf(verticesNumber));
        output.newLine(); 

        //prints to file
        for (int i = 0; i < verticesNumber; i++) {
            int distance = distances[i];
            int parent = solve.vertices[i].parent;
            output.write((i + 1) + " " + (distance == Integer.MAX_VALUE ? -1 : distance) + " " + (parent == -1 ? -1 : parent + 1));
            output.newLine();
        }

        scan.close();
        output.close();
    }
}
class Vertex {
    int vertex;
    int distance;
    int parent;

    public Vertex(int vertex, int distance, int parent) {
        this.vertex = vertex;
        this.distance = distance;
        this.parent = parent;
    }
}
    
class Edge {
    int to;
    int weight;
    
    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}