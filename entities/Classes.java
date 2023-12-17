package isdrozklad.entities;

import java.time.LocalDate;

public class Classes {
    int pairNumber;
    String pairTime;
    String pairDetails;

    public Classes(int pairNumber, String pairTime, String pairDetails) {
        this.pairNumber = pairNumber;
        this.pairTime = pairTime;
        this.pairDetails = pairDetails;
    }

    public Classes() {
    }

    public int getPairNumber() {
        return pairNumber;
    }

    public void setPairNumber(int pairNumber) {
        this.pairNumber = pairNumber;
    }

    public String getPairTime() {
        return pairTime;
    }

    public void setPairTime(String pairTime) {
        this.pairTime = pairTime;
    }

    public String getPairDetails() {
        return pairDetails;
    }

    public void setPairDetails(String pairDetails) {
        this.pairDetails = pairDetails;
    }

    @Override
    public String toString() {
        return "%d. %s - %s\n".formatted(pairNumber, pairTime, pairDetails);
    }
}
