package persist;

import java.util.ArrayList;

public interface IPersist {
	static final String PATH_SCORES = "src/main/ressources/Highscores/";
	ArrayList<Score> lireListe(String nom);
	void ecrireListe(String nom, ArrayList<Score> liste);
}
