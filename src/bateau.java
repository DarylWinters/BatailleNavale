public class bateau {

    private int tailleOrig;
    
    private int taille;

    private boolean enVIe;

    private int x;

    private int y;

    private boolean horizontal;
    
    private segmentBateau[] segments = new segmentBateau[6];

    public bateau(int x, int y, plateau Obj) {
        //TODO: placer bateau
    }

    public int getTaille() {
        return tailleOrig;
    }

    public void setTaille(int parTaille) {
        taille = parTaille;
        tailleOrig = parTaille;
    }

    public boolean enVie() {
        return taille <= 0;
    }

    public int caseRestantes() {
        return taille;
    }

    public void touche() {
        taille--;
    }
}
