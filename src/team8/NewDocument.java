/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sam
 */
public class NewDocument extends javax.swing.JDialog {
    private boolean _has_init = false;
    private boolean _txt_had_focus = false;
    private String docName = "Samuel Beasto Term Life";
    private final Object mutex = new Object();
    
    /**
     * Creates new form NewDocument
     */
    public NewDocument(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        
        this.setModalityType(ModalityType.DOCUMENT_MODAL);
        
        Util.setFrameInMiddle(this);

        this.populateClientList(null);
    }
    
    /**
     * Initialize with parameters
     * 
     * @param parent owner of this dialog
     * @param modal whether to block windows or not
     * @param nm name of document
     * @param docType type
     * @param docProvider provider
     */
    public NewDocument(java.awt.Frame parent, boolean modal, String nm, String docType, String docProvider) {
        super(parent, modal);
        initComponents();
        addListeners();
        
        this.setModalityType(ModalityType.DOCUMENT_MODAL);
        
        Util.setFrameInMiddle(this);
        
        this.comboProvider.setSelectedItem(docProvider);
        this.comboType.setSelectedItem(docType);
        this.docName = nm;
        
        this.populateClientList(null);
    }
    
    private void addListeners() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                if (!dlgClientDropdown.isVisible()) {
                    return;
                }

                super.componentHidden(e);
                dlgClientDropdown.dispose();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if (!dlgClientDropdown.isVisible()) {
                    return;
                }

                super.componentMoved(e);
                Point p = txtSearchClient.getLocationOnScreen();
                dlgClientDropdown.setLocation(p.x, p.y);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                if (!dlgClientDropdown.isVisible()) {
                    return;
                }

                super.componentResized(e);

                Point p = txtSearchClient.getLocationOnScreen();
                dlgClientDropdown.setLocation(p.x, p.y);
                dlgClientDropdown.setSize(txtSearchClient.getWidth(), 200);
            }
        });
        
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jLabel2.requestFocus();
                dlgClientDropdown.dispose();
            }

            @Override
            public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
                jLabel2.requestFocus();
                dlgClientDropdown.dispose();
            }            
        });
    }

    /**
     * Displays this dialog on screen
     */
    public void display() {
        this._has_init = true;
        this.setVisible(true);
    }
    
    public void setDocName(String nm) {
        this.docName = nm;
    }

    private void populateClientList(String filter) {
        Client[] c = FakeDB.getClientList();

        if (filter == null) {
            filter = "";
        }

        this.lstListClients.removeAll();
        
        String[] names = new String[c.length];
        
        int ind = 0;
        
        for (int i = 0; i < c.length; i++) {
            if (!filter.equals("")
                    && !c[i].getName().toLowerCase().contains(filter)) {
                continue;
            }
            
            names[ind] = c[i].getName();
            ind++;
        }
        
        this.lstListClients.setListData(names);

        this.lstListClients.doLayout();
        this.scrpnlListClients.doLayout();
    }

    /**
     * Used to check whether the fields entered are enough
     */
    private void checkInformationSuffice() {
        if (this.txtSearchClient.getText().length() != 0
                && !this.comboAge.getSelectedItem().equals("Age")
                && !this.comboDependents.getSelectedItem().equals("Dependents")
                && !this.comboEmployment.getSelectedItem().equals("Employment")
                && !this.comboGender.getSelectedItem().equals("Gender")
                && !this.comboIncome.getSelectedItem().equals("Income")
                && !this.comboMarital.getSelectedItem().equals("Marital")
                && !this.comboProvider.getSelectedItem().equals("Provider")
                && !this.comboType.getSelectedItem().equals("Type")) {
            this.btnSubmit.setEnabled(true);
        } else {
            this.btnSubmit.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgClientDropdown = new javax.swing.JDialog();
        pnlClientDropdownContainer = new javax.swing.JPanel();
        scrpnlListClients = new javax.swing.JScrollPane();
        lstListClients = new javax.swing.JList();
        btnAddNewClient = new javax.swing.JButton();
        txtSearchInDialog = new javax.swing.JTextField();
        btnViewFullsizedDocument = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblClientInfo = new javax.swing.JLabel();
        comboGender = new javax.swing.JComboBox();
        comboAge = new javax.swing.JComboBox();
        comboDependents = new javax.swing.JComboBox();
        comboIncome = new javax.swing.JComboBox();
        comboMarital = new javax.swing.JComboBox();
        comboEmployment = new javax.swing.JComboBox();
        btnCustomizeClientAttribute = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        comboProvider = new javax.swing.JComboBox();
        comboType = new javax.swing.JComboBox();
        btnCustomizeDocumentAttribute = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaNotes = new javax.swing.JTextArea();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtSearchClient = new javax.swing.JTextField();

        dlgClientDropdown.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgClientDropdown.setAlwaysOnTop(true);
        dlgClientDropdown.setBackground(new java.awt.Color(254, 254, 254));
        dlgClientDropdown.setResizable(false);
        dlgClientDropdown.setUndecorated(true);

        pnlClientDropdownContainer.setBackground(new java.awt.Color(242, 242, 242));
        pnlClientDropdownContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lstListClients.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstListClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstListClientsMouseClicked(evt);
            }
        });
        scrpnlListClients.setViewportView(lstListClients);

        btnAddNewClient.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        btnAddNewClient.setText("+ Add New Client");
        btnAddNewClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewClientActionPerformed(evt);
            }
        });

        txtSearchInDialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchInDialogKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlClientDropdownContainerLayout = new javax.swing.GroupLayout(pnlClientDropdownContainer);
        pnlClientDropdownContainer.setLayout(pnlClientDropdownContainerLayout);
        pnlClientDropdownContainerLayout.setHorizontalGroup(
            pnlClientDropdownContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrpnlListClients, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(btnAddNewClient, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addComponent(txtSearchInDialog)
        );
        pnlClientDropdownContainerLayout.setVerticalGroup(
            pnlClientDropdownContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientDropdownContainerLayout.createSequentialGroup()
                .addComponent(txtSearchInDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrpnlListClients, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddNewClient))
        );

        javax.swing.GroupLayout dlgClientDropdownLayout = new javax.swing.GroupLayout(dlgClientDropdown.getContentPane());
        dlgClientDropdown.getContentPane().setLayout(dlgClientDropdownLayout);
        dlgClientDropdownLayout.setHorizontalGroup(
            dlgClientDropdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlClientDropdownContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dlgClientDropdownLayout.setVerticalGroup(
            dlgClientDropdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlClientDropdownContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Document");

        btnViewFullsizedDocument.setBackground(new java.awt.Color(254, 254, 254));
        btnViewFullsizedDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/team8/image/zoom.png"))); // NOI18N
        btnViewFullsizedDocument.setText("View Fullsized Document");
        btnViewFullsizedDocument.setOpaque(true);

        lblClientInfo.setText("Client Information");

        comboGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gender", "Male", "Female", "Other" }));
        comboGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGenderActionPerformed(evt);
            }
        });

        comboAge.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Age", "Below 20", "20 to 40", "40 to 60", "Above 60", "Other" }));
        comboAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAgeActionPerformed(evt);
            }
        });

        comboDependents.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dependents", "Yes", "No" }));
        comboDependents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDependentsActionPerformed(evt);
            }
        });

        comboIncome.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Income", "Below 20K", "20K to 40K", "40K to 60K", "60K to 80K", "Above 80K" }));
        comboIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboIncomeActionPerformed(evt);
            }
        });

        comboMarital.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Marital", "Single", "Married", "Widowed", "Divorced", "Common Law" }));
        comboMarital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMaritalActionPerformed(evt);
            }
        });

        comboEmployment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Employment", "Employeed", "Unemployeed" }));
        comboEmployment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmploymentActionPerformed(evt);
            }
        });

        btnCustomizeClientAttribute.setText("Customize");

        jLabel2.setText("Document Information");

        comboProvider.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Provider", "ABC Corp.", "Ahaha Foundation", "DEF Group", "Momentum Canada, Inc.", "Sammy Beast Corp.", "TD Waterhouse" }));
        comboProvider.setSelectedIndex(6);
        comboProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProviderActionPerformed(evt);
            }
        });

        comboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type", "Insurance", "Investment" }));
        comboType.setSelectedIndex(2);
        comboType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTypeActionPerformed(evt);
            }
        });

        btnCustomizeDocumentAttribute.setText("Customize");

        jLabel3.setText("Additional Notes");

        txtAreaNotes.setColumns(20);
        txtAreaNotes.setRows(5);
        txtAreaNotes.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAreaNotes);

        btnSubmit.setText("Submit");
        btnSubmit.setEnabled(false);
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtSearchClient.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchClientFocusGained(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboAge, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboDependents, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCustomizeClientAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCustomizeDocumentAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnViewFullsizedDocument)
                            .addComponent(lblClientInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(comboIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboMarital, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addComponent(comboEmployment, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewFullsizedDocument)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClientInfo)
                .addGap(6, 6, 6)
                .addComponent(txtSearchClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDependents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomizeClientAttribute))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboMarital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboEmployment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomizeDocumentAttribute))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void comboGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboGenderActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboGenderActionPerformed

    private void comboAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAgeActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboAgeActionPerformed

    private void comboDependentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDependentsActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboDependentsActionPerformed

    private void comboEmploymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmploymentActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboEmploymentActionPerformed

    private void comboMaritalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMaritalActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboMaritalActionPerformed

    private void comboIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboIncomeActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboIncomeActionPerformed

    private void comboTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTypeActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboTypeActionPerformed

    private void comboProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProviderActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboProviderActionPerformed

    private void txtSearchClientFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchClientFocusGained
        this.dlgClientDropdown.setSize(this.txtSearchClient.getWidth(), 200);
        Point p = this.txtSearchClient.getLocationOnScreen();
        this.dlgClientDropdown.setLocation(p.x, p.y);
        this.dlgClientDropdown.setAlwaysOnTop(true);

        this._txt_had_focus = true;

        this.dlgClientDropdown.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                synchronized (mutex) {                        
                    super.windowLostFocus(e);
                    jLabel2.requestFocus();
                    dlgClientDropdown.dispose();
                }
            }
        });

        this.jLabel2.requestFocus();
        this.dlgClientDropdown.setAlwaysOnTop(true);
        this.dlgClientDropdown.setVisible(true);
    }//GEN-LAST:event_txtSearchClientFocusGained

    private void txtSearchInDialogKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchInDialogKeyReleased
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            String input = this.txtSearchInDialog.getText();
            String[] s = input.split(" ");
            
            input = "";
            for (int i = 0; i < s.length; i++) {
                input = input + s[i].substring(0,1).toUpperCase() + s[i].substring(1).toLowerCase() + " ";
            }
            
            this.txtSearchClient.setText(input.trim());
            jLabel2.requestFocus();
            dlgClientDropdown.dispose();
        } else {
            populateClientList(txtSearchInDialog.getText());
        }
    }//GEN-LAST:event_txtSearchInDialogKeyReleased

    private void lstListClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstListClientsMouseClicked
        this.txtSearchClient.setText((String)this.lstListClients.getSelectedValue());
        Client c = FakeDB.getClient((String)this.lstListClients.getSelectedValue());
        
        if (c != null) {
            this.comboAge.setSelectedItem(c.getAge());
            this.comboDependents.setSelectedItem(c.getDependents());
            this.comboEmployment.setSelectedItem(c.getEmployment());
            this.comboGender.setSelectedItem(c.getGender());
            this.comboIncome.setSelectedItem(c.getIncome());
            this.comboMarital.setSelectedItem(c.getMarital());
        }
        
        checkInformationSuffice();
        this.dlgClientDropdown.dispose();
    }//GEN-LAST:event_lstListClientsMouseClicked

    private void btnAddNewClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewClientActionPerformed
        this.txtSearchClient.setText(this.txtSearchInDialog.getText());
        this.dlgClientDropdown.dispose();
    }//GEN-LAST:event_btnAddNewClientActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        Client c = FakeDB.getClient(this.txtSearchClient.getText());
        
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        
        if (c == null) {
            c = new Client(
                    this.txtSearchClient.getText(),
                    dateFormat.format(date),
                    (String)this.comboGender.getSelectedItem(),
                    (String)this.comboAge.getSelectedItem(),
                    (String)this.comboDependents.getSelectedItem(),
                    (String)this.comboIncome.getSelectedItem(),
                    (String)this.comboMarital.getSelectedItem(),
                    (String)this.comboEmployment.getSelectedItem(),
                    false
            );
            
            c.addDocument(new Document(
                    this.docName,
                    dateFormat.format(date),
                    (String)this.comboType.getSelectedItem(),
                    (String)this.comboProvider.getSelectedItem(),
                    this.txtAreaNotes.getText()
            ));
            
            FakeDB.addClient(c);
        } else {
            FakeDB.addDocument(c.getName(), new Document(
                    this.docName,
                    dateFormat.format(date),
                    (String)this.comboType.getSelectedItem(),
                    (String)this.comboProvider.getSelectedItem(),
                    this.txtAreaNotes.getText()
            ));
        }
        
        Dashboard.getInstance().updateClientList();
        this.dispose();
    }//GEN-LAST:event_btnSubmitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewClient;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCustomizeClientAttribute;
    private javax.swing.JButton btnCustomizeDocumentAttribute;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnViewFullsizedDocument;
    private javax.swing.JComboBox comboAge;
    private javax.swing.JComboBox comboDependents;
    private javax.swing.JComboBox comboEmployment;
    private javax.swing.JComboBox comboGender;
    private javax.swing.JComboBox comboIncome;
    private javax.swing.JComboBox comboMarital;
    private javax.swing.JComboBox comboProvider;
    private javax.swing.JComboBox comboType;
    private javax.swing.JDialog dlgClientDropdown;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblClientInfo;
    private javax.swing.JList lstListClients;
    private javax.swing.JPanel pnlClientDropdownContainer;
    private javax.swing.JScrollPane scrpnlListClients;
    private javax.swing.JTextArea txtAreaNotes;
    private javax.swing.JTextField txtSearchClient;
    private javax.swing.JTextField txtSearchInDialog;
    // End of variables declaration//GEN-END:variables
}
