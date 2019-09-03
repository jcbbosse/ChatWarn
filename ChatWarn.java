package me.jones01sean.ChatWarn;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	String from = "en_us";
	String to = getConfig().getString("language");


	

	//All of the wonderful strings that will be translated courtesy of FabioZumbi12 from bukkit while using a modified version of his API from his UltimateTranslate plugin
    String enableMSG = " has been enabled!";
    String antiAD = " Advertisement detected message cancelled!";
    String antiADNotification = " Has Had Their Message Cancelled For Advertising!";
    String noPerm = " You are not permitted to do this command.";
    String tooManyWarningUUID = " (banned for exceeding amount of warnings.)";
    String staffMuteAlrt = " has been muted.";
    String playerMuteAlrt = " You are now muted by: ";
    String staffUnmuteAlrt = " is now unmuted!";
    String playerUnmuteAlrt = " You are now unmuted by: ";
    String properUsage = " The proper usage is: ";
    String tooManyArgs = "Too many arguments! ";
    String setWarnBroadcast = " has set the warn level of ";
    String playerWarnBan = " You are now being banned for exceeding amount of warnings allowed by the plugin!";
    String num1 = " The number must be higher than 0!";
    String num2 = " The number must be lower than 17!";
    String staffBanUUID = "is now getting their UUID banned from the server";
    String playerBanUUID = "Your username has been UUID banned, if you wish for a possibility to be unbanned do NOT change your username otherwise it might be harder for staff to unban you.";
    String staffBanBypass = " has attempted to UUID ban: ";
    String officialLink = " Official link:";
    String chatCredits = "Shows proper credits to contributors/creators ";
    String resetUUID = "Resets a specified players warn level.";
    String warnMSG = "Warn a player relating to a CHAT issue.";
    String chatLoad = "Reloads ChatWarn plugin.";
    String banUUID = "Bans a players UUID from the server.";
    String checkUP = "Shows a players warn level.";
    String setWARN = "Sets a players warn level to a specified amount.";
    String checkUpALL = "List of all players that have been warned.";
    String chatMUTE = "A command to mute a player with no time set available.";
    String configReloaded = " Config reloaded.";
    String credit = "Credit to the plugin goes to:";
    String credit2 = "(main coder and created page on BukkitDev forums)";
    String staffUUIDRst = "Players warning level successfully reset.";
    String staffUUIDNot = " just had their UUID reset.";
    String noPlayer = "Cannot find player specified.";
    String beenWarned = " You have been warned for: ";
    String warnIssued = " Warning successfully issued.";
    String pendingPunish = " Punishment is pending.";
    String nowBan =  " now has to be banned.";
    String playerBan = " A ban is now pending for you. When the ban goes through please make an appeal at the websites forums.";
	
	
	
	/*
	 String pFrom = UltimateAPI.getFrom(Player p)
	 String pTo = UltimateAPI.getTo(p);
	
	*/
    
    
    public static String translate(String msg, String from, String to)
    {
      String strf = "";
      try
      {
        URL url = new URL(String.format("https://translate.google.com.tr/m?hl=en&sl=%s&tl=%s&ie=UTF-8&prev=_m&q=%s", new Object[] { from, to, URLEncoder.encode(msg, "UTF-8") }));
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        
        StringBuilder str = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
          str.append(line);

        }
        br.close();
        Pattern pattern = Pattern.compile("<div dir=\"ltr\" class=\"t0\">(.+?)</div>", 32);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
          strf = matcher.group(1);
        }
      }
      catch (IOException mue)
      {
        mue.printStackTrace();
      }
      return strf;
    }
    
    
    

	
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
	
	
	/*
	 * getConfig().getString("lang." + getConfig().getString("language") + "." + "ENTER STRING NAME HERE")
	 */
	// p.sendMessage(this.getConfig().getString("lang." + this.getConfig().getString("language") + "." + "message"))
	
	public void onLoad(){
		Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " ChatWarn v:" + Bukkit.getServer().getPluginManager().getPlugin("ChatWarn").getDescription().getVersion() + translate(enableMSG,from,to), "chatwarn.notify");
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
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
				String uuid = player.getUniqueId().toString();
				int id = getConfig().getInt(uuid);
				
				if(id == 16){
					sender.sendMessage(ChatColor.GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
					sender.sendMessage(ChatColor.RED + "               UUID warn levels of all players");
					sender.sendMessage(ChatColor.AQUA + Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName() + ": " + ChatColor.RED +  id + translate(tooManyWarningUUID, from, to));
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
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if(args.length < 1) {
				sender.sendMessage(ChatColor.RED + translate(properUsage, from, to) + "/chatmute <player>");
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			
			if(!mutedPlayers.containsKey(target.getName())){
				mutedPlayers.put(target.getName(), null);
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + target.getName() + translate(staffMuteAlrt, from, to), "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(playerMuteAlrt, from, to) + sender.getName() + "!");
				return true;
			}
			if(mutedPlayers.containsKey(target.getName())) {
				mutedPlayers.remove(target.getName());
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + target.getName() + translate(staffUnmuteAlrt, from, to), "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(playerUnmuteAlrt, from, to) + sender.getName() + "!");

				return true;
			}
					
		}
		
		if(cmd.getName().equalsIgnoreCase("setwarn")){
			if(!sender.hasPermission("chatwarn.setwarn")){
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if (args.length < 2){
				sender.sendMessage(ChatColor.RED +  translate(properUsage, from, to) + "/setwarn <player> <int>");
				return true;
			}
			if(args.length >= 3){
				sender.sendMessage(ChatColor.RED + translate(tooManyArgs,from,to) + translate(properUsage, from, to) +"/setwarn <player> <int>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			Player onlineTarget = Bukkit.getServer().getPlayer(args[0]);
			
			
			int id = Integer.parseInt( args[1] );
			
			if(id == 16){
				if(target.isOnline()){
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + translate(setWarnBroadcast,from,to) + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");
					onlineTarget.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + translate(playerWarnBan, from, to));
					getConfig().set(uuid, 16);
					saveConfig();
					return true;
				} else
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + translate(setWarnBroadcast,from,to) + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");

					getConfig().set(uuid, id);
					saveConfig();
					return true;
				

			}
			
			if(id <= 0){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(num1,from,to));
				return true;
			}
			if(id >= 17){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(num2,from,to));
				return true;
			}
			
			
			if (!getConfig().contains(uuid)) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + translate(setWarnBroadcast,from,to) + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");
				getConfig().set(uuid, id);
				saveConfig();
				return true;
			}
			if(getConfig().contains(uuid)){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.BOLD + sender.getName() + ChatColor.GREEN + translate(setWarnBroadcast,from,to) + ChatColor.RED + " " + ChatColor.BOLD + target.getName() + ChatColor.GREEN + " to" + ChatColor.YELLOW + " " + ChatColor.BOLD + id, "chatwarn.notify");

				getConfig().set(uuid, id);
				saveConfig();
				return true;
			}
			return true;
		}

		
		if (cmd.getName().equalsIgnoreCase("checkup")){
			if(!sender.hasPermission("chatwarn.checkup")){
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if (args.length < 1){
				sender.sendMessage(ChatColor.RED + translate(properUsage, from, to) +"/checkup <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);

			
			
			String uuid = target.getUniqueId().toString();
			int id = this.getConfig().getInt(uuid);
			
			if(id == 16){
				sender.sendMessage(ChatWarnPrefix + ChatColor.RED + " " + ChatColor.AQUA + target.getName() + ChatColor.AQUA + "'s Warning Level is: " + ChatColor.GOLD + id  + translate(tooManyWarningUUID,from,to));
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
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + translate(properUsage, from, to) + "/banuuid <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			if(!((Player) target).hasPermission("chatwarn.bypass")){
			Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + target.getName() + " " + translate(staffBanUUID, from, to), "chatwarn.notify");
			((Player) target).kickPlayer(ChatWarnPrefix + ChatColor.RED + " " + translate(playerBanUUID, from, to));
			getConfig().set(uuid, 19);
			saveConfig();
			return true;
			}
			if(((Player) target).hasPermission("chatwarn.bypass")){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + sender.getName() + translate(staffBanBypass,from,to) + target.getName() + ChatColor.GREEN + "!" , "chatwarn.notify");
				return true;
			}
			
		}
		
		
		if (cmd.getName().equalsIgnoreCase("chatwarn")){
			if(!sender.hasPermission("chatwarn.help")){
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
			sender.sendMessage(ChatColor.AQUA + "                            ChatWarn");
			sender.sendMessage(ChatColor.AQUA + "                       Made By Jones01Sean");
			sender.sendMessage(ChatColor.AQUA + translate(officialLink,from,to) + " dev.bukkit.org/bukkit-plugins/jones01sean-chatwarn/");
			sender.sendMessage(ChatColor.RED + "/chatcredits - " + translate(resetUUID,from,to));
			sender.sendMessage(ChatColor.RED + "/resetuuid - " + translate(resetUUID,from,to));
			sender.sendMessage(ChatColor.RED + "/warn - " + translate(warnMSG,from,to));
			sender.sendMessage(ChatColor.RED + "/chatload - " + translate(chatLoad,from,to));
			sender.sendMessage(ChatColor.RED + "/banuuid - " + translate(banUUID,from,to));
			sender.sendMessage(ChatColor.RED + "/checkup - " + translate(checkUP,from,to));
			sender.sendMessage(ChatColor.RED + "/setwarn - " + translate(setWARN,from,to));
			sender.sendMessage(ChatColor.RED + "/checkupall - " + translate(checkUpALL,from,to));
			sender.sendMessage(ChatColor.RED + "/chatmute - " + translate(chatMUTE,from,to));
			sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		}
		
		if (cmd.getName().equalsIgnoreCase("chatload")){
			if(!sender.hasPermission("chatwarn.reload")){
			sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
		    return true;
		}
			reloadConfig();

		    sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(configReloaded,from,to));
		}
		
		if (cmd.getName().equalsIgnoreCase("chatcredits")) {
			if(!sender.hasPermission("chatwarn.credits")){
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			
		sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		sender.sendMessage(ChatColor.RED + translate(credit,from,to));
		sender.sendMessage(ChatColor.AQUA + "Jones01Sean " + translate(credit2,from,to));
		sender.sendMessage(ChatColor.AQUA + "FabioZumbi12 " + translate("Creating the API for multi-language support", from, to));
		sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
		}
		
		if (cmd.getName().equalsIgnoreCase("resetuuid")) {
			if(!sender.hasPermission("chatwarn.uuid")){
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + translate(properUsage, from, to)+ " /resetuuid <player>");
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
			String uuid = target.getUniqueId().toString();
			String playerName = target.getName();
			int l1 = this.getConfig().getInt(uuid);

			
			if (!getConfig().contains(uuid)) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
				getConfig().set(uuid, 1);
				saveConfig();
				return true;
			}
			if (l1 == 2) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 3) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 4) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 5) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 6) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");			
				saveConfig();
				return true;
			}
			if (l1 == 7) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 8) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");			
				saveConfig();
				return true;
			}
			if (l1 == 9) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 10) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 11) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 12) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");	
				saveConfig();
				return true;
			}
			if (l1 == 13) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 14) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 15) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");		
				saveConfig();
				return true;
			}
			if (l1 == 16) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
			saveConfig();
				return true;
			}
			if (l1 == 17) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");				saveConfig();
				return true;
			}
			if (l1 == 18) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");				saveConfig();
				return true;
			}
			if (l1 == 19) {
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to)); 
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
				saveConfig();
				return true;
			}
			if (l1 == 20){
				getConfig().set(uuid, 1);
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + " " + translate(staffUUIDRst,from,to));
				Bukkit.getServer().broadcast(ChatWarnPrefix + ChatColor.RED + " " + playerName + translate(staffUUIDNot,from,to), "chatwarn.notify");
				saveConfig();
				return true;
			}
		}
			

		if (cmd.getName().equalsIgnoreCase("warn")) {
			if(!sender.hasPermission("chatwarn.warn")) {
				sender.sendMessage(ChatColor.RED + translate(noPerm, from, to));
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + translate(properUsage, from, to) + "/warn <player> <reason>");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);

			if(target == null){
				sender.sendMessage(ChatColor.RED + translate(noPlayer,from,to));
				return true;
			}
			String PlayerDisplayName = target.getName();
			if(sender.hasPermission("chatwarn.bypassall")){
				if(target.hasPermission("chatwarn.bypass")){
					
					
			
				String msg = ChatWarnPrefix + ChatColor.RED + translate(beenWarned,from,to) + ChatColor.GREEN;
				for (int i = 1; i < args.length; i++  ) {
					msg += args[i] + " ";
				}
				target.sendTitle(ChatColor.DARK_RED + translate("Warning!",from,to),ChatColor.RED + msg);
				String uuid = target.getUniqueId().toString();
				target.sendMessage(msg);
				
				
				
				if (!getConfig().contains(uuid)) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
					getConfig().set(uuid, 2);
					saveConfig();
					return true;
				}
				
				int l = this.getConfig().getInt(uuid);
				
				if (l == 1) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
					getConfig().set(uuid, 2);
					saveConfig();
					return true;
				}
				
				if (l == 2) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to) + PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
					getConfig().set(uuid, 3);
					saveConfig();
					return true;
				}
				

				if (l == 3) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
					getConfig().set(uuid, 4);
					saveConfig();
					return true;
				}
				if (l == 4) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN+ translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
					getConfig().set(uuid, 5);
					saveConfig();
					return true;
				}
				if (l == 5) {
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + translate(nowBan,from,to), "chatwarn.notify");
					target.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(playerBan,from,to));
					getConfig().set(uuid, 6);
					saveConfig();
					return true;
				}
				
				if (l == 6) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
					getConfig().set(uuid, 7);
					saveConfig();
					return true;
				}
				
				
				if (l == 7) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
					getConfig().set(uuid, 8);
					saveConfig();
					return true;
				}
				

				if (l == 8) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to) +PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
					getConfig().set(uuid, 9);
					saveConfig();
					return true;
				}
				if (l == 9) {
					sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN+ translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
					getConfig().set(uuid, 10);
					saveConfig();
					return true;
				}
				if (l == 10) {
					Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + translate(nowBan,from,to), "chatwarn.notify");
					target.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(playerBan,from,to));
					getConfig().set(uuid, 11);
					saveConfig();
					return true;
				}
				
			if (l == 11) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
				getConfig().set(uuid, 12);
				saveConfig();
				return true;
				}
			
			if (l == 12) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 13);
				saveConfig();
				return true;
				}

			if (l == 13) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 14);
				saveConfig();
				return true;
				}
			if (l == 14) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 15);
				saveConfig();
				return true;
				}
			if (l == 15) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + PlayerDisplayName + ChatColor.RED +  translate(staffBanUUID, from, to), "chatwarn.notify");
				target.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + translate(playerWarnBan, from, to));
				getConfig().set(uuid, 16);
				saveConfig();
				return true;
				}
				}
			}
			
			if(target.hasPermission("chatwarn.bypass")){
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + sender.getName() + ChatColor.GREEN + translate(" has attempted to warn the player: ",from,to) + target.getName() + ChatColor.GREEN + translate(" but that player has the chatwarn.bypass permission! ",from,to), "chatwarn.notify");
			}
			if (!target.hasPermission("chatwarn.bypass")){
				
			
			
			
		
			String msg = ChatWarnPrefix + ChatColor.RED + translate(beenWarned,from,to) + ChatColor.GREEN;
			for (int i = 1; i < args.length; i++  ) {
				msg += args[i] + " ";
			}

			target.sendTitle(ChatColor.DARK_RED + translate("Warning!",from,to), ChatColor.RED + msg);
			String uuid = target.getUniqueId().toString();
			target.sendMessage(msg);

			// Player fromUUID = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
			
			
			if (!getConfig().contains(uuid)) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
				getConfig().set(uuid, 2);
				saveConfig();
				return true;
			}
			
			int l = this.getConfig().getInt(uuid);
			
			if (l == 1) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
				getConfig().set(uuid, 2);
				saveConfig();
				return true;
			}
			
			if (l == 2) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 3);
				saveConfig();
				return true;
			}
			

			if (l == 3) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN+ translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 4);
				saveConfig();
				return true;
			}
			if (l == 4) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN +translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 5);
				saveConfig();
				return true;
			}
			if (l == 5) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + translate(nowBan,from,to), "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(playerBan,from,to));
				getConfig().set(uuid, 6);
				saveConfig();
				return true;
			}
			
			if (l == 6) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
				getConfig().set(uuid, 7);
				saveConfig();
				return true;
			}
			
			
			if (l == 7) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN  +translate(" The player ",from,to) +PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
				getConfig().set(uuid, 8);
				saveConfig();
				return true;
			}
			

			if (l == 8) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + translate(" The player ",from,to)+ PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
				getConfig().set(uuid, 9);
				saveConfig();
				return true;
			}
			if (l == 9) {
				sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + translate(" The player ",from,to) + PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
				getConfig().set(uuid, 10);
				saveConfig();
				return true;
			}
			if (l == 10) {
				Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + " " + PlayerDisplayName + translate(nowBan,from,to), "chatwarn.notify");
				target.sendMessage(ChatWarnPrefix + ChatColor.RED + translate(playerBan,from,to));
				getConfig().set(uuid, 11);
				saveConfig();
				return true;
			}
			
		if (l == 11) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(warnIssued,from,to));
			getConfig().set(uuid, 12);
			saveConfig();
			return true;
			}
		
		if (l == 12) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + translate(" The player ",from,to) + PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + FirstMuteTime + " Minute(s)!", "chatwarn.notify");
			getConfig().set(uuid, 13);
			saveConfig();
			return true;
			}

		if (l == 13) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + translate(" The player ",from,to) + PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + SecondMuteTime + " Hour(s)!", "chatwarn.notify");
			getConfig().set(uuid, 14);
			saveConfig();
			return true;
			}
		if (l == 14) {
			sender.sendMessage(ChatWarnPrefix + ChatColor.GREEN + translate(pendingPunish,from,to));
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.GREEN + translate(" The player ",from,to) + PlayerDisplayName + ChatColor.GREEN + translate(" should be muted for ",from,to) + ThirdMuteTime + " Day(s)!", "chatwarn.notify");
			getConfig().set(uuid, 15);
			saveConfig();
			return true;
			}
		if (l == 15) {
			Bukkit.broadcast(ChatWarnPrefix + ChatColor.RED + " " + PlayerDisplayName + ChatColor.RED +  translate(staffBanUUID, from, to), "chatwarn.notify");
			target.kickPlayer(ChatWarnPrefix + ChatColor.GOLD + translate(playerWarnBan, from, to));
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
			e.disallow(Result.KICK_BANNED, ChatWarnPrefix + ChatColor.RED + " " + translate(playerWarnBan, from, to));
		}
	}
	
	@EventHandler
	public void onPlayerLogin1(PlayerLoginEvent e) {
		String uuid = e.getPlayer().getUniqueId().toString();
		
		if (getConfig().contains(uuid) && getConfig().getInt(uuid) == 19) {
			e.disallow(Result.KICK_BANNED, ChatWarnPrefix + ChatColor.RED + " " + translate(playerBanUUID, from, to));
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
	private static ChatWarn plugin;
	public static ChatWarn get() {
		return plugin;
	}
		
	
}

                      