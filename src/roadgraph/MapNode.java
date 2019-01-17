package roadgraph;

import geography.GeographicPoint;
import java.util.HashSet;

import java.util.Set;

public class MapNode {

    GeographicPoint location;
    HashSet<MapEdge> nodeEdges;

    public MapNode(GeographicPoint location) {
        this.location = location;
        nodeEdges = new HashSet<>();
    }
    //return all of this nodes edges
    public HashSet<MapEdge> getEdges() {
        return this.nodeEdges;
    }

    public GeographicPoint getLocation() {
        return this.location;
    }

    //add an Edge to this specific node object in the graph
    public void addEdge(MapEdge newEdge) {
        this.nodeEdges.add(newEdge);
    }

}
