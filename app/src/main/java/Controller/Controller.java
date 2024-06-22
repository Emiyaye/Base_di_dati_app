package Controller;

import java.util.Objects;

import Model.Model;
import View.AdminPanel;
import View.Op1Panel;
import View.Op2Panel;
import View.Op3Panel;
import View.Op4Panel;
import View.Op5Panel;
import View.Op6Panel;
import View.Op7Panel;
import View.Op16Panel;
import View.Op17Panel;
import View.Op18Panel;
import View.UserPanel;
import data.Dati;

public class Controller {

    private final Model model;
    private final UserPanel userPanel;
    private final AdminPanel adminPanel;
    private final Op1Panel Op1Panel = new Op1Panel();
    private final Op2Panel Op2Panel = new Op2Panel();
    private final Op3Panel Op3Panel = new Op3Panel();
    private final Op4Panel Op4Panel = new Op4Panel();
    private final Op5Panel Op5Panel = new Op5Panel();
    private final Op6Panel Op6Panel = new Op6Panel();
    private final Op7Panel Op7Panel = new Op7Panel();
    private final Op16Panel Op16Panel = new Op16Panel();
    private final Op17Panel Op17Panel = new Op17Panel();
    private final Op18Panel Op18Panel = new Op18Panel();
    

    public Controller(final Model model, final UserPanel userPanel, final AdminPanel adminPanel) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(userPanel);
        this.model = model;
        this.userPanel = userPanel;
        this.adminPanel = adminPanel;

        this.userPanel.addOp1Listener(e -> userPanel.updateCenterPanel(Op1Panel));
        this.Op1Panel.addSubmitListener(e -> {
            final Dati.Op1Data data = this.Op1Panel.getData();
            this.model.Op1_addAccount(data.nickname(), data.email(), data.password(),
                    data.dataNascita(), data.genere(), data.nazione(),
                    data.tipoPagamento(), data.numeroCarta(), data.scadenzaCarta(),
                    data.tipoAbbonamento());

        });
        this.userPanel.addOp2Listener(e -> userPanel.updateCenterPanel(Op2Panel));
        this.Op2Panel.addSubmitListener(e -> {
            final Dati.Op2Data data = this.Op2Panel.getData();
            this.model.Op2_inviteAbbonamento(data.accountInvitato(), data.accountInvitante());
        });

        this.userPanel.addOp3Listener(e -> userPanel.updateCenterPanel(Op3Panel));
        this.Op3Panel.addSubmitListener(e -> {
            final Dati.Op3Data data = this.Op3Panel.getData();
            this.model.Op3_followArtist(data.accountSeguito(), data.accountSeguente());
        });

        this.userPanel.addOp4Listener(e -> userPanel.updateCenterPanel(Op4Panel));
        this.Op4Panel.addSubmitListener(e -> {
            final Dati.Op4Data data = this.Op4Panel.getData();
            this.model.Op4_createPlaylist(data.nome(), data.descrizione(), data.immagine(), data.privato(),
                    data.account(), data.collaboratore());
        });

        this.userPanel.addOp5Listener(e -> userPanel.updateCenterPanel(Op5Panel));
        this.Op5Panel.addSubmitListener(e -> {
            final Dati.Op5Data data = this.Op5Panel.getData();
            this.model.Op5_DeleteSong(data.codPlaylist(), data.numero());
        });

        this.userPanel.addOp6Listener(e -> userPanel.updateCenterPanel(Op6Panel));
        this.Op6Panel.addSearchListener(e -> {
            final String data = this.Op6Panel.getData();
            this.Op6Panel.updateTable(this.model.Op6_viewUserLibrary(data));
        });

        this.userPanel.addOp7Listener(e -> userPanel.updateCenterPanel(Op7Panel));
        this.Op7Panel.addSearchListener(e -> {
            final String data = this.Op7Panel.getData();
            this.Op7Panel.updateTable(this.model.Op7_searchSong(data));
        });

        this.adminPanel.addOp16Listener(e -> {
            this.adminPanel.updateCenterPanel(Op16Panel);
            this.Op16Panel.updateTable(this.model.Op16_viewActiveAbbonamento());
        });

        this.adminPanel.addOp17Listener(e -> {
            this.adminPanel.updateCenterPanel(Op17Panel);
            this.Op17Panel.updateTable(this.model.Op17_mostPopularArtist());
        });

        this.adminPanel.addOp18Listener(e -> adminPanel.updateCenterPanel(Op18Panel));
        this.Op18Panel.addSearchListener(e -> {
            final int data = Integer.parseInt(this.Op18Panel.getData());
            this.Op18Panel.updateTable(this.model.Op18_serviceTurnover(data));
        });
    }
}
