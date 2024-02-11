package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import Model.*;

public class VuePartie{

    Plateau plateau;
    JFrame jeu;
    JButton menu,recommence,niveau,aide;
    JPanel partie;
    JPanel tabCube;
    JButton[][] tableau = new JButton[10][10];

    public VuePartie(Plateau p) {
	plateau = p;
	jeu = new JFrame("Pet Rescue Saga");
	jeu.setVisible(true);
	jeu.setSize(700,700);
	jeu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//jeu.setResizable(false);
	//on met le setResizable en commentaire car crée des bug d'ouverture du frame sur terminal
	
	draw();
	
    }

    public void draw() {
	partie = new JPanel(new BorderLayout());
	JPanel panneauBoutton = new JPanel(new GridLayout(1,4));
	menu = new JButton("Menu");
	recommence = new JButton("Recommencer");
	niveau = new JButton("Niveau");
	aide = new JButton("Aide");

	menu.addActionListener((event) -> {jeu.dispose(); new MaVue();});
	recommence.addActionListener((event) -> {
		jeu.dispose();
		new VuePartie(new Plateau(plateau.nbAnimal));
	    });
	niveau.addActionListener((event) -> {jeu.dispose(); new Niveau();});
	aide.addActionListener((event) -> {plateau.botCoup(); draw();});
	
	panneauBoutton.add(menu);
	panneauBoutton.add(recommence);
	panneauBoutton.add(niveau);
	panneauBoutton.add(aide);
	
	partie.add(panneauBoutton,BorderLayout.NORTH);

	String s = "Score : " + String.valueOf(plateau.animauxSauver()) + "/" + String.valueOf(plateau.nbAnimal);
	JLabel score = new JLabel(s);
	score.setFont(score.getFont().deriveFont(30f));//agrandit taille du label
	partie.add(score, BorderLayout.SOUTH);

	if(!plateau.verif_win() && !plateau.bloquer()) {//tant qu'on a pas gagner et qu'on peut encore jouer la partie continue
	    afficheCube();
	
	    partie.add(tabCube,BorderLayout.CENTER);
	    
	    jeu.setContentPane(partie);
	
	    jeu.revalidate();
	    jeu.repaint();
	} else if(!plateau.verif_win() && plateau.bloquer()){ defaite();}//si on a pas gagner et qu'on ne peut plus jouer la partie est perdu
	else {
	    victoire();
	}
    }

    public void placementCube() {
	for(int i = 0; i < 10; i++) {
	    for(int j = 0; j < 10; j++) {
		if(plateau.tab[i][j] != null) {
		    final int a = i;
		    final int b = j;
		    JButton button = new JButton();//on crée un boutton correspondant a la couleur du cube ou de son instance(cube ou animal)
		    if(plateau.tab[i][j] instanceof Cube) {
			button.setBackground(plateau.tab[i][j].getCouleur());//on lui donne la couleur du cube correspondant dans le plateau
		    } else {
			try {
			    Image photo = ImageIO.read(new File("View/image/dog.png"));
			    button.setIcon(new ImageIcon(photo));// si c'est un animal on definit l'element avec un image d'animal
			} catch(Exception e) {
			    button.setBackground(plateau.tab[i][j].getCouleur());//s'il y une erreur on recupere la couleur qui est noir
			}
		    }
		    tableau[i][j] = button;//on ajoute le boutton dans un JButton[][] qui servira a crée le panel qui sera un gridLayout(10,10)
		    button.addActionListener((event) -> {plateau.suppCube(a,b); draw();});
		} else {
			JButton button = new JButton();
			button.setEnabled(false);//si l'element a deja été detruit on crée un boutton désactivé
			tableau[i][j] = button;
		}
	    }
	}


    }
    


    public void afficheCube() {
	placementCube();//on effectue la fonction juste au dessus afin de crée tout les boutton
	tabCube = new JPanel(new GridLayout(10,10));
	for(int i = 0;i < 10; i++) {
	    for(int j = 0;j < 10; j++) {
		tabCube.add(tableau[i][j]);//on les ajoute 1 par 1 au panel
	    }
	}

    }

    public void victoire() {
        tabCube.removeAll();//on affiche un message quand la partie est gagnée et supprime tout le panel des cube
	JLabel label = new JLabel("Félicitation vous avez gagné");
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setFont(label.getFont().deriveFont(35f)); //on agrandit la taille du message
	partie.add(label,BorderLayout.CENTER);
	jeu.setContentPane(partie);
	jeu.revalidate();
	jeu.repaint();
	
    }

    public void defaite() {
        tabCube.removeAll();//on affiche un message quand la partie est perdue et supprime tout le panel des cube
	JLabel label = new JLabel("Dommage vous avez perdu, réésayez vous pouvez y arrivé");
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setFont(label.getFont().deriveFont(20f)); //on agrandit la taille du message
	partie.add(label,BorderLayout.CENTER);
	jeu.setContentPane(partie);
	jeu.revalidate();
	jeu.repaint();
	
    }


    
    
    



}

    
    
    
    
