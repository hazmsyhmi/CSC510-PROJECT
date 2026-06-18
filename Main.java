import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ShipSolver solver = new ShipSolver();
        boolean isSolved = false;

        Printer.printBanner();

        System.out.println("  Welcome! This program solves the Five Ships logic puzzle");
        System.out.println("  using a constraint-satisfaction algorithm.");
        System.out.println("  There are 5 ships with 5 attributes each, and 15 clues.");
        System.out.println();

        boolean running = true;

        while (running) {

            Printer.printMenu();

            String input = scanner.nextLine().trim();

            switch (input) {

                case "1":
                    Printer.printSeparator();
                    Printer.printClues();
                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                case "2":
                    Printer.printSeparator();
                    Printer.printSolvingAnimation(200);

                    boolean found = solver.solve();

                    if (found) {
                        isSolved = true;
                        Printer.printSolutionTable(solver);
                        Printer.printAnswers(solver);
                    } else {
                        System.out.println("  [!] No solution could be found. The puzzle may have no valid answer.");
                        System.out.println("      Please check that all clues are correctly entered in ShipData.java.");
                    }

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                case "3":
                    Printer.printSeparator();
                    if (!isSolved) {
                        System.out.println("  " + Printer.YELLOW +
                            "[!] Please solve the puzzle first (Option 2) before viewing deduction steps." +
                            Printer.RESET);
                        System.out.println();
                        break;
                    }

                    Printer.printStepByStep(scanner);

                    System.out.println("  Here is the complete solution for reference:");
                    Printer.printSolutionTable(solver);
                    Printer.printAnswers(solver);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                case "4":
                    Printer.printSeparator();
                    if (!isSolved) {
                        System.out.println("  " + Printer.CYAN +
                            "[*] Solving the puzzle first before starting quiz..." + Printer.RESET);

                        solver.solve();
                        isSolved = true;
                        System.out.println("  " + Printer.GREEN + "[OK] Ready! Starting quiz..." + Printer.RESET);
                    }

                    QuizMode.run(solver, scanner);

                    System.out.print("  Press ENTER to return to the menu...");
                    scanner.nextLine();
                    System.out.println();
                    break;

                case "5":
                    System.out.println();
                    System.out.println(Printer.CYAN +
                        "  Thank you for using the Five Ships Solver!" + Printer.RESET);
                    System.out.println("  -- Discrete Structures Group Assignment");
                    System.out.println();
                    running = false;
                    break;

                default:
                    Printer.printInvalidInput();
                    break;
            }
        }

        scanner.close();
    }
}
