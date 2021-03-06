// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.protection;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import static com.sk89q.worldguard.bukkit.BukkitUtil.*;

/**
 * Timer for handling flags that require a constant timer..
 * 
 * @author Michael
 */
public class TimedFlagsTimer implements Runnable {

    private WorldGuardPlugin plugin;

    private Map<String, TimedFlagPlayerInfo> playerData;

    /**
     * Construct the object.
     * 
     * @param plugin
     */
    public TimedFlagsTimer(WorldGuardPlugin plugin) {
        this.plugin = plugin;
        this.playerData = new HashMap<String, TimedFlagPlayerInfo>();
    }

    private TimedFlagPlayerInfo getPlayerInfo(String name) {
        TimedFlagPlayerInfo ret = playerData.get(name);
        if (ret == null) {
            ret = new TimedFlagPlayerInfo();
            playerData.put(name, ret);
        }

        return ret;
    }

    public void run() {
/*
        // get players
        Player[] players = plugin.getServer().getOnlinePlayers();

        for (Player player : players) {

            TimedFlagPlayerInfo nfo = getPlayerInfo(player.getName());
            long now = System.currentTimeMillis();

            // check healing flag
            if (nfo.sheduledHealTick != null && now >= nfo.sheduledHealTick) {
                player.setHealth(player.getHealth() + nfo.sheduledHealAmount);
                nfo.sheduledHealTick = null;
                nfo.lastHealTick = now;
            }

            if (player.getWorld().getName() == null) {
                continue;
            }
            RegionManager mgr = plugin.getGlobalRegionManager().get(
                    player.getWorld().getName());
            ApplicableRegionSet regions = mgr
                    .getApplicableRegions(toVector(player.getLocation()));

            Integer healDelay = regions.getFlag(DefaultFlag.HEAL_DELAY);

            if (healDelay > 0) {
                healDelay *= 1000;
                int healAmount = regions.getIntegerFlag(
                        DefaultFlag.HEAL_AMOUNT, true).getValue(1);
                if (now - nfo.lastHealTick > healDelay) {
                    if (player.getHealth() < 20) {
                        if (player.getHealth() + healAmount > 20) {
                            player.setHealth(20);
                        } else {
                            player.setHealth(player.getHealth() + healAmount);
                        }
                    }
                } else {
                    nfo.sheduledHealTick = now + healDelay;
                    nfo.sheduledHealAmount = healAmount;
                }
            }

            // check greeting/farewell flag
            String newRegionName = regions.getAffectedRegionId();

            if (newRegionName != null) {

                if (nfo.lastRegion == null
                        || !newRegionName.equals(nfo.lastRegion)) {
                    String newGreetMsg = regions.getStringFlag(
                            DefaultFlag.GREET_MESSAGE, true).getValue();
                    String farewellMsg = regions.getStringFlag(
                            DefaultFlag.FAREWELL_MESSAGE, true).getValue();

                    if (nfo.lastFarewellMsg != null) {
                        player.sendMessage(nfo.lastFarewellMsg);
                        nfo.lastFarewellMsg = null;
                    }
                    if (newGreetMsg != null) {
                        player.sendMessage(newGreetMsg);
                    }
                    if (regions.getBooleanFlag(DefaultFlag.NOTIFY_GREET, false)
                            .getValue(false)) {
                        broadcastNotification(ChatColor.YELLOW + "Player "
                                + player.getName() + " entered region "
                                + newRegionName);
                    }
                    nfo.lastFarewellMsg = farewellMsg;
                    nfo.lastRegion = newRegionName;
                }
            } else {
                if (nfo.lastRegion != null) {
                    if (nfo.lastFarewellMsg != null) {
                        player.sendMessage(nfo.lastFarewellMsg);
                        nfo.lastFarewellMsg = null;
                    }
                    if (regions.getBooleanFlag(DefaultFlag.NOTIFY_FAREWELL,
                            false).getValue(false)) {
                        broadcastNotification(ChatColor.YELLOW + "Player "
                                + player.getName() + " left region "
                                + nfo.lastRegion);
                    }
                    nfo.lastRegion = null;
                }
            }

            // check passthrough flag
            LocalPlayer lplayer = BukkitPlayer.wrapPlayer(plugin, player);
            if (!regions.isStateFlagAllowed(DefaultFlag.PASSTHROUGH, lplayer)) {
                Location newLoc = player.getLocation().clone();
                newLoc.setX(newLoc.getBlockX() - 30);
                newLoc.setY(newLoc.getWorld().getHighestBlockYAt(newLoc) + 1);
                player.teleportTo(newLoc);
            }

        }*/
    }

    public void broadcastNotification(String msg) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (plugin.hasPermission(player, "notify_onenter")) {
                player.sendMessage(msg);
            }
        }
    }

}
