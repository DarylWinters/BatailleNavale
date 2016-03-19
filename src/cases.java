
import java.awt.Dimension;

public class cases {

    boolean touche = false;
    IHMImage toucheImg;
    IHMImage normalImg;
    
    public cases(IHMImage touche, IHMImage normal) {
        toucheImg = touche;
        normalImg = normal;
    }

    public  void toucher() {
        
    }
    
    public IHMImage getImage(){
        return (touche) ? toucheImg : normalImg;
    }
    
    public Dimension getDimension(){
        return new Dimension(Math.min(toucheImg.largeur(), normalImg.largeur()), Math.min(toucheImg.hauteur(), normalImg.hauteur()));
    }
 
    
}
