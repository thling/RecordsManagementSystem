/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 *
 * @author sam
 */
public class Dashboard extends javax.swing.JFrame {
    private static Dashboard _instance = null;
    public static String sortType;
    private ArrayList<String> globalFilter = new ArrayList<>();
    private static Point lastPnlDocumentClick = null;
    
    /**
     * Creates new form Dashboard
     */
    private Dashboard() {
        initComponents();
        
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                dlgFilterType.dispose();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                Point p = btnFilterClients.getLocationOnScreen();
                dlgFilterType.setSize(btnFilterClients.getWidth(), 300);
                dlgFilterType.setLocation(p.x, p.y - dlgFilterType.getHeight() + btnFilterClients.getHeight());
            }

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Point p = btnFilterClients.getLocationOnScreen();
                dlgFilterType.setSize(btnFilterClients.getWidth(), 300);
                dlgFilterType.setLocation(p.x, p.y - dlgFilterType.getHeight() + btnFilterClients.getHeight());
            }
        
        });
        
        
        this.pnlLoadingFiles.setVisible(false);
    }
    
    /**
     * Returns an instance of dashboard
     * @return Dashboard
     */
    public static Dashboard getInstance() {
        if (_instance == null) {
            _instance = new Dashboard();
        }
        
        return _instance;
    }
    
    /**
     * Process some other post initialization
     */
    public void display() {
        Util.setFrameInMiddle(this);
        setVisible(true);
        this.comboSortType.removeAllItems();
        this.comboSortType.addItem("Date Added");
        this.comboSortType.addItem("Name");
        this.comboSortType.setSelectedItem("Name");
        
        sortType = "Name";
        
        updateClientList();
        
        this.lblClientName.setVisible(false);
        this.lblClientSince.setVisible(false);
        this.txtAge.setVisible(false);
        this.txtGender.setVisible(false);
        this.txtIncome.setVisible(false);
        this.txtMarital.setVisible(false);
        this.btnEditClient.setEnabled(false);
    }
    
    /**
     * Updates the client list
     */
    public void updateClientList() {
        this.txtSearchClient.setText(null);
        updateClientList(null);
    }
    
    /**
     * Filter client list
     * 
     * @param c Client object
     * @param filter String to filter with
     * @return True if the client satisfies everything listed in filter
     */
    private boolean clientSatisfies(Client c, String[] filter) {
        if (this.txtSearchClient.getText().length() > 0) {
            if (!c.getName().toLowerCase().contains(this.txtSearchClient.getText())) {
                return false;
            }
        }
        
        for (int i = 0; i < filter.length; i++) {
            if (!c.getAge().contains(filter[i])
                    && !c.getGender().contains(filter[i])
                    && !c.getIncome().contains(filter[i])
                    && !c.getMarital().contains(filter[i])) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Updates the client list
     * 
     * @param filter List of filter to use with
     */
    public void updateClientList(String[] filter) {
        Client[] clist = FakeDB.getClientList();
        
        if (filter == null) {
            filter = new String[0];
        }
        
        this.lstClientList.removeAll();
        String[] names = new String[clist.length];
        int lstInd = 0;
        for (int i = 0; i < clist.length; i++) {
            if (!clientSatisfies(clist[i], filter)) {
                
                continue;
            }
            
            names[lstInd] = clist[i].getName();
            lstInd++;
        }
        
        this.lstClientList.setListData(names);
        this.lstClientList.repaint();
    }
    
    /**
     * Update the document list with a client name
     * 
     * @param name Name a client
     */
    public void updateDocumentList(String name) {
        this.txtSearchDocument.setText(null);
        updateDocumentList(name, "");
    }
    
    /**
     * Updates the document list under a client, filter with second pararmeter
     * 
     * @param name Name of a client
     * @param filter Search strings to filter with
     */
    public void updateDocumentList(String name, String filter) {
        // If filter is null, and if there's no selected client, return
        if (filter != null) {
            if (this.lstClientList.getSelectedIndex() == -1) {
                this.pnlDocumentList.removeAll();
                this.pnlDocumentList.doLayout();
                this.scrpnlDocuments.doLayout();
                return;
            }
        }
        
        // Gets the list of documents owned by the selected client
        Document[] dlist = FakeDB.getDocumentsByClient(name, (String)this.comboSortType.getSelectedItem());
                
        this.pnlDocumentList.removeAll();
        
        if (filter == null) {
            filter = "";
        }
        
        // Document list
        ArrayList<DocumentPanel> panelsToAdd = new ArrayList<>();
        for (int i = 0; i < dlist.length; i++) {
            DocumentPanel p = new DocumentPanel(dlist[i]);
            if (!filter.equals("")
                    && !p.getDocument().getName().toLowerCase().contains(filter.toLowerCase())) {
                continue;
            }
            
            panelsToAdd.add(p);
        }
        
        // Sets up the grids in scroll bar
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(3,3,3,3);
        
        this.pnlDocumentList.setLayout(g);
        
        for (int i = 0; i < panelsToAdd.size(); i++) {
            c.gridy = i;
            if ( i == panelsToAdd.size() - 1) {
                c.weighty = 1.0;
            }
            
            DocumentPanel dp = panelsToAdd.get(i);
            g.setConstraints(dp, c);
            this.pnlDocumentList.add(dp);
        }
        
        for (int i = 0; i < this.pnlDocumentList.getComponentCount(); i++) {
            this.pnlDocumentList.getComponent(i).doLayout();
        }
        
        // Relayout
        this.pnlDocumentList.doLayout();
        this.scrpnlDocuments.doLayout();
    }
    
    /**
     * Updates the client information shown above the document list and document view
     * 
     * @param c Client object to update with
     */
    public void updateClientPanel(Client c) {
        if (c == null) {
            return;
        }
        
        this.btnEditClient.setEnabled(true);
        this.lblClientName.setText(" " + c.getName());
        this.lblClientSince.setText("Client Since " + c.getSince());
        this.txtAge.setText("  Age: " + c.getAge() + "  ");
        this.txtGender.setText("  Gender: " + c.getGender() + "  ");
        this.txtIncome.setText("  Income: " + c.getIncome() + "  ");
        this.txtMarital.setText("  Marital: " + c.getMarital() + "  ");
        
        for (int i = 0; i < this.pnlClientInfo.getComponentCount(); i++) {
            this.pnlClientInfo.getComponent(i).doLayout();
        }
        
        this.pnlClientInfo.doLayout();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgFilterType = new javax.swing.JDialog();
        pnlFilter = new javax.swing.JPanel();
        btnHideFilter = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        chkGenderMale = new javax.swing.JCheckBox();
        chkGenderFemale = new javax.swing.JCheckBox();
        chkGenderOther = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        chkIncomeBelow20K = new javax.swing.JCheckBox();
        chkIncome20Kto40K = new javax.swing.JCheckBox();
        chkIncome40Kto60K = new javax.swing.JCheckBox();
        chkIncome60Kto80K = new javax.swing.JCheckBox();
        chkIncomeAbove80K = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        chkAgeBelow20 = new javax.swing.JCheckBox();
        chkAge20to40 = new javax.swing.JCheckBox();
        chkAge40to60 = new javax.swing.JCheckBox();
        chkAgeAbove60 = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        chkMaritalSingle = new javax.swing.JCheckBox();
        chkMaritalMarried = new javax.swing.JCheckBox();
        chkMaritalDivorced = new javax.swing.JCheckBox();
        chkMaritalWidowed = new javax.swing.JCheckBox();
        chkMaritalCommonLaw = new javax.swing.JCheckBox();
        tabpnlClients = new javax.swing.JTabbedPane();
        tabActive = new javax.swing.JPanel();
        btnFilterClients = new javax.swing.JButton();
        txtSearchClient = new javax.swing.JTextField();
        scrpnlClientList = new javax.swing.JScrollPane();
        lstClientList = new javax.swing.JList();
        tabInactive = new javax.swing.JPanel();
        pnlLoadingFiles = new javax.swing.JPanel();
        pnlImporting1 = new javax.swing.JPanel();
        lblDocumentName = new javax.swing.JLabel();
        pnlImporting2 = new javax.swing.JPanel();
        lblDocumentName1 = new javax.swing.JLabel();
        pnlImporting3 = new javax.swing.JPanel();
        lblDocumentName2 = new javax.swing.JLabel();
        lblLastSync = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSync = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnNewClient = new javax.swing.JButton();
        btnNewDocument = new javax.swing.JButton();
        txtSearchDocument = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        scrpnlDocuments = new javax.swing.JScrollPane();
        pnlDocumentList = new javax.swing.JPanel();
        scrpnlDocumentView = new javax.swing.JScrollPane();
        pnlDocumentView = new javax.swing.JPanel();
        comboSortType = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        pnlClientInfo = new javax.swing.JPanel();
        lblClientName = new javax.swing.JLabel();
        lblClientSince = new javax.swing.JLabel();
        btnEditClient = new javax.swing.JButton();
        txtGender = new javax.swing.JTextField();
        txtAge = new javax.swing.JTextField();
        txtIncome = new javax.swing.JTextField();
        txtMarital = new javax.swing.JTextField();

        dlgFilterType.setUndecorated(true);

        pnlFilter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(121, 121, 121), 2));

        btnHideFilter.setText("Hide");
        btnHideFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHideFilterActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        jLabel3.setText("Filter by");

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        jLabel4.setText("Gender");

        chkGenderMale.setText("Male");
        chkGenderMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenderMaleActionPerformed(evt);
            }
        });

        chkGenderFemale.setText("Female");
        chkGenderFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenderFemaleActionPerformed(evt);
            }
        });

        chkGenderOther.setText("Other");
        chkGenderOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenderOtherActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        jLabel5.setText("Annual Income");

        chkIncomeBelow20K.setText("Below 20K");
        chkIncomeBelow20K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIncomeBelow20KActionPerformed(evt);
            }
        });

        chkIncome20Kto40K.setText("20K to 40K");
        chkIncome20Kto40K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIncome20Kto40KActionPerformed(evt);
            }
        });

        chkIncome40Kto60K.setText("40K to 60K");
        chkIncome40Kto60K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIncome40Kto60KActionPerformed(evt);
            }
        });

        chkIncome60Kto80K.setText("60K to 80K");
        chkIncome60Kto80K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIncome60Kto80KActionPerformed(evt);
            }
        });

        chkIncomeAbove80K.setText("Above 80K");
        chkIncomeAbove80K.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIncomeAbove80KActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        jLabel6.setText("Age");

        chkAgeBelow20.setText("Below 20");
        chkAgeBelow20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAgeBelow20ActionPerformed(evt);
            }
        });

        chkAge20to40.setText("20 to 40");
        chkAge20to40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAge20to40ActionPerformed(evt);
            }
        });

        chkAge40to60.setText("40 to 60");
        chkAge40to60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAge40to60ActionPerformed(evt);
            }
        });

        chkAgeAbove60.setText("Above 60");
        chkAgeAbove60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAgeAbove60ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        jLabel7.setText("Marital Status");

        chkMaritalSingle.setText("Single");
        chkMaritalSingle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMaritalSingleActionPerformed(evt);
            }
        });

        chkMaritalMarried.setText("Married");
        chkMaritalMarried.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMaritalMarriedActionPerformed(evt);
            }
        });

        chkMaritalDivorced.setText("Divorced");
        chkMaritalDivorced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMaritalDivorcedActionPerformed(evt);
            }
        });

        chkMaritalWidowed.setText("Widowed");
        chkMaritalWidowed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMaritalWidowedActionPerformed(evt);
            }
        });

        chkMaritalCommonLaw.setText("Common Law");
        chkMaritalCommonLaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMaritalCommonLawActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(chkGenderMale)
                            .addComponent(chkGenderFemale)
                            .addComponent(chkGenderOther)
                            .addComponent(chkIncomeBelow20K)
                            .addComponent(chkIncome20Kto40K)
                            .addComponent(chkIncome40Kto60K)
                            .addComponent(chkIncome60Kto80K)
                            .addComponent(chkIncomeAbove80K)
                            .addComponent(chkAgeBelow20)
                            .addComponent(chkAge20to40)
                            .addComponent(chkAge40to60)
                            .addComponent(chkAgeAbove60)
                            .addComponent(chkMaritalSingle)
                            .addComponent(chkMaritalMarried)
                            .addComponent(chkMaritalDivorced)
                            .addComponent(chkMaritalWidowed)
                            .addComponent(chkMaritalCommonLaw))
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGenderMale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGenderFemale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkGenderOther)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIncomeBelow20K)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIncome20Kto40K)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIncome40Kto60K)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIncome60Kto80K)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkIncomeAbove80K)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAgeBelow20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAge20to40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAge40to60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAgeAbove60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMaritalSingle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMaritalMarried)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMaritalDivorced)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMaritalWidowed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMaritalCommonLaw))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout pnlFilterLayout = new javax.swing.GroupLayout(pnlFilter);
        pnlFilter.setLayout(pnlFilterLayout);
        pnlFilterLayout.setHorizontalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
            .addComponent(btnHideFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlFilterLayout.setVerticalGroup(
            pnlFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilterLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHideFilter))
        );

        javax.swing.GroupLayout dlgFilterTypeLayout = new javax.swing.GroupLayout(dlgFilterType.getContentPane());
        dlgFilterType.getContentPane().setLayout(dlgFilterTypeLayout);
        dlgFilterTypeLayout.setHorizontalGroup(
            dlgFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgFilterTypeLayout.setVerticalGroup(
            dlgFilterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Records Management System");

        btnFilterClients.setText("Filter Clients by...");
        btnFilterClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterClientsActionPerformed(evt);
            }
        });

        txtSearchClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchClientKeyReleased(evt);
            }
        });

        lstClientList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstClientList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstClientListMouseClicked(evt);
            }
        });
        scrpnlClientList.setViewportView(lstClientList);

        javax.swing.GroupLayout tabActiveLayout = new javax.swing.GroupLayout(tabActive);
        tabActive.setLayout(tabActiveLayout);
        tabActiveLayout.setHorizontalGroup(
            tabActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnFilterClients, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addComponent(txtSearchClient)
            .addComponent(scrpnlClientList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        tabActiveLayout.setVerticalGroup(
            tabActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabActiveLayout.createSequentialGroup()
                .addComponent(txtSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrpnlClientList, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFilterClients))
        );

        tabpnlClients.addTab("Active Clients", tabActive);

        javax.swing.GroupLayout tabInactiveLayout = new javax.swing.GroupLayout(tabInactive);
        tabInactive.setLayout(tabInactiveLayout);
        tabInactiveLayout.setHorizontalGroup(
            tabInactiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );
        tabInactiveLayout.setVerticalGroup(
            tabInactiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );

        tabpnlClients.addTab("Inactive Clients", tabInactive);

        pnlLoadingFiles.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pnlImporting1.setBackground(new java.awt.Color(254, 223, 222));
        pnlImporting1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true));

        lblDocumentName.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblDocumentName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/loader.gif"))); // NOI18N
        lblDocumentName.setText("Importing TD Waterhou...");

        javax.swing.GroupLayout pnlImporting1Layout = new javax.swing.GroupLayout(pnlImporting1);
        pnlImporting1.setLayout(pnlImporting1Layout);
        pnlImporting1Layout.setHorizontalGroup(
            pnlImporting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImporting1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocumentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlImporting1Layout.setVerticalGroup(
            pnlImporting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentName, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        pnlImporting2.setBackground(new java.awt.Color(220, 254, 214));
        pnlImporting2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true));
        pnlImporting2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlImporting2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlImporting2MouseClicked(evt);
            }
        });

        lblDocumentName1.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblDocumentName1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/checkmark.png"))); // NOI18N
        lblDocumentName1.setText("Imported Samuel Life E...");

        javax.swing.GroupLayout pnlImporting2Layout = new javax.swing.GroupLayout(pnlImporting2);
        pnlImporting2.setLayout(pnlImporting2Layout);
        pnlImporting2Layout.setHorizontalGroup(
            pnlImporting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImporting2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocumentName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlImporting2Layout.setVerticalGroup(
            pnlImporting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlImporting3.setBackground(new java.awt.Color(220, 254, 214));
        pnlImporting3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(128, 128, 128), 1, true));
        pnlImporting3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlImporting3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlImporting3MouseClicked(evt);
            }
        });

        lblDocumentName2.setBackground(new java.awt.Color(179, 253, 165));
        lblDocumentName2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        lblDocumentName2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/checkmark.png"))); // NOI18N
        lblDocumentName2.setText("Imported Crazi Being Life");

        javax.swing.GroupLayout pnlImporting3Layout = new javax.swing.GroupLayout(pnlImporting3);
        pnlImporting3.setLayout(pnlImporting3Layout);
        pnlImporting3Layout.setHorizontalGroup(
            pnlImporting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImporting3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocumentName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlImporting3Layout.setVerticalGroup(
            pnlImporting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDocumentName2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlLoadingFilesLayout = new javax.swing.GroupLayout(pnlLoadingFiles);
        pnlLoadingFiles.setLayout(pnlLoadingFilesLayout);
        pnlLoadingFilesLayout.setHorizontalGroup(
            pnlLoadingFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoadingFilesLayout.createSequentialGroup()
                .addComponent(pnlImporting1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlImporting2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlImporting3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlLoadingFilesLayout.setVerticalGroup(
            pnlLoadingFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlImporting1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlImporting2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlImporting3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lblLastSync.setText("Last Sync: Jul 20, 2012 20:31");
        lblLastSync.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLastSyncMouseClicked(evt);
            }
        });

        btnSync.setBackground(new java.awt.Color(254, 254, 254));
        btnSync.setForeground(new java.awt.Color(1, 1, 1));
        btnSync.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/sync.png"))); // NOI18N
        btnSync.setText("Sync");
        btnSync.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSync.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnNewClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/new_contact.png"))); // NOI18N
        btnNewClient.setText("New Client");
        btnNewClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewClientActionPerformed(evt);
            }
        });

        btnNewDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/new_document.png"))); // NOI18N
        btnNewDocument.setText("New Doc");
        btnNewDocument.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewDocument.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewDocumentActionPerformed(evt);
            }
        });

        txtSearchDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDocumentKeyReleased(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnSync, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(btnNewClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNewDocument, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchDocument)
                .addContainerGap())
            .addComponent(btnSync, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlDocumentList.setBackground(new java.awt.Color(254, 254, 254));
        pnlDocumentList.setMinimumSize(new java.awt.Dimension(10, 100));
        pnlDocumentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDocumentListMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlDocumentListLayout = new javax.swing.GroupLayout(pnlDocumentList);
        pnlDocumentList.setLayout(pnlDocumentListLayout);
        pnlDocumentListLayout.setHorizontalGroup(
            pnlDocumentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );
        pnlDocumentListLayout.setVerticalGroup(
            pnlDocumentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        scrpnlDocuments.setViewportView(pnlDocumentList);

        javax.swing.GroupLayout pnlDocumentViewLayout = new javax.swing.GroupLayout(pnlDocumentView);
        pnlDocumentView.setLayout(pnlDocumentViewLayout);
        pnlDocumentViewLayout.setHorizontalGroup(
            pnlDocumentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );
        pnlDocumentViewLayout.setVerticalGroup(
            pnlDocumentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        scrpnlDocumentView.setViewportView(pnlDocumentView);

        comboSortType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboSortType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortTypeActionPerformed(evt);
            }
        });

        jLabel2.setText("Sort by:");

        pnlClientInfo.setBackground(new java.awt.Color(254, 254, 254));
        pnlClientInfo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(158, 158, 158), 1, true));
        pnlClientInfo.setPreferredSize(new java.awt.Dimension(50, 50));

        lblClientName.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        lblClientName.setText("jLabel3");

        lblClientSince.setText("jLabel3");

        btnEditClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/edit.png"))); // NOI18N
        btnEditClient.setText("Edit");
        btnEditClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditClientActionPerformed(evt);
            }
        });

        txtGender.setEditable(false);
        txtGender.setText("jTextField1");
        txtGender.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(115, 115, 115), null));

        txtAge.setEditable(false);
        txtAge.setText("jTextField2");
        txtAge.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(115, 115, 115), null));

        txtIncome.setEditable(false);
        txtIncome.setText("jTextField3");
        txtIncome.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(115, 115, 115), null));

        txtMarital.setEditable(false);
        txtMarital.setText("jTextField4");
        txtMarital.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(115, 115, 115), null));

        javax.swing.GroupLayout pnlClientInfoLayout = new javax.swing.GroupLayout(pnlClientInfo);
        pnlClientInfo.setLayout(pnlClientInfoLayout);
        pnlClientInfoLayout.setHorizontalGroup(
            pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClientInfoLayout.createSequentialGroup()
                        .addComponent(lblClientName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClientSince))
                    .addGroup(pnlClientInfoLayout.createSequentialGroup()
                        .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMarital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditClient, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlClientInfoLayout.setVerticalGroup(
            pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEditClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientInfoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientName)
                    .addComponent(lblClientSince))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabpnlClients, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrpnlDocuments, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(3, 3, 3)
                                        .addComponent(comboSortType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrpnlDocumentView))
                            .addComponent(pnlClientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)))
                    .addComponent(pnlLoadingFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLastSync)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlClientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboSortType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrpnlDocuments, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(scrpnlDocumentView, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(tabpnlClients))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLoadingFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastSync)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboSortTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortTypeActionPerformed
        if (this.comboSortType.getItemCount() == 0 || this.lstClientList.getModel().getSize() == 0) {
            return;
        }
        
        updateDocumentList((String)this.lstClientList.getSelectedValue());
    }//GEN-LAST:event_comboSortTypeActionPerformed

    private void lstClientListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstClientListMouseClicked
        updateDocumentList((String)this.lstClientList.getSelectedValue());
        
        Client c = FakeDB.getClient((String)this.lstClientList.getSelectedValue());
        if (c == null) {
            return;
        }
        
        updateClientPanel(c);
        
        this.pnlDocumentView.removeAll();
        
        for (int i = 0; i < this.pnlClientInfo.getComponentCount(); i++) {
            this.pnlClientInfo.getComponent(i).setVisible(true);
        }
        this.pnlClientInfo.doLayout();
        
        this.pnlDocumentView.doLayout();
        this.scrpnlDocumentView.doLayout();
    }//GEN-LAST:event_lstClientListMouseClicked

    private void txtSearchClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchClientKeyReleased
        updateClientList(null);
    }//GEN-LAST:event_txtSearchClientKeyReleased

    private void pnlDocumentListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDocumentListMouseClicked
        Point thisClick = evt.getPoint();
        
        Component c = this.pnlDocumentList.getComponentAt(thisClick);
        if (!c.getClass().getName().equals("team8.DocumentPanel")) {
            return;
        }
        
        DocumentPanel dp = (DocumentPanel)c;
        
        dp.setBackground(Color.white);
        
        if (lastPnlDocumentClick != null) {
            if (this.pnlDocumentList.getComponentAt(lastPnlDocumentClick) != null) {
                this.pnlDocumentList.getComponentAt(lastPnlDocumentClick).setBackground(new Color(238, 238, 238));
            }
        }
        
        lastPnlDocumentClick = thisClick;
        
        this.pnlDocumentView.removeAll();
        
        this.pnlDocumentView.setLayout(new GridLayout(1,1));
        DocumentView dv = new DocumentView(dp.getDocument());
        
        this.pnlDocumentView.add(dv);
        
        this.scrpnlDocumentView.doLayout();
        this.pnlDocumentView.doLayout();
        dv.doLayout();
        
        this.scrpnlDocumentView.repaint();
    }//GEN-LAST:event_pnlDocumentListMouseClicked

    private void txtSearchDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDocumentKeyReleased
        updateDocumentList((String)lstClientList.getSelectedValue(), this.txtSearchDocument.getText());
    }//GEN-LAST:event_txtSearchDocumentKeyReleased

    private void btnNewDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewDocumentActionPerformed
        new NewDocument(this, true).setVisible(true);
    }//GEN-LAST:event_btnNewDocumentActionPerformed

    private void btnEditClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditClientActionPerformed
        Client c = FakeDB.getClient((String)this.lstClientList.getSelectedValue());
        
        if (c == null) {
            return;
        }
        
        new ModifyClient(this, true, c, false).setVisible(true);
    }//GEN-LAST:event_btnEditClientActionPerformed

    private void btnFilterClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterClientsActionPerformed
        this.dlgFilterType.addWindowFocusListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dlgFilterType.dispose();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                super.windowLostFocus(e);
                dlgFilterType.dispose();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
                dlgFilterType.dispose();
            }
            
        });
        
        Point p = this.btnFilterClients.getLocationOnScreen();
        
        this.dlgFilterType.setSize(btnFilterClients.getWidth(), 300);
        this.dlgFilterType.setLocation(p.x, p.y - dlgFilterType.getHeight() + btnFilterClients.getHeight());
        this.dlgFilterType.setAlwaysOnTop(true);
        this.dlgFilterType.setVisible(true);
    }//GEN-LAST:event_btnFilterClientsActionPerformed

    private void btnHideFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHideFilterActionPerformed
        this.dlgFilterType.dispose();
    }//GEN-LAST:event_btnHideFilterActionPerformed

    private void chkGenderFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenderFemaleActionPerformed
        if (chkGenderFemale.isSelected()) {
            this.globalFilter.add(chkGenderFemale.getText());
        } else {
            this.globalFilter.remove(chkGenderFemale.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkGenderFemaleActionPerformed

    private void chkGenderMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenderMaleActionPerformed
        if (chkGenderMale.isSelected()) {
            this.globalFilter.add(chkGenderMale.getText());
        } else {
            this.globalFilter.remove(chkGenderMale.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkGenderMaleActionPerformed

    private void chkIncomeBelow20KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIncomeBelow20KActionPerformed
        if (chkIncomeBelow20K.isSelected()) {
            this.globalFilter.add(chkIncomeBelow20K.getText());
        } else {
            this.globalFilter.remove(chkIncomeBelow20K.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkIncomeBelow20KActionPerformed

    private void chkGenderOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenderOtherActionPerformed
        if (chkGenderOther.isSelected()) {
            this.globalFilter.add(chkGenderOther.getText());
        } else {
            this.globalFilter.remove(chkGenderOther.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkGenderOtherActionPerformed

    private void chkIncome20Kto40KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIncome20Kto40KActionPerformed
        if (chkIncome20Kto40K.isSelected()) {
            this.globalFilter.add(chkIncome20Kto40K.getText());
        } else {
            this.globalFilter.remove(chkIncome20Kto40K.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkIncome20Kto40KActionPerformed

    private void chkIncome40Kto60KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIncome40Kto60KActionPerformed
        if (chkIncome40Kto60K.isSelected()) {
            this.globalFilter.add(chkIncome40Kto60K.getText());
        } else {
            this.globalFilter.remove(chkIncome40Kto60K.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkIncome40Kto60KActionPerformed

    private void chkIncome60Kto80KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIncome60Kto80KActionPerformed
        if (chkIncome60Kto80K.isSelected()) {
            this.globalFilter.add(chkIncome60Kto80K.getText());
        } else {
            this.globalFilter.remove(chkIncome60Kto80K.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkIncome60Kto80KActionPerformed

    private void chkIncomeAbove80KActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIncomeAbove80KActionPerformed
        if (chkIncomeAbove80K.isSelected()) {
            this.globalFilter.add(chkIncomeAbove80K.getText());
        } else {
            this.globalFilter.remove(chkIncomeAbove80K.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkIncomeAbove80KActionPerformed

    private void chkAgeBelow20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAgeBelow20ActionPerformed
        if (chkAgeBelow20.isSelected()) {
            this.globalFilter.add(chkAgeBelow20.getText());
        } else {
            this.globalFilter.remove(chkAgeBelow20.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkAgeBelow20ActionPerformed

    private void chkAge20to40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAge20to40ActionPerformed
        if (chkAge20to40.isSelected()) {
            this.globalFilter.add(chkAge20to40.getText());
        } else {
            this.globalFilter.remove(chkAge20to40.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkAge20to40ActionPerformed

    private void chkAge40to60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAge40to60ActionPerformed
        if (chkAge40to60.isSelected()) {
            this.globalFilter.add(chkAge40to60.getText());
        } else {
            this.globalFilter.remove(chkAge40to60.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkAge40to60ActionPerformed

    private void chkAgeAbove60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAgeAbove60ActionPerformed
        if (chkAgeAbove60.isSelected()) {
            this.globalFilter.add(chkAgeAbove60.getText());
        } else {
            this.globalFilter.remove(chkAgeAbove60.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkAgeAbove60ActionPerformed

    private void chkMaritalSingleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMaritalSingleActionPerformed
        if (chkMaritalSingle.isSelected()) {
            this.globalFilter.add(chkMaritalSingle.getText());
        } else {
            this.globalFilter.remove(chkMaritalSingle.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkMaritalSingleActionPerformed

    private void chkMaritalMarriedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMaritalMarriedActionPerformed
        if (chkMaritalMarried.isSelected()) {
            this.globalFilter.add(chkMaritalMarried.getText());
        } else {
            this.globalFilter.remove(chkMaritalMarried.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkMaritalMarriedActionPerformed

    private void chkMaritalDivorcedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMaritalDivorcedActionPerformed
        if (chkMaritalDivorced.isSelected()) {
            this.globalFilter.add(chkMaritalDivorced.getText());
        } else {
            this.globalFilter.remove(chkMaritalDivorced.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkMaritalDivorcedActionPerformed

    private void chkMaritalWidowedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMaritalWidowedActionPerformed
        if (chkMaritalWidowed.isSelected()) {
            this.globalFilter.add(chkMaritalWidowed.getText());
        } else {
            this.globalFilter.remove(chkMaritalWidowed.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkMaritalWidowedActionPerformed

    private void chkMaritalCommonLawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMaritalCommonLawActionPerformed
        if (chkMaritalCommonLaw.isSelected()) {
            this.globalFilter.add(chkMaritalCommonLaw.getText());
        } else {
            this.globalFilter.remove(chkMaritalCommonLaw.getText());
        }
        
        this.updateClientList(globalFilter.toArray(new String[0]));
    }//GEN-LAST:event_chkMaritalCommonLawActionPerformed

    private void lblLastSyncMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLastSyncMouseClicked
        this.pnlLoadingFiles.setVisible(true);
    }//GEN-LAST:event_lblLastSyncMouseClicked

    private void pnlImporting3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlImporting3MouseClicked
        this.pnlImporting3.setVisible(false);
        new NewDocument(this, true, "RunAway Life Advantage", "Insurance", "Momentum Canada, Inc.").setVisible(true);
    }//GEN-LAST:event_pnlImporting3MouseClicked

    private void pnlImporting2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlImporting2MouseClicked
        this.pnlImporting2.setVisible(false);
        new NewDocument(this, true, "HealthCheck+ Critical Illness", "Insurance", "Sammy Beast Corp.").setVisible(true);
    }//GEN-LAST:event_pnlImporting2MouseClicked

    private void btnNewClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewClientActionPerformed
        new ModifyClient(this, true, null, true).setVisible(true);
    }//GEN-LAST:event_btnNewClientActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditClient;
    private javax.swing.JButton btnFilterClients;
    private javax.swing.JButton btnHideFilter;
    private javax.swing.JButton btnNewClient;
    private javax.swing.JButton btnNewDocument;
    private javax.swing.JButton btnSync;
    private javax.swing.JCheckBox chkAge20to40;
    private javax.swing.JCheckBox chkAge40to60;
    private javax.swing.JCheckBox chkAgeAbove60;
    private javax.swing.JCheckBox chkAgeBelow20;
    private javax.swing.JCheckBox chkGenderFemale;
    private javax.swing.JCheckBox chkGenderMale;
    private javax.swing.JCheckBox chkGenderOther;
    private javax.swing.JCheckBox chkIncome20Kto40K;
    private javax.swing.JCheckBox chkIncome40Kto60K;
    private javax.swing.JCheckBox chkIncome60Kto80K;
    private javax.swing.JCheckBox chkIncomeAbove80K;
    private javax.swing.JCheckBox chkIncomeBelow20K;
    private javax.swing.JCheckBox chkMaritalCommonLaw;
    private javax.swing.JCheckBox chkMaritalDivorced;
    private javax.swing.JCheckBox chkMaritalMarried;
    private javax.swing.JCheckBox chkMaritalSingle;
    private javax.swing.JCheckBox chkMaritalWidowed;
    private javax.swing.JComboBox comboSortType;
    private javax.swing.JDialog dlgFilterType;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientSince;
    private javax.swing.JLabel lblDocumentName;
    private javax.swing.JLabel lblDocumentName1;
    private javax.swing.JLabel lblDocumentName2;
    private javax.swing.JLabel lblLastSync;
    private javax.swing.JList lstClientList;
    private javax.swing.JPanel pnlClientInfo;
    private javax.swing.JPanel pnlDocumentList;
    private javax.swing.JPanel pnlDocumentView;
    private javax.swing.JPanel pnlFilter;
    private javax.swing.JPanel pnlImporting1;
    private javax.swing.JPanel pnlImporting2;
    private javax.swing.JPanel pnlImporting3;
    private javax.swing.JPanel pnlLoadingFiles;
    private javax.swing.JScrollPane scrpnlClientList;
    private javax.swing.JScrollPane scrpnlDocumentView;
    private javax.swing.JScrollPane scrpnlDocuments;
    private javax.swing.JPanel tabActive;
    private javax.swing.JPanel tabInactive;
    private javax.swing.JTabbedPane tabpnlClients;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtGender;
    private javax.swing.JTextField txtIncome;
    private javax.swing.JTextField txtMarital;
    private javax.swing.JTextField txtSearchClient;
    private javax.swing.JTextField txtSearchDocument;
    // End of variables declaration//GEN-END:variables
}
