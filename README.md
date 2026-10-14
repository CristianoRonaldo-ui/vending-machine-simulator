# Vending Machine Simulator

A small Java console program that simulates a vending machine, built to practise **custom checked exceptions**. Instead of throwing a generic error, the machine throws `OutOfStockException` or `InsufficientPaymentException`, and the caller handles each case differently

## Features

- Displays a product menu with price and remaining stock
- Buys a product and returns the correct change
- Throws `OutOfStockException` when a product has no stock left
- Throws `InsufficientPaymentException` when the payment is too low
- Checks the payment **before** dispensing, so a failed purchase never reduces stock
- Built-in manual tests that print PASS / FAIL

## Project Structure

```
vending-machine-simulator/
├── .gitignore
├── README.md
└── src/
    ├── OutOfStockException.java          # custom checked exception
    ├── InsufficientPaymentException.java # custom checked exception
    ├── Product.java                      # name, price, quantity + dispense()
    └── VendingMachine.java               # purchase logic, main, tests
```

## How to Run

Requires a JDK (Java 17+ recommended).

```bash
javac src/*.java
java -cp src VendingMachine
```

## Sample Output

```
=== Vending Machine ===
Chips - $1.50 (2 left)
Soda - $2.00 (0 left)
Candy - $1.25 (5 left)

Bought Chips. Change: $0.50
Sorry: Soda is out of stock
Payment error: Not enough money for Candy: need $1.25, got $1.00

=== Running Tests ===
Test 1 PASS: correct change and stock decreased
Test 2 PASS: OutOfStockException thrown
Test 3 PASS: InsufficientPaymentException thrown, stock unchanged
```

## How It Works

1. `Product.dispense()` throws `OutOfStockException` if `quantity == 0`, otherwise reduces stock by 1.
2. `VendingMachine.purchase()` first checks the payment and throws `InsufficientPaymentException` if it is too low. Only then does it call `dispense()`. The `OutOfStockException` from `dispense()` is not caught here, so it propagates up to the caller
3. `attemptPurchase()` wraps the call in `try` with **two separate `catch` blocks**, so each error gets its own message.

## Complexity

| Operation | Time | Why |
|---|---|---|
| `dispense()` | O(1) | one comparison and one decrement |
| `purchase()` | O(1) | one comparison + `dispense()` |
| Print menu | O(n) | loops over n products |

## What I Learned

- **Custom exceptions are just classes** that `extend Exception` and pass the message to the parent with `super(message)`.
- **Checked vs unchecked:** a class extending `Exception` is *checked*, so the compiler forces every caller to either `catch` it or declare it with `throws`. My earlier project used `IllegalArgumentException`, which extends `RuntimeException` (*unchecked*), so no `throws` was needed.
- **Exceptions propagate:** `purchase()` never throws `OutOfStockException` itself, but must still declare it because `dispense()` can throw it.
- **Validate before mutate:** checking the payment before calling `dispense()` keeps the stock correct when a purchase fails. Test 3 proves this.
- **Testing that an exception happens:** put a FAIL message right after the call; if the exception is thrown correctly, that line is skipped and the `catch` prints PASS
- **Variable scope in try/catch:** a variable declared inside `try { }` is not visible in `catch { }`, so the test product has to be created before the `try`.
- **Compile all files together** with `javac src/*.java`, because the classes depend on each other