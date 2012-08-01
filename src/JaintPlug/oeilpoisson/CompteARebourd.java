

package JaintPlug.oeilpoisson;

/**
 *
 * @author jonas
 */
public class CompteARebourd extends Thread
{
    private int temps;

    /**
     *
     * @param t
     */
    public CompteARebourd(int t)
    {
        super();
        this.temps = t;
    }
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(this.temps);
        }
        catch (InterruptedException e)
        {
                    e.printStackTrace();
        }


    }
}
