package JaintPlug;

/**
 *
 * @author Jonas
 */
import Jaint.*;
/**
 *
 * @author Joshua
 */
public class CompteARebourds extends Thread
{
    private int temps;
    private Plugin plug;

   
    /**
     *
     * @param t
     * @param p
     */
    public CompteARebourds(int t, Plugin p)
    {
        super();
        this.temps = t;
        this.plug = p;
    }
    
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(this.temps);
            ThreadEffet effect = new ThreadEffet(this.plug);
            effect.start();
        }
        catch (InterruptedException e)
        {
                    
        }




    }
}

