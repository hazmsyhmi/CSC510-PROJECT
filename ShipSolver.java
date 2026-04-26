import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * FILE: ShipSolver.java
 * PURPOSE: Contains the core constraint-satisfaction algorithm
 * that solves Puzzle 3 - The Five Ships.
 *
 * ALGORITHM USED: Brute-Force Permutation with Constraint Pruning
 *
 * HOW IT WORKS:
 * 1. Generate ALL possible orderings (permutations) of 5 items.
 * For 5 items, there are 5! = 120 permutations per attribute.
 * 2. Try every combination of (nationality, color, destination,
 * cargo, time) permutations.
 * 3. For each combination, check if ALL 15 puzzle clues are
 * satisfied simultaneously.
 * 4. The first combination that satisfies all clues is the answer.
 *
 * PRUNING OPTIMIZATION:
 * We don't wait until all 5 attributes are chosen before checking
 * clues. Instead, we check relevant clues as early as possible
 * (e.g., after choosing nationality, we immediately check clue 4).
 * This eliminates large branches of the search tree early,
 * reducing the number of combinations we actually check.
 *
 * TIME COMPLEXITY: O(5!^5) worst case = 24,883,200,000 checks,
 * but pruning reduces actual checks to well under 100,000.
 *
 * WHO CALLS THIS FILE?
 * - Main.java calls solver.solve() in case "2" and case "4"
 * - Main.java reads solver.solNat[], solColor[], etc. indirectly
 *   (passes solver to Printer.java and QuizMode.java)
 * - Printer.java reads sol*[] arrays + solveTimeMs for display
 * - QuizMode.java reads sol*[] arrays to check quiz answers
 * - Printer.java calls getPortSaidPosition() and getTeaPosition()
 * ============================================================
 */

// -----------------------------------------------------------------------
// CLASS: ShipSolver
// Unlike ShipData (all static) and Printer (all static), ShipSolver
// is an INSTANCE class — you create an object with "new ShipSolver()"
// and then call solver.solve(). The solution is stored in the object's
// fields (solNat, solColor, etc.) so they persist after solve() returns.
// -----------------------------------------------------------------------
public class ShipSolver {

    // ----------------------------------------------------------
    // SOLUTION STORAGE
    // Once solved, the solution arrays are stored here.
    // Each array has 5 elements (one per ship position 0-4).
    // Example: solNat[2] = 0 means ship at position 3 is Greek.
    //
    // READ BY: Printer.java (printSolutionTable, printAnswers)
    //          QuizMode.java (run method, to verify quiz answers)
    // ----------------------------------------------------------

    /** Nationality of each ship by position */
    // solNat[i] = nationality INDEX (0-4) at ship position i
    // Convert to name: ShipData.NAT[solNat[i]] (done in Printer.java)
    public int[] solNat;

    /** Chimney color of each ship by position */
    // solColor[i] = color INDEX (0-4) at ship position i
    // Convert to name: ShipData.COLOR[solColor[i]] (done in Printer.java)
    public int[] solColor;

    /** Destination of each ship by position */
    // solDest[i] = destination INDEX (0-4) at ship position i
    // Convert to name: ShipData.DEST[solDest[i]] (done in Printer.java)
    public int[] solDest;

    /** Cargo of each ship by position */
    // solCargo[i] = cargo INDEX (0-4) at ship position i
    // Convert to name: ShipData.CARGO[solCargo[i]] (done in Printer.java)
    public int[] solCargo;

    /** Departure time of each ship by position */
    // solTime[i] = time INDEX (0-4) at ship position i
    // Convert to name: ShipData.TIME[solTime[i]] (done in Printer.java)
    public int[] solTime;

    /** How long the solver took (milliseconds) */
    // Set at the end of solve(). Read by Printer.printSolutionTable()
    public long solveTimeMs;

    /** Total number of full combinations checked before solution found */
    // Incremented inside the innermost loop of solve()
    public long combinationsChecked = 0;

    // ----------------------------------------------------------
    // PUBLIC SOLVE METHOD
    // Call this to run the solver. Returns true if a solution
    // was found, false otherwise.
    //
    // CALLED FROM: Main.java -> case "2" (solver.solve())
    //              Main.java -> case "4" (auto-solve before quiz)
    // ----------------------------------------------------------

    /**
     * Runs the constraint-satisfaction solver.
     * Populates the sol* arrays if a solution is found.
     *
     * @return true if solution found, false if no solution exists
     */
    public boolean solve() {
        // Record start time to measure how long solving takes
        long startTime = System.currentTimeMillis();

        // Generate all 120 permutations of [0, 1, 2, 3, 4]
        // These represent the 5 possible positions for each attribute value
        // Calls generatePermutations() (defined below in this file)
        // Returns a List of 120 int[] arrays, each a different ordering
        List<int[]> perms = generatePermutations(new int[] { 0, 1, 2, 3, 4 });

        // ===============================================================
        // THE 5-LAYER NESTED LOOP STRUCTURE:
        // Layer 1: Try all 120 nationality permutations
        //   Layer 2: Try all 120 chimney color permutations
        //     Layer 3: Try all 120 destination permutations
        //       Layer 4: Try all 120 cargo permutations
        //         Layer 5: Try all 120 time permutations
        //
        // At each layer, we PRUNE (skip) invalid combinations early
        // using "continue" statements. This is what makes the solver
        // fast — most branches are eliminated before reaching layer 5.
        // ===============================================================

        // -- LAYER 1: Try every permutation of nationalities ------
        // Each "nat" is an int[5] like {2,0,4,1,3} meaning:
        //   position 0 = nationality 2 (French)
        //   position 1 = nationality 0 (Greek)
        //   etc.
        for (int[] nat : perms) {

            // Find where each nationality sits in this permutation
            // Calls indexOf() (defined below in this file)
            // indexOf(nat, ShipData.GREEK) finds position of Greek in this arrangement
            int greekPos = indexOf(nat, ShipData.GREEK);       // Uses ShipData.GREEK (= 0) from ShipData.java
            int englishPos = indexOf(nat, ShipData.ENGLISH);   // Uses ShipData.ENGLISH (= 1) from ShipData.java
            int frenchPos = indexOf(nat, ShipData.FRENCH);     // Uses ShipData.FRENCH (= 2) from ShipData.java
            int spanishPos = indexOf(nat, ShipData.SPANISH);   // Uses ShipData.SPANISH (= 3) from ShipData.java
            int brazilPos = indexOf(nat, ShipData.BRAZILIAN);  // Uses ShipData.BRAZILIAN (= 4) from ShipData.java

            // -- PRUNE: Clue 4 ------------------------------------
            // "French ship is LEFT of the coffee ship."
            // Clue 1 says Greek carries coffee.
            // So French position must be strictly less than Greek position.
            // "continue" skips to the next nationality permutation
            if (frenchPos >= greekPos)
                continue; // Skip this nationality arrangement

            // -- LAYER 2: Try every permutation of chimney colors -
            for (int[] color : perms) {

                // Find positions of specific colors in this arrangement
                int blackPos = indexOf(color, ShipData.BLACK);   // Uses ShipData.BLACK (= 3)
                int redPos = indexOf(color, ShipData.RED);       // Uses ShipData.RED (= 0)
                int greenPos = indexOf(color, ShipData.GREEN);   // Uses ShipData.GREEN (= 1)
                int whitePos = indexOf(color, ShipData.WHITE);   // Uses ShipData.WHITE (= 4)

                // -- PRUNE: Clue 2 --------------------------------
                // "The ship in the MIDDLE has a black chimney."
                // Middle position = index 2 (5 ships, 0-indexed)
                if (blackPos != 2)
                    continue;

                // -- PRUNE: Clue 4 (part 2) -----------------------
                // "The FRENCH ship has a BLUE chimney."
                // The chimney at the French ship's position must be blue.
                // color[frenchPos] gets the color at the French ship's position
                // ShipData.BLUE (= 2) is defined in ShipData.java
                if (color[frenchPos] != ShipData.BLUE)
                    continue;

                // -- LAYER 3: Try every permutation of destinations -
                for (int[] dest : perms) {

                    // Find positions of specific destinations
                    int hamburgPos = indexOf(dest, ShipData.HAMBURG);       // ShipData.HAMBURG (= 0)
                    int marseillePos = indexOf(dest, ShipData.MARSEILLE);   // ShipData.MARSEILLE (= 1)

                    // -- PRUNE: Clue 6 ----------------------------
                    // "Brazilian ship heading for Manila."
                    // dest[brazilPos] = destination at Brazilian's position
                    // Must equal ShipData.MANILA (= 4, from ShipData.java)
                    if (dest[brazilPos] != ShipData.MANILA)
                        continue;

                    // -- PRUNE: Clue 10 ---------------------------
                    // "Red chimney ship goes to Hamburg."
                    // Hamburg must be at the same position as red chimney
                    if (hamburgPos != redPos)
                        continue;

                    // -- PRUNE: Clue 9 ----------------------------
                    // "Spanish is to the RIGHT of the Marseille ship."
                    // Right means spanishPos = marseillePos + 1 (exactly next door)
                    if (spanishPos != marseillePos + 1)
                        continue;

                    // -- LAYER 4: Try every permutation of cargos -
                    for (int[] cargo : perms) {

                        // Find positions of specific cargo types
                        int cocoaPos = indexOf(cargo, ShipData.COCOA);   // ShipData.COCOA (= 2)
                        int ricePos = indexOf(cargo, ShipData.RICE);     // ShipData.RICE (= 3)
                        int cornPos = indexOf(cargo, ShipData.CORN);     // ShipData.CORN (= 4)

                        // -- PRUNE: Clue 1 ------------------------
                        // "Greek ship carries coffee."
                        // cargo[greekPos] = cargo at Greek's position
                        // Must equal ShipData.COFFEE (= 0, from ShipData.java)
                        if (cargo[greekPos] != ShipData.COFFEE)
                            continue;

                        // -- PRUNE: Clue 5 ------------------------
                        // "To the RIGHT of the cocoa ship is the Marseille ship."
                        // So: cocoaPos + 1 = marseillePos
                        if (cocoaPos + 1 != marseillePos)
                            continue;

                        // -- PRUNE: Clue 12 -----------------------
                        // "Border ship carries corn."
                        // Border = position 0 (leftmost) or position 4 (rightmost)
                        if (cornPos != 0 && cornPos != 4)
                            continue;

                        // -- PRUNE: Clue 14 -----------------------
                        // "Corn ship is NEXT TO the rice ship."
                        // Calls nextTo() (defined below in this file)
                        if (!nextTo(cornPos, ricePos))
                            continue;

                        // -- PRUNE: Clue 7 ------------------------
                        // "Next to rice ship is a GREEN chimney ship."
                        // Calls nextTo() (defined below in this file)
                        if (!nextTo(ricePos, greenPos))
                            continue;

                        // -- LAYER 5: Try every permutation of times -
                        // This is the innermost loop — if we get here,
                        // nationality, color, destination, and cargo all
                        // pass their clue checks. Now we check time clues.
                        for (int[] time : perms) {

                            // -- CHECK: Clue 1 --------------------
                            // "Greek ship leaves at SIX (6:00)."
                            // ShipData.T6 (= 1) from ShipData.java
                            if (time[greekPos] != ShipData.T6)
                                continue;

                            // -- CHECK: Clue 3 --------------------
                            // "English ship leaves at NINE (9:00)."
                            // ShipData.T9 (= 4) from ShipData.java
                            if (time[englishPos] != ShipData.T9)
                                continue;

                            // -- CHECK: Clue 8 --------------------
                            // "Ship going to Genoa leaves at FIVE (5:00)."
                            // First find Genoa's position in this dest permutation
                            int genoaPos = indexOf(dest, ShipData.GENOA);  // ShipData.GENOA (= 2)
                            // ShipData.T5 (= 0) from ShipData.java
                            if (time[genoaPos] != ShipData.T5)
                                continue;

                            // -- CHECK: Clue 9 --------------------
                            // "Spanish ship leaves at SEVEN (7:00)."
                            // ShipData.T7 (= 2) from ShipData.java
                            if (time[spanishPos] != ShipData.T7)
                                continue;

                            // -- CHECK: Clue 11 -------------------
                            // "NEXT TO the ship leaving at 7:00 is a WHITE chimney."
                            // The 7:00 ship is Spanish (from Clue 9).
                            // Calls nextTo() (defined below in this file)
                            if (!nextTo(spanishPos, whitePos))
                                continue;

                            // -- CHECK: Clue 13 -------------------
                            // "Black chimney ship leaves at EIGHT (8:00)."
                            // ShipData.T8 (= 3) from ShipData.java
                            if (time[blackPos] != ShipData.T8)
                                continue;

                            // -- CHECK: Clue 15 -------------------
                            // "Hamburg ship leaves at SIX (6:00)."
                            // ShipData.T6 (= 1) from ShipData.java
                            if (time[hamburgPos] != ShipData.T6)
                                continue;

                            // -------------------------------------
                            // ALL 15 CLUES SATISFIED!
                            // Save this combination as the solution.
                            // .clone() creates a copy so the solution
                            // isn't overwritten by subsequent iterations.
                            // -------------------------------------
                            combinationsChecked++;
                            solNat = nat.clone();      // Save nationality arrangement
                            solColor = color.clone();  // Save color arrangement
                            solDest = dest.clone();    // Save destination arrangement
                            solCargo = cargo.clone();  // Save cargo arrangement
                            solTime = time.clone();    // Save time arrangement
                            // Record how long solving took
                            solveTimeMs = System.currentTimeMillis() - startTime;
                            return true;  // Solution found! Return to Main.java
                        }
                        combinationsChecked++;
                    }
                }
            }
        }

        // If we reach here, no valid solution was found
        // (should never happen for a valid puzzle)
        solveTimeMs = System.currentTimeMillis() - startTime;
        return false;  // Return false to Main.java
    }

    // ----------------------------------------------------------
    // HELPER: nextTo
    // Returns true if positions a and b are adjacent (differ by 1).
    // Used to check "next to" clues.
    // Example: nextTo(2, 3) = true, nextTo(0, 2) = false
    //
    // CALLED FROM: solve() method (above in this file)
    //   - Clue 14: nextTo(cornPos, ricePos)
    //   - Clue 7:  nextTo(ricePos, greenPos)
    //   - Clue 11: nextTo(spanishPos, whitePos)
    // ----------------------------------------------------------

    /**
     * Checks if two ship positions are adjacent (next to each other).
     *
     * @param a position of first ship (0-4)
     * @param b position of second ship (0-4)
     * @return true if |a - b| == 1 (they are neighbors)
     */
    private boolean nextTo(int a, int b) {
        // Math.abs() returns the absolute value (removes negative sign)
        // Two ships are "next to" each other if their positions differ by exactly 1
        return Math.abs(a - b) == 1;
    }

    // ----------------------------------------------------------
    // HELPER: indexOf
    // Finds the index of a value in an integer array.
    // Java arrays don't have a built-in indexOf, so we write our own.
    // Example: indexOf([2,0,4,1,3], 4) = 2
    //
    // CALLED FROM: solve() method (above in this file)
    //   Used to find the POSITION of a specific attribute value
    //   within a permutation array.
    //   Example: indexOf(nat, ShipData.GREEK) finds which position
    //   the Greek ship occupies in this nationality arrangement.
    // ----------------------------------------------------------

    /**
     * Returns the index of the given value in the array.
     *
     * @param arr the array to search (a permutation like [2,0,4,1,3])
     * @param val the value to find (e.g., ShipData.GREEK = 0)
     * @return index of val in arr, or -1 if not found
     */
    private int indexOf(int[] arr, int val) {
        // Linear search through the array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val)
                return i;  // Found it at position i
        }
        return -1; // Should never happen for valid permutations
    }

    // ----------------------------------------------------------
    // HELPER: generatePermutations
    // Generates all possible orderings of the given array.
    // Uses a classic recursive backtracking algorithm (Heap's-style).
    //
    // HOW IT WORKS:
    // To permute [0,1,2,3,4]:
    // - Fix 0 at position 0, permute [1,2,3,4] for remaining spots
    // - Fix 1 at position 0, permute [0,2,3,4] for remaining spots
    // - ... and so on recursively
    //
    // Result: 5! = 120 permutations
    //
    // CALLED FROM: solve() method (above in this file)
    //   Called once at the start to generate all 120 orderings,
    //   which are then reused across all 5 nested loops.
    // ----------------------------------------------------------

    /**
     * Generates all permutations of the given integer array.
     *
     * @param arr the array to permute (e.g., {0,1,2,3,4})
     * @return list of all 120 permutations (each is an int[5])
     */
    public List<int[]> generatePermutations(int[] arr) {
        // Create an empty list to hold all permutations
        List<int[]> result = new ArrayList<>();
        // Start recursive generation from position 0
        // Calls permHelper() (defined below in this file)
        permHelper(arr, 0, result);
        return result;  // Returns list of 120 int[] arrays
    }

    /**
     * Recursive helper for permutation generation.
     * At each call, we fix one element at position 'start'
     * and recursively permute the remaining elements.
     *
     * CALLED FROM: generatePermutations() (above in this file)
     *              and recursively by itself.
     *
     * @param arr    the current state of the array being permuted
     * @param start  the current position being fixed (0 to 4)
     * @param result accumulator list — permutations are added here
     */
    private void permHelper(int[] arr, int start, List<int[]> result) {
        // Base case: if start == length, we've fixed all positions
        // The current arrangement is one complete permutation
        if (start == arr.length) {
            result.add(arr.clone()); // Save a COPY (not reference)
            // .clone() is critical — without it, all 120 entries would
            // point to the SAME array, which keeps getting modified
            return;
        }

        // Try placing each remaining element at position 'start'
        // This generates all possible choices for this position
        for (int i = start; i < arr.length; i++) {
            swap(arr, start, i); // Place arr[i] at position 'start'
            // Calls swap() (defined below in this file)

            permHelper(arr, start + 1, result); // Recurse for next position
            // This recursive call fixes position 'start+1', then 'start+2', etc.

            swap(arr, start, i); // Undo swap (backtrack)
            // Backtracking: restore the array to its previous state
            // so the next iteration of this loop can try a different element
        }
    }

    /**
     * Swaps two elements in an array.
     *
     * CALLED FROM: permHelper() (above in this file)
     *   Used to place elements and undo placements (backtrack).
     *
     * @param arr the array containing elements to swap
     * @param i   index of first element
     * @param j   index of second element
     */
    private void swap(int[] arr, int i, int j) {
        // Classic swap using a temporary variable
        int temp = arr[i];   // Save arr[i] in temp
        arr[i] = arr[j];     // Overwrite arr[i] with arr[j]
        arr[j] = temp;       // Put saved value into arr[j]
    }

    // ----------------------------------------------------------
    // CONVENIENCE METHODS
    // These make it easy to query the solution from other classes.
    //
    // CALLED FROM: Printer.java (printAnswers method)
    //              QuizMode.java (run method, questions 6 and 7)
    // ----------------------------------------------------------

    /**
     * Returns the position (0-4) of the ship going to Port Said.
     * Used to answer puzzle question 1.
     *
     * CALLED FROM: Printer.java -> printAnswers() to find Q1 answer.
     *              QuizMode.java -> run() for quiz question 6.
     */
    public int getPortSaidPosition() {
        // Linear search through solDest[] for PORT_SAID
        // ShipData.PORT_SAID (= 3) is defined in ShipData.java
        for (int i = 0; i < 5; i++) {
            if (solDest[i] == ShipData.PORT_SAID)
                return i;  // Found Port Said at position i
        }
        return -1;  // Should never happen if puzzle is solved
    }

    /**
     * Returns the position (0-4) of the ship carrying Tea.
     * Used to answer puzzle question 2.
     *
     * CALLED FROM: Printer.java -> printAnswers() to find Q2 answer.
     *              QuizMode.java -> run() for quiz question 7.
     */
    public int getTeaPosition() {
        // Linear search through solCargo[] for TEA
        // ShipData.TEA (= 1) is defined in ShipData.java
        for (int i = 0; i < 5; i++) {
            if (solCargo[i] == ShipData.TEA)
                return i;  // Found Tea at position i
        }
        return -1;  // Should never happen if puzzle is solved
    }

    /**
     * Returns the nationality name of the ship at a given position.
     *
     * CALLED FROM: Not currently used by other files, but available
     *              as a convenience method for future use.
     */
    public String getNationalityAt(int position) {
        // solNat[position] gives nationality INDEX, ShipData.NAT[] converts to name
        return ShipData.NAT[solNat[position]];
    }

    /**
     * Returns the destination name of the ship at a given position.
     *
     * CALLED FROM: Not currently used by other files, but available
     *              as a convenience method for future use.
     */
    public String getDestinationAt(int position) {
        // solDest[position] gives destination INDEX, ShipData.DEST[] converts to name
        return ShipData.DEST[solDest[position]];
    }
}
