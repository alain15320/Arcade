package moteur.specifique.pacman;

import moteur.generique.IFabriqueObjets;
import moteur.generique.IMoteur;
import moteur.generique.ObjetJeu;

public class FabriquePacMan implements IFabriqueObjets {

	private IMoteur moteur;

	@Override
	public ObjetJeu creerObject(int type) {
		ObjetJeu result = null;
		if (type==ParamPacMan.PAC) result = new Pac();
		else if (type==ParamPacMan.FANTOME) result = new Fantome();
		else if (type==ParamPacMan.LABYRINTHE) result = new Labyrinthe();
		result.setMoteurSpecifique(moteur.getMoteurSpecifique());
		return result;
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}

}
