package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

    private final JButton back;
    private final JButton OP1_CreateAccount;
    private final JButton OP2_InviteAccount;
    private final JButton OP3_FollowArtist;
    private final JButton OP4_CreatePlaylist;
    private final JButton OP5_DeleteSong;
    private final JButton OP6_ViewUserLibrary;
    private final JButton OP7_SearchSongs;
    private final JButton OP8_ViewAlbum;
    private final JButton OP9_SubHistory;
    private final JButton OP10_ReproduceTrack;
    private final JButton OP11_AddTextSong;
    private JPanel LastPanel;

    public UserPanel() {
        back = new JButton("BACK");
        LastPanel = new JPanel();
        setLayout(new BorderLayout());
        final JPanel westPanel = new JPanel(new BorderLayout());
        final JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(back);
        westPanel.add(backButtonPanel, BorderLayout.SOUTH);
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        OP1_CreateAccount = new JButton("OP1 Create Account");
        buttonPanel.add(OP1_CreateAccount);

        OP2_InviteAccount = new JButton("OP2 Invite Account");
        buttonPanel.add(OP2_InviteAccount);

        OP3_FollowArtist = new JButton("OP3 Follow Artist");
        buttonPanel.add(OP3_FollowArtist);

        OP4_CreatePlaylist = new JButton("Op4 Create Playlist and add a Collaborator");
        buttonPanel.add(OP4_CreatePlaylist);
        
        OP5_DeleteSong = new JButton("Op5 Delete Song");
        buttonPanel.add(OP5_DeleteSong);
        
        OP6_ViewUserLibrary = new JButton("Op6 View User Library");
        buttonPanel.add(OP6_ViewUserLibrary);

        OP7_SearchSongs = new JButton("OP7 Search Songs");
        buttonPanel.add(OP7_SearchSongs);

        OP8_ViewAlbum = new JButton("OP8_ViewAlbum");
        buttonPanel.add(OP8_ViewAlbum);

        OP9_SubHistory = new JButton("OP9 Sub History");
        buttonPanel.add(OP9_SubHistory);

        OP10_ReproduceTrack = new JButton("OP10 Reproduce Track");
        buttonPanel.add(OP10_ReproduceTrack);

        OP11_AddTextSong = new JButton("OP11 Add Text to a song");
        buttonPanel.add(OP11_AddTextSong);

        westPanel.add(buttonPanel, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);
        add(LastPanel, BorderLayout.CENTER);
    }

    public void addBackListener(final ActionListener ac) {
        back.addActionListener(ac);
    }

    public void addOp1Listener(final ActionListener ac) {
        OP1_CreateAccount.addActionListener(ac);
    }

    public void addOp2Listener(final ActionListener ac) {
        OP2_InviteAccount.addActionListener(ac);
    }

    public void addOp3Listener(final ActionListener ac) {
        OP3_FollowArtist.addActionListener(ac);
    }

    public void addOp4Listener(final ActionListener ac) {
        OP4_CreatePlaylist.addActionListener(ac);
    }

    public void addOp5Listener(final ActionListener ac) {
        OP5_DeleteSong.addActionListener(ac);
    }

    public void addOp6Listener(final ActionListener ac) {
        OP6_ViewUserLibrary.addActionListener(ac);
    }

    public void addOp7Listener(final ActionListener ac) {
        OP7_SearchSongs.addActionListener(ac);
    }

    public void addOp8Listener(final ActionListener ac) {
        OP8_ViewAlbum.addActionListener(ac);
    }

    public void addOp9Listener(final ActionListener ac){
        OP9_SubHistory.addActionListener(ac);
    }

    public void addOp10Listener(final ActionListener ac){
        OP10_ReproduceTrack.addActionListener(ac);
    }

    public void addOp11Listener(final ActionListener ac){
        OP11_AddTextSong.addActionListener(ac);
    }

    public void updateCenterPanel(final JPanel panel) {
            remove(LastPanel);
            LastPanel = panel;
            add(LastPanel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }

}
