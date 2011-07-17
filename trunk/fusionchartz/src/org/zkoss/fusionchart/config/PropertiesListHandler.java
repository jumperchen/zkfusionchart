/* PropertiesListHandler.java

	Purpose:
		
	Description:
		
	History:
		Jul 3, 2011 11:08:20 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.zul.event.ChartDataEvent;

/**
 * @author jimmy
 * 
 */
abstract class PropertiesListHandler extends ChartInfoNotifier {
	private static final long serialVersionUID = 20110703230910L;
	private List _propsList = new ArrayList(4);
	
	/**
	 * Create the chart properties.
	 * 
	 * @param defProps
	 * @return ChartProperties
	 */
	protected ChartProperties createProperties(
			ChartProperties defProps) {
		_propsList.add(defProps);
		addPropertyListener(defProps);
		return defProps;
	}

	/**
	 * Returns the chart properties in the specified position.
	 * 
	 * @param index
	 * @return ChartProperties
	 */
	protected ChartProperties getProperties(int index) {
		return (ChartProperties) _propsList.get(index);
	}

	/**
	 * Remove the chart properties.
	 * 
	 * @param properties
	 */
	protected void removeProperties(ChartProperties properties) {
		if (_propsList.remove(properties)) {
			removePropertyListener(properties);
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
	
	/**
	 * Remove the chart properties in the specified position.
	 * 
	 * @param index
	 */
	protected void removeProperties(int index) {
		Object o = _propsList.remove(index);
		if (o != null) {
			removePropertyListener((ChartProperties) o);
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
	
	/**
	 * Return the count of properties.
	 * 
	 * @return int
	 */
	public int size() {
		return _propsList.size();
	}

	/**
	 * Clear all of chart properties.
	 */
	protected void clearAllProperties() {
		if (!_propsList.isEmpty()) {
			for (Iterator it = _propsList.iterator(); it.hasNext();)
				removePropertyListener((ChartProperties) it.next());

			_propsList.clear();
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
}
