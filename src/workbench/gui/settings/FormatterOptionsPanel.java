/*
 * FormatterOptionsPanel.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2012, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.settings;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import workbench.interfaces.Restoreable;
import workbench.resource.ResourceMgr;
import workbench.resource.Settings;
import workbench.util.StringUtil;

/**
 *
 * @author  Thomas Kellerer
 */
public class FormatterOptionsPanel
	extends JPanel
	implements Restoreable, ActionListener
{
	public FormatterOptionsPanel()
	{
		super();
		initComponents();
	}

	@Override
	public void restoreSettings()
	{
		funcsLower.setSelected(Settings.getInstance().getFormatterLowercaseFunctions());
		insertColumns.setText(Integer.toString(Settings.getInstance().getFormatterMaxColumnsInInsert()));
		updateColumns.setText(Integer.toString(Settings.getInstance().getFormatterMaxColumnsInUpdate()));
		keywordsUpper.setSelected(Settings.getInstance().getFormatterUpperCaseKeywords());
		spaceAfterComma.setSelected(Settings.getInstance().getFormatterAddSpaceAfterComma());
		commaAfterLineBreak.setSelected(Settings.getInstance().getFormatterCommaAfterLineBreak());
		addSpaceAfterLineBreakComma.setSelected(Settings.getInstance().getFormatterAddSpaceAfterLineBreakComma());
		addSpaceAfterLineBreakComma.setEnabled(commaAfterLineBreak.isSelected());
	}

	@Override
	public void saveSettings()
	{
		Settings set = Settings.getInstance();
		set.setMaxNumInListElements(StringUtil.getIntValue(maxNumElements.getText(),-1));
		set.setMaxCharInListElements(StringUtil.getIntValue(maxCharElements.getText(),-1));
		set.setFormatterMaxSubselectLength(StringUtil.getIntValue(subselectMaxLength.getText(),60));
		set.setFormatterMaxColumnsInSelect(StringUtil.getIntValue(selectColumns.getText(),1));
		set.setFormatterLowercaseFunctions(funcsLower.isSelected());
		set.setFormatterMaxColumnsInInsert(StringUtil.getIntValue(insertColumns.getText(),1));
		set.setFormatterMaxColumnsInUpdate(StringUtil.getIntValue(updateColumns.getText(),1));
		set.setFormatterUpperCaseKeywords(keywordsUpper.isSelected());
		set.setFormatterAddSpaceAfterComma(spaceAfterComma.isSelected());
		set.setFormatterCommaAfterLineBreak(commaAfterLineBreak.isSelected());
		set.setFormatterAddSpaceAfterLineBreakComma(addSpaceAfterLineBreakComma.isSelected());
	}
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    subselectMaxLabel = new javax.swing.JLabel();
    subselectMaxLength = new javax.swing.JTextField();
    maxCharElementsLabel = new javax.swing.JLabel();
    maxCharElements = new javax.swing.JTextField();
    maxNumElementsLabel = new javax.swing.JLabel();
    maxNumElements = new javax.swing.JTextField();
    selectColumns = new javax.swing.JTextField();
    selectColumnsLabel = new javax.swing.JLabel();
    funcsLower = new javax.swing.JCheckBox();
    insertColumnsLabel = new javax.swing.JLabel();
    insertColumns = new javax.swing.JTextField();
    updateColumns = new javax.swing.JTextField();
    updateColumnsLabel = new javax.swing.JLabel();
    keywordsUpper = new javax.swing.JCheckBox();
    spaceAfterComma = new javax.swing.JCheckBox();
    addSpaceAfterLineBreakComma = new javax.swing.JCheckBox();
    commaAfterLineBreak = new javax.swing.JCheckBox();

    setLayout(new java.awt.GridBagLayout());

    subselectMaxLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    subselectMaxLabel.setText(ResourceMgr.getString("LblMaxSub")); // NOI18N
    subselectMaxLabel.setToolTipText(ResourceMgr.getDescription("LblMaxSub"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(subselectMaxLabel, gridBagConstraints);

    subselectMaxLength.setText(Integer.toString(Settings.getInstance().getFormatterMaxSubselectLength()));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(10, 9, 0, 15);
    add(subselectMaxLength, gridBagConstraints);

    maxCharElementsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    maxCharElementsLabel.setText(ResourceMgr.getString("LblMaxCharEle")); // NOI18N
    maxCharElementsLabel.setToolTipText(ResourceMgr.getDescription("LblMaxCharEle"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
    add(maxCharElementsLabel, gridBagConstraints);

    maxCharElements.setText(Integer.toString(Settings.getInstance().getMaxCharInListElements()));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 9, 0, 15);
    add(maxCharElements, gridBagConstraints);

    maxNumElementsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    maxNumElementsLabel.setText(ResourceMgr.getString("LblMaxNumEle")); // NOI18N
    maxNumElementsLabel.setToolTipText(ResourceMgr.getDescription("LblMaxNumEle"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
    add(maxNumElementsLabel, gridBagConstraints);

    maxNumElements.setText(Integer.toString(Settings.getInstance().getMaxNumInListElements()));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 9, 0, 15);
    add(maxNumElements, gridBagConstraints);

    selectColumns.setText(Integer.toString(Settings.getInstance().getFormatterMaxColumnsInSelect()));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 9, 0, 15);
    add(selectColumns, gridBagConstraints);

    selectColumnsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    selectColumnsLabel.setText(ResourceMgr.getString("LblSqlFmtColNum")); // NOI18N
    selectColumnsLabel.setToolTipText(ResourceMgr.getDescription("LblSqlFmtColNum"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
    add(selectColumnsLabel, gridBagConstraints);

    funcsLower.setText(ResourceMgr.getString("LblFmtFuncLower")); // NOI18N
    funcsLower.setToolTipText(ResourceMgr.getString("d_LblFmtFuncLower")); // NOI18N
    funcsLower.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(funcsLower, gridBagConstraints);

    insertColumnsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    insertColumnsLabel.setText(ResourceMgr.getString("LblSqlFmtColNumIns")); // NOI18N
    insertColumnsLabel.setToolTipText(ResourceMgr.getDescription("LblSqlFmtColNumIns"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
    add(insertColumnsLabel, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 9, 0, 15);
    add(insertColumns, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 9, 0, 15);
    add(updateColumns, gridBagConstraints);

    updateColumnsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    updateColumnsLabel.setText(ResourceMgr.getString("LblSqlFmtColNumUpd")); // NOI18N
    updateColumnsLabel.setToolTipText(ResourceMgr.getDescription("LblSqlFmtColNumUpd"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
    add(updateColumnsLabel, gridBagConstraints);

    keywordsUpper.setText(ResourceMgr.getString("LblFmtKeyWordUp")); // NOI18N
    keywordsUpper.setToolTipText(ResourceMgr.getString("d_LblFmtKeyWordUp")); // NOI18N
    keywordsUpper.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(keywordsUpper, gridBagConstraints);

    spaceAfterComma.setText(ResourceMgr.getString("LblSpaceAfterComma")); // NOI18N
    spaceAfterComma.setToolTipText(ResourceMgr.getString("d_LblSpaceAfterComma")); // NOI18N
    spaceAfterComma.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(spaceAfterComma, gridBagConstraints);

    addSpaceAfterLineBreakComma.setText(ResourceMgr.getString("LblSpaceAfterLineBreakComma")); // NOI18N
    addSpaceAfterLineBreakComma.setToolTipText(ResourceMgr.getString("d_LblSpaceAfterLineBreakComma")); // NOI18N
    addSpaceAfterLineBreakComma.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(addSpaceAfterLineBreakComma, gridBagConstraints);

    commaAfterLineBreak.setText(ResourceMgr.getString("LblCommaAfterLineBreak")); // NOI18N
    commaAfterLineBreak.setToolTipText(ResourceMgr.getString("d_LblCommaAfterLineBreak")); // NOI18N
    commaAfterLineBreak.setBorder(null);
    commaAfterLineBreak.addActionListener(this);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 9;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
    add(commaAfterLineBreak, gridBagConstraints);
  }

  // Code for dispatching events from components to event handlers.

  public void actionPerformed(java.awt.event.ActionEvent evt) {
    if (evt.getSource() == commaAfterLineBreak) {
      FormatterOptionsPanel.this.commaAfterLineBreakActionPerformed(evt);
    }
  }// </editor-fold>//GEN-END:initComponents

	private void commaAfterLineBreakActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_commaAfterLineBreakActionPerformed
	{//GEN-HEADEREND:event_commaAfterLineBreakActionPerformed
		addSpaceAfterLineBreakComma.setEnabled(commaAfterLineBreak.isSelected());
	}//GEN-LAST:event_commaAfterLineBreakActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox addSpaceAfterLineBreakComma;
  private javax.swing.JCheckBox commaAfterLineBreak;
  private javax.swing.JCheckBox funcsLower;
  private javax.swing.JTextField insertColumns;
  private javax.swing.JLabel insertColumnsLabel;
  private javax.swing.JCheckBox keywordsUpper;
  private javax.swing.JTextField maxCharElements;
  private javax.swing.JLabel maxCharElementsLabel;
  private javax.swing.JTextField maxNumElements;
  private javax.swing.JLabel maxNumElementsLabel;
  private javax.swing.JTextField selectColumns;
  private javax.swing.JLabel selectColumnsLabel;
  private javax.swing.JCheckBox spaceAfterComma;
  private javax.swing.JLabel subselectMaxLabel;
  private javax.swing.JTextField subselectMaxLength;
  private javax.swing.JTextField updateColumns;
  private javax.swing.JLabel updateColumnsLabel;
  // End of variables declaration//GEN-END:variables

}
