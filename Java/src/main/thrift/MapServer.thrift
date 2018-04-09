namespace java de.dfki.iui.gen

enum ServerTileType {
    EMPTY = 1,
    OCCUPIED = 2,
    CONDITIONAL = 3,
}

struct Coordinate {
    1: required i32 x;
    2: required i32 y;
}

service MapServer {

    list<list<ServerTileType>> status(), //übergibt die aktuelle Map

    /**
    * This method has a oneway modifier. That means the client only makes
    * a request and does not listen for any response at all. Oneway methods
    * must be void.
    * Arguments: (1: i32  id, 2: Coordinate c),
    */
   oneway void addObstacle(1: map<Coordinate, i32> obs), // mapt Koordinaten auf Höhe der Hindernisse in mm
}