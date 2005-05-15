/*
 * XmlOptionsPanel.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2005, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.dialogs.dataimport;

import workbench.resource.ResourceMgr;
import workbench.resource.Settings;
import workbench.gui.dialogs.export.XmlOptions;

/**
 *
 * @author  support@sql-workbench.net
 */
public class XmlOptionsPanel 
	extends javax.swing.JPanel
	implements XmlImportOptions
{
	
	public XmlOptionsPanel()
	{
		initComponents();
	}

	public void saveSettings()
	{
		Settings s = Settings.getInstance();
		s.setBoolProperty("workbench.export.xml.verbosexml", this.getUseVerboseXml());
	}
	
	public void restoreSettings()
	{
		Settings s = Settings.getInstance();
		this.setUseVerboseXml(s.getBoolProperty("workbench.export.xml.verbosexml", true));
	}
	
	public boolean getUseVerboseXml()
	{
		return this.verboseXmlCheckBox.isSelected();
	}
	
	public void setUseVerboseXml(boolean flag)
	{
		this.verboseXmlCheckBox.setSelected(flag);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    verboseXmlCheckBox = new javax.swing.JCheckBox();

    setLayout(new java.awt.GridBagLayout());

    verboseXmlCheckBox.setText(ResourceMgr.getString("LabelExportVerboseXml"));
    verboseXmlCheckBox.setToolTipText(ResourceMgr.getDescription("LabelExportVerboseXml"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    add(verboseXmlCheckBox, gridBagConstraints);

  }
  // </editor-fold>//GEN-END:initComponents
	
	
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox verboseXmlCheckBox;
  // End of variables declaration//GEN-END:variables
	
}
