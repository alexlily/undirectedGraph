/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  private int vertices;
  private int edges;
  private DList allvertices;
  private HashTableChained vertexlist;
  private HashTableChained edgelist;
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
    vertices = 0;
    edges = 0;
    allvertices = new DList();
    vertexlist = new HashTableChained(1);
    edgelist = new HashTableChained(1);
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
    return vertices;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
    return edges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
    Object[] totalvertices = new Object[allvertices.length()];
    DListNode temp = (DListNode) (allvertices.front());
    try {
      for (int i = 0; i < allvertices.length(); i++) {
        totalvertices[i] = ((Vertex) (temp.item())).item();
        temp = (DListNode) (temp.next());
      }
    } catch (InvalidNodeException e) {}
    return totalvertices;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
    if(vertexlist.find(vertex) == null) {
      Vertex newbie = new Vertex(vertex);
      DListNode rookie = (DListNode)allvertices.insertBack(newbie);
      vertexlist.insert(vertex, rookie);
      vertices++;
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
    Entry trashentry = vertexlist.find(vertex);
    if (trashentry!=null) {
      DListNode trashvertex = (DListNode) (trashentry.value());
      try {
        DListNode trashpair;
        DListNode trash;
        Vertex v = (Vertex) (trashvertex.item());
        int degree = v.degree();
        DListNode temp = (DListNode) (v.adjacencyedge().front());
        trashvertex.remove();
        edges -= degree;
        for (int i = 0; i < degree; i++) {
          trashpair = ((Edge) (temp.item())).partner();
          trash = temp;
          if (temp != (DListNode) v.adjacencyedge().back()) {
            temp = (DListNode) (temp.next());
          }
          if (!((Edge) trash.item()).selfEdge()) {
            trashpair.remove();
          }
          trash.remove();
        }
        vertices--;
        vertexlist.remove(vertex);
      } catch (InvalidNodeException e) {}
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
    Entry entry = vertexlist.find(vertex);
    try {
      if (entry != null) {
        DListNode node = (DListNode) (entry.value());
        if (node != null && vertex.equals( ((Vertex) (node.item())).item() )) {
        return true;
        }
      }
    } catch (InvalidNodeException e) {}
    return false;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
    Entry v = vertexlist.find(vertex);
    if (v != null) {
      DListNode node = (DListNode) (v.value());
      try {
        int vdegree = ((Vertex) (node.item())).degree();
        return vdegree;
      } catch (InvalidNodeException e) {}
    }
    return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
    Entry myEntry = vertexlist.find(vertex);
    int degree = degree(vertex) ;
    try{
      if(degree > 0){
        Vertex myVertex = (Vertex)((DListNode)myEntry.value()).item();
        Neighbors result = new Neighbors();
        DList allEdges = myVertex.adjacencyedge();
        DListNode node = (DListNode)allEdges.front();
        Edge thisEdge;
        Object neighbor, v1, v2;
        result.neighborList = new Object[degree];
        result.weightList = new int[degree];
        for(int i = 0; i < degree; i++){
          thisEdge = (Edge)node.item();
          v1 = ((Vertex) thisEdge.pairVertices()[0].item()).item();
          v2 = ((Vertex) thisEdge.pairVertices()[1].item()).item();
          neighbor = (vertex.equals(v1) ? v2 : v1);
          result.neighborList[i] = neighbor;
          result.weightList[i] = thisEdge.weight();
          node = (DListNode) node.next();
        }            
        return result;
      }
    } catch(InvalidNodeException e) {}      
    return null;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
    VertexPair newpair = new VertexPair(u, v);
    Entry entry1 = vertexlist.find(u); Entry entry2 = vertexlist.find(v);
    Entry edge = edgelist.find(newpair);
    if (entry1 != null && entry2 != null) {
      DListNode v1 = (DListNode) (entry1.value());
      DListNode v2 = (DListNode) (entry2.value());
      Edge e1, e2;
      try {
        if (edge == null) {
          e1 = new Edge(v1, v2, weight);
          if (!e1.selfEdge()) {
            DListNode node1 = ((Vertex) (v1.item())).addEdge(e1);
            edgelist.insert(newpair, node1);
            e2 = new Edge(v1, v2, weight);
            DListNode node2 = ((Vertex) (v2.item())).addEdge(e2);
            e2.addPartner(node1);
            e1.addPartner(node2);
          }else {
            DListNode node1 = ((Vertex) (v1.item())).addEdge(e1);
            e1.addPartner(node1);   
            edgelist.insert(newpair, node1);
          }
          edges++;

        }else {
          e1 = (Edge) ((DListNode) edge.value()).item();
          e2 = (Edge) e1.partner().item();
          e1.weightUpdate(weight); e2.weightUpdate(weight);
        }
      } catch (InvalidNodeException e) {}
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
    VertexPair trashpair = new VertexPair(u, v);
    Entry entry1 = vertexlist.find(u); Entry entry2 = vertexlist.find(v);
    Entry edge = edgelist.find(trashpair);
    if (edge != null && entry1 != null && entry2 != null) {
      DListNode trash1 = (DListNode) (edge.value());
      try {
        DListNode trash2 = ((Edge) (trash1.item())).partner();
        if (!((Edge) trash1.item()).selfEdge()) {
          trash2.remove();
        }
        trash1.remove();
        edges--;
      }catch (InvalidNodeException e){}
    }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
    VertexPair pair = new VertexPair(u, v);
    Entry edge = edgelist.find(pair);
    if (edge != null) {
      DListNode node = (DListNode) (edge.value());
      try {
        DListNode[] vertices = ((Edge) (node.item())).pairVertices();
        VertexPair realpair = new VertexPair(((Vertex) vertices[0].item()).item(), ((Vertex) vertices[1].item()).item());
        if (pair.equals(realpair)) {
          return true;
        }
      } catch (InvalidNodeException e) {}
    }
    return false;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
    VertexPair pair = new VertexPair(u, v);
    Entry v1 = vertexlist.find(u); Entry v2 = vertexlist.find(v);
    Entry edge = edgelist.find(pair);
    if (edge != null && v1 != null && v2 != null) {
      DListNode node = (DListNode) (edge.value());
      try {
        return ((Edge) (node.item())).weight();
      } catch (InvalidNodeException e) {}
    }
    return 0;
  }
}
