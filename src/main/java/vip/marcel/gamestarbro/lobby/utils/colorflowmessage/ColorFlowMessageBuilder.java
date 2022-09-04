package vip.marcel.gamestarbro.lobby.utils.colorflowmessage;

import net.md_5.bungee.api.ChatColor;
import vip.marcel.gamestarbro.lobby.Lobby;

import java.awt.*;

public class ColorFlowMessageBuilder {

    private final Lobby plugin;

    public ColorFlowMessageBuilder(Lobby plugin) {
        this.plugin = plugin;
    }

    public String buildColorFlowMessage(String message, String fromHexColor, String toHexColor) {
        StringBuilder messageColor = new StringBuilder();

        Color fromColor = Color.decode(fromHexColor);
        Color toColor = Color.decode(toHexColor);

        int i = 0;
        char[] messageArray = message.toCharArray();

        while(i < messageArray.length) {
            messageColor.append(ChatColor.of(new ColorToColorInterpolation(fromColor, toColor, messageArray.length).getInterpolated(i)));
            messageColor.append(messageArray[i]);

            i++;
        }

        return messageColor.toString();
    }

}
