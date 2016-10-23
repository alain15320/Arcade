package moteur.generique;

public interface IFabriqueObjets {
	ObjetJeu creerObject(int type);
	void setMoteur(IMoteur m);
}
