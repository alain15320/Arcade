package moteur.specifique.pacman;

public class ParamPacMan {

	public static final int FREQUENCE_MAJ = 100;
	public static final int NB_FANTOMES = 4;
	public static final int DUREE_PORTE_OUVERTE = 100;
	public static final int DUREE_EFFRAYE = 400;
	public static final int VITESSE_FANTOME = 25;
	
	public static final String[] definition = {
		     "XXXXXXXXXXXXXXXXXXXXX",	// 0
		     "X.........X.........X",	// 1
		     "XOXXX.XXX.X.XXX.XXXOX",	// 2
		     "X......X..X.........X",	// 3
		     "XXX.XX.X.XXX.XX.X.X.X",	// 4
		     "X....X..........X.X.X",	// 5
		     "X.XX.X.XXX-XXX.XX.X.X",	// 6
		     "X.XX.X.XBBBBBX......X",	// 7
		     "X.XX...XBBBBBX.XXXX.X",	// 8
		     "X.XX.X.XXXXXXX.XXXX.X",	// 9
		     "X....X..............X",	// 10
		     "XXX.XX.XXXXXXX.X.X.XX",	// 11
		     "X.........X....X....X",	// 12
		     "XOXXXXXXX.X.XXXXXXXOX",	// 13
		     "X.........P.........X",	// 14
		     "XXXXXXXXXXXXXXXXXXXXX",	// 15
	};

	public static final int TAILLE_CELLULE = 30;
	public static final int LIGNES = definition.length;
	public static final int COLLONES = definition[0].length();
	public static final String PATH_FANTOME = "src/main/ressources/Images/gost.png";
	public static final String PATH_FANTOME_EFFRAYE = "src/main/ressources/Images/scaredgost.png";
	public static final String PATH_BLANCHE = "src/main/ressources/Images/blank.png";
	public static final String PATH_MUR = "src/main/ressources/Images/wall.png";
	public static final String PATH_POINT = "src/main/ressources/Images/dot.png";
	public static final String PATH_POINT_PUISSANCE = "src/main/ressources/Images/power_dot.png";
	public static final String PATH_PORTE_OUVERTE = "src/main/ressources/Images/door_open.png";
	public static final String PATH_PORTE_FERMEE = "src/main/ressources/Images/door_close.png";
	public static final String PATH_PACD = "src/main/ressources/Images/pacr.png";
	public static final String PATH_PACG = "src/main/ressources/Images/pacl.png";
	public static final String PATH_PACH = "src/main/ressources/Images/pacu.png";
	public static final String PATH_PACB = "src/main/ressources/Images/pacd.png";
	public static final int PAC = 1;
	public static final int LABYRINTHE = 2;
	public static final int FANTOME = 3;

}
