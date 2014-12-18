package parole;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionnaire {

	public List <String> mot;
	Scanner fichier;
	
	public Dictionnaire(String path) {
			
		mot = new ArrayList<String>() ;
		int index =0;

		try {
			fichier = new Scanner(new FileReader(path));
			
			fichier.useDelimiter(System.getProperty("line.separator"));

			while ( fichier.hasNext() ) 
			{
			    mot.add( fichier.next());
			    System.out.println(mot.get(index));
			    index++;			    
			}
			
			fichier.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		}

	}
