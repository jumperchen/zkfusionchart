/* ChartProperties.java

	Purpose:
		
	Description:
		
	History:
		Jun 28, 2011 9:56:10 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.api;

import java.util.Map;

/**
 * @author jimmy
 *
 */
public interface ChartProperties {
	/**
	 * Add the value of the specified property.
	 * 
	 * @param key
	 * @param value
	 */
	public ChartProperties addProperty(String key, String value);

	/**
	 * Removes the specified property.
	 * 
	 * @param key
	 */
	public ChartProperties removeProperty(String key);
	
	/**
	 * Returns the specified property.
	 */
	public String getProperty(String key);

	/**
	 * Returns all properties.
	 */
	public Map getAllProperties();

	/**
	 * Clear all properties.
	 */
	public void clear();

	/**
	 * Add all properties.
	 */
	public void addAllProperties(Map config);
}
