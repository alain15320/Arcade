package moteur.specifique.serpent;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class SegmentSerpent extends ObjetJeu {

	private Image icorps = new ImageIcon(ParamSerpent.PATH_CORPS).getImage();
	private Serpent.Direction direction;
	private int longueur;

	public SegmentSerpent(int tX, int tY, int l, Serpent.Direction d) {
		x = tX;
		y = tY;
		direction = d;
		longueur = l;
	}

	@Override
	public void generer() {}

	@Override
	public void mettreAJour() {}
	
	public void grandir() {
		switch (direction) {
		case GAUCHE:
			x--;
			break;
		case DROITE:
			x++;
			break;
		case HAUT:
			y--;
			break;
		case BAS:
			y++;
			break;
		}
	}
	
	public void diminuer() {
		longueur--;
	}

	@Override
	public void dessiner(Graphics g) {
		int x = this.x;
		int y = this.y;
		int t = ParamSerpent.TAILLE_CELLULE;
		switch (direction) {
		case GAUCHE:
			for (int i=0; i<longueur; i++) {
				g.drawImage(icorps, x*t, y*t, t, t, null);
				x++;
			}
			break;
		case DROITE:
			for (int i=0; i<longueur; i++) {
				g.drawImage(icorps, x*t, y*t, t, t, null);
				x--;
			}
			break;
		case HAUT:
			for (int i=0; i<longueur; i++) {
				g.drawImage(icorps, x*t, y*t, t, t, null);
				y++;
			}
			break;
		case BAS:
			for (int i=0; i<longueur; i++) {
				g.drawImage(icorps, x*t, y*t, t, t, null);
				y--;
			}
			break;
		}
	}

	public int getTeteX() {
		return x;
	}

	public int getTeteY() {
		return y;
	}

	private int getQueueX() {
		if (direction==Serpent.Direction.GAUCHE)
			return x+longueur-1;
		else if (direction==Serpent.Direction.DROITE)
			return x-longueur+1;
		else
			return x;
	}

	private int getQueueY() {
		if (direction==Serpent.Direction.BAS)
			return y-longueur+1;
		else if (direction==Serpent.Direction.HAUT)
			return y+longueur-1;
		else
			return y;
	}
	
	public boolean contains(int x, int y) {
		switch (direction) {
		case GAUCHE:
			return ((y==this.y) && ((x>=this.x) && (x<=getQueueX())));
		case DROITE:
			return ((y==this.y) && ((x<=this.x) && (x>=getQueueX())));
		case HAUT:
			return ((x==this.y) && ((y>=this.x) && (y<=getQueueY())));
		case BAS:
			return ((x==this.y) && ((y<=this.x) && (y>=getQueueY())));
		}
		return false;
	}

	public int getLongueur() {
		return longueur;
	}


}
