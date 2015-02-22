package parole;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Genre_musique {

	public List <String> artist;
	Scanner fichier;
	
	public Genre_musique(String path) {
			
		artist = new ArrayList<String>() ;
		int index =0;

		try {
			
			fichier = new Scanner(new FileReader(path));			
			fichier.useDelimiter(System.getProperty("line.separator"));

			while ( fichier.hasNext() ) 
			{
			    artist.add( fichier.next());
			    //System.out.println(mot.get(index));
			    index++;			    
			}
			
			fichier.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		}

	}