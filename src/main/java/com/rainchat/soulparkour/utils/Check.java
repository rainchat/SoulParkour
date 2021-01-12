package com.rainchat.soulparkour.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;

public class Check {
    public static boolean isATunnel(Player p) {
        if (!(getBlock(p, 1, 1).isAir() || (getBlock(p, 1, 1).name().contains("WALL") || getBlock(p, 1, 1).name().contains("FENCE"))) && getBlock(p, 1, 0).isAir()) {
            return true;
        }
        return false;
    }


    public static boolean isAWall(Player p) {
        if ((getBlock(p, 0, 1).name().contains("WALL") || getBlock(p, 0, 1).name().contains("FENCE")) && getBlock(p, 0, 0).isAir()) {
            return true;
        }
        return false;
    }

    public static Material getBlock(Player p, int XZ, int Y) {
        Location loc = p.getLocation();
        if (p.getFacing().equals(BlockFace.NORTH)) {
            loc.add(0, Y, -XZ);
        } else if (p.getFacing().equals(BlockFace.SOUTH)) {
            loc.add(0, Y, XZ);
        } else if (p.getFacing().equals(BlockFace.EAST)) {
            loc.add(XZ, Y, 0);
        } else if (p.getFacing().equals(BlockFace.WEST)) {
            loc.add(-XZ, Y, 0);
        }
        return loc.getBlock().getType();
    }

    public static boolean canGoTrough(Location loc) {
        if (loc.getBlock().isEmpty()) {
            return true;
        } else if (!loc.getBlock().getType().name().contains("CARPET") && !loc.getBlock().getType().name().contains("TRAPDOOR") && !loc.getBlock().getType().name().contains("TRAP_DOOR") && !loc.getBlock().getType().equals(Material.REPEATER) && !loc.getBlock().getType().equals(Material.COMPARATOR) && !loc.getBlock().getType().equals(Material.SCAFFOLDING)) {
            if (loc.getBlock().getType().equals(Material.SNOW)) {
                Snow snow = (Snow) loc.getBlock().getBlockData();
                if (snow.getLayers() < 5) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean checkTargetBlock(Player p) {
        Block block = p.getLocation().getBlock();

        if (p.getFacing().equals(BlockFace.NORTH)) {
            block = p.getLocation().clone().add(0, 0, -1).getBlock();
        } else if (p.getFacing().equals(BlockFace.SOUTH)) {
            block = p.getLocation().clone().add(0, 0, 1).getBlock();
        } else if (p.getFacing().equals(BlockFace.EAST)) {
            block = p.getLocation().clone().add(1, 0, 0).getBlock();
        } else if (p.getFacing().equals(BlockFace.WEST)) {
            block = p.getLocation().clone().add(-1, 0, 0).getBlock();
        }
        Block block2 = p.getLocation().clone().add(0, -1, 0).getBlock();

        if (block.isEmpty() || !block2.getType().isAir()) {
            return false;
        }
        Location bar = block2.getLocation();
        if (p.getLocation().distance(block.getLocation()) <= 1.1 && (p.getFacing().equals(BlockFace.SOUTH) || p.getFacing().equals(BlockFace.EAST))) {
            return true;
        } else if (p.getLocation().distance(block.getLocation()) <= 2.1 && (p.getFacing().equals(BlockFace.WEST) || p.getFacing().equals(BlockFace.NORTH))) {
            return true;
        } else {
            return false;
        }


    }

}
