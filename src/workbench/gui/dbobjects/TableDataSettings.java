/*
 * TableDataSettings.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2012, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.dbobjects;

import java.awt.event.ActionListener;

import workbench.resource.ResourceMgr;
import workbench.util.StringUtil;

/**
 *
 * @author Thomas Kellerer
 */
public class TableDataSettings
	extends javax.swing.JPanel
	implements ActionListener
{

	public TableDataSettings()
	{
		super();
		initComponents();
		this.checkBoxEnableWarning.addActionListener(this);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    checkBoxEnableWarning = new javax.swing.JCheckBox();
    autoloadRowCount = new javax.swing.JCheckBox();
    autoloadData = new javax.swing.JCheckBox();
    thresholdLabel = new javax.swing.JLabel();
    textFieldThresholdValue = new javax.swing.JTextField();
    jPanel1 = new javax.swing.JPanel();

    setLayout(new java.awt.GridBagLayout());

    checkBoxEnableWarning.setText(ResourceMgr.getString("LblEnableDataThresholdWarning"));
    checkBoxEnableWarning.setToolTipText(ResourceMgr.getDescription("LblEnableDataThresholdWarning"));
    checkBoxEnableWarning.setMargin(new java.awt.Insets(0, 0, 0, 0));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    add(checkBoxEnableWarning, gridBagConstraints);

    autoloadRowCount.setText(ResourceMgr.getString("LblAutoLoadRowCount"));
    autoloadRowCount.setToolTipText(ResourceMgr.getDescription("LblAutoLoadRowCount"));
    autoloadRowCount.setMargin(new java.awt.Insets(0, 0, 0, 0));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
    add(autoloadRowCount, gridBagConstraints);

    autoloadData.setText(ResourceMgr.getString("LblAutoLoadTableData"));
    autoloadData.setToolTipText(ResourceMgr.getDescription("LblAutoLoadTableData"));
    autoloadData.setMargin(new java.awt.Insets(0, 0, 0, 0));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
    add(autoloadData, gridBagConstraints);

    thresholdLabel.setText(ResourceMgr.getString("LblThresholdLevel"));
    thresholdLabel.setToolTipText(ResourceMgr.getDescription("LblThresholdLevel"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    add(thresholdLabel, gridBagConstraints);

    textFieldThresholdValue.setColumns(8);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 9);
    add(textFieldThresholdValue, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weighty = 1.0;
    add(jPanel1, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

	public int getThresholdValue()
	{
		if (this.checkBoxEnableWarning.isSelected())
		{
			return StringUtil.getIntValue(this.textFieldThresholdValue.getText(), -1);
		}
		else
		{
			return -1;
		}
	}

	public void setAutoloadRowCount(boolean flag) { this.autoloadRowCount.setSelected(flag); }
	public boolean getAutoloadRowCount() { return this.autoloadRowCount.isSelected(); }

	public void setAutoloadData(boolean flag) { this.autoloadData.setSelected(flag); }
	public boolean getAutoloadData() { return this.autoloadData.isSelected(); }

	public void setThresholdValue(int aValue)
	{
		this.checkBoxEnableWarning.setSelected(aValue > 0);
		this.textFieldThresholdValue.setEnabled(aValue > 0);
		this.thresholdLabel.setEnabled(aValue > 0);
		if (aValue <= 0)
		{
			this.textFieldThresholdValue.setText("");
		}
		else
		{
			this.textFieldThresholdValue.setText(Integer.toString(aValue));
		}
	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		if (e.getSource() == this.checkBoxEnableWarning)
		{
			this.textFieldThresholdValue.setEnabled(this.checkBoxEnableWarning.isSelected());
			this.thresholdLabel.setEnabled(this.checkBoxEnableWarning.isSelected());
		}
	}

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox autoloadData;
  private javax.swing.JCheckBox autoloadRowCount;
  private javax.swing.JCheckBox checkBoxEnableWarning;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextField textFieldThresholdValue;
  private javax.swing.JLabel thresholdLabel;
  // End of variables declaration//GEN-END:variables

}
