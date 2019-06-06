package com.keyob.payment.gateway;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.keyob.payment.gateway.dal.model.Wallet;
import com.google.gson.Gson;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context instrumentationCtx;


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mahmood_mohammadi.testaauhtentication", appContext.getPackageName());
    }

    //    @Before
//    public void setup() {
//        instrumentationCtx = InstrumentationRegistry.getContext();
//    }
    @Test
    public void database() {
        Gson gson= new Gson();
        String s = "{\"id\":1,\"name\":\"محمود\",\"createDate\":\"Tue Apr 02 20:59:34 IRDT 2019\",\"publicId\":\"k.s_1null313546\",\"passPayment\":\"31354\",\"walletAddress\":\"wallet/mah/def\",\"userId\":1,\"logoPath\":null,\"bannerPath\":null,\"address\":\"کایبل\",\"default\":null,\"typeId\":1}";
        Wallet wallet = gson.fromJson(s, Wallet.class);
    }

}
