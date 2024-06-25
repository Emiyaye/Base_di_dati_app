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
import View.Op8Panel;
import View.Op9Panel;
import View.Op10Panel;
import View.Op12Panel;
import View.Op13Panel;
<<<<<<< HEAD
import View.Op14Panel;
=======
import View.Op15Panel;
>>>>>>> 2da4b70b0344be7ebfdefba1278a4bf6a0f78ae8
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
    private final Op8Panel Op8Panel = new Op8Panel();
    private final Op9Panel Op9Panel = new Op9Panel();
    private final Op10Panel Op10Panel = new Op10Panel();
    private final Op12Panel Op12Panel = new Op12Panel();
    private final Op13Panel Op13Panel = new Op13Panel();
<<<<<<< HEAD
    private final Op14Panel Op14Panel = new Op14Panel();
=======
    private final Op15Panel Op15Panel = new Op15Panel();
>>>>>>> 2da4b70b0344be7ebfdefba1278a4bf6a0f78ae8
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

        this.userPanel.addOp8Listener(e -> userPanel.updateCenterPanel(Op8Panel));
        this.Op8Panel.addSearchListener(e -> {
            final int data = Integer.parseInt(this.Op8Panel.getData());
            this.Op8Panel.updateTable(this.model.Op8_viewAlbum(data));
        });

        this.userPanel.addOp9Listener(e -> userPanel.updateCenterPanel(Op9Panel));
        this.Op9Panel.addSearchListener(e -> {
            final String data = this.Op9Panel.getData();
            this.Op9Panel.updateTable(this.model.Op9_subHistory(data));
        });

        this.userPanel.addOp10Listener(e -> userPanel.updateCenterPanel(Op10Panel));
        this.Op10Panel.addSubmitListener(e -> {
            final Dati.Op10Data data = this.Op10Panel.getData();
            this.model.OP10_ReproduceTrack(data.codBrano(), data.email(), data.msRiprodotti());
        });

        this.adminPanel.addOp12Listener(e -> adminPanel.updateCenterPanel(Op12Panel));
        this.Op12Panel.addSubmitListener(e -> {
            final Dati.Op12Data data = this.Op12Panel.getData();
            this.model.OP12_DisableEnableAccount(data.disable(), data.account());
        });

        this.adminPanel.addOp13Listener(e -> adminPanel.updateCenterPanel(Op13Panel));
        this.Op13Panel.addSubmitListener(e -> {
            final Dati.Op13Data data = this.Op13Panel.getData();
            this.model.OP13_TrackAnalysis(data.codBrano(), data.linkImage());
        });

<<<<<<< HEAD
        this.adminPanel.addOp14Listener(e -> adminPanel.updateCenterPanel(Op14Panel));
        this.Op14Panel.addSearchListener(e -> {
            final String data = this.Op14Panel.getData();
            this.Op14Panel.updateTable(this.model.OP14_Top50(data));
=======
        this.adminPanel.addOp15Listener(e -> adminPanel.updateCenterPanel(Op15Panel));
        this.Op15Panel.addSearchListener(e -> {
            final String data = this.Op15Panel.getData();
            this.Op15Panel.updateTable(this.model.Op15_artistSongs(data));
>>>>>>> 2da4b70b0344be7ebfdefba1278a4bf6a0f78ae8
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
