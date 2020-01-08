package at.alex.BungeeSystem.PunishManager.BanCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.BanManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Date;

public class CheckbanCommand extends Command {

    /*
    Syntax: /checkban <Spieler>
     */

    public CheckbanCommand(String name) {
        super(name, "bungeesystem.punishmanager.checkban", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (BanManager.isRegistred(args[0])) {
                sender.sendMessage(Main.prefix + "§7---------- §4Ban-Infos §7----------");
                if (!BanManager.isBanned(BanManager.getOfflineUUID(args[0]))) {
                    sender.sendMessage("§eName: " + args[0]);
                    sender.sendMessage("§eGebannt: §cNein");
                    sender.sendMessage(Main.prefix + "§7-------------------------------");
                } else {
                    Date banday = new Date(BanManager.getBanDay(BanManager.getOfflineUUID(args[0])));

                    sender.sendMessage("§eName: " + args[0]);
                    sender.sendMessage("§eGebannt: §aJa");
                    sender.sendMessage("§eGrund: §4" + BanManager.getReason(BanManager.getOfflineUUID(args[0])));
                    sender.sendMessage("§eGebannt von: §9" + BanManager.getBanner(BanManager.getOfflineUUID(args[0])));
                    sender.sendMessage("§eGebannt am: §3" + banday.toLocaleString());
                    if (BanManager.getEnd(BanManager.getOfflineUUID(args[0])) != -1) {
                        Date banend = new Date(BanManager.getEnd(BanManager.getOfflineUUID(args[0])));
                        sender.sendMessage("§eEnde: §3" + banend.toLocaleString());
                    } else {
                        sender.sendMessage("§eEnde: §4NIEMALS!!!");
                    }
                    sender.sendMessage(Main.prefix + "§7-------------------------------");
                }

            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/checkban <spieler>"));
    }

}
