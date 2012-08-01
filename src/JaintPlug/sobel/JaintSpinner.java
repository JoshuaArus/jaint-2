package JaintPlug.sobel;

/**
 *
 * @author Jonas
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import JaintPlug.*;

public class JaintSpinner extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3578172434766370065L;

	//Ecouteur
    /**
     * Ecouteur sur le bouton "Plus" permettant d'incrementer la valeur
     */
    public class JBEcouteur_plus implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
                    if(!jtf_nombre.getText().equals(""))
                    {
                        jtf_nombre.setText("" + (Integer.parseInt(jtf_nombre.getText()) + 1));
                    }
                    else
                    {
                        jtf_nombre.setText("1");
                    }
			

		}
	}

        /**
         * Ecouteur sur le bouton "Moins" permettant de decrementer la valeur
         */
        public class JBEcouteur_moins implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
                    if(!jtf_nombre.getText().equals(""))
                    {
                        jtf_nombre.setText("" + (Integer.parseInt(jtf_nombre.getText()) - 1));
                    }
                    else
                    {
                        jtf_nombre.setText("1");
                    }
			

		}
	}

	//Attributs
        /**
         * Champs contenant la valeur
         */
        protected JTextField jtf_nombre;
        /**
         * Bouton "Plus"
         */
        protected JButton jb_plus; //source
        /**
         * Bouton "Moins"
         */
        protected JButton jb_moins; //source
        /**
         * ActionListener du bouton "Plus"
         */
        protected ActionListener al_plus; //ecouteur
        /**
         * ActionListener du bouton "Moins"
         */
        protected ActionListener al_moins; //ecouteur
        /**
         * Instance de Sobel en cours
         */
        protected Sobel sbl;
        /**
         * Icone du bouton "Plus"
         */
        protected ImageIcon iconPlus;
        /**
         * Icone du bouton "Moins"
         */
        protected ImageIcon iconMoins;
        /**
         * Image en cours de modification
         */
        protected Image tmp;


	//Constructeurs
        /**
         *
         * @param s Instance de Sobel
         */
        public JaintSpinner(Sobel s)
	{
		super();

		//Constructeurs des elements
                this.sbl = s;
                
                this.iconPlus =  new ImageIcon("./lib/Icons/Plugins/plus.png");

                
                this.iconMoins = new ImageIcon("./lib/Icons/Plugins/moins.png");
                

                
		this.jb_plus = new JButton();
		this.jb_moins = new JButton();
		this.jtf_nombre = new JTextField("1");
                this.al_plus = new JBEcouteur_plus();
		this.al_moins = new JBEcouteur_moins();

                
                this.jb_moins.setIcon(this.iconMoins);
                this.jb_plus.setIcon(this.iconPlus);

                //Parametrage de la taille des elements
                this.jtf_nombre.setPreferredSize( new Dimension(90,30));
                this.jb_plus.setPreferredSize( new Dimension(30,30));
                this.jb_moins.setPreferredSize( new Dimension(30,30));

                
                
		//Parametrage des elements
		this.jtf_nombre.setEditable(true);
		this.jtf_nombre.setHorizontalAlignment(JTextField.RIGHT);

		//Parametrage des evenements
		this.jb_plus.addActionListener(al_plus);
		this.jb_moins.addActionListener(al_moins);

		//Saisie controlee
                
		jtf_nombre.setDocument(new NumberDoc(this));
                jtf_nombre.setText("1");
                

                //Ecouteur pour la saisie
                this.jtf_nombre.getDocument().addDocumentListener(new EcouteurSpinner(sbl));

		//Parametrage du JPanel
		this.setLayout(new BorderLayout());

		//Ajout des elements a un JPanel
		this.add(jb_plus,BorderLayout.EAST);
		this.add(jtf_nombre,BorderLayout.CENTER);
		this.add(jb_moins,BorderLayout.WEST);


	}//Constructeur

        /**
         * Renvoi la valeur contenue dans le champ de texte
         * @return
         */
        public int getValue()
	{
            String res = this.jtf_nombre.getText();
            return (res.equals("")) ? -1 : Integer.valueOf(res);
	}

        /**
         * Specifie la valeur contenue dans le champ de texte
         * @param v
         */
        public void setValue(int v)
        {
            this.jtf_nombre.setText(String.valueOf(v));
        }

       

}
