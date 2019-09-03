package com.gmail.artizangaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Translate
{
  private static HashMap<Player, String> cpfrom = new HashMap();
  private static HashMap<Player, String> cpto = new HashMap();
  
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
  
  public static String From(Boolean self, Player p, String from, String to, String msg)
  {
    String translatedText = "";
    String symbol = " ";
    if ((!from.equals("--")) && (!from.equals(to)))
    {
      translatedText = translate(msg.replace(",", ""), from, to);
      translatedText = symbol + translatedText;
      if (self.booleanValue()) {
        p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + symbol + translate("You sent", "en", from) + ": " + msg);
      }
    }
    else
    {
      translatedText = msg;
    }
    return translatedText;
  }
  
  
  public static String getSvFrom()
  {
    return "en_us";
  }
  
  public static String getSvTo()
  {
    return ChatWarn.get().getConfig().getString("language");
  }
  

}
