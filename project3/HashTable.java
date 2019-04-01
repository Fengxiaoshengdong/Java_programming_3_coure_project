/**
 * Filename:   HashTable.java
 * Project:    p3
 * Authors:    Yuanhang Wang LEC 001
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   Oct 29 at 11:59pm
 * Version:    1.0
 * 
 * Credits:    None
 * 
 * Bugs:       None
 */

import java.util.NoSuchElementException;

// Collision handler is the bucket. Data sturture in each bucket is a binary search tree.
// The table array in hashTable stores nodes in buckets as the root of each binary search tree.
// The hash algoritm is simply Objects' hashCode mod capacity of the hashTable
/**
 * A hashTable class using binary search tree as buckets to handle collision. 
 * @author YW
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
	private final static double LOAD_FACTOR = 0.75; // Default load factor
	private final static int INITIAL_CAPACITY = 17;	// Default initial capacity
	
	private int capacity;					// Capacity of the hashTable
	private double loadFactor;              // Load factor of the hashTable
	private int size;						// Number of items in the hashTable
	private Object[] table;					// Array that stores the BucketNodes
	
	// Create a hashTable with default initial_capacity and load_factor
	public HashTable() {
		this(INITIAL_CAPACITY, LOAD_FACTOR);
	}
	
	// Create a hashTable with initial_capacity and load_factor given
	public HashTable(int initialCapacity, double loadFactor) {
		table = new Object[initialCapacity];
		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;
	}

	/**
	 * Put Key and Value into hashTable
	 */
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null) throw new IllegalArgumentException();
		
		ensureCapacity();									 // Ensure the capacity is big enough
		
		BucketNode hashNode = new BucketNode(key, value);    // The node to put in buckets
															 // in hashTable
		hashNode.setHash(hash(key));
		// If there is no item at the hashed location of this key, the node containing 
		// the key would be the root of the binary search tree.
		if (table[hashNode.getHash()] == null) {
			table[hashNode.getHash()] = hashNode;
			size++;
			return;
		}
		// If there is a node on the position, either insert it to the tree or update.
		insertOrUpdateBST((BucketNode)table[hashNode.getHash()], hashNode);
	}
	
	/**
	 * A helper method to help insert the node to the binary search tree if no duplicate
	 * node 
	 * @param object
	 */
	private void insertOrUpdateBST(BucketNode curNode, BucketNode hashNode) {
		// If we find a node with same key, update the value and return
		if (curNode.getKey().compareTo(hashNode.getKey()) == 0) {
			curNode.setValue(hashNode.getValue());
			return;
		}
		// If the key to be put is less than the current node's key, look at the left branch
		if (curNode.getKey().compareTo(hashNode.getKey()) > 0) {
			// If there is no left child, put the key here
			if (curNode.getLeft() == null) {
				curNode.setLeft(hashNode);
				size++;
				return;
			// Otherwise, recursively find a place to put the key
			} else {
				insertOrUpdateBST(curNode.getLeft(), hashNode);
			}
		// The key of the current node is greater than the key to put, look at the right branch
		} else {
			// If there is no right child, put the key here
			if (curNode.getRight() == null) {
				curNode.setRight(hashNode);
				size++;
				return;
				// Otherwise, recursively find a place to put the key
			} else {
				insertOrUpdateBST(curNode.getRight(), hashNode);
			}
		}
	}

	@Override
	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) throw new IllegalArgumentException();
		// If there is no bucket at the hash position, throw new NoSuchElementException
		if (table[hash(key)] == null) throw new NoSuchElementException(); 
		return searchBST((BucketNode)table[hash(key)], key);
	}
	
	/**
	 * A helper method to find a node with key recursively
	 * @param curNode
	 * @param key
	 * @throws NoSuchElementException   if the node to find does not exist
	 * @return the node with key if found
	 */
	private V searchBST(BucketNode curNode, K key) throws NoSuchElementException{
		if (curNode == null) throw new NoSuchElementException();
		// If the keys are the same, we found the correct node and return the value
		if (curNode.getKey().compareTo(key) == 0) {
			return curNode.getValue();
		} 
		// If key of the current node is less than the key, search the left subtree
		if (curNode.getKey().compareTo(key) > 0) {
			return searchBST(curNode.getLeft(), key);
		// The key of the current node is greater than the key, look at the right subtree
		} else {
			return searchBST(curNode.getRight(), key);
		}
	}

	@Override
	public void remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) throw new IllegalArgumentException();
		BucketNode treeRoot = (BucketNode) table[hash(key)];// The root of tree at the hash position
		// If there is no bucket at the hash position, throw new NoSuchElementException
		if (treeRoot == null) throw new NoSuchElementException();
		treeRoot = remove(treeRoot, key) ;
		table[hash(key)] = treeRoot;
		size--;
	}
	
	/**
	 * A helper method for remove method. 
	 * @param node
	 * @param key
	 * @throws NoSuchElementException
	 */
	private BucketNode remove(BucketNode curNode, K key) throws NoSuchElementException{
		if (curNode == null) {
			throw new NoSuchElementException();
		}
		// curNode is the node to be removed
		if (key.equals(curNode.getKey())) {
			// Check the left child. If null, return right child 
			if (curNode.getLeft() == null) {
				return curNode.getRight();
			// Otherwise, left is not null. 
			} else {
				// Check the right child. If null, return left child
				// otherwise, recursively remove the in-order successor
				if (curNode.getRight() == null) {
					return curNode.getLeft();
				} else {
					BucketNode successor = findInorderSuccessor(curNode); // The inorder successor
					curNode.setKey(successor.getKey());           
					curNode.setValue(successor.getValue());           
					remove(curNode.getRight(), successor.getKey()); // After set the key, remove
																	// successor from the tree.
					return curNode;
				}
			}
		}
		// Here the key is greater than the curNode's key, look for and delete the node with
		// the key in the right subtree if it exists
		else if(curNode.getKey().compareTo(key) < 0) {
			curNode.setRight(remove(curNode.getRight(), key));
			return curNode;
		} 
		// Here the key is less than the curNode's key, look for and delete the node with
		// the key in the left subtree if it exists
		else {
			curNode.setLeft(remove(curNode.getLeft(), key));
			return curNode;
		}
	}
	
	/**
	 * A helper method to find in-order successor of a given node
	 * @param node
	 * @return the in-order successor node
	 */
	private BucketNode findInorderSuccessor(BucketNode node) {
		node = node.getRight();
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Hash function of the hashTable
	 * @param key
	 * @return index in the hashTable
	 */
	private int hash(K key) {
		return key.hashCode() % capacity;
	}
	
	/**
	 * Check if the hashTable needs to enlarge its capacity. If
	 * it needs enlarge, resize it and rehash the elements; otherwise do nothing.
	 */
	private void ensureCapacity() {
		if(size * loadFactor > capacity) {
			capacity = capacity * 2 + 1;                  // New capacity for the new hashTable
			Object[] temp = table;			
			table = new Object[capacity];	
			size = 0;
			for (int i = 0; i < temp.length; i++) {		  // Put keys in the old hashTable into new one
				BucketNode node = (BucketNode) temp[i];
				if (node != null)
					reHash(node);
			}
		}
	}
	
	/**
	 * A method that creates hashTable with new size and puts all keys and values in the 
	 * previous hashTable into the new hash position in the new HashTable
	 */
	private void reHash(BucketNode node) {
		if (node == null) {
			return;
		}
		put(node.getKey(), node.getValue());       // Put elements in buckets into new table
		reHash(node.getLeft());                	  
		reHash(node.getRight());
	}
	
	/**
	 * Inner class BucketNode is the node in the HashTable's bucket. The bucket
	 * is a binary search tree
	 */
	class BucketNode {
		private K key;				//Stores the key
		private V value;			//Stores the value
		private BucketNode left;	//Stores the reference of the leftChild
		private BucketNode right;	//Stores the reference of the rightChild
		private int hash;			//Records the hashCode. Only calculate 1 time
		
		// Constructor using key and value field
		public BucketNode(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		/*Accessors and mutators*/
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public BucketNode getLeft() {
			return left;
		}
		public void setLeft(BucketNode left) {
			this.left = left;
		}
		public BucketNode getRight() {
			return right;
		}
		public void setRight(BucketNode right) {
			this.right = right;
		}
		public int getHash() {
			return hash;
		}
		public void setHash(int hash) {
			this.hash = hash;
		}
	}
		
}
