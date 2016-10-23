package moteur.specifique.serpent;

import moteur.generique.IFabriqueObjets;
import moteur.generique.IMoteur;
import moteur.generique.ObjetJeu;

public class FabriqueSerpent implements IFabriqueObjets {

	private IMoteur moteur;

	@Override
	public ObjetJeu creerObject(int type) {
		ObjetJeu result = null;
		if (type==ParamSerpent.NOURRITURE)
			result = new Nourriture();
		else if (type==ParamSerpent.SERPENT)
			result = new Serpent();
		else if (type==ParamSerpent.MUR)
			result = new Mur();
		result.setMoteurSpecifique(moteur.getMoteurSpecifique());
		return result;
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}

}
