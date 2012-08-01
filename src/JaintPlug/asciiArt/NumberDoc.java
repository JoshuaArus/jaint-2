package JaintPlug.asciiArt;

/**
 *
 * @author Jonas
 */

import javax.swing.text.*;

public class NumberDoc extends PlainDocument
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7491023518126183801L;
	private JaintSpinner jsTaille;

    /**
     *
     * @param js
     */
    public NumberDoc(JaintSpinner js)
    {
      this.jsTaille = js;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        int valeurJs = jsTaille.getValue();
        String c = str.trim(); // copie de str
        
        if(valeurJs != -1)
            str = valeurJs + str;
        
        

        if(str != null && estChiffre(str)) //Si la chaine str n'est pas nulle et que ce sont que des lettres
        {
            int nb = Integer.parseInt(str);
            
            if(nb < 1)
               jsTaille.setValue(1);
            else if(nb > 50)
                jsTaille.setValue(50);
            else
                super.insertString(offs, c, a);
        }

    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean ok(String str)
    {
        boolean correct = false;
        int nb = Integer.parseInt(str);

        if(nb <= 50 || nb >= 1)
        {
            correct = true;
        }

        
        return correct;
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean estChiffre(String str)
    {
        boolean chiffre = true;
        int i = 0;
        

        while(i < str.length() && chiffre )//tant que la longueur n'est pas atteinte et que le caractere courant est une lettre alors
        {
            
            chiffre = Character.isDigit(str.charAt(i)); // Vrai si le caractere est un chiffre
            i++;
        }
       
        return chiffre;
    }
}
