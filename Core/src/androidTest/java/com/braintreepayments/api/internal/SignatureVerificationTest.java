package com.braintreepayments.api.internal;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignatureVerificationTest {

    @Test(timeout = 20000)
    public void isSignatureValid_returnsFalseWhenAppNotInstalled() {
        Log.d("request_command", "uninstall paypal wallet");
        SystemClock.sleep(15000);
        assertFalse(isAppInstalled());

        assertFalse(checkSignature());
    }

    @Test(timeout = 85000)
    public void isSignatureValid_returnsTrueWhenAppIsInstalled() {
        Log.d("request_command", "install paypal wallet");
        SystemClock.sleep(80000);
        assertTrue(isAppInstalled());

        assertTrue(checkSignature());
    }

    private boolean isAppInstalled() {
        PackageManager pm = getTargetContext().getPackageManager();
        try {
            pm.getPackageInfo("com.paypal.android.p2pmobile", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private boolean checkSignature() {
        return SignatureVerification.isSignatureValid(getTargetContext(),
                "com.paypal.android.p2pmobile", "O=Paypal", "O=Paypal", 34172764);
    }
}
