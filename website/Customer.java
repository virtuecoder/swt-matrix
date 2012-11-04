
public class Customer {

  static String[] customers = {"Morgan Stanley", "Fico", "Vanguard"};

  public static void main(String[] args) {
    System.out.println(hashCode(customers[1]));
  }

  static long hashCode(String customer) {
    int hashCode = ("Customer: " + customer).hashCode();
    return hashCode < 0 ? hashCode + 0x80000000 : hashCode;
  }

}
