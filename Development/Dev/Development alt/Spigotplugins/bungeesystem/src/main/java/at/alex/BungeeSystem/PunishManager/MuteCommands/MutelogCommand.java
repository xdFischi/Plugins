package at.alex.BungeeSystem.PunishManager.MuteCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Date;
import java.util.HashMap;

public class MutelogCommand extends Command {

    public MutelogCommand(String name) {
        super(name, "bungeesystem.punishmanager.mutelog", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (MuteManager.isRegistred(args[0])) {
                HashMap<Integer, HashMap<String, String>> logs = MuteManager.getLogs(MuteManager.getOfflineUUID(args[0]));
                if (!logs.isEmpty()) {
                    try {
                        int key = Integer.parseInt(args[1]);
                        if (logs.containsKey(key)) {
                            sender.sendMessage(Main.prefix + "§7---------- §4Mute-Log §7----------");
                            sender.sendMessage("§eName: §9" + MuteManager.getOfflineName(MuteManager.getOfflineUUID(args[0])));
                            sender.sendMessage("§eUUID: §9" + MuteManager.getOfflineUUID(args[0]));
                            Date erstellt = new Date(Long.parseLong(logs.get(key).get("gemutedam")));
                            Date aufgehoben = new Date(Long.parseLong(logs.get(key).get("gemutedbis")));
                            sender.sendMessage("§eErstellt am: §9" + erstellt.toLocaleString());
                            sender.sendMessage("§eAufgehoben am: §9" + aufgehoben.toLocaleString());
                            sender.sendMessage("§eAufhebungsgrund: §9" + logs.get(key).get("unmutegrund"));
                            sender.sendMessage("§eMutegrund: §9" + logs.get(key).get("grund"));
                            sender.sendMessage("§eGemuted von: §9" + logs.get(key).get("gemutedvon"));
                            sender.sendMessage("§eEntmuted von: §9" + logs.get(key).get("unmutedvon"));
                            sender.sendMessage("§eSeite: §6" + args[1] + " von " + logs.size());
                            sender.sendMessage(Main.prefix + "-----------------------------");
                        } else {
                            sender.sendMessage(Main.prefix + "§cVerwende eine Zahl zwischen §61 §cund §6" + logs.size() + "§c!");
                        }
                    } catch (Exception e) {
                        sender.sendMessage(Main.prefix + "§cVerwende eine Zahl zwischen §61 §cund §6" + logs.size() + "§c!");
                    }
                } else {
                    sender.sendMessage(Main.prefix + "§cDieser Spieler wurde noch nie gemuted!");
                }
            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/mutelog <spieler> <seite>"));
    }

}
