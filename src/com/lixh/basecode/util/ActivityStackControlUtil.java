/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lixh.basecode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import com.lixh.basecode.app.BaseApplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * Observable is used to notify a group of Observer objects when a change
 * occurs. On creation, the set of observers is empty. After a change occurred,
 * the application can call the {@link #notifyObservers()} method. This will
 * cause the invocation of the {@code update()} method of all registered
 * Observers. The order of invocation is not specified. This implementation will
 * call the Observers in the order they registered. Subclasses are completely
 * free in what order they call the update methods.
 * 
 * @see Observer
 */
public class ActivityStackControlUtil {

	List<Activity> activities = new ArrayList<Activity>();
	private static ActivityStackControlUtil stacks;

	/**
	 * Constructs a new {@code Observable} object.
	 */
	public ActivityStackControlUtil() {
	}

	public static ActivityStackControlUtil getInstance() {
		if (stacks == null) {
			stacks = new ActivityStackControlUtil();
		}
		return stacks;

	}

	/**
	 * Adds the specified observer to the list of observers. If it is already
	 * registered, it is not added a second time.
	 * 
	 * @param activities
	 *            the Observer to add.
	 */
	public void addActivity(Activity activity) {
		if (activity == null) {
			throw new NullPointerException("observer == null");
		}
		if (!activities.contains(activity))
			activities.add(activity);
	}

	/**
	 * Returns the number of observers registered to this {@code Observable}.
	 * 
	 * @return the number of observers.
	 */
	public int countActivity() {
		return activities.size();
	}

	/**
	 * Removes the specified observer from the list of observers. Passing null
	 * won't do anything.
	 * 
	 * @param observer
	 *            the observer to remove.
	 */
	public synchronized void finishActivity(Activity activity) {
		activities.remove(activity);
		activity.finish();
	}

	public void finishAllActivity() {
		for (Activity ac : activities) {
			ac.finish();
		}
		ActivityManager manager = (ActivityManager) BaseApplication
				.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
		manager.killBackgroundProcesses(BaseApplication.getInstance()
				.getPackageName());
	}

	/**
	 * Removes all observers from the list of observers.
	 */
	public synchronized void deleteActivity() {
		activities.clear();
	}

	/**
	 * 
	 */
	public void NotifyExitApp() {
		for (Activity activity : activities) {
			activity.finish();
		}
		int nPid = android.os.Process.myPid();
		android.os.Process.killProcess(nPid);
	}
}
