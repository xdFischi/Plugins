package at.alex.BungeeSystem.PunishManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum TimeUnit {

    SECOND("Sekunde", "Sekunden", 1, "sec"),
    MINUTE("Minute", "Minuten", 60, "min"),
    HOUR("Stunde", "Stunden", 60*60, "hour"),
    DAY("Tag", "Tage", 24*60*60, "day"),
    WEEK("Woche", "Wochen", 7*24*60*60, "week"),
    MONTH("Monat", "Monate", 30*24*60*60, "month"),
    YEAR("Jahr", "Jahre", 365*24*60*60, "year");

    private String singlename;
    private String multiname;
    private long seconds;
    private String shortcut;

    private TimeUnit(String singlename, String multiname, long seconds, String shortcut) {
        this.singlename = singlename;
        this.multiname = multiname;
        this.seconds = seconds;
        this.shortcut = shortcut;
    }

    public long getSeconds() {
        return seconds;
    }

    public String getSinglename() {
        return singlename;
    }

    public String getMultiname() {
        return multiname;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static HashMap<String, String> getFullNames() {
        HashMap<String, String> names = new HashMap<>();
        for (TimeUnit unit : TimeUnit.values()) {
            names.put(unit.getShortcut(), unit.getMultiname());
        }
        return names;
    }

    public static List<String> getTimeUnits() {
        List<String> timeunits = new ArrayList<>();
        for (TimeUnit unit : TimeUnit.values()) {
            timeunits.add(unit.getShortcut());
        }
        return timeunits;
    }

    public static TimeUnit getTimeUnit(String shortcut) {
        for (TimeUnit unit : TimeUnit.values()) {
            if (unit.getShortcut().equalsIgnoreCase(shortcut)) {
                return unit;
            }
        }
        return null;
    }
}
