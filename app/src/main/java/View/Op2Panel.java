package View;

import data.Dati;

public class Op2Panel extends AbstractInsertPanel<Dati.Op2Data> {

    private static final String FIELD1 = "Il tuo Account";
    private static final String FIELD2 = "Account Invitato";

    public Op2Panel() {
        super(new String[] { FIELD1, FIELD2 }, "Confirm", "Cancel");

    }

    @Override
    public Dati.Op2Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op2Data(data.get(FIELD1).getText(), data.get(FIELD2).getText());
    }
}
