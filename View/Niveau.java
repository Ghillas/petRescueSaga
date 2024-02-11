package View;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class Niveau{

    JFrame frame;
    JPanel panel,create;
    JButton n1,n2,n3,n4,n5,edit,menu;
    JLabel label;

    public Niveau() {
	frame = new JFrame("Pet Rescue Saga");
	frame.setSize(700,700);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//frame.setResizable(false);
	//on met le setResizable en commentaire car crée des bug d'ouverture du frame sur terminal

	panel = new JPanel(new GridLayout(3,1));
	label = new JLabel("Selectionnez le niveau que vous souhaitez\n");

	JPanel haut = new JPanel();//correspond au haut de la page
	haut.add(label);

	menu = new JButton("Menu");
	menu.addActionListener((event) -> {frame.dispose(); new MaVue();});
	haut.add(menu);
	
	n1 = new JButton("Niveau 1");
	n2 = new JButton("Niveau 2");
	n3 = new JButton("Niveau 3");
	n4 = new JButton("Niveau 4");
	n5 = new JButton("Niveau 5");


	n1.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(2));});
	n2.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(3));});
	n3.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(5));});
	n4.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(7));});
	n5.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(9));});
	
	JPanel level = new JPanel(new GridLayout(5,1));// panel des niveau afin que la taille des boutton ne prennent pas toute la largeur de la page
	level.add(n1);
	level.add(n2);
	level.add(n3);
	level.add(n4);
	level.add(n5);

        JPanel inter = new JPanel(new GridLayout(1,3));//panel du milieu de la page afin que les boutton ne prennent pas toutes la largeur de la page
	inter.add(new JPanel());
	inter.add(level);
	inter.add(new JPanel());

	label.setHorizontalAlignment(SwingConstants.CENTER);
	panel.add(haut);
	panel.add(inter);

        edit = new JButton("Créer un niveau");
	edit.addActionListener((event) -> {creer();});

	JPanel createInter = new JPanel(new GridLayout(1,3));//panel afin que les composant ne prennent pas toute la largeur de la page
        create = new JPanel(new GridLayout(3,1));
	create.add(edit);
	createInter.add(new JPanel());
	createInter.add(create);
	createInter.add(new JPanel());
	panel.add(createInter);
	
        frame.setContentPane(panel);

    }

    public void creer() {
	JSlider slide = new JSlider(1,10,1);//correspond au nombre d'animaux souhaité afin de créer une partie personnalisé
	slide.setMinorTickSpacing(2);
	slide.setMajorTickSpacing(1);
	slide.setPaintTicks(true);
	slide.setPaintLabels(true);
	create.add(slide);
	JButton test = new JButton("Jouer");
	test.addActionListener((event) -> {frame.dispose(); new VuePartie(new Plateau(slide.getValue()));});
	create.add(test);
	edit.setEnabled(false);//desactive le boutton
	frame.revalidate();
	frame.repaint();
    }
	
	


}
	
