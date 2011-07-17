/* PropertiesListProperties.java

	Purpose:
		
	Description:
		
	History:
		Jul 3, 2011 11:21:46 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Iterator;
import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;

/**
 * @author jimmy
 * 
 */
abstract class PropertiesListProperties extends PropertiesListHandler implements
		ChartProperties {
	private static final long serialVersionUID = 20110703232210L;
	private ChartProperties _properties;

	/* package */PropertiesListProperties() {
		_properties = new ChartPropertiesImpl();
		addPropertyListener(_properties);
	}

	public ChartProperties addProperty(String key, String value) {
		return _properties.addProperty(key, value);
	}

	public ChartProperties removeProperty(String key) {
		return _properties.removeProperty(key);
	}

	public String getProperty(String key) {
		return _properties.getProperty(key);
	}

	public Map getAllProperties() {
		return _properties.getAllProperties();
	}

	public void clear() {
		_properties.clear();
	}

	public void addAllProperties(Map config) {
		_properties.addAllProperties(config);
	}
	
	protected void renderAll(StringBuffer sb) {
		for (Iterator it = getAllProperties().entrySet().iterator(); it
				.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sb.append(" ").append(entry.getKey()).append("='")
					.append(entry.getValue()).append("'");
		}
	}
}
