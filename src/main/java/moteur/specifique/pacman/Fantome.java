package moteur.specifique.pacman;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class Fantome extends ObjetJeu {

	private enum DIR {HAUT, BAS, DROITE, GAUCHE};
	private DIR direction;
	private int vitesse;
	private boolean effraye;
	private boolean enBoite;
	private Image fant = new ImageIcon(ParamPacMan.PATH_FANTOME).getImage();
	private Image fantEff = new ImageIcon(ParamPacMan.PATH_FANTOME_EFFRAYE).getImage();
	private int nbCycles;

	
	public Fantome() {
		vitesse = ParamPacMan.VITESSE_FANTOME;
	}

	@Override
	public void generer() {
		effraye = false;
		enBoite = true;
		nbCycles = 0;
	}

	@Override
	public void mettreAJour() {
		if (x==-1 && y==-1) {
			return;
		}
		nbCycles++;
		if (nbCycles%vitesse != 0) {
			return;
		} else {
			nbCycles = 0;
		}
		Labyrinthe laby = ((MoteurPacMan)mots).getLabyrinthe();
		Labyrinthe.CELLULE[][] e = laby.getEtat();
		boolean porte = ((MoteurPacMan)mots).getPorteOuverte();
		int px = laby.getPorteX();
		int py = laby.getPorteY();
		Labyrinthe.CELLULE b = Labyrinthe.CELLULE.BLANCHE;
		Labyrinthe.CELLULE p = Labyrinthe.CELLULE.POINT;
		if (enBoite && porte && x==px && y==py+1 && (e[px][py-1]==b || e[px][py-1]==p)) {
			sortirBoite(px, py);
		}
		if (enBoite && x<px && e[x+1][y]==b && laby.celluleVide(x+1, y)) {
			x = x+1;
		}
		if (enBoite && x>px && e[x-1][y]==b && laby.celluleVide(x-1, y)) {
			x = x-1;
		}
		if (enBoite && y<py && e[x][y-1]==b && laby.celluleVide(x, y-1)) {
			y = y-1;
		}
		if (!enBoite) {
			seDeplacer();
		}
	}

	private void sortirBoite(int px, int py) {
		x = px;
		y = py-2;
		enBoite = false;
		direction = choisirDir();
	}

	private void seDeplacer() {
		direction = choisirDir();
		if (direction == DIR.HAUT) y = y-1;
		else if (direction == DIR.BAS) y = y+1;
		else if (direction == DIR.GAUCHE) x = x-1;
		else x = x+1;
	}
	
	private DIR choisirDir() {
		ArrayList<DIR> possible = new ArrayList<DIR>();
		Labyrinthe labyrinthe = ((MoteurPacMan)mots).getLabyrinthe();
		Labyrinthe.CELLULE[][] etat = labyrinthe.getEtat();
		Labyrinthe.CELLULE m = Labyrinthe.CELLULE.MUR;
		Labyrinthe.CELLULE p = Labyrinthe.CELLULE.PORTE_O;
		if (etat[x][y+1]!=m && etat[x][y+1]!=p && direction!=DIR.HAUT) possible.add(DIR.BAS);
		if (etat[x][y-1]!=m && etat[x][y+1]!=p && direction!=DIR.BAS) possible.add(DIR.HAUT);
		if (etat[x-1][y]!=m && etat[x][y+1]!=p && direction!=DIR.DROITE) possible.add(DIR.GAUCHE);
		if (etat[x+1][y]!=m && etat[x][y+1]!=p && direction!=DIR.GAUCHE) possible.add(DIR.DROITE);
		if (possible.size()==0) return DIR.DROITE;
		int dir = rand.nextInt(possible.size());
		return direction = possible.get(dir);
	}

	@Override
	public void dessiner(Graphics g) {
		int t = ParamPacMan.TAILLE_CELLULE;
		if (!effraye) {
			g.drawImage(fant, getX()*t, getY()*t, t, t, null);
		} else {
			g.drawImage(fantEff, getX()*t, getY()*t, t, t, null);
		}
	}

	public void acceler() {
		if (vitesse>ParamPacMan.VITESSE_FANTOME/6)
			vitesse -= ParamPacMan.VITESSE_FANTOME/6;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean estEffraye() {
		return effraye;
	}
	public void setEffraye(boolean b) {
		effraye = b;
	}

}
