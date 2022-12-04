package com.example.subscrazy;


import androidx.annotation.NonNull;

import java.util.Calendar;

@SuppressWarnings("unused")
public class Subscription {
    private String name;
    private String payment;

    // for billing, ex: every 2(recurrence) months(billing_time)
    private String recurrence;
    private String billingTime;

    private String notes;
    private static int notificationID=0;
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
        notificationID++;

    }
    public int getNotificationID(){
        return notificationID;
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
    }

    public long getAlarmTime(int whenNotify){
        Calendar cal = getDate();
        Calendar current = Calendar.getInstance();
        int subDate = cal.get(Calendar.DAY_OF_MONTH);
        int today = cal.get(Calendar.DAY_OF_MONTH);
        Calendar feb = Calendar.getInstance();
        feb.set(Calendar.MONTH,Calendar.FEBRUARY);

        current.set(Calendar.DAY_OF_MONTH, subDate);
        current.set(Calendar. SECOND , 0 ) ;
        current.set(Calendar. MINUTE , 0 ) ;
        current.set(Calendar. HOUR , 0) ;
        current.set(Calendar. AM_PM , Calendar. AM ) ;
        current.add(Calendar.MONTH,1);
        current.add(Calendar.DAY_OF_MONTH, -whenNotify);
        return current.getTimeInMillis();
    }

    @NonNull
    public String toString(){
        return name+" "+payment+" "+recurrence+" "+billingTime+" "+notes;
    }
}


