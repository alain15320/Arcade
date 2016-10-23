package moteur.specifique.pacman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import moteur.generique.ObjetJeu;

public class Labyrinthe extends ObjetJeu {
	
	public enum CELLULE {BLANCHE, MUR, POINT, POINT_PUISS, PORTE_F, PORTE_O}
	private CELLULE[][] etat;
	private ArrayList<Point> zoneDepart;
	private int initial_loc_pac_X;
	private int initial_loc_pac_Y;
	private Image blanche = new ImageIcon(ParamPacMan.PATH_BLANCHE).getImage();
	private Image mur = new ImageIcon(ParamPacMan.PATH_MUR).getImage();
	private Image point = new ImageIcon(ParamPacMan.PATH_POINT).getImage();
	private Image point_p = new ImageIcon(ParamPacMan.PATH_POINT_PUISSANCE).getImage();
	private Image porte_f = new ImageIcon(ParamPacMan.PATH_PORTE_OUVERTE).getImage();
	private Image porte_o = new ImageIcon(ParamPacMan.PATH_PORTE_FERMEE).getImage();

	public Labyrinthe() {
		zoneDepart = new ArrayList<Point>();
		etat = new CELLULE[ParamPacMan.COLLONES][ParamPacMan.LIGNES];
		generer();
	}

	@Override
	public void generer() {
		for (int i=0; i<ParamPacMan.COLLONES; i++) {
			for (int j=0; j<ParamPacMan.LIGNES; j++) {
				System.out.println(i+" "+j+" "+ParamPacMan.definition[j].charAt(i)); // a retirer
				switch (ParamPacMan.definition[j].charAt(i)) {
				case 'B':
					zoneDepart.add(new Point(i,j));
				case ' ':
					etat[i][j] = CELLULE.BLANCHE;
					break;
				case 'X':
					etat[i][j] = CELLULE.MUR;
					break;
				case '.':
					etat[i][j] = CELLULE.POINT;
					break;
				case 'O':
					etat[i][j] = CELLULE.POINT_PUISS;
					break;
				case '-':
					etat[i][j] = CELLULE.PORTE_F;
					break;
				case 'P':
					etat[i][j] = CELLULE.BLANCHE;
					initial_loc_pac_X = i;
					initial_loc_pac_Y = j;
					break;
				}
			}
		}
	}

	@Override
	public void mettreAJour() {
	}

	@Override
	public void dessiner(Graphics g) {
		int t = ParamPacMan.TAILLE_CELLULE;
		for (int i=0; i<ParamPacMan.COLLONES; i++) {
			for (int j=0; j<ParamPacMan.LIGNES; j++) {
				switch (etat[i][j]) {
				case BLANCHE:
					g.drawImage(blanche, i*t, j*t, t, t, null);
					break;
				case MUR:
					g.drawImage(mur, i*t, j*t, t, t, null);
					break;
				case POINT:
					g.drawImage(point, i*t, j*t, t, t, null);
					break;
				case POINT_PUISS:
					g.drawImage(point_p, i*t, j*t, t, t, null);
					break;
				case PORTE_F:
					g.drawImage(porte_f, i*t, j*t, t, t, null);
					break;
				case PORTE_O:
					g.drawImage(porte_o, i*t, j*t, t, t, null);
					break;
				}
			}
		}
	}

	public int getInitialPacLocY() {
		return initial_loc_pac_Y;
	}

	public int getInitialPacLocX() {
		return initial_loc_pac_X;
	}

	public ArrayList<Point> getZoneDepart() {
		return zoneDepart;
	}

	public CELLULE[][] getEtat() {
		return etat;
	}

	public int getPorteX() {
		int x = 0;
		for (int i=0; i<ParamPacMan.COLLONES; i++) {
			for (int j=0; j<ParamPacMan.LIGNES; j++) {
				if (etat[i][j]==CELLULE.PORTE_F || etat[i][j]==CELLULE.PORTE_O)
					x = i;
			}			
		}
		return x;
	}

	public int getPorteY() {
		int y = 0;
		for (int i=0; i<ParamPacMan.COLLONES; i++) {
			for (int j=0; j<ParamPacMan.LIGNES; j++) {
				if (etat[i][j]==CELLULE.PORTE_F || etat[i][j]==CELLULE.PORTE_O)
					y = j;
			}			
		}
		return y;
	}
	
	public boolean celluleVide(int x, int y) {
		boolean res = true;
		List<Fantome> fantomes = ((MoteurPacMan)mots).getFantomes();
		for (int i=0; i<fantomes.size(); i++) {
			if (fantomes.get(i).getX()==x && fantomes.get(i).getY()==y) {
				res = false;
			}
				
		}
		return res;
	}

}
