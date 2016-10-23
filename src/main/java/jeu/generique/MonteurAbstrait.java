package jeu.generique;

public abstract class MonteurAbstrait {
	public void construire() {
		construireGui();
		construireMoteur();
		ajouterSon();
		ajouterPersistance();
		terminerConstruire();
	}

	public abstract void construireGui();
	public abstract void construireMoteur();
	public abstract void ajouterSon();
	public abstract void ajouterPersistance();	
	public abstract void terminerConstruire();
}
