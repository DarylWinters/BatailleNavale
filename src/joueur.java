public class joueur {

    protected plateau map;
    public boolean joueurIsPlaying; 
    
    public joueur() {

    }

    public boolean isEmpty(int x, int y){
        boolean isEmpty; 
        cases casesAVerifier = map.getCases(x, y);  
        isEmpty = !(casesAVerifier instanceof segmentBateau);     
        
        return isEmpty; 
    }
    
    public cases tirer(int x, int y, joueur cible) {
        return cible.essuyerTir(x, y);
    }

    public cases essuyerTir(int x, int y) {
        cases casesAVerifier = map.getCases(x, y); 
        casesAVerifier.toucher(); 
        
        return casesAVerifier; 
    }
}
