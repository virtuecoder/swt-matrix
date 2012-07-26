
public class Customer {

  static String[] customers = {"Morgan Stanley"};

  public static void main(String[] args) {
    System.out.println(hashCode(customers[0]));
  }

  static long hashCode(String customer) {
    return ("Customer: " + customer).hashCode() + 0x80000000;
  }

}
