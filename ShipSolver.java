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
 * ============================================================
 */
public class ShipSolver {

    // ----------------------------------------------------------
    // SOLUTION STORAGE
    // Once solved, the solution arrays are stored here.
    // Each array has 5 elements (one per ship position 0-4).
    // Example: solNat[2] = 0 means ship at position 3 is Greek.
    // ----------------------------------------------------------

    /** Nationality of each ship by position */
    public int[] solNat;

    /** Chimney color of each ship by position */
    public int[] solColor;

    /** Destination of each ship by position */
    public int[] solDest;

    /** Cargo of each ship by position */
    public int[] solCargo;

    /** Departure time of each ship by position */
    public int[] solTime;

    /** How long the solver took (milliseconds) */
    public long solveTimeMs;

    /** Total number of full combinations checked before solution found */
    public long combinationsChecked = 0;

    // ----------------------------------------------------------
    // PUBLIC SOLVE METHOD
    // Call this to run the solver. Returns true if a solution
    // was found, false otherwise.
    // ----------------------------------------------------------

    /**
     * Runs the constraint-satisfaction solver.
     * Populates the sol* arrays if a solution is found.
     *
     * @return true if solution found, false if no solution exists
     */
    public boolean solve() {
        long startTime = System.currentTimeMillis();

        // Generate all 120 permutations of [0, 1, 2, 3, 4]
        // These represent the 5 possible positions for each attribute value
        List<int[]> perms = generatePermutations(new int[] { 0, 1, 2, 3, 4 });

        // -- LAYER 1: Try every permutation of nationalities ------
        for (int[] nat : perms) {

            // Find where each nationality sits in this permutation
            int greekPos = indexOf(nat, ShipData.GREEK);
            int englishPos = indexOf(nat, ShipData.ENGLISH);
            int frenchPos = indexOf(nat, ShipData.FRENCH);
            int spanishPos = indexOf(nat, ShipData.SPANISH);
            int brazilPos = indexOf(nat, ShipData.BRAZILIAN);

            // -- PRUNE: Clue 4 ------------------------------------
            // "French ship is LEFT of the coffee ship."
            // Clue 1 says Greek carries coffee.
            // So French position must be strictly less than Greek position.
            if (frenchPos >= greekPos)
                continue; // Skip this nationality arrangement

            // -- LAYER 2: Try every permutation of chimney colors -
            for (int[] color : perms) {

                int blackPos = indexOf(color, ShipData.BLACK);
                int redPos = indexOf(color, ShipData.RED);
                int greenPos = indexOf(color, ShipData.GREEN);
                int whitePos = indexOf(color, ShipData.WHITE);

                // -- PRUNE: Clue 2 --------------------------------
                // "The ship in the MIDDLE has a black chimney."
                // Middle position = index 2 (5 ships, 0-indexed)
                if (blackPos != 2)
                    continue;

                // -- PRUNE: Clue 4 (part 2) -----------------------
                // "The FRENCH ship has a BLUE chimney."
                // The chimney at the French ship's position must be blue.
                if (color[frenchPos] != ShipData.BLUE)
                    continue;

                // -- LAYER 3: Try every permutation of destinations -
                for (int[] dest : perms) {

                    int hamburgPos = indexOf(dest, ShipData.HAMBURG);
                    int marseillePos = indexOf(dest, ShipData.MARSEILLE);

                    // -- PRUNE: Clue 6 ----------------------------
                    // "Brazilian ship heading for Manila."
                    if (dest[brazilPos] != ShipData.MANILA)
                        continue;

                    // -- PRUNE: Clue 10 ---------------------------
                    // "Red chimney ship goes to Hamburg."
                    // So Hamburg must be at the same position as red chimney.
                    if (hamburgPos != redPos)
                        continue;

                    // -- PRUNE: Clue 9 ----------------------------
                    // "Spanish is to the RIGHT of the Marseille ship."
                    // Right means spanishPos = marseillePos + 1 (exactly next door)
                    if (spanishPos != marseillePos + 1)
                        continue;

                    // -- LAYER 4: Try every permutation of cargos -
                    for (int[] cargo : perms) {

                        int cocoaPos = indexOf(cargo, ShipData.COCOA);
                        int ricePos = indexOf(cargo, ShipData.RICE);
                        int cornPos = indexOf(cargo, ShipData.CORN);

                        // -- PRUNE: Clue 1 ------------------------
                        // "Greek ship carries coffee."
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
                        if (!nextTo(cornPos, ricePos))
                            continue;

                        // -- PRUNE: Clue 7 ------------------------
                        // "Next to rice ship is a GREEN chimney ship."
                        if (!nextTo(ricePos, greenPos))
                            continue;

                        // -- LAYER 5: Try every permutation of times -
                        for (int[] time : perms) {

                            // -- CHECK: Clue 1 --------------------
                            // "Greek ship leaves at SIX (6:00)."
                            if (time[greekPos] != ShipData.T6)
                                continue;

                            // -- CHECK: Clue 3 --------------------
                            // "English ship leaves at NINE (9:00)."
                            if (time[englishPos] != ShipData.T9)
                                continue;

                            // -- CHECK: Clue 8 --------------------
                            // "Ship going to Genoa leaves at FIVE (5:00)."
                            int genoaPos = indexOf(dest, ShipData.GENOA);
                            if (time[genoaPos] != ShipData.T5)
                                continue;

                            // -- CHECK: Clue 9 --------------------
                            // "Spanish ship leaves at SEVEN (7:00)."
                            if (time[spanishPos] != ShipData.T7)
                                continue;

                            // -- CHECK: Clue 11 -------------------
                            // "NEXT TO the ship leaving at 7:00 is a WHITE chimney."
                            // The 7:00 ship is Spanish (from Clue 9).
                            if (!nextTo(spanishPos, whitePos))
                                continue;

                            // -- CHECK: Clue 13 -------------------
                            // "Black chimney ship leaves at EIGHT (8:00)."
                            if (time[blackPos] != ShipData.T8)
                                continue;

                            // -- CHECK: Clue 15 -------------------
                            // "Hamburg ship leaves at SIX (6:00)."
                            if (time[hamburgPos] != ShipData.T6)
                                continue;

                            // -------------------------------------
                            // ALL 15 CLUES SATISFIED!
                            // Save this combination as the solution.
                            // -------------------------------------
                            combinationsChecked++;
                            solNat = nat.clone();
                            solColor = color.clone();
                            solDest = dest.clone();
                            solCargo = cargo.clone();
                            solTime = time.clone();
                            solveTimeMs = System.currentTimeMillis() - startTime;
                            return true;
                        }
                        combinationsChecked++;
                    }
                }
            }
        }

        // If we reach here, no valid solution was found
        solveTimeMs = System.currentTimeMillis() - startTime;
        return false;
    }

    // ----------------------------------------------------------
    // HELPER: nextTo
    // Returns true if positions a and b are adjacent (differ by 1).
    // Used to check "next to" clues.
    // Example: nextTo(2, 3) = true, nextTo(0, 2) = false
    // ----------------------------------------------------------

    /**
     * Checks if two ship positions are adjacent (next to each other).
     *
     * @param a position of first ship
     * @param b position of second ship
     * @return true if |a - b| == 1
     */
    private boolean nextTo(int a, int b) {
        return Math.abs(a - b) == 1;
    }

    // ----------------------------------------------------------
    // HELPER: indexOf
    // Finds the index of a value in an integer array.
    // Java arrays don't have a built-in indexOf, so we write our own.
    // Example: indexOf([2,0,4,1,3], 4) = 2
    // ----------------------------------------------------------

    /**
     * Returns the index of the given value in the array.
     *
     * @param arr the array to search
     * @param val the value to find
     * @return index of val in arr, or -1 if not found
     */
    private int indexOf(int[] arr, int val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == val)
                return i;
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
    // ----------------------------------------------------------

    /**
     * Generates all permutations of the given integer array.
     *
     * @param arr the array to permute (e.g., {0,1,2,3,4})
     * @return list of all 120 permutations
     */
    public List<int[]> generatePermutations(int[] arr) {
        List<int[]> result = new ArrayList<>();
        permHelper(arr, 0, result);
        return result;
    }

    /**
     * Recursive helper for permutation generation.
     * At each call, we fix one element at position 'start'
     * and recursively permute the remaining elements.
     *
     * @param arr    the current state of the array
     * @param start  the current position being fixed
     * @param result accumulator for all permutations found
     */
    private void permHelper(int[] arr, int start, List<int[]> result) {
        // Base case: if start == length, we've fixed all positions
        // The current arrangement is one complete permutation
        if (start == arr.length) {
            result.add(arr.clone()); // Save a COPY (not reference)
            return;
        }

        // Try placing each remaining element at position 'start'
        for (int i = start; i < arr.length; i++) {
            swap(arr, start, i); // Place arr[i] at position 'start'
            permHelper(arr, start + 1, result); // Recurse for next position
            swap(arr, start, i); // Undo swap (backtrack)
        }
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arr the array
     * @param i   index of first element
     * @param j   index of second element
     */
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ----------------------------------------------------------
    // CONVENIENCE METHODS
    // These make it easy to query the solution from other classes.
    // ----------------------------------------------------------

    /**
     * Returns the position (0-4) of the ship going to Port Said.
     * Used to answer puzzle question 1.
     */
    public int getPortSaidPosition() {
        for (int i = 0; i < 5; i++) {
            if (solDest[i] == ShipData.PORT_SAID)
                return i;
        }
        return -1;
    }

    /**
     * Returns the position (0-4) of the ship carrying Tea.
     * Used to answer puzzle question 2.
     */
    public int getTeaPosition() {
        for (int i = 0; i < 5; i++) {
            if (solCargo[i] == ShipData.TEA)
                return i;
        }
        return -1;
    }

    /**
     * Returns the nationality name of the ship at a given position.
     */
    public String getNationalityAt(int position) {
        return ShipData.NAT[solNat[position]];
    }

    /**
     * Returns the destination name of the ship at a given position.
     */
    public String getDestinationAt(int position) {
        return ShipData.DEST[solDest[position]];
    }
}
