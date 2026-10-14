public class VendingMachine {

    public static double purchase(Product product, double payment)
            throws OutOfStockException, InsufficientPaymentException {
        if (payment < product.getPrice()) {
            throw new InsufficientPaymentException("Not enough money for " + product.getName()
                    + ": need $" + String.format("%.2f", product.getPrice())
                    + ", got $" + String.format("%.2f", payment));
        }
        product.dispense();
        return payment - product.getPrice();
    }

    public static void attemptPurchase(Product product, double payment) {
        try {
            double change = purchase(product, payment);
            System.out.println("Bought " + product.getName()
                    + ". Change: $" + String.format("%.2f", change));
        } catch (OutOfStockException e) {
            System.out.println("Sorry: " + e.getMessage());
        } catch (InsufficientPaymentException e) {
            System.out.println("Payment error: " + e.getMessage());
        }
    }

    public static void runTests() {
        System.out.println();
        System.out.println("=== Running Tests ===");

        // Test 1: successful purchase returns correct change and reduces stock
        try {
            Product water = new Product("Water", 1.50, 1);
            double change = purchase(water, 2.00);
            if (Math.abs(change - 0.50) < 0.0001 && water.getQuantity() == 0) {
                System.out.println("Test 1 PASS: correct change and stock decreased");
            } else {
                System.out.println("Test 1 FAIL: change = " + change
                        + ", quantity = " + water.getQuantity());
            }
        } catch (Exception e) {
            System.out.println("Test 1 FAIL: unexpected exception - " + e.getMessage());
        }

        // Test 2: buying an out-of-stock product must throw OutOfStockException
        try {
            Product juice = new Product("Juice", 2.00, 0);
            purchase(juice, 5.00);
            System.out.println("Test 2 FAIL: no exception was thrown");
        } catch (OutOfStockException e) {
            System.out.println("Test 2 PASS: OutOfStockException thrown");
        } catch (InsufficientPaymentException e) {
            System.out.println("Test 2 FAIL: wrong exception type");
        }

        // Test 3: insufficient payment must throw InsufficientPaymentException
        // and must NOT reduce the stock
        Product gum = new Product("Gum", 1.00, 3);
        try {
            purchase(gum, 0.50);
            System.out.println("Test 3 FAIL: no exception was thrown");
        } catch (InsufficientPaymentException e) {
            if (gum.getQuantity() == 3) {
                System.out.println("Test 3 PASS: InsufficientPaymentException thrown, stock unchanged");
            } else {
                System.out.println("Test 3 FAIL: stock changed to " + gum.getQuantity());
            }
        } catch (OutOfStockException e) {
            System.out.println("Test 3 FAIL: wrong exception type");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Product chips = new Product("Chips", 1.50, 2);
        Product soda = new Product("Soda", 2.00, 0);
        Product candy = new Product("Candy", 1.25, 5);

        Product[] products = { chips, soda, candy };

        System.out.println();
        System.out.println("=== Vending Machine ===");
        for (Product p : products) {
            System.out.println(p.getName() + " - $" + String.format("%.2f", p.getPrice())
                    + " (" + p.getQuantity() + " left)");
        }

        System.out.println();
        attemptPurchase(chips, 2.00);
        attemptPurchase(soda, 5.00);
        attemptPurchase(candy, 1.00);

        runTests();
    }
}