
import java.awt.Dimension;
import java.util.Random;


public class ordinateur extends joueur {

    private int niveauDifficulte;
    
    public ordinateur() {
        
    }
    public ordinateur(int niveauDeDifficulté) {
        if (joueurIsPlaying == false){
            switch (niveauDeDifficulté){
                case 1 :{ //Niveau facile
                    int[] target = randomTarget(map);  
                
                    // TODO: Paramètre
                    tirer(target[0] , target[1], cible); 
                }
                
                case 2:{
                    
                }
                case 3:{
                    
                }
                default:{
                    System.out.print("Ok ta mer");
                }
            }
        }  
    }
    
   private int[] randomTarget(plateau carte){
        Dimension mapSize = carte.getMapSize(); 
        Random rand = new Random();
        int x = rand.nextInt(mapSize.width + 1); //Nombre aléatoire entre 0 et n-1   
        int y = rand.nextInt(mapSize.height + 1); //Entre 0 et n-1   
        int[] tabCoordonnées = {x,y};     
    
        return tabCoordonnées;
   }
}
