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
	static String PATH = "/home/kristof/siteweb/list_file";
	static String autor,title;
	static String format;
	static Elements element;


	public static void main(String[] args) {
		File song = new File(PATH);
		File chanson;
		int index = 0;
		Scanner sc;
		try {
			sc = new Scanner(song);
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
				System.out.println(lines.get(index));
				chanson = new File(lines.get(index));
				try {
					doc = Jsoup.parse(chanson, "UTF-8") ;
					element= doc.getElementsByClass("ebzNative").remove() ;
					element= doc.getElementsByClass("song-text");
					texte = element.html();
					if(texte!="")
					{
						texte = Parse(texte);					
						Create_file(texte,String.valueOf(index));
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

		texte = texte.replace(System.getProperty("line.separator"),System.getProperty("line.separator")+format);
		texte = format + texte;


		//		System.out.println(texte);
		return texte;
	}

	private static void Create_file(String texte,String name){
		FileWriter file;
		try {
			file = new FileWriter("/home/kristof/siteweb/"+"0", true);
			BufferedWriter bw = new BufferedWriter ( file ) ;  
			PrintWriter pw = new PrintWriter ( bw ) ;
			pw. print (texte) ; 
			pw. close ( ) ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    

	}

}
