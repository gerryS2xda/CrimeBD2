package Model;

public class Tuple {

    private String offense_code_group;
    private int hour;

    public Tuple(String offense_code_group, int hour){
        this.offense_code_group= offense_code_group;
        this.hour = hour;
    }

    public String getOffense_code_group() {
        return offense_code_group;
    }

    public void setOffense_code_group(String offense_code_group) {
        this.offense_code_group = offense_code_group;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
