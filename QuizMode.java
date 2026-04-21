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
 * ============================================================
 */
public class QuizMode {

    // ----------------------------------------------------------
    //  MAIN QUIZ RUNNER
    //  This is the only public method -- it runs the full quiz.
    // ----------------------------------------------------------

    /**
     * Runs the interactive quiz mode.
     * Asks the user 10 questions about the puzzle solution.
     *
     * @param solver  a ShipSolver that has already been solved
     * @param scanner the Scanner reading from System.in
     */
    public static void run(ShipSolver solver, Scanner scanner) {

        System.out.println();
        System.out.println(Printer.MAGENTA +
            "  =========================================================" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  QUIZ MODE -- Can you figure out the ships?" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  Answer based on the 15 clues. Good luck!" + Printer.RESET);
        System.out.println(Printer.MAGENTA +
            "  =========================================================" + Printer.RESET);
        System.out.println();

        int score = 0;       // Number of correct answers
        int totalQ = 0;      // Total questions asked

        // -- QUESTION 1 -------------------------------------------
        // Ask which ship goes to Hamburg
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " Which nationality ship goes to HAMBURG?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        // Find the correct answer from the solution
        int hamburgPos = findDestPos(solver, ShipData.HAMBURG);
        int correctNat = solver.solNat[hamburgPos]; // e.g., 0 = Greek

        int guess = getChoice(scanner, 1, 5);
        // User enters 1-5, but our index is 0-4, so subtract 1
        boolean correct = (guess - 1) == correctNat;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.NAT[correctNat]);

        // -- QUESTION 2 -------------------------------------------
        // Ask which ship goes to Manila
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " Which nationality ship goes to MANILA?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        int manilaPos = findDestPos(solver, ShipData.MANILA);
        int correctNat2 = solver.solNat[manilaPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctNat2;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.NAT[correctNat2]);

        // -- QUESTION 3 -------------------------------------------
        // Ask what time the English ship departs
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What time does the ENGLISH ship depart?");
        System.out.println("  [1] 5:00   [2] 6:00   [3] 7:00   [4] 8:00   [5] 9:00");

        int engPos = findNatPos(solver, ShipData.ENGLISH);
        int correctTime = solver.solTime[engPos]; // Index 0-4 maps to 5-9am
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctTime;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.TIME[correctTime]);

        // -- QUESTION 4 -------------------------------------------
        // Ask what cargo the middle ship (position 3) carries
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the ship in position 3 (middle) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        // Position 3 = index 2 (0-indexed)
        int correctCargo = solver.solCargo[2];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctCargo;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.CARGO[correctCargo]);

        // -- QUESTION 5 -------------------------------------------
        // Ask what chimney color the French ship has
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What chimney color does the FRENCH ship have?");
        System.out.println("  [1] Red   [2] Green   [3] Blue   [4] Black   [5] White");

        int frenchPos = findNatPos(solver, ShipData.FRENCH);
        int correctColor = solver.solColor[frenchPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctColor;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.COLOR[correctColor]);

        // -- QUESTION 6 -------------------------------------------
        // Ask which ship goes to Port Said (the main puzzle question!)
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " " + Printer.YELLOW + "[KEY QUESTION]" + Printer.RESET +
            " Which ship goes to PORT SAID?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        int psPos = solver.getPortSaidPosition();
        int correctPS = solver.solNat[psPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctPS;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.NAT[correctPS]);

        // -- QUESTION 7 -------------------------------------------
        // Ask which ship carries Tea (the second main question!)
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " " + Printer.YELLOW + "[KEY QUESTION]" + Printer.RESET +
            " Which ship carries TEA?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        int teaPos = solver.getTeaPosition();
        int correctTea = solver.solNat[teaPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctTea;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.NAT[correctTea]);

        // -- QUESTION 8 -------------------------------------------
        // Ask what position the Greek ship is at
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What POSITION (1-5, left to right) is the GREEK ship?");
        System.out.println("  [1] Position 1   [2] Position 2   [3] Position 3   [4] Position 4   [5] Position 5");

        int greekPos = findNatPos(solver, ShipData.GREEK);
        guess = getChoice(scanner, 1, 5);
        // User enters 1-5, position index is 0-4
        correct = (guess - 1) == greekPos;
        if (correct) score++;
        Printer.printQuizFeedback(correct, "Position " + (greekPos + 1));

        // -- QUESTION 9 -------------------------------------------
        // Ask what cargo the border ship carries
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the BORDER ship (position 1 or 5) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        // Border ship is whichever of position 0 or 4 carries corn (Clue 12)
        int correctBorderCargo = solver.solCargo[0]; // Could also be position 4 -- corn is always border
        // Actually check which border has corn
        for (int borderIdx : new int[]{0, 4}) {
            if (solver.solCargo[borderIdx] == ShipData.CORN) {
                correctBorderCargo = ShipData.CORN;
                break;
            }
        }
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctBorderCargo;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.CARGO[correctBorderCargo]);

        // -- QUESTION 10 ------------------------------------------
        // Ask what the departure time of the Spanish ship is
        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What time does the SPANISH ship depart?");
        System.out.println("  [1] 5:00   [2] 6:00   [3] 7:00   [4] 8:00   [5] 9:00");

        int spanishPos = findNatPos(solver, ShipData.SPANISH);
        int correctSpTime = solver.solTime[spanishPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctSpTime;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.TIME[correctSpTime]);

        // -- FINAL SCORE -------------------------------------------
        Printer.printQuizScore(score, totalQ);
    }

    // ----------------------------------------------------------
    //  PRIVATE HELPER METHODS
    // ----------------------------------------------------------

    /**
     * Reads a valid integer choice from the user within [min, max].
     * Keeps prompting until a valid number is entered.
     * Handles non-integer inputs gracefully using try-catch.
     *
     * @param scanner the input scanner
     * @param min     minimum valid choice
     * @param max     maximum valid choice
     * @return the valid integer entered by the user
     */
    private static int getChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("  Your answer: ");
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input); // Convert string to int
                if (val >= min && val <= max) {
                    return val; // Valid -- return it
                } else {
                    System.out.println("  " + Printer.RED +
                        "[!] Please enter a number between " + min + " and " + max + "." + Printer.RESET);
                }
            } catch (NumberFormatException e) {
                // User typed something that isn't a number
                System.out.println("  " + Printer.RED +
                    "[!] Invalid input. Please enter a number." + Printer.RESET);
            }
        }
    }

    /**
     * Finds the position (0-4) of a given destination in the solution.
     *
     * @param solver  the solved ShipSolver
     * @param destVal the destination constant (e.g., ShipData.HAMBURG)
     * @return position index (0-4)
     */
    private static int findDestPos(ShipSolver solver, int destVal) {
        for (int i = 0; i < 5; i++) {
            if (solver.solDest[i] == destVal) return i;
        }
        return -1; // Should never happen
    }

    /**
     * Finds the position (0-4) of a given nationality in the solution.
     *
     * @param solver the solved ShipSolver
     * @param natVal the nationality constant (e.g., ShipData.GREEK)
     * @return position index (0-4)
     */
    private static int findNatPos(ShipSolver solver, int natVal) {
        for (int i = 0; i < 5; i++) {
            if (solver.solNat[i] == natVal) return i;
        }
        return -1; // Should never happen
    }
}
