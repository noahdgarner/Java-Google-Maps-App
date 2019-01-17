/**
 * A class that represents a maze to navigate through.
 */
package week3example;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

/**
 * A class that represents a 2D maze, represented using a graph.  
 * 
 * @author UCSD Intermediate Programming MOOC Team
 *
 */

public class Maze {
	//MazeNode, holding some data about a node,
	//and a Maze class, implemented as a adjList using a 2D array to hold MazeNodes,
	//Note that MazeNodes will contain lists that will ;
	private MazeNode[][] cells;
	private int width;
	private int height;

	private final int DEFAULT_SIZE = 10;

	/** 
	 * Create a new empty maze with default size 10x10
	 */
	public Maze() {

		cells = new MazeNode[DEFAULT_SIZE][DEFAULT_SIZE];
		this.width = DEFAULT_SIZE;
		this.height = DEFAULT_SIZE;
	}

	/** 
	 * Create a new empty Maze with specified height and width
	 * 
	 * */
	public Maze(int width, int height) {
		cells = new MazeNode[height][width];
		this.width = width;
		this.height = height;
	}

	/**
	 * Reset the maze to have the given height and width
	 * @param width The width of the maze
	 * @param height The height of the maze
	 */
	public void initialize(int width, int height) {
		cells = new MazeNode[height][width];
		this.width = width;
		this.height = height;

	}

	/**
	 * Add a graph node (i.e. not a wall) at the given location.
	 * Any grid entry that doesn't contain a node is interpreted as a wall.
	 * @param row  The row where the node exists
	 * @param col  The column where the node exists
	 */
	public void addNode(int row, int col) {
		cells[row][col] = new MazeNode(row, col);
	}

	/**
	 * Link the nodes that are adjacent (and not null) to each other with an
	 * edge. There is an edge between any two adjacent nodes up, down, left or
	 * right.
	 */

	//This needs to be refactored
	//We will handle MazeNode edges in the MazeNode class
	public void linkEdges() {
		int numRows = cells.length;
		for (int row = 0; row < numRows; row++) {
			int numCols = cells[row].length;
			for (int col = 0; col < numCols; col++) {
				if (cells[row][col] != null) {
					if (row > 0 && cells[row - 1][col] != null) {
						cells[row][col].addNeighbor(cells[row - 1][col]);
					}
					if (col > 0 && cells[row][col - 1] != null) {
						cells[row][col].addNeighbor(cells[row][col - 1]);
					}
					if (row < numRows - 1 && cells[row + 1][col] != null) {
						cells[row][col].addNeighbor(cells[row + 1][col]);
					}
					if (col < numCols - 1 && cells[row][col + 1] != null) {
						cells[row][col].addNeighbor(cells[row][col + 1]);
					}
				}
			}
		}
	}

	/**
	 * Print the maze grid to the screen.
	 */
	public void printMaze() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				if (cells[r][c] == null) {
					System.out.print('*');
				} else {
					System.out.print(cells[r][c].getDisplayChar());
				}
			}
			System.out.print("\n");
		}

	}

	/**
	 * Change the display of the maze so that it will print the 
	 * path found from start to goal.
	 * 
	 * NOTE: This method could use redesigning so that it did not expose
	 * the MazeNode class to the outside world.
	 * 
	 * @param path A path of MazeNodes from start to goal.
	 */
	public void setPath(List<MazeNode> path) {
		int index = 0;
		for (MazeNode n : path) {
			if (index == 0) {
				n.setDisplayChar(MazeNode.START);
			} else if (index == path.size() - 1) {
				n.setDisplayChar(MazeNode.GOAL);
			} else {
				n.setDisplayChar(MazeNode.PATH);
			}
			index++;
		}

	}

	/**
	 * Clear (reset) the maze so that it will not disply a path
	 * from start to goal.
	 */
	public void clearPath() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[r].length; c++) {
				MazeNode n = cells[r][c];
				if (n != null) {
					n.setDisplayChar(MazeNode.EMPTY);
				}
			}
		}
	}

	/** depth first search from (startRow,startCol) to (endRow,endCol)
	 * 
	 * NOTE: This method is refactored during the videos in week 3.  
	 * The refactored code is shown commented out below.
	 * 
	 * @param startRow  The row of the starting position
	 * @param startCol  The column of the starting position
	 * @param endRow The row of the end position
	 * @param endCol The column of the end position
	 * @return the path from starting position to ending position, or
	 * an empty list if there is no path.
	 */

	//Note the use of this inner class, makes for more secure code
	public class searchObject {
		private MazeNode start, goal;
		private HashMap<MazeNode, MazeNode> parentMap;
		private HashSet<MazeNode> visited;
		private Stack<MazeNode> stackToExplore;
		private Queue<MazeNode> queueToExplore;
		//constructs the searchObject
		public searchObject(int startRow, int startCol, int endRow, int endCol) {
			start = cells[startRow][startCol];
			goal = cells[endRow][endCol];
			parentMap = new HashMap<>(); //move initializer to constructor, proper java syn
			visited = new HashSet<>(); //moving intitializer to constructor
			stackToExplore = new Stack<>(); //move initializer to constructor, proper java syn
			queueToExplore = new LinkedList<>();

		}

		//accessors because I made all local variables to searchObject private
		//init all the private members
		public MazeNode getStart() {return start;}
		public MazeNode getGoal() {return goal;}
		public HashMap<MazeNode,MazeNode> getParentMap() {return parentMap;}
		public HashSet<MazeNode> getVisited() {return visited;}
		public Stack<MazeNode> getStackToExplore() {return stackToExplore;}
		public Queue<MazeNode> getQueueToExplore() {return queueToExplore;}
		//note there are no mutators/setters, because this class merely initializes for DFS

		//note you cannot create a main method inside an inner class in Java
		/* public static void main(String[] args) {
			System.out.println("Hello");
		} */ //This will create a compile time error
	}

	//refactored and ready to go
	public List<MazeNode> dfs(int startRow, int startCol, int endRow, int endCol) {

		//create a DFS object, so that we can have private accessors
		//and we don't need to recreate tons of hashmaps/hashsets/etc etc
		//part 1
		searchObject newDfsObj = new searchObject(startRow, startCol, endRow, endCol);

		if (newDfsObj.getStart() == null || newDfsObj.getGoal() == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<>();
		}

		//begin the algorithm, part 2
		boolean found = dfsSearch(newDfsObj); //did we find it?
		
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<>();
		}
		//a path was found, so reconstruct it, part 3
		return reconstructPath(newDfsObj);
	}
	//notice we are actually doing 3 sort of sub tasks within 1 method above.
	//This would be a great place to refactor the code
	//Good indication that we should pull these tasks apart to and create
	//HELPER methods, each with 1 task
	//refactored and ready to go
	public List<MazeNode> bfs(int startRow, int startCol, int endRow, int endCol) {
		//1. create a search object to init the bfs process
		searchObject newBfsObj = new searchObject(startRow, startCol, endRow, endCol);
		//improper start or goal, null value
		if (newBfsObj.getStart() == null || newBfsObj.getGoal() == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<MazeNode>();
		}
		//2. begin the search for the gold BFS style
		boolean found = bfsSearch(newBfsObj);
		//didnt fint it
		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<>();
		}
		// reconstruct the path
		//3. return the reconstructoed path
		return reconstructPath(newBfsObj);
	}

	//next 3 methods are helpers for bfs and dfs
	//refactored and ready, use object reference which holds unimportant data
	private static boolean dfsSearch(searchObject searchObject) {

		//begin the algorithm
		searchObject.getStackToExplore().push(searchObject.getStart());
		boolean found = false;

		// Do the search
		while (!searchObject.getStackToExplore().empty()) {
			MazeNode curr = searchObject.getStackToExplore().pop();
			if (curr == searchObject.getGoal()) {
				found = true;
				break;
			}
			List<MazeNode> neighbors = curr.getNeighbors();
			ListIterator<MazeNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MazeNode next = it.previous();
				if (!searchObject.getVisited().contains(next)) {
					searchObject.getVisited().add(next);
					searchObject.getParentMap().put(next, curr);
					searchObject.getStackToExplore().push(next);
				}
			}
		}
		return found;
	}
	private static boolean bfsSearch(searchObject searchObject) {

		//begin the algorithm
		//TODO finish helper bfsSearch method
		searchObject.getQueueToExplore().add(searchObject.getStart());
		boolean found = false;
		while (!searchObject.getQueueToExplore().isEmpty()) {
			MazeNode curr = searchObject.getQueueToExplore().remove();
			if (curr == searchObject.getGoal()) {
				found = true;
				break;
			}
			List<MazeNode> neighbors = curr.getNeighbors();
			ListIterator<MazeNode> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				MazeNode next = it.previous();
				if (!searchObject.getVisited().contains(next)) {
					searchObject.getVisited().add(next);
					searchObject.getParentMap().put(next, curr);
					searchObject.getQueueToExplore().add(next);
				}
			}
		}

		return found;
	}
	//note that changes to an object in a method are reflected in original object
	public LinkedList<MazeNode> reconstructPath(searchObject searchObject) {
		// reconstruct the path
		LinkedList<MazeNode> path = new LinkedList<MazeNode>();
		MazeNode curr = searchObject.getGoal();
		while (curr != searchObject.getStart()) {
			path.addFirst(curr);
			curr = searchObject.getParentMap().get(curr);
		}
		path.addFirst(searchObject.getStart());
		return path;
	}


	public static void main(String[] args) {
		String mazeFile = "data/mazes/maze1.maze";
		Maze maze = new Maze();
		MazeLoader.loadMaze(mazeFile, maze);
		maze.printMaze();
		List<MazeNode> path = maze.dfs(3, 3, 2, 0);
		maze.setPath(path);
		System.out.println("\n");
		maze.printMaze();
		maze.clearPath();
		maze.setPath(maze.bfs(3, 3, 2, 0));
		System.out.println("\n");
		maze.printMaze();
	}

}
