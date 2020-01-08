package at.alex.Lobby.main;

import at.alex.Lobby.Coins.CoinCommands;
import at.alex.Lobby.MySQL.MySQL;
import at.alex.Lobby.OnlineTime.OnTimeAPI;
import at.alex.Lobby.Scoreboard.scoreUpdate;
import at.alex.Lobby.Tablist.TablistAPI;
import at.alex.Lobby.Tablist.tabUpdate;
import at.alex.Lobby.Utils.FileManager;
import at.alex.Lobby.commands.*;
import at.alex.Lobby.listeners.ClickListener;
import at.alex.Lobby.listeners.CoolerListener;
import at.alex.Lobby.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static HashMap<String, ItemStack[]> inventorys = new HashMap<>();
    public static HashMap<String, String> rank = new HashMap<>();
    private static String prefix = "§8[§5Lobby§8] ";
    private static String noperm = "§cDazu hast du keine Rechte!";

    private static List<String> pinbuild = new ArrayList<>();
    private static List<Player> inSpec = new ArrayList<>();
    private static List<String> allPlayers = new ArrayList<>();
    private static List<String> inCheck = new ArrayList<>();
    private static List<Player> team = new ArrayList<>();
    private static List<Player> hideAll = new ArrayList<>();
    private static List<Player> showTeam = new ArrayList<>();
    private static List<Player> silent = new ArrayList<>();


    @Override
    public void onEnable() {
        instance = this;
        plugin = this;
        registerCommands();
        registerEvents();
        createInv();
        createTabTeams();
        FileManager.setStandartMySQL();
        FileManager.readMySQL();
        MySQL.connect();
        MySQL.createTable();
        OnTimeAPI.startTimer();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin enabled!");
    }

    @Override
    public void onDisable() {
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage(prefix + "§cPlugin disabled!");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new ClickListener(), this);
        pm.registerEvents(new CoolerListener(), this);
    }

    private void registerCommands() {
        SetLoc loc = new SetLoc(this);
        getCommand("setspawn").setExecutor(loc);
        getCommand("setgame").setExecutor(loc);
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("spec").setExecutor(new specCommand());
        getCommand("gm").setExecutor(new GMCommand());
        getCommand("check").setExecutor(new checkCommand());
        getCommand("adminitems").setExecutor(new GiveAdminItems());
        getCommand("tabreload").setExecutor(new tabUpdate());
        getCommand("rank").setExecutor(new groupChangeCommand());
        getCommand("scorereload").setExecutor(new scoreUpdate());
        CoinCommands coinCommands = new CoinCommands();
        getCommand("setcoins").setExecutor(coinCommands);
        getCommand("addcoins").setExecutor(coinCommands);
        getCommand("removecoins").setExecutor(coinCommands);
        getCommand("coins").setExecutor(coinCommands);
    }

    private static void createTabTeams() {
        TablistAPI teams = new TablistAPI();
        teams.create("owner", 10, "§4Owner §8»§7 ", null, "lobby.tablist.owner");
        teams.create("sadmin", 11, "§4Admin §8»§7 ", null, "lobby.tablist.superadmin");
        teams.create("admin", 12, "§cAdmin §8»§7 ", null, "lobby.tablist.admin");
        teams.create("dev", 13, "§dDev §8»§7 ", null, "lobby.tablist.developer");
        teams.create("smod", 14, "§cMod §8»§7 ", null, "lobby.tablist.supermoderator");
        teams.create("mod", 15, "§9Mod §8»§7 ", null, "lobby.tablist.moderator");
        teams.create("sarchi", 16, "§2Archi §8»§7 ", null, "lobby.tablist.superarchitekt");
        teams.create("archi", 17, "§aArchi §8»§7 ", null, "lobby.tablist.architekt");
        teams.create("team", 18, "§3Team §8»§7 ", null, "lobby.tablist.team");
        teams.create("sup", 19, "§bSup §8»§7 ", null, "lobby.tablist.supporter");
        teams.create("testsup", 20, "§bT-Sup §8»§7 ", null, "lobby.tablist.testsupporter");
        teams.create("tdev", 21, "§dT-Dev §8»§7 ", null, "lobby.tablist.testdeveloper");
        teams.create("streamer", 22, "§5Twitch §8»§7 ", null, "lobby.tablist.streamer");
        teams.create("yt", 22, "§5YT §8»§7 ", null, "lobby.tablist.youtuber");
        teams.create("diamond", 23, "§3Dia §8»§7 ", null, "lobby.tablist.diamond");
        teams.create("platin", 24, "§fPlatin §8»§7 ", null, "lobby.tablist.platin");
        teams.create("vip", 25, "§6VIP §8»§7 ", null, "lobby.tablist.vip");
        teams.create("player", 26, "§7", null, null);
    }

    public static Main instance;



    public static Main getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoperm() {
        return noperm;
    }

    public static HashMap<String, ItemStack[]> getInventorys() {
        return inventorys;
    }

    public static void setBuild(String uuid) {
        pinbuild.add(uuid);
    }

    public static void remBuild(String uuid) {
        pinbuild.remove(uuid);
    }

    public static boolean isBuild(String uuid) {
        if (pinbuild.contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    public static void leaveBuild(Player p) {
        remBuild(p.getUniqueId().toString());
        p.setGameMode(GameMode.ADVENTURE);
        p.sendMessage(getPrefix() + "§7Build ist nun §cdeaktiviert§7!");
        setInv(p);
    }

    public static void setSpec(Player p) {
        inSpec.add(p);
    }

    public static void remSpec(Player p) {
        inSpec.remove(p);
    }

    public static boolean isSpec(Player p) {
        if (inSpec.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Player> getSpec() {
        return inSpec;
    }

    public static void setAllP(String uuid) {
        allPlayers.add(uuid);
    }

    public static void remAllP(String uuid) {
        allPlayers.remove(uuid);
    }

    public static boolean isAllP(String uuid) {
        if (allPlayers.contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> getAll() {
        return allPlayers;
    }

    public static void setInv(Player p) {
        if (p.hasPermission("lobby.inventory.admin")) {
            p.getInventory().setContents(inventorys.get("admin"));
        } else if (p.hasPermission("lobby.inventory.youtube")) {
            p.getInventory().setContents(inventorys.get("youtube"));
        } else {
            p.getInventory().setContents(inventorys.get("standart"));
        }
    }

    public static void setCheck(String uuid) {
        inCheck.add(uuid);
    }

    public static void remCheck(String uuid) {
        inCheck.remove(uuid);
    }

    public static boolean isCheck(String uuid) {
        if (inCheck.contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    public static void addTeam(Player p) {
        team.add(p);
    }

    public static void remTeam(Player p) {
        team.remove(p);
    }

    public static boolean isTeam(Player p) {
        if (team.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Player> getTeam() {
        return team;
    }

    public static void addhideAll(Player p) {
        hideAll.add(p);
    }

    public static void remhideAll(Player p) {
        hideAll.remove(p);
    }

    public static boolean ishideall(Player p) {
        if (hideAll.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Player> getHideAll() {
        return hideAll;
    }

    public static void addshowTeam(Player p) {
        showTeam.add(p);
    }

    public static void remmshowTeam(Player p) {
        showTeam.remove(p);
    }

    public static boolean isshowTeam(Player p) {
        if (showTeam.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Player> getShowTeam() {
        return showTeam;
    }

    public static void addSilent(Player p) {
        silent.add(p);
    }

    public static void remSilent(Player p) {
        silent.remove(p);
    }

    public static boolean isSilent(Player p) {
        if (silent.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Player> getSilent() {
        return silent;
    }

    public static void leaveSpec(Player p) {
        remSpec(p);
        //Show Player for all
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(p);
        }
        for (Player vanished : Main.getSpec()) {
            p.hidePlayer(vanished);
        }

        p.setAllowFlight(false);
        p.getInventory().clear();
        setInv(p);
        p.sendMessage(getPrefix() + "§7Spec wurde §cdeaktiviert§7!");
    }

    private void createInv() {
        //Items
        //Teleporter
        ItemStack cp = new ItemStack(Material.COMPASS);
        ItemMeta cpmeta = cp.getItemMeta();
        cpmeta.setDisplayName("§aTeleporter");
        cp.setItemMeta(cpmeta);
        //Spieler verstecken
        ItemStack sv = new ItemStack(Material.BLAZE_ROD);
        ItemMeta svmeta = sv.getItemMeta();
        svmeta.setDisplayName("§6Spieler verstecken");
        sv.setItemMeta(svmeta);
        //Gadgets
        ItemStack gg = new ItemStack(Material.CHEST);
        ItemMeta ggmeta = gg.getItemMeta();
        ggmeta.setDisplayName("§6Gadgets §7(§cWartung§7)");
        gg.setItemMeta(ggmeta);
        //Auto-Nick
        ItemStack an = new ItemStack(Material.NAME_TAG);
        ItemMeta anmeta = an.getItemMeta();
        anmeta.setDisplayName("§6Auto-Nick §7(§cWartung§7)");
        an.setItemMeta(anmeta);
        //Silent-Lobby
        ItemStack sl = new ItemStack(Material.TNT);
        ItemMeta slmeta = sl.getItemMeta();
        slmeta.setDisplayName("§5Silent-Lobby");
        sl.setItemMeta(slmeta);
        //Lobbies
        ItemStack lo = new ItemStack(Material.NETHER_STAR);
        ItemMeta lometa = lo.getItemMeta();
        lometa.setDisplayName("§aLobbies §7(§cWartung§7)");
        lo.setItemMeta(lometa);
        //Freunde
        ItemStack fr = new ItemStack(Material.SKULL_ITEM);
        ItemMeta frmeta = fr.getItemMeta();
        frmeta.setDisplayName("§6Freunde §7(§cWartung§7)");
        fr.setItemMeta(frmeta);
        //Fly
        ItemStack fl = new ItemStack(Material.FEATHER);
        ItemMeta flmeta = fl.getItemMeta();
        flmeta.setDisplayName("§9Fly");
        fl.setItemMeta(flmeta);
        //Gamemodes
        ItemStack gm = new ItemStack(Material.COMMAND);
        ItemMeta gmmeta = gm.getItemMeta();
        gmmeta.setDisplayName("§4GAMEMODES");
        gm.setItemMeta(gmmeta);
        //Shield
        ItemStack sh = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta shmeta = sh.getItemMeta();
        shmeta.setDisplayName("§5Shield");
        sh.setItemMeta(shmeta);


        //Create inventory standart
        Inventory standart = getServer().createInventory(null, 9 * 4);
        standart.setItem(0, cp);
        standart.setItem(1, sv);
        standart.setItem(3, gg);
        standart.setItem(7, lo);
        standart.setItem(8, fr);

        //Add items to inventory standart
        inventorys.put("standart", standart.getContents());

        //Create Inventory youtube
        Inventory youtube = getServer().createInventory(null, 9 * 4);
        youtube.setItem(0, cp);
        youtube.setItem(1, sv);
        youtube.setItem(3, gg);
        youtube.setItem(4, an);
        youtube.setItem(5, sl);
        youtube.setItem(7, lo);
        youtube.setItem(8, fr);
        youtube.setItem(31, fl);

        //Add items to inventory youtube
        inventorys.put("youtube", youtube.getContents());

        //Create inventory admin
        Inventory admin = getServer().createInventory(null, 9 * 4);
        admin.setItem(0, cp);
        admin.setItem(1, sv);
        admin.setItem(3, gg);
        admin.setItem(4, an);
        admin.setItem(5, sl);
        admin.setItem(7, lo);
        admin.setItem(8, fr);
        admin.setItem(22, gm);
        admin.setItem(30, fl);
        admin.setItem(32, sh);

        //Add items to inventory admin
        inventorys.put("admin", admin.getContents());

        //Create inventory teleport
        //Spawn item
        ItemStack spawn = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta spawnmeta = spawn.getItemMeta();
        spawnmeta.setDisplayName("§6Spawn");
        spawn.setItemMeta(spawnmeta);
        //Dev item 1
        ItemStack dev = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta devmeta = dev.getItemMeta();
        devmeta.setDisplayName("§bDev-Server");
        dev.setItemMeta(devmeta);
        //Build 1
        ItemStack build = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta buildmeta = build.getItemMeta();
        buildmeta.setDisplayName("§2Build-Server");
        build.setItemMeta(buildmeta);

        Inventory teleporter = getServer().createInventory(null, 3 * 9);
        teleporter.setItem(13, spawn);
        teleporter.setItem(11, dev);
        teleporter.setItem(15, build);
        inventorys.put("teleporter", teleporter.getContents());

        //Create inventory Spielerverstecken
        ItemStack greenstack = new ItemStack(Material.INK_SACK, 1, DyeColor.PURPLE.getData());//LIME --> PURPLE
        ItemMeta greenmeta = greenstack.getItemMeta();
        greenmeta.setDisplayName("§aAlle Spieler anzeigen");
        greenstack.setItemMeta(greenmeta);

        ItemStack pinkstack = new ItemStack(Material.INK_SACK, 1, DyeColor.CYAN.getData());//CYAN --> PINK
        ItemMeta pinkmeta = pinkstack.getItemMeta();
        pinkmeta.setDisplayName("§eNur Teammitglieder anzeigen");
        pinkstack.setItemMeta(pinkmeta);

        ItemStack redstack = new ItemStack(Material.INK_SACK, 1, DyeColor.ORANGE.getData());//ORANGE --> RED
        ItemMeta redmeta = redstack.getItemMeta();
        redmeta.setDisplayName("§cKeine Spieler anzeigen");
        redstack.setItemMeta(redmeta);

        Inventory verstecken = getServer().createInventory(null, 1 * 9);
        verstecken.setItem(2, greenstack);
        verstecken.setItem(4, pinkstack);
        verstecken.setItem(6, redstack);

        inventorys.put("spielerverstecken", verstecken.getContents());

        //Create inventory gamemodes
        //CREATIVE
        ItemStack cr = new ItemStack(Material.GRASS);
        ItemMeta crmeta = cr.getItemMeta();
        crmeta.setDisplayName("§aCREATIVE");
        cr.setItemMeta(crmeta);
        //SURVIVAL
        ItemStack su = new ItemStack(Material.GOLD_SWORD);
        ItemMeta sumeta = su.getItemMeta();
        sumeta.setDisplayName("§aSURVIVAL");
        su.setItemMeta(sumeta);
        //ADVENTURE
        ItemStack ad = new ItemStack(Material.BOAT);
        ItemMeta admeta = ad.getItemMeta();
        admeta.setDisplayName("§aADVENTURE");
        ad.setItemMeta(admeta);
        //SPECTATOR
        ItemStack sp = new ItemStack(Material.POTION, 1, (short) 8270);
        ItemMeta spmeta = sp.getItemMeta();
        spmeta.setDisplayName("§aSPECTATOR");
        sp.setItemMeta(spmeta);

        Inventory gamemodes = getServer().createInventory(null, 1 * 9);
        gamemodes.setItem(1, cr);
        gamemodes.setItem(3, su);
        gamemodes.setItem(5, ad);
        gamemodes.setItem(7, sp);
        inventorys.put("gamemodes", gamemodes.getContents());

        //Create inventory spectator
        ItemStack sc = new ItemStack(Material.COMPASS);
        ItemMeta scmeta = sc.getItemMeta();
        scmeta.setDisplayName("§6Teleporter");
        sc.setItemMeta(scmeta);

        ItemStack sleave = new ItemStack(Material.REDSTONE);
        ItemMeta sleavemeta = sleave.getItemMeta();
        sleavemeta.setDisplayName("§cSpectator verlassen");
        sleave.setItemMeta(sleavemeta);

        ItemStack scheck = new ItemStack(Material.STICK);
        ItemMeta scheckmeta = scheck.getItemMeta();
        scheckmeta.setDisplayName("§6Check");
        scheck.setItemMeta(scheckmeta);

        ItemStack ban = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta banmeta = ban.getItemMeta();
        banmeta.setDisplayName("§4Ban-Sword");
        banmeta.spigot().setUnbreakable(true);
        ban.setItemMeta(banmeta);

        ItemStack mute = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta mutemeta = mute.getItemMeta();
        mutemeta.setDisplayName("§4Mute-Axe");
        mutemeta.spigot().setUnbreakable(true);
        mute.setItemMeta(mutemeta);
        //Check
        ItemStack check = new ItemStack(Material.STICK);
        ItemMeta checkmeta = check.getItemMeta();
        checkmeta.setDisplayName("§6Check");
        check.setItemMeta(checkmeta);

        //Spec Normal
        Inventory specNormal = getServer().createInventory(null, 4 * 9);
        specNormal.setItem(0, sc);
        specNormal.setItem(8, sleave);
        inventorys.put("specNormal", specNormal.getContents());
        //Spec Mute
        Inventory specMute = getServer().createInventory(null, 4 * 9);
        specMute.setItem(0, sc);
        specMute.setItem(4, check);
        specMute.setItem(8, sleave);
        inventorys.put("specCheck", specMute.getContents());
        //Spec Check
        Inventory specCheck = getServer().createInventory(null, 4 * 9);
        specCheck.setItem(0, sc);
        specCheck.setItem(3, mute);
        specCheck.setItem(5, check);
        specCheck.setItem(8, sleave);
        inventorys.put("specMute", specCheck.getContents());
        //Spec Ban
        Inventory specBan = getServer().createInventory(null, 4 * 9);
        specBan.setItem(0, sc);
        specBan.setItem(5, mute);
        specBan.setItem(4, check);
        specBan.setItem(3, ban);
        specBan.setItem(8, sleave);
        inventorys.put("specBan", specBan.getContents());

        //Rank-Inventory
        ItemStack owner = new ItemStack(Material.PAPER);
        ItemMeta ownermeta = owner.getItemMeta();
        ownermeta.setDisplayName("§l§4Owner");
        owner.setItemMeta(ownermeta);

        ItemStack sadmin = new ItemStack(Material.PAPER);
        ItemMeta sadminmeta = sadmin.getItemMeta();
        sadminmeta.setDisplayName("§l§4Super-Admin");
        sadmin.setItemMeta(sadminmeta);

        ItemStack radmin = new ItemStack(Material.PAPER);
        ItemMeta radminmeta = radmin.getItemMeta();
        radminmeta.setDisplayName("§l§cAdmin");
        radmin.setItemMeta(radminmeta);

        ItemStack developer = new ItemStack(Material.PAPER);
        ItemMeta developermeta = developer.getItemMeta();
        developermeta.setDisplayName("§l§dDeveloper");
        developer.setItemMeta(developermeta);

        ItemStack smod = new ItemStack(Material.PAPER);
        ItemMeta smodmeta = smod.getItemMeta();
        smodmeta.setDisplayName("§l§cSuper-Moderator");
        smod.setItemMeta(smodmeta);

        ItemStack sarchi = new ItemStack(Material.PAPER);
        ItemMeta sarchimeta = sarchi.getItemMeta();
        sarchimeta.setDisplayName("§l§2Super-Architekt");
        sarchi.setItemMeta(sarchimeta);

        ItemStack mod = new ItemStack(Material.PAPER);
        ItemMeta modmeta = mod.getItemMeta();
        modmeta.setDisplayName("§l§9Moderator");
        mod.setItemMeta(modmeta);

        ItemStack archi = new ItemStack(Material.PAPER);
        ItemMeta archimeta = archi.getItemMeta();
        archimeta.setDisplayName("§l§aArchitekt");
        archi.setItemMeta(archimeta);

        ItemStack team = new ItemStack(Material.PAPER);
        ItemMeta teammeta = team.getItemMeta();
        teammeta.setDisplayName("§l§3Team");
        team.setItemMeta(teammeta);

        ItemStack sup = new ItemStack(Material.PAPER);
        ItemMeta supmeta = sup.getItemMeta();
        supmeta.setDisplayName("§l§bSupporter");
        sup.setItemMeta(supmeta);

        ItemStack tdev = new ItemStack(Material.PAPER);
        ItemMeta tdevmeta = tdev.getItemMeta();
        tdevmeta.setDisplayName("§l§dTest-Developer");
        tdev.setItemMeta(tdevmeta);

        ItemStack tsup = new ItemStack(Material.PAPER);
        ItemMeta tsupmeta = tsup.getItemMeta();
        tsupmeta.setDisplayName("§l§bTest-Supporter");
        tsup.setItemMeta(tsupmeta);

        ItemStack streamer = new ItemStack(Material.PAPER);
        ItemMeta streamermeta = streamer.getItemMeta();
        streamermeta.setDisplayName("§l§5Streamer");
        streamer.setItemMeta(streamermeta);

        ItemStack yt = new ItemStack(Material.PAPER);
        ItemMeta ytmeta = yt.getItemMeta();
        ytmeta.setDisplayName("§l§5Youtuber");
        yt.setItemMeta(ytmeta);

        ItemStack diamond = new ItemStack(Material.PAPER);
        ItemMeta diamondmeta = diamond.getItemMeta();
        diamondmeta.setDisplayName("§l§3Diamond");
        diamond.setItemMeta(diamondmeta);

        ItemStack platin = new ItemStack(Material.PAPER);
        ItemMeta platinmeta = platin.getItemMeta();
        platinmeta.setDisplayName("§l§fPlatin");
        platin.setItemMeta(platinmeta);

        ItemStack vip = new ItemStack(Material.PAPER);
        ItemMeta vipmeta = vip.getItemMeta();
        vipmeta.setDisplayName("§l§6VIP");
        vip.setItemMeta(vipmeta);

        ItemStack player = new ItemStack(Material.PAPER);
        ItemMeta playermeta = player.getItemMeta();
        playermeta.setDisplayName("§l§7Player");
        player.setItemMeta(playermeta);

        //Glass
        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glasspanemeta = glassPane.getItemMeta();
        glasspanemeta.setDisplayName("");
        glassPane.setItemMeta(glasspanemeta);

        Inventory ranks = Bukkit.getServer().createInventory(null, 5 * 9);
        //Reihe 1
        ranks.setItem(0, glassPane);
        ranks.setItem(1, player);
        ranks.setItem(3, vip);
        ranks.setItem(5, platin);
        ranks.setItem(7, diamond);
        ranks.setItem(8, glassPane);
        //Reihe 2
        ranks.setItem(9, glassPane);
        ranks.setItem(11, yt);
        ranks.setItem(13, streamer);
        ranks.setItem(15, tdev);
        ranks.setItem(17, glassPane);
        //Reihe 3
        ranks.setItem(18, glassPane);
        ranks.setItem(19, tsup);
        ranks.setItem(21, sup);
        ranks.setItem(23, team);
        ranks.setItem(25, archi);
        ranks.setItem(26, glassPane);
        //Reihe 4
        ranks.setItem(27, glassPane);
        ranks.setItem(29, sarchi);
        ranks.setItem(31, mod);
        ranks.setItem(33, smod);
        ranks.setItem(35, glassPane);
        //Reihe 5
        ranks.setItem(36, glassPane);
        ranks.setItem(37, developer);
        ranks.setItem(39, radmin);
        ranks.setItem(41, sadmin);
        ranks.setItem(43, owner);
        ranks.setItem(44, glassPane);
        //Reihe 7
        inventorys.put("ranks", ranks.getContents());
    }

}