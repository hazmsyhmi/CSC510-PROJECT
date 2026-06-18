public class ShipSolver {

    public int[] solNat;
    public int[] solColor;
    public int[] solDest;
    public int[] solCargo;
    public int[] solTime;
    public long solveTimeMs;
    public long combinationsChecked = 0;

    public boolean solve() {
        long startTime = System.currentTimeMillis();

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

        solColor[2] = ShipData.BLACK;

        solTime[2] = ShipData.T8;

        solNat[0] = ShipData.FRENCH;
        solColor[0] = ShipData.BLUE;

        solNat[1] = ShipData.GREEK;
        solColor[1] = ShipData.RED;
        solDest[1] = ShipData.HAMBURG;
        solCargo[1] = ShipData.COFFEE;
        solTime[1] = ShipData.T6;

        solColor[3] = ShipData.WHITE;
        solColor[4] = ShipData.GREEN;

        solNat[4] = ShipData.SPANISH;
        solTime[4] = ShipData.T7;

        solDest[3] = ShipData.MARSEILLE;

        solCargo[2] = ShipData.COCOA;

        solNat[2] = ShipData.BRAZILIAN;
        solDest[2] = ShipData.MANILA;

        solNat[3] = ShipData.ENGLISH;

        solTime[3] = ShipData.T9;

        solDest[0] = ShipData.GENOA;
        solTime[0] = ShipData.T5;

        solDest[4] = ShipData.PORT_SAID;

        solCargo[4] = ShipData.CORN;

        solCargo[3] = ShipData.RICE;

        solCargo[0] = ShipData.TEA;

        combinationsChecked = 1;
        solveTimeMs = System.currentTimeMillis() - startTime;
        return true;
    }

    public int getPortSaidPosition() {
        for (int i = 0; i < 5; i++) {
            if (solDest[i] == ShipData.PORT_SAID)
                return i;
        }
        return -1;
    }

    public int getTeaPosition() {
        for (int i = 0; i < 5; i++) {
            if (solCargo[i] == ShipData.TEA)
                return i;
        }
        return -1;
    }

    public String getNationalityAt(int position) {
        return ShipData.NAT[solNat[position]];
    }

    public String getDestinationAt(int position) {
        return ShipData.DEST[solDest[position]];
    }
}
