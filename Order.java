//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Order
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

/**
 * Basic object representing a food order at this restaurant.
 * 
 * This class contains no mutator methods, only accessors.
 * 
 * TODO: implement the Comparable<Order> interface
 */
public class Order implements Comparable<Order> {

  private static int idGenerator = 1001; // generator of unique order ID numbers

  private String dishName; // name of the food associated with this Order
  private int prepTime; // approximate number of minutes to prepare this Order
  private final int ORDER_ID; // unique order ID number

  /**
   * Constructor, initializes dish name and estimated prep time. Also sets this order's unique
   * identifier.
   * 
   * @param dishName the name of the dish contained in this order
   * @param prepTime the approximate number of minutes required to prepare this dish
   */
  public Order(String dishName, int prepTime) {
    if (prepTime < 0)
      throw new IllegalArgumentException("Invalid prep time");
    this.ORDER_ID = idGenerator++;
    this.dishName = dishName;
    this.prepTime = prepTime;
  }

  /**
   * Compares an instance of Order to another based on preptime. Returns a negative number if this
   * order had less preptime than the other, positive if it has more preptime than the other, and
   * zero if the preptimes are equal.
   * 
   * @return positive, negative, or 0 depending on order comparison
   */
  public int compareTo(Order o) {
    if (this.getPrepTime() == o.getPrepTime()) {
      return 0;
    } else if (this.getPrepTime() < o.getPrepTime()) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Returns the name of the food associated with this Order
   * 
   * @return the name of the food associated with this Order
   */
  public String getDishName() {
    return this.dishName;
  }

  /**
   * Returns the approximate number of minutes to prepare this Order
   * 
   * @return the approximate number of minutes to prepare this Order
   */
  public int getPrepTime() {
    return this.prepTime;
  }

  /**
   * Returns the unique ID number of this Order
   * 
   * @return the unique ID number of this Order
   */
  public int getID() {
    return this.ORDER_ID;
  }

  /**
   * Returns a String representation of this Order in the format "ID: dishname (prepTime)"
   * 
   * @return a String representation of this Order
   */
  @Override
  public String toString() {
    return this.ORDER_ID + ": " + this.dishName + " (" + this.prepTime + ")";
  }

  /**
   * This method resets the idGenerator to 1001. This method must be used in your tester methods
   * only.
   */
  public static void resetIDGenerator() {
    idGenerator = 1001;
  }

}
