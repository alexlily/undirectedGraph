/* Edge.java */

package graphalg;

import list.*;

/**
 * The Edge represents an edge in a WUGraph. The purpose is to 
 * keep a list of weighted edges that can be sorted in order to perform 
 * Kruska's algorithm. 
 */

class Edge implements Comparable{
  public Object vert1;
  public Object vert2;
  public int weight;
  public Edge(Object v1, Object v2, int w){
    vert1 = v1;
    vert2 = v2;
    weight = w;
  }
  public String toString(){
    return vert1 + "-" + weight + "-" + vert2; 
  }
  public int compareTo(Object b){
    if (((Edge)b).weight < weight){ // a > b, return positive num
      return 10;
    }
    else if (((Edge)b).weight == weight){
      return 0;
    }
    else{ // a < b, return negative number
      return -10;
    }
  }
  public static void main(String[]args){
    Edge e1 = new Edge(new Integer(3), new Integer(4), 5);
    Edge e2 = new Edge(new Integer(3), new Integer(4), 3);
    System.out.println("e1 == e1 expect 0: " + e1.compareTo(e1));
    System.out.println("e1 > e2 expect 10: " + e1.compareTo(e2));
    System.out.println("e2 < e1 expect -10: " + e2.compareTo(e1));
  }
}
