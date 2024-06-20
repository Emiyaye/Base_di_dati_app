package View;

import data.Dati;

public class Op3Panel extends AbstractInsertPanel<Dati.Op3Data> {

    private static final String FIELD1 = "Account Seguito";
    private static final String FIELD2 = "Account Seguente";

    public Op3Panel() {
        super(new String[] { FIELD1, FIELD2 }, "Follow", "Cancel");

    }

    @Override
    public Dati.Op3Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op3Data(data.get(FIELD1).getText(), data.get(FIELD2).getText());
    }
}
