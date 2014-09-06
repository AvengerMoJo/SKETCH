package sketch.avengergear.com;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;



public class ClipboardHelper extends Service {
    private static final String TAG = "SKETCH-CLIP-SERVICE";
    ClipboardManager clipboardManager;

    ClipboardManager.OnPrimaryClipChangedListener mPrimaryChangeListener =
        new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            ClipData clipData = clipboardManager.getPrimaryClip();
            Log.e(TAG, "Clip changed, clipData: " + clipData.getItemAt(0));
       }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // super.onStartCommand(intent, flags, startId);
        Log.e(TAG, "ClipboardHelper started" ); 
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener( mPrimaryChangeListener ); 
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "ClipboardHelper onCreate" ); 
        HandlerThread thread = new HandlerThread(TAG,
            Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        // mServiceLooper = thread.getLooper();
        // mServiceHandler = new ServiceHandler(mServiceLooper);
  }
} 
