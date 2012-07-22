/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team8;

import javax.swing.JTextField;

/**
 *
 * @author sam
 */
public class NewDocument extends javax.swing.JDialog {
    private boolean _has_init = false;
    /**
     * Creates new form NewDocument
     */
    public NewDocument(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Util.setFrameInMiddle(this);

        this.populateClientList(null);
    }

    public void display() {
        this._has_init = true;
        this.setVisible(true);
    }

    private void populateClientList(String filter) {
        Client[] c = FakeDB.getClientList();

        if (filter == null) {
            filter = "";
        }

        this.comboName.removeAllItems();

        this.comboName.addItem("");
        
        for (int i = 0; i < c.length; i++) {
            if (!filter.equals("")
                    && !c[i].getName().toLowerCase().contains(filter)) {
                continue;
            }
            this.comboName.addItem(c[i].getName());
        }

        this.comboName.doLayout();
        this.comboName.repaint();
    }

    private void checkInformationSuffice() {
        if (this.comboName.getSelectedIndex() != -1
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
        comboName = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Document");

        btnViewFullsizedDocument.setText("View Fullsized Document");

        lblClientInfo.setText("Client Information");

        comboGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gender", "Male", "Female" }));
        comboGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboGenderActionPerformed(evt);
            }
        });

        comboAge.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Age", "Below 20", "20 to 40", "40 to 60", "Above 60" }));
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
        comboProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProviderActionPerformed(evt);
            }
        });

        comboType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Type", "Insurance", "Investment" }));
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

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        comboName.setEditable(true);
        comboName.setAutoscrolls(true);
        comboName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboNameActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
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
                            .addComponent(comboName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void comboNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboNameActionPerformed
        checkInformationSuffice();
    }//GEN-LAST:event_comboNameActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JComboBox comboName;
    private javax.swing.JComboBox comboProvider;
    private javax.swing.JComboBox comboType;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblClientInfo;
    private javax.swing.JTextArea txtAreaNotes;
    // End of variables declaration//GEN-END:variables
}
