package View;

import javax.swing.*;
import java.awt.*;


public class MaVue{

    JFrame jeu;
    JPanel panel,p2;
    JButton play,quitte;
    JLabel label;
    
    public MaVue() {
	jeu = new JFrame ("Pet Rescue Saga");
	jeu.setVisible(true);
	jeu.setSize(700,700);
	jeu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//jeu.setResizable(false);
	//on met le setResizable en commentaire car crée des bug d'ouverture du frame sur terminal	    

        panel = new JPanel();
	
	label = new JLabel("Bienvenue dans Pet Rescue Saga");
	label.setFont(label.getFont().deriveFont(20f)); //agrandit la taille du label
	play = new JButton("play");
	quitte = new JButton("Quitter");
	play.addActionListener((event) -> {jeu.dispose(); new Niveau(); jeu.revalidate(); jeu.repaint();});
	quitte.addActionListener((event) -> {jeu.dispose();});

	p2 = new JPanel();//on crée un second panel afin de mettre le bouton au milieu de lu frame et que le titre soit en haut

	jeu.setLayout(new GridLayout(2,1));

	panel.add(label);
	p2.add(play);
	p2.add(quitte);

        jeu.add(panel,BorderLayout.NORTH);
	jeu.add(p2,BorderLayout.SOUTH);

	
    }

 /*
    public static void main(String[] args) {
	MaVue v = new MaVue();
    }
    */
}
