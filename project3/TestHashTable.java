/**
 * Filename:   TestHashTable.java
 * Project:    p3
 * Authors:    TODO: add your name(s) and lecture numbers here
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   TODO: add assignment due date and time
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

import java.util.NoSuchElementException; // expect to need
import static org.junit.Assert.*; 
import org.junit.Before;  // setUp method
import org.junit.Rule;
import org.junit.After;   // tearDown method
import org.junit.Test;
import org.junit.rules.ExpectedException;   

/** TODO: add class header comments here*/
public class TestHashTable{

	// TODO: add other fields that will be used by multiple tests
	@Rule
	public final ExpectedException exp = ExpectedException.none();
	
	// Allows us to create a new hash table before each test
	static HashTableADT<Integer, Object> ht;
	
	// TODO: add code that runs before each test method
	@Before
	public void setUp() throws Exception {
		ht = new HashTable<Integer, Object>();  
	}

	// TODO: add code that runs after each test method
	@After
	public void tearDown() throws Exception {
		ht = null;
	}
		
	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Tests that a HashTable is empty upon initialization
	 */
	@Test
	public void test000_IsEmpty() {
		assertEquals("size with 0 entries:", 0, ht.size());
	}
	
	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Tests that a HashTable is not empty after adding one (K, V) pair
	 */
	@Test
	public void test001_IsNotEmpty() {
		ht.put(1,"0001");
		int expected = 1;
		int actual = ht.size();
		assertEquals("size with one entry:",expected,actual);
	}

	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Other tests assume <int,Object> pairs,
	 * this test checks that <Long,Object> pair also works.
	 */
	@Test
	public void test010_Long_Object() {
		Long key = 9876543210L;
		Object expected = "" + key;		
		HashTableADT<Long,Object> table = 
				new HashTable<Long,Object>();
		table.put(key, expected);
		Object actual = table.get(key);
		assertTrue("put-get of (Long,Object) pair",
				expected.equals(actual));
	}
	
	/*
	 * Tests that the value for a key is updated 
	 * when tried to insert again.
	 */
	@Test
	public void test011_Update() {
		ht.put(1,"0001");
		ht.put(1,"0011");
		assertEquals("0011", ht.get(1));
	}
	
	/*
	 * Tests that inserting many and removing one entry
	 * from the hash table works
	 */
	@Test(timeout=1000 * 10)
	public void test100_InsertManyRemoveOne() {
		ht.put(1,"0001");
		ht.put(2,"0010");
		ht.put(3,"0011");
		ht.put(4,"0100");
		assertEquals("0100", ht.get(4));
		assertEquals(4, ht.size());
		ht.remove(4);
		System.out.println(ht.get(3));
		assertEquals(3, ht.size());
		exp.expect(NoSuchElementException.class);
		System.out.println(ht.get(4));
	}
	
	/*
	 * Tests remove non-existent element
	 */
	@Test(timeout=1000 * 10)
	public void test101_removeNone() {
		exp.expect(NoSuchElementException.class);
		ht.remove(1);
	}
	
	/*
	 * Tests ability to insert many entries and 
	 * and remove many entries from the hash table
	 */
	@Test(timeout=1000 * 10)
	public void test110_InsertRemoveMany() {
		ht.put(18,"a");
		ht.put(1,"b");
		ht.put(2,"0010");
		ht.put(3,"c");
		ht.put(4,"d");
		ht.put(69,"e");
		ht.put(52,"f");
		ht.put(35,"g");
		ht.put(86,"h");
		ht.put(103,"i");
		ht.put(103, "j");
		assertEquals("j", ht.get(103));
		assertEquals(10, ht.size());
		assertEquals("b", ht.get(1));
		ht.remove(1);
		assertEquals("a", ht.get(18));
		assertEquals("e", ht.get(69));
		assertEquals("f", ht.get(52));
		assertEquals("g", ht.get(35));
		assertEquals("h", ht.get(86));
		assertEquals("j", ht.get(103));
		ht.remove(18);
		assertEquals("e", ht.get(69));
		assertEquals("f", ht.get(52));
		assertEquals("g", ht.get(35));
		assertEquals("h", ht.get(86));
		assertEquals("j", ht.get(103));
		ht.remove(35);
		assertEquals("e", ht.get(69));
		assertEquals("f", ht.get(52));
		assertEquals("h", ht.get(86));
		assertEquals("j", ht.get(103));
		ht.remove(52);
		assertEquals("e", ht.get(69));
		assertEquals("h", ht.get(86));
		assertEquals("j", ht.get(103));
		ht.remove(69);
		assertEquals("h", ht.get(86));
		assertEquals("j", ht.get(103));
		ht.remove(86);
		assertEquals("j", ht.get(103));
		ht.remove(103);
		assertEquals(3, ht.size());
		ht.remove(2);
		ht.remove(3);
		ht.remove(4);
		assertEquals(0, ht.size());
		
		ht.put(18,"a");
		ht.put(1,"b");
		ht.put(2,"0010");
		ht.put(3,"c");
		ht.put(4,"d");
		ht.put(69,"e");
		ht.put(52,"f");
		ht.put(35,"g");
		ht.put(86,"h");
		ht.put(103,"i");
		assertEquals(10, ht.size());
	}

	/*
	 * Tests get null
	 */
	@Test
	public void test111_getNull() {
		exp.expect(IllegalArgumentException.class);
		ht.get(null);
	}
	
	/*
	 * Tests remove null
	 */
	@Test
	public void test1000_removeNull() {
		exp.expect(IllegalArgumentException.class);
		ht.remove(null);
	}

	/*
	 * Tests resize
	 */
	@Test
	public void test1001_resize() {
		ht.put(18,"0001");
		ht.put(1,"0001");
		ht.put(69,"0001");
		ht.put(52,"0001");
		ht.put(35,"0001");
		ht.put(86,"0001");
		ht.put(103,"0001");
		ht.put(120,"0001");
		ht.put(137,"0001");
		ht.put(154,"0001");
		ht.put(171,"0001");
		ht.put(188,"0001");
		ht.put(205,"0001");
		ht.put(222,"0001");
		ht.put(239,"0001");
		ht.put(256,"0001");
		ht.put(273,"0001");
		ht.put(290,"0001");
		ht.put(307,"0001");
		ht.put(324,"0001");
		assertEquals(20, ht.size());
	}
	
	/*
	 * Tests resize
	 */
	@Test
	public void test1010_constructor() {
		HashTableADT<Long,Object> table = new HashTable<Long,Object>(1,1);
		table.put(1L, "1111");
		assertEquals(1, table.size());
		assertEquals("1111", table.get(1L));
		table.put(12L, "1112");
		table.put(13L, "1113");
		assertEquals(3, table.size());
		table.put(14L, "1114");
		assertEquals(4, table.size());
		table.put(1L, "1010");
		assertEquals("1010", table.get(1L));
		table.remove(1L);
		assertEquals(3, table.size());
		table.put(1L, "1111");
		assertEquals(4, table.size());
		assertEquals("1111", table.get(1L));
		
	}
}


