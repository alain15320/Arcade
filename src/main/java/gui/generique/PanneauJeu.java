package gui.generique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import moteur.generique.EtatMoteur;
import moteur.generique.IGui;

public class PanneauJeu implements IPanneauJeu {

	private IGui gui;
	private JPanel panneau;
	private IAireJeu aire;
	
	private class ToucheListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (gui.getMoteur().getEtat()==EtatMoteur.ARRETE) return;
			aire.appuiTouche(e.getKeyCode());
		}
	}
	
	@SuppressWarnings("serial")
	public PanneauJeu(IGui g) {
		this.gui = g;
		panneau = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				aire.peindre(g);
			}
		};
		panneau.setFocusable(true);
		panneau.requestFocus();
		panneau.addKeyListener(new ToucheListener());
	}

	@Override
	public boolean contains(int x, int y) {
		if ((x<0 || x>aire.getNbLignes())) return false;
		if ((y<0 || y>aire.getNbColonnes())) return false;
		return true;
	}

	@Override
	public void dessineGameover(Graphics g) {
		int h = getHauteur()/5;
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, getLargeur(), getHauteur());
		g.setFont(new Font("Verdana", Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString("GAME OVER !", position(g, "GAME OVER !"), h);
		h += h;
		String s = gui.getMeilleurScore().afficherTop5();
		g.setColor(Color.GRAY);
		g.drawString("MEILLEURS SCORES", position(g, "MEILLEURS SCORES"), h);
		for (String line : s.split("\n")) {
			h += g.getFontMetrics().getHeight();
			g.drawString(line, position(g, line), h);
		}
	}
	
	private int position(Graphics g, String s) {
		return getLargeur()/2-(g.getFontMetrics().stringWidth(s))/2;
	}

	@Override
	public void remplirImageFond(String imagePath, Graphics g) {
		try {
			BufferedImage bimg = ImageIO.read(new File(imagePath));
			Graphics2D g2 = (Graphics2D) g;
			Rectangle r = new Rectangle(0, 0, 32, 32);
			g2.setPaint(new TexturePaint(bimg, r));
			Rectangle rect = new Rectangle(0, 0, getLargeur(), getHauteur());
			g2.fill(rect);
		} catch(Exception e) {}
	}

	@Override
	public IAireJeu getAirJeu() {
		return aire;
	}

	@Override
	public int getLargeur() {
		return aire.getNbColonnes()*aire.getTailleCellule();
	}

	@Override
	public int getHauteur() {
		return aire.getNbLignes()*aire.getTailleCellule();
	}

	@Override
	public void setAireJeu(IAireJeu spe) {
		aire = spe;
	}

	@Override
	public JPanel getPanneau() {
		return panneau;
	}

}
