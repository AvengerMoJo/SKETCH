package sketch.avengergear.com;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Cipher;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class KeyManager
{
    private static final String TAG = "SKETCH-KEYMANAGER";

    PrivateKey privateKey = null;
    PublicKey publicKey  = null;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }
    
    public void createKeyPair()
    {
        try { 
            // Create the public and private keys
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "SC");

            SecureRandom random = new SecureRandom();
            generator.initialize(2048, random);
            KeyPair keyPair = generator.genKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey  = keyPair.getPublic();

            Log.v(TAG, keyPair.getPrivate().toString() );

        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No provider exception", e);
        } catch (NoSuchAlgorithmException e) { 
            Log.e(TAG, "No Algorithum exception", e);
        }
    }

    public PrivateKey getPrivateKey()
    {
        return privateKey; 
    }

    public PublicKey getPublicKey()
    {
        return publicKey;
    }

    public String getCreateUUID(Context curContext)
    {
        TelephonyManager tm = (TelephonyManager)curContext.getSystemService(Context.TELEPHONY_SERVICE);

        String device_id = tm.getDeviceId();
        // String sim_id = tm.getSimSerialNumber();
        // String phone_number = tm.getLine1Number();
        // String mnc_info = Integer.toString(curContext.getResources().getConfiguration().mnc);
        // String mcc_info = Integer.toString(curContext.getResources().getConfiguration().mcc);

        Log.v(TAG, "Device ID :" + device_id ); 
        // Log.v(TAG, "SIM ID :" + sim_id ); 
        // Log.v(TAG, "Phone Number :" + phone_number ); 
        // Log.v(TAG, "MNC Number :" + mnc_info ); 
        // Log.v(TAG, "MCC Number :" + mcc_info ); 
        // return device_id + ":" + sim_id + ":" + phone_number + ":" + mnc_info + ":" + mcc_info; 
        return device_id;
    }
}

