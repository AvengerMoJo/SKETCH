package sketch.avengergear.com;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

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
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class sketchUI extends Activity
{
    private static final String TAG = "SKETCH-UI";

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try { 
            // Create the public and private keys
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

            SecureRandom random = new SecureRandom();
            generator.initialize(2048, random);
            KeyPair keyPair = generator.genKeyPair();

            byte[] pri = keyPair.getPrivate().getEncoded();
            byte[] pub = keyPair.getPublic().getEncoded();

            Log.v(TAG, keyPair.getPrivate().toString() );

        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No provider exception", e);
        } catch (NoSuchAlgorithmException e) { 
            Log.e(TAG, "No Algorithum exception", e);
        }
        
        String uuid = UUID.randomUUID().toString();
        
        Log.v(TAG,"uuid = " + uuid);

    }
}

