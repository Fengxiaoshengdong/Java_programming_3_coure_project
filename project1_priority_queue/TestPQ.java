/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Yuanhang Wang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    LEC 001
 * 
 * Note: Warnings are suppressed on methods that construct new instances of 
 * generic PriorityQueue types.  The exceptions that occur are caught and 
 * such types are not able to be tested with this program.
 * 
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 * 
 * Credits:    
 *             NOTE: this is an individual assignment, you are not allowed
 *             to view or use code written by anyone but yourself.
 * 
 * Bugs:       no known bugs, but not complete either
 */


import java.util.NoSuchElementException;

/**
 * Runs black-box unit tests on the priority queue implementations
 * passed in as command-line arguments (CLA).
 * 
 * If a class with the specified class name does not exist 
 * or does not implement the PriorityQueueADT interface,
 * the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor,
 * it will also be reported as unable to test.
 * 
 * If the class exists and implements PriorityQueueADT, 
 * and has a default constructor, the tests will be run.  
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include:
 *     input, expected output, and actual output
 *     
 * Example Output:
 * Testing priority queue class: PQ01
 *    5 PASSED
 *    0 FAILED
 *    5 TOTAL TESTS RUN
 * Testing priority queue class: PQ02
 *    FAILED test00isEmpty: unexpectedly threw java.lang.NullPointerException
 *    FAILED test04insertRemoveMany: unexpectedly threw java.lang.ArrayIndexOutOfBoundsException
 *    3 PASSED
 *    2 FAILED
 *    5 TOTAL TESTS RUN
 * 
 *   ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

	// set to true to see call stack trace for exceptions
	private static final boolean DEBUG = true;

	/**
	 * Run tests to determine if each Priority Queue implementation
	 * works as specified. User names the Priority Queue types to test.
	 * If there are no command-line arguments, nothing will be tested.
	 * 
	 * @param args names of PriorityQueueADT implementation class types 
	 * to be tested.
	 */
	public static void main(String[] args) {
		for (int i=0; i < args.length; i++) 
			test(args[i]);

		if ( args.length < 1 ) 
			print("no PQs to test");
	}

	/** 
	 * Run all tests on each priority queue type that is passed as a classname.
	 * 
	 * If constructing the priority queue in the first test causes exceptions, 
	 * then no other tests are run.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 */
	private static void test(String className) {
		print("Testing priority queue class: "+className);
		int passCount = 0;
		int failCount = 0;
		try {

			if (test00isEmpty(className)) passCount++; else failCount++;		
			if (test01getMaxEXCEPTION(className)) passCount++; else failCount++;

			if (test02removeMaxEXCEPTION(className)) passCount++; else failCount++;
			if (test03insertRemoveOne(className)) passCount++; else failCount++;
			if (test04insertRemoveMany(className)) passCount++; else failCount++;
			if (test05duplicatesAllowed(className)) passCount++; else failCount++;
			if (test06manyDataItems(className)) passCount++; else failCount++;
			
			if (test07insertIsEmpty(className)) passCount++; else failCount++;
			if (test08insertGetMaxMany(className)) passCount++; else failCount++;
			if (test09removeMaxGetMaxMany(className)) passCount++; else failCount++;
			
			String passMsg = String.format("%4d PASSED", passCount);
			String failMsg = String.format("%4d FAILED", failCount);
			print(passMsg);
			print(failMsg);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			if (DEBUG) e.printStackTrace();
			print(className + " FAIL: Unable to construct instance of " + className);
		} finally {
			String msg = String.format("%4d TOTAL TESTS RUN", passCount+failCount);
			print(msg);
		}

	}

	
	/** Confirm that GetMax returns the element with the highest priority after removeMax 
	 * @param className
	 * @return true if getMax returns the item with highest priority in the pq after calling removeMax 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test09removeMaxGetMaxMany(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(1);
			pq.insert(5);
			pq.insert(9);
			pq.insert(4);
			if(pq.getMax() != 9) {
				print("FAILED test09removeMaxGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.removeMax();			
			if(pq.getMax() != 5) {
				print("FAILED test09removeMaxGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.removeMax();
			if(pq.getMax() != 4) {
				print("FAILED test09removeMaxGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.removeMax();
			if(pq.getMax() != 1) {
				print("FAILED test09removeMaxGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test09removeMaxGetMaxMany: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}
	/** Confirm that getMax returns the value with highest priority when inserting many items
	 * @param className
	 * @return true if getMax method returns the value with highest priority after each item inserted
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test08insertGetMaxMany(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(4);
			if(pq.getMax() != 4) {
				print("FAILED test08insertGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.insert(8);
			if(pq.getMax() != 8) {
				print("FAILED test08insertGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.insert(5);
			if(pq.getMax() != 8) {
				print("FAILED test08insertGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
			pq.insert(9);
			if(pq.getMax() != 9) {
				print("FAILED test08insertGetMaxMany: getMax returned a value that is not the most prior in the PQ");
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test08insertGetMaxMany: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}
	/** Confirm that isEmpty works well if a priority queue has elements
	 * @param className
	 * @return true if isEmpty returns false when a pq has elements
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test07insertIsEmpty(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(1);
			if (pq.isEmpty()) {
				print("FAILED test07insertIsEmpty: isEmpty returned true when the pq is not empty");		
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test07insertIsEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}
	
	/** Confirm that the pq can handle a large number of elements (1 million in this test)
	 * @param className
	 * @return true if the pq inserts and successfully removes all 1 million elements
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test06manyDataItems(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			// 1 million is simply too big and takes too much time for PQ 02. So I used 100000
			for(int i = 0; i < 100000; i++) {
				pq.insert(i);
			}
			for (int i = 99999; i >= 0; i--) {
				if (pq.removeMax() != i) {
					print("FAILED test06manyDataItems: the removeMax returned value that is not the most prior in the PQ");
					return false;
				}
				
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test06manyDataItems: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}

	/** Confirm that duplicate values are allowed to be inserted in the pq and be correctly removed
	 * by removeMax method
	 * @param className
	 * @return true if insert and removeMax works well on duplicate values
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test05duplicatesAllowed(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(3);
			pq.insert(3);
			pq.insert(1);
			if(pq.removeMax() != 3) {
				print("FAILED test05duplicatesAllowed: removeMax failed to remove duplicate values");		
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test05duplicatesAllowed: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}

	/** Confirm that insert successfully inserts many elements into PQ and the removeMax
	 * successfully removes the element with highest priority. Any exception thrown indicates a fail
	 * @param className
	 * @return true if insert works and removeMax is actually removing the highest-priority element
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test04insertRemoveMany(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(1);
			pq.insert(5);
			pq.insert(4);
			pq.insert(8);
			if(pq.removeMax() != 8) {
				print("FAILED test04insertRemoveMany: removeMax returned a value that is not the most prior in the PQ");
				return false;
			}
			if(pq.removeMax() != 5) {
				print("FAILED test04insertRemoveMany: removeMax returned a value that is not the most prior in the PQ");
				return false;
			}
			if(pq.removeMax() != 4) {
				print("FAILED test04insertRemoveMany: removeMax returned a value that is not the most prior in the PQ");
				return false;
			}
			if(pq.removeMax() != 1) {
				print("FAILED test04insertRemoveMany: removeMax returned a value that is not the most prior in the PQ");
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test04insertRemoveManyEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}

	/** Confirm that insert successfully inserts an element into PQ and the removeMax
	 * successfully removes the element inserted. Any exception thrown indicates a fail
	 * @param className
	 * @return true if insert and removeMax 1 element work properly
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test03insertRemoveOne(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(1);
			if(pq.isEmpty()) {
				print("FAILED test03insertRemoveOne: isEmpty returned true after inserting an element");
				return false;
			}
			if(pq.removeMax() != 1) {
				print("FAILED test03insertRemoveOne: removeMax returned wrong value in the PQ");
				return false;
			}
			if (!pq.isEmpty()) {
				print("FAILED test03insertRemoveOne: isEmpty returned false when the only element in PQ is removed");				
				return false;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test03insertRemoveOneEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return true;
	}

	/** Confirm that removeMax throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * @param className
	 * @return true if removeMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 */
	private static boolean test02removeMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.removeMax();
		} catch (NoSuchElementException e){
			return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		return false;
	}

	/** DO NOT EDIT -- provided as an example
	 * Confirm that getMax throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test01getMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.getMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * @return true if able to construct Integer priority queue and 
	 * the instance isEmpty.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test00isEmpty(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
		if (pq.isEmpty()) 
			return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Integer using the class that is name.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Integer> newIntegerPQ(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Integer>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Double using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Double> newDoublePQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Double>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of String using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<String> newStringPQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<String>) obj;
		}
		return null;
	}


	/** DO NOT EDIT THIS METHOD
	 * Write the message to the standard output stream.
	 * Always adds a new line to ensure each message is on its own line.
	 * @param message Text string to be output to screen or other.
	 */
	private static void print(String message) {
		System.out.println(message);
	}

}