// ======================================================
// RecipeModule.java
// User ke selected meals ki recipe dikhana
// Simple display logic - HashMap lookup
// ======================================================

import java.util.Scanner;

public class RecipeModule {

    // ── Show recipe for one meal ───────────────────────
    public static void showRecipe(
            String mealTitle, Meal meal) {

        if (meal == null) return;

        String name = meal.getFoodName();

        System.out.println("\n  ------------------------------------------");
        System.out.println("  RECIPE: " + name
            + " (" + mealTitle + ")");
        System.out.println("  ------------------------------------------");

        System.out.println("  Cook Time  : "
            + RecipeDatabase.getCookTime(name));
        System.out.println("  Difficulty : "
            + RecipeDatabase.getDifficulty(name));
        System.out.println("  Calories   : "
            + meal.getCalories() + " kcal");

        System.out.println("\n  INGREDIENTS:");
        java.util.ArrayList<Ingredient> ingredients =
            MealIngredients.getIngredients(name);

        if (ingredients.isEmpty()) {
            System.out.println("  (Standard recipe ingredients)");
        } else {
            for (Ingredient ing : ingredients) {
                System.out.println("  - " + ing.getName()
                    + " : " + formatQty(
                        ing.getQuantity(), ing.getUnit()));
            }
        }

        System.out.println("\n  PREPARATION STEPS:");
        String recipe = RecipeDatabase.getRecipe(name);
        System.out.println("  " + recipe);

        System.out.println("\n  STORAGE TIP:");
        printStorageTip(meal.getCategory());
    }

    // ── Quantity format helper ─────────────────────────
    private static String formatQty(
            double qty, String unit) {
        if (qty == (int) qty) {
            return (int) qty + " " + unit;
        }
        return qty + " " + unit;
    }

    // ── Storage tips based on meal type ────────────────
    private static void printStorageTip(String category) {

        if (category.equalsIgnoreCase("Breakfast")) {
            System.out.println("  Fresh khao - 2 ghante ke andar");
        } else if (category.equalsIgnoreCase("Lunch")
                || category.equalsIgnoreCase("Dinner")) {
            System.out.println("  Fridge mein 24 ghante store kar sakte ho");
        } else {
            System.out.println("  Turant consume karo - fresh best hai");
        }
    }

    // ── Main menu: user ko poocho kis meal ki recipe ──
    public static void showAllRecipesMenu(
            Meal detox, Meal breakfast, Meal lunch,
            Meal snack, Meal dinner,
            Meal preWorkout, Meal postWorkout) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("       SMARTFIT RECIPE GUIDE");
        System.out.println("==========================================");

        boolean continueLoop = true;

        while (continueLoop) {

            System.out.println("\nWhich recipe do you want to see?");
            System.out.println("  1. Breakfast - " + safeName(breakfast));
            System.out.println("  2. Lunch     - " + safeName(lunch));
            System.out.println("  3. Dinner    - " + safeName(dinner));
            System.out.println("  4. Detox     - " + safeName(detox));
            System.out.println("  5. Snack     - " + safeName(snack));
            System.out.println("  6. Show All Recipes");
            System.out.println("  0. Skip Recipe Module");
            System.out.print("\nEnter choice (0-6): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    showRecipe("Breakfast", breakfast);
                    break;
                case "2":
                    showRecipe("Lunch", lunch);
                    break;
                case "3":
                    showRecipe("Dinner", dinner);
                    break;
                case "4":
                    showRecipe("Detox", detox);
                    break;
                case "5":
                    showRecipe("Snack", snack);
                    break;
                case "6":
                    showRecipe("Detox", detox);
                    showRecipe("Breakfast", breakfast);
                    showRecipe("Lunch", lunch);
                    showRecipe("Snack", snack);
                    showRecipe("Dinner", dinner);
                    continueLoop = false;
                    break;
                case "0":
                    continueLoop = false;
                    break;
                default:
                    System.out.println("  Invalid choice, try again.");
            }

            if (continueLoop && !choice.equals("6")) {
                System.out.print("\nSee another recipe? (yes/no): ");
                String more = sc.nextLine().trim();
                if (!more.equalsIgnoreCase("yes")) {
                    continueLoop = false;
                }
            }
        }

        System.out.println("\n  Recipe Module Complete!");
    }

    private static String safeName(Meal m) {
        return m == null ? "Not Available" : m.getFoodName();
    }
}