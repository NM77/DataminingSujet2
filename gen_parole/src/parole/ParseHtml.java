package parole;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParseHtml {

	static Document doc;
	static String texte;
	static String PATH = "/home/5tid1a/cdossa08/siteweb/";
	static String LANG = "/home/5tid1a/cdossa08/git/DataminingSujet2/gen_parole/Langues/";
	static String autor,title;
	static String format;
	static Elements element;
	static Dictionnaire dict_anglais;
	static Dictionnaire dict_francais;
	static Dictionnaire dict_spanish;
	static Dictionnaire dict_german;
	static Dictionnaire dict_coreen;
	//find $PDW -type f 

	public static void main(String[] args) {

		dict_francais = new Dictionnaire(LANG+"french");		
		dict_anglais = new Dictionnaire(LANG+"english");
		dict_spanish = new Dictionnaire(LANG+"spanish");
		dict_german = new Dictionnaire(LANG+"german");
		dict_coreen = new Dictionnaire(LANG+"coreen");

		File song = new File(PATH+"liste_chanson");
		File chanson;
		int index = 0;
		Scanner sc;

		try {
			sc = new Scanner(song);
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
				//System.out.println(lines.get(index));
				chanson = new File(lines.get(index));
				try {
					doc = Jsoup.parse(chanson, "UTF-8") ;
					if(doc!=null)
					{
						element = doc.getElementsByClass("ebzNative").remove() ;
						element = doc.getElementsByClass("song-text");					
						texte = element.html();
						texte = texte.toLowerCase();

						if ( is_french(texte))
						{
							if(texte!="")
							{
								if(Parse(texte)!=null)
								{
									texte = Parse(texte);
									Create_file(texte);
								}

							}
						}
					}
					else
					{
						;
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				index++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(index);
	}

	private static String Parse(String texte){
		texte = texte.replace("<br><br>"," ");
		texte = texte.replace("<br>"," ");			
		texte = texte.replace("  ","");
		//texte = texte.replace("  ","");

		texte = texte.replace(" "+System.getProperty("line.separator")+System.getProperty("line.separator"),System.getProperty("line.separator"));
		//texte = texte.replace(System.getProperty("line.separator")+System.getProperty("line.separator")+" ",System.getProperty("line.separator"));

		texte = texte.replace(System.getProperty("line.separator")+System.getProperty("line.separator"),System.getProperty("line.separator"));

		element = doc.select("span[property=v:artist]");
		autor = element.text();
		element = doc.select("span[property=v:name]");
		title = element.text();

		format = autor+"¤"+title+"¤";

		if (format.contains("Traduction"))
			return null;
		else
		{
			texte = texte.replace(System.getProperty("line.separator"),System.getProperty("line.separator")+format);
			texte = format + texte;
			//		System.out.println(texte);
			return texte;
		}

	}

	private static void Create_file(String texte){
		FileWriter file;
		try {
			file = new FileWriter(PATH+"filtrat", true);
			BufferedWriter bw = new BufferedWriter ( file ) ;  
			PrintWriter pw = new PrintWriter ( bw ) ;
			pw. print (texte) ; 
			pw. close ( ) ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    

	}

	private static boolean is_french(String texte)
	{
		int mot_anglais = 0  ,mot_francais = 0, mot_esp = 0,mot_german=0, mot_coreen=0;


		for(int i=0;i<dict_anglais.mot.size();i++)
		{		
			if( texte.contains( dict_anglais.mot.get(i) ))
				mot_anglais++;
		}

		for(int i=0;i<dict_german.mot.size();i++)
		{		
			if( texte.contains( dict_german.mot.get(i) ))
				mot_german++;
		}

		for(int i=0;i<dict_francais.mot.size();i++)
		{
			if(texte.contains( dict_francais.mot.get(i) ))
				mot_francais++;
		}

		for(int i=0;i<dict_spanish.mot.size();i++)
		{
			if(texte.contains( dict_spanish.mot.get(i) ))
				mot_esp++;
		}
		
		for(int i=0;i<dict_coreen.mot.size();i++)
		{
			if(texte.contains( dict_coreen.mot.get(i) ))
				mot_coreen++;
		}

		/*		
		System.out.println("nombre de mot francais :"+ mot_francais );
		System.out.println("nombre de mot anglais :"+ mot_anglais );
		System.out.println("nombre de mot spanish :"+ mot_esp );
		System.out.println("nombre de mot allemand :"+ mot_german );		 
		 */

		if(mot_anglais>mot_francais || mot_german>mot_francais || mot_esp>mot_francais || mot_francais==0 || mot_coreen>mot_francais)
			return false;
		else
			return true;
	}


}
