package moteur.specifique.tetris;

import java.awt.Graphics;

import moteur.generique.EtatMoteur;
import moteur.generique.IMoteur;
import moteur.generique.IMoteurSpecifique;

public class MoteurTetris implements IMoteurSpecifique {

	private Tetromino tetromino;
	private FabriqueTetris fab;
	private IMoteur moteur;
	private Tetromino.Tetrominoes[][] tas;
	private int frequenceMaj;
	
	public MoteurTetris() {
		fab = new FabriqueTetris();
	}

	@Override
	public void initialiserJeu() {
		fab.setMoteur(moteur);
		tas = new Tetromino.Tetrominoes[ParamTetris.LIGNES][ParamTetris.COLONNES];
		for (int i=0; i<ParamTetris.LIGNES; i++) {
			for (int j=0; j<ParamTetris.COLONNES; j++) {
				tas[i][j] = Tetromino.Tetrominoes.Vide;
			}
		}
		tetromino = (Tetromino) fab.creerObject(ParamTetris.TETROMINO);
	}

	@Override
	public void genererJeu() {
		moteur.setScore(0);
		moteur.setNiveau(1);
		frequenceMaj = ParamTetris.FREQUENCE_MAJ;
		moteur.notification();
		moteur.setEtat(EtatMoteur.INITIALISE);
	}

	@Override
	public void mettreAJourJeu() {
		traiterCollision();
		tetromino.mettreAJour();
		chercherLignePleine();
		if (tasPlein()) moteur.terminerJeu();
	}

	private void chercherLignePleine() {
		boolean plein = true;
		for (int i=0; i<ParamTetris.LIGNES; i++) {
			for (int j=0; j<ParamTetris.COLONNES; j++) {
				if (tas[i][j]==Tetromino.Tetrominoes.Vide) {
					plein = false;
					break;
				}
			}
			if (plein) {
				moteur.setScore(moteur.getScore()+100);
				mettreAJourTas(i);
			}
		}
	}

	private void mettreAJourTas(int i) {
		for (int k=i; k>0; k--)
			for (int l=0; l<ParamTetris.COLONNES; l++)
				tas[k][l] = tas[k-1][l];
		changerNiveau();
		moteur.notification();		
	}


	private boolean tasPlein() {
		boolean res = false;
		for (int j=0; j<ParamTetris.COLONNES; j++) {
			if (tas[1][j] != Tetromino.Tetrominoes.Vide) {
				res = true;
				break;
			}
		}
		return res;
	}
	
	@Override
	public void traiterCollision() {
		int[][] coords = tetromino.getCoords();
		int y = tetromino.getY();
		int x = tetromino.getX();
		for (int i=0; i<4; i++) {
			if ((y+coords[i][1]==ParamTetris.LIGNES-1) ||
			(tas[y+coords[i][1]+1][x+coords[i][0]]!=Tetromino.Tetrominoes.Vide)) {
				stockerEtChanger();
				break;				
			}
		}
	}

	public void stockerEtChanger() {
		int[][] coords = tetromino.getCoords();
		int y = tetromino.getY();
		int x = tetromino.getX();
		for (int i=0; i<4; i++) {
			tas[y+coords[i][1]][x+coords[i][0]] = tetromino.getForme();
		}
		tetromino = (Tetromino) fab.creerObject(ParamTetris.TETROMINO);
		tetromino.donnerFormeAuHasard();
		moteur.setScore(moteur.getScore()+1);
		moteur.notification();
	}
	
	@Override
	public long getPeriodeMaj() {
		return 1000000000L/frequenceMaj;
	}
	
	private void changerNiveau() {
		moteur.setNiveau(moteur.getNiveau()+1);
		frequenceMaj += 1;
	}

	private void dessinerTas(Graphics g) {
		int t = ParamTetris.TAILLE_CELLULE;
		for (int i=0; i<ParamTetris.COLONNES; i++) {
			for (int j=0; j<ParamTetris.LIGNES; j++) {
				if (tas[j][i] != Tetromino.Tetrominoes.Vide) {
					g.setColor(Tetromino.getColor(tas[j][i]));
					g.fillRect(i*t, j*t, t, t);
				}
			}
		}
	}
	
	@Override
	public void dessine(Graphics g) {
		switch (moteur.getEtat()) {
		case EN_COURS:
		case ARRETE:
			tetromino.dessiner(g);
			dessinerTas(g);
			break;
		case GAMEOVER:
		case INITIALISE:
			break;
		}
	}

	@Override
	public void toucheHaut() {
		tetromino.tournerGauche();
	}

	@Override
	public void toucheBas() {
		tetromino.tournerDroite();	}

	@Override
	public void toucheDroite() {
		tetromino.deplacerDroite();	}

	@Override
	public void toucheGauche() {
		tetromino.deplacerGauche();	}

	@Override
	public int getParamInt(int p) {
		int ret;
		switch (p) {
		case 1:
			ret = ParamTetris.LIGNES;
			break;
		case 2:
			ret = ParamTetris.COLONNES;
			break;
		case 3:
			ret = ParamTetris.TAILLE_CELLULE;
			break;
		default:
			ret = 0;	
		}
		return ret;
	}

	@Override
	public String getParamString(int p) {
		return new String();
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}
	
	public Tetromino getTetromino() {
		return tetromino;
	}

	public boolean peutTourner(int[][] cells) {
		boolean res = true;
		for (int i=0; i<4; i++) {
			if (cells[i][0]-1>ParamTetris.COLONNES-1 || cells[i][0]<0 || cells[i][1]>ParamTetris.LIGNES-1) {
				res = false;
				break;
			} else {
				if (tas[cells[i][1]][cells[i][0]] != Tetromino.Tetrominoes.Vide) {
					res = false;
					break;
				}
			}
		}
		return res;
	}


	public boolean peutAllerGauche(int[][] cells) {
		boolean res = true;
		for (int i=0; i<4; i++) {
			if (cells[i][0]-1<0 || cells[i][1]<0) {
				res = false;
				break;
			} else {
				if (tas[cells[i][1]][cells[i][0]-1] != Tetromino.Tetrominoes.Vide) {
					res = false;
					break;
				}
			}
		}
		return res;
	}

	public boolean peutAllerDroite(int[][] cells) {
		boolean res = true;
		for (int i=0; i<4; i++) {
			if (cells[i][0]+1 > ParamTetris.COLONNES-1) {
				res = false;
				break;
			} else {
				if (tas[cells[i][1]][cells[i][0]-1] != Tetromino.Tetrominoes.Vide) {
					res = false;
					break;
				}
			}
		}
		return res;
	}
}
