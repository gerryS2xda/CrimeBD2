package Model;

public class Tuple_Count {

    private String offense_code_group;
    private int count;

    public Tuple_Count(String offense_code_group, int count){
        this.offense_code_group= offense_code_group;
        this.count = count;
    }

    public String getOffense_code_group() {
        return offense_code_group;
    }

    public void setOffense_code_group(String offense_code_group) {
        this.offense_code_group = offense_code_group;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
