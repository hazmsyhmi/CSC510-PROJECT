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
 *
 *  WHO USES THIS FILE?
 *  - ShipSolver.java uses the INDEX CONSTANTS (GREEK, RED, HAMBURG, etc.)
 *    inside its solve() method to write readable constraint checks.
 *  - Printer.java uses the STRING ARRAYS (NAT[], COLOR[], DEST[], etc.)
 *    to convert solution indices back into readable names for display.
 *  - Printer.java also uses CLUES[] and DEDUCTION_STEPS[][] for the
 *    clues display and step-by-step walkthrough.
 *  - QuizMode.java uses both INDEX CONSTANTS (to find correct answers)
 *    and STRING ARRAYS (to display answer names).
 * ============================================================
 */

// -----------------------------------------------------------------------
// CLASS: ShipData
// This class has NO methods and NO constructor — it is purely a container
// for constant data. Think of it as a "data dictionary" that other classes
// reference. Every field is "public static final", meaning:
//   public  = accessible from any other class
//   static  = belongs to the class, not an instance (use ShipData.GREEK)
//   final   = cannot be changed after initialization (constant)
// -----------------------------------------------------------------------
public class ShipData {

    // ----------------------------------------------------------
    //  ATTRIBUTE LABELS (String arrays)
    //  These String arrays let us convert index numbers back
    //  into readable names for display purposes.
    //  Example: NAT[0] = "Greek", COLOR[3] = "Black"
    //
    //  USED BY: Printer.java (printSolutionTable, printAnswers)
    //           and QuizMode.java (to display correct answer names)
    // ----------------------------------------------------------

    /** The 5 nationalities of the ships */
    // Index 0="Greek", 1="English", 2="French", 3="Spanish", 4="Brazilian"
    // These indices match the INDEX CONSTANTS below (GREEK=0, ENGLISH=1, etc.)
    // Used in Printer.java to convert solver.solNat[i] -> readable name
    // Used in QuizMode.java to show correct nationality in feedback
    public static final String[] NAT = {
        "Greek", "English", "French", "Spanish", "Brazilian"
    };

    /** The 5 chimney colors */
    // Index 0="Red", 1="Green", 2="Blue", 3="Black", 4="White"
    // Used in Printer.java to convert solver.solColor[i] -> readable name
    // Used in QuizMode.java to show correct color in quiz feedback
    public static final String[] COLOR = {
        "Red", "Green", "Blue", "Black", "White"
    };

    /** The 5 destination ports */
    // Index 0="Hamburg", 1="Marseille", 2="Genoa", 3="Port Said", 4="Manila"
    // Used in Printer.java to convert solver.solDest[i] -> readable name
    public static final String[] DEST = {
        "Hamburg", "Marseille", "Genoa", "Port Said", "Manila"
    };

    /** The 5 types of cargo */
    // Index 0="Coffee", 1="Tea", 2="Cocoa", 3="Rice", 4="Corn"
    // Used in Printer.java to convert solver.solCargo[i] -> readable name
    // Used in QuizMode.java to show correct cargo in quiz feedback
    public static final String[] CARGO = {
        "Coffee", "Tea", "Cocoa", "Rice", "Corn"
    };

    /** The 5 departure times */
    // Index 0="5:00", 1="6:00", 2="7:00", 3="8:00", 4="9:00"
    // Used in Printer.java to convert solver.solTime[i] -> readable name
    // Used in QuizMode.java to show correct time in quiz feedback
    public static final String[] TIME = {
        "5:00", "6:00", "7:00", "8:00", "9:00"
    };

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- NATIONALITY
    //  These map each nationality to its index in the NAT array.
    //  Using named constants avoids "magic numbers" in the code.
    //
    //  USED BY: ShipSolver.java (constraint checks in solve())
    //           QuizMode.java (finding positions of specific nationalities)
    //
    //  Example usage in ShipSolver.java:
    //    int greekPos = indexOf(nat, ShipData.GREEK);
    //    // Instead of the hard-to-read:
    //    int greekPos = indexOf(nat, 0);
    // ----------------------------------------------------------

    // GREEK = 0 means "Greek" is at index 0 in the NAT[] array
    public static final int GREEK      = 0;

    // ENGLISH = 1 means "English" is at index 1 in the NAT[] array
    public static final int ENGLISH    = 1;

    // FRENCH = 2 means "French" is at index 2 in the NAT[] array
    public static final int FRENCH     = 2;

    // SPANISH = 3 means "Spanish" is at index 3 in the NAT[] array
    public static final int SPANISH    = 3;

    // BRAZILIAN = 4 means "Brazilian" is at index 4 in the NAT[] array
    public static final int BRAZILIAN  = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- CHIMNEY COLOR
    //  Maps each chimney color to its index in the COLOR[] array.
    //
    //  USED BY: ShipSolver.java (constraint checks for color-related clues)
    //  Example: if (color[frenchPos] != ShipData.BLUE) continue;
    // ----------------------------------------------------------

    // RED = 0 maps to COLOR[0] = "Red"
    public static final int RED   = 0;

    // GREEN = 1 maps to COLOR[1] = "Green"
    public static final int GREEN = 1;

    // BLUE = 2 maps to COLOR[2] = "Blue"
    public static final int BLUE  = 2;

    // BLACK = 3 maps to COLOR[3] = "Black"
    public static final int BLACK = 3;

    // WHITE = 4 maps to COLOR[4] = "White"
    public static final int WHITE = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- DESTINATION
    //  Maps each destination to its index in the DEST[] array.
    //
    //  USED BY: ShipSolver.java (constraint checks for destination clues)
    //           Printer.java (checking if a row is the Port Said answer)
    //           QuizMode.java (finding which position has a specific destination)
    // ----------------------------------------------------------

    // HAMBURG = 0 maps to DEST[0] = "Hamburg"
    public static final int HAMBURG   = 0;

    // MARSEILLE = 1 maps to DEST[1] = "Marseille"
    public static final int MARSEILLE = 1;

    // GENOA = 2 maps to DEST[2] = "Genoa"
    public static final int GENOA     = 2;

    // PORT_SAID = 3 maps to DEST[3] = "Port Said" (KEY PUZZLE ANSWER)
    // Used by ShipSolver.getPortSaidPosition() and Printer.printSolutionTable()
    public static final int PORT_SAID = 3;

    // MANILA = 4 maps to DEST[4] = "Manila"
    public static final int MANILA    = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- CARGO
    //  Maps each cargo type to its index in the CARGO[] array.
    //
    //  USED BY: ShipSolver.java (constraint checks for cargo clues)
    //           Printer.java (checking if a row is the Tea answer)
    //           QuizMode.java (finding correct cargo for quiz questions)
    // ----------------------------------------------------------

    // COFFEE = 0 maps to CARGO[0] = "Coffee"
    public static final int COFFEE = 0;

    // TEA = 1 maps to CARGO[1] = "Tea" (KEY PUZZLE ANSWER)
    // Used by ShipSolver.getTeaPosition() and Printer.printSolutionTable()
    public static final int TEA    = 1;

    // COCOA = 2 maps to CARGO[2] = "Cocoa"
    public static final int COCOA  = 2;

    // RICE = 3 maps to CARGO[3] = "Rice"
    public static final int RICE   = 3;

    // CORN = 4 maps to CARGO[4] = "Corn"
    public static final int CORN   = 4;

    // ----------------------------------------------------------
    //  INDEX CONSTANTS -- DEPARTURE TIME
    //  T5 = 5:00am, T6 = 6:00am, ..., T9 = 9:00am
    //  Maps each departure time to its index in the TIME[] array.
    //
    //  USED BY: ShipSolver.java (constraint checks for time-related clues)
    //  Example: if (time[greekPos] != ShipData.T6) continue;
    //           This checks "does the Greek ship leave at 6:00?"
    // ----------------------------------------------------------

    // T5 = 0 maps to TIME[0] = "5:00"
    public static final int T5 = 0;

    // T6 = 1 maps to TIME[1] = "6:00"
    public static final int T6 = 1;

    // T7 = 2 maps to TIME[2] = "7:00"
    public static final int T7 = 2;

    // T8 = 3 maps to TIME[3] = "8:00"
    public static final int T8 = 3;

    // T9 = 4 maps to TIME[4] = "9:00"
    public static final int T9 = 4;

    // ----------------------------------------------------------
    //  PUZZLE CLUES (for display only)
    //  Stored as readable strings for display in the menu.
    //  The actual logic of each clue is implemented in
    //  ShipSolver.java as boolean conditions.
    //
    //  USED BY: Printer.java -> printClues() method
    //           (loops through this array to display all clues)
    //
    //  NOTE: These strings are for DISPLAY ONLY. The solver in
    //  ShipSolver.java implements each clue as code logic (if-checks).
    //  If you change a clue here, you must also update the solver.
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
    //  DEDUCTION STEPS (for step-by-step walkthrough)
    //  Human-readable explanation of how the solver logically
    //  deduces each step. Used in the step-by-step mode.
    //  Format: { "Clue reference", "What we can deduce" }
    //
    //  USED BY: Printer.java -> printStepByStep() method
    //           (loops through this 2D array to show each step)
    //
    //  Each element is a String[2]:
    //    [0] = which clue(s) this step references (e.g., "Clues 1 + 15")
    //    [1] = the logical deduction explanation
    // ----------------------------------------------------------
    public static final String[][] DEDUCTION_STEPS = {
        // Step 1: Combine Clue 1 (Greek leaves at 6, carries coffee)
        //         with Clue 15 (Hamburg ship leaves at 6)
        //         -> Greek ship goes to Hamburg
        {"Clues 1 + 15",
         "Both state something leaves at 6:00. Clue 1 says Greek leaves at 6.\n" +
         "         Clue 15 says Hamburg ship leaves at 6. Therefore: Greek -> Hamburg."},

        // Step 2: Clue 10 (red chimney -> Hamburg)
        //         Since Greek -> Hamburg (from step 1), Greek has red chimney
        {"Clue 10",
         "The red chimney ship goes to Hamburg.\n" +
         "         Since Greek goes to Hamburg, Greek ship has the red chimney."},

        // Step 3: Clue 2 (middle ship = black chimney)
        //         Position 3 (index 2) = Black chimney
        {"Clue 2",
         "The middle ship (position 3 of 5) has a black chimney.\n" +
         "         So position 3 = Black chimney."},

        // Step 4: Clue 13 (black chimney -> 8:00)
        //         Combined with step 3: position 3 departs at 8:00
        {"Clue 13",
         "The black chimney ship leaves at 8:00.\n" +
         "         Combined with Clue 2: position 3 departs at 8:00."},

        // Step 5: Clue 4 (French = blue chimney, left of coffee)
        //         Coffee = Greek (from clue 1), so French is left of Greek
        {"Clue 4",
         "The French ship (blue chimney) is LEFT of the coffee ship.\n" +
         "         Clue 1 says Greek carries coffee, so French is left of Greek."},

        // Step 6: Clue 3 (English -> 9:00)
        {"Clue 3",
         "The English ship leaves at 9:00.\n" +
         "         This is the latest departure time."},

        // Step 7: Clue 8 (Genoa -> 5:00)
        {"Clue 8",
         "The Genoa-bound ship leaves at 5:00.\n" +
         "         This is the earliest departure time."},

        // Step 8: Clue 9 (Spanish -> 7:00, right of Marseille)
        {"Clue 9",
         "The Spanish ship leaves at 7:00 and is immediately RIGHT of Marseille.\n" +
         "         So Marseille is at position (Spanish - 1)."},

        // Step 9: Clue 11 (next to 7:00 ship = white chimney)
        //         7:00 = Spanish (from clue 9), so white is adjacent to Spanish
        {"Clue 11",
         "The ship next to the 7:00 ship has a white chimney.\n" +
         "         The 7:00 ship is Spanish (Clue 9), so white chimney is adjacent to Spanish."},

        // Step 10: Clue 6 (Brazilian -> Manila)
        {"Clue 6",
         "The Brazilian ship is heading to Manila.\n" +
         "         So Brazilian position = Manila position."},

        // Step 11: Clue 5 (Marseille is right of cocoa)
        {"Clue 5",
         "The Marseille ship is immediately RIGHT of the cocoa ship.\n" +
         "         So: cocoa position + 1 = Marseille position."},

        // Step 12: Clue 12 (border ship = corn)
        //         Border = position 1 or 5 (index 0 or 4)
        {"Clue 12",
         "The border ship (position 1 or 5) carries corn.\n" +
         "         Corn must be at index 0 or 4."},

        // Step 13: Clues 14 + 7 (corn next to rice, rice next to green)
        {"Clues 14 + 7",
         "Corn is next to rice (Clue 14). Rice is next to green chimney (Clue 7).\n" +
         "         These chain together to anchor positions."},

        // Step 14: Final elimination assigns remaining values
        {"Elimination",
         "After all constraints are applied, the remaining Port Said\n" +
         "         destination and Tea cargo are assigned by elimination."}
    };
}
