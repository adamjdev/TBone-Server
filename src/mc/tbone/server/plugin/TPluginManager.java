package mc.tbone.server.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import mc.tbone.api.plugin.Plugin;
import mc.tbone.api.plugin.PluginManager;
import mc.tbone.server.TServer;

public class TPluginManager implements PluginManager {

	public File pluginDirectory;
	public HashMap< String, Plugin > plugins;
	
	public TPluginManager( File pluginDirectory )
	{
		if( !this.pluginDirectory.isDirectory() )
		{
			throw new IllegalArgumentException( "The given plugin folder is not a directory. Closing server." );
			TServer.getServer().stop();
		}
		this.pluginDirectory = pluginDirectory;
	}
	
	@Override
	public Plugin getPlugin(String pluginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enablePlugin(Plugin plugin) {
		plugin.setEnabled();
	}

	@Override
	public void enablePlugin(String pluginName) {
		if( getPlugin( pluginName ) != null )
			getPlugin( pluginName ).setEnabled();
	}

	@Override
	public void disablePlugin(Plugin plugin) {
		plugin.setDisabled();
	}

	@Override
	public void disablePlugin(String pluginName) {
		if( getPlugin( pluginName ) != null )
			getPlugin( pluginName ).setDisabled();
	}

	@Override
	public Map<String, Plugin> getPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

}
