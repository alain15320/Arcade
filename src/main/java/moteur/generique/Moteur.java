package moteur.generique;

import java.awt.Graphics;
import java.util.Observable;

public class Moteur extends Observable implements IMoteur {
	
	private IMoteurSpecifique moteur;
	private EtatMoteur etat;
	private IGui gui;
	private ISon son;
	private int score;
	private int niveau;

	public Moteur() {
		etat = EtatMoteur.INITIALISE;
	}

	@Override
	public void demarrerJeu() {
		Thread theadJeu = new Thread() {
			public void run() {
				moteur.initialiserJeu();
				boucleDeJeu();
			}
		};
		theadJeu.start();
	}

	protected void boucleDeJeu() {
		if (etat==EtatMoteur.INITIALISE || etat==EtatMoteur.GAMEOVER) {
			moteur.genererJeu();
			etat = EtatMoteur.EN_COURS;
		}
		long tempsDebut, tempsPris, tempsRestant;
		while (true) {
			tempsDebut = System.nanoTime();
			if (etat==EtatMoteur.GAMEOVER) {
				gui.resetPanneauControle();
				break;
			}
			if (etat==EtatMoteur.EN_COURS) {
				moteur.mettreAJourJeu();
			}
			gui.repaintPanneauJeu();
			tempsPris = System.nanoTime()-tempsDebut;
			tempsRestant = (moteur.getPeriodeMaj()-tempsPris)/1000000;
			if (tempsRestant<10) {
				tempsRestant = 10;
			}
			try {
				Thread.sleep(tempsRestant);
			} catch (InterruptedException ex) {}
		}
	}

	@Override
	public void terminerJeu() {
		son.jouerFin();
		setEtat(EtatMoteur.GAMEOVER);
		gui.getMeilleurScore().ajouterTop5(score);
	}

	@Override
	public void notification() {
		setChanged();
		notifyObservers();
	}

	@Override
	public void setMoteurSpecifique(IMoteurSpecifique spe) {
		moteur = spe;
	}

	@Override
	public IMoteurSpecifique getMoteurSpecifique() {
		return moteur;
	}

	@Override
	public void setGui(IGui g) {
		gui = g;
	}

	@Override
	public IGui getGui() {
		return gui;
	}

	@Override
	public void dessine(Graphics g) {
		moteur.dessine(g);
	}

	@Override
	public void toucheHaut() {
		moteur.toucheHaut();
	}

	@Override
	public void toucheBas() {
		moteur.toucheBas();
	}

	@Override
	public void toucheDroite() {
		moteur.toucheDroite();
	}

	@Override
	public void toucheGauche() {
		moteur.toucheGauche();
	}

	@Override
	public int getParamInt(int p) {
		return moteur.getParamInt(p);
	}

	@Override
	public String getParamString(int p) {
		return moteur.getParamString(p);
	}

	@Override
	public EtatMoteur getEtat() {
		return etat;
	}

	@Override
	public void setEtat(EtatMoteur e) {
		etat = e;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getNiveau() {
		return niveau;
	}

	@Override
	public ISon getSon() {
		return son;
	}

	@Override
	public void setScore(int s) {
		score = s;
	}

	@Override
	public void setNiveau(int n) {
		niveau = n;
	}

	@Override
	public void setSon(ISon s) {
		son = s;
	}

}
