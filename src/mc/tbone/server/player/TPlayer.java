package mc.tbone.server.player;

import java.util.UUID;

import mc.tbone.api.TBone;
import mc.tbone.api.player.Player;
import mc.tbone.api.player.PlayerConnection;
import mc.tbone.api.world.Location;

public class TPlayer implements Player {

	private UUID uuid;
	private String name;
	private Location location;
	
	private PlayerConnection conncetion;
	
	@Override
	public UUID getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerConnection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void op() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deop() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWhitelisted() {
		return TBone.getServer().getWhitelist().isWhitelisted( uuid );
	}

	@Override
	public void whitelist() {
		TBone.getServer().getWhitelist().addUUID( uuid );
	}

	@Override
	public void unWhitelist() {
		TBone.getServer().getWhitelist().removeUUID( uuid );
	}

	@Override
	public void kick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void kick(String message) {
		// TODO Auto-generated method stub

	}

}
