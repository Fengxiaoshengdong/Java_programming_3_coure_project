import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Filename:   GraphImpl.java
 * Project:    p4
 * Course:     cs400 
 * Authors:    Yuanhang Wang
 * Due Date:   Nov 9th
 * 
 * T is the label of a vertex, and List<T> is a list of
 * adjacent vertices for that vertex.
 *
 * Additional credits: 
 *
 * Bugs or other notes: 
 *
 * @param <T> type of a vertex
 */
public class GraphImpl<T> implements GraphADT<T> {

    // YOU MAY ADD ADDITIONAL private members
    // YOU MAY NOT ADD ADDITIONAL public members

    /**
     * Store the vertices and the vertice's adjacent vertices
     */
    private Map<T, List<T>> verticesMap; 
    
    
    /**
     * Construct and initialize and empty Graph
     */ 
    public GraphImpl() {
        verticesMap = new HashMap<T, List<T>>();
        // you may initialize additional data members here
    }

    public void addVertex(T vertex) {
    	if (vertex == null) return;
    	if (hasVertex(vertex)) return;
    	verticesMap.put(vertex, new ArrayList<T>());
    }

    public void removeVertex(T vertex) {
    	if (vertex == null) return;
    	if (!hasVertex(vertex)) return;
    	for (T vertices : verticesMap.keySet()){
    		removeEdge(vertices, vertex);
    	}
    	verticesMap.remove(vertex);
    }

    public void addEdge(T vertex1, T vertex2) {
    	if (vertex1 == null || vertex2 == null) {
    		return;
    	}
    	// Check if two vertices are both in the verticesMap
    	if (!hasVertex(vertex1) || !hasVertex(vertex2)) {
    		return;
    	}
    	// Check if the vertex1's adjacency list contains vertex2(indicating an edge exist)
    	if (verticesMap.get(vertex1).contains(vertex2)) {
    		return;
    	}
		verticesMap.get(vertex1).add(vertex2);
    }
    
    public void removeEdge(T vertex1, T vertex2) {
    	if (vertex1 == null || vertex2 == null) {
    		return;
    	}
    	// Check if two vertices are both in the verticesMap
    	if (!hasVertex(vertex1) || !hasVertex(vertex2)) {
    		return;
    	}
    	if (!verticesMap.get(vertex1).contains(vertex2)) {
    		return;
    	}
    	verticesMap.get(vertex1).remove(vertex2);
    }    
    
    public Set<T> getAllVertices() {
    	return verticesMap.keySet();
    }

    public List<T> getAdjacentVerticesOf(T vertex) {
    	if (vertex == null) return null;
    	if (!hasVertex(vertex)) return null;
    	return verticesMap.get(vertex);
    }
    
    public boolean hasVertex(T vertex) {
        return verticesMap.get(vertex) != null;
    }

    public int order() {
    	return verticesMap.size();
    }

    public int size() {
    	int size = 0;
    	for (T vertex : verticesMap.keySet()) {
			size += verticesMap.get(vertex).size();
		}
    	return size;
    }
    
    
    /**
     * Prints the graph for the reference
     * DO NOT EDIT THIS FUNCTION
     * DO ENSURE THAT YOUR verticesMap is being used 
     * to represent the vertices and edges of this graph.
     */
    public void printGraph() {
        for ( T vertex : verticesMap.keySet() ) {
            if ( verticesMap.get(vertex).size() != 0) {
                for (T edges : verticesMap.get(vertex)) {
                    System.out.println(vertex + " -> " + edges + " ");
                }
            } else {
                System.out.println(vertex + " -> " + " " );
            }
        }
    }
}
