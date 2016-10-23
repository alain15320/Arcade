package moteur.specifique.serpent;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class Serpent extends ObjetJeu {

	public static enum Direction {HAUT, BAS, DROITE, GAUCHE};
	private Direction direction;
	private List<SegmentSerpent> segments;
	
	private Image image_tete = new ImageIcon(ParamSerpent.PATH_TETE).getImage();
	private boolean majDirAttente;


	public Serpent() {
		segments = new ArrayList<SegmentSerpent>();
	}

	@Override
	public void generer() {
		segments.clear();
		int taille = ParamSerpent.LONGUEUR_INITIALE;
		int r = rand.nextInt(Direction.values().length);
		direction = Direction.values()[r];
		r = rand.nextInt(ParamSerpent.COLLONES-2*taille);
		int teteX = taille+r;
		r = rand.nextInt(ParamSerpent.COLLONES-2*taille);
		int teteY = taille+r;
		if (direction==Direction.HAUT) {
			r = rand.nextInt(ParamSerpent.LIGNES/2);
			teteY = ParamSerpent.LIGNES/2+r-taille;			
		} else if (direction==Direction.BAS) {
			r = rand.nextInt(ParamSerpent.LIGNES/2);
			teteY = r+taille;
		}  else if (direction==Direction.GAUCHE) {
			r = rand.nextInt(ParamSerpent.COLLONES/2);
			teteX = ParamSerpent.COLLONES/2+r-taille;
		} else {
			r = rand.nextInt(ParamSerpent.COLLONES/2);
			teteX = r+taille;
		}
		segments.add(new SegmentSerpent(teteX, teteY, taille, direction));
		majDirAttente = false;
	}

	@Override
	public void mettreAJour() {
		SegmentSerpent segmentTete;
		segmentTete = segments.get(0);
		segmentTete.grandir();
		majDirAttente = false;
	}

	@Override
	public void dessiner(Graphics g) {
		for (int i=0; i<segments.size(); i++)
			segments.get(i).dessiner(g);
		if (segments.size()>0) {
			int t = ParamSerpent.TAILLE_CELLULE;
			g.drawImage(image_tete, getTeteX()*t, getTeteY()*t, t, t, null);
		}
	}

	public int getTeteX() {
		return segments.get(0).getTeteX();
	}
	
	public int getTeteY() {
		return segments.get(0).getTeteY();
	}
	
	public int getLongueur() {
		int longueur = 0;
		for (SegmentSerpent seg : segments)
			longueur += seg.getLongueur();
		return longueur;
	}

	public boolean contains(int x, int y) {
		for (int i=0; i<segments.size(); i++) {
			SegmentSerpent segment = segments.get(i);
			if (segment.contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean intersects(int x, int y) {
		if (getTeteX()==x || getTeteY()==y)
			return true;
		else
			return false;
	}

	public boolean mangeLuiMeme() { // AO - Touche 4e segments ou plus
		int teteX = getTeteX();
		int teteY = getTeteY();
		for (int i=3; i<segments.size(); i++) {
			SegmentSerpent segment = segments.get(i);
			if (segment.contains(teteX, teteY));
				return true;
		}
		return false;
	}

	public void diminuer() {
		SegmentSerpent segmentQueue;
		segmentQueue = segments.get(segments.size()-1);
		segmentQueue.diminuer();
		if (segmentQueue.getLongueur()==0)
			segments.remove(segmentQueue);
	}

	public void setDirection(Direction nDir) {
		if (!majDirAttente && (nDir!=direction) &&
			((nDir==Direction.HAUT && direction!=Direction.BAS) ||
			(nDir==Direction.BAS && direction!=Direction.HAUT) ||
			(nDir==Direction.GAUCHE && direction!=Direction.DROITE) ||
			(nDir==Direction.DROITE && direction!=Direction.GAUCHE))) {
			SegmentSerpent segmentTete = segments.get(0);
			int x = segmentTete.getTeteX();
			int y = segmentTete.getTeteY();
			segments.add(0, new SegmentSerpent(x, y, 0, nDir));
			direction = nDir;
			majDirAttente = true; // AO - sera effacé après maj
		}
	}

}
