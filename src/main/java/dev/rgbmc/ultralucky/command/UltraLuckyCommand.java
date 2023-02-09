package dev.rgbmc.ultralucky.command;

import dev.rgbmc.ultralucky.UltraLucky;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static dev.rgbmc.ultralucky.utils.Color.color;

public class UltraLuckyCommand implements CommandExecutor {
    private static void sendHelp(CommandSender sender) {
        sender.sendMessage(color("&a☘ &7- &eUltraLucky"));
        if (sender.hasPermission("ultralucky.admin")) {
            sender.sendMessage(color("&7/ulucky reload &8———— &c重载插件"));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("ultralucky.admin")) {
                    UltraLucky.getModuleManager().reloadAllModule();
                    sender.sendMessage(color("&7[&a☘&7] &e&l配置已重载!"));
                } else {
                    sendHelp(sender);
                }
            } else {
                sendHelp(sender);
            }
        } else {
            sendHelp(sender);
        }
        return false;
    }
}
