public class ShipData {

    public static final String[] NAT = {
        "Greek", "English", "French", "Spanish", "Brazilian"
    };

    public static final String[] COLOR = {
        "Red", "Green", "Blue", "Black", "White"
    };

    public static final String[] DEST = {
        "Hamburg", "Marseille", "Genoa", "Port Said", "Manila"
    };

    public static final String[] CARGO = {
        "Coffee", "Tea", "Cocoa", "Rice", "Corn"
    };

    public static final String[] TIME = {
        "5:00", "6:00", "7:00", "8:00", "9:00"
    };

    public static final int GREEK      = 0;
    public static final int ENGLISH    = 1;
    public static final int FRENCH     = 2;
    public static final int SPANISH    = 3;
    public static final int BRAZILIAN  = 4;

    public static final int RED   = 0;
    public static final int GREEN = 1;
    public static final int BLUE  = 2;
    public static final int BLACK = 3;
    public static final int WHITE = 4;

    public static final int HAMBURG   = 0;
    public static final int MARSEILLE = 1;
    public static final int GENOA     = 2;
    public static final int PORT_SAID = 3;
    public static final int MANILA    = 4;

    public static final int COFFEE = 0;
    public static final int TEA    = 1;
    public static final int COCOA  = 2;
    public static final int RICE   = 3;
    public static final int CORN   = 4;

    public static final int T5 = 0;
    public static final int T6 = 1;
    public static final int T7 = 2;
    public static final int T8 = 3;
    public static final int T9 = 4;

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
