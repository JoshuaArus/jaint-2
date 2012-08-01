package JaintPlug;

import Jaint.*;

/**
 *
 * @author Jonas
 */
class ThreadEffet extends Thread
{
    private Plugin plug;
    


    ThreadEffet(Plugin p)
    {
        super();
        this.plug = p;


    }

    @Override
    public void run()
    {
        if(this.plug instanceof Grain)
        {
            Grain g  = (Grain) this.plug;
            g.setJsValue();
            g.previsualiser();
        }

        if(this.plug instanceof PeintureHuile)
        {

            PeintureHuile ph = (PeintureHuile) this.plug;
            ph.setJsIntValue();
            ph.setJsRayValue();
            ph.previsualiser();

        }


        
    }
}
