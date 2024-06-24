package View;

import java.util.List;

import data.Dati;

public class Op9Panel extends GeneralSelectPanel<Dati.Op9Data>{

    public Op9Panel() {
        super("Subscription history",
                List.of(new Table<>("Subscription history",
                        new String[]{"Tipo Abbonamento", "DurataMesi", "Data Pagamento", "Data Scadenza", "Account Pagante"},
                        Op9Panel::function)));
    }

    private static Object[] function(Dati.Op9Data data) {
        return new Object[]{
                data.tipoAbbonamento(),
                data.durataMesi(),
                data.dataPagamento(),
                data.dataScadenza(),
                data.accountPagante()
        };
    }

}
