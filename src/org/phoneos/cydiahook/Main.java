package org.phoneos.cydiahook;

import java.lang.reflect.Method;

import android.os.Environment;
import com.saurik.substrate.MS;

public class Main {

	static void initialize() {

		// getExternalStorageState hook to return true, will cause boot issue
		// so hook it after boot
		//if (!MainActivity.isManualTrigger) return;

//	      MS.hookClassLoad("android.content.res.Resources",
//	            new MS.ClassLoadHook() {
//	               public void classLoaded(Class<?> resources) {
//	 
//	                  Method getColor;
//	                  try {
//	                     getColor = resources.getMethod("getColor",
//	                        Integer.TYPE);
//	                  } catch (NoSuchMethodException e) {
//	                     getColor = null;
//	                  }
//	 
//	                  if (getColor != null) {
//	                	  MS.hookMethod(resources, getColor, new MS.MethodAlteration<Resources, Integer>() {
//	                		    public Integer invoked(Resources resources, Object... args)
//	                		        throws Throwable
//	                		    {
//	                		        return invoke(resources, args) & ~0x0000ff00 | 0x00ff0000;
//	                		    }
//	                		});
//	                    }
//	               }
//	            });

		   MS.hookClassLoad("android.os.Environment",
				   new MS.ClassLoadHook() {

					@Override
					public void classLoaded(Class<?> arg0) {
						Method getExternalStorageState; 
						try {
							getExternalStorageState = arg0.getMethod("getExternalStorageState", new Class<?>[0]);
						} catch (NoSuchMethodException e) {
		                     getExternalStorageState = null;
		                }

						if (getExternalStorageState != null) {
					        final MS.MethodPointer old = new MS.MethodPointer();

					        MS.hookMethod(arg0, getExternalStorageState, new MS.MethodHook() {
					            @Override
								public Object invoked(Object obj, Object... args)
					                throws Throwable
					            {
					                return Environment.MEDIA_MOUNTED;
					            }
					        }, old);
						}
					}
				});
	   }
}