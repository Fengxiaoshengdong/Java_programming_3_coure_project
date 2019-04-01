/**
 * Filename:   MyPQ.java
 * Project:    p1TestPQ
 * Version:    1.0
 * User:       Shaw Wang
 * Date:       Sep 11th, 2018
 * Authors:    Yuanhang Wang
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Instructor: Deppeler (deppeler@cs.wisc.edu)
 * Credits:    n/a
 * Bugs:       no known bugs, but not complete either
 *
 * Due Date:   before 10:00 pm on September 17th
 */


import java.util.NoSuchElementException;

/**This is a priority queue implemented with an array with a heap in it.
 * @param <T>
 *
 */
public class MyPQ<T extends Comparable<T>> implements PriorityQueueADT<T> {
	private Object[] queue;
	private int size;
	
	/**
	 * default constructor. Initialize the max number of values in queue to be 30
	 */
	public MyPQ() {
		this.queue = new Object[30];
	}
	
	/**
	 * ensures the queue is big enough to hold all values.
	 */
	private void ensureCapacity() {
		if (queue[queue.length - 1] != null) {
			Object[] newQueue = new Object[queue.length * 2];
			System.arraycopy(queue, 0, newQueue, 0, queue.length - 1);
			this.queue = newQueue;
		}
	}
	
	private void swap(Object[] queue, int index1, int index2) {
		Object temp = queue[index1];
		queue[index1] = queue[index2];
		queue[index2] = temp;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(T p) {
		ensureCapacity();
		this.queue[++size] = p; //Put p in the array
		int index = size;       //The index of p
		// Iteratively look for parent and see if the child is greater than the parent.
		// If the child is greater, swap the values; otherwise the child is in the right position.
		while (queue[index / 2] != null) {
			if (p.compareTo((T)queue[index / 2]) > 0) {
				swap(queue, index / 2, index);
				index /= 2;
			}
			else {
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T removeMax() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		T returnVal = (T) queue[1];
		queue[1] = queue[size];
		queue[size--] = null;
		removeMax(1, queue);
		return returnVal;
	}
	
	@SuppressWarnings("unchecked")
	private void removeMax(int index, Object[] queue) {
		T parent = (T) queue[index];
		T leftChild = null;
		if (index * 2 < queue.length) {
			leftChild = (T) queue[index * 2];             //FIXME out of bound exception			
		}
		T rightChild = null;
		if (index * 2  + 1 < queue.length) {
			rightChild = (T) queue[index * 2 + 1];             //FIXME out of bound exception			
		}
		//If the left child is null, compare with the right child. If the right child doesn't
		//exist either, we are done. If it does exist, compare to the right child and do the
		//swap if necessary. If no swap needed, p is in the right location. Otherwise, recursively
		//call this method to compare with children.
		if (leftChild == null) {
			if (rightChild == null) {
				return;
			} else {
				if (parent.compareTo(rightChild) < 0) {
					swap(queue, index, index * 2 + 1);
					removeMax(index * 2 + 1, queue);
				} else {
					return;
				}
			}
		} else { //Otherwise, the left child exists.
			//If the rightChild exists, compare to it. If swap needed, call this method recursively
			//Otherwise the parent is in the right position
			if (rightChild == null) {
				if (parent.compareTo(leftChild) < 0) {
					swap(queue, index, index * 2);
					removeMax(index * 2, queue);
				} else {
					return;
				}
			//In the last case, both leftChild and rightChild exists. Compare them and find which
			//one has greater value, and compare it to the parent. If the parent is greater, it is
			//in good position. Otherwise swap the parent with the lesser one.
			} else {
				if (leftChild.compareTo(rightChild) >= 0) {
					if (parent.compareTo(leftChild) < 0) {
						swap(queue, index, index * 2);
						removeMax(index * 2, queue);
					}
				} else {
					if (parent.compareTo(rightChild) < 0) {
						swap(queue, index, index * 2 + 1);
						removeMax(index * 2 + 1, queue);
					}					
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return (T)queue[1];
	}
	
//	public static void main(String[] args) {
//		MyPQ<Integer> pq = new MyPQ<>();
//		pq.insert(1);
//		pq.insert(5);
//		pq.insert(4);
//		pq.insert(7);
//		pq.insert(6);
//
//		System.out.println(pq.removeMax());
//		System.out.println(pq.removeMax());
//		System.out.println(pq.removeMax());
//		System.out.println(pq.removeMax());
//		System.out.println(pq.removeMax());
//		System.out.println(pq.removeMax());
//
//	}
}