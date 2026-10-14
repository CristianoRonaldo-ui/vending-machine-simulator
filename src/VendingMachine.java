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
    }
}