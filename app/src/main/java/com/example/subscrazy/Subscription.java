package com.example.subscrazy;

import java.util.Date;

public class Subscription {
    String name;
    String payment;

    // for billing, ex: every 2(recurrence) months(billing_time)
    String recurrence;
    String billingTime;

    String notes;

    public Subscription(String name,
                        String payment,
                        String recurrence,
                        String billingTime,
                        String notes) {

        this.name = name;
        this.payment = payment;
        this.recurrence = recurrence;
        this.billingTime = billingTime;
        this.notes = notes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment() {
        return this.payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRecurrence() {
        return this.recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getBillingTime() {
        return this.billingTime;
    }

    public void setBilling_time(String billing_time) {
        this.billingTime = billing_time;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDatePart(String datePart){
        String[] dateParts = this.billingTime.split("/");
        if(datePart.compareTo("DAY")==0){
            return Integer.parseInt(dateParts[0]);
        }
        if(datePart.compareTo("MONTH")==0){
            return Integer.parseInt(dateParts[1]);
        }
        if(datePart.compareTo("YEAR")==0){
            return Integer.parseInt(dateParts[2]);
        }
        return -1;
    }

}
