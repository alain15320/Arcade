package gui.generique.serpent;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gui.generique.AireJeuAbstrait;
import moteur.generique.EtatMoteur;

public class AireJeuSerpent extends AireJeuAbstrait {

	@Override
	public void peindre(Graphics g) {
		gui.setImageFondPanneauJeu(getImageFond(), g);
		if (moteur.getEtat()!=EtatMoteur.GAMEOVER) moteur.dessine(g);
		else gui.dessineGameover(g);
	}

	@Override
	public void appuiTouche(int code) {
		switch (code) {
			case KeyEvent.VK_UP:
				moteur.toucheHaut();
				break;
			case KeyEvent.VK_DOWN:
				moteur.toucheBas();
				break;
			case KeyEvent.VK_LEFT:
				moteur.toucheGauche();
				break;
			case KeyEvent.VK_RIGHT:
				moteur.toucheDroite();
				break;
		}
	}

}
