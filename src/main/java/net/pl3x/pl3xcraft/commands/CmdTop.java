package net.pl3x.pl3xcraft.commands;

import net.pl3x.pl3xcraft.Pl3xCraft;
import net.pl3x.pl3xcraft.configuration.Lang;
import net.pl3x.pl3xcraft.task.TeleportSounds;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTop implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Lang.send(sender, Lang.PLAYER_COMMAND);
            return true;
        }

        if (!sender.hasPermission("command.top")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        Player player = (Player) sender;
        Location targetLoc = player.getLocation();
        targetLoc.setY(player.getWorld().getHighestBlockAt(targetLoc).getLocation().getY() + 1);

        new TeleportSounds(targetLoc, player.getLocation())
                .runTaskLater(Pl3xCraft.getInstance(), 1);

        player.teleportAsync(targetLoc).thenAccept(result ->
                Lang.send(sender, Lang.TELEPORTING_TOP));
        return true;
    }
}
