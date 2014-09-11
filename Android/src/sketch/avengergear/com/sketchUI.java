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
    private KeyManager keyManager; 
    private CipherHelper cipherHelper;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        keyManager = new KeyManager(); 
        keyManager.createKeyPair(); 

        cipherHelper = new CipherHelper( keyManager.getPrivateKey(), keyManager.getPublicKey() ); 

        String testText = "Avenger testing the MoJo";
        TextView original_textview = (TextView)findViewById(R.id.original_tv);
        original_textview.setText("[ORIGINAL]:\n" + testText + "\n");

        // Encode the original data with RSA public key
        byte[] encoded = cipherHelper.encodeBytes(testText.getBytes()); 

        TextView encoded_textview = (TextView)findViewById(R.id.encoded_tv);
        encoded_textview.setText("[ENCODED]:\n" + 
        Base64.encodeToString(encoded, Base64.DEFAULT) + "\n");

        // Decode the encoded data with RSA public key
        byte[] decoded = cipherHelper.decodeBytes(encoded);

        TextView decoded_textview = (TextView)findViewById(R.id.decoded_tv);
        decoded_textview.setText("[DECODED]:\n" + new String(decoded) + "\n");

        TextView sim_textview = (TextView)findViewById(R.id.sim_tv); 
        sim_textview.setText("[SIM]:\n" + keyManager.getCreateUUID( this.getApplicationContext()) + "\n");  

        Log.v(TAG, "Start Clipboard service");
        Intent i= new Intent(this, ClipboardHelper.class);
        startService(i);
        Log.v(TAG, "Clipboard service started");
    }

}

