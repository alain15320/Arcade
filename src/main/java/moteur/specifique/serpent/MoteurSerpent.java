package moteur.specifique.serpent;

import java.awt.Graphics;

import moteur.generique.EtatMoteur;
import moteur.generique.IMoteur;
import moteur.generique.IMoteurSpecifique;

public class MoteurSerpent implements IMoteurSpecifique {

	private FabriqueSerpent fab;
	private IMoteur moteur;
	private Mur[] murs;
	private Serpent serpent;
	private Nourriture nourriture;
	private int frequenceMaj;

	public MoteurSerpent() {
		fab = new FabriqueSerpent();
	}

	@Override
	public void initialiserJeu() {
		fab.setMoteur(moteur);
		murs = new Mur[ParamSerpent.NB_MURS];
		for (int i=0 ; i<ParamSerpent.NB_MURS; i++) {
			murs[i] = (Mur) fab.creerObject(ParamSerpent.MUR);
		}
		serpent = (Serpent) fab.creerObject(ParamSerpent.SERPENT);
		nourriture = (Nourriture) fab.creerObject(ParamSerpent.NOURRITURE);
	}

	@Override
	public void genererJeu() {
		moteur.setScore(0);
		moteur.setNiveau(1);
		frequenceMaj = ParamSerpent.FREQUENCE_MAJ;
		moteur.notification();
		serpent.generer();
		do {
			nourriture.generer();
		} while (serpent.contains(nourriture.getX(), nourriture.getY()));
		Mur m;
		for (int i=0; i<ParamSerpent.NB_MURS; i++) {
			m = murs[i];
			do {
				m.generer();
			} while (serpent.intersects(m.getX(), m.getY()) || (nourriture.getX()==m.getX() && nourriture.getY()==m.getY()));
		}
		moteur.setEtat(EtatMoteur.INITIALISE);
	}

	@Override
	public void mettreAJourJeu() {
		serpent.mettreAJour();
	}

	@Override
	public void traiterCollision() {
		int teteX = serpent.getTeteX();
		int teteY = serpent.getTeteY();
		if (!moteur.getGui().containsPanneauJeu(teteX, teteY)) {
			moteur.terminerJeu();
		}
		for (int i=0; i<ParamSerpent.NB_MURS; i++) {
			if (teteX==murs[i].getX() && teteY==murs[i].getY()) {
				moteur.terminerJeu();
				return;
			}
		}
		if (serpent.mangeLuiMeme()) {
			moteur.terminerJeu();
			return;
		}
		if (teteX==nourriture.getX() && teteY==nourriture.getY())
			manger();
		else serpent.diminuer();
	}

	private void manger() {
		int x, y;
		boolean meme;
		moteur.getSon().jouerCollision();
		moteur.setScore(moteur.getScore()+1);
		if (moteur.getScore()%10==0)
			changerNiveau();
		moteur.notification();
		do {
			meme = false;
			nourriture.generer();
			x = nourriture.getX();
			y = nourriture.getY();
			for (int i=0; i<ParamSerpent.NB_MURS; i++) {
				if (x==murs[i].getX() && y==murs[i].getY()) {
					meme = true;
					break;
				}
			}
		} while (serpent.contains(x, y) || meme);
	}

	@Override
	public long getPeriodeMaj() {
		return 10000000000L/frequenceMaj;
	}

	@Override
	public void dessine(Graphics g) {
		switch (moteur.getEtat()) {
		case EN_COURS:
		case ARRETE:
			serpent.dessiner(g);
			nourriture.dessiner(g);
			for (int i=0; i<ParamSerpent.NB_MURS; i++)
				murs[i].dessiner(g);
			break;
		case GAMEOVER:
		case INITIALISE:
			break;
		}
	}

	@Override
	public void toucheHaut() {
		serpent.setDirection(Serpent.Direction.HAUT);
	}

	@Override
	public void toucheBas() {
		serpent.setDirection(Serpent.Direction.BAS);	}

	@Override
	public void toucheGauche() {
		serpent.setDirection(Serpent.Direction.GAUCHE);	}

	@Override
	public void toucheDroite() {
		serpent.setDirection(Serpent.Direction.DROITE);	}

	@Override
	public int getParamInt(int p) {
		int ret;
		switch(p) {
		case 1:
			ret = ParamSerpent.LIGNES;
			break;
		case 2:
			ret = ParamSerpent.COLLONES;
			break;
		case 3:
			ret = ParamSerpent.TAILLE_CELLULE;
			break;
		default:
				ret = 0;
		}
		return ret;
	}

	@Override
	public String getParamString(int p) {
		String ret;
		switch(p) {
		case 1:
			ret = ParamSerpent.FICHIER_HERBE;
			break;
		default:
			ret = null;
		}
		return ret;
	}

	private void changerNiveau() {
		moteur.setNiveau(moteur.getNiveau()+1);
		frequenceMaj += 5;
	}
	
	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}

	public Serpent getSerpent() {
		return serpent;
	}
	
	public Nourriture getNourriture() {
		return nourriture;
	}
	
	public Mur[] getMurs() {
		return murs;
	}
}
