
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class plateau extends JPanel{

    private cases[][] monde;

    public cases getCases(int x, int y) {
        return monde[x][y];
}
    
    public Dimension getMapSize(){
        //FIXEME: obtenir taille du tableau a 2 dimensions
        return new Dimension(monde.length, monde.length); 
    }
    
    
    public plateau(int largeur, int hauteur) {
        monde = new cases[largeur][hauteur];
        for (int j = 0 ; j <  hauteur; j++){
            for (int i = 0 ; i < largeur; i++){ 
                monde[i][j] = new cases();    
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    
    }
    
    /*
    public boolean tryPlace(int x, int y, int taille, boolean isHorizontal)
    {
        
        
        return false;
    }
    */
    
    
    public boolean tryPlace(int x, int y, bateau elem)
    {
        
        return false;
    }
    
    /**
     * 
     * @param x1
     * @param y1
     * @return int[2], x,y
     */
    public static int[] JPanelCoordinateToMapCoordinate(int x1, int y1)
    {
        return new int[2];
    }
}
