
/**
 * Filename:   AVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, Yuanhang Wang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    LEC 002
 * 
 * Due Date:   Before 10pm on September 24, 2018
 * Version:    1.0
 * 
 * Credits:    
 * 
 * Bugs:       no known bugs, but not complete either
 */

/** The tree node class for the AVL Tree
 * @param <K>
 */
public class AVLTree<K extends Comparable<K>> implements AVLTreeADT<K> {
	
	private BSTNode<K> root;      //The root of the AVL Tree
	
	/** The treenode of the AVL tree
	 * @param <K>
	 */
	class BSTNode<K> {
		/* fields */
		private K key;	                //The value stored in the Treenodes
		private int height;				//The height of the tree, which is the number of nodes on the longest branch
		private BSTNode<K> left, right;	//Left is the left child node, and right is the right child node
		private BSTNode<K> parent;      //The parent node for the current node
		private int balanceFactor;      //The balance factor leftHeight - rightHeight. Can be negative if the right child
										//has a greater height
		
		/**
		 * Constructor for a BST node.
		 * @param key
		 */
		BSTNode(K key) {
			this.key = key;
		}

		/* accessors */
		public K getKey() {
			return key;
		}

		public int getHeight() {
			return height;
		}

		public BSTNode<K> getLeft() {
			return left;
		}

		public BSTNode<K> getRight() {
			return right;
		}
		
		public BSTNode<K> getParent() {
			return parent;
		}

		public int getBalanceFactor() {
			return balanceFactor;
		}

		/* mutators */
		public void setKey(K key) {
			this.key = key;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public void setLeft(BSTNode<K> left) {
			this.left = left;
		}
		
		public void setRight(BSTNode<K> right) {
			this.right = right;
		}

		public void setParent(BSTNode<K> parent) {
			this.parent = parent;
		}

		public void setBalanceFactor(int balanceFactor) {
			this.balanceFactor = balanceFactor;
		}
		
		//TODO  delete
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return key + "";
		}
	}
	
	/**
	 * To check if the tree is empty(has no node).
	 */
	@Override
	public boolean isEmpty() {
		return null == root;
	}

	/**
	 * The insert method for AVL Tree. It calls its helper method insert to recursively
	 * find the location to insert the key. It updates the height and balance factor for each node,
	 * and checks if the tree is balanced, and reBalance the tree if necessary.
	 */
	@Override
	public void insert(K key) throws DuplicateKeyException, IllegalArgumentException {
		if (null == key) {
			throw new IllegalArgumentException("Can not insert null into AVL trees");
		}
		if (isEmpty()) {
			root = new BSTNode<K>(key);
			return;
		}
		insert(key, root);
		updateHeightAndBf(root);
		while (!checkForBalancedTree()) {
			rebalance();
		}
	}
	
	/**
	 * A method to reBalance the tree and update the height and balance factor
	 */
	private void rebalance() {
		// first, find the unBalanced node
		BSTNode<K> gNode = findUnbalancedNode(root); //deepest node that is unbalanced 
		// Look for the pattern of the unbalanced node
		// If the BF of gNode is greater than 1, then the tree is left-heavy tree
		if (gNode.getBalanceFactor() > 1) {
			// If its left child also has a positive BF, than it is a linear case just like 
			// you are inserting numbers in decreasing order : 5, 4, 3. And that needs one 
			// right rotate.
			// Also, if the left child has a 0 BF, we can also do a simple right rotate. 
			if(gNode.getLeft().getBalanceFactor() >= 0){
				rightRotate(gNode, gNode.getLeft());
			} else { // In this case, the BF is less than 0, meaning the right subtree is heavier.
					 // So, we will have to do 2 rotates, 1 left followed by 1 right
					 // For the left rotate, we are rotating the Kid around Parent,
					 // and for the right rotate, we are rotating the Grandparent around current
					 // Parent(originally the Kid)
				leftRotate(gNode.getLeft(), gNode.getLeft().getRight());
				rightRotate(gNode, gNode.getLeft());
			}
		} else {    // The exact opposite case of the above case
			if (gNode.getRight().getBalanceFactor() <= 0) {
				leftRotate(gNode, gNode.getRight());
			} else {
				rightRotate(gNode.getRight(), gNode.getRight().getLeft());
				leftRotate(gNode, gNode.getRight());
			}
		}
		//At last, update the height and BF after rebalancing
		updateHeightAndBf(root);
	}

	/**
	 * A helper method for insert
	 * @param key
	 * @param node
	 * @throws DuplicateKeyException 
	 */
	private void insert(K key, BSTNode<K> node) throws DuplicateKeyException {
		if (key == node.getKey()) {
			throw new DuplicateKeyException();
		}
		// If the key has a greater value comparing to the node's key, look for the node's
		// right child. If it is null, set as right child; otherwise compare to its right child;
		if (key.compareTo(node.getKey()) > 0) {
			if(node.getRight() == null) {
				node.setRight(new BSTNode<K>(key));
				node.getRight().setParent(node);
			} else {
				insert(key, node.getRight());
			}
		} else { // Another case is if the key is less than the node's key. Check the existence of 
				 // the node's left child. If null, insert there; otherwise compare with the left child.
			if(node.getLeft() == null) {
				node.setLeft(new BSTNode<K>(key));
				node.getLeft().setParent(node);
			} else {
				insert(key, node.getLeft());
			}
		}
	}

	/**
	 * The delete method that deletes a node with a certain key in the tree.
	 */
	@Override
	public void delete(K key) throws IllegalArgumentException {
		if (null == key) {
			throw new IllegalArgumentException("Can not delete NULL from AVL trees");
		}
		if (isEmpty()) {
			return;
		}
		BSTNode<K> searchResult = search(key, root);
		
		//If the node to be deleted actually exists in the tree, do as following
		if (searchResult != null) {
			BSTNode<K> parent = searchResult.getParent();  //The parent node of the deleted node
			// If the node has no child, delete it.
			// If the node has only 1 child, replace it with another child
			// If it has 2 children, replace with in-order successor
			if (searchResult.getLeft() == null && searchResult.getRight() == null) {
				//If the node has no parent, it is the root. Since it has no child, delete it.
				if (parent == null) {
					root = null;
					return;
				}
				//If the node to be deleted is the left child of its parent, call parent's set left.
				// otherwise, call parent's setRight
				if (parent.getLeft() == searchResult) {
					parent.setLeft(null);
				} else {
					parent.setRight(null);
				}
			} else if(searchResult.getRight() == null) {
				//If the node has no parent, it is the root. Set the only child to root
				if (parent == null) {
					root = searchResult.getLeft();
					searchResult.getLeft().setParent(null);
					return;
				}
				//If the node to be deleted is the left child of its parent, call parent's set left.
				// otherwise, call parent's setRight
				if (parent.getLeft() == searchResult) {
					parent.setLeft(searchResult.getLeft());
				} else {
					parent.setRight(searchResult.getLeft());
				}
				searchResult.setParent(null);
				searchResult.setLeft(null);
			// same if searchResult only has left child
			} else if (searchResult.getLeft() == null){
				//If the node has no parent, it is the root. Set the only child to root
				if (parent == null) {
					root = searchResult.getRight();
					searchResult.getRight().setParent(null);
					return;
				}
				if (parent.getLeft() == searchResult) {
					parent.setLeft(searchResult.getRight());
				} else {
					parent.setRight(searchResult.getRight());
				}
				searchResult.setParent(null);
				searchResult.setLeft(null);				
			} else {
				BSTNode<K> cursor = searchResult.getRight(); // This is a cursor looking for in-order successor
				//After the while loop, the cursor should be the in-order successor of the searchResult
				while(cursor.getLeft() != null) {
					cursor = cursor.getLeft();
				}
				//Moving the cursor to the deleted location
				searchResult.setKey(cursor.getKey());
				if (cursor.getParent().getLeft() == cursor) {
					cursor.getParent().setLeft(cursor.getRight());
				} else {
					cursor.getParent().setRight(cursor.getRight());
				}
				if (cursor.getRight() != null)
					cursor.getRight().setParent(cursor.getParent());
				cursor.setParent(null);
				cursor.setRight(null);

			}
		}
		updateHeightAndBf(root);
		if (!checkForBalancedTree()) {
			rebalance();
		}
	}	
	
	/**
	 * Search for a node in the AVL tree.
	 */
	@Override
	public boolean search(K key) throws IllegalArgumentException {
		if (null == key) {
			throw new IllegalArgumentException("Can not search null element");
		}
		if (this.isEmpty()) {
			return false;
		}
		BSTNode<K> result = search(key, root);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * A helper method for search method
	 * @param key
	 * @param node
	 * @return the corresponding node if it exist; otherwise return null.
	 */
	private BSTNode<K> search(K key, BSTNode<K> node) {
		if (key.compareTo(node.getKey()) == 0) {
			return node;
		// If the searching key is greater, search the right subtree if the right 
		// subtree exists. Otherwise there is no such key in the tree
		} else if (key.compareTo(node.getKey()) > 0) {
			if (node.getRight() == null) {
				return null;
			} else {
				return search(key, node.getRight());
			}
		// The last case: key is less than the node. Search the left subtree if the left
		// subtree exists. Otherwise there is no such key in the tree
		} else {
			if (node.getLeft() == null) {
				return null;
			} else {
				return search(key, node.getLeft());
			}
		}
	}

	/**
	 * To print out the tree in in-order traveral 
	 */
	@Override
	public String print() {
		return print(root);
	}
	
	/**
	 * A helper method for printing. Recursively print subtrees to the returned string
	 * @param node
	 * @return 
	 * 	A string of in-order traversal of the tree
	 */
	private String print(BSTNode<K> node) {
		if (root == null) {
			return "";
		}
		if (node.getLeft() == null && node.getRight() == null) {
			return node.getKey() + " ";
		} else if (node.getLeft() == null) { 		//node's right child must not be null
			return node.getKey().toString() + " " + print(node.getRight());
		} else if (node.getRight() == null) { 		//node's left child must not be null
			return print(node.getLeft()) + node.getKey() + " ";			
		} else { 									//node's 2 children must not be null
			return print(node.getLeft()) + node.getKey() + " " + print(node.getRight());			
		}
	}
	

	/**
	 * Check if the tree is balanced. 
	 */
	@Override
	public boolean checkForBalancedTree() {
		return checkForBalancedTree(root);
	}
	
	/**
	 * Recursively check if all subtrees are balanced.  
	 * @param node
	 * @return true if the tree and all its subtree are balanced. Otherwise return false.
	 */
	private boolean checkForBalancedTree(BSTNode<K> node) {
		// A tree (subtree) with no root is balanced
		if (node == null) {
			return true;
		}
		// If the method reaches the leaves, it means this branch is balanced.
		if ((node.getLeft() == null && node.getRight() == null)) {
			return true;
		}
		// If this node is balanced, check its 2 subtrees. 
		if (Math.abs(node.getBalanceFactor()) <= 1) {
			return checkForBalancedTree(node.getLeft()) && checkForBalancedTree(node.getRight());
		} else {
			return false;
		}
	}

	/**
	 * find the unbalanced node
	 * @param node
	 * @return
	 */
	private BSTNode<K> findUnbalancedNode(BSTNode<K> node){
		if (node == null) {
			return null;
		}
		if (Math.abs(node.getBalanceFactor()) > 1) {
			return node;
		} else {
			// If the return value for left child is null, then the left subtree is balanced and the return value
			// depends on the right subtree. 
			BSTNode<K> rtVal = findUnbalancedNode(node.getLeft()) == null ? 
						findUnbalancedNode(node.getRight()) : findUnbalancedNode(node.getLeft());
			return rtVal;
		}
	}

	/**
	 * Check if the tree is a binary search tree
	 */
	@Override
	public boolean checkForBinarySearchTree() {
		return checkForBinarySearchTree(root);
	}
	
	/**
	 * A helper method to recursively check if each subtree is a binary search tree
	 * @param node
	 * @return true if all subtrees are binary search tree
	 */
	private boolean checkForBinarySearchTree(BSTNode<K> node) {
		// an empty tree is a binary search tree
		if (node == null) {
			return true;
		}
		if (node.getLeft() != null) {
			// check for left child
			if (node.getKey().compareTo(node.getLeft().getKey()) <= 0) {
				return false;
			}
		}
		if (node.getRight() != null) {
			//check for the right child
			if (node.getKey().compareTo(node.getRight().getKey()) >= 0) {
				return false;
			}
		}
		return checkForBinarySearchTree(node.getLeft()) && checkForBinarySearchTree(node.getRight());
	}

	/**
	 * Recursively update height and balance factor for all nodes that are descendants of the input node 
	 * @param node 
	 * 		the root of the subtree that you want to update the height. Usually root of the whole tree.
	 * @return
	 * 		the height of the root (used to recursively call this method)
	 */
	private int updateHeightAndBf(BSTNode<K> node) {
		if (node == null) {
			return 0;
		}
		if (node.getLeft() == null && node.getRight() == null) {
			node.setHeight(1);
			node.setBalanceFactor(0);
			return 1;
		} else if (node.getLeft() == null) {
			int height = updateHeightAndBf(node.getRight()) + 1;  
			node.setHeight(height);
			node.setBalanceFactor(1 - height);// 0 - height of right child = 1 - height of the node
			return height;
		} else if (node.getRight() == null) {
			int height = updateHeightAndBf(node.getLeft()) + 1;  
			node.setHeight(height);
			node.setBalanceFactor(height - 1);
			return height;
		} else {
			int leftHeight = updateHeightAndBf(node.getLeft());   //calculate leftchild's height
			int rightHeight = updateHeightAndBf(node.getRight()); //calculate rightchild's height
			int bf = leftHeight - rightHeight;               //calculate node's balance factor
			node.setBalanceFactor(bf);					
			if (leftHeight >= rightHeight) {
				node.setHeight(leftHeight + 1);
				return leftHeight + 1;
			} else {
				node.setHeight(rightHeight + 1);
				return rightHeight + 1;
			}
		}
//		if (node == null) {
//			return 0;
//		}
//		if (node.getLeft() == null && node.getRight() == null) {
//			return 1;
//		} else if (node.getLeft() == null) {
//			return calculateHeight(node.getLeft()) + 1;
//		} else if (node.getRight() == null) {
//			return calculateHeight(node.getRight()) + 1;
//		} else {
//			int leftHeight = calculateHeight(node.getLeft());   //calculate leftchild's height
//			int rightHeight = calculateHeight(node.getRight()); //calculate rightchild's height
//			if (leftHeight >= rightHeight) {
//				return leftHeight + 1;
//			} else {
//				return rightHeight + 1;
//			}
//		}
	}

	/**
	 * A helper method for reBalance a linear structure:simply rotate the sub tree to left
	 * @param g
	 * @param p
	 */
	private void leftRotate(BSTNode<K> g, BSTNode<K> p) {
		BSTNode<K> tmp = p.getLeft();
		p.setLeft(g);
		g.setRight(tmp);
		if (tmp != null)
			tmp.setParent(g);
		// Replace the position of g (either left of right child of its parent) with p
		if (g.getParent() == null) {
			root = p;
		} else {
			if (g.getParent().getLeft() == g) {
				g.getParent().setLeft(p);
			} else {
				g.getParent().setRight(p);
			}
		}
		p.setParent(g.getParent());
		g.setParent(p);
	}

	/**
	 * A helper method for reBalance: rotate the sub tree to right
	 * @param g
	 * @param p
	 */
	private void rightRotate(BSTNode<K> g, BSTNode<K> p)  {
		BSTNode<K> tmp = p.getRight();
		p.setRight(g);
		g.setLeft(tmp);
		if (tmp != null)
			tmp.setParent(g);
		// Replace the position of g (either left of right child of its parent) with p
		if (g.getParent() == null) {
			root = p;
		} else {
			if (g.getParent().getLeft() == g) {
				g.getParent().setLeft(p);
			} else {
				g.getParent().setRight(p);
			}
		}
		p.setParent(g.getParent());
		g.setParent(p);
	}
	
	public static void main(String[] args) {
		AVLTree<Integer> a = new AVLTree<>();
		try {
			a.insert(1);
			a.insert(2);
			a.insert(3);
			a.insert(4);
			a.insert(5);
			a.insert(6);
			a.insert(7);
			a.insert(8);
			a.insert(9);
			a.insert(10);
			System.out.println(a.print());
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
		}
	}
	
}
