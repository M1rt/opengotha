/*
 * JFrGamesRR.java
 */

package info.vannier.gotha;

import net.miginfocom.swing.MigLayout;
import ru.gofederation.gotha.model.Game;
import ru.gofederation.gotha.ui.FrameBase;
import ru.gofederation.gotha.util.GothaLocale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Admin
 */
public class JFrGamesRR extends javax.swing.JFrame{
    private static final long REFRESH_DELAY = 2000;
    private long lastComponentsUpdateTime = 0;

    public static final int NAME_COL = 0;
    public static final int RANK_COL = 1;
    public static final int NUMBER_COL = 2;
    public static final int FIRST_PLAYER_COL = 3;

    /** This number is related to layout issues . The RR Table should be visible without scrolling */
    static final int MAX_NUMBER_OF_RR_PLAYERS = 20;

    private TournamentInterface tournament;

    /** Creates new form JFrGamesRR */
    public JFrGamesRR(TournamentInterface tournament) throws RemoteException{
        this.tournament = tournament;

        initComponents();
        customInitComponents();
        setupRefreshTimer();
    }

    private final GothaLocale locale = GothaLocale.getCurrentLocale();

    private volatile boolean running = true;
    javax.swing.Timer timer = null;
    private void setupRefreshTimer() {
        ActionListener taskPerformer;
        taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!running){
                    timer.stop();
                }
                try {
                    if (tournament.getLastTournamentModificationTime() > lastComponentsUpdateTime) {
                        updateAllViews();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(JFrGamesResults.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer = new javax.swing.Timer((int) REFRESH_DELAY, taskPerformer);
        timer.start();
    }

    private void initComponents() {
        btnClose = new javax.swing.JButton();
        pnlGames = new javax.swing.JPanel();
        scpGames = new javax.swing.JScrollPane();
        tblGames = new javax.swing.JTable();
        lblBNumberOfRounds = new javax.swing.JLabel();
        txfNumberOfRounds = new javax.swing.JTextField();
        pnlUncompatibility = new javax.swing.JPanel();
        lblUncompatibility1 = new javax.swing.JLabel();
        lblUncompatibility3 = new javax.swing.JLabel();
        lblUncompatibility4 = new javax.swing.JLabel();
        pnlHelp = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Games .. Round-robin");
        setResizable(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new MigLayout("flowy, insets dialog", "[grow, fill]", "[grow, fill]unrel[]unrel[]"));

        pnlGames.setLayout(new MigLayout("insets 0"));

        scpGames.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scpGames.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tblGames.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Title 1", "Name", "Nr", " 1", " 2", " 3", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23"
            }
        ));
        tblGames.setCellSelectionEnabled(true);
        tblGames.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblGamesMousePressed(evt);
            }
        });
        tblGames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblGamesKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblGamesKeyTyped(evt);
            }
        });
        scpGames.setViewportView(tblGames);

        pnlGames.add(scpGames, "spanx 2, wrap");
        scpGames.setBounds(0, 0, 784, 339);

        lblBNumberOfRounds.setText(locale.getString("tournament.number_of_rounds"));
        pnlGames.add(lblBNumberOfRounds);

        txfNumberOfRounds.setText("0");
        txfNumberOfRounds.setEnabled(false);
        pnlGames.add(txfNumberOfRounds, "wmin 36lp, wrap");
        txfNumberOfRounds.setBounds(120, 340, 30, 20);

        getContentPane().add(pnlGames, "hidemode 3");
        pnlGames.setBounds(4, 5, 790, 360);

        pnlUncompatibility.setLayout(new MigLayout("flowy, insets 0", "push[al c]push", "push[]36lp[]24lp[]push"));

        lblUncompatibility1.setFont(FrameBase.scaleFont(lblUncompatibility1, 1.2f));
        lblUncompatibility1.setForeground(new java.awt.Color(255, 51, 51)); // TODO: theme
        lblUncompatibility1.setText(locale.getString("tournament.rr.incompatible_1"));
        pnlUncompatibility.add(lblUncompatibility1);

        lblUncompatibility3.setFont(FrameBase.scaleFont(lblUncompatibility3, 1.2f));
        lblUncompatibility3.setText(locale.getString("tournament.rr.incompatible_2"));
        pnlUncompatibility.add(lblUncompatibility3);

        lblUncompatibility4.setFont(FrameBase.scaleFont(lblUncompatibility4, 1.2f));
        lblUncompatibility4.setText(locale.getString("tournament.rr.incompatible_3"));
        pnlUncompatibility.add(lblUncompatibility4);

        getContentPane().add(pnlUncompatibility, "hidemode 3");

        pnlHelp.setBorder(javax.swing.BorderFactory.createTitledBorder(locale.getString("tournament.rr.help_frame")));
        pnlHelp.setLayout(new MigLayout("insets panel, wrap 4", "push[][]unrel:48lp:[][]push"));

        Font keyFont = FrameBase.scaleFont(jLabel7, 1.2f);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Set or change result / Select game ");
        pnlHelp.add(jLabel1);

        jLabel7.setFont(keyFont);
        jLabel7.setText(locale.getString("tournament.rr.left_click"));
        pnlHelp.add(jLabel7);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText(locale.getString("tournament.rr.increase_handicap"));
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlHelp.add(jLabel2);

        jLabel6.setFont(keyFont);
        jLabel6.setText("+");
        pnlHelp.add(jLabel6);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Change color ");
        pnlHelp.add(jLabel12);

        jLabel14.setFont(keyFont);
        jLabel14.setText(locale.getString("tournament.rr.right_click"));
        pnlHelp.add(jLabel14);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText(locale.getString("tournament.rr.decrease_handicap"));
        pnlHelp.add(jLabel3);

        jLabel8.setFont(keyFont);
        jLabel8.setText("-");
        pnlHelp.add(jLabel8);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText(locale.getString("tournament.rr.delete_game"));
        pnlHelp.add(jLabel4, "skip 2");

        jLabel9.setFont(keyFont);
        jLabel9.setText("Del");
        pnlHelp.add(jLabel9);

        getContentPane().add(pnlHelp, "spanx 2");

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/info/vannier/gotha/gothalogo16.jpg")));
        btnHelp.setText(locale.getString("btn.help"));
        btnHelp.addActionListener(this::btnHelpActionPerformed);
        getContentPane().add(btnHelp, "split 2, flowx, tag help");

        btnClose.setText(locale.getString("btn.close"));
        btnClose.addActionListener(this::btnCloseActionPerformed);
        getContentPane().add(btnClose, "tag cancel");

        pack();
    }

private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
    cleanClose();
}

    private void cleanClose(){
        running = false;
        dispose();
    }

private void tblGamesMousePressed(java.awt.event.MouseEvent evt) {
    int r = tblGames.rowAtPoint(evt.getPoint());
    int c = tblGames.columnAtPoint(evt.getPoint());
    if (r == c - FIRST_PLAYER_COL) return;
    Player p1 = getPlayerAtRow(r);
    if (p1 == null) return;
    Player p2 = getPlayerAtRow(c - FIRST_PLAYER_COL);
    if(p2 == null) return;
    int mod = evt.getModifiers();
    if ( (mod & InputEvent.BUTTON1_MASK) != 0) changeResult(p1, p2);
    if ( (mod & InputEvent.BUTTON3_MASK) != 0) changeColor(p1, p2);

    this.tournamentChanged();

}

private void tblGamesKeyPressed(java.awt.event.KeyEvent evt) {
    int row = tblGames.getSelectedRow();
    int col = tblGames.getSelectedColumn();
    int kc = evt.getKeyCode();

    if (kc != KeyEvent.VK_DELETE)return;

    Player p1 = getPlayerAtRow(row);
    if (p1 == null) return;
    Player p2 = getPlayerAtRow(col - FIRST_PLAYER_COL);
    if(p2 == null) return;
    Game g = findGame(p1, p2);
    if (g == null) return;
    try {
        tournament.removeGame(g);
    } catch (TournamentException ex) {
        Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
        Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
    }

    this.tournamentChanged();

}

private void tblGamesKeyTyped(java.awt.event.KeyEvent evt) {
    int row = tblGames.getSelectedRow();
    int col = tblGames.getSelectedColumn();
    char car = evt.getKeyChar();

    Player p1 = getPlayerAtRow(row);
    if (p1 == null) return;
    Player p2 = getPlayerAtRow(col - FIRST_PLAYER_COL);
    if(p2 == null) return;


    if ( car == '+') changeHandicap(p1, p2, 1);
    else if ( car == '-') changeHandicap(p1, p2, -1);
    else return;

    this.tournamentChanged();

}

private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {
    Gotha.displayGothaHelp("Games Round-robin frame");
}

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        cleanClose();
    }

    private void customInitComponents()throws RemoteException{
        initGamesComponents();
        updateAllViews();
//        updateComponents();
    }

    private void initGamesComponents(){
        // Total available width is 784
        final int NAME_WIDTH = 134;
        final int RANK_WIDTH = 30;
        final int NUMBER_WIDTH = 20;
        final int GAME_WIDTH = 30;

        Vector<String> columnNames = new Vector<String>();
        columnNames.add(locale.getString("player.player_name"));
        columnNames.add(locale.getString("player.rank_s"));
        columnNames.add(locale.getString("nr"));
        for (int ip = 0; ip < MAX_NUMBER_OF_RR_PLAYERS; ip++){
            columnNames.add("" + (ip + 1));
        }

        RRGamesTableModel tbm = new RRGamesTableModel(columnNames,
                FIRST_PLAYER_COL + 1 + MAX_NUMBER_OF_RR_PLAYERS);
        this.tblGames.setModel(tbm);


        TableColumnModel tcm = this.tblGames.getColumnModel();
        tcm.getColumn(NAME_COL).setPreferredWidth(NAME_WIDTH);
        tcm.getColumn(RANK_COL).setPreferredWidth(RANK_WIDTH);
        tcm.getColumn(NUMBER_COL).setPreferredWidth(NUMBER_WIDTH);

        for (int ip = 0; ip < MAX_NUMBER_OF_RR_PLAYERS; ip++){
            tcm.getColumn(FIRST_PLAYER_COL + ip).setPreferredWidth(GAME_WIDTH);
        }


        // Renderer
        for (int iCol = 0; iCol < tbm.getColumnCount(); iCol++){
            TableColumn tc = this.tblGames.getColumnModel().getColumn(iCol);
            tc.setCellRenderer(new RRGamesTableCellRenderer());
        }
    }

    private void updateComponents(){
        if (!isRRCompatible()){
            this.pnlGames.setVisible(false);
            this.pnlHelp.setVisible(false);
            lblUncompatibility3.setText(locale.format("tournament.rr.incompatible_2", MAX_NUMBER_OF_RR_PLAYERS));
            this.pnlUncompatibility.setVisible(true);
            return;
        }
        else{
            this.pnlGames.setVisible(true);
            this.pnlHelp.setVisible(true);
            this.pnlUncompatibility.setVisible(false);
        }
        updateTblGames();
        try {
            this.txfNumberOfRounds.setText("" + tournament.getTournamentParameterSet().getGeneralParameterSet().getNumberOfRounds());
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTblGames(){
        ArrayList<Player> alPlayers = null;
        try {
            alPlayers = tournament.playersList();
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Player> alDisplayedPlayers = new ArrayList<Player>(alPlayers);
        PlayerComparator playerComparator = new PlayerComparator(PlayerComparator.RANK_ORDER);
        Collections.sort(alDisplayedPlayers, playerComparator);

        TableModel tbm = tblGames.getModel();
        for (int iRow = 0; iRow < MAX_NUMBER_OF_RR_PLAYERS; iRow++){
            Player p = null;
            if (iRow < alDisplayedPlayers.size()){
                p = alDisplayedPlayers.get(iRow);
                String strNP = p.fullName();
                tbm.setValueAt(strNP, iRow, NAME_COL);
                String strRank = Player.convertIntToKD(p.getRank());
                tbm.setValueAt(strRank, iRow, RANK_COL);
                tbm.setValueAt("" + (iRow + 1), iRow, NUMBER_COL);
            }
            else{
                tbm.setValueAt("", iRow, NAME_COL);
                tbm.setValueAt("", iRow, RANK_COL);
                tbm.setValueAt("", iRow, NUMBER_COL);
            }
        }

        // Games
        ArrayList<Game> alG = null;
        try {
            alG = tournament.gamesList();
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        // By default, set all games strings to ""
        for (int i = 0; i < MAX_NUMBER_OF_RR_PLAYERS; i++){
            int row = i;
            for (int j = 0; j < MAX_NUMBER_OF_RR_PLAYERS; j++){
                int col = j + FIRST_PLAYER_COL;
                tblGames.setValueAt("", row, col);
            }
        }

        for (Game g: alG){
            Player wP = g.getWhitePlayer();
            Player bP = g.getBlackPlayer();
            int hd = g.getHandicap();
            Game.Result res = g.getResult();

            // Build the game strings
            String strW = "w";
            String strB = "b";
            switch(res){
                case WHITEWINS:
                    strW = "+/" + strW;
                    strB = "-/" + strB;
                    break;
                case WHITEWINS_BYDEF:
                    strW = "+!" + strW;
                    strB = "-!" + strB;
                    break;
                case BLACKWINS:
                    strW = "-/" + strW;
                    strB = "+/" + strB;
                    break;
                case BLACKWINS_BYDEF:
                    strW = "-!" + strW;
                    strB = "+!" + strB;
                    break;
                case EQUAL:
                    strW = "=/" + strW;
                    strB = "=/" + strB;
                    break;
                case EQUAL_BYDEF:
                    strW = "=!" + strW;
                    strB = "=!" + strB;
                    break;
                case UNKNOWN:
                    strW = "?" + strW;
                    strB = "?" + strB;
                    break;
                case BOTHWIN:
                    strW = "+/" + strW;
                    strB = "+/" + strB;
                    break;
                case BOTHWIN_BYDEF:
                    strW = "+!" + strW;
                    strB = "+!" + strB;
                    break;
                case BOTHLOSE:
                    strW = "-/" + strW;
                    strB = "-/" + strB;
                    break;
                case BOTHLOSE_BYDEF:
                    strW = "-!" + strW;
                    strB = "-!" + strB;
                    break;
            }
            strW += hd;
            strB += hd;

            // Search for players
            int iWP = -1;
            int iBP = -1;
            for (int i = 0; i < alDisplayedPlayers.size(); i++){
                Player p = alDisplayedPlayers.get(i);
                if (p.hasSameKeyString(wP)) iWP = i;
                if (p.hasSameKeyString(bP)) iBP = i;
            }
            tbm.setValueAt(strW, iWP, iBP + FIRST_PLAYER_COL);
            tbm.setValueAt(strB, iBP, iWP + FIRST_PLAYER_COL);
        }
    }

    private void tournamentChanged(){
        try {
            tournament.setLastTournamentModificationTime(tournament.getCurrentTournamentTime());
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateAllViews();
    }

    private void updateAllViews(){
        try {
            if (!tournament.isOpen()) cleanClose();
            this.lastComponentsUpdateTime = tournament.getCurrentTournamentTime();
            setTitle("Games .. Round-robin. " + tournament.getFullName());
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.updateComponents();
    }

    /**
     * test Round-robin compatibility
     * 2 conditions required :
     * - The number of players must be smaller or equal to MAX_NUMBER_OF_RR_PLAYERS
     * - Any pair of players should be paired one or zero time
     *
     * @return
     */
    private boolean isRRCompatible(){
        try {
            if (tournament.numberOfPlayers() > MAX_NUMBER_OF_RR_PLAYERS) return false;
            if (tournament.duplicateGames().size() > 0) return false;
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;

    }

    private Player getPlayerAtRow(int rowIndex){
        if (rowIndex < 0) return null;
        TableModel tbm = tblGames.getModel();
        String strNP = (String)tbm.getValueAt(rowIndex, NAME_COL);
        if (strNP.compareTo("") == 0) return null;
        Player p = null;
        try {
            p = tournament.getPlayerByKeyString(strNP);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    private Game findGame(Player p1, Player p2){
        ArrayList<Game> alG = null;
        try {
            alG = tournament.gamesPlayedBy(p1, p2);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (alG.size() > 1){
            return null;
        }
        if(alG.isEmpty()) return null;
        return alG.get(0);
    }

/**
 * Changes a result
 * Beware : The result cycle does not process BYDEF results
 * @param p1
 * @param p2
 */
    private void changeResult(Player p1, Player p2){
        Game g = findGame(p1, p2);
        if (g == null) createGame(p1, p2);
        else{
            Game.Result oldResult = g.getResult();
            Game.Result newResult = Game.Result.UNKNOWN;
            if (oldResult == Game.Result.UNKNOWN){
                if (p1.hasSameKeyString(g.getWhitePlayer())) newResult = Game.Result.WHITEWINS;
                else newResult = Game.Result.BLACKWINS;
            }
            else if (oldResult == Game.Result.WHITEWINS){
                if (p1.hasSameKeyString(g.getWhitePlayer())) newResult = Game.Result.BLACKWINS;
                else newResult = Game.Result.EQUAL;
            }
            else if (oldResult == Game.Result.BLACKWINS){
                if (p1.hasSameKeyString(g.getWhitePlayer())) newResult = Game.Result.EQUAL;
                else newResult = Game.Result.WHITEWINS;
            }
            else if (oldResult == Game.Result.EQUAL) newResult = Game.Result.BOTHWIN;
            else if (oldResult == Game.Result.BOTHWIN) newResult = Game.Result.BOTHLOSE;
            else if (oldResult == Game.Result.BOTHLOSE) newResult = Game.Result.UNKNOWN;

            try {
                tournament.setResult(g, newResult);
            } catch (RemoteException | TournamentException ex) {
                            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createGame(Player p1, Player p2){
        if (p1 == null) return;
        if (p2 == null) return;

        // Search for an available round
        ArrayList<Game> alG1 = null;
        ArrayList<Game> alG2 = null;

        int availableRound = -1;
        boolean[] bAvailable = new boolean[Gotha.MAX_NUMBER_OF_ROUNDS];
        for (int r = 0; r < Gotha.MAX_NUMBER_OF_ROUNDS; r++){
            bAvailable[r] = true;
        }
        try {
            alG1 = tournament.gamesPlayedBy(p1);
            alG2 = tournament.gamesPlayedBy(p2);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Game g: alG1){
            int r = g.getRound();
            bAvailable[r] = false;
        }
        for (Game g: alG2){
            int r = g.getRound();
            bAvailable[r] = false;
        }
        for (int r = 0; r < Gotha.MAX_NUMBER_OF_ROUNDS; r++){
            if (bAvailable[r]){
                availableRound = r;
                break;
            }
        }
        if (availableRound == -1){
            String strMessage = locale.getString("tournament.rr.game_not_created_number_of_rounds");
            JOptionPane.showMessageDialog (this, strMessage, locale.getString("alert.message"), JOptionPane.ERROR_MESSAGE);
        }

        try {
            int numberOfRounds = tournament.getTournamentParameterSet().getGeneralParameterSet().getNumberOfRounds();
            if (availableRound >= numberOfRounds) {
                TournamentParameterSet tps = tournament.getTournamentParameterSet();
                tps.getGeneralParameterSet().setNumberOfRounds(availableRound + 1);
                tournament.setTournamentParameterSet(tps);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Force participation to true
        boolean[] bPart1 = p1.getParticipating();
        if (!bPart1[availableRound]){
            bPart1[availableRound] = true;
            try {
                tournament.modifyPlayer(p1, p1);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        boolean[] bPart2 = p2.getParticipating();
        if (!bPart2[availableRound]){
            bPart2[availableRound] = true;
            try {
                tournament.modifyPlayer(p2, p2);
            } catch (TournamentException ex) {
                Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //clear bye player if necessary
        Player bye = null;
        try {
            bye = tournament.getByePlayer(availableRound);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (p1.hasSameKeyString(bye)) try {
            tournament.unassignByePlayer(availableRound);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (p2.hasSameKeyString(bye)) try {
            tournament.unassignByePlayer(availableRound);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Make pairing as if we were pairing for round 0
        ArrayList<Player> alPlayersToPair = new ArrayList<Player>();
        alPlayersToPair.add(p1);
        alPlayersToPair.add(p2);

        ArrayList<Game> alNewGames = null;
        try {
            alNewGames = tournament.makeAutomaticPairing(alPlayersToPair, 0);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesPair.class.getName()).log(Level.SEVERE, null, ex);
        }

        Game.Builder g = alNewGames.get(0).builder();
        g.setRound(availableRound);
        // Give a table number
        int tN = 0;
        boolean bTNOK;  // Table number OK
        do {
            bTNOK = true;
            try {
                for (Game oldG : tournament.gamesList(availableRound)) {
                    if (oldG.getBoard() == tN) {
                        tN++;
                        bTNOK = false;
                    }
                }
            } catch (RemoteException ex) {
                            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!bTNOK);

        g.setBoard(tN);

        try {
            tournament.addGame(g.build());
        } catch (TournamentException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }

    private void changeColor(Player p1, Player p2){
        Game g = findGame(p1, p2);
        if (g == null) return;
        try {
            tournament.exchangeGameColors(g);
        } catch (RemoteException | TournamentException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void changeHandicap(Player p1, Player p2, int deltaHd){
        Game g = findGame(p1, p2);
        if (g == null) return;
        int hd = g.getHandicap();
        hd += deltaHd;
        try {
            tournament.setGameHandicap(g, hd);
        } catch (RemoteException | TournamentException ex) {
            Logger.getLogger(JFrGamesRR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHelp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblBNumberOfRounds;
    private javax.swing.JLabel lblUncompatibility1;
    private javax.swing.JLabel lblUncompatibility3;
    private javax.swing.JLabel lblUncompatibility4;
    private javax.swing.JPanel pnlGames;
    private javax.swing.JPanel pnlHelp;
    private javax.swing.JPanel pnlUncompatibility;
    private javax.swing.JScrollPane scpGames;
    private javax.swing.JTable tblGames;
    private javax.swing.JTextField txfNumberOfRounds;
}

class RRGamesTableCellRenderer extends JLabel implements TableCellRenderer {
    // This method is called each time a cell in a column
    // using this renderer needs to be rendered.
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int rowIndex, int colIndex) {
        TableModel model = table.getModel();
        if (rowIndex >= JFrGamesRR.MAX_NUMBER_OF_RR_PLAYERS){
            setText("N/A");
            return this;
        }
        int numPH = rowIndex;
        int numPV = colIndex - JFrGamesRR.FIRST_PLAYER_COL;

        // Default
        setOpaque(true);
        setBackground(Color.WHITE);
        if (isSelected && colIndex >= JFrGamesRR.FIRST_PLAYER_COL){
            setBackground(new Color(224, 224, 255));
        }
        String strNP = (String)model.getValueAt(numPH, JFrGamesRR.NAME_COL);
        if (strNP.compareTo("") == 0){
            setBackground(Color.LIGHT_GRAY);
        }
        else if (numPH == numPV){
            setBackground(Color.BLACK);
        }
        else if( numPV >= 0){
            String strNPOpp = (String)model.getValueAt(numPV, JFrGamesRR.NAME_COL);
            if (strNPOpp.compareTo("") == 0){
                setBackground(Color.LIGHT_GRAY);
            }
        }

        // Foreground colors
        // default is black
        setForeground(Color.BLACK);
        String str =  (String)model.getValueAt(rowIndex, colIndex);
        if (colIndex >= JFrGamesRR.FIRST_PLAYER_COL && str.length() >= 1){
            String strRes = str.substring(0, 1);
            if (strRes.compareTo("+") == 0) setForeground(Color.RED);
            if (strRes.compareTo("-") == 0) setForeground(Color.BLUE);
            if (strRes.compareTo("=") == 0) setForeground(Color.MAGENTA);
        }

//        setFont(new Font("Arial", Font.PLAIN, 12));
        setFont(this.getFont().deriveFont(Font.PLAIN));
        setText("" + model.getValueAt(rowIndex, colIndex));


        return this;
    }
}

class RRGamesTableModel extends DefaultTableModel{
    public RRGamesTableModel(Vector columnNames, int rowCount){
        super(columnNames, rowCount);
    }
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
}
