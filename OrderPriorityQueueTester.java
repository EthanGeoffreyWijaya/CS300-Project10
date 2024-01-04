//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: OrderPriorityQueueTester
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
 * This class checks the correctness of the implementation of the methods defined in the class
 * OrderPriorityQueue.
 * 
 * You MAY add additional public static boolean methods to this class if you like, and any private
 * static helper methods you need.
 */
public class OrderPriorityQueueTester {

  /**
   * Checks the correctness of the isEmpty method of OrderPriorityQueue.
   * 
   * You should, at least: (1) create a new OrderPriorityQueue and verify that it is empty (2) add a
   * new Order to the queue and verify that it is NOT empty (3) remove that Order from the queue and
   * verify that it is empty again
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testIsEmpty() {
    Order.resetIDGenerator();
    OrderPriorityQueue heap = new OrderPriorityQueue(10);

    // 1) Unexpected exception test
    try {
      heap.isEmpty();
    } catch (Exception e) {
      System.out
          .println("Error 1| isEmpty() should not throw an exception. Details: " + e.getMessage());
      return false;
    }

    // 2) test for empty heap
    if (!heap.isEmpty()) {
      System.out.println("Error 2| isEmpty() should return true for empty heaps.");
      return false;
    }

    // 3) test for heap with one order
    heap.insert(new Order("1", 1));
    if (heap.isEmpty()) {
      System.out.println("Error 3| isEmpty() should return false if heap has one value");
      return false;
    }

    // 4) test for heap with more than one order
    heap.insert(new Order("2", 2));
    heap.insert(new Order("3", 3));
    if (heap.isEmpty()) {
      System.out.println("Error 4| isEmpty() should return false if heap has values.");
      return false;
    }

    // 5) test for heap after removing all orders
    try {
      heap.removeBest();
      heap.removeBest();
      heap.removeBest();
      if (!heap.isEmpty()) {
        System.out.println("Error 5| isEmpty() should return true if heap is empty after removal.");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Error 5.1| isEmpty() should not throw an exception");
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least: (1) create a new OrderPriorityQueue and add a single order with a large
   * prepTime to it (2) use the OrderPriorityQueue toString method to verify that the queue's
   * internal structure is a valid heap (3) add at least three more orders with DECREASING prepTimes
   * to the queue and repeat step 2.
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertBasic() {
    Order.resetIDGenerator();
    OrderPriorityQueue heap = new OrderPriorityQueue(10);

    // 1) unexpected exception test
    try {
      heap.insert(new Order("1", 10));
    } catch (Exception e) {
      System.out.println("Error 1| insert() should not throw an exception");
      return false;
    }

    // 2) proper insert test
    if (heap.isEmpty() || heap.size() != 1) {
      System.out.println("Error 2| order not added properly. isEmpty(): " + heap.isEmpty()
          + ". Size: " + heap.size());
      return false;
    }

    // 3) insert order test 1
    if (!heap.toString().equals("1001(10)")) {
      System.out.println(
          "Error 3| insert ordered heap improperly. Expected: 1001(10). " + "Actual: " + heap);
      return false;
    }

    // 4) insert order test 2
    heap.insert(new Order("1", 6));
    heap.insert(new Order("1", 3));
    heap.insert(new Order("1", 2));
    if (!heap.toString().equals("1001(10), 1002(6), 1003(3), 1004(2)")) {
      System.out.println("Error 4| insert ordered heap improperly. Expected: 1001(10), "
          + "1002(6), 1003(3), 1004(2). Actual: " + heap);
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least: (1) create an array of at least four Orders that represents a valid heap
   * (2) add a fifth order at the next available index that is NOT in a valid heap position (3) pass
   * this array to OrderPriorityQueue.percolateUp() (4) verify that the resulting array is a valid
   * heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateUp() {
    Order.resetIDGenerator();
    Order[] orderArr = new Order[] {new Order("1", 8), new Order("1", 7), new Order("1", 5),
        new Order("1", 6), null};
    Order[] orderArrCopy = Arrays.copyOf(orderArr, 5);

    // 1) Unexpected exception test
    try {
      OrderPriorityQueue.percolateUp(orderArr, 3);
    } catch (Exception e) {
      System.out.println(
          "Error 1| percolateUp() should not throw an exception. Details: " + e.getMessage());
      return false;
    }

    // 2) percolateUp test on valid heap
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 2| percolateUp should not change a valid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    // 3) percolateUp test on invalid heap 1
    orderArr[4] = new Order("1", 10);
    orderArrCopy = Arrays.copyOf(orderArr, 5);
    Order tempOrder = orderArrCopy[1];
    orderArrCopy[1] = orderArrCopy[4];
    orderArrCopy[4] = tempOrder;
    tempOrder = orderArrCopy[0];
    orderArrCopy[0] = orderArrCopy[1];
    orderArrCopy[1] = tempOrder;

    OrderPriorityQueue.percolateUp(orderArr, 4);
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 3| percolateUp incorrectly altered invalid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    // 4) percolateUP test on invalid heap 2
    orderArr[4] = new Order("1", 9);
    orderArrCopy = Arrays.copyOf(orderArr, 5);
    tempOrder = orderArrCopy[1];
    orderArrCopy[1] = orderArrCopy[4];
    orderArrCopy[4] = tempOrder;

    OrderPriorityQueue.percolateUp(orderArr, 4);
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 4| percolateUp incorrectly altered invalid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least: (1) create a new OrderPriorityQueue with at least 6 orders of varying
   * prepTimes, adding them to the queue OUT of order (2) use the OrderPriorityQueue toString method
   * to verify that the queue's internal structure is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertAdvanced() {
    Order.resetIDGenerator();
    OrderPriorityQueue heap = new OrderPriorityQueue(2);

    try {
      // 1) insert order test 1
      heap.insert(new Order("1", 5));
      heap.insert(new Order("1", 15));
      if (!heap.toString().equals("1002(15), 1001(5)")) {
        System.out.println(
            "Error 1| incorrect insert order. Expected: 1002(15), 1001(5). Actual: " + heap);
        return false;
      }

      // 2) insert order test 2
      heap.insert(new Order("1", 21));
      heap.insert(new Order("1", 8));
      if (!heap.toString().equals("1003(21), 1004(8), 1002(15), 1001(5)")) {
        System.out
            .println("Error 2| incorrect insert order. Expected: 1003(21), 1004(8), 1002(15), "
                + "1001(5). Actual: " + heap);
        return false;
      }

      // 3) insert order test 3
      heap.insert(new Order("1", 7));
      heap.insert(new Order("1", 22));
      if (!heap.toString().equals("1006(22), 1004(8), 1003(21), 1001(5), 1005(7), 1002(15)")) {
        System.out
            .println("Error 3| incorrect insert order. Expected: 1006(22), 1004(8), 1003(21), "
                + "1001(5), 1005(7), 1002(15). Actual: " + heap);
        return false;
      }
    } catch (Exception e) {// 4) exception test
      System.out.println("Error 4| insert() should not throw an exception.");
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least: (1) create an array of at least five Orders where the Order at index 0 is
   * NOT in valid heap position (2) pass this array to OrderPriorityQueue.percolateDown() (3) verify
   * that the resulting array is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateDown() {
    Order.resetIDGenerator();
    Order[] orderArr = new Order[] {new Order("1", 9), new Order("1", 7), new Order("1", 8),
        new Order("1", 5), new Order("1", 3)};
    Order[] orderArrCopy = Arrays.copyOf(orderArr, 5);

    // 1) unexpected exception test
    try {
      OrderPriorityQueue.percolateDown(orderArr, 0, 5);
    } catch (Exception e) {
      System.out.println(
          "Error 1| percolateDown should not throw an exception. Details: " + e.getMessage());
      return false;
    }

    // 2) percolateDown valid heap test
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 2| percolateDown should not alter a valid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    // 3) percolateDown invalid heap test 1
    orderArr[0] = new Order("1", 1);
    orderArrCopy = Arrays.copyOf(orderArr, 5);
    Order tempOrder = orderArrCopy[2];
    orderArrCopy[2] = orderArrCopy[0];
    orderArrCopy[0] = tempOrder;

    OrderPriorityQueue.percolateDown(orderArr, 0, 5);
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 3| percolateDown incorrectly altered invalid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    // 4) percolateDown invalid heap test 2
    orderArr[0] = new Order("1", 4);
    orderArrCopy = Arrays.copyOf(orderArr, 5);
    tempOrder = orderArrCopy[1];
    orderArrCopy[1] = orderArrCopy[0];
    orderArrCopy[0] = tempOrder;
    tempOrder = orderArrCopy[3];
    orderArrCopy[3] = orderArrCopy[1];
    orderArrCopy[1] = tempOrder;

    OrderPriorityQueue.percolateDown(orderArr, 0, 5);
    if (!Arrays.equals(orderArr, orderArrCopy)) {
      System.out.println("Error 4| percolateDown incorrectly altered invalid heap. Expected: "
          + Arrays.toString(orderArrCopy) + ". Actual: " + Arrays.toString(orderArr));
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the removeBest and peekBest methods of OrderPriorityQueue.
   * 
   * You should, at least: (1) create a new OrderPriorityQueue with at least 6 orders of varying
   * prepTimes, adding them to the queue in whatever order you like (2) remove all but one of the
   * orders, verifying that each order has a SHORTER prepTime than the previously-removed order (3)
   * peek to see that the only order left is the one with the SHORTEST prepTime (4) check isEmpty to
   * verify that the queue has NOT been emptied (5) remove the last order and check isEmpty to
   * verify that the queue HAS been emptied
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPeekRemove() {
    Order.resetIDGenerator();
    OrderPriorityQueue heap = new OrderPriorityQueue(6);
    heap.insert(new Order("1", 7));
    heap.insert(new Order("1", 12));
    heap.insert(new Order("1", 4));
    heap.insert(new Order("1", 8));
    heap.insert(new Order("1", 13));
    heap.insert(new Order("1", 5));
    Order removed;

    // 1) removeBest unexpected exception test
    try {
      removed = heap.removeBest();
    } catch (Exception e) {
      System.out.println(
          "Error 1| removeBest() should not throw an exception. Details: " + e.getMessage());
      return false;
    }

    // 2) removeBest correct value test
    if (removed.getID() != 1005) {
      System.out.println("Error 2| removeBest returned incorrect value. Expected: 1005 1 (13). "
          + "Actual: " + removed);
      return false;
    }

    // 3) removeBest order test
    if (!heap.toString().equals("1002(12), 1004(8), 1006(5), 1001(7), 1003(4)")) {
      System.out.println("Error 3| incorrect order after removeBest. Expected: 1002(12), 1004(8), "
          + "1006(5), 1001(7), 1003(4). Actual: " + heap);
      return false;
    }

    // 4) peekBest unexpected exception test
    try {
      heap.peekBest();
    } catch (Exception e) {
      System.out
          .println("Error 4| peekBest() should not throw an exception. Details: " + e.getMessage());
      return false;
    }

    // 5) peekBest() order test
    if (!heap.toString().equals("1002(12), 1004(8), 1006(5), 1001(7), 1003(4)")) {
      System.out.println("Error 5| peekBest should not alter heap order. Expected: 1002(12), "
          + "1004(8), 1006(5), 1001(7), 1003(4). Actual: " + heap);
      return false;
    }

    // 6) peekBest() return value test
    if (heap.peekBest().getID() != 1002) {
      System.out.println("Error 6| peekBest() returned incorrect value. Expected: 1002 1 (12). "
          + "Actual: " + heap.peekBest());
      return false;
    }

    // 7) removeBest order test 2
    heap.removeBest();
    heap.removeBest();
    heap.removeBest();
    heap.removeBest();
    if (!heap.toString().equals("1003(4)")) {
      System.out
          .println("Error 7| incorrect order after removeBest. Expected: 1003(4). Actual: " + heap);
      return false;
    }

    // 8) peekBest test 2
    if (heap.peekBest().getID() != 1003) {
      System.out.println("Error 8| peekBest returned incorrect value. Expected: 1003 1 (4). "
          + "Actual: " + heap.peekBest());
      return false;
    }

    // 9) final peekBest() alterations check
    if (heap.isEmpty()) {
      System.out.println("Error 9| removeBest or peekBest caused incorrect isEmpty return.");
      return false;
    }

    // 10) removeBest final test
    heap.removeBest();
    if (!heap.isEmpty()) {
      System.out.println("Error 10| removeBest() failed to remove final value in heap.");
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of the removeBest and peekBest methods, as well as the constructor of
   * the OrderPriorityQueue class for erroneous inputs and/or states
   * 
   * You should, at least: (1) create a new OrderPriorityQueue with an invalid capacity argument,
   * and verify that the correct exception is thrown (2) call peekBest() on an OrderPriorityQueue
   * with an invalid state for peeking, and verify that the correct exception is thrown (3) call
   * removeBest() on an OrderPriorityQueue with an invalid state for removing, and verify that the
   * correct exception is thrown
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testErrors() {
    Order.resetIDGenerator();
    OrderPriorityQueue heap;
    // 1) OrderPriorityQueue constructor test
    try {
      heap = new OrderPriorityQueue(0);
      heap = new OrderPriorityQueue(-5);
      System.out.println("Error 1.1| No exception thrown for invalid OrderPriorityQueue constructor"
          + " arguments.");
      return false;
    } catch (IllegalArgumentException e) {

    } catch (Exception e) {
      System.out.println("Error 1.2| Wrong exception thrown for invalid OrderPriorityQueue "
          + "constructor arguments. Details: " + e.getMessage());
    }

    // 2) peekBest() exception test
    heap = new OrderPriorityQueue(3);
    try {
      heap.peekBest();
      System.out.println("Error 2.1| No exception thrown when calling peekBest on empty heap.");
      return false;
    } catch (java.util.NoSuchElementException e) {

    } catch (Exception e) {
      System.out.println("Error 2.2| Wrong exception thrown when calling peekBest on empty heap.");
      return false;
    }

    // 3) removeBest() exception test
    try {
      heap.removeBest();
      System.out.println("Error 3.1| No exception thrown when calling removeBest on empty heap.");
      return false;
    } catch (java.util.NoSuchElementException e) {

    } catch (Exception e) {
      System.out.println("Error 3.2| Wrong exception thrown when calling removeBest on empty heap");
      return false;
    }

    return true;
  }

  /**
   * Calls the test methods individually and displays their output
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("isEmpty: " + testIsEmpty());
    System.out.println("insert basic: " + testInsertBasic());
    System.out.println("percolate UP: " + testPercolateUp());
    System.out.println("insert advanced: " + testInsertAdvanced());
    System.out.println("percolate DOWN: " + testPercolateDown());
    System.out.println("peek/remove valid: " + testPeekRemove());
    System.out.println("error: " + testErrors());
  }

}
