package Model;

import java.awt.Color;

public abstract class Element {

    public Color getCouleur() {
	if(this instanceof Animal) return new Color(0,0,0);//si l'element est un animal on renvoie la couleur noir
	else {
	    Cube c = (Cube)this;
	    return c.color;
	}
    }
	

}
