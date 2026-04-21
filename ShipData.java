/**
 * ============================================================
 *  FILE: ShipData.java
 *  PURPOSE: Stores all constant data for Puzzle 3 - The Five Ships.
 *
 *  This file acts as a central "dictionary" for the program.
 *  Instead of using raw numbers like 0, 1, 2 throughout the code,
 *  we give them meaningful names (e.g., GREEK = 0, ENGLISH = 1).
 *  This makes the code far easier to read and debug.
 *
 *  DESIGN PATTERN USED: Constants Class
 *  All fields are static final -- meaning they belong to the class
 *  itself (not any object), and cannot be changed once set.
 * ============================================================
 */
public class ShipData {

    // ----------------------------------------------------------
    //  ATTRIBUTE LABELS
    //  These String arrays let us convert index numbers back
    //  into readable names for display purposes.
    //  Example: NAT[0] = "Greek", COLOR[3] = "Black"
    // ----------------------------------------------------------

    /** The 5 nationalities of the ships */
    public static final String[] NAT = {
        "Greek", "English", "French", "Spanish", "Brazilian"
    };

    /** The 5 chimney colors */
    public static final String[] COLOR = {
        "Red", "Green", "Blue", "Black", "White"
    };

    /** The 5 destination ports */
    public static final String[] DEST = {
        "Hamburg", "Marseille", "Genoa", "Port Said", "Manila"
    };

    /** The 5 types of cargo */
    public static final String[] CARGO = {
        "Coffee", "Tea", "Cocoa", "Rice", "Corn"
    };

    /** The 5 departure times */
    public static final String[] TIME = {
        "5:00", "6:00", "7:00", "8:00", "9:00"
    };

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- NATIONALITY
    //  These map each nationality to its index in the NAT array.
    //  Using named constants avoids "magic numbers" in the code.
    // ----------------------------------------------------------
    public static final int GREEK      = 0;
    public static final int ENGLISH    = 1;
    public static final int FRENCH     = 2;
    public static final int SPANISH    = 3;
    public static final int BRAZILIAN  = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- CHIMNEY COLOR
    // ----------------------------------------------------------
    public static final int RED   = 0;
    public static final int GREEN = 1;
    public static final int BLUE  = 2;
    public static final int BLACK = 3;
    public static final int WHITE = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- DESTINATION
    // ----------------------------------------------------------
    public static final int HAMBURG   = 0;
    public static final int MARSEILLE = 1;
    public static final int GENOA     = 2;
    public static final int PORT_SAID = 3;
    public static final int MANILA    = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- CARGO
    // ----------------------------------------------------------
    public static final int COFFEE = 0;
    public static final int TEA    = 1;
    public static final int COCOA  = 2;
    public static final int RICE   = 3;
    public static final int CORN   = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- DEPARTURE TIME
    //  T5 = 5:00am, T6 = 6:00am, ..., T9 = 9:00am
    // ----------------------------------------------------------
    public static final int T5 = 0;
    public static final int T6 = 1;
    public static final int T7 = 2;
    public static final int T8 = 3;
    public static final int T9 = 4;

    // ----------------------------------------------------------
    //  PUZZLE CLUES
    //  Stored as readable strings for display in the menu.
    //  The actual logic of each clue is implemented in
    //  ShipSolver.java as boolean conditions.
    // ----------------------------------------------------------
    public static final String[] CLUES = {
        " 1. The Greek ship leaves at six and carries coffee.",
        " 2. The ship in the middle has a black chimney.",
        " 3. The English ship leaves at nine.",
        " 4. The French ship with blue chimney is to the left of the coffee ship.",
        " 5. To the right of the cocoa ship is a ship going to Marseille.",
        " 6. The Brazilian ship is heading for Manila.",
        " 7. Next to the rice ship is a ship with a green chimney.",
        " 8. A ship going to Genoa leaves at five.",
        " 9. The Spanish ship leaves at seven and is to the right of the Marseille ship.",
        "10. The ship with a red chimney goes to Hamburg.",
        "11. Next to the ship leaving at seven is a ship with a white chimney.",
        "12. The ship on the border carries corn.",
        "13. The ship with a black chimney leaves at eight.",
        "14. The ship carrying corn is anchored next to the ship carrying rice.",
        "15. The ship to Hamburg leaves at six."
    };

    // ----------------------------------------------------------
    //  DEDUCTION STEPS
    //  Human-readable explanation of how the solver logically
    //  deduces each step. Used in the step-by-step mode.
    //  Format: { "Clue reference", "What we can deduce" }
    // ----------------------------------------------------------
    public static final String[][] DEDUCTION_STEPS = {
        {"Clues 1 + 15",
         "Both state something leaves at 6:00. Clue 1 says Greek leaves at 6.\n" +
         "         Clue 15 says Hamburg ship leaves at 6. Therefore: Greek -> Hamburg."},

        {"Clue 10",
         "The red chimney ship goes to Hamburg.\n" +
         "         Since Greek goes to Hamburg, Greek ship has the red chimney."},

        {"Clue 2",
         "The middle ship (position 3 of 5) has a black chimney.\n" +
         "         So position 3 = Black chimney."},

        {"Clue 13",
         "The black chimney ship leaves at 8:00.\n" +
         "         Combined with Clue 2: position 3 departs at 8:00."},

        {"Clue 4",
         "The French ship (blue chimney) is LEFT of the coffee ship.\n" +
         "         Clue 1 says Greek carries coffee, so French is left of Greek."},

        {"Clue 3",
         "The English ship leaves at 9:00.\n" +
         "         This is the latest departure time."},

        {"Clue 8",
         "The Genoa-bound ship leaves at 5:00.\n" +
         "         This is the earliest departure time."},

        {"Clue 9",
         "The Spanish ship leaves at 7:00 and is immediately RIGHT of Marseille.\n" +
         "         So Marseille is at position (Spanish - 1)."},

        {"Clue 11",
         "The ship next to the 7:00 ship has a white chimney.\n" +
         "         The 7:00 ship is Spanish (Clue 9), so white chimney is adjacent to Spanish."},

        {"Clue 6",
         "The Brazilian ship is heading to Manila.\n" +
         "         So Brazilian position = Manila position."},

        {"Clue 5",
         "The Marseille ship is immediately RIGHT of the cocoa ship.\n" +
         "         So: cocoa position + 1 = Marseille position."},

        {"Clue 12",
         "The border ship (position 1 or 5) carries corn.\n" +
         "         Corn must be at index 0 or 4."},

        {"Clues 14 + 7",
         "Corn is next to rice (Clue 14). Rice is next to green chimney (Clue 7).\n" +
         "         These chain together to anchor positions."},

        {"Elimination",
         "After all constraints are applied, the remaining Port Said\n" +
         "         destination and Tea cargo are assigned by elimination."}
    };
}
