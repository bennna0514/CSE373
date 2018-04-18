package misc.graphs;

import datastructures.concrete.ArrayDisjointSet;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import datastructures.interfaces.ISet;
import misc.Searcher;
import misc.exceptions.NoPathExistsException;

/**
 * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
 * and unconnected components.
 *
 * Note: This class is not meant to be a full-featured way of representing a graph.
 * We stick with supporting just a few, core set of operations needed for the
 * remainder of the project.
 */
public class Graph<V, E extends Edge<V> & Comparable<E>> {
    
    // NOTE 1:
    //
    // Feel free to add as many fields, private helper methods, and private
    // inner classes as you want.
    //
    // And of course, as always, you may also use any of the data structures
    // and algorithms we've implemented so far.
    //
    // Note: If you plan on adding a new class, please be sure to make it a private
    // static inner class contained within this file. Our testing infrastructure
    // works by copying specific files from your project to ours, and if you
    // add new files, they won't be copied and your code will not compile.
    //
    //
    // NOTE 2:
    //
    // You may notice that the generic types of Graph are a little bit more
    // complicated then usual.
    //
    // This class uses two generic parameters: V and E.
    //
    // - 'V' is the type of the vertices in the graph. The vertices can be
    //   any type the client wants -- there are no restrictions.
    //
    // - 'E' is the type of the edges in the graph. We've contrained Graph
    //   so that E *must* always be an instance of Edge<V> AND Comparable<E>.
    //
    //   What this means is that if you have an object of type E, you can use
    //   any of the methods from both the Edge interface and from the Comparable
    //   interface
    //
    // If you have any additional questions about generics, or run into issues while
    // working with them, please ask ASAP either on Piazza or during office hours.
    //
    // Working with generics is really not the focus of this class, so if you
    // get stuck, let us know we'll try and help you get unstuck as best as we can.
    private IDictionary<V, IList<E>> graph;
    private IList<V> verts;
    private IList<E> edg;

    /**
     * Constructs a new graph based on the given vertices and edges.
     *
     * @throws IllegalArgumentException  if any of the edges have a negative weight
     * @throws IllegalArgumentException  if one of the edges connects to a vertex not
     *                                   present in the 'vertices' list
     */
    public Graph(IList<V> vertices, IList<E> edges) {

        //iterate through and check for negative weight or vertices not present in vertices list.
        //potentially nested loop
        
        //try to make an adjacency list while checking?
        
        this.graph = this.buildGraph(vertices, edges);
        this.verts = vertices;
        this.edg = edges;
    
    }
    /*
    * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
    * and unconnected components.
    */
    private IDictionary<V, IList<E>> buildGraph(IList<V> vertices, IList<E> edges) {
        
        IDictionary<V, IList<E>> test = new ChainedHashDictionary<V, IList<E>>();
        ISet<V> nodes = new ChainedHashSet<V>();
        for (V vertex: vertices) {
            nodes.add(vertex);
        }
        for (E item: edges) {
            if (item.getWeight()<0) {
                throw new IllegalArgumentException();
            }
            if (!nodes.contains(item.getVertex1()) || !nodes.contains(item.getVertex2())) {
                throw new IllegalArgumentException();
            }
            //nodes.add(item.getVertex1());
            //nodes.add(item.getVertex2());
        }
        
        
        
        for (V vertex : vertices) {
            
            IList<E> vertexEdges = new DoubleLinkedList<E>();
            
            for (E item: edges) {
                if (item.getVertex1().equals(vertex) || item.getVertex2().equals(vertex)) {
                    vertexEdges.add(item);
                }
            }
            test.put(vertex, vertexEdges);
 
        }
        return test;
        
    }

    /**
     * Sometimes, we store vertices and edges as sets instead of lists, so we
     * provide this extra constructor to make converting between the two more
     * convenient.
     */
    public Graph(ISet<V> vertices, ISet<E> edges) {
        // You do not need to modify this method.
        this(setToList(vertices), setToList(edges));
        
    }

    // You shouldn't need to call this helper method -- it only needs to be used
    // in the constructor above.
    private static <T> IList<T> setToList(ISet<T> set) {
        IList<T> output = new DoubleLinkedList<>();
        for (T item : set) {
            output.add(item);
        }
        return output;
    }

    /**
     * Returns the number of vertices contained within this graph.
     */
    public int numVertices() {
        return this.graph.size();
        //throw new NotYetImplementedException();
    }

    /**
     * Returns the number of edges contained within this graph.
     */
    public int numEdges() {
        return this.edg.size();
        //throw new NotYetImplementedException();
    }

    /**
     * Returns the set of all edges that make up the minimum spanning tree of
     * this graph.
     *
     * If there exists multiple valid MSTs, return any one of them.
     *
     * Precondition: the graph does not contain any unconnected components.
     */
    public ISet<E> findMinimumSpanningTree() {
        IDisjointSet<V> kruskal = new ArrayDisjointSet<>();
        
        //sort edges with topKsort based on edge weights.
        //IList<Integer> top = Searcher.topKSort(5, list);
        IList<E> sortedEdges = Searcher.topKSort(numEdges(), this.edg);
        
        //need to return an ISet<E>
        
        ISet<E> mst = new ChainedHashSet<E>();
        // make every vertex a MST
        //plug each vertex into a DisjointSet
        
        for (KVPair<V, IList<E>> vertex: graph) {
            kruskal.makeSet(vertex.getKey());
        }
        
        //order weights of each edge and iterate through
        //try to union everything together until we iterate through all the edges
        //add the used edges to an ISet
        
        for (E item: sortedEdges) {
            if (kruskal.findSet(item.getVertex1()) != kruskal.findSet(item.getVertex2())) {
                kruskal.union(item.getVertex1(), item.getVertex2());
                mst.add(item);
            }
        }
        return mst;
        
    }

    /**
     * Returns the edges that make up the shortest path from the start
     * to the end.
     *
     * The first edge in the output list should be the edge leading out
     * of the starting node; the last edge in the output list should be
     * the edge connecting to the end node.
     *
     * Return an empty list if the start and end vertices are the same.
     *
     * @throws NoPathExistsException  if there does not exist a path from the start to the end
     */
    public IList<E> findShortestPathBetween(V start, V end) {
        IList<E> shortestPath = new DoubleLinkedList<E>();
        IPriorityQueue<E> heap = new ArrayHeap<E>();
        IDictionary<V, Double> storeWeight = new ChainedHashDictionary<>();
        IDictionary<V, IList<E>> storePath = new ChainedHashDictionary<>();
        ISet<V> visited = new ChainedHashSet<>();
        ISet<E> visitedEdge = new ChainedHashSet<>();
        V firstVertex = null;
        V secondVertex = null;
        for (V vertex : this.verts) {
            storeWeight.put(vertex, Double.POSITIVE_INFINITY);
        }
        if (start.equals(end)) {
            return shortestPath;
        }
        visited.add(start);
        storeWeight.put(start, 0.0);
        storePath = updateGraph(start, heap, visitedEdge, storePath);
        while (!heap.isEmpty()) {
            E currentMin = heap.removeMin();
            double currWeight = currentMin.getWeight();
            if (!visited.contains(currentMin.getVertex1())) {
                firstVertex = currentMin.getVertex2();
                secondVertex = currentMin.getVertex1();
            }
            else {
                firstVertex = currentMin.getVertex1();
                secondVertex = currentMin.getVertex2();
            }          
            visited.add(secondVertex);
            double prevWeight = storeWeight.get(firstVertex);
            
            currWeight = currWeight + prevWeight;
            if (storeWeight.get(secondVertex) > currWeight) {
                storeWeight.put(secondVertex, currWeight);
                IList<E> travel = new DoubleLinkedList<>();
                for (E edge : storePath.get(firstVertex)) {
                    travel.add(edge);
                }
                travel.add(currentMin);
                storePath.put(secondVertex, travel);
            }
            
            if (secondVertex == end) {
                shortestPath = storePath.get(secondVertex);
                return shortestPath;
            }
            
            
            for (E edge : graph.get(secondVertex)) {
                if (!visitedEdge.contains(edge)) {
                    heap.insert(edge);
                    visitedEdge.add(edge);
                }
            }
        }
        throw new NoPathExistsException();    
    }
    
    public IDictionary<V, IList<E>> updateGraph(V start, IPriorityQueue<E> heap, ISet<E> visitedEdge,
            IDictionary<V, IList<E>> path) {
        for (E edge : graph.get(start)) {
            heap.insert(edge);
            visitedEdge.add(edge);
        }
        path.put(start, new DoubleLinkedList<>());
        return path;
    }
    

}
