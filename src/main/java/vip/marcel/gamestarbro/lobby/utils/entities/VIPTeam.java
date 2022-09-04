package vip.marcel.gamestarbro.lobby.utils.entities;

public class VIPTeam {

    private int sortId;

    private String name;

    private String colorCode;

    private String prefix;

    private String suffix;

    public VIPTeam() {
    }

    public int getSortId() {
        return this.sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public void setColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
