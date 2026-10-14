public class Meal {

    private String foodName;
    private String category;
    private String quantity;

    private int calories;
    private double protein;
    private double carbs;
    private double fat;

    private String region;
    private String budget;
    private String reason;

    public Meal(
            String foodName,
            String category,
            String quantity,
            int calories,
            double protein,
            double carbs,
            double fat,
            String region,
            String budget,
            String reason) {

        this.foodName = foodName;
        this.category = category;
        this.quantity = quantity;

        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;

        this.region = region;
        this.budget = budget;
        this.reason = reason;
    }

    public String getFoodName() { return foodName; }
    public String getCategory() { return category; }
    public String getQuantity() { return quantity; }
    public int getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFat() { return fat; }
    public String getRegion() { return region; }
    public String getBudget() { return budget; }
    public String getReason() { return reason; }
}