package pro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class GlavniProgram {

	public static void main(String[] args) {
		Knjizica k = new Knjizica();
		k.ukloniZnake();
		k.pronadjiNoveReci();
		k.brojPojavljivanja();
		Recnik r=new Recnik();
	
		ArrayList<String> najcesceReci = k.pronadjiNajcesceReci(20);
		Collections.sort(najcesceReci);
		for (String rec : najcesceReci) {
			System.out.println(rec);
		}
		k.upisUFajl("sortiraneReci");

	}

}
