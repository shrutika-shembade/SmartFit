// ======================================================
// RecipeDatabase.java
// Har meal ke cooking steps store karna
// DSA: HashMap - meal name -> recipe steps mapping
// ======================================================

import java.util.HashMap;

public class RecipeDatabase {

    // ── HashMap: meal name -> recipe text ──────────────
    private static HashMap<String, String> recipeMap
        = new HashMap<>();

    private static HashMap<String, String> cookTimeMap
        = new HashMap<>();

    private static HashMap<String, String> difficultyMap
        = new HashMap<>();

    static {

        // ════ BREAKFAST RECIPES ════

        recipeMap.put("Poha",
            "1. Poha ko paani se 2 baar dho lo\n" +
            "2. 5 min paani mein bhigo ke rakho\n" +
            "3. Kadhai mein oil garam karo\n" +
            "4. Mustard seeds daalo - tadakne do\n" +
            "5. Onion daalo - golden hone tak bhuno\n" +
            "6. Turmeric powder daalo\n" +
            "7. Bheega hua poha daalo\n" +
            "8. Salt daalo - mix karo\n" +
            "9. 3-4 min dhak ke pakao\n" +
            "10. Peanuts aur lemon juice daal ke serve karo");
        cookTimeMap.put("Poha", "15 minutes");
        difficultyMap.put("Poha", "Easy");

        recipeMap.put("Sprouts",
            "1. Moong dal raat bhar bhigo do\n" +
            "2. Subah paani nitar do\n" +
            "3. Gile cloth mein bandh ke rakho\n" +
            "4. 1-2 din mein sprouts nikal aayenge\n" +
            "5. Onion, tomato, lemon mix karo\n" +
            "6. Salt daalo - serve karo");
        cookTimeMap.put("Sprouts", "5 minutes (+ 2 days sprouting)");
        difficultyMap.put("Sprouts", "Easy");

        recipeMap.put("Besan Chilla",
            "1. Besan mein paani daal ke batter banao\n" +
            "2. Onion, tomato baareek kaato\n" +
            "3. Batter mein daalo\n" +
            "4. Salt aur masala daalo\n" +
            "5. Tawa garam karo - oil lagao\n" +
            "6. Batter daal ke phailao\n" +
            "7. Dono taraf golden hone tak seko");
        cookTimeMap.put("Besan Chilla", "15 minutes");
        difficultyMap.put("Besan Chilla", "Easy");

        recipeMap.put("Moong Chilla",
            "1. Moong dal 4 ghante bhigo do\n" +
            "2. Mixer mein pees lo - thick batter\n" +
            "3. Onion, tomato kaat ke mix karo\n" +
            "4. Salt aur masala daalo\n" +
            "5. Tawa par oil laga ke seko\n" +
            "6. Dono taraf golden hone tak pakao");
        cookTimeMap.put("Moong Chilla", "20 minutes (+ 4 hr soaking)");
        difficultyMap.put("Moong Chilla", "Medium");

        recipeMap.put("Upma",
            "1. Suji ko sukha bhuno - halka golden\n" +
            "2. Alag rakho\n" +
            "3. Kadhai mein oil garam karo\n" +
            "4. Mustard seeds, onion daalo\n" +
            "5. Paani daalo - ubalne do\n" +
            "6. Bhuna suji dheere dheere daalo\n" +
            "7. Lagatar chalate raho - lumps na bane\n" +
            "8. Dhak ke 5 min pakao");
        cookTimeMap.put("Upma", "20 minutes");
        difficultyMap.put("Upma", "Easy");

        recipeMap.put("Oats",
            "1. Oats ko dudh mein daalo\n" +
            "2. Gas par chadhao\n" +
            "3. Ubalne do - 5 min\n" +
            "4. Lagatar chalate raho\n" +
            "5. Banana kaat ke daalo\n" +
            "6. Honey daal ke serve karo");
        cookTimeMap.put("Oats", "10 minutes");
        difficultyMap.put("Oats", "Easy");

        recipeMap.put("Egg White Omelette",
            "1. Eggs ke white part alag karo\n" +
            "2. Whisk karo - fluffy hone tak\n" +
            "3. Onion, tomato kaato\n" +
            "4. Mix karo egg mein\n" +
            "5. Tawa par oil lagao\n" +
            "6. Batter daal ke dono taraf seko\n" +
            "7. Salt pepper daal ke serve karo");
        cookTimeMap.put("Egg White Omelette", "10 minutes");
        difficultyMap.put("Egg White Omelette", "Easy");

        recipeMap.put("Idli",
            "1. Idli batter ko steamer mould mein daalo\n" +
            "2. Steamer mein 12-15 min steam karo\n" +
            "3. Toothpick se check karo - clean nikle to ready\n" +
            "4. Garam garam sambar chutney ke saath serve karo");
        cookTimeMap.put("Idli", "20 minutes");
        difficultyMap.put("Idli", "Easy");

        // ════ LUNCH RECIPES ════

        recipeMap.put("Dal Rice",
            "1. Toor dal ko paani se dho lo\n" +
            "2. Cooker mein dal, paani, turmeric daalo\n" +
            "3. 3-4 whistle laga ke pakao\n" +
            "4. Tadka: Oil mein jeera, garlic, onion bhuno\n" +
            "5. Tomato daal ke pakao\n" +
            "6. Dal mein tadka mix karo\n" +
            "7. Chawal alag pakao - serve karo saath mein");
        cookTimeMap.put("Dal Rice", "30 minutes");
        difficultyMap.put("Dal Rice", "Easy");

        recipeMap.put("Khichdi",
            "1. Chawal aur moong dal mix karke dho lo\n" +
            "2. Cooker mein daalo\n" +
            "3. Turmeric, salt, paani daalo\n" +
            "4. 3-4 whistle laga ke pakao\n" +
            "5. Ghee daal ke garam garam serve karo");
        cookTimeMap.put("Khichdi", "25 minutes");
        difficultyMap.put("Khichdi", "Easy");

        recipeMap.put("Palak Paneer",
            "1. Spinach ko blanch karo - ubalte paani mein\n" +
            "2. Thanda paani mein daal ke puree banao\n" +
            "3. Kadhai mein oil garam karo\n" +
            "4. Onion, garlic, ginger bhuno\n" +
            "5. Tomato daal ke pakao\n" +
            "6. Spinach puree daalo\n" +
            "7. Paneer cubes daalo\n" +
            "8. Cream daal ke 5 min pakao");
        cookTimeMap.put("Palak Paneer", "30 minutes");
        difficultyMap.put("Palak Paneer", "Medium");

        recipeMap.put("Rajma Rice",
            "1. Rajma raat bhar bhigo do\n" +
            "2. Cooker mein rajma, paani daalo\n" +
            "3. 5-6 whistle laga ke pakao - soft hona chahiye\n" +
            "4. Tadka: Onion, tomato, masala bhuno\n" +
            "5. Rajma mix karo tadke mein\n" +
            "6. 10 min pakao - chawal ke saath serve karo");
        cookTimeMap.put("Rajma Rice", "40 minutes (+ overnight soak)");
        difficultyMap.put("Rajma Rice", "Medium");

        recipeMap.put("Jowar Bhakri",
            "1. Jowar atta mein garam paani daalo\n" +
            "2. Dough banao - soft\n" +
            "3. Haath se golakar bhakri banao\n" +
            "4. Tawa par dono taraf seko\n" +
            "5. Direct flame par phula lo");
        cookTimeMap.put("Jowar Bhakri", "20 minutes");
        difficultyMap.put("Jowar Bhakri", "Medium");

        recipeMap.put("Veg Biryani",
            "1. Basmati rice 30 min bhigo do\n" +
            "2. Mixed vegetables kaato\n" +
            "3. Oil mein onion golden karo\n" +
            "4. Veggies daalo - masala daalo\n" +
            "5. Rice layer karo upar\n" +
            "6. Dum pe 15 min pakao");
        cookTimeMap.put("Veg Biryani", "45 minutes");
        difficultyMap.put("Veg Biryani", "Hard");

        // ════ DINNER RECIPES ════

        recipeMap.put("Dal Khichdi",
            "1. Chawal aur moong dal mix karke dho lo\n" +
            "2. Cooker mein daalo paani ke saath\n" +
            "3. Turmeric, salt daalo\n" +
            "4. 3 whistle laga ke pakao\n" +
            "5. Ghee daal ke serve karo - light dinner");
        cookTimeMap.put("Dal Khichdi", "25 minutes");
        difficultyMap.put("Dal Khichdi", "Easy");

        recipeMap.put("Moong Dal",
            "1. Moong dal dho lo\n" +
            "2. Cooker mein daalo paani ke saath\n" +
            "3. Turmeric daalo - 2 whistle\n" +
            "4. Tadka: Jeera, onion, tomato bhuno\n" +
            "5. Dal mein mix karo - serve karo");
        cookTimeMap.put("Moong Dal", "20 minutes");
        difficultyMap.put("Moong Dal", "Easy");

        recipeMap.put("Boiled Eggs",
            "1. Eggs ko paani mein daalo\n" +
            "2. 10-12 min ubaalo\n" +
            "3. Thande paani mein daalo\n" +
            "4. Chilka utaaro\n" +
            "5. Salt pepper laga ke khao");
        cookTimeMap.put("Boiled Eggs", "12 minutes");
        difficultyMap.put("Boiled Eggs", "Easy");

        recipeMap.put("Tomato Soup",
            "1. Tomato kaat ke ubaalo\n" +
            "2. Thanda karke puree banao\n" +
            "3. Kadhai mein oil garam karo\n" +
            "4. Onion, garlic bhuno\n" +
            "5. Tomato puree daalo\n" +
            "6. Salt, pepper daalo - ubaalo 5 min");
        cookTimeMap.put("Tomato Soup", "20 minutes");
        difficultyMap.put("Tomato Soup", "Easy");

        // ════ DRINKS ════

        recipeMap.put("Lemon Water",
            "1. Glass mein paani lo\n" +
            "2. Lemon nichodo\n" +
            "3. Honey daalo (optional)\n" +
            "4. Mix karo - serve karo thanda ya garam");
        cookTimeMap.put("Lemon Water", "2 minutes");
        difficultyMap.put("Lemon Water", "Easy");

        recipeMap.put("Beetroot Juice",
            "1. Beetroot ko kaato\n" +
            "2. Mixer mein daalo paani ke saath\n" +
            "3. Achhe se blend karo\n" +
            "4. Chhan lo - lemon daalo\n" +
            "5. Turant serve karo");
        cookTimeMap.put("Beetroot Juice", "10 minutes");
        difficultyMap.put("Beetroot Juice", "Easy");

        recipeMap.put("Banana Shake",
            "1. Banana kaato\n" +
            "2. Mixer mein milk ke saath daalo\n" +
            "3. Honey daalo\n" +
            "4. Blend karo achhe se\n" +
            "5. Glass mein daal ke serve karo thanda");
        cookTimeMap.put("Banana Shake", "5 minutes");
        difficultyMap.put("Banana Shake", "Easy");
    }

    // ── Public method: recipe steps do ─────────────────
    public static String getRecipe(String mealName) {
        return recipeMap.getOrDefault(mealName,
            "Recipe coming soon! Try a simple home-style "
            + "preparation with basic spices.");
    }

    // ── Public method: cook time do ────────────────────
    public static String getCookTime(String mealName) {
        return cookTimeMap.getOrDefault(
            mealName, "15-20 minutes (estimate)");
    }

    // ── Public method: difficulty level do ─────────────
    public static String getDifficulty(String mealName) {
        return difficultyMap.getOrDefault(
            mealName, "Easy");
    }

    // ── Check if recipe exists ─────────────────────────
    public static boolean hasRecipe(String mealName) {
        return recipeMap.containsKey(mealName);
    }
}
