/* GanttChartCategoriesConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 20, 2011 11:28:46 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.impl.Utils;

/**
 * @author jimmy
 * 
 */
public class GanttChartCategoriesConfig extends PropertiesListHandler {
	private static final long serialVersionUID = 20110620232910L;

	/**
	 * Create the categories properties.
	 * 
	 * @return GanttChartCategoriesProperties
	 */
	public GanttChartCategoriesProperties createCategoriesProperties() {
		return (GanttChartCategoriesProperties) super
				.createProperties(new GanttChartCategoriesProperties());
	}

	/**
	 * Returns the categories properties in the specified position..
	 * 
	 * @param index
	 * @return GanttChartCategoriesProperties
	 */
	public GanttChartCategoriesProperties getCategoriesProperties(int index) {
		return (GanttChartCategoriesProperties) super.getProperties(index);
	}

	/**
	 * Remove the categories property.
	 * 
	 * @param props
	 */
	public void removeCategoriesProperties(GanttChartCategoriesProperties props) {
		super.removeProperties(props);
	}

	/**
	 * Remove the categories property in the specified position..
	 * 
	 * @param index
	 */
	public void removeCategoriesProperties(int index) {
		super.removeProperties(index);
	}

	/**
	 * Remove all of categories property.
	 */
	public void clearAllCategoriesProperties() {
		super.clearAllProperties();
	}

	/**
	 * @author jimmy
	 * 
	 */
	public static class GanttChartCategoriesProperties extends
			PropertiesMapProperties {
		private static final long serialVersionUID = 20110623222910L;
		
		/**
		 * Create the category properties.
		 * 
		 * @return GanttChartCategoryProperties
		 */
		public ChartProperties createCategoryProperties(String name,
				Date start, Date end) {
			List key = Arrays.asList(new Comparable[] { name, start, end });
			ChartPropertiesImpl props = new ChartPropertiesImpl();
			props.addProperty("name", name);
			props.addProperty("start", Utils.toFusionchartDate(start));
			props.addProperty("end", Utils.toFusionchartDate(end));
			return super.createProperties(key, props);
		}

		/**
		 * Returns the category properties.
		 * 
		 * @return GanttChartCategoryProperties
		 */
		public ChartProperties getCategoryProperties(String name,
				Date start, Date end) {
			List key = Arrays.asList(new Comparable[] { name, start, end });
			return super.getProperties(key);
		}

		/**
		 * Remove the category property.
		 * @param name
		 * @param start
		 * @param end
		 */
		public void removeCategoryProperties(String name, Date start, Date end) {
			List key = Arrays.asList(new Comparable[] { name, start, end });
			super.removeProperties(key);
		}

		/**
		 * Remove all of category property.
		 */
		public void clearAllCategoryProperties() {
			super.clearAllProperties();
		}
	}
}
