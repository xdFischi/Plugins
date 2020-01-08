package at.alex.BungeeSystem.PunishManager.BanCommands;

import at.alex.BungeeSystem.Main;
import at.alex.BungeeSystem.PunishManager.BanManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Date;
import java.util.HashMap;

public class BanlogCommand extends Command {


    public BanlogCommand(String name) {
        super(name, "bungeesystem.punishmanager.banlog", new String[]{});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            if (BanManager.isRegistred(args[0])) {
                HashMap<Integer, HashMap<String, String>> logs = BanManager.getLogs(BanManager.getOfflineUUID(args[0]));
                if (!logs.isEmpty()) {
                    try {
                        int key = Integer.parseInt(args[1]);
                        if (logs.containsKey(key)) {
                            sender.sendMessage(Main.prefix + "§7---------- §4Ban-Log §7----------");
                            sender.sendMessage("§eName: §9" + BanManager.getOfflineName(BanManager.getOfflineUUID(args[0])));
                            sender.sendMessage("§eUUID: §9" + BanManager.getOfflineUUID(args[0]));
                            Date erstellt = new Date(Long.parseLong(logs.get(key).get("gebanntam")));
                            Date aufgehoben = new Date(Long.parseLong(logs.get(key).get("gebanntbis")));
                            sender.sendMessage("§eErstellt am: §9" + erstellt.toLocaleString());
                            sender.sendMessage("§eAufgehoben am: §9" + aufgehoben.toLocaleString());
                            sender.sendMessage("§eAufhebungsgrund: §9" + logs.get(key).get("unbangrund"));
                            sender.sendMessage("§eBangrund: §9" + logs.get(key).get("grund"));
                            sender.sendMessage("§eGebannt von: §9" + logs.get(key).get("gebanntvon"));
                            sender.sendMessage("§eEntbannt von: §9" + logs.get(key).get("unbannedvon"));
                            sender.sendMessage("§eSeite: §6" + args[1] + " von " + logs.size());
                            sender.sendMessage(Main.prefix + "-----------------------------");
                        } else {
                            sender.sendMessage(Main.prefix + "§cVerwende eine Zahl zwischen §61 §cund §6" + logs.size() + "§c!");
                        }
                    } catch (Exception e) {
                        sender.sendMessage(Main.prefix + "§cVerwende eine Zahl zwischen §61 §cund §6" + logs.size() + "§c!");
                    }
                } else {
                    sender.sendMessage(Main.prefix + "§cDieser Spieler wurde noch nie gebannt!");
                }
            } else sender.sendMessage(Main.neverPlayedBefore);
        } else sender.sendMessage(Main.usageMessage.replace("%usage%", "/banlog <spieler> <seite>"));
    }

}



















