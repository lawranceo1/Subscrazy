package com.example.subscrazy;

import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

import android.content.Context;
import android.os.Build;
import android.content.Context; import android.os.Build;
import androidx.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Before
    public void grantPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName()
                    + " android.permission.READ_EXTERNAL_STORAGE");
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + InstrumentationRegistry.getTargetContext().getPackageName()
                            + " android.permission.WRITE_EXTERNAL_STORAGE");
        }
    }
    Context context;

    @Test
    public void testDB() {

        Subscription sub = new Subscription(
                "Netflix", "10.99", "Monthly", "04/10/2022","");
        DBHandler dbTest = new DBHandler(context);
        assertEquals(0,dbTest.addNewSubscription(sub));


    }

}
