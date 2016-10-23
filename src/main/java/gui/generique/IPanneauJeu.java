package gui.generique;

import java.awt.Graphics;

import javax.swing.JPanel;

public interface IPanneauJeu {
	boolean contains(int x, int y);
	void dessineGameover(Graphics g);
	void remplirImageFond(String s, Graphics g);
	IAireJeu getAirJeu();
	int getLargeur();
	int getHauteur();
	void setAireJeu(IAireJeu spe);
	JPanel getPanneau();
}
