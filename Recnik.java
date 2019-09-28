package pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Recnik {
	private HashMap<String, ArrayList<String>> recnik;
	private String connString = "jdbc:sqlite:C:\\Users\\Korisnik\\Desktop\\IT Bootcamp\\23.9.2019 Zavrsni rad\\Dictionary.db";

	public Recnik() {
		recnik = new HashMap<String, ArrayList<String>>();
		dodajTabelu();
	}

	private void dodajTabelu() {
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm = con.createStatement();

			String upit = "CREATE TABLE if not exists noveReci (word varchar(30))";
			stm.executeUpdate(upit);
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void popuniRecnik() {
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm = con.createStatement();

			String upit = "SELECT word, definition " 
			            + " FROM entries";

			ResultSet rs = stm.executeQuery(upit);
			while (rs.next()) {
				String rec = rs.getString("word").toLowerCase();

				ArrayList<String> definitions = recnik.get(rec);
				if (definitions == null) {
					definitions = new ArrayList<String>();
					recnik.put(rec, definitions);
				}
				definitions.add(rs.getString("definition"));
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dodajNovuRec(String s) {
		s = s.toLowerCase();
		try (Connection con = DriverManager.getConnection(connString)) {
			Statement stm1 = con.createStatement();

			String upit1 = "SELECT word " 
			             + "FROM noveReci " 
					     + "WHERE word = '" + s + "'";

			ResultSet rs = stm1.executeQuery(upit1);
			if (rs.next() == false) {
				Statement stm2 = con.createStatement();

				String upit2 = "INSERT INTO NoveReci (word) " 
				             + "VALUES ('" + s + "')";

				stm2.executeUpdate(upit2);
				stm2.close();
			}
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean nadjiRec(String s) {
		s = s.toLowerCase();
		if (recnik.containsKey(s))
			return true;
		else
			return false;
	}

	public HashMap<String, ArrayList<String>> getRecnik() {
		return recnik;
	}

}
