package org.zkoss.fusionchart.config;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.io.Serializables;
import org.zkoss.zul.event.ChartDataEvent;
import org.zkoss.zul.event.ChartDataListener;

public abstract class ChartInfoNotifier implements java.io.Serializable {

	private static final long serialVersionUID = 20110310190455L;
	private List _listeners = new LinkedList();
	private ChartDataListener _listener;

	/**
	 * Fires a {@link ChartDataEvent} for all registered listener (thru
	 * {@link #addChartDataListener}.
	 * 
	 * <p>
	 * Note: you can invoke this method only in an event listener.
	 */
	protected void fireEvent(int type, Comparable series, Object data) {
		for (Iterator it = _listeners.iterator(); it.hasNext();)
			((ChartDataListener) it.next()).onChange(null);
	}

	protected void fireEvent(int type, Object data) {
		fireEvent(type, null, data);
	}

	// -- ChartModel --//
	public void addChartDataListener(ChartDataListener l) {
		if (l == null)
			throw new NullPointerException();
		_listeners.add(l);
	}

	public void removeChartDataListener(ChartDataListener l) {
		_listeners.remove(l);
	}
	// -- Chart Properties --//
	protected void addPropertyListener(ChartInfoNotifier property) {
		if (_listener == null)
			_listener = new MyChartDataListener();
		property.addChartDataListener(_listener);
	}
	
	protected void addPropertyListener(ChartProperties property) {
		if (property instanceof ChartInfoNotifier)
			addPropertyListener((ChartInfoNotifier)property);
	}
	
	protected void removePropertyListener(ChartInfoNotifier property) {
		property.removeChartDataListener(_listener);
	}
	
	protected void removePropertyListener(ChartProperties property) {
		if (property instanceof ChartInfoNotifier)
			removePropertyListener((ChartInfoNotifier)property);
	}

	private class MyChartDataListener implements ChartDataListener,
			Serializable {
		private static final long serialVersionUID = 20110311122122L;

		public void onChange(ChartDataEvent event) {
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}

	// Serializable//
	private synchronized void writeObject(java.io.ObjectOutputStream s)
			throws java.io.IOException {
		s.defaultWriteObject();

		Serializables.smartWrite(s, _listeners);
	}

	private synchronized void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();

		_listeners = new LinkedList();
		Serializables.smartRead(s, _listeners);
	}

	
}