package gui.generique;

import java.awt.Graphics;

import moteur.generique.IGui;
import moteur.generique.IMoteur;

public interface IAireJeu {
	int getNbLignes();
	int getNbColonnes();
	int getTailleCellule();
	void peindre(Graphics g);
	void appuiTouche(int code);
	void setGui(IGui j);
	void setMoteur(IMoteur moteur);
}
