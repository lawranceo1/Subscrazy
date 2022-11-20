package com.example.subscrazy;


import androidx.annotation.NonNull;

import java.util.Calendar;

@SuppressWarnings("unused")
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

    public Calendar getDate() {
        Calendar cal1 = Calendar.getInstance();
        cal1.clear();
        String[] dateParts = this.billingTime.split("/");

        cal1.set(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[0]));
        return cal1;
    }

    public int getRemainingDays() {
        Calendar cal = getDate();
        Calendar feb = Calendar.getInstance();
        feb.set(Calendar.MONTH, Calendar.FEBRUARY);
        Calendar tmp = Calendar.getInstance();
        int billDay = cal.get(Calendar.DAY_OF_MONTH);
        int today = tmp.get(Calendar.DAY_OF_MONTH);

        if (cal.get(Calendar.DAY_OF_MONTH) <= tmp.get(Calendar.DAY_OF_MONTH)) {

            int days = tmp.getActualMaximum(Calendar.DAY_OF_MONTH) - tmp.get(Calendar.DAY_OF_MONTH);

            tmp.add(Calendar.MONTH, 1);
            if (cal.get(Calendar.DAY_OF_MONTH) >= tmp.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                tmp.set(Calendar.DAY_OF_MONTH, tmp.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                tmp.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
            }
            days += tmp.get(Calendar.DAY_OF_MONTH);
            return days;

        } else {
            if (cal.get(Calendar.MONTH) == Calendar.JANUARY && tmp.get(Calendar.MONTH) == Calendar.FEBRUARY && cal.get(Calendar.DAY_OF_MONTH) > feb.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                return tmp.getActualMaximum(Calendar.DAY_OF_MONTH) - tmp.get(Calendar.DAY_OF_MONTH);
            }
            if (cal.get(Calendar.DAY_OF_MONTH) == 31 && tmp.getActualMaximum(Calendar.DAY_OF_MONTH) == 30) {
                return tmp.getActualMaximum(Calendar.DAY_OF_MONTH) - tmp.get(Calendar.DAY_OF_MONTH);
            }

            return cal.get(Calendar.DAY_OF_MONTH) - tmp.get(Calendar.DAY_OF_MONTH);
        }
 /*
        int days = 0;
        Calendar cal = getDate();
        Calendar tmp = Calendar.getInstance();
        int today = tmp.get(Calendar.DATE);
        int billday = cal.get(Calendar.DATE);
        int monthdays = tmp.getActualMaximum(Calendar.DAY_OF_MONTH);
        int diff = billday - today;
        if(diff >=0 ){
            days = diff;
        }
        else{
            days = monthdays - today + billday;
        }
        return days;
*/
    }

    @NonNull
    public String toString(){
        return name+" "+payment+" "+recurrence+" "+billingTime+" "+notes;
    }
}


