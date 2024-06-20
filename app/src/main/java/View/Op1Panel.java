package View;

import java.time.LocalDate;
import java.util.Optional;

import data.Dati;
import data.Dati.Op1Data;

public class Op1Panel extends AbstractInsertPanel<Dati.Op1Data> {
    private static final String FIELD1 = "Nickname";
    private static final String FIELD2 = "Email";
    private static final String FIELD3 = "Password";
    private static final String FIELD4 = "DataNascita (YYYY-MM-DD)";
    private static final String FIELD5 = "Genere (M / F)";
    private static final String FIELD6 = "Nazione (Ex: IT)";
    private static final String FIELD7 = "TipoPagamento (1 - 3)";
    private static final String FIELD8 = "NumeroCarta (Optional)";
    private static final String FIELD9 = "ScadenzaCarta (Optional)";
    private static final String FIELD10 = "TipoAbbonamento (1 - 6)";

    public Op1Panel() {
        super(new String[] { FIELD1, FIELD2, FIELD3, FIELD4, FIELD5, FIELD6, FIELD7, FIELD8, FIELD9, FIELD10 },
                "Insert", "Cancel");
    }

    @Override
    public Op1Data getData() {
        final var textFieldMap = getTextFieldMap();
        return new Dati.Op1Data(
                textFieldMap.get(FIELD1).getText(),
                textFieldMap.get(FIELD2).getText(),
                textFieldMap.get(FIELD3).getText(),
                LocalDate.parse(textFieldMap.get(FIELD4).getText()),
                textFieldMap.get(FIELD5).getText(),
                textFieldMap.get(FIELD6).getText(),
                Integer.parseInt(textFieldMap.get(FIELD7).getText()),
                textFieldMap.get(FIELD8).getText().isEmpty() ? Optional.empty()
                        : Optional.of(Integer.parseInt(textFieldMap.get(FIELD8).getText())),
                textFieldMap.get(FIELD9).getText().isEmpty() ? Optional.empty()
                        : Optional.of(Integer.parseInt(textFieldMap.get(FIELD9).getText())),
                Integer.parseInt(textFieldMap.get(FIELD10).getText()));
    }

}
