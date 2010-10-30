/*
 * GeneralExportOptionsPanel.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2010, Thomas Kellerer
 * No part of this code may be reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.dialogs.export;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import workbench.gui.components.EncodingPanel;
import workbench.resource.ResourceMgr;
import workbench.resource.Settings;

/**
 *
 * @author  Thomas Kellerer
 */
public class GeneralExportOptionsPanel
	extends JPanel
	implements ExportOptions
{

	public GeneralExportOptionsPanel()
	{
		super();
		initComponents();
	}

	public void saveSettings()
	{
		Settings s = Settings.getInstance();
		s.setProperty("workbench.export.general.dateformat", this.getDateFormat());
		s.setProperty("workbench.export.general.timestampformat", this.getTimestampFormat());
		s.setProperty("workbench.export.general.encoding", this.getEncoding());
	}

	public void restoreSettings()
	{
		Settings s = Settings.getInstance();
		this.setDateFormat(s.getProperty("workbench.export.general.dateformat", ""));
		this.setTimestampFormat(s.getProperty("workbench.export.general.timestampformat", ""));
		this.setEncoding(s.getProperty("workbench.export.general.encoding", s.getDefaultDataEncoding()));
	}

	public String getDateFormat()
	{
		return this.dateFormat.getText();
	}

	public String getEncoding()
	{
		return encodingPanel.getEncoding();
	}

	public String getTimestampFormat()
	{
		return this.timestampFormat.getText();
	}

	public void setDateFormat(String format)
	{
		dateFormat.setText(format);
	}

	public void setEncoding(String enc)
	{
		encodingPanel.setEncoding(enc);
	}

	public void setTimestampFormat(String format)
	{
		timestampFormat.setText(format);
	}

	public void showRetrieveColumnsLabel()
	{
		selectColumnsButton.setText(ResourceMgr.getString("LblRetrieveColumns"));
	}

	public void showSelectColumnsLabel()
	{
		selectColumnsButton.setText(ResourceMgr.getString("LblSelectColumns"));
	}
	public void allowSelectColumns(boolean flag)
	{
		this.selectColumnsButton.setEnabled(flag);
	}

	public Object addColumnSelectListener(ActionListener l)
	{
		this.selectColumnsButton.addActionListener(l);
		return this.selectColumnsButton;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
		GridBagConstraints gridBagConstraints;

    encodingPanel = new EncodingPanel();
    dateFormatLabel = new JLabel();
    dateFormat = new JTextField();
    timestampFormatLabel = new JLabel();
    timestampFormat = new JTextField();
    jPanel1 = new JPanel();
    selectColumnsButton = new JButton();

    setLayout(new GridBagLayout());
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new Insets(0, 5, 0, 4);
    add(encodingPanel, gridBagConstraints);

    dateFormatLabel.setText(ResourceMgr.getString("LblDateFormat")); // NOI18N
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.insets = new Insets(12, 6, 0, 0);
    add(dateFormatLabel, gridBagConstraints);
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.insets = new Insets(8, 4, 0, 4);
    add(dateFormat, gridBagConstraints);

    timestampFormatLabel.setText(ResourceMgr.getString("LblTimestampFormat")); // NOI18N
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.insets = new Insets(4, 6, 0, 0);
    add(timestampFormatLabel, gridBagConstraints);
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.insets = new Insets(0, 4, 0, 4);
    add(timestampFormat, gridBagConstraints);
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    add(jPanel1, gridBagConstraints);

    selectColumnsButton.setText(ResourceMgr.getString("LblSelectColumns")); // NOI18N
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = GridBagConstraints.WEST;
    gridBagConstraints.insets = new Insets(8, 4, 0, 4);
    add(selectColumnsButton, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JTextField dateFormat;
  private JLabel dateFormatLabel;
  private EncodingPanel encodingPanel;
  private JPanel jPanel1;
  private JButton selectColumnsButton;
  private JTextField timestampFormat;
  private JLabel timestampFormatLabel;
  // End of variables declaration//GEN-END:variables

}
