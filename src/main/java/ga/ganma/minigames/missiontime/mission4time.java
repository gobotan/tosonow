package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.Eventget;
import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.mission;
import ga.ganma.minigames.tosoCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class mission4time extends BukkitRunnable {
    @Override
    public void run() {
        if(Minigames.gametime == mission.mission4t){
            mission.ismission = false;
            this.cancel();
            Eventget.chat = true;
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "誰も押さなかったため、賞金単価は上がらなかった...","" , 0,100,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
            tosoCommand.world.getBlockAt(mission.blockL).setType(Material.AIR);
        }else if(!mission.ismission){
            this.cancel();
            Eventget.chat = true;
            for (Player allp: Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター強化！",mission.CLEARp.getName() + "により、単価が300円に上がったがハンターが強化された！", 0,100,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
    }
}
