package View;

import data.Dati;

public class Op5Panel extends AbstractInsertPanel<Dati.Op5Data> {

    private static final String FIELD1 = "Codice Playlist";
    private static final String FIELD2 = "numero";

    public Op5Panel() {
        super(new String[] { FIELD1, FIELD2 }, "Confirm", "Cancel");

    }

    @Override
    public Dati.Op5Data data() {
        final var data = getTextFieldMap();
        return new Dati.Op5Data(Integer.parseInt(data.get(FIELD1).getText()),
                Integer.parseInt(data.get(FIELD2).getText()));
    }
}
