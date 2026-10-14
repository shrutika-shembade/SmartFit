// ======================================================
// FoodFilter.java - UPDATED
// Added: Food preference filter (Veg/NonVeg/Vegan)
// Budget is now cumulative (high = low+medium+high)
// ======================================================

import java.util.ArrayList;

public class FoodFilter {

    // Non-veg foods list
    private static final ArrayList<String> NON_VEG = new ArrayList<>();
    // Dairy foods list (vegan skip these)
    private static final ArrayList<String> DAIRY = new ArrayList<>();

    static {
        NON_VEG.add("Chicken Rice");
        NON_VEG.add("Grilled Chicken");
        NON_VEG.add("Chicken Salad");
        NON_VEG.add("Grilled Chicken Thali");
        NON_VEG.add("Egg Curry Rice");
        NON_VEG.add("Boiled Eggs");
        NON_VEG.add("Egg Salad");
        NON_VEG.add("Boiled Egg");
        NON_VEG.add("Egg White Omelette");
        NON_VEG.add("Egg Curry");
        NON_VEG.add("Fish Curry Rice");
        NON_VEG.add("Salmon");
        NON_VEG.add("Salmon Bowl");
        NON_VEG.add("Tuna Salad");
        NON_VEG.add("Chicken Breast");

        DAIRY.add("Milk");
        DAIRY.add("Paneer");
        DAIRY.add("Curd");
        DAIRY.add("Greek Yogurt");
        DAIRY.add("Buttermilk");
        DAIRY.add("Paneer Salad");
        DAIRY.add("Palak Paneer");
        DAIRY.add("Paneer Rice");
        DAIRY.add("Grilled Paneer");
        DAIRY.add("Paneer Bhurji");
        DAIRY.add("Paneer Butter Masala");
        DAIRY.add("Paneer Paratha");
        DAIRY.add("Banana Shake");
        DAIRY.add("Chocolate Milk");
        DAIRY.add("Oats Shake");
        DAIRY.add("Turmeric Latte");
        DAIRY.add("Ashwagandha Milk");
    }

    // Main filter with food preference
    public static ArrayList<Meal> filterFoods(
            ArrayList<Meal> foods,
            String region,
            String budget,
            String foodPreference) {

        ArrayList<Meal> result = new ArrayList<>();

        for (Meal meal : foods) {

            // Region match
            boolean regionOk =
                meal.getRegion().equalsIgnoreCase(region)
                || meal.getRegion().equalsIgnoreCase("All");

            // Budget match - cumulative
            boolean budgetOk = false;
            if (budget.equalsIgnoreCase("low")) {
                budgetOk = meal.getBudget().equalsIgnoreCase("Low");
            } else if (budget.equalsIgnoreCase("medium")) {
                budgetOk = meal.getBudget().equalsIgnoreCase("Low")
                        || meal.getBudget().equalsIgnoreCase("Medium");
            } else if (budget.equalsIgnoreCase("high")) {
                budgetOk = true;
            }

            // Food preference match
            boolean prefOk = true;
            String name = meal.getFoodName();
            if (foodPreference != null) {
                if (foodPreference.equalsIgnoreCase("Veg")) {
                    if (NON_VEG.contains(name)) prefOk = false;
                } else if (foodPreference.equalsIgnoreCase("Vegan")) {
                    if (NON_VEG.contains(name) || DAIRY.contains(name)) prefOk = false;
                }
            }

            if (regionOk && budgetOk && prefOk) {
                result.add(meal);
            }
        }
        return result;
    }

    // Backward compatible - no pref param
    public static ArrayList<Meal> filterFoods(
            ArrayList<Meal> foods, String region, String budget) {
        return filterFoods(foods, region, budget, "NonVeg");
    }
}