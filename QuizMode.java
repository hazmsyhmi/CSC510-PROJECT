import java.util.Scanner;

public class QuizMode {

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

        int score = 0;
        int totalQ = 0;

        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " Which nationality ship goes to HAMBURG?");
        System.out.println("  [1] Greek   [2] English   [3] French   [4] Spanish   [5] Brazilian");

        int hamburgPos = findDestPos(solver, ShipData.HAMBURG);
        int correctNat = solver.solNat[hamburgPos];
        int guess = getChoice(scanner, 1, 5);

        boolean correct = (guess - 1) == correctNat;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.NAT[correctNat]);

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

        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What time does the ENGLISH ship depart?");
        System.out.println("  [1] 5:00   [2] 6:00   [3] 7:00   [4] 8:00   [5] 9:00");

        int engPos = findNatPos(solver, ShipData.ENGLISH);
        int correctTime = solver.solTime[engPos];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctTime;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.TIME[correctTime]);

        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the ship in position 3 (middle) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        int correctCargo = solver.solCargo[2];
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == correctCargo;
        if (correct) score++;
        Printer.printQuizFeedback(correct, ShipData.CARGO[correctCargo]);

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

        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What POSITION (1-5, left to right) is the GREEK ship?");
        System.out.println("  [1] Position 1   [2] Position 2   [3] Position 3   [4] Position 4   [5] Position 5");

        int greekPos = findNatPos(solver, ShipData.GREEK);
        guess = getChoice(scanner, 1, 5);
        correct = (guess - 1) == greekPos;
        if (correct) score++;
        Printer.printQuizFeedback(correct, "Position " + (greekPos + 1));

        totalQ++;
        System.out.println("  " + Printer.BOLD + "Question " + totalQ + ":" + Printer.RESET +
            " What cargo does the BORDER ship (position 1 or 5) carry?");
        System.out.println("  [1] Coffee   [2] Tea   [3] Cocoa   [4] Rice   [5] Corn");

        int correctBorderCargo = solver.solCargo[0];
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

        Printer.printQuizScore(score, totalQ);
    }

    private static int getChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("  Your answer: ");
            String input = scanner.nextLine().trim();

            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) {
                    return val;
                } else {
                    System.out.println("  " + Printer.RED +
                        "[!] Please enter a number between " + min + " and " + max + "." + Printer.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println("  " + Printer.RED +
                    "[!] Invalid input. Please enter a number." + Printer.RESET);
            }
        }
    }

    private static int findDestPos(ShipSolver solver, int destVal) {
        for (int i = 0; i < 5; i++) {
            if (solver.solDest[i] == destVal) return i;
        }
        return -1;
    }

    private static int findNatPos(ShipSolver solver, int natVal) {
        for (int i = 0; i < 5; i++) {
            if (solver.solNat[i] == natVal) return i;
        }
        return -1;
    }
}
