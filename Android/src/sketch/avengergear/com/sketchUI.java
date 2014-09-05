package sketch.avengergear.com;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
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
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "SC");

            SecureRandom random = new SecureRandom();
            generator.initialize(2048, random);
            KeyPair keyPair = generator.genKeyPair();

            //PrivateKey privateKey = keyPair.getPrivate().getEncoded();
            //PublicKey publicKey  = keyPair.getPublic().getEncoded();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey  = keyPair.getPublic();

            Log.v(TAG, keyPair.getPrivate().toString() );

            String uuid = UUID.randomUUID().toString();
            
            Log.v(TAG,"uuid = " + uuid);

            String testText = "Avenger testing the MoJo";
            TextView original_textview = (TextView)findViewById(R.id.original_tv);
            original_textview.setText("[ORIGINAL]:\n" + testText + "\n");

            // Encode the original data with RSA public key
            byte[] encoded = null;
            try {
                Cipher rsa_cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");
                rsa_cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                encoded = rsa_cipher.doFinal(testText.getBytes());
            } catch (Exception e) {
                Log.e(TAG, "RSA encryption error", e);
            }

            TextView encoded_textview = (TextView)findViewById(R.id.encoded_tv);
            encoded_textview.setText("[ENCODED]:\n" + 
            Base64.encodeToString(encoded, Base64.DEFAULT) + "\n");

            // Decode the encoded data with RSA public key
            byte[] decoded = null;
            try {
                Cipher rsa_cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");
                rsa_cipher.init(Cipher.DECRYPT_MODE, privateKey);
                decoded= rsa_cipher.doFinal(encoded);
            } catch (Exception e) {
                Log.e(TAG, "RSA decryption error", e);
            }
            TextView decoded_textview = (TextView)findViewById(R.id.decoded_tv);
            decoded_textview.setText("[DECODED]:\n" + new String(decoded) + "\n");


        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No provider exception", e);
        } catch (NoSuchAlgorithmException e) { 
            Log.e(TAG, "No Algorithum exception", e);
        }
    }
}

