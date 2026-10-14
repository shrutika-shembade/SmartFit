// ======================================================
// MealIngredients.java
// Har meal ke ingredients ka mapping
// DSA Concept: HashMap - key=mealName, value=ArrayList of Ingredients
// Jaise dictionary - meal name dhundo ingredients milenge
// ======================================================

import java.util.ArrayList;
import java.util.HashMap;

public class MealIngredients {

    // ── HashMap: meal name -> ingredients list ─────────
    // Key   = meal ka naam  (String)
    // Value = uske ingredients (ArrayList<Ingredient>)
    private static HashMap<String, ArrayList<Ingredient>> mealMap
        = new HashMap<>();

    // ── Static block: saara data ek baar load hoga ─────
    static {

        // ════════════════════════════════════════════════
        // DETOX DRINKS
        // ════════════════════════════════════════════════

        add("Lemon Water",      list(
            ing("Lemon",        "pieces", 1,    "Fruits"),
            ing("Water",        "ml",     250,  "Other"),
            ing("Honey",        "tsp",    1,    "Spices")));

        add("Jeera Water",      list(
            ing("Jeera",        "tsp",    1,    "Spices"),
            ing("Water",        "ml",     250,  "Other")));

        add("Buttermilk",       list(
            ing("Curd",         "g",      100,  "Dairy"),
            ing("Water",        "ml",     150,  "Other"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Green Tea",        list(
            ing("Green Tea Bag","pieces", 1,    "Other"),
            ing("Water",        "ml",     200,  "Other")));

        add("Beetroot Juice",   list(
            ing("Beetroot",     "g",      100,  "Vegetables"),
            ing("Water",        "ml",     100,  "Other"),
            ing("Lemon",        "pieces", 0.5,  "Fruits")));

        add("Spinach Juice",    list(
            ing("Spinach",      "g",      100,  "Vegetables"),
            ing("Water",        "ml",     100,  "Other"),
            ing("Lemon",        "pieces", 0.5,  "Fruits")));

        add("Amla Juice",       list(
            ing("Amla",         "pieces", 3,    "Fruits"),
            ing("Water",        "ml",     200,  "Other")));

        add("Coconut Water",    list(
            ing("Coconut",      "pieces", 1,    "Fruits")));

        add("Pomegranate Juice",list(
            ing("Pomegranate",  "pieces", 1,    "Fruits")));

        add("Turmeric Latte",   list(
            ing("Milk",         "ml",     200,  "Dairy"),
            ing("Turmeric",     "tsp",    0.5,  "Spices"),
            ing("Honey",        "tsp",    1,    "Spices")));

        add("Ginger Water",     list(
            ing("Ginger",       "g",      10,   "Spices"),
            ing("Water",        "ml",     250,  "Other")));

        add("Mint Water",       list(
            ing("Mint Leaves",  "g",      10,   "Vegetables"),
            ing("Water",        "ml",     250,  "Other")));

        add("Orange Juice",     list(
            ing("Orange",       "pieces", 2,    "Fruits")));

        add("Carrot Juice",     list(
            ing("Carrot",       "g",      150,  "Vegetables"),
            ing("Water",        "ml",     100,  "Other")));

        add("Black Coffee",     list(
            ing("Coffee Powder","tsp",    1,    "Other"),
            ing("Water",        "ml",     150,  "Other")));

        // ════════════════════════════════════════════════
        // BREAKFAST
        // ════════════════════════════════════════════════

        add("Poha",             list(
            ing("Poha Flakes",  "g",      80,   "Grains"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Peanuts",      "g",      20,   "Protein"),
            ing("Oil",          "ml",     10,   "Oils"),
            ing("Turmeric",     "tsp",    0.5,  "Spices"),
            ing("Mustard Seeds","tsp",    0.5,  "Spices")));

        add("Sprouts",          list(
            ing("Moong",        "g",      80,   "Protein"),
            ing("Lemon",        "pieces", 0.5,  "Fruits"),
            ing("Salt",         "pinch",  1,    "Spices"),
            ing("Onion",        "g",      30,   "Vegetables")));

        add("Oats",             list(
            ing("Oats",         "g",      80,   "Grains"),
            ing("Milk",         "ml",     150,  "Dairy"),
            ing("Banana",       "pieces", 0.5,  "Fruits"),
            ing("Honey",        "tsp",    1,    "Spices")));

        add("Idli",             list(
            ing("Idli Batter",  "g",      150,  "Grains"),
            ing("Sambar",       "g",      100,  "Protein"),
            ing("Coconut Chutney","g",    50,   "Spices")));

        add("Dosa",             list(
            ing("Dosa Batter",  "g",      150,  "Grains"),
            ing("Oil",          "ml",     10,   "Oils"),
            ing("Sambar",       "g",      100,  "Protein")));

        add("Moong Chilla",     list(
            ing("Moong Dal",    "g",      80,   "Protein"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Tomato",       "g",      30,   "Vegetables"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Besan Chilla",     list(
            ing("Besan",        "g",      80,   "Grains"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Tomato",       "g",      30,   "Vegetables"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Upma",             list(
            ing("Suji",         "g",      80,   "Grains"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils"),
            ing("Mustard Seeds","tsp",    0.5,  "Spices")));

        add("Boiled Egg",       list(
            ing("Eggs",         "pieces", 2,    "Protein")));

        add("Fruit Bowl",       list(
            ing("Banana",       "pieces", 1,    "Fruits"),
            ing("Apple",        "pieces", 0.5,  "Fruits"),
            ing("Pomegranate",  "pieces", 0.5,  "Fruits")));

        add("Banana",           list(
            ing("Banana",       "pieces", 1,    "Fruits")));

        add("Cornflakes",       list(
            ing("Cornflakes",   "g",      60,   "Grains"),
            ing("Milk",         "ml",     150,  "Dairy")));

        add("Muesli",           list(
            ing("Muesli",       "g",      70,   "Grains"),
            ing("Milk",         "ml",     150,  "Dairy")));

        add("Peanut Butter Toast", list(
            ing("Bread",        "pieces", 2,    "Grains"),
            ing("Peanut Butter","g",      30,   "Protein")));

        add("Egg White Omelette", list(
            ing("Eggs",         "pieces", 3,    "Protein"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Oil",          "ml",     5,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Paneer Paratha",   list(
            ing("Wheat Flour",  "g",      100,  "Grains"),
            ing("Paneer",       "g",      80,   "Dairy"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Greek Yogurt Bowl",list(
            ing("Greek Yogurt", "g",      150,  "Dairy"),
            ing("Honey",        "tsp",    1,    "Spices"),
            ing("Banana",       "pieces", 0.5,  "Fruits")));

        add("Chia Pudding",     list(
            ing("Chia Seeds",   "g",      30,   "Other"),
            ing("Milk",         "ml",     200,  "Dairy"),
            ing("Honey",        "tsp",    1,    "Spices")));

        // ════════════════════════════════════════════════
        // LUNCH
        // ════════════════════════════════════════════════

        add("Dal Rice",         list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Toor Dal",     "g",      60,   "Protein"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils"),
            ing("Turmeric",     "tsp",    0.5,  "Spices"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Khichdi",          list(
            ing("Rice",         "g",      80,   "Grains"),
            ing("Moong Dal",    "g",      60,   "Protein"),
            ing("Turmeric",     "tsp",    0.5,  "Spices"),
            ing("Ghee",         "tsp",    1,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Dal Roti",         list(
            ing("Wheat Flour",  "g",      120,  "Grains"),
            ing("Toor Dal",     "g",      80,   "Protein"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Jowar Bhakri",     list(
            ing("Jowar Flour",  "g",      120,  "Grains"),
            ing("Curd",         "g",      50,   "Dairy")));

        add("Pithla Bhakri",    list(
            ing("Jowar Flour",  "g",      120,  "Grains"),
            ing("Besan",        "g",      60,   "Grains"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Rajma Rice",       list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Rajma",        "g",      80,   "Protein"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Palak Paneer",     list(
            ing("Spinach",      "g",      100,  "Vegetables"),
            ing("Paneer",       "g",      80,   "Dairy"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils"),
            ing("Cream",        "g",      20,   "Dairy")));

        add("Paneer Rice",      list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Paneer",       "g",      100,  "Dairy"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Veg Biryani",      list(
            ing("Basmati Rice", "g",      100,  "Grains"),
            ing("Mixed Veggies","g",      150,  "Vegetables"),
            ing("Onion",        "g",      60,   "Vegetables"),
            ing("Oil",          "ml",     15,   "Oils"),
            ing("Biryani Masala","tsp",   1,    "Spices")));

        add("Chole Rice",       list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Chickpeas",    "g",      80,   "Protein"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Sambar Rice",      list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Toor Dal",     "g",      60,   "Protein"),
            ing("Mixed Veggies","g",      100,  "Vegetables"),
            ing("Tamarind",     "g",      10,   "Spices"),
            ing("Sambar Powder","tsp",    1,    "Spices")));

        add("Sprouts Khichdi",  list(
            ing("Rice",         "g",      80,   "Grains"),
            ing("Moong",        "g",      60,   "Protein"),
            ing("Ghee",         "tsp",    1,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Missal Pav",       list(
            ing("Pav Bread",    "pieces", 2,    "Grains"),
            ing("Moth Dal",     "g",      80,   "Protein"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Egg Curry Rice",   list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Eggs",         "pieces", 2,    "Protein"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Chicken Rice",     list(
            ing("Rice",         "g",      100,  "Grains"),
            ing("Chicken",      "g",      150,  "Protein"),
            ing("Onion",        "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Oil",          "ml",     15,   "Oils")));

        add("Mixed Dal",        list(
            ing("Toor Dal",     "g",      40,   "Protein"),
            ing("Moong Dal",    "g",      30,   "Protein"),
            ing("Masoor Dal",   "g",      30,   "Protein"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        // ════════════════════════════════════════════════
        // EVENING SNACKS
        // ════════════════════════════════════════════════

        add("Roasted Chana",    list(
            ing("Chana",        "g",      50,   "Protein")));

        add("Makhana",          list(
            ing("Makhana",      "g",      30,   "Other")));

        add("Peanuts",          list(
            ing("Peanuts",      "g",      30,   "Protein")));

        add("Dates",            list(
            ing("Dates",        "pieces", 4,    "Fruits")));

        add("Almonds",          list(
            ing("Almonds",      "g",      20,   "Protein")));

        add("Walnuts",          list(
            ing("Walnuts",      "g",      20,   "Protein")));

        add("Greek Yogurt",     list(
            ing("Greek Yogurt", "g",      120,  "Dairy")));

        add("Sweet Potato",     list(
            ing("Sweet Potato", "g",      100,  "Vegetables")));

        add("Sprouts Chaat",    list(
            ing("Moong",        "g",      60,   "Protein"),
            ing("Onion",        "g",      20,   "Vegetables"),
            ing("Tomato",       "g",      20,   "Vegetables"),
            ing("Lemon",        "pieces", 0.5,  "Fruits")));

        add("Boiled Egg",       list(
            ing("Eggs",         "pieces", 1,    "Protein")));

        add("Protein Bar",      list(
            ing("Protein Bar",  "pieces", 1,    "Other")));

        add("Corn Chaat",       list(
            ing("Sweet Corn",   "g",      100,  "Vegetables"),
            ing("Onion",        "g",      20,   "Vegetables"),
            ing("Lemon",        "pieces", 0.5,  "Fruits"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Vegetable Soup",   list(
            ing("Mixed Veggies","g",      150,  "Vegetables"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Oil",          "ml",     5,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        // ════════════════════════════════════════════════
        // DINNER
        // ════════════════════════════════════════════════

        add("Dal Khichdi",      list(
            ing("Rice",         "g",      80,   "Grains"),
            ing("Moong Dal",    "g",      60,   "Protein"),
            ing("Ghee",         "tsp",    1,    "Oils"),
            ing("Cumin",        "tsp",    0.5,  "Spices"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Moong Khichdi",    list(
            ing("Rice",         "g",      80,   "Grains"),
            ing("Moong Dal",    "g",      70,   "Protein"),
            ing("Ghee",         "tsp",    1,    "Oils"),
            ing("Turmeric",     "tsp",    0.5,  "Spices")));

        add("Dal Roti",         list(
            ing("Wheat Flour",  "g",      120,  "Grains"),
            ing("Toor Dal",     "g",      80,   "Protein"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     10,   "Oils")));

        add("Jowar Roti",       list(
            ing("Jowar Flour",  "g",      120,  "Grains"),
            ing("Water",        "ml",     80,   "Other")));

        add("Moong Dal",        list(
            ing("Moong Dal",    "g",      80,   "Protein"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Tomato",       "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Turmeric",     "tsp",    0.5,  "Spices")));

        add("Paneer Salad",     list(
            ing("Paneer",       "g",      100,  "Dairy"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Cucumber",     "g",      50,   "Vegetables"),
            ing("Onion",        "g",      30,   "Vegetables"),
            ing("Lemon",        "pieces", 0.5,  "Fruits")));

        add("Boiled Eggs",      list(
            ing("Eggs",         "pieces", 3,    "Protein")));

        add("Grilled Paneer",   list(
            ing("Paneer",       "g",      150,  "Dairy"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Spices",       "tsp",    1,    "Spices")));

        add("Chicken Salad",    list(
            ing("Chicken",      "g",      150,  "Protein"),
            ing("Lettuce",      "g",      50,   "Vegetables"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Cucumber",     "g",      50,   "Vegetables"),
            ing("Olive Oil",    "tsp",    1,    "Oils")));

        add("Grilled Chicken",  list(
            ing("Chicken",      "g",      150,  "Protein"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Spices",       "tsp",    1,    "Spices")));

        add("Tomato Soup",      list(
            ing("Tomato",       "g",      200,  "Vegetables"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Fruit Salad",      list(
            ing("Banana",       "pieces", 1,    "Fruits"),
            ing("Apple",        "pieces", 0.5,  "Fruits"),
            ing("Pomegranate",  "pieces", 0.5,  "Fruits"),
            ing("Orange",       "pieces", 0.5,  "Fruits")));

        add("Lentil Soup",      list(
            ing("Masoor Dal",   "g",      80,   "Protein"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Onion",        "g",      40,   "Vegetables"),
            ing("Oil",          "ml",     8,    "Oils"),
            ing("Cumin",        "tsp",    0.5,  "Spices")));

        // ════════════════════════════════════════════════
        // PRE WORKOUT
        // ════════════════════════════════════════════════

        add("Banana",           list(
            ing("Banana",       "pieces", 1,    "Fruits")));

        add("Peanut Chikki",    list(
            ing("Peanuts",      "g",      30,   "Protein"),
            ing("Jaggery",      "g",      20,   "Other")));

        add("Watermelon",       list(
            ing("Watermelon",   "g",      300,  "Fruits")));

        add("Coconut Water",    list(
            ing("Coconut",      "pieces", 1,    "Fruits")));

        // ════════════════════════════════════════════════
        // POST WORKOUT
        // ════════════════════════════════════════════════

        add("Milk",             list(
            ing("Milk",         "ml",     300,  "Dairy")));

        add("Banana Shake",     list(
            ing("Banana",       "pieces", 1,    "Fruits"),
            ing("Milk",         "ml",     250,  "Dairy"),
            ing("Honey",        "tsp",    1,    "Spices")));

        add("Protein Shake",    list(
            ing("Whey Protein", "scoop",  1,    "Other"),
            ing("Milk",         "ml",     250,  "Dairy")));

        add("Paneer",           list(
            ing("Paneer",       "g",      100,  "Dairy")));

        add("Chocolate Milk",   list(
            ing("Milk",         "ml",     250,  "Dairy"),
            ing("Cocoa Powder", "tsp",    1,    "Other")));

        add("Oats Shake",       list(
            ing("Oats",         "g",      60,   "Grains"),
            ing("Milk",         "ml",     250,  "Dairy"),
            ing("Banana",       "pieces", 1,    "Fruits")));

        add("Egg Salad",        list(
            ing("Eggs",         "pieces", 3,    "Protein"),
            ing("Tomato",       "g",      50,   "Vegetables"),
            ing("Cucumber",     "g",      50,   "Vegetables"),
            ing("Salt",         "pinch",  1,    "Spices")));

        add("Sweet Potato",     list(
            ing("Sweet Potato", "g",      150,  "Vegetables")));

        add("Sprouts",          list(
            ing("Moong",        "g",      80,   "Protein")));

        add("Mixed Nuts",       list(
            ing("Almonds",      "g",      10,   "Protein"),
            ing("Walnuts",      "g",      10,   "Protein"),
            ing("Cashews",      "g",      10,   "Protein")));
    }

    // ── Helper: ingredient banana ──────────────────────
    // Short naam - code likhna aasaan hoga
    private static Ingredient ing(
            String name, String unit,
            double qty, String cat) {
        return new Ingredient(name, unit, qty, cat);
    }

    // ── Helper: list banana ────────────────────────────
    private static ArrayList<Ingredient> list(
            Ingredient... items) {
        ArrayList<Ingredient> l = new ArrayList<>();
        for (Ingredient i : items) l.add(i);
        return l;
    }

    // ── Helper: map mein add karna ─────────────────────
    private static void add(
            String mealName,
            ArrayList<Ingredient> ingredients) {
        mealMap.put(mealName, ingredients);
    }

    // ── Public method: meal ke ingredients do ──────────
    public static ArrayList<Ingredient> getIngredients(
            String mealName) {
        // HashMap.get() - O(1) time complexity
        ArrayList<Ingredient> result = mealMap.get(mealName);
        if (result == null) return new ArrayList<>();
        return result;
    }

    // ── Check if meal exists in map ────────────────────
    public static boolean hasMeal(String mealName) {
        return mealMap.containsKey(mealName);
    }
}