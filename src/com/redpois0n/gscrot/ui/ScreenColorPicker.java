package com.redpois0n.gscrot.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TrayIcon.MessageType;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.redpois0n.gscrot.ClipboardHelper;
import com.redpois0n.gscrot.TrayIconHelper;

@SuppressWarnings("serial")
public class ScreenColorPicker extends RegionCapture {
	
	private Color selected = null;

	public ScreenColorPicker(Rectangle rect, BufferedImage image) {
		super(rect, image);
	}
	
	/**
	 * Copy selected pixel color to clipboard in format "red, green, blue"
	 */
	@Override
	public void submit() {
		if (selected != null) {
			String s = selected.getRed() + ", " + selected.getGreen() + ", " + selected.getBlue();
			ClipboardHelper.setString(s);
			
			close();
			
			TrayIconHelper.showMessage(s + " copied to clipboard", MessageType.INFO);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (selected != null) {
			g.setColor(selected);
			g.fillRect(x - 50, y - 50, 40, 40);
			
			g.setColor(Color.black);
			g.drawRect(x - 50, y - 50, 40, 40);
		}
	}
	
	/**
	 * When mouse is pressed, get selected color at clicked pixel
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		int array[] = new int[1];
		image.getRGB(x, y, 1, 1, array, 0, 1);

		selected = new Color(array[0]);
	}
	
	/**
	 * Disable dragging by overriding mouseDragged()
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			submit();
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE && selected == null) {
			close();
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			selected = null;
		}
		
		repaint();
	}

}
