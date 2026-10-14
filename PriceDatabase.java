// ======================================================
// PriceDatabase.java
// Har ingredient ka region-wise price store karna
// DSA: Nested HashMap
//   Outer key = ingredient name
//   Inner key = region
//   Value     = price per unit
// ══════════════════════════════════════════════════════

import java.util.HashMap;

public class PriceDatabase {

    // ── Nested HashMap ─────────────────────────────────
    // HashMap<IngredientName, HashMap<Region, Price>>
    private static HashMap<String,
                   HashMap<String, Double>> priceMap
        = new HashMap<>();

    // ── Static block: saara data load ─────────────────
    static {

        // ── price(name, mah, north, south, all) ───────
        // Prices per standard unit (kg/L/dozen etc.)

        // ════ GRAINS ════
        price("Rice",           60,  55,  58,  57);
        price("Wheat Flour",    45,  40,  43,  42);
        price("Jowar Flour",    50,  48,  49,  49);
        price("Bajra Flour",    48,  45,  47,  46);
        price("Besan",          70,  65,  68,  67);
        price("Poha Flakes",    55,  50,  52,  52);
        price("Suji",           40,  38,  39,  39);
        price("Oats",           180, 175, 178, 177);
        price("Muesli",         350, 340, 345, 345);
        price("Cornflakes",     200, 195, 198, 197);
        price("Basmati Rice",   120, 110, 115, 115);
        price("Idli Batter",    60,  55,  50,  55);
        price("Dosa Batter",    60,  55,  50,  55);
        price("Bread",          45,  42,  43,  43);

        // ════ PROTEIN ════
        price("Toor Dal",       120, 110, 115, 115);
        price("Moong Dal",      130, 120, 125, 125);
        price("Masoor Dal",     100, 95,  98,  97);
        price("Chana",          90,  85,  88,  87);
        price("Rajma",          150, 140, 145, 145);
        price("Moong",          130, 120, 125, 125);
        price("Chickpeas",      120, 115, 118, 117);
        price("Moth Dal",       100, 95,  97,  97);
        price("Peanuts",        120, 110, 115, 115);
        price("Almonds",        800, 780, 790, 790);
        price("Walnuts",        700, 680, 690, 690);
        price("Cashews",        900, 880, 890, 890);
        price("Peanut Butter",  250, 240, 245, 245);

        // ════ DAIRY ════
        price("Milk",           55,  52,  54,  53);   // per litre
        price("Paneer",         380, 360, 370, 370);  // per kg
        price("Curd",           60,  55,  58,  57);
        price("Greek Yogurt",   200, 195, 198, 197);
        price("Ghee",           550, 520, 535, 535);  // per kg
        price("Cream",          200, 190, 195, 195);
        price("Butter",         520, 500, 510, 510);

        // ════ EGGS & MEAT ════
        price("Eggs",           7,   6,   7,   7);    // per egg
        price("Chicken",        220, 200, 210, 210);  // per kg
        price("Fish",           300, 280, 250, 277);

        // ════ VEGETABLES ════
        price("Onion",          30,  25,  28,  27);
        price("Tomato",         35,  30,  28,  31);
        price("Spinach",        30,  25,  28,  27);
        price("Potato",         25,  22,  24,  23);
        price("Sweet Potato",   40,  35,  38,  37);
        price("Carrot",         40,  35,  38,  37);
        price("Beetroot",       35,  30,  32,  32);
        price("Cucumber",       25,  22,  24,  23);
        price("Lettuce",        60,  55,  58,  57);
        price("Mixed Veggies",  60,  55,  58,  57);
        price("Sweet Corn",     40,  35,  38,  37);

        // ════ FRUITS ════
        price("Banana",         3,   2,   3,   2);    // per banana
        price("Apple",          15,  12,  14,  13);   // per apple
        price("Orange",         10,  8,   9,   9);    // per orange
        price("Lemon",          5,   4,   4,   4);    // per lemon
        price("Pomegranate",    40,  35,  38,  37);   // per piece
        price("Watermelon",     20,  18,  19,  19);   // per kg
        price("Coconut",        25,  22,  18,  21);   // per coconut
        price("Amla",           3,   2,   3,   2);    // per piece
        price("Dates",          400, 380, 390, 390);  // per kg
        price("Mango",          80,  70,  75,  75);   // per kg

        // ════ OILS & FATS ════
        price("Oil",            130, 120, 125, 125);  // per litre
        price("Olive Oil",      600, 580, 590, 590);
        price("Coconut Oil",    200, 180, 170, 183);

        // ════ SPICES ════
        price("Salt",           20,  18,  19,  19);   // per kg
        price("Turmeric",       200, 180, 190, 190);  // per kg
        price("Cumin",          300, 280, 290, 290);
        price("Mustard Seeds",  180, 160, 170, 170);
        price("Biryani Masala", 350, 330, 340, 340);
        price("Sambar Powder",  300, 280, 250, 277);
        price("Tamarind",       150, 140, 130, 140);
        price("Spices",         200, 180, 190, 190);
        price("Honey",          500, 480, 490, 490);  // per kg

        // ════ OTHER ════
        price("Green Tea Bag",  5,   5,   5,   5);    // per bag
        price("Coffee Powder",  400, 380, 390, 390);  // per kg
        price("Chia Seeds",     800, 780, 790, 790);  // per kg
        price("Makhana",        700, 680, 690, 690);  // per kg
        price("Jaggery",        60,  55,  58,  57);   // per kg
        price("Cocoa Powder",   400, 380, 390, 390);
        price("Whey Protein",   3000,2900,2950,2950); // per kg
        price("Protein Bar",    80,  78,  79,  79);   // per bar
        price("Sambar",         60,  55,  50,  55);
        price("Coconut Chutney",40,  35,  30,  35);
        price("Water",          0,   0,   0,   0);    // free
    }

    // ── Helper method: price add karna ─────────────────
    // Ek baar mein 4 regions ka price set karo
    private static void price(
            String name,
            double mah,     // Maharashtra
            double north,   // NorthIndia
            double south,   // SouthIndia
            double all) {   // All/default

        HashMap<String, Double> regionPrices
            = new HashMap<>();

        regionPrices.put("Maharashtra", mah);
        regionPrices.put("NorthIndia",  north);
        regionPrices.put("SouthIndia",  south);
        regionPrices.put("All",         all);

        priceMap.put(name, regionPrices);
    }

    // ── Public method: kisi ingredient ka price do ─────
    // O(1) lookup - HashMap ka faida
    public static double getPrice(
            String ingredientName,
            String region) {

        HashMap<String, Double> regionMap
            = priceMap.get(ingredientName);

        if (regionMap == null) return 5.0; // default price

        Double p = regionMap.get(region);
        if (p == null) p = regionMap.get("All");
        if (p == null) return 5.0;

        return p;
    }
}