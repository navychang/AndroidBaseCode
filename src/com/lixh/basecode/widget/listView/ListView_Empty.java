package com.lixh.basecode.widget.listView;

import com.lixh.basecode.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @Title: NoScrollListView.java
 * @Package com.cihon.widget
 * @Description: listview 加了 emptView
 * @author lixh
 * @date 2015年5月13日 下午3:34:10
 * @version V1.0
 */
public class ListView_Empty extends ListView {

	public ListView_Empty(Context context) {
		super(context);
		initView(context, null);
	}

	private void initView(Context context, AttributeSet attrs) {
		this.setEmptyView(createEmaptyView());

	}

	public ListView_Empty(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	TextView textView;

	public void setText(String msg) {
		textView.setText(msg);
	}

	private View createEmaptyView() {
		View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, null);
		textView = (TextView) headerView.findViewById(R.id.text_view);
		return headerView;
	}
}