public class ordinateur extends joueur {

    private int niveauDifficulte;

    public ordinateur() {
        
    }
    public ordinateur(int niveauDeDifficulté) {
        if (joueurIsPlaying == false){
            switch (niveauDeDifficulté){
                case 1 :{
                    // kamoulox
                }
                case 2:{
                    // TEST
                }
                case 3:{
                    // GPT
                }
                default:{
                    System.out.print("Ok ta mer");
                }
            }
        }  
    }
}
