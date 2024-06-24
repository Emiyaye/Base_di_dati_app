package View;

import data.Dati;

public class Op10Panel extends AbstractInsertPanel<Dati.Op10Data> {

    private static final String FIELD1 = "Brano";
    private static final String FIELD2 = "Account";
    private static final String FIELD3 = "msRiprodotti";

    public Op10Panel () {
        super(new String[] {FIELD1, FIELD2, FIELD3}, "Reproduce", "Cancel");
    }

    public Dati.Op10Data getData() {
        final var data = getTextFieldMap();
        return new Dati.Op10Data(Integer.parseInt(data.get(FIELD1).getText()),
                data.get(FIELD2).getText(),
                Integer.parseInt(data.get(FIELD3).getText()));
    }

}
