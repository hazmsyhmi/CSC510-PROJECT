import java.util.Scanner;

/**
 * ============================================================
 *  FILE: Main.java
 *  PURPOSE: Entry point for Puzzle 3 - The Five Ships program.
 *           Controls the main menu loop and coordinates all
 *           other classes.
 *
 *  HOW TO COMPILE AND RUN:
 *  ---------------------------------------------------------
 *  Step 1 -- Open a terminal in the folder containing all .java files.
 *
 *  Step 2 -- Compile ALL files at once:
 *    javac *.java
 *
 *  Step 3 -- Run the program:
 *    java Main
 *  ---------------------------------------------------------
 *
 *  FILE STRUCTURE & RESPONSIBILITIES:
 *  ---------------------------------------------------------
 *  Main.java       -> YOU ARE HERE. Menu loop & program flow.
 *  ShipData.java   -> All constants (names, indices, clues).
 *  ShipSolver.java -> Core constraint-satisfaction algorithm.
 *  Printer.java    -> All formatted console output & colors.
 *  QuizMode.java   -> Interactive quiz where user guesses answers.
 *  ---------------------------------------------------------
 *
 *  DESIGN PRINCIPLE: Single Responsibility
 *  Each file does ONE job. Main.java only controls flow.
 *  It delegates everything to the appropriate specialist class.
 * ============================================================
 */

// -----------------------------------------------------------------------
// CLASS: Main
// This is the only class with a main() method, so it is the starting point
// when you run "java Main". It does NOT solve the puzzle itself —
// it delegates to ShipSolver.java for solving, Printer.java for display,
// and QuizMode.java for the quiz.
// -----------------------------------------------------------------------
public class Main {

    // ===================================================================
    // METHOD: main(String[] args)
    // This is the entry point of the entire program. Java looks for this
    // exact method signature to start execution. The "args" parameter
    // holds any command-line arguments (not used here).
    // ===================================================================
    public static void main(String[] args) {

        // -- Setup -------------------------------------------------

        // Scanner reads keyboard input from the user throughout the program
        // "System.in" is the standard input stream (keyboard)
        // This single Scanner instance is shared across the program — it is
        // passed to other methods/classes that need user input.
        Scanner scanner = new Scanner(System.in);

        // Create a ShipSolver instance -- it stores the solution once solved
        // ShipSolver is defined in ShipSolver.java — it contains the
        // constraint-satisfaction algorithm that solves the puzzle.
        // After solver.solve() is called, its solution arrays (solNat, solColor,
        // solDest, solCargo, solTime) hold the answer.
        ShipSolver solver = new ShipSolver();

        // Track whether the puzzle has been solved yet
        // Quiz mode and step-by-step require the solution to exist first
        // This boolean acts as a "guard" — certain menu options check it
        // before proceeding, so the user can't view results that don't exist yet.
        boolean isSolved = false;

        // -- Opening Banner ----------------------------------------

        // Calls Printer.printBanner() in Printer.java
        // This prints the decorative title box at the top of the program
        // using ANSI color codes for a professional look.
        Printer.printBanner();

        // Brief introduction text
        // These are simple System.out.println() calls — no external method needed
        System.out.println("  Welcome! This program solves the Five Ships logic puzzle");
        System.out.println("  using a constraint-satisfaction algorithm.");
        System.out.println("  There are 5 ships with 5 attributes each, and 15 clues.");
        System.out.println();

        // -- Main Menu Loop ----------------------------------------
        // The program keeps showing the menu until the user chooses to exit.
        // This is a standard "menu-driven" program structure.

        // "running" controls the while loop below — when set to false,
        // the loop ends and the program exits.
        boolean running = true;

        // ---------------------------------------------------------------
        // MAIN LOOP: keeps running until user picks option 5 (Exit).
        // Each iteration: display menu -> read input -> process choice.
        // ---------------------------------------------------------------
        while (running) {

            // Display the menu options
            // Calls Printer.printMenu() in Printer.java
            // This prints the formatted menu box with options [1] through [5].
            Printer.printMenu();

            // Read the user's choice as a line of text
            // .trim() removes any leading/trailing whitespace the user may have typed
            String input = scanner.nextLine().trim();

            // Parse and handle the user's choice
            // A switch statement checks "input" against each case ("1", "2", etc.)
            // and runs the matching block of code.
            switch (input) {

                // -- Option 1: View Clues -------------------------
                // Shows all 15 puzzle clues stored in ShipData.CLUES[]
                case "1":
                    // Calls Printer.printSeparator() in Printer.java
                    // Prints a blue dashed line to visually separate menu from content
                    Printer.printSeparator();

                    // Calls Printer.printClues() in Printer.java
                    // This method loops through ShipData.CLUES[] (defined in ShipData.java)
                    // and prints each clue inside a yellow bordered box.
                    Printer.printClues();

                    // Pause so user can read before menu reappears
                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // -- Option 2: Solve the Puzzle -------------------
                // Runs the constraint-satisfaction algorithm and displays the solution
                case "2":
                    // Calls Printer.printSeparator() in Printer.java
                    Printer.printSeparator();

                    // Show an animated "working..." display
                    // Calls Printer.printSolvingAnimation(200) in Printer.java
                    // The parameter 200 = milliseconds between each animated dot.
                    // This is purely cosmetic — gives the user visual feedback.
                    Printer.printSolvingAnimation(200);

                    // Run the actual constraint solver
                    // Calls solver.solve() in ShipSolver.java
                    // This is the core algorithm — it tries all permutations of
                    // ship attributes and checks them against the 15 clues.
                    // Returns true if a valid solution is found, false otherwise.
                    // After this call, solver.solNat[], solver.solColor[], etc.
                    // contain the solution data.
                    boolean found = solver.solve();

                    if (found) {
                        isSolved = true; // Mark as solved so other modes can use it

                        // Display the full solution table
                        // Calls Printer.printSolutionTable(solver) in Printer.java
                        // Reads solver.solNat[], solColor[], solDest[], solCargo[], solTime[]
                        // and formats them into a neat ASCII table. Also uses ShipData.NAT[],
                        // ShipData.COLOR[], etc. (from ShipData.java) to convert index
                        // numbers back into readable names.
                        Printer.printSolutionTable(solver);

                        // Display the highlighted answers to the two puzzle questions
                        // Calls Printer.printAnswers(solver) in Printer.java
                        // This calls solver.getPortSaidPosition() and solver.getTeaPosition()
                        // (both in ShipSolver.java) to find the two key answers, then
                        // formats them in a green highlighted box.
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

                // -- Option 3: Step-by-Step Deduction -------------
                // Shows the logical reasoning behind the solution, one step at a time
                case "3":
                    // Calls Printer.printSeparator() in Printer.java
                    Printer.printSeparator();

                    // Check if solver has been run -- we need the solution
                    // If the user hasn't solved the puzzle yet (option 2),
                    // we can't show deduction steps because there's no solution to reference.
                    if (!isSolved) {
                        // Printer.YELLOW and Printer.RESET are ANSI color code constants
                        // defined in Printer.java — used here for inline colored output.
                        System.out.println("  " + Printer.YELLOW +
                            "[!] Please solve the puzzle first (Option 2) before viewing deduction steps." +
                            Printer.RESET);
                        System.out.println();
                        break;
                    }

                    // Walk through each deduction step interactively
                    // Calls Printer.printStepByStep(scanner) in Printer.java
                    // This method reads from ShipData.DEDUCTION_STEPS[][] (in ShipData.java)
                    // and displays each step one at a time, pausing for ENTER between each.
                    // The scanner is passed so it can wait for user input.
                    Printer.printStepByStep(scanner);

                    // After steps, show the final solution as a reminder
                    System.out.println("  Here is the complete solution for reference:");

                    // Calls Printer.printSolutionTable(solver) in Printer.java
                    // Same as option 2 — shows the full solution table again.
                    Printer.printSolutionTable(solver);

                    // Calls Printer.printAnswers(solver) in Printer.java
                    // Same as option 2 — shows the two key puzzle answers.
                    Printer.printAnswers(solver);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // -- Option 4: Quiz Mode --------------------------
                // Interactive quiz where the user guesses ship attributes
                case "4":
                    // Calls Printer.printSeparator() in Printer.java
                    Printer.printSeparator();

                    // Quiz mode needs the solution to check answers
                    if (!isSolved) {
                        // Auto-solve silently so quiz can proceed
                        // Instead of blocking the user, we solve automatically here.
                        System.out.println("  " + Printer.CYAN +
                            "[*] Solving the puzzle first before starting quiz..." + Printer.RESET);

                        // Calls solver.solve() in ShipSolver.java
                        // Runs the solver in the background so quiz has answers to check against.
                        solver.solve();
                        isSolved = true;
                        System.out.println("  " + Printer.GREEN + "[OK] Ready! Starting quiz..." + Printer.RESET);
                    }

                    // Run the full interactive quiz
                    // Calls QuizMode.run(solver, scanner) in QuizMode.java
                    // This is the main entry point of the quiz feature.
                    // It asks 10 questions about the solution, reads user answers
                    // via the scanner, checks them against solver's solution arrays,
                    // and calls Printer.printQuizFeedback() (in Printer.java) for each answer.
                    // At the end, it calls Printer.printQuizScore() (in Printer.java)
                    // to display the final score.
                    QuizMode.run(solver, scanner);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                // -- Option 5: Exit -------------------------------
                // Ends the program gracefully
                case "5":
                    System.out.println();
                    // Printer.CYAN is the ANSI code for cyan color (defined in Printer.java)
                    System.out.println(Printer.CYAN +
                        "  Thank you for using the Five Ships Solver!" + Printer.RESET);
                    System.out.println("  -- Discrete Structures Group Assignment");
                    System.out.println();
                    running = false; // Exit the while loop, ending the program
                    break;

                // -- Invalid Input --------------------------------
                // User typed something that isn't 1-5
                default:
                    // Calls Printer.printInvalidInput() in Printer.java
                    // Prints a red error message telling the user to enter a valid number.
                    Printer.printInvalidInput();
                    break;
            }
        }

        // Close the scanner to free system resources
        // This is good practice — prevents resource leaks.
        scanner.close();

        // Program ends here -- Java exits automatically
    }
}
