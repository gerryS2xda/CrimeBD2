package controller;

public class InputParameter {

    //instance field
    private String select;
    private String textfield;
    private int numfieldmin;
    private int numfieldmax;

    //Constructor
    public InputParameter(){
        this.select = "";
        this.textfield = "";
        this.numfieldmin = 0;
        this.numfieldmax = 0;
    }

    //getter and setter

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getTextfield() {
        return textfield;
    }

    public void setTextfield(String textfield) {
        this.textfield = textfield;
    }

    public int getNumfieldmin() {
        return numfieldmin;
    }

    public void setNumfieldmin(int numfieldmin) {
        this.numfieldmin = numfieldmin;
    }

    public int getNumfieldmax() {
        return numfieldmax;
    }

    public void setNumfieldmax(int numfieldmax) {
        this.numfieldmax = numfieldmax;
    }
}
