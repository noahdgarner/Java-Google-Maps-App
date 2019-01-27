/**
 * A class to represent a node in the map
 */
package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

import static java.lang.Double.*;

/**
 * @author UCSD MOOC development team
 *
 * Class representing a vertex (or node) in our MapGraph
 *
 */
class MapNode implements Comparable<MapNode> //when implementing comparable, your comparing an object against another
{                                                                                //of the same kind
    /** The list of edges out of this node */
    private HashSet<MapEdge> edges;

    /** the latitude and longitude of this node */
    private GeographicPoint location;

    private double actualDistance; //starts at infinity.

    private double predictedDistance;

    public double getDistance() {
        return predictedDistance;
    }

    public void setDistance(double predictedDistance) {
        this.predictedDistance = predictedDistance;
    }

    /**
     * Create a new MapNode at a given Geographic location
     * @param loc the location of this node
     */
    MapNode(GeographicPoint loc)
    {
        this.location = loc;
        this.edges = new HashSet<>();
        //we need this
        this.actualDistance = 0.0;//init infinity, dumb this makes no sense
        this.predictedDistance = 0.0;
    }

    /**
     * Add an edge that is outgoing from this node in the graph
     * @param edge The edge to be added
     */
    void addEdge(MapEdge edge)
    {
        edges.add(edge);
    }

    /**
     * Return the neighbors of this MapNode
     * @return a set containing all the neighbors of this node
     */
    Set<MapNode> getNeighbors()
    {
        Set<MapNode> neighbors = new HashSet<MapNode>();
        for (MapEdge edge : edges) {
            neighbors.add(edge.getOtherNode(this));
        }
        return neighbors;
    }

    /**
     * Get the geographic location that this node represents
     * @return the geographic location of this node
     */
    GeographicPoint getLocation()
    {
        return location;
    }

    /**
     * return the edges out of this node
     * @return a set contianing all the edges out of this node.
     */
    Set<MapEdge> getEdges()
    {
        return edges;
    }

    public Double getActualDistance() {
        return actualDistance;
    }

    public void setActualDistance(Double actualDistance) {
        this.actualDistance = actualDistance;
    }

    @Override
    //DEFAULT COMPARETO METHOD IN JAVA IS IMPLEMENTED AS MIN HEAP!!!!
    //if a negative value is returned, it knows to bubble up the value until it is inserted
    //in a position where it is smaller than all of its children, but  larger than all of its parents.
    public int compareTo(MapNode otherNode) {
        //you need to cast it as a double so you can treat this as the expected type Double when comparing
        return ((Double)this.getDistance()).compareTo(otherNode.getDistance());
    }


    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof MapNode) || (o == null)) {
            return false;
        }
        MapNode node = (MapNode)o;
        return node.location.equals(this.location);
    }

    /** Because we compare nodes using their location, we also
     * may use their location for HashCode.
     * @return The HashCode for this node, which is the HashCode for the
     * underlying point
     */

    @Override
    public int hashCode()
    {
        return location.hashCode();
    }


    @Override
    public String toString()
    {
        String toReturn = "[NODE at location (" + location + ")";
        toReturn += " intersects streets: ";
        for (MapEdge e: edges) {
            toReturn += e.getRoadName() + ", ";
        }
        toReturn += "]";
        return toReturn;
    }

    // For debugging, output roadNames as a String.
    public String roadNamesAsString()
    {
        String toReturn = "(";
        for (MapEdge e: edges) {
            toReturn += e.getRoadName() + ", ";
        }
        toReturn += ")";
        return toReturn;
    }

}