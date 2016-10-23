package moteur.specifique.tetris;

import moteur.generique.IFabriqueObjets;
import moteur.generique.IMoteur;
import moteur.generique.ObjetJeu;

public class FabriqueTetris implements IFabriqueObjets {

	private IMoteur moteur;

	@Override
	public ObjetJeu creerObject(int type) {
		ObjetJeu result = null;
		if (type==ParamTetris.TETROMINO) result = new Tetromino();
		result.setMoteurSpecifique(moteur.getMoteurSpecifique());
		return result ;
	}

	@Override
	public void setMoteur(IMoteur m) {
		moteur = m;
	}

}
