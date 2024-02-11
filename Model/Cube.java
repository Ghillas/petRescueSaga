package Model;

import java.awt.*;
import java.util.Random;

public class Cube extends Element {
    Color color;
    Color [] ensemble = {new Color(255,0,0),new Color(0,255,0),new Color(0,0,255)};

    public Cube () {
	Random r  = new Random();
	int c = r.nextInt(3);
	color = ensemble[c]; //la couleur est choisi aleatoirement dans le tableau de couleur "ensemble"
    }
    
    public String toString () {
    	if (color.equals(ensemble[0])) return "[R]";
    	else if (color.equals(ensemble[1])) return "[V]";
    	else if (color.equals(ensemble[2])) return "[B]";
    	else return "[N]";
    }

}
