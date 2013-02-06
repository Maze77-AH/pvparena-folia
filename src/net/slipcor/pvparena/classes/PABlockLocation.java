package net.slipcor.pvparena.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <pre>
 * PVP Arena Location class
 * </pre>
 * 
 * A simple wrapper of the Bukkit Location, only calculating blocks
 * 
 * @author slipcor
 * 
 * @version v0.9.1
 */

public class PABlockLocation {
	private final String world;
	private int x;
	private int y;
	private int z;

	public PABlockLocation(final String world, final int x, final int y, final int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public PABlockLocation(final Location bukkitLocation) {
		this.world = bukkitLocation.getWorld().getName();
		this.x = bukkitLocation.getBlockX();
		this.y = bukkitLocation.getBlockY();
		this.z = bukkitLocation.getBlockZ();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		
		final PABlockLocation location = (PABlockLocation) other;
		
		if (this.world != location.world && (this.world == null || !this.world.equals(location.world))) {
			return false;
		}
		
		if (this.x != location.x) {
			return false;
		}
		
		if (this.y != location.y) {
			return false;
		}
		
		if (this.z != location.z) {
			return false;
		}
		
		return true;
	}

	public double getDistance(final PABlockLocation otherLocation) {
		if (otherLocation == null) {
			throw new IllegalArgumentException(
					"Cannot measure distance to a null location");
		}
		if (!otherLocation.world.equals(world)) {
			throw new IllegalArgumentException(
					"Cannot measure distance between " + world + " and "
							+ otherLocation.world);
		}

		return Math.sqrt(Math.pow(this.x - otherLocation.x, 2.0D)
				+ Math.pow(this.y - otherLocation.y, 2.0D) + Math.pow(this.z - otherLocation.z, 2.0D));
	}

	public PABlockLocation getMidpoint(final PABlockLocation location) {
		return new PABlockLocation(world, (x + location.x) / 2, (y + location.y) / 2,
				(z + location.z) / 2);
	}

	public String getWorldName() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public boolean isInAABB(final PABlockLocation min, final PABlockLocation max) {
		if (this.getX() < min.getX() || this.getX() > max.getX()) {
			return false;
		}
		if (this.getY() < min.getY() || this.getY() > max.getY()) {
			return false;
		}
		if (this.getZ() < min.getZ() || this.getZ() > max.getZ()) {
			return false;
		}
		return true;
	}

	public PABlockLocation pointTo(final PABlockLocation dest, final Double length) {
		final Vector source = new Vector(x, y, z);
		final Vector destination = new Vector(dest.x, dest.y, dest.z);

		Vector goal = source.subtract(destination);

		goal = goal.normalize().multiply(length);

		return new PABlockLocation(world, x + x + goal.getBlockX(), y
				+ goal.getBlockY(), z + goal.getBlockZ());
	}

	public void setX(final int value) {
		x = value;
	}

	public void setY(final int value) {
		y = value;
	}

	public void setZ(final int value) {
		z = value;
	}

	public Location toLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z);
	}

	@Override
	public String toString() {
		return world + ":" + x + "," + y + "," + z;
	}
}
