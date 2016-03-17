/******************************************************************************
 *                              CLASSE IHMImage
 *     Fournit des outils pour manipuler de façon simple des images 24 bits :
 *      - manipulation des pixels
 *      - manipulation des dimensions
 *      - rognage / incrustation
 *      - lecture écriture d'images sur le disque dur (jpg, png, bmp, gif)
 *      - Affichage dans une fenêtre munie d'une barre de menu
 * 
 * @author ISN
 * @since 5 avril 2015
 * @version 1.1
 ******************************************************************************/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/******************************************************************************/

public class IHMImage {
    private BufferedImage image;
    private String pathname; // à voir
    private String repertoire;
    
    /***********************************************************************
     *                           Constructeurs                             *
     ***********************************************************************/
    
    public IHMImage() {
        this(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * Construit une image de taille 1x1 selon le modèle de couleur spécifié 
     * parmi la liste des entiers (int) suivants :
     *       TYPE_INT_RGB
     *       TYPE_INT_BGR 
     *       TYPE_INT_ARGB 
     *       TYPE_INT_ARGB_PRE
     *       TYPE_3BYTE_BGR
     *       TYPE_4BYTE_ABGR_PRE
     *       TYPE_4BYTE_ABGR
     *       TYPE_BYTE_BINARY
     *       TYPE_BYTE_GRAY
     *       TYPE_BYTE_INDEXED
     *       TYPE_USHORT_GRAY
     *       TYPE_USHORT_555_RGB
     *       TYPE_USHORT_565_RGB
     * 
     * @param modeleCouleur un entier
     */
    public IHMImage(int modeleCouleur) {
        this(1, 1, modeleCouleur);
    }
    
    /**
     * Construit une image de taille largeur par hauteur selon le modèle
     * de couleur TYPE_INT_RGB, tous les pixels sont noirs
     * On choisit le nom de fichier et le dossier.
     * @param largeur entier strictement positif
     * @param hauteur entier strictement positif
     * @param nom     chaîne de caractères désignant le nom de fichier
     * @param dossier chaîne de caractères désignant le nom du dossier
     */
    public IHMImage(int largeur, int hauteur, String nom, String dossier) {
        this(largeur, hauteur, BufferedImage.TYPE_INT_RGB, nom, dossier);
    }
    
    /**
     * Même chose que le précédent, sans le choix du nom de fichier
     * et de dossier.
     * @param largeur entier strictement positif
     * @param hauteur entier strictement positif
     */
    public IHMImage(int largeur, int hauteur) {
        this(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
    }
    
    /**
     * Même chose que le précédent, avec le choix du modèle de couleur.
     * @param largeur entier strictement positif
     * @param hauteur entier strictement positif
     * @param modeleCouleur entier désignant un modèle de couleur 
     */
    public IHMImage(int largeur, int hauteur, int modeleCouleur) {
        this(largeur, hauteur, modeleCouleur, "sans_titre", ".");
    }
    
    /**
     * Même chose que le précédent, avec le choix du nom de fichier et
     * du dossier.
     * @param largeur entier strictement positif
     * @param hauteur entier strictement positif
     * @param modeleCouleur entier désignant un modèle de couleur 
     * @param nom     chaîne de caractères désignant le nom de fichier
     * @param dossier chaîne de caractères désignant le nom du dossier
     */
    public IHMImage(int largeur, int hauteur, int modeleCouleur,
            String nom, String dossier) {
        this.image = new BufferedImage(largeur, hauteur, modeleCouleur);
        this.pathname = nom;
        this.repertoire = dossier;
    }
    
    /***********************************************************************
     *                        Méthodes d'instance                          *
     ***********************************************************************/
    
    /**
     * @return la largeur de l'image en nombre de pixels.
     */
    public int largeur() {
        return this.image.getWidth();
    }
    
    /**
     * @return la hauteur de l'image en nombre de pixels.
     */
    public int hauteur() {
        return this.image.getHeight();
    }
    
    /**
     * @param i abscisse
     * @param j ordonnée
     * @return la couleur du pixel de coordonnées (i;j) sous
     *         forme d'un entier rvb.
     */
    public int getCouleurPixel(int i, int j) {
        return this.image.getRGB(i, j);
    }
    
    /**
     * @param i abscisse
     * @param j ordonnée
     * @return la valeur du pixel de coordonnées (i;j) sous la forme d'un
     *         tableau de 3 entiers {R, V, B} compris entre 0 et 255.
     */
    public int[] getPixel(int i, int j) {
        int[] px = new int[3];
        int p = this.image.getRGB(i, j) + 0x1000000;
        px[2] = p % 0x100;
        p = p / 0x100;
        px[1] = p % 0x100;
        px[0] = p / 0x100;
        return px;
    }
    
    public String getPathname() {
        return this.pathname;
    }
        
    public String getRepertoire() {
        return this.repertoire;
    }
        
    /**
     * Fixe la couleur du pixel de coordonnées (i;j) à la couleur 
     * codée par l'entier rvb.
     * @param i abscisse
     * @param j ordonnée
     * @param rvb couleur 
     */
    public void putPixel(int i, int j, int rvb) {
        this.image.setRGB(i, j, rvb);
    }
    
    /**
     * Fixe la couleur du pixel de coordonnées (i;j) à la couleur 
     * codée par les 3 entiers r, v et b.
     * @param i abscisse
     * @param j ordonnée
     * @param r |
     * @param v > couleur
     * @param b |
     */
    public void putPixel(int i, int j, int r, int v, int b) {
        int rvb = (0xFF << 24) | (r << 16) | (v << 8) | b;
        this.image.setRGB(i, j, rvb);
    }
    
    /** Découpage d'une zone rectangulaire de l'image.  
	 * @param x0 abscisse du coin supérieur gauche
	 * @param y0 ordonnée du même coin
	 * @param l largeur de la zone à conserver
	 * @param h hauteur de la zone à conserver
     * @return Renvoie une <code>IHMImage</code> qui est la sous-image,
     * contenue dans la zone rectangulaire définie par les paramètres
     * ci-dessus, de cette <code>IHMImage</code>.
	 */      
	public IHMImage rogne(int x0, int y0, int l, int h) {
        IHMImage ImageTemp = new IHMImage();
        ImageTemp.image = this.image.getSubimage(x0, y0, l, h);
		return ImageTemp;
	}  
    
    /**
     * Incruste dans cette <code>IHMIMage</code> l'<code>IHMImage Insert</code>.
     * <p>Le point d'incrustation correspond au coin supérieur
     * gauche de l'image <code>Insert</code>.
     * @param Insert IHMImage à incruster
     * @param x abscisse du point d'incrustation
     * @param y ordonnée  du point d'incrustation
     */
	public void incrusteImage(IHMImage Insert, int x, int y) { 
        int l = Insert.largeur();
        int h = Insert.hauteur();
		this.image.createGraphics().drawImage(Insert.image, x, y, l, h, null);
	}	
   
    /**
     * Affiche l'image dans une fenêtre anonyme à sa taille réelle.
     */
    public void afficher() {
        this.afficher(1.0);
    }
    /**
     * Affiche l'image dans une fenêtre anonyme.
     * @param echelle facteur d'échelle
     */
    public void afficher(double echelle) {
        String titre;
        if (this.pathname == null) titre = "Sans titre";
        else titre = this.pathname;
        this.afficher(titre, echelle);
    }    
    
    /**
     * Affiche l'image dans une fenêtre à sa taille réelle.
     * @param titre titre de la fenêtre
     */
    public void afficher(String titre) {
        this.afficher(titre, 1.0);
    }
    
    /**
     * Affiche l'image dans une fenêtre.
     * @param titre titre de la fenêtre
     * @param echelle facteur d'échelle
     */
    public void afficher(final String titre, final double echelle) {
        final BufferedImage img = this.image;
        final String nomFichier = this.pathname;
        final String dossier    = this.repertoire;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                creerEtAfficherFenetre(img, titre, echelle,
                                       nomFichier, dossier, 100, 100);
            }
        });
    }
    
    /**
     * Crée et affiche à l'écran une fenêtre contenant une image donnée.
     *
     * @param image l'image à afficher.
     */
    private static void creerEtAfficherFenetre(final BufferedImage img,
                                               final String titre,
                                               final double echelle,
                                               final String nomFichier,
                                               final String dossier,
                                               final int x,
                                               final int y) {
        final JFrame fenetre = new JFrame(titre);
        JMenuBar barreMenu = new javax.swing.JMenuBar();
        JMenu mFichier = new javax.swing.JMenu();
        JMenuItem itSauvImg = new javax.swing.JMenuItem();
        JMenuItem itFermer = new javax.swing.JMenuItem();
        JMenu mAffichage = new javax.swing.JMenu();
        JRadioButtonMenuItem it100 = new javax.swing.JRadioButtonMenuItem();
        JRadioButtonMenuItem it75 = new javax.swing.JRadioButtonMenuItem();
        JRadioButtonMenuItem it50 = new javax.swing.JRadioButtonMenuItem();
        JRadioButtonMenuItem it25 = new javax.swing.JRadioButtonMenuItem();
        
        barreMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        mFichier.setText("Fichier");
        itSauvImg.setText("Enregistrer l'image sous ...");
        itFermer.setText("Fermer");

        itSauvImg.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
           java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itSauvImg.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                File fichier = new File(dossier, nomFichier);
                JFileChooser dialogue = new JFileChooser(dossier);
                FileFilter Filtre;
                Filtre = new FileNameExtensionFilter(
                        "Images *.jpg, *.jpeg, *.png, *.bmp, *.gif",
                        "jpg", "jpeg", "png", "bmp", "gif");
                dialogue.setDialogTitle("ouvrir une image ...");
                dialogue.setSelectedFile(fichier);
                dialogue.addChoosableFileFilter(Filtre);
                dialogue.setFileFilter(Filtre);

                if(dialogue.showSaveDialog(null)== JFileChooser.APPROVE_OPTION){
                    fichier = dialogue.getSelectedFile();
                    try {
                        String format = fichier.getName();
                        int i = format.lastIndexOf('.');
                        if (i>0) format = format.substring(i+1, format.length())
                                          .toUpperCase();
                        else format = "JPG";
                        if (format.length() == 0) format = "JPG";
                        System.out.println(format);
                        ImageIO.write(img, format, fichier);
                        fenetre.setTitle(fichier.getAbsolutePath());
                    } catch (IOException ex) {}
                }
            }
        });
        itFermer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
           java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        itFermer.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fenetre.dispose();
            }
        });

        mFichier.add(itSauvImg);
        mFichier.add(itFermer);
        barreMenu.add(mFichier);
        
        mAffichage.setText("Affichage");
        it100.setText("100 %");
        it75.setText("  75 %");
        it50.setText("  50 %");
        it25.setText("  25 %");

        it100.setSelected(echelle == 1.0);
        it75.setSelected(echelle == .75);
        it50.setSelected(echelle == .5);
        it25.setSelected(echelle == .25);

        it100.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Point location = fenetre.getLocation();
                int x0 = location.x;
                int y0 = location.y;
                fenetre.dispose();
                creerEtAfficherFenetre(img, titre, 1.0, nomFichier, dossier,
                                        x0, y0);
            }
        });

        it75.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Point location = fenetre.getLocation();
                int x0 = location.x;
                int y0 = location.y;
                fenetre.dispose();
                creerEtAfficherFenetre(img, titre, .75, nomFichier, dossier,
                                        x0, y0);
            }
        });

        it50.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Point location = fenetre.getLocation();
                int x0 = location.x;
                int y0 = location.y;
                fenetre.dispose();
                creerEtAfficherFenetre(img, titre, .5, nomFichier, dossier,
                                        x0, y0);
            }
        });

        it25.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Point location = fenetre.getLocation();
                int x0 = location.x;
                int y0 = location.y;
                fenetre.dispose();
                creerEtAfficherFenetre(img, titre, .25, nomFichier, dossier,
                                        x0, y0);
            }
        });

        mAffichage.add(it100);
        mAffichage.add(it75);
        mAffichage.add(it50);
        mAffichage.add(it25);
        barreMenu.add(mAffichage);
        
        fenetre.add(barreMenu);
        fenetre.setJMenuBar(barreMenu);
         
        fenetre.add(new JPanel() {
            private static final long serialVersionUID = 1L;
            /**
             * surcharge de paintComponent de façon à afficher le
             * contenu de notre BufferedImage.
             */
            @Override
            public void paintComponent(Graphics g) {
                int x, y, width, height;
                width  = (int) (echelle*img.getWidth(this));
                height = (int) (echelle*img.getHeight(this));
                x = (this.getWidth() - width)/2;
                y = (this.getHeight()- height)/2;
                g.drawImage(img, x, y, width, height, null);
            }
             
            /** Constructeur qui définit la taille préférée du panneau */
            {
                setPreferredSize(
                        new Dimension((int) (echelle*img.getWidth()),
                                      (int) (echelle*img.getHeight())));
            }
        }, BorderLayout.CENTER);
         
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.pack();
        
        fenetre.setLocation(x, y);
        fenetre.setVisible(true);
    }

    private static void reafficherFenetre(final BufferedImage img,
                                          final String titre,
                                          final double echelle,
                                          final String nomFichier,
                                          final String dossier) {
        
    }

        private void charger(String rep) throws IOException {
        File fichier;
        JFileChooser dialogue = new JFileChooser(rep);
        FileFilter Filtre;
        Filtre = new FileNameExtensionFilter(
                "Images *.jpg, *.jpeg, *.png, *.bmp, *.gif",
                "jpg", "jpeg", "png", "bmp", "gif");
        dialogue.setDialogTitle("ouvrir une image ...");
        dialogue.addChoosableFileFilter(Filtre);
        dialogue.setFileFilter(Filtre);

        if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
			fichier = dialogue.getSelectedFile();
            this.image = ImageIO.read(fichier);
            this.pathname = fichier.getAbsolutePath();
            this.repertoire = dialogue.getCurrentDirectory().getAbsolutePath();
        }
    }

    /**
     * Enregistre un fichier image correspondant l'objet instance de
     * <code>IHMImage</code> au format <code>formatname</code>
     * via une boite de dialogue.
     * <p> les formats autorisés sont :
     * <p> "JPG", "BMP", "PNG", "GIF".
     * @param rep chemin d'accès au répertoire
     * @param formatname format de fichier image
     * @throws IOException
     */
    public void enregistrer(String rep, String formatname) throws IOException {
        File fichier;
        JFileChooser dialogue = new JFileChooser(rep);
        dialogue.setDialogTitle("enregistrer une image ...");

        if (dialogue.showSaveDialog(null)== JFileChooser.APPROVE_OPTION) {
			fichier = dialogue.getSelectedFile();
            ImageIO.write(this.image, formatname, fichier);
        }
    }
    
    /**
     * Méthode surchargée qui n'est généralement pas utilisée directement.
     * <p><code>image.toString()</code> est automatiquement appelée par
     * <code>System.out.println(image)</code> qui affiche la valeur renvoyée
     * dans la console.
     * @return Renvoie une chaîne de caractères contenant une série
     * d'informations relatives à l'image.
     */
    @Override
    public String toString() {
        String infos, type;
        type = null;
        
        switch (this.image.getType()) {
            case BufferedImage.TYPE_INT_RGB     : type = "RGB"; break;
            case BufferedImage.TYPE_INT_BGR     : type = "BGR"; break;
            case BufferedImage.TYPE_INT_ARGB    : type = "ARGB"; break;
            case BufferedImage.TYPE_INT_ARGB_PRE: type = "ARGB_PRE 8-8-8"; break;
            case BufferedImage.TYPE_3BYTE_BGR   : type = "RGB 8-8-8"; break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE :
            case BufferedImage.TYPE_4BYTE_ABGR  : type = "ARGB 8-8-8-8"; break;
            case BufferedImage.TYPE_BYTE_BINARY : type = "BYTE_BINARY"; break;
            case BufferedImage.TYPE_BYTE_GRAY   : type = "BYTE_GRAY"; break;
            case BufferedImage.TYPE_BYTE_INDEXED: type = "INDEXED 256"; break;
            case BufferedImage.TYPE_USHORT_GRAY : type = "GRAY"; break;
            case BufferedImage.TYPE_USHORT_555_RGB : type = "RGB 5-5-5"; break;
            case BufferedImage.TYPE_USHORT_565_RGB : type = "RGB 5-6-5"; break;
        }
        infos =
           ("                 ** Infos **\n"
          + "\nFichier        : " + this.pathname
          + "\nDimensions     : " + this.largeur() + " x " + this.hauteur()
          + "\nMode           : " + type
          + "\nBits par pixel : " + this.image.getColorModel().getPixelSize()
          + "\n");
        
        return infos;
    }
    
    /***********************************************************************
     *                   Méthodes de classe (static)                       *
     ***********************************************************************/
    /**
     * Construit une instance de <code>IHMImage</code> à partir d'un
     * fichier image ouvert via une boite de dialogue.
     * <p> Ouvre les fichiers .jpg, .jpeg, .bmp, .png, .gif
     * @param rep chemin d'accès au répertoire
     * @return une instance de <code>IHMImage</code>
     * @throws IOException 
     */
    public static IHMImage ouvrir(String rep) throws IOException {
        IHMImage img = new IHMImage();
        img.charger(rep);
        if (img.image == null) return null;
        else return img;
    }
}