package fr.eulbobo.bf.wesh;

import fr.eulbobo.bf.support.BF;

public enum WeshCommands {

    WESH(BF.P),
    YO(BF.M),
    HEY(BF.G),
    GROS(BF.L),
    ZIVA(BF.O),
    WOULAH(BF.C),
    COUSIN(BF.PT),
    POTO(BF.CM);

    private BF bf;

    WeshCommands(final BF bf){
        this.bf = bf;
    }

    public static BF bfFromValue(final String value) {
        BF b = null;
        try {
            b = WeshCommands.valueOf(value).bf;
        } catch (IllegalArgumentException e){
            // meh.
        }
        return b;
    }

}
