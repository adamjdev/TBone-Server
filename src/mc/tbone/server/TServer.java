package mc.tbone.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionParser;
import mc.tbone.api.Server;
import mc.tbone.api.TBone;
import mc.tbone.api.player.Player;
import mc.tbone.api.world.World;
import windows.prefs.IniFile;

public class TServer implements Server {

	private static String workingDirectory;
	
	private String serverName;
	private String serverMotd;
	private String serverVersion;
	
	private boolean whitelistEnabled;
	
	private int maxPlayers;
	
	private ArrayList< Player > onlinePlayers;
	private ArrayList< World > worlds;
	
	private int port;
	
	private ServerSocket socket;
	
	private String shutdownMessage;
	
	private final String boneVersion = "0.0.1";
	
	public static void main( String[] args )
	{
		System.out.println( "Starting TBone." );
		
		try
		{
			workingDirectory = Paths.get( "" ).toAbsolutePath().toString();
		}
		catch( Exception ex )
		{
			System.out.println( "Could't get current working directory. Server stopping" );
			System.exit( 0 );
		}
		
		OptionParser parser = new OptionParser();
		parser.acceptsAll( Arrays.asList( "w", "world" ) ).withRequiredArg().ofType( String.class );
		
		IniFile config = null;
		File configFile = new File( workingDirectory, "config.ini" );
		
		if( !configFile.exists() )
		{
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				System.out.println( "Couldn't create empty configuration file. Shutting down." );
				System.exit( 0 );
			}
			
			try
			{
				String newLine = System.getProperty( "line.separator");
				
				BufferedWriter s = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( configFile ), "UTF-8" ) );
				s.write( "[info]" + newLine
						+ "server_name=A Minecraft Server " + newLine
						+ "server_motd=A Minecraft Server running on TBone" + newLine
						+ "" + newLine
						+ "[connection]" + newLine
						+ "server_port=25565" + newLine
						+ "max_players=20" );
			}
			catch( IOException ex )
			{
				System.out.println( "Couldn't create default configuration file. Check directory permissions." );
			}
		}
		
		try
		{
			config = new IniFile( workingDirectory + File.separator + "config.ini" );
		} catch (IOException e) {e.printStackTrace();}
		
		if( config != null )
		{
			
			TBone.load( new TServer( config ) );
		}
		else
		{
			System.out.println( "Problem with loading the configuration file." );
			System.exit( 0 );
		}
	}
	
	public TServer( IniFile serverIniConfiguration )
	{
		System.out.println( "TBone v" + boneVersion + " running for Minecraft " + getVersion() );
		
		this.serverName = serverIniConfiguration.getString( "info", "server_name", "Minecraft Server" );
		this.serverMotd = serverIniConfiguration.getString( "info", "server_motd", "A Minecraft Server using TBone Server!" );
		
		this.port = serverIniConfiguration.getInt( "connection", "server_port", 25565 );
		this.maxPlayers = serverIniConfiguration.getInt( "connection", "max_players", 20 );
		
		this.shutdownMessage = serverIniConfiguration.getString( "messages", "shutdown", "Server is stopping" );
		
		try
		{
			socket = new ServerSocket( this.port );
		}
		catch( Exception ex )
		{
			System.out.println( "Couldn't start server socket on port " + this.port + ". Maybe there's already a server running?" );
			System.exit( 0 );
		}
	}
	
	@Override
	public String getName() {
		return serverName;
	}

	@Override
	public String getMotd() {
		return serverMotd;
	}

	@Override
	public String getVersion() {
		return serverVersion;
	}

	@Override
	public boolean whitelistEnabled() {
		return whitelistEnabled;
	}

	@Override
	public int getMaxPlayers() {
		return maxPlayers;
	}

	@Override
	public int getPlayersOnline() {
		return onlinePlayers.size();
	}

	@Override
	public World getWorld( String worldName ) {
		if( worldName != null && worldName != "" )
		{
			for( World w : worlds )
			{
				if( w.getName() == worldName )
					return w;
			}
		}
		return null;
	}

	@Override
	public List<Player> getOnlinePlayers() {
		return onlinePlayers;
	}

	@Override
	public ServerSocket getServerSocket() {
		return socket;
	}

	@Override
	public void shutdown() {
		for( Player p : this.onlinePlayers )
		{
			p.kick( shutdownMessage );
		}
	}

	@Override
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

}
