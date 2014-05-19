/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;
import queue.*;
/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {
  
  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){
    /*
      2D array graph representation
      
    */
    WUGraph minSpan = new WUGraph(); // construct a graph with no vertices or edges
    Object[] vertices = g.getVertices(); // array of all the vertices of g    
    DisjointSets vertexSet = new DisjointSets(vertices.length); 
    
    // track connected points
    // keys are Objects (vertices) and values are ints (index in set)
    HashTableChained verticesToSet = new HashTableChained(1);
    
    
    for (int i = 0; i < vertices.length; i++){
      minSpan.addVertex(vertices[i]);
      verticesToSet.insert(vertices[i], i);   // key, value
    } // add all the vertices, no edges
    
    Edge curEdge;
    int v1, v2;
    LinkedQueue edgeList = getNeighbors(g, vertices);  // weights has a sorted LinkedQueue of ints that are weights
    try{
      while( !edgeList.isEmpty() ){         
        curEdge = (Edge)(edgeList.dequeue());
        v1 = (int)((verticesToSet.find(curEdge.vert1)).value());
        v2 = (int)((verticesToSet.find(curEdge.vert2)).value());        
        if (vertexSet.find(v1) != vertexSet.find(v2)){
          vertexSet.union(v1,v2);
          minSpan.addEdge(curEdge.vert1, curEdge.vert2, curEdge.weight);
        }
      }
    }
    catch(QueueEmptyException e){}
    return minSpan;    
  }

  private static LinkedQueue getNeighbors(WUGraph g, Object[]vertices){ 
    LinkedQueue edgeList = new LinkedQueue();    
    Neighbors neighbors;    
    Edge newEdge;
    for (int i = 0; i < vertices.length; i++){
      neighbors = g.getNeighbors(vertices[i]); // neighbors for vertices[i]
      for (int j = 0; j < neighbors.weightList.length; j++){        
        newEdge = new Edge(vertices[i], neighbors.neighborList[j], neighbors.weightList[j]);	
        edgeList.enqueue(newEdge);
        ListSorts.quickSort(edgeList);
      }
    }
    return edgeList;
  }  
}
