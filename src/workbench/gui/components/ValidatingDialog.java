/*
 * ValidatingDialog.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2005, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;

import workbench.gui.WbSwingUtilities;
import workbench.gui.actions.EscAction;
import workbench.interfaces.ValidatingComponent;
import workbench.resource.ResourceMgr;


/**
 *
 * @author  support@sql-workbench.net
 */
public class ValidatingDialog
	extends JDialog
	implements WindowListener, ActionListener
{
	private ValidatingComponent validator = null;
	private JButton okButton;
	private JButton cancelButton;
	private boolean isCancelled = true;
	private EscAction esc;
	
	/** Creates a new instance of ValidatingDialog */
	public ValidatingDialog(Frame owner, String title, JPanel editor)
	{
		super(owner, title, true);
		if (!(editor instanceof ValidatingComponent))
		{
			throw new IllegalArgumentException("The supplied panel does not implement the ValidatingComponent interface");
		}
		this.validator = (ValidatingComponent)editor;
		this.okButton = new WbButton(ResourceMgr.getString("LabelOK"));
		this.okButton.addActionListener(this);
		this.cancelButton = new WbButton(ResourceMgr.getString("LabelCancel"));
		this.cancelButton.addActionListener(this);
		
		JRootPane root = this.getRootPane();
		root.setDefaultButton(okButton);		

		InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = root.getActionMap();
		this.esc = new EscAction(this);
		im.put(esc.getAccelerator(), esc.getActionName());
		am.put(esc.getActionName(), esc);

		im = editor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		am = editor.getActionMap();
		im.put(esc.getAccelerator(), esc.getActionName());
		am.put(esc.getActionName(), esc);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		Border b = BorderFactory.createEmptyBorder(10,10,10,10);
		content.setBorder(b);
		content.add(editor, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		b = BorderFactory.createEmptyBorder(2, 0, 0, 0);
		buttonPanel.setBorder(b);
		content.add(buttonPanel, BorderLayout.SOUTH);
		this.getContentPane().add(content);
		this.doLayout();
		this.pack();
		this.addWindowListener(this);
		WbSwingUtilities.center(this, owner);
	}
	
	public boolean isCancelled()
	{
		return this.isCancelled;
	}
	
	public static boolean showConfirmDialog(Frame parent, JPanel editor, String title)
	{
		ValidatingDialog dialog = new ValidatingDialog(parent, title, editor);
		dialog.setVisible(true);
		return !dialog.isCancelled();
	}
	
	private void close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	public void windowActivated(WindowEvent e)
	{
	}
	
	public void windowClosed(WindowEvent e)
	{
	}
	
	public void windowClosing(WindowEvent e)
	{
		this.close();
	}
	
	public void windowDeactivated(WindowEvent e)
	{
	}
	
	public void windowDeiconified(WindowEvent e)
	{
	}
	
	public void windowIconified(WindowEvent e)
	{
	}
	
	public void windowOpened(WindowEvent e)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				validator.componentDisplayed();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.cancelButton || e.getSource() == this.esc)
		{
			this.isCancelled = true;
			this.close();
		}
		if (e.getSource() == this.okButton)
		{
			if (this.validator.validateInput())
			{
				this.isCancelled = false;
				this.close();
			}
		}
	}
	
}
