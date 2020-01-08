package at.alex.BungeeSystem.PunishManager.BanCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.BanManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

    /*
    Syntax: /unban <Spieler> <grund>
     */

    public BanCommand(String name) {
        super(name, "bungeesystem.punishmanager.ban", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String bannername = "";
            if (sender instanceof ProxiedPlayer) {
                bannername = sender.getName();
            } else {
                bannername = "CONSOLE";
            }
            if (BanManager.isRegistred(args[0])) {
                if (Main.banIDs.containsKey(args[1])) {
                    if (sender.hasPermission(BanManager.getIdPermission(args[1]))) {
                        if (!BanManager.isBanned(BanManager.getOfflineUUID(args[0]))) {
                            BanManager.ban(BanManager.getOfflineName(BanManager.getOfflineUUID(args[0])), BanManager.getOfflineUUID(args[0]), BanManager.getIdSeconds(args[1]), BanManager.getIdReason(args[1]), bannername, System.currentTimeMillis());
                            String bm = Main.banMessage;
                            bm = bm.replace("%player%", args[0]);
                            bm = bm.replace("%grund%", BanManager.getIdReason(args[1]));
                            sender.sendMessage(bm);
                        } else sender.sendMessage(Main.isBannedMessage);
                    } else sender.sendMessage(Main.noPerm);
                } else BanManager.sendBanIDs(sender);
            } else sender.sendMessage(Main.neverPlayedBefore);

        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/ban <spieler> <id>"));
    }
}
