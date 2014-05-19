/* ListSorts.java */

package graphalg;

import graphalg.Edge;
import queue.*;

public class ListSorts {

  //private final static int SORTSIZE = 100;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue single, holder, middle;
    holder = new LinkedQueue();
    middle = new LinkedQueue();
    try{
      while(!q.isEmpty()){	
	single = new LinkedQueue();
	single.enqueue(q.dequeue());      
	holder.enqueue(single);	
      }      
    }
    catch(QueueEmptyException e){
      System.out.println("exception in makeQueueOfQueues");
    }
    return holder;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    try{
      LinkedQueue merged = new LinkedQueue();      
      while(!q1.isEmpty() && !q2.isEmpty()){	
	if (((Comparable)(q1.front())).compareTo(((Comparable)(q2.front()))) < 0){ // q1 < q2, pick q1
	  merged.enqueue(q1.dequeue());
	}
	else{
	  merged.enqueue(q2.dequeue());
	}
      }
      if(q1.isEmpty()){
	while(!q2.isEmpty()){
	  merged.enqueue(q2.dequeue());
	}
      }
      else{
	while(!q1.isEmpty()){
	  merged.enqueue(q1.dequeue());
	}
      }
      return merged;
    }
    catch(QueueEmptyException e){
      System.out.println("exception in mergeSortedQueues ");
    }
    return null;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    try{
      Object curItem;
      if (qIn.size() <= 1){	
	return;
      }           
      while (qIn.size()>0){	
	curItem = qIn.front();
	if (pivot.compareTo((Comparable)curItem) < 0){
	  qLarge.enqueue(qIn.dequeue());	  
	}
	else if (pivot.compareTo((Comparable)curItem) == 0){
	  qEquals.enqueue(qIn.dequeue());	  
	}
	else{
	  qSmall.enqueue(qIn.dequeue());	  
	} 
      }      
      quickSort(qLarge);      
      quickSort(qSmall);      
      qIn.append(qSmall);
      qIn.append(qEquals);
      qIn.append(qLarge);
    }
    catch(QueueEmptyException e){
      System.out.println("exception in partition");
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    LinkedQueue first, second, merged;
    LinkedQueue sorted = makeQueueOfQueues(q);
    try{
      while(sorted.size() > 1){
	first = (LinkedQueue)(sorted.dequeue());
	second = (LinkedQueue)(sorted.dequeue());	
	sorted.enqueue(mergeSortedQueues(first, second));
      }
      q.append((LinkedQueue)(sorted.dequeue()));
    }
    catch(QueueEmptyException e){
      System.out.println("exception in mergeSort ");
    }    
  }
  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if (q.size() == 0){
      q = new LinkedQueue();
      return;
    }        
    Object pivot = ((q.nth((int)(q.size()*Math.random()))));    
    partition(q, (Comparable)pivot, new LinkedQueue(), new LinkedQueue(), new LinkedQueue());
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      //q.enqueue(4);
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {
    /*
    LinkedQueue a = makeRandom(3);
    System.out.println("a: " + a.toString());
    LinkedQueue b = makeQueueOfQueues(a);
    System.out.println("b: " + b.toString());
    
    LinkedQueue c = new LinkedQueue();
    c.enqueue(new Integer(1));
    c.enqueue(new Integer(2));
    c.enqueue(new Integer(2));
    System.out.println("c: " + c.toString());
    
    LinkedQueue d = new LinkedQueue();
    d.enqueue(new Integer(2));
    d.enqueue(new Integer(2));
    d.enqueue(new Integer(6));
    System.out.println("d: " + d.toString());
    
    LinkedQueue e = mergeSortedQueues(c,d);
    System.out.println("e: " + e.toString());
    */
    /*
    LinkedQueue q = makeRandom(3);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());
    
    LinkedQueue q = makeRandom(0);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());
    System.out.println();
    LinkedQueue r = makeRandom(1);
    System.out.println(r.toString());
    quickSort(r);
    System.out.println(r.toString());
    System.out.println();
    LinkedQueue s = makeRandom(15);
    System.out.println(s.toString());
    quickSort(s);
    System.out.println(s.toString());  
    */
    LinkedQueue q = new LinkedQueue();
    Edge e1 = new Edge(new Integer(3), new Integer(4), 5);
    Edge e2 = new Edge(new Integer(3), new Integer(4), 2);

    q.enqueue(e1);
    q.enqueue(e2);
    //q.enqueue(2);
    quickSort(q);
    System.out.println(q.toString());
  }

}
