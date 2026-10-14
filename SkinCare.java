// ======================================================
// SkinCare.java
// Gender + Age + Skin Type + Budget ke hisab se
// Morning aur Night skincare routine suggest karna
// ======================================================

import java.util.Scanner;

public class SkinCare {

    public static void generateSkinCare(User user) {

        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      SMARTFIT SKIN CARE ROUTINE      ║");
        System.out.println("╚══════════════════════════════════════╝");

        Scanner sc = new Scanner(System.in);

        String gender = user.getGender();
        String budget = user.getBudget();

        // ── Skin type input ────────────────────────────
        System.out.println("\nSelect Your Skin Type:");
        System.out.println("1. Oily    (Chehra chikna rehta hai)");
        System.out.println("2. Dry     (Chehra tight/dry feel hota hai)");
        System.out.println("3. Normal  (Na zyada oily na dry)");
        System.out.println("4. Combination (T-zone oily, baaki normal)");
        System.out.print("\nEnter skin type: ");
        String skinType = sc.nextLine().trim();

        System.out.println("\n  ─────────────────────────────────────");
        System.out.println("  Gender    : " + gender);
        System.out.println("  Skin Type : " + skinType);
        System.out.println("  Budget    : " + budget);
        System.out.println("  ─────────────────────────────────────");

        // ── Morning Routine ────────────────────────────
        printMorningRoutine(gender, skinType, budget);

        // ── Night Routine ──────────────────────────────
        printNightRoutine(gender, skinType, budget);

        // ── Diet for skin ──────────────────────────────
        printSkinDiet(skinType);

        // ── Common mistakes ────────────────────────────
        printCommonMistakes(skinType, gender);
    }

    // ── MORNING ROUTINE ───────────────────────────────
    private static void printMorningRoutine(
            String gender, String skinType, String budget) {

        System.out.println("\n  ══ MORNING ROUTINE ══");
        System.out.println("  (Subah uthne ke baad karo)");
        System.out.println();

        // ── Step 1: Cleanser ───────────────────────────
        System.out.println("  STEP 1 - CLEANSER (Face wash)");

        if (skinType.equalsIgnoreCase("Oily")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Besan + Haldi + Rose water paste se face wash karo");
                System.out.println("  * Ya plain water se 2-3 baar wash karo");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Himalaya Neem Face Wash (Rs.100)");
                System.out.println("  * Ya Ponds Pure White Face Wash");
            } else {
                System.out.println("  * Product: CeraVe Foaming Cleanser");
                System.out.println("  * Ya The Derma Co 1% Salicylic Face Wash");
            }
        } else if (skinType.equalsIgnoreCase("Dry")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Malai (milk cream) + Besan se face wash karo");
                System.out.println("  * Harsh soap avoid karo");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Cetaphil Gentle Skin Cleanser (Rs.250)");
                System.out.println("  * Ya Himalaya Moisturizing Aloe Face Wash");
            } else {
                System.out.println("  * Product: CeraVe Hydrating Cleanser");
                System.out.println("  * Ya La Roche-Posay Toleriane Cleanser");
            }
        } else {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Plain water se wash karo");
                System.out.println("  * Ya Multani mitti + Rose water");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Himalaya Purifying Neem Face Wash");
                System.out.println("  * Ya Simple Kind to Skin Face Wash");
            } else {
                System.out.println("  * Product: CeraVe Hydrating or Foaming Cleanser");
            }
        }

        // ── Step 2: Toner ──────────────────────────────
        System.out.println("\n  STEP 2 - TONER");
        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  * Rose water - best toner (Rs.50)");
            System.out.println("  * Green tea thanda karke use karo");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  * Product: Dot & Key AHA BHA Toner (Rs.400)");
            System.out.println("  * Ya Plum Green Tea Toner");
        } else {
            System.out.println("  * Product: Paula's Choice BHA Toner");
            System.out.println("  * Ya Some By Mi AHA BHA PHA Toner");
        }

        // ── Step 3: Moisturizer ────────────────────────
        System.out.println("\n  STEP 3 - MOISTURIZER");
        if (skinType.equalsIgnoreCase("Oily")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Aloe vera gel - best for oily skin (Rs.80)");
                System.out.println("  * Pure Aloe vera plant se bhi le sakte ho");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Neutrogena Hydro Boost Gel (Rs.400)");
                System.out.println("  * Ya Dot & Key Water Gel Moisturizer");
            } else {
                System.out.println("  * Product: CeraVe AM Facial Moisturizing Lotion");
            }
        } else if (skinType.equalsIgnoreCase("Dry")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Pure coconut oil (Rs.100/bottle)");
                System.out.println("  * Ya Vaseline + Rose water mix");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Cetaphil Moisturizing Cream (Rs.350)");
                System.out.println("  * Ya Minimalist 2% Hyaluronic Acid Moisturizer");
            } else {
                System.out.println("  * Product: CeraVe Moisturizing Cream");
                System.out.println("  * Ya La Roche-Posay Toleriane Double Repair");
            }
        } else {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Aloe vera gel ya coconut oil (light layer)");
            } else if (budget.equalsIgnoreCase("medium")) {
                System.out.println("  * Product: Plum Green Tea Renewed Clarity Moisturizer");
            } else {
                System.out.println("  * Product: CeraVe AM or PM Moisturizer");
            }
        }

        // ── Step 4: Sunscreen ──────────────────────────
        System.out.println("\n  STEP 4 - SUNSCREEN (MUST - Skip mat karo!)");
        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  * Lakme Sun Expert SPF 50 (Rs.180) - best budget");
            System.out.println("  * Lotus Herbals Safe Sun SPF 30");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  * Product: Minimalist SPF 50 Sunscreen (Rs.250)");
            System.out.println("  * Ya Dot & Key Mineral Sunscreen SPF 50");
        } else {
            System.out.println("  * Product: Re' equil Oxybenzone Free Sunscreen");
            System.out.println("  * Ya La Roche-Posay Anthelios SPF 50");
        }

        // ── Male specific ──────────────────────────────
        if (gender.equalsIgnoreCase("Male")) {
            System.out.println("\n  EXTRA STEP FOR MALES - After Shave Care:");
            System.out.println("  * Shave karne ke baad aloe vera lagao");
            System.out.println("  * Alcohol based aftershave avoid karo - drying hoti hai");
            System.out.println("  * Beard oil use karo agar beard hai");
        }

        System.out.println("\n  Morning Routine Total Time: 5-7 minutes");
    }

    // ── NIGHT ROUTINE ─────────────────────────────────
    private static void printNightRoutine(
            String gender, String skinType, String budget) {

        System.out.println("\n  ══ NIGHT ROUTINE ══");
        System.out.println("  (Sone se 30 min pehle karo)");
        System.out.println();

        // ── Step 1: Double Cleanse ─────────────────────
        System.out.println("  STEP 1 - REMOVE MAKEUP/SUNSCREEN");
        if (gender.equalsIgnoreCase("Female")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Coconut oil se makeup remove karo");
                System.out.println("  * Phir normal face wash karo");
            } else {
                System.out.println("  * Micellar water ya cleansing oil use karo");
                System.out.println("  * Product: Simple Micellar Water (Rs.200)");
            }
        } else {
            System.out.println("  * Sunscreen remove karo - face wash se");
            System.out.println("  * Double cleanse zaroor karo");
        }

        // ── Step 2: Night Cleanser ─────────────────────
        System.out.println("\n  STEP 2 - FACE WASH");
        System.out.println("  * Same as morning routine ka face wash");
        System.out.println("  * Gently karo - zyada rub mat karo");

        // ── Step 3: Treatment ──────────────────────────
        System.out.println("\n  STEP 3 - TREATMENT (Weekly 2-3 times)");
        if (skinType.equalsIgnoreCase("Oily")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Multani mitti face pack - 15 min (Rs.30)");
                System.out.println("  * Haldi + Besan + Curd pack");
            } else {
                System.out.println("  * Niacinamide serum (pores khatam karta hai)");
                System.out.println("  * Product: Minimalist 10% Niacinamide (Rs.400)");
            }
        } else if (skinType.equalsIgnoreCase("Dry")) {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Honey + Milk face pack (Rs.0 - ghar mein hai)");
                System.out.println("  * Banana + Honey mask");
            } else {
                System.out.println("  * Vitamin C serum ya Hyaluronic Acid");
                System.out.println("  * Product: Minimalist 2% HA Serum (Rs.500)");
            }
        } else {
            if (budget.equalsIgnoreCase("low")) {
                System.out.println("  * Curd + Besan pack - 15 min");
                System.out.println("  * Rice water face rinse");
            } else {
                System.out.println("  * Vitamin C serum ya Retinol (age 25+)");
            }
        }

        // ── Step 4: Night Moisturizer ──────────────────
        System.out.println("\n  STEP 4 - NIGHT CREAM / MOISTURIZER");
        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  * Pure Aloe vera gel - oily skin");
            System.out.println("  * Coconut oil (light layer) - dry skin");
            System.out.println("  * Vaseline - very dry skin");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  * Product: Cetaphil Night Cream (Rs.400)");
            System.out.println("  * Ya Plum Bright Years Cell Renewal Night Cream");
        } else {
            System.out.println("  * Product: CeraVe Skin Renewing Night Cream");
            System.out.println("  * Ya Olay Regenerist Night Cream");
        }

        // ── Step 5: Eye cream / Lip care ──────────────
        System.out.println("\n  STEP 5 - LIP CARE");
        System.out.println("  * Raat ko lips par pure ghee ya coconut oil lagao");
        System.out.println("  * Ya Vaseline lip therapy (Rs.50)");
        System.out.println("  * Ye lips ko soft rakhega");

        System.out.println("\n  Night Routine Total Time: 10 minutes");
        System.out.println("  Raat ko skin repair hoti hai - ye time important hai!");
    }

    // ── Skin ke liye diet ──────────────────────────────
    private static void printSkinDiet(String skinType) {

        System.out.println("\n  ══ SKIN DIET TIPS ══");
        System.out.println("  (Jo khao ge woh skin par dikhega)");
        System.out.println();

        System.out.println("  EAT MORE:");
        System.out.println("  * Pani - 2.5 to 3 litre daily");
        System.out.println("  * Vitamin C - Amla, Orange, Lemon");
        System.out.println("  * Vitamin E - Almonds, Sunflower seeds");
        System.out.println("  * Zinc - Pumpkin seeds, Chickpeas");
        System.out.println("  * Omega 3 - Walnuts, Fish, Flaxseeds");

        if (skinType.equalsIgnoreCase("Oily")) {
            System.out.println("  * Green tea - sebum control karta hai");
            System.out.println("  * Cucumber - hydrating");
        } else if (skinType.equalsIgnoreCase("Dry")) {
            System.out.println("  * Avocado - healthy fats");
            System.out.println("  * Coconut water - hydration");
        }

        System.out.println("\n  AVOID:");
        System.out.println("  * Zyada sugar - skin aging badhata hai");
        System.out.println("  * Dairy (agar acne ho) - limit karo");
        System.out.println("  * Oily junk food - pores band hote hain");
        System.out.println("  * Alcohol - skin dehydrate hoti hai");
    }

    // ── Common mistakes ────────────────────────────────
    private static void printCommonMistakes(
            String skinType, String gender) {

        System.out.println("\n  ══ COMMON MISTAKES (AVOID KARO) ══");
        System.out.println();
        System.out.println("  * Sunscreen skip karna - BIGGEST mistake");
        System.out.println("  * Zyada face wash karna - skin dry hoti hai");
        System.out.println("  * Chin/cheek touch karna - bacteria transfer");
        System.out.println("  * Phone screen par chehra rakhna - bacteria");
        System.out.println("  * Raat ko makeup remove na karna");
        System.out.println("  * Pillowcase change na karna - weekly change karo");

        if (skinType.equalsIgnoreCase("Oily")) {
            System.out.println("  * Moisturizer skip karna (Oily skin ko bhi chahiye)");
            System.out.println("  * Zyada toner use karna - skin dry hoti hai");
        }

        if (gender.equalsIgnoreCase("Male")) {
            System.out.println("  * Soap se face wash karna - face wash use karo");
            System.out.println("  * Sunscreen na lagana - men ko bhi chahiye");
        }

        System.out.println("\n  Results timeline:");
        System.out.println("  1 week  - Skin hydrated feel hogi");
        System.out.println("  2 weeks - Texture improve hoga");
        System.out.println("  1 month - Visible glow aayega");
        System.out.println("  3 months - Full transformation");
        System.out.println("\n  Consistency is everything in skincare!");
    }
}