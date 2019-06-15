package com.keyob.payment.gateway;
import com.keyob.payment.gateway.model.Wallet;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        Gson gson= new Gson();
        String s = "{\"id\":1,\"name\":\"محمود\",\"createDate\":\"Tue Apr 02 20:59:34 IRDT 2019\",\"publicId\":\"k.s_1null313546\",\"passPayment\":\"31354\",\"walletAddress\":\"wallet/mah/def\",\"userId\":1,\"logoPath\":null,\"bannerPath\":null,\"address\":\"کایبل\",\"default\":null,\"typeId\":1}";
        Wallet wallet = gson.fromJson(s, Wallet.class);
    }

}
