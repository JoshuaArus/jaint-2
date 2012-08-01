package JaintPlug.redim;


import JaintPlug.*;
import javax.swing.JTextField;
import javax.swing.text.*;

/**
 *
 * @author Joshua
 */
public class DocumentLH extends PlainDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1204524326842144186L;
	private Redimensionnement re;
    private  boolean fait = false;
    private JTextField courant;
    private JTextField autre;
    private int ancienneVal = 1;
    private int courantVal = 1;

    /**
     *
     * @param re Instance de la fenêtre de redimensionnement
     * @param courant Le JTextField sur lequel on rajoute l'écouteur
     * @param autre L'autre JTextField
     */
    public DocumentLH(Redimensionnement re, JTextField courant, JTextField autre)
    {
        this.re = re;
        this.courant = courant;
        this.autre = autre;
        if (courant == re.getJtfHauteur())
            this.ancienneVal = re.getHauteurOrigine();
        else
            this.ancienneVal = re.getLargeurOrigine();
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        if(!str.equals("") && fait == false)
        {
            super.insertString(offs, trier(str), a);
            String s = courant.getText();
            if (s.isEmpty())
                courantVal = 1;
            else
                courantVal = Integer.parseInt(courant.getText());
            maj();
        }
                
    }

    /**
     * Supprime tous les caractères non numérique de la chaine
     * @param s Chaine à trier
     * @return Chaine "numérique"
     */
    public String trier(String s)
    {
        String res = "";

        for (int i = 0; i < s.length(); i++)
        {
            if(Character.isDigit(s.charAt(i)))
            {
                res = res + s.charAt(i);
            }
        }
        String fin = courant.getText()+res;

        if (!fin.isEmpty())
            if (Integer.parseInt(fin) > 3000)
                res = "";

        return res;
    }

    private void maj()
    {
        fait = true;
        boolean autreFait = ((DocumentLH) autre.getDocument()).getFait();

        if (!autreFait && re.getJcbRatio().isSelected())
        {
            int autreVal = 1;

            if (re.getJtfHauteur() == courant)
                autreVal = re.getLargeurOrigine();
            else
                autreVal = re.getHauteurOrigine();


            autre.setText("" + Math.round(autreVal*courantVal/ancienneVal));

        }

        fait = false;
        String largeur = re.getJtfLargeur().getText();
        String hauteur = re.getJtfHauteur().getText();

        if(!re.getJbValider().isEnabled())
        {
            if(!largeur.isEmpty() && !hauteur.isEmpty())
            {
                re.getJbValider().setEnabled(true);
            }

        }
    }

    /**
     * Pour garder le ratio; dès modification d'un champ, il faut mettre l'autre a jour. Seulement, lorsque l'autre se met a jour il essaye de faire apreil avec le champ courant. Il faut donc tester si le champ a déjà été testé
     * @return Renvoi vrai si c'est ce JTextField qu'on est en train de modifier.
     */
    public boolean getFait()
    {
        return fait;
    }

    @Override
    public void remove(int i, int j) throws BadLocationException
    {
        super.remove(i,j);
        maj();
        if (courant.getText().isEmpty())
            re.getJbValider().setEnabled(false);
    }

}
