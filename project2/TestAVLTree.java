/**
 * Filename:   TestAVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, Yuanhang Wang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    LEC 001
 * 
 * Due Date:   Before 10pm on September 24, 2018
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       no known bugs, but not complete either
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * TestAVLTree class tests the implementations of AVLTreeADT.
 * @author YW
 */
public class TestAVLTree {
	@Rule
	public Timeout globalTimeout = new Timeout(10000, TimeUnit.MILLISECONDS);
	
	/**
	 * Tests that an AVLTree is empty upon initialization.
	 */
	@Test
	public void test00Constructor() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
	}

	/**
	 * Tests that an AVLTree is empty upon initialization.
	 */
	@Test()
	public void test01isEmpty() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		assertTrue(tree.isEmpty());
	}

	/**
	 * Tests that an AVLTree is not empty after adding a node.
	 */
	@Test
	public void test02isNotEmpty() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try {
			tree.insert(1);
			assertFalse(tree.isEmpty());
		} catch (DuplicateKeyException e) {
			fail("Unexpected exception thrown " + e);
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail("Unexpected exception thrown " + e);
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Tests functionality of a single delete following several inserts.
	 */
	@Test
	public void test03insertManyDeleteOne() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try {
			tree.insert(1);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			
			tree.insert(2);
			tree.insert(3);
			tree.insert(4);			
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			
			tree.delete(1);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			assertEquals("2 3 4 ", tree.print());        //Check if the node is correctly deleted
		} catch (IllegalArgumentException | DuplicateKeyException e) {
			fail("Unexpected exception thrown " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests functionality of many deletes following several inserts.
	 */
	@Test
	public void test04insertManyDeleteMany() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try {
			tree.insert(1);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			
			tree.insert(2);
			tree.insert(3);
			tree.insert(4);			
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			
			tree.delete(1);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			assertEquals("2 3 4 ", tree.print());        //Check if the node is correctly deleted
			
			tree.delete(2);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			assertEquals("3 4 ", tree.print());    
			
			tree.delete(4);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			assertEquals("3 ", tree.print()); 
			
			tree.delete(3);
			assertTrue(tree.checkForBalancedTree());
			assertTrue(tree.checkForBinarySearchTree());
			assertEquals("", tree.print());
		} catch (IllegalArgumentException | DuplicateKeyException e) {
			fail("Unexpected exception thrown " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests the functionality of inserting 2 nodes with same key
	 * @throws DuplicateKeyException 
	 * @throws IllegalArgumentException 
	 */
	@Test(expected = DuplicateKeyException.class)
	public void test05insertDuplicateEntries() throws DuplicateKeyException {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		tree.insert(1);
		tree.insert(1);
	}
	
	/**
	 * Tests the functionality of inserting null to the tree
	 * @throws DuplicateKeyException 
	 * @throws IllegalArgumentException 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test06insertNull() throws IllegalArgumentException, DuplicateKeyException {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		tree.insert(null);
	}
	
	/**
	 * Tests the functionality of inserting null to the tree
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test07deleteNull(){
		AVLTree<Integer> tree = new AVLTree<Integer>();
		tree.delete(null);
	}
	
	/**
	 * Tests the functionality of in-order printing the tree
	 */
	@Test
	public void test08print() {
		AVLTree<Integer> tree = new AVLTree<>();
		try {
			for (int i = 0; i < 15; i++) {
				tree.insert(i);
			}
			assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 ", tree.print());
		} catch (IllegalArgumentException | DuplicateKeyException e) {
			fail("Unexpected exception thrown " + e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Tests the functionality of delete when deleting a non-existent node
	 */
	@Test
	public void test09deleteNonexistKey() {
		AVLTree<Integer> tree = new AVLTree<>();
		tree.delete(2); //deleting on a empty tree
		
		try {
			tree.insert(1); //deleting a non-existent node on a non-empty tree
			tree.delete(2);
			assertEquals("1 ", tree.print()); 
		} catch (IllegalArgumentException | DuplicateKeyException e) {
			fail("Unexpected exception thrown " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests the functionality of search in a tree
	 */
	@Test
	public void test10search() {
		AVLTree<Integer> tree = new AVLTree<>();
		assertFalse(tree.search(1)); //search in an empty tree
		try {
			tree.insert(1);
			tree.insert(2);
			tree.insert(3);
			
			assertTrue(tree.search(1));
			assertTrue(tree.search(2));
			assertTrue(tree.search(3));
			
			tree.delete(2);
			assertFalse(tree.search(2));
		} catch (IllegalArgumentException | DuplicateKeyException e) {
			fail("Unexpected exception thrown: " + e.getClass());
			e.printStackTrace();
		}
	}
}
