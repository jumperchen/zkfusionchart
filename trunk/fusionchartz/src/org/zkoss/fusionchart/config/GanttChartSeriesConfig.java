/* GanttChartSeriesConfig.java

	Purpose:
		
	Description:
		
	History:
		Jul 3, 2011 10:41:16 PM, Created by jimmyshiau

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
public class GanttChartSeriesConfig extends PropertiesMapHandler {
	private static final long serialVersionUID = 20110703224210L;
	

	/**
	 * Create the series properties.
	 * 
	 * @param series
	 * @return GanttChartSeriesProperties
	 */
	public GanttChartSeriesProperties createSeriesProperties(Comparable series) {
		return (GanttChartSeriesProperties) super.createProperties(series,
				new GanttChartSeriesProperties());
	}

	/**
	 * Create the series properties at the specified position.
	 * 
	 * @param index
	 * @return GanttChartSeriesProperties
	 */
	public GanttChartSeriesProperties createSeriesProperties(int index) {
		return createSeriesProperties(new Integer(index));
	}

	/**
	 * Returns the series properties.
	 * 
	 * @param series
	 * @return GanttChartSeriesProperties
	 */
	public GanttChartSeriesProperties getSeriesProperties(Comparable series) {
		return (GanttChartSeriesProperties) super.getProperties(series);
	}

	/**
	 * Returns the series properties at the specified position.
	 * 
	 * @param index
	 * @return GanttChartSeriesProperties
	 */
	public GanttChartSeriesProperties getSeriesProperties(int index) {
		return getSeriesProperties(new Integer(index));
	}

	/**
	 * Remove the series properties.
	 * 
	 * @param series
	 */
	public void removeSeriesProperties(Comparable series) {
		super.removeProperties(series);
	}

	/**
	 * Remove the series properties at the specified position.
	 * 
	 * @param index
	 */
	public void removeSeriesProperties(int index) {
		removeSeriesProperties(new Integer(index));
	}

	/**
	 * Remove all of series property.
	 */
	public void clearAllSeriesProperties() {
		super.clearAllProperties();
	}

	/**
	 * @author jimmy
	 * 
	 */
	public static class GanttChartSeriesProperties extends
			PropertiesMapProperties {
		private static final long serialVersionUID = 20110703224410L;

		/**
		 * Create the task properties.
		 * 
		 * @param taskName
		 * @return ChartProperties
		 */
		public ChartProperties createTaskProperties(Comparable taskName) {
			return super.createProperties(taskName, new ChartPropertiesImpl());
		}

		/**
		 * Create the task properties.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties createTaskProperties(int index) {
			return createTaskProperties(new Integer(index));
		}

		/**
		 * Returns the task properties.
		 * 
		 * @param taskName
		 * @return ChartProperties
		 */
		public ChartProperties getTaskProperties(Comparable taskName) {
			return super.getProperties(taskName);
		}

		/**
		 * Returns the task properties.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties getTaskProperties(int index) {
			return getTaskProperties(new Integer(index));
		}

		/**
		 * Remove the task properties.
		 * 
		 * @param taskName
		 */
		public void removeTaskProperties(Comparable taskName) {
			super.removeProperties(taskName);
		}

		/**
		 * Remove the task properties.
		 * 
		 * @param index
		 */
		public void removeTaskProperties(int index) {
			removeTaskProperties(new Integer(index));
		}

		/**
		 * Remove all of task properties.
		 */
		public void clearAllTaskProperties() {
			super.clearAllProperties();
		}

	}
}
