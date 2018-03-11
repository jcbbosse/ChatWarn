package me.jones01sean.ChatWarn;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatWarn extends JavaPlugin implements Listener {



	
	@SuppressWarnings("unused")
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);;
		saveConfig();
		Updater updater = new Updater(this, 92706, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check

		   
		   
		   
		     if(getConfig().getBoolean("ChatWarnUpdater") == true){
		    	 Updater updater1 = new Updater(this, 92706, this.getFile(), Updater.UpdateType.DEFAULT, true);
		     }
		     if(getConfig().getBoolean("ChatWarnUpdater") == false){
		    	 Updater updater2 = new Updater(this, 92706, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
		     }   
	}
	
	
	
	public void onLoad(){
		Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " ChatWarn v:" + Bukkit.getServer().getPluginManager().getPlugin("ChatWarn").getDescription().getVersion() + " has been enabled!", "chatwarn.notify");
	}


	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player target = e.getPlayer();
		String message = e.getMessage().toLowerCase();
		String AntiPhraseMuteMsg =  ChatColor.RED + " Advertisement Detected Message Cancelled!";
		
		
		if(message.contains("come") && message.contains("and") && message.contains("join")){
			if(!target.hasPermission("chatwarn.bypass")){
			target.sendMessage(ChatWarnPrefix + AntiPhraseMuteMsg);


			target.sendTitle(ChatColor.DARK_RED + "Warning!", ChatColor.RED + ChatWarnPrefix + AntiPhraseMuteMsg);
			Bukkit.broadcast(ChatWarnPrefix + " " + ChatColor.GREEN + target.getName() + ChatColor.GREEN + " Has Had Their Message Cancelled For Advertising!", "chatwarn.notify");
			e.setCancelled(true);
			}
		}
		
		
		
		if(message.contains("free") && message.contains("op")){
			if(!target.hasPermission("chatwarn.bypass")){
			target.sendMessage(ChatWarnPrefix + AntiPhraseMuteMsg);
			target.sendTitle(ChatColor.DARK_RED + "Warning!", ChatColor.RED + ChatWarnPrefix + AntiPhraseMuteMsg);
			Bukkit.broadcast(ChatWarnPrefix + " " + ChatColor.GREEN + target.getName() + ChatColor.GREEN +  " Has Had Their Message Cancelled For Advertising!", "chatwarn.notify");
			e.setCancelled(true);
			}
		}
		
		if(message.contains("join") && message.contains("my") && message.contains("server")){
			if(!target.hasPermission("chatwarn.bypass")){
			target.sendMessage(ChatWarnPrefix + AntiPhraseMuteMsg);
			target.sendTitle(ChatColor.DARK_RED + "Warning!", ChatColor.RED + ChatWarnPrefix + AntiPhraseMuteMsg);
			Bukkit.broadcast(ChatWarnPrefix + " " + ChatColor.GREEN + target.getName() + ChatColor.GREEN +  " Has Had Their Message Cancelled For Advertising!", "chatwarn.notify");
			e.setCancelled(true);
			}
		}
		if(message.contains("im") && message.contains("from") && message.contains("planet") && message.contains("minecraft")){
			if(!target.hasPermission("chatwarn.bypass")){
			target.sendMessage(ChatWarnPrefix + ChatColor.RED + " We Have Plugins To Avoid Things Like This.");
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + target.getName() + ChatColor.GREEN + " Has Just Attempted To Say That They Are From PMC (Thank God I Am Here)", "chatwarn.notify");
			e.setCancelled(true);
			}

		}
		if(message.contains("i'm") && message.contains("from") && message.contains("planet") && message.contains("minecraft")){
			if(!target.hasPermission("chatwarn.bypass")){
			target.sendMessage(ChatWarnPrefix + ChatColor.RED + " We Have Plugins To Avoid Things Like This.");
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + target.getName() + ChatColor.GREEN + " Has Just Attempted To Say That They Are From PMC (Thank God I Am Here)", "chatwarn.notify");
			e.setCancelled(true);
			}
		}
		
		
	}
	
	
	 public final HashMap<String, ArrayList<Block>> mutedPlayers = new HashMap<String, ArrayList<Block>>();
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if(mutedPlayers.containsKey(player.getName())){
			try {
				event.setCancelled(true);
			}catch(Exception e){
				event.setMessage("");
			}
		}
	}

	
	public void loadConfiguration(){

	 	 getConfig().set("ChatWarnUpdater", true);
	     getConfig().set("ChatWarnMuteToggle", true);
	     
	     
	     saveConfig();
	}

	/**
	 * &0	BLACK
&1	DARK BLUE
&2	DARK GREEN
&3	DARK AQUA
&4	DARK RED
&5	DARK PURPLE 
&6	GOLD
&7	GRAY
&8	DARK GRAY
&9	INDIGO
&A GREEN
&B	AQUA
&C	RED
&D	PINK
&E	YELLOW
&F	WHITE
&M	STRIKE THROUGH
&N	UNDERLINED
&L	BOLD
&K	RANDOM
&O	ITALIC
	 */
	
	String ChatWarnPrefix1 = getConfig().getString("ChatWarnPrefix"),
	ChatWarnPrefix = ChatWarnPrefix1.replaceAll("(&([a-f0-9]))", "\u00A7$2");





	
	
	String FirstMuteTime = getConfig().getString("FirstMuteTime");
	String SecondMuteTime = getConfig().getString("SecondMuteTime");
	String ThirdMuteTime = getConfig().getString("ThirdMuteTime");
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("checkupall")){
			if(!sender.hasPermission("chatwarn.checkupall")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
				String uuid = player.getUniqueId().toString();
				int id = getConfig().getInt(uuid);
				
				if(id == 16){
					sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
					sender.sendMessage(ChatColor.RED + "               UUID warn levels of all players");
					sender.sendMessage(ChatColor.AQUA + Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName() + ": " + ChatColor.RED +  id + " (Banned for exceding amount of warnings)");
					return true;
				}
				if(id == 19){
					sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
					sender.sendMessage(ChatColor.RED + "               UUID warn levels of all players");
					sender.sendMessage(ChatColor.AQUA + Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName() + ": " + ChatColor.RED +  id + " (UUID Banned)");
					return true;
				}
				
				if(id <=15){
				sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
				sender.sendMessage(ChatColor.RED + "               UUID warn levels of all players");
				sender.sendMessage(ChatColor.AQUA + Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName() + ": " + ChatColor.RED +  id);
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("chatmute")) {
			if(!sender.hasPermission("chatwarn.mute")) {
				sender.sendMessage(ChatColor.RED + "You are not permitted to use this command.");
				return true;
			}
			if(args.length < 1) {
				sender.sendMessage(ChatColor.RED + "The proper usage is /chatmute <player>");
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			
			if(!mutedPlayers.containsKey(target.getName())){
				mutedPlayers.put(target.getName(), null);
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + target.getName() + " is now muted!");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + " You are now muted by: " + sender.getName() + "!");
				return true;
			}
			if(mutedPlayers.containsKey(target.getName())) {
				mutedPlayers.remove(target.getName());
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + target.getName() + " is now unmuted!");
				target.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " You are now unmuted by: " + sender.getName() + "!");

				return true;
			}
					
		}
		
		if(cmd.getName().equalsIgnoreCase("setwarn")){
			if(!sender.hasPermission("chatwarn.setwarn")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			if (args.length < 2){
				sender.sendMessage(ChatColor.RED + "The Proper Usage is: /setwarn <player> <int>");
				return true;
			}
			if(args.length >= 3){
				sender.sendMessage(ChatColor.RED + "Too many arguments! The Proper Usage is: /setwarn <player> <int>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			Player onlineTarget = Bukkit.getServer().getPlayer(args[0]);
			
			
			int id = Integer.parseInt( args[1] );
			
			if(id == 16){
				if(target.isOnline()){
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + " has set the warn level of" + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");
					onlineTarget.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + " You Are Now Being Banned By ChatWarn For Exceding Amount Of Warnings Allowed By Plugin!");
					getConfig().set(uuid, 16);
					saveConfig();
					return true;
				} else
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + " has set the warn level of" + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");

					getConfig().set(uuid, id);
					saveConfig();
					return true;
				

			}
			
			if(id <= 0){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " The integer must be higher than 0!");
				return true;
			}
			if(id >= 17){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " The integer must be lower than 17!");
				return true;
			}
			
			
			if (!getConfig().contains(uuid)) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + " has set the warn level of" + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");
				getConfig().set(uuid, id);
				saveConfig();
				return true;
			}
			if(getConfig().contains(uuid)){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + " has set the warn level of" + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");

				getConfig().set(uuid, id);
				saveConfig();
				return true;
			}
			return true;
		}

		
		if (cmd.getName().equalsIgnoreCase("checkup")){
			if(!sender.hasPermission("chatwarn.checkup")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			if (args.length < 1){
				sender.sendMessage(ChatColor.RED + "The Proper Usage is: /checkup <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);

			
			
			String uuid = target.getUniqueId().toString();
			int id = this.getConfig().getInt(uuid);
			
			if(id == 16){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.AQUA + target.getName() + ChatColor.AQUA + "'s Warning Level is: " + ChatColor.GOLD + id  + " (Banned for exceding amount of warnings)");
				return true;
			}
			if(id == 19){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.AQUA + target.getName() + ChatColor.AQUA + "'s Warning Level is: " + ChatColor.GOLD + id + " (UUID Banned)");
				return true;
			}
			if(id == 20){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.AQUA + target.getName() + ChatColor.AQUA + "'s Warning Level is: " + ChatColor.GOLD + id + " (Muted via ChatWarn)");
			}
			
			if(id <=15){

			sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.AQUA + target.getName() + ChatColor.AQUA + "'s Warning Level is: " + ChatColor.GOLD + id);
			}
		}
			
			

			
			
	
		
		
		
		if (cmd.getName().equalsIgnoreCase("banuuid")){
			if(!sender.hasPermission("chatwarn.banuuid")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "The Proper Usage is: /banuuid <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			if(!((Player) target).hasPermission("chatwarn.bypass")){
			Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + target.getName() + " " + "Is Now Getting Their UUID Banned From The Server!", "chatwarn.notify");
			((Player) target).kickPlayer(ChatWarnPrefix + ChatColor.RED + " " + "Your Username has been UUID banned if you wish for a possibility to be unbanned do NOT change your username otherwise it might be harder for staff to unban you!");
			getConfig().set(uuid, 19);
			saveConfig();
			return true;
			}
			if(((Player) target).hasPermission("chatwarn.bypass")){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + sender.getName() + " Has Attempted To UUID Ban " + target.getName() + ChatColor.GREEN + "!" , "chatwarn.notify");
				return true;
			}
			
		}
		
		
		if (cmd.getName().equalsIgnoreCase("chatwarn")){
			if(!sender.hasPermission("chatwarn.help")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.AQUA + "                            ChatWarn");
			sender.sendMessage(ChatColor.AQUA + "                       Made By Jones01Sean");
			sender.sendMessage(ChatColor.AQUA + "Official Link: dev.bukkit.org/bukkit-plugins/jones01sean-chatwarn/");
			sender.sendMessage(ChatColor.RED + "/chatcredits - Shows Proper Credits to Contributers/Creators");
			sender.sendMessage(ChatColor.RED + "/resetuuid - Resets a Specified Player's Warn Level");
			sender.sendMessage(ChatColor.RED + "/warn - Warn a Player Relating to a CHAT related issue");
			sender.sendMessage(ChatColor.RED + "/chatload - Reloads ChatWarn Plugin");
			sender.sendMessage(ChatColor.RED + "/banuuid - Bans A Player's UUID From The Server");
			sender.sendMessage(ChatColor.RED + "/checkup - Shows A Player's Warn Level");
			sender.sendMessage(ChatColor.RED + "/setwarn - Sets A Player's Warn Level To The Specified Amount");
			sender.sendMessage(ChatColor.RED + "/checkupall - List of all players that has been warned");
			sender.sendMessage(ChatColor.RED + "/chatmute - A command to mute a player with no time set available");
			sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		}
		
		if (cmd.getName().equalsIgnoreCase("chatload")){
			if(!sender.hasPermission("chatwarn.reload")){
			sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
		    return true;
		}
			reloadConfig();

		    sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Configuration Reloaded!");
		}
		
		if (cmd.getName().equalsIgnoreCase("chatcredits")) {
			if(!sender.hasPermission("chatwarn.credits")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			
		sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		sender.sendMessage(ChatColor.RED + "Credit To This Plugin Goes To:");
		sender.sendMessage(ChatColor.AQUA + "Jones01Sean (Main Coder and Created Page On BukkitDev Forums)");
		sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		}
		
		if (cmd.getName().equalsIgnoreCase("resetuuid")) {
			if(!sender.hasPermission("chatwarn.uuid")){
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "The Proper Usage is: /resetuuid <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			String playerName = target.getName();
			int l1 = this.getConfig().getInt(uuid);

			if (!getConfig().contains(uuid)) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
				getConfig().set(uuid, 1);
				saveConfig();
				return true;
			}
			if (l1 == 2) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 3) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 4) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 5) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 6) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");			
				saveConfig();
				return true;
			}
			if (l1 == 7) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 8) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");			
				saveConfig();
				return true;
			}
			if (l1 == 9) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 10) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 11) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 12) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 13) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 14) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 15) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 16) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
			saveConfig();
				return true;
			}
			if (l1 == 17) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");				saveConfig();
				return true;
			}
			if (l1 == 18) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");				saveConfig();
				return true;
			}
			if (l1 == 19) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset."); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 20){
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + "Player's Warning Level Succefully Reset.");
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + " Just Had Their UUID Reset", "chatwarn.notify");
				saveConfig();
				return true;
			}
		}
			

		if (cmd.getName().equalsIgnoreCase("warn")) {
			if(!sender.hasPermission("chatwarn.warn")) {
				sender.sendMessage(ChatColor.RED + "You Are Not Permitted To Do This Command.");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "The Proper Usage is: /warn <player> <reason>");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);

			if(target == null){
				sender.sendMessage(ChatColor.RED + "Cannot Find Player Specified");
				return true;
			}
			String PlayerDisplayName = target.getName();
			if(sender.hasPermission("chatwarn.bypassall")){
				if(target.hasPermission("chatwarn.bypass")){
					
					
				// TODO bookmark
				String msg = ChatWarnPrefix + ChatColor.RED + " You Have Been Warned For " + ChatColor.GREEN;
				for (int i = 1; i < args.length; i++  ) {
					msg += args[i] + " ";
				}
				target.sendTitle(ChatColor.DARK_RED + "Warning!",ChatColor.RED + msg);
				String uuid = target.getUniqueId().toString();
				target.sendMessage(msg);
				
				
				
				if (!getConfig().contains(uuid)) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
					getConfig().set(uuid, 2);
					saveConfig();
					return true;
				}
				
				int l = this.getConfig().getInt(uuid);
				
				if (l == 1) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
					getConfig().set(uuid, 2);
					saveConfig();
					return true;
				}
				
				if (l == 2) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
					getConfig().set(uuid, 3);
					saveConfig();
					return true;
				}
				

				if (l == 3) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
					getConfig().set(uuid, 4);
					saveConfig();
					return true;
				}
				if (l == 4) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
					getConfig().set(uuid, 5);
					saveConfig();
					return true;
				}
				if (l == 5) {
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + " Now Has To Be Banned!", "chatwarn.notify");
					target.sendMessage(ChatWarnPrefix + ChatColor.RED + " A Ban Is Now Pending For You. When The Ban Goes Through Please Make An Appeal At The Websites Forums!");
					getConfig().set(uuid, 6);
					saveConfig();
					return true;
				}
				
				if (l == 6) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
					getConfig().set(uuid, 7);
					saveConfig();
					return true;
				}
				
				
				if (l == 7) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
					getConfig().set(uuid, 8);
					saveConfig();
					return true;
				}
				

				if (l == 8) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
					getConfig().set(uuid, 9);
					saveConfig();
					return true;
				}
				if (l == 9) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
					getConfig().set(uuid, 10);
					saveConfig();
					return true;
				}
				if (l == 10) {
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + " Now Has To Be Banned!", "chatwarn.notify");
					target.sendMessage(ChatWarnPrefix + ChatColor.RED + " A Ban Is Now Pending For You. When The Ban Goes Through Please Make An Appeal At The Websites Forums!");
					getConfig().set(uuid, 11);
					saveConfig();
					return true;
				}
				
			if (l == 11) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
				getConfig().set(uuid, 12);
				saveConfig();
				return true;
				}
			
			if (l == 12) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 13);
				saveConfig();
				return true;
				}

			if (l == 13) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 14);
				saveConfig();
				return true;
				}
			if (l == 14) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 15);
				saveConfig();
				return true;
				}
			if (l == 15) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + PlayerDisplayName + ChatColor.RED +  " Now Is Banned For Exceding Amount Of Warnings Allowed By ChatWarn!", "chatwarn.notify");
				target.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + " You Are Now Being Banned By ChatWarn For Exceding Amount Of Warnings Allowed By Plugin!");
				getConfig().set(uuid, 16);
				saveConfig();
				return true;
				}
				}
			}
			
			if(target.hasPermission("chatwarn.bypass")){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + sender.getName() + ChatColor.GREEN + " has attempted to warn the player: " + target.getName() + ChatColor.GREEN + " but that player has the chatwarn.bypass permission! ", "chatwarn.notify");
			}
			if (!target.hasPermission("chatwarn.bypass")){
				
			
			
			
		
			String msg = ChatWarnPrefix + ChatColor.RED + " You Have Been Warned For " + ChatColor.GREEN;
			for (int i = 1; i < args.length; i++  ) {
				msg += args[i] + " ";
			}

			target.sendTitle(ChatColor.DARK_RED + "Warning!", ChatColor.RED + msg);
			String uuid = target.getUniqueId().toString();
			target.sendMessage(msg);

			// Player fromUUID = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
			
			
			if (!getConfig().contains(uuid)) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
				getConfig().set(uuid, 2);
				saveConfig();
				return true;
			}
			
			int l = this.getConfig().getInt(uuid);
			
			if (l == 1) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
				getConfig().set(uuid, 2);
				saveConfig();
				return true;
			}
			
			if (l == 2) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 3);
				saveConfig();
				return true;
			}
			

			if (l == 3) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 4);
				saveConfig();
				return true;
			}
			if (l == 4) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 5);
				saveConfig();
				return true;
			}
			if (l == 5) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + " Now Has To Be Banned!", "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + " A Ban Is Now Pending For You. When The Ban Goes Through Please Make An Appeal At The Websites Forums!");
				getConfig().set(uuid, 6);
				saveConfig();
				return true;
			}
			
			if (l == 6) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
				getConfig().set(uuid, 7);
				saveConfig();
				return true;
			}
			
			
			if (l == 7) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 8);
				saveConfig();
				return true;
			}
			

			if (l == 8) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 9);
				saveConfig();
				return true;
			}
			if (l == 9) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 10);
				saveConfig();
				return true;
			}
			if (l == 10) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + " Now Has To Be Banned!", "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + " A Ban Is Now Pending For You. When The Ban Goes Through Please Make An Appeal At The Websites Forums!");
				getConfig().set(uuid, 11);
				saveConfig();
				return true;
			}
			
		if (l == 11) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Warning Successfully Issued.");
			getConfig().set(uuid, 12);
			saveConfig();
			return true;
			}
		
		if (l == 12) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
			getConfig().set(uuid, 13);
			saveConfig();
			return true;
			}

		if (l == 13) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
			getConfig().set(uuid, 14);
			saveConfig();
			return true;
			}
		if (l == 14) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " Punishment Is Pending.");
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " The Player " + PlayerDisplayName + ChatColor.GREEN + " Should Be Muted For " + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
			getConfig().set(uuid, 15);
			saveConfig();
			return true;
			}
		if (l == 15) {
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + PlayerDisplayName + ChatColor.RED +  " Now Is Banned For Exceding Amount Of Warnings Allowed By ChatWarn!", "chatwarn.notify");
			target.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + " You Are Now Being Banned By ChatWarn For Exceding Amount Of Warnings Allowed By Plugin!");
			getConfig().set(uuid, 16);
			saveConfig();
			return true;
			}

		}
		return true;
		}
		return true;
		
	}


 
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		
		String uuid = e.getPlayer().getUniqueId().toString();
		
		if (getConfig().contains(uuid) && getConfig().getInt(uuid) == 16) {
			e.disallow(Result.KICK_BANNED, ChatWarnPrefix + ChatColor.RED + " " + "You Are Now Banned By ChatWarn For Exceding Amount Of Warnings Allowed By Plugin!");
		}
	}
	
	@EventHandler
	public void onPlayerLogin1(PlayerLoginEvent e) {
		String uuid = e.getPlayer().getUniqueId().toString();
		
		if (getConfig().contains(uuid) && getConfig().getInt(uuid) == 19) {
			e.disallow(Result.KICK_BANNED, ChatWarnPrefix + ChatColor.RED + " " + "Your Username has been UUID banned if you wish for a possibility to be unbanned do NOT change your username otherwise it might be harder for staff to unban you!");
		}
	} 
	@EventHandler
	public void onPlayerChat2(AsyncPlayerChatEvent e ){
		String uuid = e.getPlayer().getUniqueId().toString();
/**		int counter;
		int minute = 20*60;
		int hour = minute*60;
		int day = hour*24;
		int firstMute = getConfig().getInt(FirstMuteTime);
		int secondMute = getConfig().getInt(SecondMuteTime);
		int thirdMute = getConfig().getInt(ThirdMuteTime);*/
		Player target = e.getPlayer();
/**		boolean status = getConfig().getBoolean("ChatWarnMuteToggle");
		if(status == true) {
			if(getConfig().contains(uuid) && getConfig().getInt(uuid) == 3 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 8 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 13) {
				counter = 0;
				while(counter <firstMute*minute) {
					counter++;
					mutedPlayers.put(target.getName(), null);
				}
				mutedPlayers.remove(target.getName());
			}
			if(getConfig().contains(uuid) && getConfig().getInt(uuid) == 4 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 9 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 14) {
				counter = 0;
				while(counter < secondMute*hour) {
					mutedPlayers.put(target.getName(),null);
				}
				mutedPlayers.remove(target.getName());
			}
			if(getConfig().contains(uuid) && getConfig().getInt(uuid) == 5 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 10 || getConfig().contains(uuid) && getConfig().getInt(uuid) == 15) {
				counter = 0;
				while(counter < thirdMute*day) {
					
				}
			}
		}*/
		if (getConfig().contains(uuid) && getConfig().getInt(uuid) == 20){
			 mutedPlayers.put(target.getName(), null);
			return;  
		}
		
	}
		
	
}

                      