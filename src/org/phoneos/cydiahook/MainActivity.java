package org.phoneos.cydiahook;

import java.io.DataOutputStream;
import java.io.InputStream;
import org.phoneos.cydiahook.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "cydiahook.MainActivity";
	public static boolean isManualTrigger = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		updateSDMountedStatus();
		
		Button btnMountSd = (Button)this.findViewById(R.id.button1);
		btnMountSd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isManualTrigger = true;

				if (!isMounted()) {
					mountAFakeSD();
				}
				else {
					Toast toast = Toast.makeText(MainActivity.this, "SD card has been mounted", Toast.LENGTH_SHORT);
					toast.show();
				}

				updateSDMountedStatus();
			}
		});
	}

	private void updateSDMountedStatus()
	{
		String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
		Log.v(TAG, sdpath);

		TextView v = (TextView) this.findViewById(R.id.textView1);
		v.setText(sdpath);

		String mountState = "unmounted";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			mountState = "SD card has been mounted";
		}

		v = (TextView) this.findViewById(R.id.textView2);
		v.setText(mountState);
		
		Log.v(TAG, mountState);
	}

    private boolean isMounted()
    {
    	boolean result = false;
    	try {
            // parse the uid given a package name (which should be visible in ps)
            Process p = Runtime.getRuntime().exec("mount");
            InputStream reader = p.getInputStream();
            Thread.sleep(200);
            byte[] buff = new byte[10000];
            
            reader.read(buff, 0, buff.length);
            String str = new String(buff);
            if (str.indexOf("/dev/block/vold/179:8 /storage/sdcard0 vfat rw") > 0) {
            	result = true;
            }
    	}
    	catch (Exception e) {}

        return result;
    }
    
	private void mountAFakeSD()
	{
	   //mount bind sd card
	   try
	   {
           Process process = Runtime.getRuntime().exec("su");
           DataOutputStream os = new DataOutputStream(process.getOutputStream());
           os.writeBytes("mount -o bind /storage/sdcard1/extsd /storage/sdcard0\n");
           os.writeBytes("exit\n");
           os.flush();
	   }
	   catch (Exception e)
	   {
	           Log.e(TAG, e.getMessage());
	   }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
