public class segmentBateau extends cases {

    private bateau appartientA;

    public segmentBateau(IHMImage touche, IHMImage normal) {
        super(touche, normal);
    }
    
    @Override
    public  void toucher() {
        if(touche == false)
        appartientA.touche();
        touche = true;
    }
    
    public void getStatut() {
     if(touche == false)
         }    
}
