package basicgraph;

import java.util.*;

import util.GraphLoader;

/** An abstract class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges is left abstract.
 * 
 * @author UCSD MOOC development team and YOU
 * 
 */

public abstract class Graph {

	private int numVertices;
	private int numEdges;
	//optional association of String labels to vertices 
	private Map<Integer,String> vertexLabels;
	
	/**
	 * Create a new empty Graph
	 */
	public Graph() {
		numVertices = 0;
		numEdges = 0;
		vertexLabels = null;
	}

	
	/**
	 * Report size of vertex set
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numVertices;
	}
	
	
	/**
	 * Report size of edge set
	 * @return The number of edges in the graph.
	 */	
	public int getNumEdges() {
		return numEdges;
	}
	
	/**
	 * Add new vertex to the graph.  This vertex will
	 * have as its index the next available integer.
	 * Precondition: contiguous integers are used to 
	 * index vertices.
	 * @return index of newly added vertex
	 */
	//We call this function, and it calls implementAddVertex, a method that
	//adds vertexes differently depending on if the graph object is a
	//adjacency matrix, or a adjacency list
	public int addVertex() {
		//abstract method that must be implemented in sub classes
		implementAddVertex();
		numVertices ++;
		return (numVertices-1);
	}
	
	/**
	 * Abstract method implementing adding a new
	 * vertex to the representation of the graph.
	 */
	//the magic of polymorphic behavior

	public abstract void implementAddVertex();
	
	/**
	 * Add new edge to the graph between given vertices,
	 * @param v Index of the start point of the edge to be added. 
	 * @param w Index of the end point of the edge to be added. 
	 */
	public void addEdge(int v , int w) {
		numEdges ++;
		if (v < numVertices && w < numVertices) {
			implementAddEdge(v , w);			
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	/**
	 * Abstract method implementing adding a new
	 * edge to the representation of the graph.
	 */
	public abstract void implementAddEdge(int v, int w);
	
	/**
	 * Get all (out-)neighbors of a given vertex.
	 * @param v Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v
	 * 	via outgoing edges from v. 
	 */
	public abstract List<Integer> getNeighbors(int v); 
	
	/**
	 * Get all in-neighbors of a given vertex.
	 * @param v Index of vertex in question.
	 * @return List of indices of all vertices that are adjacent to v
	 * 	via incoming edges to v. 
	 */
	//this is implemented in the subclasses, GraphAdjMatrix and GraphAdjList
	public abstract List<Integer> getInNeighbors(int v);
	
	

	/** 
	 * The degree sequence of a graph is a sorted (organized in numerical order 
	 * from largest to smallest, possibly with repetitions) list of the degrees 
	 * of the vertices in the graph.
	 * 
	 * @return The degree sequence of this graph.
	 */
	public List<Integer> degreeSequence() {
		// XXX: Implement in part 1 of week 2
		//list containing generic type Integer for degree sequence list
		ArrayList<Integer> degSeq = new ArrayList<>();
		for (int i =0 ; i < numVertices; i++) {
			int totalNeighbors = getNeighbors(i).size()+getInNeighbors(i).size();
			degSeq.add(totalNeighbors);
		}
		//use the java collections methods to sort our array in descending order
		//because a degree sequence must state the largest degree first
		//ArrayList is a collection not a static array, so we must use the
		//Collections library to sort it
		Collections.sort(degSeq, Collections.reverseOrder());
		return degSeq;
	}
	
	/**
	 * Get all the vertices that are 2 away from the vertex in question.
	 * @param v The starting vertex
	 * @return A list of the vertices that can be reached in exactly two hops (by 
	 * following two edges) from vertex v.
	 * XXX: Implement in part 2 of week 2 for each subclass of Graph
	 */
	public abstract List<Integer> getDistance2(int v); 

	/** Return a String representation of the graph
	 * @return A string representation of the graph
	 */
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		s += "Degree sequence: " + degreeSequence() + ".\n";
		if (numVertices <= 20) s += adjacencyString();
		return s;
	}

	/**
	 * Generate string representation of adjacency list
	 * @return the String
	 */
	public abstract String adjacencyString();

	
	// The next methods implement labeled vertices.
	// Basic graphs may or may not have labeled vertices.
	
	/**
	 * Create a new map of vertex indices to string labels
	 * (Optional: only if using labeled vertices.)
	 */
	public void initializeLabels() {
		vertexLabels = new HashMap<Integer,String>();
	}	
	/**
	 * Test whether some vertex in the graph is labeled 
	 * with a given index.
	 * @param The index being checked
	 * @return True if there's a vertex in the graph with this index; false otherwise.
	 */
	public boolean hasVertex(int v)
	{
		return v < getNumVertices();
	}
	
	/**
	 * Test whether some vertex in the graph is labeled 
	 * with a given String label
	 * @param The String label being checked
	 * @return True if there's a vertex in the graph with this label; false otherwise.
	 */
	public boolean hasVertex(String s)
	{
		return vertexLabels.containsValue(s);
	}
	
	/**
	 * Add label to an unlabeled vertex in the graph.
	 * @param The index of the vertex to be labeled.
	 * @param The label to be assigned to this vertex.
	 */
	public void addLabel(int v, String s) {
		if (v < getNumVertices() && !vertexLabels.containsKey(v)) 
		{
			vertexLabels.put(v, s);
		}
		else {
			System.out.println("ERROR: tried to label a vertex that is out of range or already labeled");
		}
	}
	
	/**
	 * Report label of vertex with given index
	 * @param The integer index of the vertex
	 * @return The String label of this vertex 
	 */
	public String getLabel(int v) {
		if (vertexLabels.containsKey(v)) {
			return vertexLabels.get(v);
		}
		else return null;
	}

	/**
	 * Report index of vertex with given label.
	 * (Assume distinct labels for vertices.)
	 * @param The String label of the vertex
	 * @return The integer index of this vertex 
	 */
	public int getIndex(String s) {
		for (Map.Entry<Integer,String> entry : vertexLabels.entrySet()) {
			if (entry.getValue().equals(s))
				return entry.getKey();
		}
		System.out.println("ERROR: No vertex with this label");
		return -1;
	}
	

	
	/** Main method provided with some basic tests.  */
	public static void main (String[] args) {
		GraphLoader.createIntersectionsFile("data/maps/ucsd.map", "data/intersections/ucsd.intersections");
		GraphLoader.createIntersectionsFile("data/maps/aMap.map", "data/intersections/aMap.intersections");
		GraphLoader.createIntersectionsFile("data/maps/Fresno.map", "data/intersections/Fresno.intersections");

		GraphLoader.createIntersectionsFile("data/maps/myhouse.map", "data/intersections/myhouse.intersections");
		GraphLoader.createIntersectionsFile("data/maps/randomCaliData.map", "data/intersections/randomCaliData.intersections");

		// For testing of Part 1 functionality
		// Add your tests here to make sure your degreeSequence method is returning
		// the correct list, after examining the graphs.
		System.out.println("Loading graphs based on real data...");
		System.out.println("Goal: use degree sequence to analyze graphs.");
		System.out.println("****");
		System.out.println("Roads / intersections:");
		GraphAdjList simpleGraph = new GraphAdjList();
		//passes into loadRaodMap the path, and loads the data into the 2nd parameter
		//which is the graphFromFile
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleGraph);
		System.out.println(simpleGraph);
		System.out.println("Observe all degrees are <= 12.");
		System.out.println("****");

		//test2
		System.out.println("Begin Self test on Road Intersections");
		System.out.println("Roads / intersections:");
		System.out.println("HollyWood Data:");
		GraphAdjList simpletest2 = new GraphAdjList();
		GraphLoader.loadRoadMap("data/testdata/simpletest2.map", simpletest2);
		System.out.println(simpletest2);
		System.out.println("End hollywood test");
		System.out.println("\n****");

		//test3 on real world UCSD maps
		System.out.println("Begin Self test on Road Intersections");
		System.out.println("Roads / intersections:");
		System.out.println("UCSD Data:");
		GraphAdjList ucsdMap = new GraphAdjList();
		//notice we call loadRoadMap, thats because we are dealing with streets
		GraphLoader.loadRoadMap("data/maps/ucsd.map", ucsdMap);
		System.out.println(ucsdMap);
		System.out.println("End UCSD Data");
		System.out.println("\n****");

		//test4 on real world UCSD maps
		System.out.println("Begin Self test on Road Intersections");
		System.out.println("Roads / intersections:");
		System.out.println("hollywood Data:");
		GraphAdjList hugeMap = new GraphAdjList();
		//notice we call loadRoadMap, thats because we are dealing with streets
		GraphLoader.loadRoadMap("data/maps/hollywood_large.map", hugeMap);
		System.out.println(hugeMap);
		System.out.println("End hollywood Data");
		System.out.println("\n****");


		// You can test with real road data here.  Use the data files in data/maps
		//test get distance2 for both matrix and list
		System.out.println("Flight data:");
		GraphAdjList airportGraph = new GraphAdjList();
		//notice here we call loadRoutes, thats becaise we are dealing with
		//airport flights
		GraphLoader.loadRoutes("data/airports/routesUA.dat", airportGraph);
		System.out.println(airportGraph);
		System.out.println("Observe most degrees are small (1-30), eight are over 100.");
		System.out.println("****");

		//For testing Part 2 functionality
		// Test your distance2 code here.
		System.out.println("Testing distance-two methods on sample graphs...");
		System.out.println("Goal: implement method using two approaches.");
		System.out.println(airportGraph.getNeighbors(1));
		System.out.println(airportGraph.getDistance2(1));
		GraphAdjMatrix airportMatrix = new GraphAdjMatrix();
		GraphLoader.loadRoutes("data/airports/routesUA.dat", airportMatrix);
		System.out.println(airportMatrix);
		System.out.println(airportMatrix.getNeighbors(1));
		System.out.println(airportMatrix.getDistance2(1));
		//testing assertion, remember to enable them you must put '-ea' in
		//run configs in upper tool bar
		assert 5 == 5;
	}
}
