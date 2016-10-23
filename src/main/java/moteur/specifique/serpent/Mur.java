package moteur.specifique.serpent;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class Mur extends ObjetJeu {

	static Image image = new ImageIcon(ParamSerpent.PATH_MUR).getImage();

	@Override
	public void generer() {
		x = rand.nextInt(ParamSerpent.COLLONES-2);
		y = rand.nextInt(ParamSerpent.LIGNES-2);
	}

	@Override
	public void mettreAJour() {}

	@Override
	public void dessiner(Graphics g) {
		int t = ParamSerpent.TAILLE_CELLULE;
		g.drawImage(image, x*t, y*t, t, t, null);
	}

}
