
package roadgraph;


import java.util.*;
import java.util.function.Consumer;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	//don't worry about the 'GeographicPoint type, just another way of saying that a node
	//is at a certain location, this is for the front end application
	private HashMap<GeographicPoint, MapNode> nodes;


	public MapGraph()
	{
		//an object was instantiated, so in the class constructor, we initialize the
		//hash map
		System.out.println("MapGraph instantiated");
		this.nodes = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return this.nodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */

	//we return a keyset because this is returning a hashset
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		//print to see the nodes
		System.out.println(this.nodes.keySet());
		return this.nodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		Set<MapEdge> edges = new HashSet<>();
		for(MapNode aNode: nodes.values()) { //for each MapNode, in nodes
			edges.addAll(aNode.getEdges());  //add all edges to new list
		}

		return edges.size();				  //and print the size of that list
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		//First method we will be implementing for the week
		//use this to assert what mine and whats not
		//i.e. whats not is whats passed in as a param if exist
		MapNode newMapNode = new MapNode(location);
		if (this.nodes.containsKey(location) || location == null)
			return false;
		else
			//this. not necessary, but we can use it because this should always be in
			//our public methods when referring to THIS object

			this.nodes.put(location, newMapNode); //not important to declare MapNode
			return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!(this.nodes.containsKey(from) || this.nodes.containsKey(to))
				|| roadName == null || roadType == null || length < 0)
			throw new IllegalArgumentException(String.format("addEdge @param Reg not met"));
		//clear to create an edge with @params
		MapEdge edge = new MapEdge(from, to, roadName, roadType, length);
		//create a directed edge starting at the from node.
		//we don't need to work with the to node at all.
		this.nodes.get(from).addEdge(edge); //get the from node, and add the edge to it
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */


	public LinkedList<GeographicPoint> bfs(GeographicPoint start,
								   GeographicPoint goal,
								   Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3
		// create a queue with starting node and visited list
		Queue<MapNode> queue = new LinkedList<>();
		Set<MapNode> visited = new HashSet<>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<>();

		// starting node put in queue path
		queue.add(nodes.get(start));

		// visit each node in queue and add any new nodes until hit goal if do
		while (!queue.isEmpty()) {
			MapNode currentNode = queue.remove();

			// if the current node is the goal return the path
			if (currentNode == nodes.get(goal))
				return reconstructPath(nodes.get(start), nodes.get(goal), parentMap);

			// loop through current nodes edges to get next points
			for (MapEdge edge : currentNode.getEdges()) {
				MapNode next = nodes.get(edge.getEnd());
				if (!visited.contains(next)) {
					// add next node to queue and visited
					visited.add(next);
					queue.add(next);

					// add next node and its parent to parent map
					parentMap.put(next, currentNode);

					// Hook for visualization.  See writeup.
					//this is adding the points to my consumer object during search
					nodeSearched.accept(next.getLocation());
				}
			}
		}
		// no path was found
		return null;

	}

	//note that changes to an object in a method are reflected in original object
	public LinkedList<GeographicPoint> reconstructPath(MapNode start, MapNode goal,
													   	HashMap<MapNode,MapNode> parentMap) {
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<>();
		MapNode currentNode = goal;
		path.add(currentNode.getLocation());
		//go through parent map and build path with geographic points
		while(currentNode != start) {
			currentNode = parentMap.get(currentNode);
			path.add(currentNode.getLocation());
		}
		Collections.reverse(path);
		return path;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		List<GeographicPoint> testrouteBFS = simpleTestMap.bfs(testStart, testEnd);
		System.out.println(testrouteBFS);



		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);

		// A very simple test using real data
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/san_diego.map", testMap);

		testStart = new GeographicPoint(32.727192, -117.16391);
		testEnd = new GeographicPoint(32.7251069, -117.1594582);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5 and BFS should be 9/10");
		List<GeographicPoint> hardTest = testMap.bfs(testStart, testEnd); //BFS check see working
		for (GeographicPoint p : hardTest) System.out.println(p);			//nice style for printing out each node that's hit		BFS
		testroute = testMap.dijkstra(testStart,testEnd);
		for (GeographicPoint p : testroute) System.out.println(p);			//nice style for printing out each node that's hit 	Dijkstra
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		for (GeographicPoint p : testroute2) System.out.println(p);			//nice style for printing out each node that's hit		A*
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);

		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
