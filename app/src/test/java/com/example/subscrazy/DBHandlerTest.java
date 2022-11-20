package com.example.subscrazy;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class DBHandlerTest {
    private  DBHandler db;
    public  void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db = new DBHandler(context);
   }

    @Test
    public void testAddNewSubscription() {
        createDB();
        Subscription sub = new Subscription("Netflix", "10.99", "Monthly", "17/11/2022","");
        db.addNewSubscription(sub);
        Subscription testSub = db.getSubscription("Netflix");
        Assert.assertEquals(sub.toString(), testSub.toString());
    }

    @Test
    public void testDeleteSubscription() {
        createDB();
        Subscription sub = new Subscription("Netflix", "10.99", "Monthly", "17/11/2022","");
        db.addNewSubscription(sub);
        db.deleteSubscription("Netflix");
        Subscription testSubs = db.getSubscription("Netflix");
        Assert.assertNull(testSubs);
    }

    @Test
    public void testUpdateSubscription(){
        createDB();
        Subscription sub = new Subscription("Netflix", "10.99", "Monthly", "17/11/2022","");
        db.addNewSubscription(sub);
        db.updateSubscription(sub.getName(),sub.getName(),"12.99","Monthly", "20/11/2022");
        Assert.assertEquals(db.getSubscription(sub.getName()).toString(),"Netflix 12.99 Monthly 20/11/2022 ");
    }

    @Test
    public void testReadSubscription(){
        createDB();
        Subscription sub0 = new Subscription("Netflix", "10.99", "Monthly", "17/11/2022","");
        Subscription sub1 = new Subscription("Youtube", "11.99", "Monthly", "16/11/2022","");
        Subscription sub2 = new Subscription("Amazon", "7.99", "Monthly", "19/11/2022","");
        Subscription sub3 = new Subscription("Halo", "9.99", "Monthly", "20/11/2022","");
        db.addNewSubscription(sub0);
        db.addNewSubscription(sub1);
        db.addNewSubscription(sub2);
        db.addNewSubscription(sub3);
        ArrayList<Subscription> allSubs = db.readSubscriptions();

        Assert.assertEquals(sub0.toString(), allSubs.get(0).toString());
        Assert.assertEquals(sub1.toString(), allSubs.get(1).toString());
        Assert.assertEquals(sub2.toString(), allSubs.get(2).toString());
        Assert.assertEquals(sub3.toString(), allSubs.get(3).toString());

    }
}