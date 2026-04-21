/**
 * ============================================================
 * FILE: Printer.java
 * PURPOSE: Handles ALL console display and formatting for
 * Puzzle 3 - The Five Ships program.
 *
 * WHY A SEPARATE FILE?
 * Separating display logic from solving logic follows the
 * "Separation of Concerns" principle. If we want to change
 * how output looks, we only touch this file - the solver
 * algorithm (ShipSolver.java) stays untouched.
 *
 * This also makes debugging easier: if output looks wrong,
 * we know to look here. If answers are wrong, we look at
 * ShipSolver.java instead.
 * ============================================================
 */
public class Printer {

    // ----------------------------------------------------------
    // ANSI COLOR CODES
    // These special codes tell the terminal to display text
    // in different colors. They work on Windows 10+, macOS,
    // and Linux terminals.
    // Format: \u001B[<code>m ... \u001B[0m (reset)
    // ----------------------------------------------------------

    /** Resets all formatting back to default */
    public static final String RESET = "\u001B[0m";

    /** Bold white text */
    public static final String BOLD = "\u001B[1m";

    /** Cyan/teal colored text */
    public static final String CYAN = "\u001B[36m";

    /** Yellow colored text */
    public static final String YELLOW = "\u001B[33m";

    /** Green colored text */
    public static final String GREEN = "\u001B[32m";

    /** Red colored text */
    public static final String RED = "\u001B[31m";

    /** Blue colored text */
    public static final String BLUE = "\u001B[34m";

    /** Magenta/purple colored text */
    public static final String MAGENTA = "\u001B[35m";

    // ----------------------------------------------------------
    // BANNER & HEADER
    // ----------------------------------------------------------

    /**
     * Prints the opening title banner when the program starts.
     * Uses box-drawing characters for a professional look.
     */
    public static void printBanner() {
        System.out.println();
        System.out.println(CYAN + "+==============================================================+" + RESET);
        System.out.println(CYAN + "|" + RESET + BOLD + "         PUZZLE 3  -  THE FIVE SHIPS SOLVER                  "
                + RESET + CYAN + " |" + RESET);
        System.out.println(CYAN + "|" + RESET + "         Discrete Structures Group Assignment                 " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "|" + RESET + "         Constraint-Based Logic Solver  |  Java               " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "+==============================================================+" + RESET);
        System.out.println();
    }

    // ----------------------------------------------------------
    // MAIN MENU
    // ----------------------------------------------------------

    /**
     * Prints the main interactive menu with all available options.
     */
    public static void printMenu() {
        System.out.println(CYAN + "  +---------------------------------------------------------+" + RESET);
        System.out.println(CYAN + "  |" + RESET + BOLD + "  MAIN MENU                                              "
                + RESET + CYAN + "|" + RESET);
        System.out.println(CYAN + "  +---------------------------------------------------------+" + RESET);
        System.out.println(CYAN + "  |" + RESET + "  [1] View all 15 puzzle clues                           " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "  |" + RESET + "  [2] Solve the puzzle (instant result)                  " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "  |" + RESET + "  [3] Step-by-step deduction walkthrough                 " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "  |" + RESET + "  [4] Quiz mode (guess the ships!)                       " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "  |" + RESET + "  [5] Exit                                               " + CYAN
                + "|" + RESET);
        System.out.println(CYAN + "  +---------------------------------------------------------+" + RESET);
        System.out.print("  Enter your choice: ");
    }

    // ----------------------------------------------------------
    // CLUES DISPLAY
    // ----------------------------------------------------------

    /**
     * Prints all 15 puzzle clues in a formatted box.
     * Uses data from ShipData.CLUES to avoid duplication.
     */
    public static void printClues() {
        System.out.println();
        System.out.println(YELLOW + "  +-- ALL 15 PUZZLE CLUES ------------------------------------------+" + RESET);

        // Loop through all clues stored in ShipData
        for (String clue : ShipData.CLUES) {
            System.out.println(YELLOW + "  |" + RESET + "  " + clue);
        }

        System.out.println(YELLOW + "  +----------------------------------------------------------------+" + RESET);
        System.out.println();
    }

    // ----------------------------------------------------------
    // SOLVING ANIMATION
    // ----------------------------------------------------------

    /**
     * Simulates the solver working by printing animated dots.
     * Uses Thread.sleep() to create a visible pause effect.
     * This gives the user feedback that computation is happening.
     *
     * @param ms milliseconds to pause between each dot
     */
    public static void printSolvingAnimation(int ms) {
        System.out.print("\n  " + CYAN + "[*] Running constraint solver" + RESET);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(ms); // Pause to show animation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interrupt safely
            }
            System.out.print(CYAN + "." + RESET);
        }
        System.out.println();
    }

    // ----------------------------------------------------------
    // SOLUTION TABLE
    // ----------------------------------------------------------

    /**
     * Prints the complete solution as a formatted table.
     * Highlights the two puzzle answers (Port Said, Tea) in green.
     *
     * @param solver the ShipSolver that has already been run
     */
    public static void printSolutionTable(ShipSolver solver) {
        System.out.println();
        System.out.println(GREEN + "  [OK] Solution found in " + solver.solveTimeMs + "ms!" + RESET);
        System.out.println();

        // -- Table Header --
        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);
        System.out.printf("  " + BOLD +
                "| %-8s | %-11s | %-7s | %-12s | %-7s | %-8s |%n" + RESET,
                "Position", "Nationality", "Chimney", "Destination", "Cargo", "Departs");
        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);

        // -- Table Rows (one per ship position) --
        for (int i = 0; i < 5; i++) {

            // Get the readable name for each attribute at position i
            String pos = "Ship " + (i + 1);
            String nat = ShipData.NAT[solver.solNat[i]];
            String col = ShipData.COLOR[solver.solColor[i]];
            String dest = ShipData.DEST[solver.solDest[i]];
            String car = ShipData.CARGO[solver.solCargo[i]];
            String tim = ShipData.TIME[solver.solTime[i]];

            // Check if this row contains one of the two puzzle answers
            boolean isPortSaid = solver.solDest[i] == ShipData.PORT_SAID;
            boolean isTea = solver.solCargo[i] == ShipData.TEA;

            // Pad strings manually because ANSI codes affect String.format length
            String destPadded = padOrColor(dest, 12, isPortSaid);
            String carPadded = padOrColor(car, 7, isTea);

            // Print the row - use raw printf for non-colored cells
            System.out.printf("  | %-8s | %-11s | %-7s | %s | %s | %-8s |%n",
                    pos, nat, col, destPadded, carPadded, tim);
        }

        // -- Table Footer --
        System.out.println("  " +
                "+----------+-------------+---------+--------------+---------+----------+");
        System.out.println("  " + GREEN + "< = Answer to puzzle questions" + RESET);
        System.out.println();
    }

    /**
     * Helper method to pad a string with spaces and optionally
     * color it green (for answer cells in the table).
     *
     * @param text    the text to display
     * @param width   the column width in characters
     * @param colored whether to apply green color
     * @return formatted string with correct padding
     */
    private static String padOrColor(String text, int width, boolean colored) {
        // Build padding spaces
        int spaces = width - text.length();
        StringBuilder sb = new StringBuilder();

        if (colored) {
            sb.append(GREEN).append(text).append(" <").append(RESET);
            // Adjust: "<" is 1 extra char, subtract from padding
            for (int i = 0; i < Math.max(0, spaces - 2); i++)
                sb.append(' ');
        } else {
            sb.append(text);
            for (int i = 0; i < spaces; i++)
                sb.append(' ');
        }
        return sb.toString();
    }

    // ----------------------------------------------------------
    // FINAL ANSWERS BOX
    // ----------------------------------------------------------

    /**
     * Prints a highlighted box showing just the two puzzle answers.
     * This is the most important output - clearly answers the question.
     *
     * @param solver the ShipSolver that has already been run
     */
    public static void printAnswers(ShipSolver solver) {
        // Find the nationality of the Port Said ship
        int psPos = solver.getPortSaidPosition();
        String portSaidShip = (psPos >= 0) ? ShipData.NAT[solver.solNat[psPos]] : "Unknown";

        // Find the nationality of the Tea ship
        int teaPos = solver.getTeaPosition();
        String teaShip = (teaPos >= 0) ? ShipData.NAT[solver.solNat[teaPos]] : "Unknown";

        System.out.println(GREEN + "  +===================================================+" + RESET);
        System.out.println(GREEN + "  |" + RESET + BOLD + "           PUZZLE ANSWERS                          " + RESET
                + GREEN + "|" + RESET);
        System.out.println(GREEN + "  +===================================================+" + RESET);
        System.out.printf(
                GREEN + "  |" + RESET + "  Q1: Which ship goes to Port Said?                " + GREEN + "|%n" + RESET);
        System.out.printf(GREEN + "  |" + RESET + GREEN + "      -> The %-37s" + RESET + GREEN + " |%n" + RESET,
                portSaidShip + " ship");
        System.out.println(GREEN + "  +===================================================+" + RESET);
        System.out.printf(
                GREEN + "  |" + RESET + "  Q2: Which ship carries tea?                      " + GREEN + "|%n" + RESET);
        System.out.printf(GREEN + "  |" + RESET + GREEN + "      -> The %-37s" + RESET + GREEN + " |%n" + RESET,
                teaShip + " ship");
        System.out.println(GREEN + "  +===================================================+" + RESET);
        System.out.println();
    }

    // ----------------------------------------------------------
    // STEP-BY-STEP DEDUCTION
    // ----------------------------------------------------------

    /**
     * Prints the deduction steps one at a time, waiting for
     * the user to press ENTER between each step.
     * This simulates "walking through" the logic manually.
     *
     * @param scanner used to wait for ENTER key press
     */
    public static void printStepByStep(java.util.Scanner scanner) {
        System.out.println();
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println(YELLOW + "  STEP-BY-STEP DEDUCTION  (Press ENTER for each step)" + RESET);
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println();

        // Go through each deduction step stored in ShipData
        for (int i = 0; i < ShipData.DEDUCTION_STEPS.length; i++) {
            String clueRef = ShipData.DEDUCTION_STEPS[i][0]; // e.g., "Clues 1+15"
            String deduction = ShipData.DEDUCTION_STEPS[i][1]; // the explanation

            // Print step number and clue reference
            System.out.printf("  " + CYAN + "Step %2d" + RESET + " [" + YELLOW + "%-13s" + RESET + "]: ", (i + 1),
                    clueRef);

            // Print the deduction text
            System.out.println(deduction);

            // Wait for user input before showing next step
            System.out.print("  " + BLUE + "(Press ENTER for next step...)" + RESET);
            scanner.nextLine(); // Pauses until user presses ENTER
        }

        System.out.println();
        System.out.println(GREEN + "  [OK] All deduction steps complete!" + RESET);
        System.out.println();
    }

    // ----------------------------------------------------------
    // QUIZ RESULT FEEDBACK
    // ----------------------------------------------------------

    /**
     * Prints feedback after a quiz answer (correct or wrong).
     *
     * @param correct whether the user's guess was correct
     * @param answer  the correct answer string
     */
    public static void printQuizFeedback(boolean correct, String answer) {
        if (correct) {
            System.out.println("  " + GREEN + "[OK] CORRECT! Well done!" + RESET);
        } else {
            System.out.println("  " + RED + "[X] Wrong! The correct answer was: " + BOLD + answer + RESET);
        }
        System.out.println();
    }

    /**
     * Prints the quiz final score in a formatted box.
     *
     * @param score number of correct answers
     * @param total total number of questions asked
     */
    public static void printQuizScore(int score, int total) {
        int percent = (int) Math.round((score * 100.0) / total);
        System.out.println();
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.println(
                CYAN + "  |" + RESET + BOLD + "          QUIZ COMPLETE!              " + RESET + CYAN + "|" + RESET);
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.printf(CYAN + "  |" + RESET + "  Score: %d / %d (%d%%)                 " + CYAN + "|%n" + RESET,
                score, total, percent);

        // Show a message based on performance
        String msg;
        if (percent == 100)
            msg = "  Perfect score! Excellent!         ";
        else if (percent >= 70)
            msg = "  Good job! Almost there.           ";
        else
            msg = "  Keep studying the clues!          ";

        System.out.println(CYAN + "  |" + RESET + msg + CYAN + "  |" + RESET);
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.println();
    }

    /**
     * Prints a simple separator line between menu sections.
     */
    public static void printSeparator() {
        System.out.println("  " + BLUE + "---------------------------------------------------------" + RESET);
        System.out.println();
    }

    /**
     * Prints an error message for invalid menu input.
     */
    public static void printInvalidInput() {
        System.out.println("  " + RED + "[!] Invalid choice. Please enter a number from the menu." + RESET);
        System.out.println();
    }
}
