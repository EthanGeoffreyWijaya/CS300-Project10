//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: OrderPriorityQueue
// Course: CS 300 Spring 2021
//
// Author: Ethan Geoffrey Wijaya
// Email: egwijaya@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: none
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Arrays;

/**
 * A max-heap implementation of a priority queue for Orders, where the Order with the LONGEST prep
 * time is returned first instead of the strict first-in-first-out queue as in P08.
 * 
 * TODO: Make sure Order implements Comparable<Order> so that this class can implement the
 * PriorityQueueADT without error!
 */
public class OrderPriorityQueue implements PriorityQueueADT<Order> {

  // Data fields; do not modify
  private Order[] queueHeap;
  private int size;

  /**
   * Constructs a PriorityQueue for Orders with the given capacity
   * 
   * @param capacity the initial capacity for the queue
   * @throws IllegalArgumentException if the given capacity is 0 or negative
   */
  public OrderPriorityQueue(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    size = 0;
    queueHeap = new Order[capacity];
  }

  /**
   * Inserts a new Order into the queue in the appropriate position using a heap's add logic.
   * 
   * @param newOrder the Order to be added to the queue
   */
  @Override
  public void insert(Order newOrder) {
    if (isEmpty()) {
      queueHeap[0] = newOrder;// Does not require percolation if heap is empty
      size++;
    } else {
      if (size == queueHeap.length) {// Doubles heap size if it is about to be exceeded
        queueHeap = Arrays.copyOf(queueHeap, size * 2);
      }
      queueHeap[size] = newOrder;
      percolateUp(queueHeap, size++);
    }
  }

  /**
   * A utility method to percolate Order values UP through the heap; see figure 13.3.1 in zyBooks
   * for a pseudocode algorithm.
   * 
   * @param heap       an array containing the Order values to be percolated into a valid heap
   * @param orderIndex the index of the Order to be percolated up
   */
  protected static void percolateUp(Order[] heap, int orderIndex) {
    while (orderIndex > 0) {
      int parentIndex = (orderIndex - 1) / 2;
      if (heap[orderIndex].compareTo(heap[parentIndex]) <= 0) {
        return;
      } else {// Swaps node with parent until parent is larger or index 0 reached
        Order tempOrder = heap[parentIndex];
        heap[parentIndex] = heap[orderIndex];
        heap[orderIndex] = tempOrder;
        orderIndex = parentIndex;
      }
    }
  }

  /**
   * Return the Order with the longest prep time from the queue and adjust the queue accordingly
   * 
   * @return the Order with the current longest prep time from the queue
   * @throws NoSuchElementException if the queue is empty
   */
  @Override
  public Order removeBest() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    Order removed = queueHeap[0];
    if (size == 1) {
      queueHeap[0] = null;// Percolation unnecessary for single order heaps
      size--;
    } else {
      queueHeap[0] = queueHeap[--size];
      queueHeap[size] = null;
      percolateDown(queueHeap, 0, size);
    }
    return removed;
  }

  /**
   * A utility method to percolate Order values DOWN through the heap; see figure 13.3.2 in zyBooks
   * for a pseudocode algorithm.
   * 
   * @param heap       an array containing the Order values to be percolated into a valid heap
   * @param orderIndex the index of the Order to be percolated down
   * @param size       the number of initialized elements in the heap
   */
  protected static void percolateDown(Order[] heap, int orderIndex, int size) {
    int childIndex = orderIndex * 2 + 1;
    Order value = heap[orderIndex];
    // Swaps with larger child node until child node is smaller or final
    // index reached
    while (childIndex < size) {
      Order maxValue = value;
      int maxIndex = -1;
      for (int i = 0; i < 2 && i + childIndex < size; i++) {
        if (heap[i + childIndex].compareTo(maxValue) > 0) {
          maxValue = heap[i + childIndex];
          maxIndex = i + childIndex;
        }
      }

      if (maxValue == value) {
        return;
      } else {
        Order tempOrder = heap[maxIndex];
        heap[maxIndex] = heap[orderIndex];
        heap[orderIndex] = tempOrder;
        orderIndex = maxIndex;
        childIndex = orderIndex * 2 + 1;
      }
    }
  }

  /**
   * Return the Order with the highest prep time from the queue without altering the queue
   * 
   * @return the Order with the current longest prep time from the queue
   * @throws NoSuchElementException if the queue is empty
   */
  @Override
  public Order peekBest() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    return queueHeap[0];
  }

  /**
   * Returns true if the queue contains no Orders, false otherwise
   * 
   * @return true if the queue contains no Orders, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the number of elements currently in the queue
   * 
   * @return the number of elements currently in the queue
   */
  public int size() {
    return this.size;
  }

  /**
   * Creates a String representation of this PriorityQueue. Do not modify this implementation; this
   * is the version that will be used by all provided OrderPriorityQueue implementations that your
   * tester code will be run against.
   * 
   * @return the String representation of this PriorityQueue, primarily for testing purposes
   */
  public String toString() {
    String toReturn = "";
    for (int i = 0; i < this.size; i++) {
      toReturn += queueHeap[i].getID() + "(" + queueHeap[i].getPrepTime() + ")";
      if (i < this.size - 1)
        toReturn += ", ";
    }
    return toReturn;
  }

}
