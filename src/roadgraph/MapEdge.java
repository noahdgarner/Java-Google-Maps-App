package roadgraph;


import geography.GeographicPoint;

public class MapEdge {

    private GeographicPoint getStart;
    private GeographicPoint end;
    private double length;
    private String streetName;
    private String roadType;

    //once a MapEdge is created, we do not need to 'set' the values, we only need getters
    //we will make MapNode set the values of MapEdge
    public MapEdge(GeographicPoint start, GeographicPoint end,
                    String streetName, String roadType, double length) {
        this.getStart = start;
        this.end = end;
        this.streetName = streetName;
        this.roadType = roadType;
        this.length = length;
    }

    public GeographicPoint getStart() {
        return getStart;
    }
    public GeographicPoint getEnd() {
        return end;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getRoadType() { return roadType; }
    public double getLength() {
        return length;
    }

}
