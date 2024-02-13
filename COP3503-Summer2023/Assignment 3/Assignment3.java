//Zachary Hull
//COP3503 assignment 3
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Assignment3 {
    private int verticesNumber;
    private int[] disBF;
    private boolean[] visited;
    private Vertex[] vertices;
    private List<List<Edge>> store;
    private int[][] disFW;

    public Assignment3(int verticesNumber) {
        this.verticesNumber = verticesNumber;
        this.disBF = new int[verticesNumber + 1];
        this.visited = new boolean[verticesNumber + 1];
        this.vertices = new Vertex[verticesNumber + 1];
        this.store = new ArrayList<>(verticesNumber + 1);
        this.disFW = new int[verticesNumber + 1][verticesNumber + 1];
        //initializes distances to maximum integer value
        for (int i = 0; i <= verticesNumber; i++) {
            store.add(new ArrayList<>());
            disBF[i] = Integer.MAX_VALUE;
            vertices[i] = new Vertex(i, Integer.MAX_VALUE, -1);
        }
        for (int i = 0; i <= verticesNumber; i++) {
            for(int j = 0; j <= verticesNumber; j++){
                store.add(new ArrayList<>());
                disFW[i][j] = Integer.MAX_VALUE;
            }
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
        //sets source distance to 0
        disBF[source] = 0;

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();
            int currentVertex = current.vertex;

            if (visited[currentVertex]) {
                continue;
            }

            visited[currentVertex] = true;

            for (Edge edge : store.get(currentVertex)) {
                int newDistance = current.distance + edge.weight;

                if (newDistance < disBF[edge.to]) {
                    //updates the distance value
                    disBF[edge.to] = newDistance;
                    //updates the parent value
                    vertices[edge.to].parent = currentVertex;
                    pq.add(new Vertex(edge.to, disBF[edge.to], currentVertex));
                }
            }
        }
    }

    
    
    public int[] getDistances() {
        return disBF;
    }
    public int[][] getFWDistance(){
        return disFW;
    }

    public static void main(String[] args) throws IOException {
        //initializes input file
        File inputFile = new File("cop3503-asn3-input.txt");
        Scanner scan = new Scanner(inputFile);
        
        //takes number of vertices, source vertex, and number of edges from the file
        int verticesNumber = scan.nextInt();
        int source = scan.nextInt() - 1;
        int edgeNum = scan.nextInt();
        
        Assignment3 solve = new Assignment3(verticesNumber);
        
        //takes in vertex values from the input file and stores them
        for(int i = 0; i < edgeNum; i++) {
            int scan1 = scan.nextInt() - 1;
            int scan2 = scan.nextInt() - 1;
            int scan3 = scan.nextInt();
            
            solve.addEdge(scan1, scan2, scan3);
        }
        
        /******************
         ***Bellman-Ford***
         ******************/

        solve.compute(source);
        //stores distances of the edges
        int[] distances = solve.getDistances();

        //allows output to file
        BufferedWriter outputBF = new BufferedWriter(new FileWriter("cop3503-asn3-output-Hull-Zachary-bf.txt"));
        outputBF.write(String.valueOf(verticesNumber));
        outputBF.newLine(); 

        //prints to file
        for (int i = 0; i < verticesNumber; i++) {
            int distance = distances[i];
            int parent = solve.vertices[i].parent;
            outputBF.write((i + 1) + " " + (distance == Integer.MAX_VALUE ? -1 : distance) + " " + (parent == 0 ? 1 : parent + 1));
            outputBF.newLine();
        }
        
        outputBF.close();
        
        /*******************
         ***Floyd-Warshall**
         ******************/

        //initializes new scanner
        Scanner scanNew = new Scanner(inputFile);
        
        //skips the first three lines of the input file
        scanNew.nextInt();
        scanNew.nextInt();
        scanNew.nextInt();
        
        //initialize distance array
        int[][] disFW = solve.getFWDistance();

        for(int i = 1; i <= verticesNumber; i++){
            for(int j = 1; j <= verticesNumber; j++){
                if (i == j) {
                    disFW[i][j] = 0;
                } else {
                    disFW[i][j] = Integer.MAX_VALUE;
                }
            }
        }

    for(int i = 0; i < edgeNum; i++) {
            int scan1 = scanNew.nextInt();
            int scan2 = scanNew.nextInt();
            int scan3 = scanNew.nextInt();
            
            disFW[scan1][scan2] = scan3;
            disFW[scan2][scan1] = scan3;
        }        
        
        //fills out the rest of the array
        for(int k = 1; k <= verticesNumber; k++){
            for(int i = 1; i <= verticesNumber; i++){
                for(int j = 1; j <= verticesNumber; j++){
                    if(disFW[i][k] != Integer.MAX_VALUE && disFW[k][j] != Integer.MAX_VALUE && disFW[i][k] + disFW[k][j] < disFW[i][j]){
                        disFW[i][j] = disFW[i][k] + disFW[k][j];
                    }
                }
            }
        }

        //write to output file
        BufferedWriter outputFW = new BufferedWriter(new FileWriter("cop3503-asn3-output-Hull-Zachary-fw.txt"));
        outputFW.write(String.valueOf(verticesNumber));
        outputFW.newLine();

        //displays output for Floyd-Warshall
        for(int i = 1; i <= verticesNumber; i++){
            for(int j = 1; j <= verticesNumber; j++){
                int floydwarshall = disFW[i][j];
                outputFW.write((floydwarshall == Integer.MAX_VALUE ? -1 : floydwarshall) + "\t");
            }
            outputFW.newLine();
        }
        
        //close scanner
        scan.close();
        scanNew.close();
        outputFW.close();
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