package at.alex.BungeeSystem.PunishManager.MuteCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Date;

public class CheckmuteCommand extends Command {

    public CheckmuteCommand(String name) {
        super(name, "bungeesystem.punishmanager.checkban", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (MuteManager.isRegistred(args[0])) {
                sender.sendMessage(Main.prefix + "§7---------- §4Mute-Infos §7----------");
                if (!MuteManager.isMute(MuteManager.getOfflineUUID(args[0]))) {
                    sender.sendMessage("§eName: " + args[0]);
                    sender.sendMessage("§eGemuted: §cNein");
                    sender.sendMessage(Main.prefix + "§7-------------------------------");
                } else {
                    Date banday = new Date(MuteManager.getMuteDay(MuteManager.getOfflineUUID(args[0])));

                    sender.sendMessage("§eName: " + args[0]);
                    sender.sendMessage("§eGemuted: §aJa");
                    sender.sendMessage("§eGrund: §4" + MuteManager.getReason(MuteManager.getOfflineUUID(args[0])));
                    sender.sendMessage("§eGemuted von: §9" + MuteManager.getMuter(MuteManager.getOfflineUUID(args[0])));
                    sender.sendMessage("§eGemuted am: §3" + banday.toLocaleString());
                    if (MuteManager.getEnd(MuteManager.getOfflineUUID(args[0])) != -1) {
                        Date banend = new Date(MuteManager.getEnd(MuteManager.getOfflineUUID(args[0])));
                        sender.sendMessage("§eEnde: §3" + banend.toLocaleString());
                    } else {
                        sender.sendMessage("§eEnde: §4NIEMALS!!!");
                    }
                    sender.sendMessage(Main.prefix + "§7-------------------------------");
                }

            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/checkmute <spieler>"));
    }

}
