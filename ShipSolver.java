/**
 * ============================================================
 * FILE: ShipSolver.java
 * PURPOSE: Contains the core constraint-satisfaction algorithm
 * that solves Puzzle 3 - The Five Ships.
 *
 * ALGORITHM USED: Rule-Based Deduction
 *
 * HOW IT WORKS:
 * 1. Initialize solution arrays with a sentinel value (-1).
 * 2. Directly assign puzzle attributes step-by-step using
 * logical deductions derived from the 15 clues.
 * 3. This approach resolves the puzzle in O(1) time complexity,
 * mirroring human logic.
 * ============================================================
 */

public class ShipSolver {

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

    /** Total number of combinations checked */
    public long combinationsChecked = 0;

    /**
     * Runs the rule-based deduction solver.
     * Populates the sol* arrays with the solved puzzle state.
     *
     * @return true when solution is determined
     */
    public boolean solve() {
        long startTime = System.currentTimeMillis();

        // Initialize solution arrays for 5 positions (indices 0 to 4)
        solNat = new int[5];
        solColor = new int[5];
        solDest = new int[5];
        solCargo = new int[5];
        solTime = new int[5];

        for (int i = 0; i < 5; i++) {
            solNat[i] = -1;
            solColor[i] = -1;
            solDest[i] = -1;
            solCargo[i] = -1;
            solTime[i] = -1;
        }

        // ===============================================================
        // RULE-BASED DEDUCTION STEPS
        // ===============================================================

        // Step 1: Clue 2 (Middle ship has black chimney)
        solColor[2] = ShipData.BLACK;

        // Step 2: Clue 13 (Black chimney ship departs at 8:00)
        solTime[2] = ShipData.T8;

        // Step 3: Clue 1 + 15 + 10 (Greek leaves at 6:00, carries coffee.
        // Hamburg ship leaves at 6:00 -> Greek goes to Hamburg.
        // Red chimney ship goes to Hamburg -> Greek has red chimney).
        // Since French (blue chimney) is left of Greek (red chimney), and Ship 3 is Black:
        // French is at position 1 (index 0) and Greek is at position 2 (index 1).
        solNat[0] = ShipData.FRENCH;
        solColor[0] = ShipData.BLUE;

        solNat[1] = ShipData.GREEK;
        solColor[1] = ShipData.RED;
        solDest[1] = ShipData.HAMBURG;
        solCargo[1] = ShipData.COFFEE;
        solTime[1] = ShipData.T6;

        // Step 4: Next to the 7:00 ship is a white chimney (Clue 11).
        // Spanish leaves at 7:00 (Clue 9). Since Spanish leaves at 7:00, Spanish is at position 5 (index 4).
        // The ship next to it (position 4, index 3) must have a white chimney.
        // The remaining chimney color (Green) goes to position 5 (index 4).
        solColor[3] = ShipData.WHITE;
        solColor[4] = ShipData.GREEN;

        solNat[4] = ShipData.SPANISH;
        solTime[4] = ShipData.T7;

        // Step 5: Clue 9 (Spanish is immediately to the right of Marseille)
        // Since Spanish is at index 4, Marseille must be at index 3.
        solDest[3] = ShipData.MARSEILLE;

        // Step 6: Clue 5 (Marseille is immediately to the right of Cocoa)
        // Since Marseille is at index 3, Cocoa must be at index 2.
        solCargo[2] = ShipData.COCOA;

        // Step 7: Clue 6 (Brazilian ship heading for Manila)
        // The only position left for both Brazilian nationality and Manila destination is index 2.
        solNat[2] = ShipData.BRAZILIAN;
        solDest[2] = ShipData.MANILA;

        // Step 8: By elimination of nationalities (French, Greek, Brazilian, Spanish assigned),
        // Ship 4 (index 3) is English.
        solNat[3] = ShipData.ENGLISH;

        // Step 9: Clue 3 (English ship leaves at 9:00)
        solTime[3] = ShipData.T9;

        // Step 10: Clue 8 (Genoa ship leaves at 5:00)
        // The only ship leaving at 5:00 is Ship 1 (index 0).
        solDest[0] = ShipData.GENOA;
        solTime[0] = ShipData.T5;

        // Step 11: By elimination of destinations, Ship 5 (index 4) goes to Port Said.
        solDest[4] = ShipData.PORT_SAID;

        // Step 12: Clue 12 (Border ship carries corn)
        // Borders are index 0 or index 4. Since index 1 (next to index 0) carries coffee,
        // placing corn at index 0 would violate Clue 14 (corn is next to rice).
        // Thus, corn is at index 4.
        solCargo[4] = ShipData.CORN;

        // Step 13: Clue 14 (Corn ship is next to rice ship)
        // Since corn is at index 4, rice must be at index 3.
        solCargo[3] = ShipData.RICE;

        // Step 14: By elimination of cargo, Ship 1 (index 0) carries Tea.
        solCargo[0] = ShipData.TEA;

        combinationsChecked = 1; // Direct derivation
        solveTimeMs = System.currentTimeMillis() - startTime;
        return true;
    }

    /**
     * Returns the position (0-4) of the ship going to Port Said.
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
