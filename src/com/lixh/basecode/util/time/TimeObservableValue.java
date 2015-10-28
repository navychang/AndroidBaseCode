package com.lixh.basecode.util.time;

import java.util.Observable;

public class TimeObservableValue extends Observable {

	public void setValue() {
		setChanged();
		notifyObservers();
	}
}
