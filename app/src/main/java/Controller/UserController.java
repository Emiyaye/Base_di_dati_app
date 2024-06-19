package Controller;

import java.util.Objects;

import Model.UserModel;
import View.Op1Panel;
import View.UserPanel;
import data.Op1Data;

public class UserController {
    
    private final UserModel model;
    private final UserPanel panel;
    private final Op1Panel Op1Panel = new Op1Panel();

    public UserController (final UserModel model, final UserPanel panel){
        Objects.requireNonNull(model);
        Objects.requireNonNull(panel);
        this.model = model;
        this.panel = panel;

        this.panel.addOp1Listener(e -> {
            panel.UpdateEastPanel(Op1Panel);
        });
        this.Op1Panel.addInsertListener(e ->{
            Op1Data data = this.Op1Panel.getOp1Data();
            this.model.OP1_addAccount(data.getNickname(), data.getEmail(), data.getPassword(),
                        data.getDataNascita(), data.getGenere(), data.getNazione(),
                        data.getTipoPagamento(), data.getNumeroCarta(), data.getScadenzaCarta(),
                        data.getTipoAbbonamento());
            
        });
    }
}
