package Model;

import java.util.Random;
import java.util.Scanner;

public class Plateau {
    public static Element[][]tab; // taille fixe donc on privilégie le tableau à la liste chainé
    public int nbAnimal; //correspond au nombre d'animaux dans le plateau
    
    
    public Plateau (int n) { // le plateau est constitué entièrement de cubes au début
	tab = new Element [10][10];
	for (int i=0; i<10; i++) {
	    for (int j=0; j<10; j++) {
		tab[i][j] = new Cube();//on remplit le plateau de cube
	    }
	}
	nbAnimal = n;
	animals_in_tab(n); // puis on en remplace par des animaux
    }
    
    public void animals_in_tab (int n) { // on place les animaux dans la grille
	if (n>10) System.out.println("Impossible de placer autant de chiots, veuillez sélectionner un nombre inférieur à " + n);
	else {
	    Random r = new Random();
	    int a = 0;
	    while (a<n) {
		int y = r.nextInt(10);
		if (tab[0][y] instanceof Cube) { // si l'emplacement est rempli par un cube ...
		    tab[0][y] = new Animal(); // ... alors on le remplace par un animal
		    a++;
		}
	    }
	}
    }
    
    public boolean verif_win () { // vérifie si le joueur a gagner
	for (int i=0; i<9; i++) {
	    for (int j=0; j<10; j++) {
		if (tab[i][j] instanceof Animal) return false;//si on trouve un animal qui n'est pas dans la derniere ligne alors le joueur n'a pas encore gagner
	    }
	}
	return true;
    }
    
    public void affiche () {
	System.out.println("    A   B   C   D   E   F   G   H   I   J"); //coordonnee axe abscisse
	for (int i=0; i<10; i++) { //coordonnee axe ordonnee
	    System.out.print(i+1 + "  ");
	    for (int j=0; j<10; j++) {
		if (tab[i][j] instanceof Cube) {
		    Cube tmpo = (Cube)tab[i][j];
		    System.out.print(tmpo.toString() + " ");//affiche la couleur correspondant au cube
		}
		if (tab[i][j] instanceof Animal) System.out.print("[#]" + " ");
		if (tab[i][j] == null) System.out.print("    ");
	    }
	    System.out.print("\n");
	}
    }
    
    public void saisiCoord () {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Entrez les coordonnee de la case que vous souhaitez jouer");
	String s = scanner.next();
	try {
	    int a = Integer.valueOf(s.substring(1)) - 1; //récuppere l'entier entree par l'utilisateur
	    char c = s.charAt(0);
	    char d = Character.toUpperCase(c); //recuppere la lettre entrer par l'utilisateur et la transforme en majuscule
	    int b = Integer.valueOf(d) - 65; //transforme le char en int de fassons a ce que cela corresponde au coordonnee d'une case
	    if(a >= tab.length || b >= tab[a].length) System.out.println("coordonnee saisie trop grande");
	    else suppCube(a,b);//
	} catch(Exception e) {
	    System.out.println("erreur dans les coordonnee saisies, veuillez reessayer");
	}
	scanner.close();
    }
    
    public void suppCube (int a, int b) {
	if (tab[a][b] instanceof Animal) System.out.println("Probleme, vous voulez supprimer un animal");
	else {
	    if (!verifSolo(a,b)) {//supprime des cube seulement s'il sont attaché a un autre cube de la meme couleur
		suppRecur(a,b);//supprime les cube de façon recursive
	    }
	    else System.out.println("Ce cube n'est attaché a aucun autre cube du même type");
	    ajusteTab();//fait descendre les element du tableau apres avoir des element 
	}
	
    }


    public void suppRecur(int a, int b) {
	var tmp = tab[a][b]; 
	tab[a][b] = null;
	try {
	    if(a-1>=0 && verifColor(tmp,tab[a-1][b]))// supprime le cube de gauche s'il est de la meme couleur
		suppRecur(a-1,b);
	    if(a+1<tab.length && verifColor(tmp,tab[a+1][b]))// supprime le cube de droite s'il est de la meme couleur
		suppRecur(a+1,b);
	    if(b-1>=0 && verifColor(tmp,tab[a][b-1]))// supprime le cube du dessous s'il est de la meme couleur
		suppRecur(a,b-1);
	    if(b+1<tab.length && verifColor(tmp,tab[a][b+1]))// supprime le cube du dessus s'il est de la meme couleur
		suppRecur(a,b+1);
	} catch(Exception e) {
	    System.out.println("erreur dimension de la grille");
	}
	
    }
    
    public static boolean verifSolo(int a, int b) {
	if(a-1>=0 && verifColor(tab[a][b],tab[a-1][b])) return false;
	if(a+1<tab.length && verifColor(tab[a][b],tab[a+1][b])) return false;
	if(b-1>=0 && verifColor(tab[a][b],tab[a][b-1])) return false;
	if(b+1<tab.length && verifColor(tab[a][b],tab[a][b+1])) return false;
	return true;//verifie si tout les element autour son de couleur differente
    }
    
    public static boolean verifColor (Element a, Element b) { // verifie si 2 element son de meme instance puis verifie s'il son de meme couleur
	if (a instanceof Cube && b instanceof Cube) {
	    Cube a2 = (Cube)a;
	    Cube b2 = (Cube)b;
	    return verifColorCube(a2,b2);
	}
	return false;
    }
    
    public static boolean verifColorCube (Cube a, Cube b) {
	return a.color.equals(b.color);//verifie si 2 cube son de meme couleur
    }
    
    public void ajusteTab() {
        int i = tab.length - 2;
	boolean b = false;
	while(!b) {
	    for(int j = 0; j < 10; j++) {//on parcourt la grille en partant de l'avant derniere ligne
	    	if(i+1<tab.length && tab[i+1][j] == null) {
		    descente(i,j);//fait descendre le cube
	    	}
	    }
	    i--;
	    if(i<0) b = true; //si on arrive a i<0 cela signifie que toute la grille a été parcouru donc on sort du while
	}
    }
    
    
    public static void descente(int i, int j) {
	//fonction qui sert a faire descendre un element dans le tableau si la case en dessous est vide
    	while(i+1<tab.length && tab[i+1][j] == null) {
	    tab[i+1][j] = tab[i][j];
	    tab[i][j] = null;
	    i++;
    	}
    }


    public int animauxSauver() {
	int res = 0;
	for(int i = 0; i < 10; i++) {
	    if(tab[9][i] instanceof Animal)
		res++;
 	}
	return res;//retourne le nombre d'animaux qui son arrivé en bas
    }

    public boolean bloquer() {//verifie s'il y a encore des coup possible
	for(int i = 0; i < 10; i++) {
	    for(int j = 0; j < 10; j++) {
		if(!verifSolo(i,j)) return false;
	    }
	}
	return true;
    }


    public void botCoup() {
	Random r = new Random();
	int a = r.nextInt(10);
	int b = r.nextInt(10);
        if(!bloquer()) {
	    while(tab[a][b] == null || verifSolo(a,b) || tab[a][b] instanceof Animal) {
		a = r.nextInt(10);
		b = r.nextInt(10);
	    }
	}//fait faire un coup au bot si cela est encore possible
	if(!bloquer()) suppCube(a,b);
    }
	
	
    
	
}
