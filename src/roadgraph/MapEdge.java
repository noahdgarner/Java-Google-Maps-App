/**
 *
 */
package roadgraph;

import geography.GeographicPoint;



class MapEdge
{
    /** The name of the road */
    private String roadName;

    /** The type of the road */
    private String roadType;

    /** The two end points of the edge */
    private MapNode start;
    private MapNode end;


    /** The length of the road segment, in km */
    private double length;

    static final double DEFAULT_LENGTH = 0.01;


    MapEdge(String roadName, String roadType,
            MapNode n1, MapNode n2, double length) {
        this.roadName = roadName;
        start = n1;
        end = n2;
        this.roadType = roadType;
        this.length = length;
    }


    MapNode getEndNode() {
        return end;
    }

    /**
     * Return the location of the start point
     * @return The location of the start point as a GeographicPoint
     */
    GeographicPoint getStartPoint()
    {
        return start.getLocation();
    }

    /**
     * Return the location of the end point
     * @return The location of the end point as a GeographicPoint
     */
    GeographicPoint getEndPoint()
    {
        return end.getLocation();
    }

    /**
     * Return the length of this road segment
     * @return the length of the road segment
     */
    public double getLength() //use this for project
    {
        return length;
    }

    /**
     * Get the road's name
     * @return the name of the road that this edge is on
     */
    public String getRoadName()
    {
        return roadName;
    }


    MapNode getOtherNode(MapNode node)
    {
        if (node.equals(start))
            return end;
        else if (node.equals(end))
            return start;
        throw new IllegalArgumentException("Looking for " +
                "a point that is not in the edge");
    }


    @Override
    public String toString()
    {
        String toReturn = "[EDGE between ";
        toReturn += "\n\t" + start.getLocation();
        toReturn += "\n\t" + end.getLocation();
        toReturn += "\nRoad name: " + roadName + " Road type: " + roadType +
                " Segment length: " + String.format("%.3g", length) + "km";

        return toReturn;
    }

}