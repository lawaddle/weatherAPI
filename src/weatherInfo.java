public class weatherInfo {
    private String name;
    private String condtion;
    private String icon;
    private double  tempF;
    private double  tempC;

    public weatherInfo(String name, String condtion, String icon, double tempF, double tempC)
    {
        this.condtion = condtion;
        this.name = name;
        this.icon = icon;
        this.tempC = tempC;
        this.tempF = tempF;
    }

    public String getName() {
        return name;
    }

    public String getCondtion() {
        return condtion;
    }

    public String getIcon() {
        return icon;
    }

    public double getTempC() {
        return tempC;
    }

    public double getTempF() {
        return tempF;
    }
}
