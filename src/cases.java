
import java.awt.Dimension;

public class cases {

    boolean touche = false;
    IHMImage toucheImg = new IHMImage();
    IHMImage normalImg = new IHMImage();
    
    public cases() {
        
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
