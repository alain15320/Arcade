package persist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FichierTexte implements IPersist {

	@Override
	public ArrayList<Score> lireListe(String nom) {
		File f = new File(PATH_SCORES+nom+".sco.txt");
		ArrayList<Score> res = new ArrayList<Score>();
		if (f.exists()) {
			try {
				String ligne = null;
				String[] mots = new String[2];
				Score s = null;
				FileReader fr = new FileReader(PATH_SCORES+nom+".sco.txt");
				BufferedReader br = new BufferedReader(fr);
				while ((ligne = br.readLine()) != null) {
					mots = ligne.split("\t");
					s = new Score(mots[0], Integer.parseInt(mots[1]));
					res.add(s);
				}
				br.close();
			} catch(Exception e) {
				System.out.println("read error : "+e.getMessage());
			}
		}
		return res;
	}

	@Override
	public void ecrireListe(String nom, ArrayList<Score> liste) {
		try {
			FileWriter fw = new FileWriter(PATH_SCORES+nom+".sco.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (Score s : liste) {
				bw.write(s.getNom()+"\t"+s.getScore());
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			System.out.println("write error : "+e);
		}
	}
}
