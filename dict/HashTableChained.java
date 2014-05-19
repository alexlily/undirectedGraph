/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  private List [] table;
  private int size; 
  private int tableSize;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    while(!isPrime(sizeEstimate)){
      sizeEstimate++;
    }
    table = new SList[sizeEstimate];
    size = 0;
    tableSize = sizeEstimate;    
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    table = new SList[101];
    size = 0;
    tableSize = 101;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int key = ((977 * code + 1363) % 15485863) % tableSize;
    if(key < 0){
      key += tableSize;
    } 
    return key;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    
    return (size == 0);
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    Entry entryObj = new Entry();
    entryObj.key = key;
    entryObj.value = value;
    int hashKey = compFunction(key.hashCode());
    if(table[hashKey] == null){
      table[hashKey] = new SList();
    }
    table[hashKey].insertFront(entryObj);
    size++;
    resize((float)size / tableSize);
    return entryObj;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int hashKey = compFunction(key.hashCode());
    SListNode found;
    Entry result = null;
    if(table[hashKey] == null){
      return null;
    }
    try{
      found = (SListNode) table[hashKey].front();      
      while(!key.equals(((Entry)found.item()).key())){
        found = (SListNode)found.next();
      }
      result = (Entry)found.item();
    }
    catch (InvalidNodeException e){}
    return result;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    int hashKey = compFunction(key.hashCode());
    SListNode found;
    Entry result = null;
    if(table[hashKey] != null){
      try{
        found = (SListNode) table[hashKey].front();

        while(!key.equals(((Entry)found.item()).key())){
          found = (SListNode)found.next();
        }
        result = (Entry)found.item();   
        found.remove();
        size--;
        resize((float)size / tableSize);
      }
      catch (InvalidNodeException e){}
      return result;
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for (int i = 0; i < tableSize; i++){
      table[i] = null;
    }
    size = 0;
  }

  public void printClossions(){
    String result = new String("[");
    int totalCol = 0;
    for(int i = 0; i < tableSize; i++){
      if( i != 0 && i % 10 == 0){
        result += " ]\n[";
      }
      if(table[i] == null){
        result += " 0 ";
      }
      if(table[i] != null){
        if(table[i].length() > 1){
          totalCol += table[i].length()-1;
        }
        result += " " + table[i].length() + " ";
      }
    }
    System.out.println(result);
    System.out.println("Total collossion = " + totalCol);
  }


  private static boolean isPrime(int n) {
    int divisor = 2;
    while (divisor < n){ 
      if (n % divisor == 0) {
        return false; 
      } 
      divisor++;
    }
    return true;
  }

  private void resize(float loadFactor){
    if(loadFactor >= 1 || loadFactor <= 0.25){
      int oldSize = tableSize;
      if(loadFactor >= 1){
        tableSize = oldSize * 2;
      }else if(loadFactor <= 0.25){
        tableSize = oldSize / 2;
      }
      while(!isPrime(tableSize)){
        tableSize++;
      }
      List[] temp = table;
      SListNode node;
      int hashKey;
      size = 0; 
      table = new SList[tableSize];
      for(int i = 0; i < oldSize; i++){
        if(temp[i] != null){
          try{
            node = (SListNode) temp[i].front();
            while(true){
              resizeInsert(((Entry)node.item()).key(), ((Entry)node.item()).value());
              node = (SListNode) node.next();
            }
          }catch(InvalidNodeException e){}
        }        
      }
      temp = null;
    }
  }

  private void resizeInsert(Object key, Object value) {
    Entry entryObj = new Entry();
    entryObj.key = key;
    entryObj.value = value;
    int hashKey = compFunction(key.hashCode());
    if(table[hashKey] == null){
      table[hashKey] = new SList();
    }
    table[hashKey].insertFront(entryObj);
    size++;
  }

  public static void main(String[] argv) {
    HashTableChained obj = new HashTableChained(1);
    if(obj.isEmpty())
      System.out.println("hash is EMPTY.... ");
    obj.insert("Hi", "Heloo");
    Entry obj1 = obj.find("Hi");
    System.out.println(obj1.value());
    System.out.println(obj.size());
    Entry obj2 = obj.find("way");
    obj.insert("way", "hahahahahaha");
    System.out.println(obj.size());
    obj2 = obj.find("way");
    System.out.println(obj2.value());
    obj.remove("way");
    System.out.println(obj.size());
    Entry obj3 = obj.find("Hi");
    
    obj.insert(new Integer(10), new Integer(3));
    obj.insert(new Integer(4), new Integer(55));
    System.out.println(obj.size());
    System.out.println(obj.find(new Integer(10)).value());
    System.out.println(obj.find(new Integer(4)).value());
    System.out.println(obj.remove(new Integer(10)).value());
    System.out.println(obj.size());
  }
}
