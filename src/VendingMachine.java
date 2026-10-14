public class VendingMachine {

    public static void main(String[] args) {
        Product chips = new Product("Chips", 1.50, 2);
        Product soda = new Product("Soda", 2.00, 0);
        Product candy = new Product("Candy", 1.25, 5);

        Product[] products = { chips, soda, candy };

        System.out.println("=== Vending Machine ===");
        for (Product p : products) {
            System.out.println(p.getName() + " - $" + String.format("%.2f", p.getPrice())
                    + " (" + p.getQuantity() + " left)");
        }
    }
}