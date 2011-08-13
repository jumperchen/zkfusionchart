/* ChartPropertyHandler.java

	Purpose:
		
	Description:
		
	History:
		Jun 13, 2011 11:01:59 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.zul.event.ChartDataEvent;

/**
 * @author jimmy
 * 
 */
public abstract class PropertiesMapHandler extends ChartInfoNotifier {
	private static final long serialVersionUID = 20110613230240L;
	private Map _propertyMap = new LinkedHashMap(10);

	/**
	 * Create the chart properties in the specified category.
	 * 
	 * @param key
	 * @param defProps
	 * @return ChartProperties
	 */
	protected ChartProperties createProperties(Object key,
			ChartProperties defProps) {
		ChartProperties properties = (ChartProperties) _propertyMap.get(key);
		if (properties == null) {
			_propertyMap.put(key, properties = defProps);
			addPropertyListener(defProps);
		}
		return properties;
	}

	/**
	 * Returns the chart properties in the specified category.
	 * 
	 * @param key
	 * @return ChartProperties
	 */
	protected ChartProperties getProperties(Object key) {
		return (ChartProperties) _propertyMap.get(key);
	}

	/**
	 * Remove the chart properties in the specified category.
	 * 
	 * @param key
	 */
	protected void removeProperties(Object key) {
		Object obj = _propertyMap.remove(key);
		if (obj != null) {
			removePropertyListener((ChartInfoNotifier) obj);
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
	
	/**
	 * Returns the keys of the propertyMap.
	 * 
	 */
	protected Collection getKeys() {
		return _propertyMap.keySet();
	}

	
	/** Return the count of properties.
	 * 
	 * @return int
	 */
	public int size() {
		return _propertyMap.size();
	}
	
	/**
	 * Clear chart properties in all of category.
	 */
	protected void clearAllProperties() {
		if (!_propertyMap.isEmpty()) {
			for (Iterator it = _propertyMap.values().iterator(); it.hasNext();)
				removePropertyListener((ChartInfoNotifier) it.next());
			_propertyMap.clear();
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
}