import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	//GananciaMinima esta en tanto por uno porciento
	static double gananciaMinima = 0.02;
	static int contadorLengthPartidosSupra = 0;
	
	static boolean isItSupraBet (double a,double b, double c) {
		if ((101/a)+(101/b)+(101/c)<=100)	
			return true;
		else
			return false;
	}
	
	
	static String whichToBet(Partido partido, double dinero) {
		double aBet,bBet,cBet;
		if(isItSupraBet(partido.getA(), partido.getB(), partido.getC())) {
			if(Math.max(partido.getA(), partido.getB())== partido.getA() && Math.max(partido.getC(), partido.getA()) == partido.getA()) {
				aBet = dinero * (1/partido.getA()) + gananciaMinima * dinero;
				if(Math.max(partido.getB(), partido.getC()) == partido.getB()) {
					bBet = dinero * (1/partido.getB()) + gananciaMinima * dinero;
					cBet = dinero - bBet - aBet;
				}
				else {
					cBet = dinero * (1/partido.getC()) + gananciaMinima * dinero;
					bBet = dinero - cBet - aBet;
				}
			}
			else if (Math.max(partido.getC(), partido.getB())==partido.getB()) {
				bBet = dinero * (1/partido.getB()) + gananciaMinima * dinero;;
				if (Math.max(partido.getA(), partido.getC())==partido.getA()) {
					aBet = dinero * (1/partido.getA()) + gananciaMinima * dinero;
					cBet = dinero - aBet - bBet;
				}
				else {
					cBet = dinero * (1/partido.getC()) + gananciaMinima * dinero;
					aBet = dinero - cBet - bBet;
				}
			}
			else {
				cBet = dinero * (1/partido.getC()) + gananciaMinima * dinero;;
				if (Math.max(partido.getA(),partido.getB())==partido.getA()) {
					aBet = dinero * (1/partido.getA()) + gananciaMinima * dinero;
					bBet = dinero - aBet - cBet;
				}
				else {
					bBet = dinero * (1/partido.getB()) + gananciaMinima * dinero;
					aBet = dinero - bBet - cBet;
				}
			}
		return partido.getNombre() + "\n" + partido.getA() +"  Dinreo a A: " + aBet + " Ganancias --> " + aBet*partido.getA() + "\n" + partido.getB()+"  Dinero a B: " + bBet + " Ganancias --> " + bBet*partido.getB() +
			   "\n" +  partido.getC()+"  Dinero a C: " + cBet + " Ganancias --> " + cBet*partido.getC() + "\n" + "  Dinero apostado: " +(aBet+cBet+bBet) + " Ganancia media --> " + (((aBet*partido.getA()
			   -dinero)+(bBet*partido.getB()-dinero)+(cBet*partido.getC()-dinero))/3) + "€" ;
		}
		else 
			return "Do not bet here";
	}
		
	
	public static void main(String[] args) throws IOException {
		
		
		//Establecer dinero para realizar la operación
		System.out.print("Introduzca dienro disponible para apostar:  ");
		Scanner sc = new Scanner(System.in);
		double dinero = sc.nextDouble();
		//double dinero = 100;
		sc.close();
		System.out.println("\n");
		
		
		
		//Descargar tasas y añadirlas al array
		double[] tasas = new double[33];
		String[] listaPartidos = new String[11];
		String[] linksPartidos = new String[11];
		String html = "https://www.oddschecker.com/es/";
		Document doc = Jsoup.connect(html).get();
		Elements ele = doc.getElementsByClass("event_subEventName_Jxb");
		System.out.println("Loading..."+"\n");
		int p = 0;
		for(Element c: ele) {
			linksPartidos[p]=((c.select("a").attr("href")));
			listaPartidos[p]=("Partido: "+ c.text());
			p++;
		}
		p=0;
		for(String link: linksPartidos) {
			doc = Jsoup.connect(link).get();
			ele = doc.getElementsByClass("bet-components_odds_3YF");
			int q=0;
			for(Element c: ele) {
				if(q==3)
					break;
				else
					tasas[p]=Double.parseDouble(c.text());
					p++;
					q++;
			}
		}
		
		//Metemos en el array ListaPartidosSupra todos los partidos que cumplan la condicion isItSupraBet
		Partido[] ListaPartidosSupra = new Partido[50];
		int e = 0;
		for(int i = 0; i<tasas.length; i+=3) {
			if(isItSupraBet(tasas[i],tasas[i+1],tasas[i+2])) {
			ListaPartidosSupra[e] = new Partido(tasas[i],tasas[i+1],tasas[i+2],listaPartidos[i/3]);
			contadorLengthPartidosSupra = e;
			e++;
			}
		}
		
		//Imprimimos los resultados
		try {
		for(int i = 0; i<= contadorLengthPartidosSupra; i++) {
			System.out.println(whichToBet(ListaPartidosSupra[i], dinero));
		}
		}catch (Exception f) {
			System.out.println("\n"+"Shutting Down... Ningún partido con SupraBet disponible...");
			
		}
	}	
}
	


