// ======================================================
// GroceryGenerator.java - FIXED VERSION
// Bug fixes:
// 1. Cost calculation fixed for all unit types
// 2. Spices showing Rs.0 fixed
// 3. Better quantity display
// DSA: HashMap, TreeMap, ArrayList, Collections.sort
// ======================================================

import java.io.*;
import java.util.*;

public class GroceryGenerator {

    // ── MAIN METHOD ────────────────────────────────────
    public static void generateGroceryList(
            Meal detox,
            Meal breakfast,
            Meal lunch,
            Meal snack,
            Meal dinner,
            Meal preWorkout,
            Meal postWorkout,
            User user) {

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("       SMARTFIT WEEKLY GROCERY LIST");
        System.out.println("==========================================");

        String region = user.getRegion();
        String budget = user.getBudget();

        // All selected meals
        Meal[] allMeals = {
            detox, breakfast, lunch,
            snack, dinner, preWorkout, postWorkout
        };

        // ── HashMap: merge duplicate ingredients ───────
        // Key = ingredient name
        // Value = Ingredient object with total quantity
        HashMap<String, Ingredient> mergedMap
            = new HashMap<>();

        // Process each meal
        for (Meal meal : allMeals) {

            if (meal == null) continue;

            ArrayList<Ingredient> ingredients =
                MealIngredients.getIngredients(
                    meal.getFoodName());

            for (Ingredient ing : ingredients) {

                String key = ing.getName()
                    + "_" + ing.getUnit(); // avoid unit mismatch

                if (mergedMap.containsKey(key)) {
                    mergedMap.get(key)
                             .addQuantity(ing.getQuantity());
                } else {
                    mergedMap.put(key, new Ingredient(
                        ing.getName(),
                        ing.getUnit(),
                        ing.getQuantity(),
                        ing.getCategory()));
                }
            }
        }

        // ── Group by category (TreeMap = sorted) ───────
        TreeMap<String, ArrayList<Ingredient>> categoryMap
            = new TreeMap<>();

        for (Ingredient ing : mergedMap.values()) {

            String cat = ing.getCategory();

            if (!categoryMap.containsKey(cat)) {
                categoryMap.put(cat, new ArrayList<>());
            }
            categoryMap.get(cat).add(ing);
        }

        // ── Display and calculate total cost ───────────
        double totalCost = 0;

        System.out.println("\n  Region : " + region);
        System.out.println("  Budget : " + budget);
        System.out.println("------------------------------------------");

        for (String category : categoryMap.keySet()) {

            System.out.println("\n  === "
                + category.toUpperCase() + " ===");

            ArrayList<Ingredient> items =
                categoryMap.get(category);

            // Sort by name
            Collections.sort(items,
                (a, b) -> a.getName().compareTo(b.getName()));

            for (Ingredient ing : items) {

                String qtyStr = formatQuantity(
                    ing.getQuantity(), ing.getUnit());

                // Fixed cost calculation
                double cost = calculateCost(
                    ing.getName(),
                    ing.getQuantity(),
                    ing.getUnit(),
                    region);

                totalCost += cost;

                // Show minimum Rs.1 for any ingredient
                String costStr = cost < 1
                    ? "< Rs.1"
                    : "Rs." + (int) cost;

                System.out.printf(
                    "  %-28s %-14s %s%n",
                    ing.getName(), qtyStr, costStr);
            }
        }

        // ── Total ──────────────────────────────────────
        System.out.println("\n------------------------------------------");
        System.out.printf(
            "  TOTAL ESTIMATED COST  : Rs.%.0f%n",
            totalCost);
        System.out.printf(
            "  DAILY COST (approx)   : Rs.%.0f%n",
            totalCost / 7);
        System.out.printf(
            "  MONTHLY COST (approx) : Rs.%.0f%n",
            totalCost * 4);
        System.out.println("------------------------------------------");

        // Budget analysis
        printBudgetAnalysis(totalCost, budget);

        // Pantry reminder
        printPantryItems();

        // Save to file
        saveToFile(categoryMap, totalCost, region, user);
    }

    // ── FIXED: Quantity format ─────────────────────────
    private static String formatQuantity(
            double qty, String unit) {

        if (unit.equals("g") && qty >= 1000) {
            return String.format("%.2f kg", qty / 1000.0);
        }
        if (unit.equals("ml") && qty >= 1000) {
            return String.format("%.1f L", qty / 1000.0);
        }
        if (qty == (int) qty) {
            return (int) qty + " " + unit;
        }
        return String.format("%.1f %s", qty, unit);
    }

    // ── FIXED: Cost calculation ────────────────────────
    // Bug was here - spices and small quantities showed Rs.0
    private static double calculateCost(
            String name,
            double qty,
            String unit,
            String region) {

        // Get price per standard unit from database
        double unitPrice = PriceDatabase.getPrice(
            name, region);

        double cost = 0;

        switch (unit) {

            case "g":
                // Price is per kg
                // qty grams / 1000 * price per kg
                cost = (qty / 1000.0) * unitPrice;
                // Minimum Rs.2 for any ingredient bought
                if (cost < 2 && cost > 0) cost = 2;
                break;

            case "kg":
                cost = qty * unitPrice;
                break;

            case "ml":
                // Price is per litre
                cost = (qty / 1000.0) * unitPrice;
                if (cost < 2 && cost > 0) cost = 2;
                break;

            case "L":
                cost = qty * unitPrice;
                break;

            case "pieces":
                // Price is per piece
                cost = qty * unitPrice;
                break;

            case "tsp":
                // 1 tsp = approx 5g
                // Price is per kg
                cost = (qty * 5.0 / 1000.0) * unitPrice;
                // Minimum Rs.2 for spices
                if (cost < 2 && cost > 0) cost = 2;
                break;

            case "tbsp":
                // 1 tbsp = approx 15g
                cost = (qty * 15.0 / 1000.0) * unitPrice;
                if (cost < 2 && cost > 0) cost = 2;
                break;

            case "pinch":
                // Very small amount - flat Rs.1
                cost = 1;
                break;

            case "scoop":
                // Protein scoop = Rs.100 per scoop approx
                cost = qty * 100;
                break;

            default:
                cost = qty * unitPrice;
                break;
        }

        return cost;
    }

    // ── Budget analysis ────────────────────────────────
    private static void printBudgetAnalysis(
            double totalCost, String budget) {

        System.out.println("\n  === BUDGET ANALYSIS ===");

        if (totalCost <= 300) {
            System.out.println("  Status  : EXCELLENT - Very budget friendly!");
        } else if (totalCost <= 600) {
            System.out.println("  Status  : GOOD - Reasonable daily cost");
        } else if (totalCost <= 1000) {
            System.out.println("  Status  : MODERATE - Consider some swaps");
            printCheaperSwaps();
        } else {
            System.out.println("  Status  : HIGH - Many cheaper alternatives available");
            printCheaperSwaps();
        }
    }

    // ── Cheaper alternatives ───────────────────────────
    private static void printCheaperSwaps() {
        System.out.println("\n  MONEY SAVING SWAPS:");
        System.out.println("  > Paneer    -> Sprouts/Tofu   (Save ~Rs.100)");
        System.out.println("  > Almonds   -> Peanuts        (Save ~Rs.50)");
        System.out.println("  > Oats      -> Poha/Upma      (Save ~Rs.60)");
        System.out.println("  > Chicken   -> Eggs           (Save ~Rs.100)");
        System.out.println("  > Buy staples in bulk monthly (Save 15-20%)");
    }

    // ── Pantry reminder ────────────────────────────────
    private static void printPantryItems() {
        System.out.println("\n  === PANTRY CHECK ===");
        System.out.println("  You might already have these:");
        System.out.println("  > Salt, Turmeric, Cumin, Mustard Seeds");
        System.out.println("  > Oil, Ghee");
        System.out.println("  > Onion, Garlic, Ginger");
        System.out.println("  Check kitchen before buying!");
    }

    // ── Save to file ───────────────────────────────────
    private static void saveToFile(
            TreeMap<String, ArrayList<Ingredient>> categoryMap,
            double totalCost,
            String region,
            User user) {

        System.out.println("\n  Saving to file...");

        try {
            FileWriter fw = new FileWriter(
                "SmartFit_GroceryList.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("==========================================\n");
            bw.write("    SMARTFIT DAILY GROCERY LIST\n");
            bw.write("==========================================\n");
            bw.write("Region : " + region + "\n");
            bw.write("Budget : " + user.getBudget() + "\n");
            bw.write("Goal   : " + user.getGoalValue() + "\n");
            bw.write("==========================================\n\n");

            for (String cat : categoryMap.keySet()) {

                bw.write("-- " + cat.toUpperCase() + " --\n");

                for (Ingredient ing : categoryMap.get(cat)) {

                    String qty = formatQuantity(
                        ing.getQuantity(), ing.getUnit());

                    double cost = calculateCost(
                        ing.getName(),
                        ing.getQuantity(),
                        ing.getUnit(),
                        region);

                    bw.write(String.format(
                        "  %-28s %-14s Rs.%.0f\n",
                        ing.getName(), qty, cost));
                }
                bw.write("\n");
            }

            bw.write("==========================================\n");
            bw.write(String.format(
                "TOTAL COST    : Rs.%.0f\n", totalCost));
            bw.write(String.format(
                "DAILY COST    : Rs.%.0f\n", totalCost / 7));
            bw.write(String.format(
                "MONTHLY COST  : Rs.%.0f\n", totalCost * 4));
            bw.write("==========================================\n");
            bw.write("Generated by SmartFit\n");

            bw.close();
            fw.close();

            System.out.println("  Saved: SmartFit_GroceryList.txt");
            System.out.println("  Check your project folder!");

        } catch (IOException e) {
            System.out.println("  File save failed: "
                + e.getMessage());
        }
    }
}