/* ProcessConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 20, 2011 9:52:11 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.api.ChartProperties;

/**
 * @author jimmy
 *
 */
public class ProcessConfig extends PropertiesMapProperties {
	private static final long serialVersionUID = 20110620215201L;

	/**
	 * Create the process properties.
	 * 
	 * @param taskName
	 * @return ChartProperties
	 */
	public ChartProperties createProcessProperties(Comparable taskName) {
		return super.createProperties(taskName, new ChartPropertiesImpl());
	}
	
	/**
	 * Create the process properties in the specified position.
	 * 
	 * @param index
	 * @return ChartProperties
	 */
	public ChartProperties createProcessProperties(int index) {
		return createProcessProperties(new Integer(index).toString());
	}
	
	/**
	 * Returns the process properties.
	 * 
	 * @param taskName
	 * @return ChartProperties
	 */
	public ChartProperties getProcessProperties(Comparable taskName) {
		return super.getProperties(taskName);
	}

	/**
	 * Returns the process properties at the specified position.
	 * 
	 * @param taskIndex
	 * @return ChartProperties
	 */
	public ChartProperties getProcessProperties(int taskIndex) {
		return super.getProperties(new Integer(taskIndex));
	}

	/**
	 * Remove the process properties.
	 * 
	 * @param taskName
	 */
	public void removeProcessProperties(Comparable taskName) {
		super.removeProperties(taskName);
	}

	/**
	 * Remove the process properties at the specified position.
	 * 
	 * @param taskIndex
	 */
	public void removeProcessProperties(int taskIndex) {
		super.removeProperties(new Integer(taskIndex));
	}

	/**
	 * Remove all of process property.
	 */
	public void clearAllProcessProperties() {
		super.clearAllProperties();
	}
}
