package crime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Crime {


    public Crime(String incidentNumber, String offenseCode, String offenseCodeGroup, String offenseDescription,
                 String district, String reportingArea, String shooting, LocalDateTime occurredOnDate, int year, int month,
                 String dayOfWeek, int hour, String uCR_Part, String street, double lat, double l, String location) {

        this.incidentNumber = incidentNumber;
        this.offenseCode = Integer.parseInt(offenseCode);
        this.offenseCodeGroup = offenseCodeGroup;
        this.offenseDescription = offenseDescription;
        this.district = district;
        this.reportingArea = reportingArea;
        this.shooting = shooting;
        this.occurredOnDate = occurredOnDate;
        this.year = year;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.UCR_Part= uCR_Part;
        this.street = street;
        this.lat = lat;
        this.Long = l;
        this.location = location;
    }

    //per praticità uso un costruttore che prende in input i valori come un array di stringhe
    public Crime(String[] attributi) {

        this.incidentNumber = attributi[0];
        this.offenseCode =  Integer.parseInt(attributi[1]);
        this.offenseCodeGroup =  attributi[2];
        this.offenseDescription =  attributi[3];
        this.district =  attributi[4];
        this.reportingArea = attributi[5];
        this.shooting =  attributi[6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.occurredOnDate =  LocalDateTime.parse(attributi[7], formatter);
        this.year =  Integer.parseInt(attributi[8]);
        this.month = Integer.parseInt(attributi[9]);
        this.dayOfWeek =  attributi[10];
        this.hour =  Integer.parseInt(attributi[11]);
        this.UCR_Part =  attributi[12];
        this.street =  attributi[13];

        if(!attributi[14].isEmpty()) this.lat =  Double.parseDouble(attributi[14]);
        else this.lat = 0;

        if(!attributi[15].isEmpty()) this.Long =  Double.parseDouble(attributi[15]);
        else this.Long = 0;

        this.location =  attributi[16];
    }

    public String getIncidentNumber() {
        return incidentNumber;
    }
    public void setIncidentNumber(String incidentNumber) {
        this.incidentNumber = incidentNumber;
    }
    public int getOffenseCode() {
        return offenseCode;
    }
    public void setOffenseCode(int offenseCode) {
        this.offenseCode = offenseCode;
    }
    public String getOffenseCodeGroup() {
        return offenseCodeGroup;
    }
    public void setOffenseCodeGroup(String offenseCodeGroup) {
        this.offenseCodeGroup = offenseCodeGroup;
    }
    public String getOffenseDescription() {
        return offenseDescription;
    }
    public void setOffenseDescription(String offenseDescription) {
        this.offenseDescription = offenseDescription;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getReportingArea() {
        return reportingArea;
    }
    public void setReportingArea(String reportingArea) {
        this.reportingArea = reportingArea;
    }
    public String getShooting() {
        return shooting;
    }
    public void setShooting(String shooting) {
        this.shooting = shooting;
    }
    public LocalDateTime getOccurredOnDate() {
        return occurredOnDate;
    }
    public void setOccurredOnDate(LocalDateTime occurredOnDate) {
        this.occurredOnDate = occurredOnDate;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public String getUCR_Part() {
        return UCR_Part;
    }
    public void setUCR_Part(String uCR_Part) {
        UCR_Part = uCR_Part;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLong() {
        return Long;
    }
    public void setLong(double l) {
        Long = l;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return "Crime [incidentNumber=" + incidentNumber + ", offenseCode=" + offenseCode + ", offenseCodeGroup="
                + offenseCodeGroup + ", offenseDescription=" + offenseDescription + ", district=" + district
                + ", reportingArea=" + reportingArea + ", shooting=" + shooting + ", occurredOnDate=" + occurredOnDate
                + ", year=" + year + ", month=" + month + ", dayOfWeek=" + dayOfWeek + ", hour=" + hour + ", UCR_Part="
                + UCR_Part + ", street=" + street + ", lat=" + lat + ", Long=" + Long + ", location=" + location + "]";
    }

    //Variabili di istanza
    private String incidentNumber;
    private int offenseCode;
    private String offenseCodeGroup;
    private String offenseDescription;
    private String district;
    private String reportingArea; //l'ho messo string in quanto può avere valori null
    private String shooting;
    private LocalDateTime occurredOnDate;
    private int year;
    private int month;
    private String dayOfWeek;
    private int hour;
    private String UCR_Part;
    private String street;
    private double lat;
    private double Long;
    private String location;

}
