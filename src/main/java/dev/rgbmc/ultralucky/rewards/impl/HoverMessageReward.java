package dev.rgbmc.ultralucky.rewards.impl;

import dev.rgbmc.ultralucky.rewards.Reward;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HoverMessageReward implements Reward {
    private static TextComponent getHoverText(String text, String hovertext) {
        TextComponent mainComponent = new TextComponent(text);
        mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hovertext).create()));
        return mainComponent;
    }

    private static TextComponent getClickHoverText(String text, String hovertext, ClickEvent.Action action, String vaule) {
        TextComponent mainComponent = new TextComponent(text);
        mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hovertext).create()));
        mainComponent.setClickEvent(new ClickEvent(action, vaule));
        return mainComponent;
    }

    @Override
    public void forward(Player player, String args) {
        String[] param = PlaceholderAPI.setPlaceholders(player, args).split(",");
        if (param.length == 2) {
            player.spigot().sendMessage(getHoverText(param[0], param[1]));
        } else if (param.length == 4) {
            ClickEvent.Action action;
            switch (param[2]) {
                case "cmd":
                    action = ClickEvent.Action.RUN_COMMAND;
                    break;
                case "suggest":
                    action = ClickEvent.Action.SUGGEST_COMMAND;
                    break;
                case "copy":
                    action = ClickEvent.Action.valueOf("COPY_TO_CLIPBOARD");
                    break;
                default:
                    action = ClickEvent.Action.OPEN_URL;
                    break;
            }
            player.spigot().sendMessage(getClickHoverText(param[0], param[1], action, param[3]));
        }
    }
}
