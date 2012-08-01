package JaintPlug.asciiArt;

/**
    *	Sous classe associant une luminosité à chaque caractères.
    */
    public class CharValue
    {
        /**
        *	Correspond au caractère.
        */
        public String caractere;
        /**
        *	Luminosité du caractère.
        */
        public int value;
        /**
        *	Construit un CharValue en associant à un caractères une luminosité
         * @param s
         * @param i
         */
        public CharValue(String s,int i)
        {
        caractere=s;
        value=i;
        }
    }