package model;

public enum Area {
    STANDARD("STANDARD"),
    VIP("VIP"),
    STREAM("STREAM");

    private String areaCode;

    Area(String areaCode) {
        this.areaCode = areaCode;
    }

    public static Area fromArea(String area) {
        for (Area a : Area.values()) {
            if (a.areaCode.equals(area)) return a;
        }
        throw new IllegalArgumentException("Invalid role: " + area);
    }

    public String getAreaCode() {
        return areaCode;
    }
}
