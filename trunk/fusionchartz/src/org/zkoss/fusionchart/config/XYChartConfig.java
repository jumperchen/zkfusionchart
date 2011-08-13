/* XYChartConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 13, 2011 10:53:39 PM, Created by jimmyshiau

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
public class XYChartConfig extends CategoryChartConfig {
	private static final long serialVersionUID = 20110613225410L;

	private XAxisConfig _xAxisConfig;

	/**
	 * Return the x axis config.
	 */
	public XAxisConfig getXAxisConfig() {
		if (_xAxisConfig == null) {
			_xAxisConfig = new XAxisConfig();
			addPropertyListener((ChartInfoNotifier)_xAxisConfig);
		}
		return _xAxisConfig;
	}

	public CategoriesConfig getCategoryConfig() {
		throw new UnsupportedOperationException(
				"Use getXAxisConfig() to retrieve config.");
	}

	/* package */XAxisConfig xAxisConfig() {
		return _xAxisConfig;
	}
	
	/**
	 * @author jimmy
	 * 
	 */
	public static class XAxisConfig extends PropertiesMapProperties {
		private static final long serialVersionUID = 20110613225810L;

		/**
		 * Create the x properties.
		 * 
		 * @param x
		 * @return ChartProperties
		 */
		public ChartProperties createXProperties(Number x) {
			return super.createProperties(x, new ChartPropertiesImpl());
		}

		/**
		 * Create the x properties in the specified position.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties createXProperties(int index) {
			return createXProperties(new Integer(index));
		}
		
		/**
		 * Returns the x property.
		 * 
		 * @param x
		 * @return ChartProperties
		 */
		public ChartProperties getXProperties(Number x) {
			return super.getProperties(x);
		}

		/**
		 * Returns the x property.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties getXProperties(int index) {
			return getProperties(new Integer(index));
		}

		/**
		 * Remove the x property.
		 * 
		 * @param x
		 */
		public void removeXProperties(Number x) {
			super.removeProperties(x);
		}

		/**
		 * Remove the x property.
		 * 
		 * @param index
		 */
		public void removeXProperties(int index) {
			removeProperties(new Integer(index));
		}

		/**
		 * Remove all of x property.
		 */
		public void clearAllXProperties() {
			super.clearAllProperties();
		}
	}
}
