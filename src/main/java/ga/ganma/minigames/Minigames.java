package ga.ganma.minigames;

import jp.jyn.jecon.Jecon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public final class Minigames extends JavaPlugin implements Listener {
    static boolean start;
    static boolean hunter;
    static ScoreboardManager manager;
    static Scoreboard board;
    static Team Runner;
    static Team Hunter;
    static Team Jailer;
    final static String GAME = ("[" + ChatColor.RED + "ZOSU鯖逃走中" + ChatColor.WHITE + "]");
    static Player sprintpl;
    static public int prize;
    static public int gametime = 3600;
    static Objective main;
    static Score Stime;
    static Score Smoney;
    static Score tanka;
    static Score Srunner;
    static Score Snull;
    static Score Snull2;
    static Score Snull3;
    static Score Snull4;
    static Score Snull5;
    static Score serverinformation;
    static Location jailL;
    static Location resL;
    static Location hunterL;
    static Location lobbyL;
    static HashMap<Player, Boolean> issprint = new HashMap<Player, Boolean>();
    static int moneytanka;
    static HashMap<Player, String> jailcount = new HashMap<Player, String>();
    public static Minigames plg;
    FileConfiguration config;
    static final String huntername = "ハンターの装備";
    private Jecon jecon;

    @Override
    public void onEnable() {
        plg = this;
        getLogger().info("逃走中プラグインが起動しました。");
        getLogger().info("create by ganma");
        getLogger().info("現在のバージョン0.1.5α");
        getCommand("toso").setExecutor(new tosoCommand());
        getCommand("phone").setExecutor(new phone());
        new Eventget(this);

        manager = Bukkit.getScoreboardManager();
        board = manager.getMainScoreboard();
        Runner = board.getTeam("Runner");
        if(Runner != null) Runner.unregister();
        Runner = board.getTeam("Runner");
        Runner = board.registerNewTeam("Runner");
        Runner.setAllowFriendlyFire(false);
        Runner.setCanSeeFriendlyInvisibles(true);
        Hunter = board.getTeam("Hunter");
        if(Hunter != null) Hunter.unregister();
            Hunter = board.registerNewTeam("Hunter");
            Hunter.setAllowFriendlyFire(false);
            Hunter.setCanSeeFriendlyInvisibles(true);
        Jailer = board.getTeam("Jailer");
        if (Jailer != null) Jailer.unregister();
            Jailer = board.registerNewTeam("Jailer");
            Hunter.setAllowFriendlyFire(false);
            Hunter.setCanSeeFriendlyInvisibles(true);
        saveDefaultConfig();
        config = getConfig();
        main = board.getObjective("main");
        if(main != null) main.unregister();
        main = board.registerNewObjective("main","dummy");
        prize = 0;
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Jecon");
        if(plugin == null || !plugin.isEnabled()) {
            // not available
            getLogger().warning("前提プラグインであるJeconが導入されていません！");
        }

        this.jecon = (Jecon) plugin;
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    static public void sendmoney() {
        int a = 0;
        for (Player winner : Bukkit.getServer().getOnlinePlayers()) {
            if (Runner.getPlayers().contains(winner)) {
                UUID id = winner.getUniqueId();
                Jecon.getInstance().getRepository().deposit(id,prize);
                Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + winner.getName() + "さんに賞金" + ChatColor.RED + prize + ChatColor.DARK_AQUA + "円を渡しました！");
                a++;
            }
            Bukkit.getServer().broadcastMessage("合計" + a + "人に賞金を渡しました！");
            prize = 0;
        }
    }
}
