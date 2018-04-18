package mazes.generators.maze;

import java.util.Random;

import datastructures.concrete.ChainedHashSet;
import datastructures.interfaces.ISet;
import mazes.entities.Maze;
import mazes.entities.Room;
import mazes.entities.Wall;
import misc.graphs.Graph;


/**
 * Carves out a maze based on Kruskal's algorithm.
 *
 * See the spec for more details.
 */
public class KruskalMazeCarver implements MazeCarver {
    
    //create a new instance of graph to plug the set into
    //use graph to 
    //may import and use java.util.Random
    
    //test by running it
    //wals are edges
    //rooms are vertices
    //assign each wall a random weight
    //run kruskals to remove any walls involved
    @Override
    public ISet<Wall> returnWallsToRemove(Maze maze) {
        Random rand = new Random();
        
        //        assign each wall a random weight.
        ISet<Wall> weightedEdges = new ChainedHashSet<Wall>();
        //for each wall in the maze
        for (Wall wall: maze.getWalls()) {
            //make a new wall with the same values and a random weight
            Wall temp = new Wall(wall.getRoom1(), wall.getRoom2(),
                    wall.getDividingLine(), Math.abs(rand.nextInt()));
            //add it to the ISet
            weightedEdges.add(temp);
        }
        //input the rooms (vertices) and the weighted ISet of walls into a graph
        Graph<Room, Wall> graph = new Graph<>(maze.getRooms(), weightedEdges);
        //return kruskal's result for MST from graph to be removed.
        return graph.findMinimumSpanningTree();
        
    }
    /*
     * 
        //        IList<>maze.getRooms();
        //        rooms are type Room
        //        maze.getWalls();//ISet of Walls
        //        maze.getRooms();//ISet of Rooms
        
        // Note: make sure that the input maze remains unmodified after this method is over.
        //
        // In particular, if you call 'wall.setDistance()' at any point, make sure to
        // call 'wall.resetDistanceToOriginal()' on the same wall before returning.

     * */
}
