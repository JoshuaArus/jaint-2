package JaintPlug.asciiArt;

/**
    *	Sous classe associant une luminosite a chaque caracteres.
    */
    public class CharValue
    {
        /**
        *	Correspond au caractere.
        */
        public String caractere;
        /**
        *	Luminosite du caractere.
        */
        public int value;
        /**
        *	Construit un CharValue en associant a un caracteres une luminosite
         * @param s
         * @param i
         */
        public CharValue(String s,int i)
        {
        caractere=s;
        value=i;
        }
    }