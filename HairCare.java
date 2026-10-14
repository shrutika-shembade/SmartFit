// ======================================================
// HairCare.java
// Hair type ke hisab se routine, products, home remedies
// SkinCare.java jaisa structure
// ======================================================

import java.util.Scanner;

public class HairCare {

    public static void generateHairCare(User user) {

        System.out.println("\n");
        System.out.println("==========================================");
        System.out.println("       SMARTFIT HAIR CARE ROUTINE");
        System.out.println("==========================================");

        Scanner sc = new Scanner(System.in);

        String gender = user.getGender();
        String budget = user.getBudget();

        System.out.println("\nSelect Your Hair Type:");
        System.out.println("  1. Straight");
        System.out.println("  2. Curly");
        System.out.println("  3. Dry/Frizzy");
        System.out.println("  4. Oily");
        System.out.print("\nEnter hair type: ");
        String hairType = sc.nextLine().trim();

        System.out.println("\nAny hair issue?");
        System.out.println("  1. Hair Fall");
        System.out.println("  2. Dandruff");
        System.out.println("  3. None");
        System.out.print("\nEnter issue: ");
        String hairIssue = sc.nextLine().trim();

        System.out.println("\n  ------------------------------------------");
        System.out.println("  Gender    : " + gender);
        System.out.println("  Hair Type : " + hairType);
        System.out.println("  Issue     : " + hairIssue);
        System.out.println("  Budget    : " + budget);
        System.out.println("  ------------------------------------------");

        printOilingRoutine(hairType, budget);
        printWashRoutine(hairType, budget);
        printHairMask(hairType, budget);
        printIssueSpecificCare(hairIssue, budget);
        printHairDiet();
        printWeeklySchedule();
    }

    // ── Oiling routine ───────────────────────────────────
    private static void printOilingRoutine(
            String hairType, String budget) {

        System.out.println("\n  === OILING ROUTINE (2-3 times/week) ===");

        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  > Coconut oil - sabse acha aur sasta (Rs.150/bottle)");
            System.out.println("  > Halka garam karke scalp pe massage karo");
            System.out.println("  > 1 ghante ke liye chhod do, phir wash karo");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  > Product: Parachute Advansed Hair Oil");
            System.out.println("  > Ya Dabur Almond Hair Oil (Rs.150)");
        } else {
            System.out.println("  > Product: Mamaearth Onion Hair Oil");
            System.out.println("  > Ya WOW Skin Science Hair Oil");
        }

        if (hairType.equalsIgnoreCase("Dry/Frizzy")) {
            System.out.println("  Extra: Argan oil ya olive oil bhi try karo");
        } else if (hairType.equalsIgnoreCase("Oily")) {
            System.out.println("  Extra: Light oil use karo - zyada mat lagao");
        }
    }

    // ── Wash routine ──────────────────────────────────────
    private static void printWashRoutine(
            String hairType, String budget) {

        System.out.println("\n  === WASH ROUTINE ===");

        String frequency;
        if (hairType.equalsIgnoreCase("Oily")) {
            frequency = "Every alternate day";
        } else if (hairType.equalsIgnoreCase("Dry/Frizzy")) {
            frequency = "2 times a week";
        } else {
            frequency = "3 times a week";
        }

        System.out.println("  Frequency: " + frequency);

        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  > Shikakai/Reetha powder - natural shampoo");
            System.out.println("  > Ya mild Himalaya shampoo (Rs.150)");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  > Product: Mamaearth Onion Shampoo (Rs.350)");
            System.out.println("  > Sulphate free hona chahiye");
        } else {
            System.out.println("  > Product: WOW Apple Cider Vinegar Shampoo");
            System.out.println("  > Ya L'Oreal Professional range");
        }

        System.out.println("  Conditioner: Sirf lengths pe lagao, scalp pe nahi");
    }

    // ── Weekly hair mask ─────────────────────────────────
    private static void printHairMask(
            String hairType, String budget) {

        System.out.println("\n  === WEEKLY HAIR MASK (1x/week) ===");

        if (budget.equalsIgnoreCase("low")) {
            System.out.println("  > Curd + Egg mask - 30 min lagao phir wash");
            System.out.println("  > Ya Banana + Honey mask");
            System.out.println("  > Aloe vera gel direct scalp pe");
        } else if (budget.equalsIgnoreCase("medium")) {
            System.out.println("  > Product: Khadi Natural Hair Mask (Rs.200)");
        } else {
            System.out.println("  > Product: Loreal Professional Hair Mask");
            System.out.println("  > Ya salon treatment monthly");
        }

        if (hairType.equalsIgnoreCase("Curly")) {
            System.out.println("  Extra: Leave-in conditioner curls ke liye zaroori");
        }
    }

    // ── Issue specific care ──────────────────────────────
    private static void printIssueSpecificCare(
            String hairIssue, String budget) {

        System.out.println("\n  === ISSUE SPECIFIC CARE ===");

        if (hairIssue.equalsIgnoreCase("Hair Fall")
            || hairIssue.contains("1")) {
            System.out.println("  HAIR FALL SOLUTION:");
            System.out.println("  > Onion juice scalp pe lagao - 2x/week");
            System.out.println("  > Protein rich diet lo (eggs, dal, paneer)");
            System.out.println("  > Tight hairstyle avoid karo");
            System.out.println("  > Stress kam karo - meditation try karo");
            if (!budget.equalsIgnoreCase("low")) {
                System.out.println("  > Product: Mamaearth Onion Hair Fall Shampoo");
            }
        } else if (hairIssue.equalsIgnoreCase("Dandruff")
                || hairIssue.contains("2")) {
            System.out.println("  DANDRUFF SOLUTION:");
            System.out.println("  > Neem water se hair rinse karo");
            System.out.println("  > Tea tree oil 2-3 drops shampoo mein mix karo");
            System.out.println("  > Scalp clean rakho - regularly wash karo");
            if (!budget.equalsIgnoreCase("low")) {
                System.out.println("  > Product: Head & Shoulders Anti-Dandruff");
            }
        } else {
            System.out.println("  Koi specific issue nahi - maintain karo current routine!");
        }
    }

    // ── Hair diet ──────────────────────────────────────
    private static void printHairDiet() {

        System.out.println("\n  === DIET FOR HEALTHY HAIR ===");
        System.out.println("  EAT MORE:");
        System.out.println("  > Protein - Eggs, Dal, Paneer, Sprouts");
        System.out.println("  > Iron - Spinach, Beetroot, Dates");
        System.out.println("  > Biotin - Almonds, Walnuts");
        System.out.println("  > Omega 3 - Flaxseeds, Fish");
        System.out.println("  > Water - 2.5-3 litre daily");

        System.out.println("\n  AVOID:");
        System.out.println("  > Crash dieting - hair fall badhata hai");
        System.out.println("  > Excessive heat styling");
        System.out.println("  > Tight hairstyles daily");
    }

    // ── Weekly schedule ──────────────────────────────────
    private static void printWeeklySchedule() {

        System.out.println("\n  === WEEKLY HAIR CARE SCHEDULE ===");
        System.out.println("  Monday    : Oil massage (night)");
        System.out.println("  Tuesday   : Wash + Conditioner");
        System.out.println("  Wednesday : Light oil if needed");
        System.out.println("  Thursday  : Hair mask treatment");
        System.out.println("  Friday    : Oil massage (night)");
        System.out.println("  Saturday  : Wash + Conditioner");
        System.out.println("  Sunday    : Rest day / trim check");

        System.out.println("\n  Consistency is key - results in 4-6 weeks!");
    }
}