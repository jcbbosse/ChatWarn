package me.jones01sean.ChatWarn;
import org.bukkit.entity.Player;

public class UltimateAPI
{
  public static String TranslateFromTo(Boolean SelfSend, Player p, String transFrom, String transTo, String Message)
  {
    return Translate.From(SelfSend, p, transFrom, transTo, Message);
  }
  
  public static String getDefaultFrom()
  {
    return "en_us";
  }
  
  /*public static String getDefaultTo()
  {
    return ChatWarn.get().getConfig().getString("language");
  }*/
  
}
