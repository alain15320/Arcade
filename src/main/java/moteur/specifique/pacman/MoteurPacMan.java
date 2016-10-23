package moteur.specifique.pacman;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import moteur.generique.EtatMoteur;
import moteur.generique.IMoteur;
import moteur.generique.IMoteurSpecifique;

public class MoteurPacMan implements IMoteurSpecifique {

	private FabriquePacMan fab;
	private IMoteur moteur;
	private Labyrinthe laby;
	private Pac pac;
	private List<Fantome> fants;
	private int timer;
	private int nb_fantomes_caches;

	public MoteurPacMan() {
		fab = new FabriquePacMan();
	}

	@Override
	public void initialiserJeu() {
		fab.setMoteur(moteur);
		laby = (Labyrinthe) fab.creerObject(ParamPacMan.LABYRINTHE);
		pac = (Pac) fab.creerObject(ParamPacMan.PAC);
		fants = new ArrayList<Fantome>();
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++)
			fants.add(((Fantome) fab.creerObject(ParamPacMan.FANTOME)));
	}

	@Override
	public void genererJeu() {
		moteur.setScore(0);
		moteur.setNiveau(1);
		timer = 0;
		nb_fantomes_caches = 0;
		moteur.notification();
		pac.generer();
		pac.setLocation(laby.getInitialPacLocX(), laby.getInitialPacLocY());
		genererFantomes();
		moteur.setEtat(EtatMoteur.INITIALISE);
	}

	private void genererFantomes() {
		ArrayList<Point> zoneDepart = laby.getZoneDepart();
		int max = zoneDepart.size();
		Random rand = new Random();
		int selectionne, x, y;
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
			selectionne = rand.nextInt(max);
			x = (int) zoneDepart.get(selectionne).getX();
			y = (int) zoneDepart.get(selectionne).getY();
			zoneDepart.remove(selectionne);
			max--;
			fants.get(i).generer();
			fants.get(i).setLocation(x, y);
		}
	}

	@Override
	public void mettreAJourJeu() {
		if (timer >= 0) timer++;
		traiterCollision();
		if (!getPorteOuverte() && (timer==ParamPacMan.DUREE_PORTE_OUVERTE)) {
			Labyrinthe.CELLULE[][] etat = getLabyrinthe().getEtat();
			etat[laby.getPorteX()][laby.getPorteY()] = Labyrinthe.CELLULE.PORTE_O;
			timer = -1;
		}
		if (!getPorteOuverte() && (timer==ParamPacMan.DUREE_EFFRAYE)) {
			for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
				fants.get(i).setEffraye(false);
				timer = -1;
			}
		}
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
			fants.get(i).mettreAJour();
		}
	}

	@Override
	public void traiterCollision() {
		int x = pac.getX();
		int y = pac.getY();
		Labyrinthe.CELLULE[][] etat = getLabyrinthe().getEtat();
		if (etat[x][y]==Labyrinthe.CELLULE.POINT)
			mangePoint(etat, x, y);
		if (etat[x][y]==Labyrinthe.CELLULE.POINT_PUISS)
			mangePointPuissance(etat, x, y);
		toucheFantome(x, y);
		if (nb_fantomes_caches==ParamPacMan.NB_FANTOMES)
			changerNiveau();
			moteur.notification();
	}

	private void mangePoint(Labyrinthe.CELLULE[][] etat, int x, int y) {
		moteur.setScore(moteur.getScore()+1);
		etat[x][y]=Labyrinthe.CELLULE.BLANCHE;
		moteur.notification();
	}
	
	private void mangePointPuissance(Labyrinthe.CELLULE[][] etat, int x, int y) {
		moteur.setScore(moteur.getScore()+1);
		etat[x][y]=Labyrinthe.CELLULE.BLANCHE;
		moteur.notification();
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
			fants.get(i).setEffraye(true);
		}
		timer = 0;
	}

	private void toucheFantome(int x, int y) {
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
			if (fants.get(i).getX()==x && fants.get(i).getY()==y && fants.get(i).estEffraye()) {
				moteur.setScore(moteur.getScore()+100);
				moteur.notification();
				nb_fantomes_caches++;
				fants.get(i).setX(-1);
				fants.get(i).setY(-1);
			} else if (fants.get(i).getX()==x && fants.get(i).getY()==y && !fants.get(i).estEffraye()){
				moteur.terminerJeu();
				return;
			}
		}
	}

	private void changerNiveau() {
		moteur.setNiveau(moteur.getNiveau()+1);
		timer = 0;
		nb_fantomes_caches = 0;
		laby.generer();
		pac.generer();
		pac.setLocation(laby.getInitialPacLocX(), laby.getInitialPacLocY());
		genererFantomes();
		for (int i=0; i<ParamPacMan.NB_FANTOMES; i++) {
			fants.get(i).acceler();
		}
	}
	
	public boolean getPorteOuverte() {
		Labyrinthe.CELLULE[][] e = getLabyrinthe().getEtat();
		return e[laby.getPorteX()][laby.getPorteY()] == Labyrinthe.CELLULE.PORTE_O;
	}

	@Override
	public long getPeriodeMaj() {
		return 1000000000L/ParamPacMan.FREQUENCE_MAJ;
	}

	@Override
	public void dessine(Graphics g) {
		switch (moteur.getEtat()) {
		case INITIALISE:
			new Labyrinthe().dessiner(g);
			break;
		case EN_COURS:
		case ARRETE:
			laby.dessiner(g);
			pac.dessiner(g);
			for (int i=0; i<fants.size(); i++) {
				fants.get(i).dessiner(g);
			}
			break;
		case GAMEOVER:
			break;
		}
	}

	@Override
	public void toucheHaut() {
		pac.setDirection(Pac.Direction.HAUT);
	}

	@Override
	public void toucheBas() {
		pac.setDirection(Pac.Direction.BAS);	}

	@Override
	public void toucheDroite() {
		pac.setDirection(Pac.Direction.DROITE);	}

	@Override
	public void toucheGauche() {
		pac.setDirection(Pac.Direction.GAUCHE);	}

	@Override
	public int getParamInt(int p) {
		int ret;
		switch (p) {
		case 1:
			ret = ParamPacMan.LIGNES;
			break;
		case 2:
			ret = ParamPacMan.COLLONES;
			break;
		case 3:
			ret = ParamPacMan.TAILLE_CELLULE;
			break;
		default:
			ret = 0;
		}
		return ret;
	}

	@Override
	public String getParamString(int p) {
		return null;
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}

	public Labyrinthe getLabyrinthe() {
		return laby;
	}
	
	public Pac getPac() {
		return pac;
	}

	public List<Fantome> getFantomes() {
		return fants;
	}
	
}
