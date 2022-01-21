package net.pooleaf.core.modules.gui.sidebar;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

@Data
public class SideBar {

    private final static String ID = "usidebar";

    private final String title;
    private List<String> texts = new ArrayList<>();


    public void addText(String text) {
        texts.add(text);
    }

    public void setText(int index, String text) {
        texts.set(index, text);
    }

    public void clearTexts() {
        texts.clear();
    }

    public String getText(int index) {
        return texts.get(index);
    }

    public Scoreboard updateScoreboard(Scoreboard scoreboard) {
        if (scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        Objective objective = scoreboard.getObjective(ID);
        if (objective != null) {
            objective.unregister();
        }

        objective = scoreboard.registerNewObjective(ID, "dummy");
        objective.setDisplayName(title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);

            Team team = scoreboard.getTeam(String.valueOf(i));
            if (team == null) {
                team = scoreboard.registerNewTeam(String.valueOf(i));
            }

            String scoreName = null;

            if (48 < text.length()) {
                text = text.substring(0, 48);
            }

            if (text.length() <= 16) {
                scoreName = text;
            } else if (text.length() <= 32) {
                team.setPrefix(text.substring(0, 16));
                scoreName = text.substring(16, text.length());
            } else if (32 < text.length()) {
                team.setPrefix(text.substring(0, 16));
                scoreName = text.substring(16, 32);
                team.setSuffix(text.substring(32, text.length()));
            }
            team.addEntry(scoreName);

            objective.getScore(scoreName).setScore(texts.size() - i);
        }

        return scoreboard;
    }


}
