import java.util.Scanner;

/**
 * ============================================================
 *  FILE: QuizMode.java
 *  PURPOSE: Implements the interactive Quiz Mode where the user
 *           tries to guess attributes of each ship, then the
 *           program reveals whether they are correct.
 *
 *  HOW IT WORKS:
 *  1. The solver must already be run before quiz mode starts.
 *  2. The quiz presents 10 questions about the solution.
 *  3. For each question, the user picks from numbered options.
 *  4. The program checks the answer against the solution arrays
 *     stored in the ShipSolver object.
 *  5. A final score is displayed at the end.
 *
 *  DEPENDENCY: Requires a solved ShipSolver object.
 *              Call ShipSolver.solve() before starting the quiz.
 *
 *  WHO CALLS THIS FILE?
 *  - Main.java calls QuizMode.run(solver, scanner) in case "4".
 *
 *  WHAT DOES THIS FILE CALL?
 *  - Reads solver.solNat[], solDest[], solTime[], solColor[], solCargo[]
 *    (solution arrays in ShipSolver.java)
 *  - Calls solver.getPortSaidPosition() and solver.getTeaPosition()
 *    (convenience methods in ShipSolver.java)
 *  - Calls Printer.printQuizFeedback() (in Printer.java) after each question
 *  - Calls Printer.printQuizScore() (in Printer.java) at the end
 *  - Uses ShipData.NAT[], COLOR[], CARGO[], TIME[] (in ShipData.java)
 *    to convert index numbers to readable names
 *  - Uses ShipData index constants like HAMBURG, MANILA, ENGLISH, etc.
 *    (in ShipData.java) to find specific values in solution arrays
 * ============================================================
 */

// -----------------------------------------------------------------------
// CLASS: QuizMode
// Like Printer, all methods are "public/private static" — no objects needed.
// The main entry point is QuizMode.run(), called from Main.java.
// -----------------------------------------------------------------------
public class QuizMode {

    // ----------------------------------------------------------
    //  MAIN QUIZ RUNNER
    //  This is the only public method -- it runs the full quiz.
    // ----------------------------------------------------------

    /**
     * Runs the interactive quiz mode.
     * Asks the user 10 questions about the puzzle solution.
     *
     * CALLED FROM: Main.java -> main() method, inside case "4".
     *   Main.java passes the solver (with solution data) and the
     *   same scanner used for all user input throughout the program.
     *
     * @param solver  a ShipSolver that has already been solved
     *                (its sol*[] arrays must contain valid solution data)
     * @param scanner the Scanner reading from System.in
     *                (same Scanner created in Main.java's main() method)
     */
    public static void run(ShipSolver solver, Scanner scanner) {

        System.out.println();
        // Quiz header — uses Printer.MAGENTA color constant (defined in Printer.java)
        System.out.println(Printer.MAGENTA +
            "  =========================================================" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  QUIZ MODE -- Can you figure out the ships?" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  Answer based on the 15 clues. Good luck!" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  =========================================================" + Printer.RESET);
        System.out.println();

        // Score tracking variables
        int score = 0;       // Number of correct answers (incremented for each correct guess)
        int totalQ = 0;      // Total questions asked (incremented before each question)

        // ===========================================================
        // QUESTION PATTERN (repeated 10 times):
        // 1. Increment totalQ
        // 2. Print the question text
        // 3. Find the correct answer from solver's solution arrays
        // 4. Call getChoice() (below in this file) to read user input
        // 5. Compare user's guess to correct answer
        // 6. If correct, increment score
        // 7. Call Printer.printQuizFeedback() (in Printer.java) to show result
        // ===========================================================

        // -- QUESTION 1 -------------------------------------------
        // Ask which ship goes to Hamburg
        totalQ++;
        // Printer.BOLD is an ANSI color constant (defined in Printer.java)
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " Which nationality ship goes to HAMBURG?");
        // Display the 5 choices — each number maps to a nationality index (1-based)
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        // Find the correct answer from the solution
        // Calls findDestPos() (defined below in this file) to find which
        // position has Hamburg as its destination
        // ShipData.HAMBURG (= 0) is defined in ShipData.java
        int hamburgPos = findDestPos(solver, ShipData.HAMBURG);

        // solver.solNat[hamburgPos] gives the nationality INDEX at that position
        // e.g., if Greek (0) is at the Hamburg position, correctNat = 0
        int correctNat = solver.solNat[hamburgPos]; // e.g., 0 = Greek

        // Read user's choice (1-5)
        // Calls getChoice() (defined below in this file) — validates input
        int guess = getChoice(scanner, 1, 5);

        // User enters 1-5, but our index is 0-4, so subtract 1
        // e.g., user enters "1" (Greek), guess-1 = 0, correctNat = 0 -> correct!
        boolean correct = (guess - 1) == correctNat;
        if (correct) score++;

        // Calls Printer.printQuizFeedback() in Printer.java
        // Shows "[OK] CORRECT!" or "[X] Wrong! Answer was: Greek"
        // ShipData.NAT[correctNat] converts index to name (from ShipData.java)
        Printer.printQuizFeedback(correct, ShipData.NAT[correctNat]);

        // -- QUESTION 2 -------------------------------------------
        // Ask which ship goes to Manila
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " Which nationality ship goes to MANILA?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        // Find Manila's position, then get the nationality at that position
        // Calls findDestPos() (below in this file) with ShipData.MANILA (= 4, from ShipData.java)
        int manilaPos = findDestPos(solver, ShipData.MANILA);
        // solver.solNat[] is the nationality solution array (from ShipSolver.java)
        int correctNat2 = solver.solNat[manilaPos];
        // Calls getChoice() (below in this file) for validated user input
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctNat2;
        if (correct) score++;
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.NAT[correctNat2]);

        // -- QUESTION 3 -------------------------------------------
        // Ask what time the English ship departs
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What time does the ENGLISH ship depart?");
        System.out.println("  [1] 5:00   [2] 6:00   [3] 7:00   [4] 8:00   [5] 9:00");

        // Find English ship's position, then get its departure time
        // Calls findNatPos() (below in this file) with ShipData.ENGLISH (= 1, from ShipData.java)
        int engPos = findNatPos(solver, ShipData.ENGLISH);
        // solver.solTime[] is the departure time solution array (from ShipSolver.java)
        // Index 0-4 maps to 5:00-9:00 (see ShipData.TIME[] in ShipData.java)
        int correctTime = solver.solTime[engPos]; // Index 0-4 maps to 5-9am
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctTime;
        if (correct) score++;
        // ShipData.TIME[correctTime] converts index to time string (from ShipData.java)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.TIME[correctTime]);

        // -- QUESTION 4 -------------------------------------------
        // Ask what cargo the middle ship (position 3) carries
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the ship in position 3 (middle) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        // Position 3 = index 2 (0-indexed)
        // solver.solCargo[] is the cargo solution array (from ShipSolver.java)
        // Directly access index 2 (the middle ship)
        int correctCargo = solver.solCargo[2];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctCargo;
        if (correct) score++;
        // ShipData.CARGO[correctCargo] converts index to cargo name (from ShipData.java)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.CARGO[correctCargo]);

        // -- QUESTION 5 -------------------------------------------
        // Ask what chimney color the French ship has
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What chimney color does the FRENCH ship have?");
        System.out.println("  [1] Red   [2] Green   [3] Blue   [4] Black   [5] White");

        // Find French ship's position, then get its chimney color
        // Calls findNatPos() (below in this file) with ShipData.FRENCH (= 2, from ShipData.java)
        int frenchPos = findNatPos(solver, ShipData.FRENCH);
        // solver.solColor[] is the chimney color solution array (from ShipSolver.java)
        int correctColor = solver.solColor[frenchPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctColor;
        if (correct) score++;
        // ShipData.COLOR[correctColor] converts index to color name (from ShipData.java)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.COLOR[correctColor]);

        // -- QUESTION 6 -------------------------------------------
        // Ask which ship goes to Port Said (the main puzzle question!)
        // This is marked as [KEY QUESTION] because it's one of the two
        // questions the puzzle is trying to answer
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " " + Printer.YELLOW + "[KEY QUESTION]" + Printer.RESET +
            " Which ship goes to PORT SAID?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        // Calls solver.getPortSaidPosition() in ShipSolver.java
        // This is a convenience method that searches solDest[] for PORT_SAID
        int psPos = solver.getPortSaidPosition();
        // Get the nationality at the Port Said position
        int correctPS = solver.solNat[psPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctPS;
        if (correct) score++;
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.NAT[correctPS]);

        // -- QUESTION 7 -------------------------------------------
        // Ask which ship carries Tea (the second main question!)
        // Also marked as [KEY QUESTION]
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " " + Printer.YELLOW + "[KEY QUESTION]" + Printer.RESET +
            " Which ship carries TEA?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        // Calls solver.getTeaPosition() in ShipSolver.java
        // This is a convenience method that searches solCargo[] for TEA
        int teaPos = solver.getTeaPosition();
        // Get the nationality at the Tea position
        int correctTea = solver.solNat[teaPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctTea;
        if (correct) score++;
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.NAT[correctTea]);

        // -- QUESTION 8 -------------------------------------------
        // Ask what position the Greek ship is at
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What POSITION (1-5, left to right) is the GREEK ship?");
        System.out.println("  [1] Position 1   [2] Position 2   [3] Position 3   [4] Position 4   [5] Position 5");

        // Find Greek ship's position
        // Calls findNatPos() (below in this file) with ShipData.GREEK (= 0, from ShipData.java)
        int greekPos = findNatPos(solver, ShipData.GREEK);
        guess = getChoice(scanner, 1, 5);
        // User enters 1-5, position index is 0-4
        correct = (guess - 1) == greekPos;
        if (correct) score++;
        // Show correct answer as "Position X" (1-based, so greekPos + 1)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, "Position " + (greekPos + 1));

        // -- QUESTION 9 -------------------------------------------
        // Ask what cargo the border ship carries
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the BORDER ship (position 1 or 5) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        // Border ship is whichever of position 0 or 4 carries corn (Clue 12)
        // Start with the cargo at position 0 as default
        int correctBorderCargo = solver.solCargo[0]; // Could also be position 4 -- corn is always border

        // Actually check which border has corn
        // ShipData.CORN (= 4) is defined in ShipData.java
        // Loop through border positions (index 0 and 4)
        for (int borderIdx : new int[]{0, 4}) {
            if (solver.solCargo[borderIdx] == ShipData.CORN) {
                correctBorderCargo = ShipData.CORN;  // Set answer to CORN
                break;  // Found it, stop looking
            }
        }
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctBorderCargo;
        if (correct) score++;
        // ShipData.CARGO[correctBorderCargo] converts index to name (from ShipData.java)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.CARGO[correctBorderCargo]);

        // -- QUESTION 10 ------------------------------------------
        // Ask what the departure time of the Spanish ship is
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What time does the SPANISH ship depart?");
        System.out.println("  [1] 5:00   [2] 6:00   [3] 7:00   [4] 8:00   [5] 9:00");

        // Find Spanish ship's position, then get its departure time
        // Calls findNatPos() (below in this file) with ShipData.SPANISH (= 3, from ShipData.java)
        int spanishPos = findNatPos(solver, ShipData.SPANISH);
        // solver.solTime[] is the time solution array (from ShipSolver.java)
        int correctSpTime = solver.solTime[spanishPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctSpTime;
        if (correct) score++;
        // ShipData.TIME[correctSpTime] converts index to time string (from ShipData.java)
        // Calls Printer.printQuizFeedback() in Printer.java
        Printer.printQuizFeedback(correct, ShipData.TIME[correctSpTime]);

        // -- FINAL SCORE -------------------------------------------
        // Calls Printer.printQuizScore(score, totalQ) in Printer.java
        // Displays the final score box with percentage and feedback message
        Printer.printQuizScore(score, totalQ);
    }

    // ----------------------------------------------------------
    //  PRIVATE HELPER METHODS
    //  These are only used within this file (private access).
    // ----------------------------------------------------------

    /**
     * Reads a valid integer choice from the user within [min, max].
     * Keeps prompting until a valid number is entered.
     * Handles non-integer inputs gracefully using try-catch.
     *
     * CALLED FROM: run() method (above in this file),
     *              once for each of the 10 quiz questions.
     *
     * @param scanner the input scanner (same Scanner from Main.java)
     * @param min     minimum valid choice (always 1)
     * @param max     maximum valid choice (always 5)
     * @return the valid integer entered by the user (1-5)
     */
    private static int getChoice(Scanner scanner, int min, int max) {
        // Infinite loop — keeps asking until valid input is received
        while (true) {
            System.out.print("  Your answer: ");

            // Read user input as a string and trim whitespace
            String input = scanner.nextLine().trim();

            try {
                // Try to convert the string to an integer
                // Integer.parseInt() throws NumberFormatException if input isn't a number
                int val = Integer.parseInt(input); // Convert string to int

                if (val >= min && val <= max) {
                    return val; // Valid -- return it to the caller
                } else {
                    // Number is outside the valid range
                    // Uses Printer.RED color constant (defined in Printer.java)
                    System.out.println("  " + Printer.RED +
                        "[!] Please enter a number between " + min + " and " + max + "." + Printer.RESET);
                }
            } catch (NumberFormatException e) {
                // User typed something that isn't a number (e.g., "abc")
                // The exception is caught here so the program doesn't crash
                System.out.println("  " + Printer.RED +
                    "[!] Invalid input. Please enter a number." + Printer.RESET);
            }
        }
    }

    /**
     * Finds the position (0-4) of a given destination in the solution.
     *
     * CALLED FROM: run() method (above in this file),
     *              for questions about Hamburg (Q1) and Manila (Q2).
     *
     * HOW IT WORKS: Searches solver.solDest[] (from ShipSolver.java)
     *   for the given destination value and returns its index.
     *
     * @param solver  the solved ShipSolver (its solDest[] holds the solution)
     * @param destVal the destination constant (e.g., ShipData.HAMBURG from ShipData.java)
     * @return position index (0-4) where that destination is assigned
     */
    private static int findDestPos(ShipSolver solver, int destVal) {
        // Linear search through the destination solution array
        for (int i = 0; i < 5; i++) {
            // solver.solDest[i] is the destination INDEX at position i
            if (solver.solDest[i] == destVal) return i;  // Found it
        }
        return -1; // Should never happen if puzzle is solved correctly
    }

    /**
     * Finds the position (0-4) of a given nationality in the solution.
     *
     * CALLED FROM: run() method (above in this file),
     *              for questions about English (Q3), French (Q5),
     *              Greek (Q8), and Spanish (Q10).
     *
     * HOW IT WORKS: Searches solver.solNat[] (from ShipSolver.java)
     *   for the given nationality value and returns its index.
     *
     * @param solver the solved ShipSolver (its solNat[] holds the solution)
     * @param natVal the nationality constant (e.g., ShipData.GREEK from ShipData.java)
     * @return position index (0-4) where that nationality is assigned
     */
    private static int findNatPos(ShipSolver solver, int natVal) {
        // Linear search through the nationality solution array
        for (int i = 0; i < 5; i++) {
            // solver.solNat[i] is the nationality INDEX at position i
            if (solver.solNat[i] == natVal) return i;  // Found it
        }
        return -1; // Should never happen if puzzle is solved correctly
    }
}
