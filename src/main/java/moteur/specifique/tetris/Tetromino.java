package moteur.specifique.tetris;

import java.awt.Color;
import java.awt.Graphics;

import moteur.generique.ObjetJeu;

public class Tetromino extends ObjetJeu {

	public static enum Tetrominoes {Vide, Z, S, Ligne, T, Carre, L, LRetourne}
	private int[][] coords;
	private Tetrominoes forme;

	public Tetromino() {
		coords = new int[4][2];
		donnerFormeAuHasard();
		x =(ParamTetris.COLONNES/2)-1;
		y = 0;
	}

	public void donnerFormeAuHasard() {
		int x = Math.abs(rand.nextInt())%7+1;
		Tetrominoes[] valeurs = Tetrominoes.values();
		donnerForme(valeurs[x]);
	}

	private void donnerForme(Tetrominoes s) {
		int[][][] coordsTable = new int[][][] {
			{{0,0}, {0,0}, {0,0}, {0,0}},
			{{0,-1}, {0,0}, {-1,0}, {-1,1}},
			{{0,-1}, {0,0}, {1,0}, {1,1}},
			{{0,-1}, {0,0}, {0,1}, {0,2}},
			{{-1,0}, {0,0}, {1,0}, {0,1}},
			{{0,0}, {1,0}, {0,1}, {1,1}},
			{{-1,-1}, {0,-1}, {0,0}, {0,1}},
			{{1,-1}, {0,-1}, {0,0}, {0,1}}
		};
		for (int i=0; i<4; i++) {
			for (int j=0; j<2; j++) {
				coords[i][j] = coordsTable[s.ordinal()][i][j];
			}
		}
		forme = s;
	}

	@Override
	public void generer() {}

	@Override
	public void mettreAJour() {
		y++;
	}

	@Override
	public void dessiner(Graphics g) {
		int t = ParamTetris.TAILLE_CELLULE;
		if (forme!=Tetrominoes.Vide)
			for (int i=0; i<4; i++)
				dessineCarre(g, (x+coords[i][0])*t, (y+coords[i][1])*t);
	}

	private void dessineCarre(Graphics g, int i, int j) {
		int t = ParamTetris.TAILLE_CELLULE;
		g.setColor(Tetromino.getColor(forme));
		g.fillRect(i, j, t, t);
	}

	public void tournerGauche() {
		if (forme==Tetrominoes.Carre)
			return;
		int old;
		int[][] ncoords = new int[4][2];
		for (int i=0; i<4; i++) {
			old = coords[i][0];
			ncoords[i][0] = coords[i][1];
			ncoords[i][1] = -old;
		}
		if (((MoteurTetris)mots).peutTourner(getCells(ncoords))) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<2; j++) {
					coords[i][j] = ncoords[i][j];
				}
			}
		}
	}

	private int[][] getCells(int[][] c) {
		int[][] cells = new int[4][2];
		for (int i=0; i<4; i++) {
			cells[i][0] = x+c[i][0];
			cells[i][1] = y+c[i][1];			
		}
		return cells;
	}

	public void tournerDroite() {
		if (forme==Tetrominoes.Carre)
			return;
		int old;
		int[][] ncoords = new int[4][2];
		for (int i=0; i<4; i++) {
			old = coords[i][0];
			ncoords[i][0] = -coords[i][1];
			ncoords[i][1] = old;
		}
		if (((MoteurTetris)mots).peutTourner(getCells(ncoords))) {
			for (int i=0; i<4; i++) {
				for (int j=0; j<2; j++) {
					coords[i][j] = ncoords[i][j];
				}
			}
		}
	}

	public void deplacerGauche() {
		if (((MoteurTetris)mots).peutAllerGauche(getCells(coords)))
			x--;
	}

	public void deplacerDroite() {
		if (((MoteurTetris)mots).peutAllerDroite(getCells(coords)))
			x++;	}

	public Tetrominoes getForme() {
		return forme;
	}
	
	public int[][] getCoords() {
		return coords;
	}

	public static Color getColor(Tetrominoes forme) {
		Color couleurs[] = {new Color(0,0,0), new Color(204,102,102),
				new Color(102,204,102), new Color(102,102,204),
				new Color(204,204,102), new Color(204,102,204),
				new Color(102,204,204), new Color(218,170,0)};
		return couleurs[forme.ordinal()];
	}

}
