//package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
	
    // num of entries to the table
    private int numEntries;
    
    // num of buckets 
    private int numBuckets;
    
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    
    // ArrayList of buckets
    // each bucket is a LinkedList of HashPair used to store entries to the table
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
    	
    	// takes an int as input, initial capacity of table
    	// using the input, the constructor initializes all the fields
    	
    	// get the amount of buckets
    	this.numBuckets = initialCapacity;
    	
    	// arraylist of buckets. this is the hashtable
    	buckets = new ArrayList<>(this.numBuckets);
    	
    	// initialize every bucket as linkedlist of hashpairs
    	for (int i=0; i<this.numBuckets; i++ ) {
    		// add the pair to the linkedlist
    		buckets.add(new LinkedList<HashPair<K,V>>());
    	}
    	
    	// update the actual number of entries added; we start with 0
    	this.numEntries = 0;
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
    	
    	// inserts (key, value) in the table if key is not found, returns null
    	// otherwise, will update value of key to the new value, returns old value
    	
    	// check if key and values are valid first
    	if (key == null || value == null) {
    		throw new NullPointerException("Cannot add invalid value or key.");
    	}

    	// the index that we need to find
    	int buck = hashFunction(key);
    	
    	// the bucket to consider
    	LinkedList<HashPair<K,V>> bucket = this.buckets.get(buck);
    	
    	// if theres nothing in that bucket, add the hashpair
    	if (bucket==null) {
    		HashPair<K,V> pair = new HashPair<>(key,value);
    		bucket = new LinkedList<>();
    		bucket.add(pair);
    		
    		// update the number of entries 
    		this.numEntries = this.numEntries + 1;

    		return null;
    		
    	} else {
    		for (int i = 0; i<bucket.size(); i++){
    			HashPair<K,V> temp = bucket.get(i);
    			if (temp.getKey().equals(key)) {
    				V old = temp.getValue();
    				temp.setValue(value);
    				return old;
    			}
    		}
    		HashPair<K,V> pair2 = new HashPair<>(key,value);
    		bucket.add(pair2);
    		
    		// update the number of entries 
    		this.numEntries = this.numEntries +1;
    	}

    	// check if we have to rehash, if there arent enough buckets
    	if (this.numEntries > this.numBuckets * MAX_LOAD_FACTOR) {
    		rehash();
        }
    	return null;
    }

    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    public V get(K key) {

    	// check if key is valid first
    	if (key == null) {
    		throw new NullPointerException("Cannot find key because it is null.");
    	}
    	
    	// finding the index to look for in the linkedlist
    	int index = hashFunction(key);
    	LinkedList<HashPair<K,V>> list = this.buckets.get(index);
    	
    	// if we find the key in the table
        if (list == null || list.size() == 0) {
        		return null;
        } else {
        	// for every hashpair in the list
        	for (HashPair<K,V> pair: list) {
        		// get its key
        		K k = pair.getKey();
        		if (k.equals(key)) {
        			// if that key pairs with the one we are looking for, return that value
        			V v = pair.getValue();
        			return v;
        		}
        	}
        	return null;
        }
    }
    
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
    	
    	// removes the hashpair with the given key
    	// returns the value associated to the key
    	
    	// check if key is valid first
    	if (key == null) {
    		throw new NullPointerException("Cannot find key because it is null.");
    	}
    	
    	// finding the index to look for in the linkedlist
    	int index = hashFunction(key);
    	LinkedList<HashPair<K,V>> list = this.buckets.get(index);

    	// assume 10 entries per bucket, which is overestimating a lot
    	// on average there will be one hashpair per bucket
    	if (list == null) {
    		return null;
    	} else {
    		// find the key in the bucket
    		int count = 0;
    		for (HashPair<K,V> pair: list) {
    			if (pair.getKey().equals(key)) {
    				// temp variable to store the value in order to return later
    				V temp = pair.getValue();
    				
    				// remove the index
    				list.remove(count);
    				this.numEntries = this.numEntries - 1;
    				return temp;
    			}
    			count++;
    		}
    		return null;
    	}
    }
  
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
    	
    	// deal with number of buckets 
    	ArrayList<LinkedList<HashPair<K,V>>> temp = this.buckets;
    	
    	// new buckets list of double the old size 
    	this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(2*this.numBuckets);
    	
    	for (int i=0; i<2*this.numBuckets; i++) {
    		this.buckets.add(new LinkedList<HashPair<K,V>>());
    	}
    	
    	// size is made 0
    	// loop through all of the nodes in original bucket and insert into new list
    	this.numEntries = 0;
    	
    	// updates the number of buckets by 2 times
    	this.numBuckets *= 2;
    	
    	for (int i=0; i<temp.size(); i++) {
    		LinkedList<HashPair<K,V>> head = temp.get(i);
    		
    		for (int j=0; j<head.size(); j++) {
    			K key = head.get(j).getKey();
    			V val = head.get(j).getValue();
    			put(key, val);
    		}
    	}
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
    	
    	// returns arraylist of all keys in the table
        ArrayList<K> k = new ArrayList<>(this.numBuckets);
        Iterator<HashPair<K,V>> temp = iterator();
        HashPair<K,V> pair = temp.next();
        
        while (pair != null) {
        	k.add(pair.getKey());
        	pair = temp.next();
        }
        return k;
    }
    
   
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        
    	// looking for unique values in the table
    	ArrayList<V> v = new ArrayList<>();
    	Iterator<HashPair<K,V>> temp = iterator();
    	MyHashTable<V,Integer> keep = new MyHashTable<>(this.numBuckets);
    	
    	// get all the nodes from the table and put them in an arraylist
    	while(temp.hasNext()) {
    		HashPair<K,V> element = temp.next();
    		if (element!=null) {
    			v.add(element.getValue());	
    		}
    	}
    	
    	// keeping the unique values
    	int count = 0;
    	for (V values: v){
    		keep.put(values, count);
    		count++;
    	}
    	return keep.keys();
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
    	
    	// list of nodes from table
    	ArrayList<HashPair<K,V>> tableInList = new ArrayList<>();
    	Iterator<HashPair<K,V>> temp = results.iterator();
    	
    	// get all the nodes from the table and put them in an arraylist
    	while (temp.hasNext()) {
    		HashPair<K,V> element = temp.next();
    		if (element!=null) {
    			tableInList.add(element);	
    		}
    	}
    	 
    	// call the helper method 
    	fastSortHelp(tableInList);
    	ArrayList<K> sortedKeys = new ArrayList<>();
    	
    	// turn sortedResults into only arraylist of keys, and return it
    	for (int i=0; i<tableInList.size(); i++) {
    		sortedKeys.add(tableInList.get(i).getKey());
    	}
    	
    	return sortedKeys;
    }
    
    private static <K, V extends Comparable<V>> void fastSortHelp(ArrayList<HashPair<K,V>> list){
    	
    	int middle;
    	ArrayList<HashPair<K,V>> left = new ArrayList<>();
    	ArrayList<HashPair<K,V>> right = new ArrayList<>();
 
    	if (list.size() > 1) {
    		middle = list.size()/2;
    		
    		// copy the left half of numbers into left
    		for (int i=0; i<middle; i++) {
    			left.add(list.get(i));
    		}
    		
    		// copy the right half of numbers into left
    		for (int j=middle; j<list.size(); j++) {
    			right.add(list.get(j));
    		}
    		
    		// resursive sort
    		fastSortHelp(left);
    		fastSortHelp(right);
    		
    		// merge together
    		merge(list, left, right);
    	}
    }
    
    private static <K, V extends Comparable<V>> void merge(ArrayList<HashPair<K,V>> list, ArrayList<HashPair<K,V>> l, ArrayList<HashPair<K,V>> r){
    	
    	ArrayList<HashPair<K,V>> temp = new ArrayList<>(); 
    	  
    	//set up index values for merging the two lists 
    	int numbersIndex = 0;    
    	int leftIndex = 0;
    	int rightIndex = 0;

    	while (leftIndex < l.size() && rightIndex < r.size()) {
    		if (l.get(leftIndex).getValue().compareTo(r.get(rightIndex).getValue()) > 0 ) {
    			list.set(numbersIndex, l.get(leftIndex));
    	        leftIndex++;
    	    } else {
    	        list.set(numbersIndex, r.get(rightIndex));
    	        rightIndex++;
    	    }
    	    numbersIndex++;
    	}
    	   
    	int tempIndex = 0;
    	if (leftIndex >= l.size()) {
    	    temp = r;
	        tempIndex = rightIndex;
	    } else {
	        temp = l;
	        tempIndex = leftIndex;
	    }
    	 
	    for (int i = tempIndex; i < temp.size(); i++) {
	        list.set(numbersIndex, temp.get(i));
	        numbersIndex++;
	    }
	 }

    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	
    	HashPair<K,V> current; 
    	HashPair<K,V> temp; 
    	
    	int b = 0;   
    	int l = 0;
    	ArrayList<LinkedList<HashPair<K,V>>> hashTable = getBuckets();
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
        	
        	boolean cond = true;
        	while (hashTable.get(b).isEmpty()) {
        		b++;
        		if (b == numBuckets()) {
        			current = null;
        			cond = false;
        			break;
        		}
        	}
        	
        	if (cond) {
        		current = hashTable.get(b).element();
        	}
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
        	
        	// returns true if the hashtable has a next has pair
        	return current!=null;
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
        	
        	// returns the next hash pair
        	l++;
        	if (b == numBuckets()) {
    			temp = current;
    			current = null;
    			return temp;
        	}
        	
        	if (!(l > hashTable.get(b).size()-1)) {
        		temp = current;
        		current = hashTable.get(b).get(l);
        		return temp;
        	} else {
        		b = b + 1;
        		l = 0;
        		if (b == numBuckets()) {
        			temp = current;
        			current = null;
        			return temp;
        		} else {
        			while (hashTable.get(b).isEmpty()) {
        				b++;
        				if (b == numBuckets()) {
        					temp = current;
        					current = null;
                			return temp;
        				}
        			}
        			
        			temp = current;
        			current = hashTable.get(b).get(0);
        			return temp;
        		}
        	}
         }
    }
    
    
    
    
}

