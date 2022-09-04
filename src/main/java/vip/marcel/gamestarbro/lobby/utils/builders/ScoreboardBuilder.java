package vip.marcel.gamestarbro.lobby.utils.builders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import vip.marcel.gamestarbro.lobby.Lobby;
import vip.marcel.gamestarbro.lobby.utils.entities.VIPTeam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardBuilder {

    private final Lobby plugin;

    private Player player;

    private Map<Integer, String> entryColorCodes;

    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardBuilder(Lobby plugin, Player player) {
        this.plugin = plugin;

        this.player = player;
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        this.entryColorCodes = new ConcurrentHashMap<>();
        this.entryColorCodes.put(0, "§0");
        this.entryColorCodes.put(1, "§1");
        this.entryColorCodes.put(2, "§2");
        this.entryColorCodes.put(3, "§3");
        this.entryColorCodes.put(4, "§4");
        this.entryColorCodes.put(5, "§5");
        this.entryColorCodes.put(6, "§6");
        this.entryColorCodes.put(7, "§7");
        this.entryColorCodes.put(8, "§8");
        this.entryColorCodes.put(9, "§9");
        this.entryColorCodes.put(10, "§a");
        this.entryColorCodes.put(11, "§b");
        this.entryColorCodes.put(12, "§c");
        this.entryColorCodes.put(13, "§d");
        this.entryColorCodes.put(14, "§e");
        this.entryColorCodes.put(15, "§f");

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("aaa", "bbb");
        this.objective.setRenderType(RenderType.INTEGER);
    }

    public ScoreboardBuilder(Lobby plugin) {
        this.plugin = plugin;
    }

    public ScoreboardBuilder setDisplaySlot(DisplaySlot displaySlot) {
        this.objective.setDisplaySlot(displaySlot);
        return this;
    }

    public ScoreboardBuilder setDisplayName(String displayName) {
        this.objective.setDisplayName(displayName);
        return this;
    }

    public ScoreboardBuilder addTeam(int sortId, String name, ChatColor color, String prefix, String suffix) {
        name = name.toLowerCase();

        Team team = this.scoreboard.registerNewTeam(sortId + "-" + name);
        team.setColor(color);
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        return this;
    }

    public ScoreboardBuilder addFixRow(int lineId, String text) {
        this.objective.getScore(text).setScore(lineId);
        return this;
    }

    public ScoreboardBuilder addUpdateableRow(int lineId, String fix, String updateable) {
        Team team = this.scoreboard.registerNewTeam("x" + lineId);
        team.setPrefix(fix);
        team.setSuffix(updateable);
        team.addEntry(this.entryColorCodes.get(lineId));

        this.objective.getScore(this.entryColorCodes.get(lineId)).setScore(lineId);
        return this;
    }

    public ScoreboardBuilder addClearRow(int lineId) {

        StringBuilder stringBuilder = new StringBuilder("§f§8");

        for(int i = 0; i < lineId; i++) {
            stringBuilder.append(" ");
        }

        this.objective.getScore(stringBuilder.toString()).setScore(lineId);
        return this;
    }

    public ScoreboardBuilder addTeams(List<VIPTeam> teams) {

        teams.forEach(team -> {

            int sortId = team.getSortId();
            String name = team.getName();
            String colorCode = team.getColorCode();
            ChatColor color = null;
            String prefix = team.getPrefix();
            String suffix = team.getSuffix();

            switch(colorCode) {

                case "§0":
                    color = ChatColor.BLACK;
                    break;

                case "§1":
                    color = ChatColor.DARK_BLUE;
                    break;

                case "§2":
                    color = ChatColor.DARK_GREEN;
                    break;

                case "§3":
                    color = ChatColor.DARK_AQUA;
                    break;

                case "§4":
                    color = ChatColor.DARK_RED;
                    break;

                case "§5":
                    color = ChatColor.DARK_PURPLE;
                    break;

                case "§6":
                    color = ChatColor.GOLD;
                    break;

                case "§7":
                    color = ChatColor.GRAY;
                    break;

                case "§8":
                    color = ChatColor.DARK_GRAY;
                    break;

                case "§9":
                    color = ChatColor.BLUE;
                    break;

                case "§a":
                    color = ChatColor.GREEN;
                    break;

                case "§b":
                    color = ChatColor.AQUA;
                    break;

                case "§c":
                    color = ChatColor.RED;
                    break;

                case "§d":
                    color = ChatColor.LIGHT_PURPLE;
                    break;

                case "§e":
                    color = ChatColor.YELLOW;
                    break;

                case "§f":
                    color = ChatColor.WHITE;
                    break;

                default:
                    color = ChatColor.RESET;
                    break;
            }

            this.addTeam(sortId, name, color, prefix, suffix);
        });

        return this;
    }

    public void updateDisplayName(Player player, String updateDisplayName) {
        if(player.getScoreboard() == null)
            return;

        if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null)
            return;

        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(updateDisplayName);
    }

    public void updateRow(Player player, int lineId, String update) {
        if(player.getScoreboard() == null)
            return;

        if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null)
            return;

        if(player.getScoreboard().getTeam("x" + lineId) == null)
            return;

        player.getScoreboard().getTeam("x" + lineId).setSuffix(update);
    }

    public void updatePlayerTeam(Player player, int sortId, String name, boolean updateDisplayName) {
        Scoreboard scoreboard = player.getScoreboard();

        scoreboard.getTeam(sortId + "-" + name.toLowerCase()).addPlayer(player);

        if(updateDisplayName)
            player.setDisplayName(scoreboard.getPlayerTeam(player).getPrefix() + player.getName());

        Bukkit.getServer().getOnlinePlayers().forEach(players -> {
            scoreboard.getTeam(players.getScoreboard().getPlayerTeam(players).getName()).addPlayer(players);

            players.getScoreboard().getTeam(sortId + "-" + name.toLowerCase()).addPlayer(player);
        });
    }

    public void build() {
        this.player.setScoreboard(this.scoreboard);
    }

}