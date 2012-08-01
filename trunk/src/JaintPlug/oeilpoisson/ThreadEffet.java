

package JaintPlug.oeilpoisson;

import JaintPlug.*;


/**
 *
 * @author Jeff
 */
class ThreadEffet extends Thread
{
    private OeilDePoisson odp;


    ThreadEffet(OeilDePoisson odp)
    {
        super();
        this.odp = odp;
    }

    @Override
    public void run()
    {
         this.odp.setZoom();
         this.odp.previsualiser();
    }

}
