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
 *
 * WHO CALLS THIS FILE?
 * - Main.java calls: printBanner(), printMenu(), printSeparator(),
 *   printClues(), printSolvingAnimation(), printSolutionTable(),
 *   printAnswers(), printStepByStep(), printInvalidInput()
 * - QuizMode.java calls: printQuizFeedback(), printQuizScore()
 * ============================================================
 */

// All methods are "public static" — call them with Printer.methodName()
// without creating a Printer object. This is a pure utility class.
public class Printer {

    // ----------------------------------------------------------
    // ANSI COLOR CODES
    // These special codes tell the terminal to display text
    // in different colors. They work on Windows 10+, macOS,
    // and Linux terminals.
    // Format: \u001B[<code>m ... \u001B[0m (reset)
    //
    // HOW ANSI CODES WORK:
    // The terminal interprets these escape sequences as color
    // instructions. Text between a color code and RESET appears
    // in that color. Example: CYAN + "Hello" + RESET -> cyan "Hello"
    // ----------------------------------------------------------

    /** Resets all formatting back to default */
    // Used after every colored text to stop color from "leaking"
    public static final String RESET = "\u001B[0m";

    /** Bold white text — used for headings and emphasis */
    public static final String BOLD = "\u001B[1m";

    /** Cyan/teal — used for borders, menu frames, info messages */
    public static final String CYAN = "\u001B[36m";

    /** Yellow — used for warnings, clue borders, step-by-step headers */
    public static final String YELLOW = "\u001B[33m";

    /** Green — used for success "[OK]", correct answers, puzzle answer highlights */
    public static final String GREEN = "\u001B[32m";

    /** Red — used for errors "[!]", wrong answers "[X]" */
    public static final String RED = "\u001B[31m";

    /** Blue — used for separator lines and "Press ENTER" prompts */
    public static final String BLUE = "\u001B[34m";

    /** Magenta/purple — used for quiz mode header */
    public static final String MAGENTA = "\u001B[35m";

    // ----------------------------------------------------------
    // BANNER & HEADER
    // ----------------------------------------------------------

    /**
     * Prints the opening title banner when the program starts.
     * Uses box-drawing characters for a professional look.
     *
     * CALLED FROM: Main.java -> main(), at program startup.
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
     *
     * CALLED FROM: Main.java -> main(), inside the while(running) loop.
     * Called at the START of every loop iteration.
     */
    public static void printMenu() {
        System.out.println(CYAN + "  +---------------------------------------------------------+" + RESET);
        System.out.println(CYAN + "  |" + RESET + BOLD + "  MAIN MENU                                              "
                + RESET + CYAN + "|" + RESET);
        System.out.println(CYAN + "  +---------------------------------------------------------+" + RESET);
        // Each option maps to a case in Main.java's switch statement
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
        // print (not println) so cursor stays on same line for user input
        System.out.print("  Enter your choice: ");
    }

    // ----------------------------------------------------------
    // CLUES DISPLAY
    // ----------------------------------------------------------

    /**
     * Prints all 15 puzzle clues in a formatted box.
     * Uses data from ShipData.CLUES to avoid duplication.
     *
     * CALLED FROM: Main.java -> case "1".
     * DATA SOURCE: ShipData.CLUES[] array (in ShipData.java).
     */
    public static void printClues() {
        System.out.println();
        System.out.println(YELLOW + "  +-- ALL 15 PUZZLE CLUES ------------------------------------------+" + RESET);

        // Loop through all clues stored in ShipData
        // ShipData.CLUES is a String[] in ShipData.java with 15 clue texts
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
     * CALLED FROM: Main.java -> case "2", before solver.solve().
     * NOTE: The actual solve is nearly instant — animation is cosmetic.
     *
     * @param ms milliseconds to pause between each dot (200 from Main.java)
     */
    public static void printSolvingAnimation(int ms) {
        // print (not println) so dots appear on the same line
        System.out.print("\n  " + CYAN + "[*] Running constraint solver" + RESET);
        // Print 5 dots with pauses: "Running constraint solver....."
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
     * CALLED FROM: Main.java -> case "2" and case "3".
     * DATA FLOW: Reads solver.sol*[] arrays (from ShipSolver.java),
     *   converts indices to names via ShipData.*[] arrays (ShipData.java).
     *
     * @param solver the ShipSolver that has already been run
     */
    public static void printSolutionTable(ShipSolver solver) {
        System.out.println();
        // solver.solveTimeMs is set at the end of ShipSolver.solve()
        System.out.println(GREEN + "  [OK] Solution found in " + solver.solveTimeMs + "ms!" + RESET);
        System.out.println();

        // -- Table Header -- ("%-8s" = left-aligned, 8 chars wide)
        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);
        System.out.printf("  " + BOLD +
                "| %-8s | %-11s | %-7s | %-12s | %-7s | %-8s |%n" + RESET,
                "Position", "Nationality", "Chimney", "Destination", "Cargo", "Departs");
        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);

        // -- Table Rows (one per ship position 0-4) --
        for (int i = 0; i < 5; i++) {

            // Get the readable name for each attribute at position i
            // solver.solNat[i] = nationality INDEX -> ShipData.NAT[] = name
            String pos = "Ship " + (i + 1);
            String nat = ShipData.NAT[solver.solNat[i]];
            String col = ShipData.COLOR[solver.solColor[i]];
            String dest = ShipData.DEST[solver.solDest[i]];
            String car = ShipData.CARGO[solver.solCargo[i]];
            String tim = ShipData.TIME[solver.solTime[i]];

            // Check if this row contains one of the two puzzle answers
            // ShipData.PORT_SAID and ShipData.TEA are constants in ShipData.java
            boolean isPortSaid = solver.solDest[i] == ShipData.PORT_SAID;
            boolean isTea = solver.solCargo[i] == ShipData.TEA;

            // Pad strings manually because ANSI codes affect String.format length
            // Calls padOrColor() helper (defined below in this file)
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
     * Helper: pads a string and optionally colors it green.
     * CALLED FROM: printSolutionTable() (above, in this file).
     * WHY: ANSI codes break String.format alignment, so we pad manually.
     *
     * @param text    the cell text (e.g., "Port Said")
     * @param width   column width in characters
     * @param colored true = green + "<" arrow for answer cells
     * @return formatted string with correct padding
     */
    private static String padOrColor(String text, int width, boolean colored) {
        // Calculate padding: e.g., "Tea"=3 chars, width=7, spaces=4
        int spaces = width - text.length();
        // StringBuilder is more efficient than string concatenation in loops
        StringBuilder sb = new StringBuilder();

        if (colored) {
            sb.append(GREEN).append(text).append(" <").append(RESET);
            // Adjust: " <" is 2 extra chars, so reduce padding by 2
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
     *
     * CALLED FROM: Main.java -> case "2" and case "3".
     * DATA FLOW: Calls solver.getPortSaidPosition() and solver.getTeaPosition()
     *   (in ShipSolver.java), then looks up ShipData.NAT[] (in ShipData.java).
     *
     * @param solver the ShipSolver that has already been run
     */
    public static void printAnswers(ShipSolver solver) {
        // Find the nationality of the Port Said ship
        // Calls solver.getPortSaidPosition() in ShipSolver.java
        int psPos = solver.getPortSaidPosition();
        // Convert position -> nationality name via ShipData.NAT[] (ShipData.java)
        String portSaidShip = (psPos >= 0) ? ShipData.NAT[solver.solNat[psPos]] : "Unknown";

        // Find the nationality of the Tea ship
        // Calls solver.getTeaPosition() in ShipSolver.java
        int teaPos = solver.getTeaPosition();
        String teaShip = (teaPos >= 0) ? ShipData.NAT[solver.solNat[teaPos]] : "Unknown";

        // Double-line (===) border makes answers box stand out
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
     * Prints deduction steps one at a time, waiting for ENTER.
     *
     * CALLED FROM: Main.java -> case "3".
     * DATA SOURCE: ShipData.DEDUCTION_STEPS[][] (in ShipData.java).
     *   [i][0] = clue reference, [i][1] = deduction explanation.
     *
     * @param scanner same Scanner from Main.java's main() method
     */
    public static void printStepByStep(java.util.Scanner scanner) {
        System.out.println();
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println(YELLOW + "  STEP-BY-STEP DEDUCTION  (Press ENTER for each step)" + RESET);
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println();

        // Go through each deduction step stored in ShipData
        // ShipData.DEDUCTION_STEPS is a String[][] in ShipData.java
        for (int i = 0; i < ShipData.DEDUCTION_STEPS.length; i++) {
            String clueRef = ShipData.DEDUCTION_STEPS[i][0]; // e.g., "Clues 1+15"
            String deduction = ShipData.DEDUCTION_STEPS[i][1]; // the explanation

            // Print step number and clue reference
            // "%2d" = 2-digit number, "%-13s" = 13-char left-aligned string
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
     * CALLED FROM: QuizMode.java -> run(), after each of the 10 questions.
     *
     * @param correct true = user guessed right, false = wrong
     * @param answer  the correct answer string (shown when wrong)
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
     * CALLED FROM: QuizMode.java -> run(), at the very end after all 10 questions.
     *
     * @param score number of correct answers (0-10)
     * @param total total number of questions (always 10)
     */
    public static void printQuizScore(int score, int total) {
        // (score * 100.0) ensures float division, not integer division
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
            msg = "  Perfect score! Excellent!         ";  // 100%
        else if (percent >= 70)
            msg = "  Good job! Almost there.           ";  // 70-99%
        else
            msg = "  Keep studying the clues!          ";  // Below 70%

        System.out.println(CYAN + "  |" + RESET + msg + CYAN + "  |" + RESET);
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.println();
    }

    /**
     * Prints a simple separator line between menu sections.
     * CALLED FROM: Main.java -> cases "1", "2", "3", "4".
     */
    public static void printSeparator() {
        System.out.println("  " + BLUE + "---------------------------------------------------------" + RESET);
        System.out.println();
    }

    /**
     * Prints an error message for invalid menu input.
     * CALLED FROM: Main.java -> default case in switch.
     */
    public static void printInvalidInput() {
        System.out.println("  " + RED + "[!] Invalid choice. Please enter a number from the menu." + RESET);
        System.out.println();
    }
}
