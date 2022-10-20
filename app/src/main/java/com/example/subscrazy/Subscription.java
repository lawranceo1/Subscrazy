package com.example.subscrazy;

import java.util.Date;

public class Subscription {
    String name;
    Date payment;

    // for billing, ex: every 2(recurrence) months(billing_time)
    int recurrence;
    String billingTime;

    String notes;

    public Subscription(String name,
                        Date payment,
                        int recurrence,
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

    public Date getPayment() {
        return this.payment;
    }

    public void setPayment(Date payment) {
        this.payment = payment;
    }

    public int getRecurrence() {
        return this.recurrence;
    }

    public void setRecurrence(int recurrence) {
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

}
