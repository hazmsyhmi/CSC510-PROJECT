public class Printer {

    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";

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

    public static void printClues() {
        System.out.println();
        System.out.println(YELLOW + "  +-- ALL 15 PUZZLE CLUES ------------------------------------------+" + RESET);

        for (String clue : ShipData.CLUES) {
            System.out.println(YELLOW + "  |" + RESET + "  " + clue);
        }

        System.out.println(YELLOW + "  +----------------------------------------------------------------+" + RESET);
        System.out.println();
    }

    public static void printSolvingAnimation(int ms) {
        System.out.print("\n  " + CYAN + "[*] Running constraint solver" + RESET);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print(CYAN + "." + RESET);
        }
        System.out.println();
    }

    public static void printSolutionTable(ShipSolver solver) {
        System.out.println();
        System.out.println(GREEN + "  [OK] Solution found in " + solver.solveTimeMs + "ms!" + RESET);
        System.out.println();

        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);
        System.out.printf("  " + BOLD +
                "| %-8s | %-11s | %-7s | %-12s | %-7s | %-8s |%n" + RESET,
                "Position", "Nationality", "Chimney", "Destination", "Cargo", "Departs");
        System.out.println("  " + BOLD +
                "+----------+-------------+---------+--------------+---------+----------+" + RESET);

        for (int i = 0; i < 5; i++) {
            String pos = "Ship " + (i + 1);
            String nat = ShipData.NAT[solver.solNat[i]];
            String col = ShipData.COLOR[solver.solColor[i]];
            String dest = ShipData.DEST[solver.solDest[i]];
            String car = ShipData.CARGO[solver.solCargo[i]];
            String tim = ShipData.TIME[solver.solTime[i]];

            boolean isPortSaid = solver.solDest[i] == ShipData.PORT_SAID;
            boolean isTea = solver.solCargo[i] == ShipData.TEA;

            String destPadded = padOrColor(dest, 12, isPortSaid);
            String carPadded = padOrColor(car, 7, isTea);

            System.out.printf("  | %-8s | %-11s | %-7s | %s | %s | %-8s |%n",
                    pos, nat, col, destPadded, carPadded, tim);
        }

        System.out.println("  " +
                "+----------+-------------+---------+--------------+---------+----------+");
        System.out.println("  " + GREEN + "< = Answer to puzzle questions" + RESET);
        System.out.println();
    }

    private static String padOrColor(String text, int width, boolean colored) {
        int spaces = width - text.length();
        StringBuilder sb = new StringBuilder();

        if (colored) {
            sb.append(GREEN).append(text).append(" <").append(RESET);
            for (int i = 0; i < Math.max(0, spaces - 2); i++)
                sb.append(' ');
        } else {
            sb.append(text);
            for (int i = 0; i < spaces; i++)
                sb.append(' ');
        }
        return sb.toString();
    }

    public static void printAnswers(ShipSolver solver) {
        int psPos = solver.getPortSaidPosition();
        String portSaidShip = (psPos >= 0) ? ShipData.NAT[solver.solNat[psPos]] : "Unknown";

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

    public static void printStepByStep(java.util.Scanner scanner) {
        System.out.println();
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println(YELLOW + "  STEP-BY-STEP DEDUCTION  (Press ENTER for each step)" + RESET);
        System.out.println(YELLOW + "  =========================================================" + RESET);
        System.out.println();

        for (int i = 0; i < ShipData.DEDUCTION_STEPS.length; i++) {
            String clueRef = ShipData.DEDUCTION_STEPS[i][0];
            String deduction = ShipData.DEDUCTION_STEPS[i][1];

            System.out.printf("  " + CYAN + "Step %2d" + RESET + " [" + YELLOW + "%-13s" + RESET + "]: ", (i + 1),
                    clueRef);
            System.out.println(deduction);

            System.out.print("  " + BLUE + "(Press ENTER for next step...)" + RESET);
            scanner.nextLine();
        }

        System.out.println();
        System.out.println(GREEN + "  [OK] All deduction steps complete!" + RESET);
        System.out.println();
    }

    public static void printQuizFeedback(boolean correct, String answer) {
        if (correct) {
            System.out.println("  " + GREEN + "[OK] CORRECT! Well done!" + RESET);
        } else {
            System.out.println("  " + RED + "[X] Wrong! The correct answer was: " + BOLD + answer + RESET);
        }
        System.out.println();
    }

    public static void printQuizScore(int score, int total) {
        int percent = (int) Math.round((score * 100.0) / total);
        System.out.println();
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.println(
                CYAN + "  |" + RESET + BOLD + "          QUIZ COMPLETE!              " + RESET + CYAN + "|" + RESET);
        System.out.println(CYAN + "  +======================================+" + RESET);
        System.out.printf(CYAN + "  |" + RESET + "  Score: %d / %d (%d%%)                 " + CYAN + "|%n" + RESET,
                score, total, percent);

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

    public static void printSeparator() {
        System.out.println("  " + BLUE + "---------------------------------------------------------" + RESET);
        System.out.println();
    }

    public static void printInvalidInput() {
        System.out.println("  " + RED + "[!] Invalid choice. Please enter a number from the menu." + RESET);
        System.out.println();
    }
}
