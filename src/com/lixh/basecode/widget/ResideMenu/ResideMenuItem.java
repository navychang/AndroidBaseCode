package com.lixh.basecode.widget.ResideMenu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * User: special Date: 13-12-10 Time: ÏÂÎç11:05 Mail: specialcyci@gmail.com
 */
public class ResideMenuItem extends LinearLayout {

	public ResideMenuItem(Context context) {
		super(context);
		initViews(context);
	}

	public ResideMenuItem(Context context, int icon, int title) {
		super(context);
		initViews(context);
	}

	public ResideMenuItem(Context context, int icon, String title) {
		super(context);
		initViews(context);
	}

	private void initViews(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	public void AddItemView(View child) {
		addView(child, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

}
