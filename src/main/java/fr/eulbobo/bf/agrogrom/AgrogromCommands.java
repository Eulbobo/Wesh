package fr.eulbobo.bf.agrogrom;

import fr.eulbobo.bf.support.BF;

public enum AgrogromCommands{

    GROM(BF.P),
    GROGROM(BF.M),
    AGROM(BF.G),
    HHARR(BF.L),
    BAAA(BF.O),
    WAARR(BF.C),
    GROOO(BF.PT),
    RAAAA(BF.CM);

    private BF bf;

    AgrogromCommands(final BF bf){
        this.bf = bf;
    }

    public static BF bfFromValue(final String value) {
        BF b = null;
        try {
            b = AgrogromCommands.valueOf(value).bf;
        } catch (IllegalArgumentException e){
            // meh.
        }
        return b;
    }

}
