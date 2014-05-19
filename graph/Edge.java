/* Edge.java */

package graph;

import list.*;

/**
 * The Edge class represents an edge between two vertices in the graph.  
 */
public class Edge {
  private DListNode[] pair = new DListNode[2];
  private int weight;
  private DListNode partner;
  
  public Edge(DListNode v1, DListNode v2, int weight) {
    pair[0] = v1;
    pair[1] = v2;
    this.weight = weight;
  }
  public void addPartner(DListNode node) {
    partner = node;
  }
  public DListNode[] pairVertices() {
    return pair;
  }
  public boolean selfEdge() {
    if (pair[0].equals(pair[1])) {
      return true;
    }
    return false;
  }
  public DListNode partner() {
    return partner;
  }
  public int weight() {
    return weight;
  }
  public void weightUpdate(int update) {
    weight = update;
  }
}
