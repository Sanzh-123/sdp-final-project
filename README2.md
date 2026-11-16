## Design patterns

- **Factory Method:** `factory/MealFactory`, `PizzaMealFactory`, `BurgerMealFactory`
- **Decorator:** `decorator/MealDecorator` and topping decorators
- **Observer:** `observer/OrderSubject` and its observers
- **Strategy:** `strategy/Discount` and discount implementations, coordinated by `strategy/Context`
- **Builder:** `builder/OrderBuilder`
- **Facade:** `facade/RestaurantFacade`

## Project structure

```text
src/
├── Main.java
├── models/
├── factory/
├── decorator/
├── observer/
├── strategy/
├── builder/
├── facade/
└── ui/
    ├── RestaurantWindow.java
    └── ImageLoader.java

assets/
├── pizza.png
└── burger.png
```

## Requirements

- JDK 8 or newer
- No external libraries

## Compile on Windows PowerShell

Run these commands from the project root:

```powershell
New-Item -ItemType Directory -Force out | Out-Null; javac -Xlint:all -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
```

## Run the Swing GUI

```powershell
java -cp out Main
```

## Run the optional console version

```powershell
java -cp out Main --console
```

The Swing application supports adding pizza and burgers, selecting toppings, removing cart items, applying discounts and checking out. Checkout sends an order-status update through the Observer pattern. No payment gateway or database is connected; this is a local demonstration application.
