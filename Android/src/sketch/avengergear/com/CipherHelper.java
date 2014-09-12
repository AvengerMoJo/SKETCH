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
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class CipherHelper
{
    private static final String TAG = "SKETCH-CIPHERHELPER";

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    Cipher rsa_encoder_cipher = null;
    Cipher rsa_decoder_cipher = null;

    public CipherHelper( PrivateKey private_key, PublicKey public_key )
    { 
        try { 
            rsa_encoder_cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");
            rsa_decoder_cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");

            rsa_encoder_cipher.init(Cipher.ENCRYPT_MODE, public_key );
            rsa_decoder_cipher.init(Cipher.DECRYPT_MODE, private_key );
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKey exception", e);
        } catch (NoSuchAlgorithmException e ){
            Log.e(TAG, "No Algorithum exception", e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "No Padding exception", e);
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No Provider exception", e);
        } 
    }

    public byte[] encodeBytesWithPublicKey(byte[] info, PublicKey public_key)
    {
        byte[] answer = null;
        try {
            Cipher temp_encoder_cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");
            temp_encoder_cipher.init(Cipher.ENCRYPT_MODE, public_key );
            answer = rsa_encoder_cipher.doFinal(info);
        } catch (BadPaddingException e) {
            Log.e(TAG, "Bad Padding exception", e);
        } catch (IllegalBlockSizeException e) { 
            Log.e(TAG, "Illegal Block Size exception", e);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "InvalidKey exception", e);
        } catch (NoSuchAlgorithmException e ){
            Log.e(TAG, "No Algorithum exception", e);
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "No Padding exception", e);
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "No Provider exception", e);
        }
        return answer;
    }

    public byte[] encodeBytes(byte[] info)
    {
        byte[] answer = null;
        try {
            answer = rsa_encoder_cipher.doFinal(info);
        } catch (BadPaddingException e) {
            Log.e(TAG, "Bad Padding exception", e);
        } catch (IllegalBlockSizeException e) { 
            Log.e(TAG, "Illegal Block Size exception", e);
        }
        return answer;
    }

    public byte[] decodeBytes(byte[] info)
    {
        byte[] answer = null;
        try {
            answer = rsa_decoder_cipher.doFinal(info);
        } catch (BadPaddingException e) {
            Log.e(TAG, "Bad Padding exception", e);
        } catch (IllegalBlockSizeException e) { 
            Log.e(TAG, "Illegal Block Size exception", e);
        }
        return answer;
    }
    
}

