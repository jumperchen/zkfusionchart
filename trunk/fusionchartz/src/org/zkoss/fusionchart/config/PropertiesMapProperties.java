/* PropertiesCollectionPropertiesHandler.java

	Purpose:
		
	Description:
		
	History:
		Jun 24, 2011 12:15:25 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;

/**
 * @author jimmy
 * 
 */
public abstract class PropertiesMapProperties extends
		PropertiesMapHandler implements ChartProperties {
	private static final long serialVersionUID = 20110624001510L;
	private ChartProperties _properties;

	/*package*/ PropertiesMapProperties() {
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
}
