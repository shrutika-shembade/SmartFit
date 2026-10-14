// ======================================================
// Ingredient.java
// Ek ingredient ka blueprint - OOP Class concept
// Jaise Meal.java aur Exercise.java hain
// ======================================================

public class Ingredient {

    // ── Fields ─────────────────────────────────────────
    private String name;        // Ingredient ka naam - Rice, Dal, Paneer
    private String unit;        // Measurement unit - g, kg, ml, L, pieces
    private double quantity;    // Kitna chahiye
    private String category;    // Grains, Dairy, Vegetables, Fruits, Spices, Oils

    // ── Constructor ────────────────────────────────────
    public Ingredient(
            String name,
            String unit,
            double quantity,
            String category) {

        this.name     = name;
        this.unit     = unit;
        this.quantity = quantity;
        this.category = category;
    }

    // ── Getters ────────────────────────────────────────
    public String getName()     { return name; }
    public String getUnit()     { return unit; }
    public double getQuantity() { return quantity; }
    public String getCategory() { return category; }

    // ── Setter - quantity update ke liye ───────────────
    // Jab same ingredient baar baar aaye to quantity badhao
    public void addQuantity(double more) {
        this.quantity += more;
    }
}