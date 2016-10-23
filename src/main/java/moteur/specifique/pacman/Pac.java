package moteur.specifique.pacman;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class Pac extends ObjetJeu {

	public enum Direction {HAUT, BAS, DROITE, GAUCHE};
	private Direction dir;
	private static Image pacd = new ImageIcon(ParamPacMan.PATH_PACD).getImage();
	private static Image pacb = new ImageIcon(ParamPacMan.PATH_PACG).getImage();
	private static Image pach = new ImageIcon(ParamPacMan.PATH_PACH).getImage();
	private static Image pacg = new ImageIcon(ParamPacMan.PATH_PACB).getImage();

	
	@Override
	public void generer() {
		dir = Direction.DROITE;
	}

	@Override
	public void mettreAJour() {}

	@Override
	public void dessiner(Graphics g) {
		int t = ParamPacMan.TAILLE_CELLULE;
		switch (dir) {
		case DROITE:
			g.drawImage(pacd, getX()*t, getY()*t, t, t, null);
			break;
		case GAUCHE:
			g.drawImage(pacg, getX()*t, getY()*t, t, t, null);
			break;
		case HAUT:
			g.drawImage(pach, getX()*t, getY()*t, t, t, null);
			break;
		case BAS:
			g.drawImage(pacb, getX()*t, getY()*t, t, t, null);
			break;}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setDirection(Pac.Direction nDir) {
		dir = nDir;
		Labyrinthe.CELLULE[][] e=((MoteurPacMan)mots).getLabyrinthe().getEtat();
		Labyrinthe.CELLULE m = Labyrinthe.CELLULE.MUR;
		Labyrinthe.CELLULE p = Labyrinthe.CELLULE.PORTE_O;
		switch (nDir) {
		case DROITE:
			if (e[x+1][y]!=m) x = x + 1;
			break;
		case GAUCHE:
			if (e[x-1][y]!=m) x = x - 1;
			break;
		case HAUT:
			if (e[x][y-1]!=m) y = y - 1;
			break;
		case BAS:
			if (e[x][y+1]!=m && (e[x][y+1]!=p || e[x][y+1]!=p)) y = y + 1;
			break;
		}
	}

}
