package ga.ganma.minigames.listeners;

import static ga.ganma.minigames.TosoNow.*;
import static org.bukkit.Bukkit.*;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ga.ganma.minigames.Mission;
import ga.ganma.minigames.TosoNow;

public class GameListeners implements Listener {

	private FileConfiguration config;
	public static String missionS;
	public static boolean chat;

	public GameListeners(TosoNow plugin) {
		this.config = plugin.getConfig();
	}

	@EventHandler
	public void arrestedEvent(EntityDamageByEntityEvent e) {
		boolean isHunter;
		boolean isRunner;
		if (TosoNow.start) {
			Set<String> tosoMember = Runner.getEntries();
			Set<String> huntMember = Hunter.getEntries();
			Entity ByEntity = e.getDamager();
			Entity fromEntity = e.getEntity();
			if (ByEntity instanceof Player) {
				if (fromEntity instanceof Player) {
					Player ByPlayer = (Player) e.getDamager();
					isHunter = huntMember.contains(ByPlayer.getName());
					Player fromplayer = (Player) e.getEntity();
					isRunner = tosoMember.contains(fromplayer.getName());
					if (isHunter && isRunner) {
						fromplayer.sendMessage("あなたは確保されました。3秒後に牢屋へテレポートします。");
						jailCount.put(fromplayer, 3);
						jailL = new Location(ByPlayer.getWorld(), plugin.getConfig().getInt("jail.x"),
								plugin.getConfig().getInt("jail.y"), plugin.getConfig().getInt("jail.z"));
						Runner.removeEntry(fromplayer.getName());
						Jailer.addEntry(fromplayer.getName());
						e.setDamage(0d);
					} else {
						e.setCancelled(true);
					}
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			((Player) e.getEntity()).damage(0d);
		}
	}

	@EventHandler
	public void changeSprintingEvent(PlayerToggleSprintEvent e) {
		if (start) {
			Set<String> huntMember = Hunter.getEntries();
			sprintpl = e.getPlayer();
			if (e.isSprinting()) {
				isSprint.put(sprintpl, true);
				if (huntMember.contains(sprintpl.getName())) {
					sprintpl.setWalkSpeed(0.3f);
				}
			} else {
				isSprint.put(sprintpl, false);
				if (huntMember.contains(sprintpl.getName())) {
					sprintpl.setWalkSpeed(0.15f);
				}
			}
		}
	}

	@EventHandler
	public void joinPlayerSetupEvent(PlayerJoinEvent e) {
		Player pl = e.getPlayer();
		if (!start) {
			pl.sendMessage(PREFIX + "ゾス鯖逃走中へようこそ！");
			pl.sendMessage(PREFIX + "ルールをしっかり読み、楽しい逃走中ライフをどうぞ！");
			pl.sendMessage(PREFIX + ChatColor.GRAY + "あなたを逃走者に追加しました。");
			Runner.addEntry(pl.getName());
			pl.setWalkSpeed(0.2f);
			if (Hunter.getEntries().contains(pl.getName())) {
				Hunter.removeEntry(pl.getName());
			}
			pl.setPlayerListName(pl.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
			if (plugin.getConfig().getBoolean("lobby.boolean")) {
				TosoNow.lobbyL = new Location(pl.getWorld(), plugin.getConfig().getInt("lobby.x"),
						plugin.getConfig().getInt("lobby.y"), plugin.getConfig().getInt("lobby.z"));
				pl.teleport(lobbyL);
			}
			if (pl.getInventory().getChestplate() != null) {
				if (pl.getInventory().getChestplate().getItemMeta().getDisplayName().equals(hunterArmorTitle)) {
					pl.getInventory().setChestplate(null);
					pl.getInventory().setLeggings(null);
					pl.getInventory().setBoots(null);
					pl.getInventory().setHelmet(null);
				}
			}
			pl.getInventory().setItem(0, null);
			pl.getInventory().setItem(1, null);
			pl.getInventory().setItem(2, null);
			pl.getInventory().setItem(3, null);
			pl.getInventory().setItem(4, null);
			pl.getInventory().setItem(5, null);
			pl.getInventory().setItem(6, null);
			pl.getInventory().setItem(7, null);
			pl.getInventory().setItem(8, null);
			pl.getInventory().setItem(9, null);
			pl.getInventory().setItem(10, null);
			pl.getInventory().setItem(11, null);
			pl.getInventory().setItem(12, null);
			pl.getInventory().setItem(13, null);
			pl.getInventory().setItem(14, null);
			pl.getInventory().setItem(15, null);
			pl.getInventory().setItem(16, null);
			pl.getInventory().setItem(17, null);
			pl.getInventory().setItem(18, null);
			pl.getInventory().setItem(19, null);
			pl.getInventory().setItem(20, null);
			pl.getInventory().setItem(21, null);
			pl.getInventory().setItem(22, null);
			pl.getInventory().setItem(23, null);
			pl.getInventory().setItem(24, null);
			pl.getInventory().setItem(25, null);
			pl.getInventory().setItem(26, null);
			pl.getInventory().setItem(27, null);
			pl.removePotionEffect(PotionEffectType.SPEED);
		} else if (!Hunter.getEntries().contains(pl.getName()) || Runner.getEntries().contains(pl.getName())) {
			if (gameTime < 1800) {
				pl.sendMessage("ゲームが開始してから30分以上経っているため、牢屋にスポーンしました。");
				jailL = new Location(pl.getWorld(), config.getInt("jail.x"), config.getInt("jail.y"),
						config.getInt("jail.z"));
				pl.teleport(jailL);
			} else if (gameTime > 1800) {
				if (!Jailer.getEntries().contains(pl.getName())) {
					pl.sendMessage("ゲームが開始しているので、ゲーム開始場所にテレポートしました。");
					resL = new Location(pl.getWorld(), config.getInt("res.x"), config.getInt("res.y"),
							config.getInt("res.z"));
					pl.teleport(resL);
					Runner.addEntry(pl.getName());
					pl.setSneaking(true);
				} else {
					pl.sendMessage("あなたはすでに確保されているため、牢屋にテレポートしました。");
					jailL = new Location(pl.getWorld(), config.getInt("jail.x"), config.getInt("jail.y"),
							config.getInt("jail.z"));
					pl.teleport(jailL);
				}
			}
		} else if (Hunter.getEntries().contains(pl.getName())) {
			pl.sendMessage("途中でログアウトしたため、ハンターボックスにテレポートしました。");
			pl.teleport(hunterL);
		}
		e.getPlayer().setFoodLevel(20);
		e.getPlayer().setHealth(20);
	}

	@EventHandler
	public void commandLogEvent(PlayerCommandPreprocessEvent e) {
		for (Player p : getServer().getOnlinePlayers()) {
			if (p.isOp()) {
				p.sendMessage(ChatColor.GRAY + "[コマンドログ]" + e.getPlayer().getName() + "：" + e.getMessage());
			}
		}
	}

	@EventHandler
	public void l(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("鍵")) {
				Player p = (Player) e.getRightClicked();
				if (!Mission.mission2B.get(p)) {
					Mission.mission2B.put((Player) e.getRightClicked(), true);
					(e.getRightClicked()).sendMessage("あなたは" + e.getPlayer().getName() + "さんに時限装置を解除されました！");
				} else if (Mission.mission2B.get(p)) {
					e.getPlayer().sendMessage("あなたがクリックしたプレイヤーはすでに時限装置は解除されています。");
				}
			}
		}
	}

	@EventHandler
	public void publicChatCanceller(AsyncPlayerChatEvent e) {
		if (!chat) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(PREFIX + "現在、全体のチャットは制限されています。");
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void missionListener(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null && Mission.isMission) {
			Block bl = e.getClickedBlock();
			if (bl.getType() == Material.DIAMOND_BLOCK) {
				moneytanka = 300;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (Hunter.getEntries().contains(p.getName())) {
						PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 4000, 1);
						p.addPotionEffect(pe);
					}
				}
				bl.setType(Material.AIR);
				Mission.isMission = false;
				Mission.CLEARp = e.getPlayer();
			} else if (bl.getType() == Material.BEDROCK) {
				Mission.isMission = false;
				bl.setType(Material.AIR);
				Mission.CLEARp = e.getPlayer();
			} else if (bl.getType() == Material.REDSTONE_BLOCK) {
				Mission.isMission = false;
				bl.setType(Material.AIR);
				Mission.CLEARp = e.getPlayer();
			}
		}
	}

	@EventHandler
	public void hunterArmorChangeCanceller(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();
		Inventory inv = e.getClickedInventory();

		if (inv == null || clickedItem == null || !clickedItem.hasItemMeta()) {
			return;
		}
		if (!inv.equals(p.getInventory())) {
			return;
		}

		if (TosoNow.hunterArmorTitle.equals(clickedItem.getItemMeta().getDisplayName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void autoSneakListener(PlayerMoveEvent e) {
		e.getPlayer().setSneaking(true);
	}

	@EventHandler
	public void creativeInventoryAllowListener(InventoryCreativeEvent e) {
		e.setCancelled(false);
	}

	@EventHandler
	public void interactCanceller(EntityInteractEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void foodLevelChangeCanceller(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void blockDamageCanceller(EntityDamageByBlockEvent e) {
		if (e.getDamager() != null) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void itemDespawnCanceller(ItemDespawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void sneakFixListener(PlayerToggleSneakEvent e) {
		if (!e.isSneaking()) {
			e.setCancelled(true);
		}
	}
}
