package Jaint;

/**
 *
 * @author jonas
 */
public class WrongPluginException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -382473127223428020L;
	private String plugin;

	/**
	 * Construit une <code>WrongPluginException</code> avec le plugin invalide specifie.
	 *
	 * @param	plugin	le nom du plugin invalide
	 */
	public WrongPluginException(String plugin)
	{
		this.plugin = plugin;
	}
	/**
	 * Retourne le nom du plugin invalide.
         *
         * @return nom  Nom du plugin invalide
         */
	public String getPlugin()
	{
		return(this.plugin);
	}
}