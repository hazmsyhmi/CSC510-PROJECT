import java.util.Scanner;

/**
 * ============================================================
 *  FILE: Main.java
 *  PURPOSE: Entry point for Puzzle 3 – The Five Ships program.
 *           Controls the main menu loop and coordinates all
 *           other classes.
 *
 *  HOW TO COMPILE AND RUN:
 *  ─────────────────────────────────────────────────────────
 *  Step 1 — Open a terminal in the folder containing all .java files.
 *
 *  Step 2 — Compile ALL files at once:
 *    javac *.java
 *
 *  Step 3 — Run the program:
 *    java Main
 *  ─────────────────────────────────────────────────────────
 *
 *  FILE STRUCTURE & RESPONSIBILITIES:
 *  ─────────────────────────────────────────────────────────
 *  Main.java       ← YOU ARE HERE. Menu loop & program flow.
 *  ShipData.java   ← All constants (names, indices, clues).
 *  ShipSolver.java ← Core constraint-satisfaction algorithm.
 *  Printer.java    ← All formatted console output & colors.
 *  QuizMode.java   ← Interactive quiz where user guesses answers.
 *  ─────────────────────────────────────────────────────────
 *
 *  DESIGN PRINCIPLE: Single Responsibility
 *  Each file does ONE job. Main.java only controls flow.
 *  It delegates everything to the appropriate specialist class.
 * ============================================================
 */
public class Main {

    public static void main(String[] args) {

        // ── Setup ─────────────────────────────────────────────
        // Scanner reads keyboard input from the user throughout the program
        Scanner scanner = new Scanner(System.in);

        // Create a ShipSolver instance — it stores the solution once solved
        ShipSolver solver = new ShipSolver();

        // Track whether the puzzle has been solved yet
        // Quiz mode and step-by-step require the solution to exist first
        boolean isSolved = false;

        // ── Opening Banner ────────────────────────────────────
        Printer.printBanner();

        // Brief introduction text
        System.out.println("  Welcome! This program solves the Five Ships logic puzzle");
        System.out.println("  using a constraint-satisfaction algorithm.");
        System.out.println("  There are 5 ships with 5 attributes each, and 15 clues.");
        System.out.println();

        // ── Main Menu Loop ────────────────────────────────────
        // The program keeps showing the menu until the user chooses to exit.
        // This is a standard "menu-driven" program structure.
        boolean running = true;

        while (running) {

            // Display the menu options
            Printer.printMenu();

            // Read the user's choice as a line of text
            String input = scanner.nextLine().trim();

            // Parse and handle the user's choice
            switch (input) {

                // ── Option 1: View Clues ─────────────────────
                case "1":
                    Printer.printSeparator();
                    Printer.printClues();
                    // Pause so user can read before menu reappears
                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // ── Option 2: Solve the Puzzle ───────────────
                case "2":
                    Printer.printSeparator();

                    // Show an animated "working..." display
                    Printer.printSolvingAnimation(200);

                    // Run the actual constraint solver
                    boolean found = solver.solve();

                    if (found) {
                        isSolved = true; // Mark as solved so other modes can use it

                        // Display the full solution table
                        Printer.printSolutionTable(solver);

                        // Display the highlighted answers to the two puzzle questions
                        Printer.printAnswers(solver);

                    } else {
                        // This shouldn't happen for a valid puzzle, but we handle it gracefully
                        System.out.println("  [!] No solution could be found. The puzzle may have no valid answer.");
                        System.out.println("      Please check that all clues are correctly entered in ShipData.java.");
                    }

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // ── Option 3: Step-by-Step Deduction ─────────
                case "3":
                    Printer.printSeparator();

                    // Check if solver has been run — we need the solution
                    if (!isSolved) {
                        System.out.println("  " + Printer.YELLOW +
                            "[!] Please solve the puzzle first (Option 2) before viewing deduction steps." +
                            Printer.RESET);
                        System.out.println();
                        break;
                    }

                    // Walk through each deduction step interactively
                    Printer.printStepByStep(scanner);

                    // After steps, show the final solution as a reminder
                    System.out.println("  Here is the complete solution for reference:");
                    Printer.printSolutionTable(solver);
                    Printer.printAnswers(solver);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // ── Option 4: Quiz Mode ───────────────────────
                case "4":
                    Printer.printSeparator();

                    // Quiz mode needs the solution to check answers
                    if (!isSolved) {
                        // Auto-solve silently so quiz can proceed
                        System.out.println("  " + Printer.CYAN +
                            "[*] Solving the puzzle first before starting quiz..." + Printer.RESET);
                        solver.solve();
                        isSolved = true;
                        System.out.println("  " + Printer.GREEN + "[✔] Ready! Starting quiz..." + Printer.RESET);
                    }

                    // Run the full interactive quiz
                    QuizMode.run(solver, scanner);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // ── Option 5: Exit ────────────────────────────
                case "5":
                    System.out.println();
                    System.out.println(Printer.CYAN +
                        "  Thank you for using the Five Ships Solver!" + Printer.RESET);
                    System.out.println("  — Discrete Structures Group Assignment");
                    System.out.println();
                    running = false; // Exit the while loop, ending the program
                    break;

                // ── Invalid Input ─────────────────────────────
                default:
                    // User typed something that isn't 1–5
                    Printer.printInvalidInput();
                    break;
            }
        }

        // Close the scanner to free system resources
        scanner.close();

        // Program ends here — Java exits automatically
    }
}
