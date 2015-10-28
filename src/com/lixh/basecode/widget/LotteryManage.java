package com.lixh.basecode.widget;

import java.util.Random;

public class LotteryManage {
	public static LotteryInfo getRandomLottery() {
		LotteryInfo info = null;
		Random r = new Random();
		int num = r.nextInt(1000);
		if (num < 10) {
			info = new LotteryInfo(LotteryInfo.TYPE_FIRST, "һ�Ƚ�",
					"�������˴�ת�̵���1�Ƚ�����𣡣�����Ҫһ����ô�������http://blog.csdn.net/panjidong_3");
		} else if (num < 30) {
			info = new LotteryInfo(LotteryInfo.TYPE_SECOND, "���Ƚ�",
					"�������˴�ת�̵���2�Ƚ�����𣡣�����Ҫһ����ô������ʣ�http://blog.csdn.net/panjidong_3");
		} else if (num < 100) {
			info = new LotteryInfo(LotteryInfo.TYPE_THIRD, "���Ƚ�",
					"�������˴�ת�̵���3�Ƚ�����𣡣�����Ҫһ����ô������ʣ�http://blog.csdn.net/panjidong_3");
		} else {
			info = new LotteryInfo(LotteryInfo.TYPE_NONE, "�ٽ�����",
					"���������˴�ת�̣�������Ʒ��̫�ã�û�н�������Ҫһ����ô������ʣ�http://blog.csdn.net/panjidong_3");
		}
		return info;
	}
}
