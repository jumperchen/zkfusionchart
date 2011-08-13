/* GanttTableProperty.java

	Purpose:
		
	Description:
		
	History:
		Mar 23, 2011 6:24:42 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.api.ChartProperties;

/**
 * A table config for gnatt chart table.
 * 
 * @author jimmy
 * 
 */
public class GanttTableConfig extends ChartPropertiesImpl {
	private static final long serialVersionUID = 20110323181610L;

	private GanttTableColumnConfig _columnConfig;

	/**
	 * Return the column config.
	 */
	public GanttTableColumnConfig getColumnConfig() {
		if (_columnConfig == null) {
			_columnConfig = new GanttTableColumnConfig();
			addPropertyListener(_columnConfig);
		}
		return _columnConfig;
	}
	
	/*package*/ GanttTableColumnConfig columnConfig() {
		return _columnConfig;
	}

	/**
	 * @author jimmy
	 *
	 */
	public static class GanttTableColumnConfig extends PropertiesMapHandler {
		private static final long serialVersionUID = 20110717094310L;
		
		/**
		 * Create the column properties at the specified position.
		 * 
		 * @param index
		 * @return SeriesProperties
		 */
		public GanttTableColumnProperties createColumnProperties(int index) {
			return (GanttTableColumnProperties) super.createProperties(new Integer(index),
					new GanttTableColumnProperties());
		}
		
		/**
		 * Returns the column properties at the specified position.
		 * 
		 * @param index
		 * @return SeriesProperties
		 */
		public GanttTableColumnProperties getColumnProperties(int index) {
			return (GanttTableColumnProperties) super.getProperties(new Integer(index));
		}
		
		/**
		 * Remove the column properties at the specified position.
		 * 
		 * @param index
		 */
		public void removeColumnProperties(int index) {
			super.removeProperties(new Integer(index));
		}
		
		/**
		 * Remove all of column property.
		 */
		public void clearAllColumnProperties() {
			super.clearAllProperties();
		}
	}
	
	/**
	 * A tableColumn properties bean for gnatt chart table.
	 * @author jimmy
	 * 
	 */
	public static class GanttTableColumnProperties extends PropertiesMapProperties {
		private static final long serialVersionUID = 20110322122910L;

		/**
		 * Create the text properties at the specified position.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties createTextProperties(int index) {
			return super.createProperties(new Integer(index), new ChartPropertiesImpl());
		}

		/**
		 * Returns the text properties at the specified position.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties getTextProperties(int index) {
			return super.getProperties(new Integer(index));
		}

		/**
		 * Remove the text properties at the specified position.
		 * 
		 * @param index
		 */
		public void removeTextProperties(int index) {
			super.removeProperties(new Integer(index));
		}

		/**
		 * Remove all of text property.
		 */
		public void clearAllTextProperties() {
			super.clearAllProperties();
		}
	}
	
	/**
	 * The GanttRow of the gnatt chart table.
	 * @author jimmy
	 * 
	 */
	public static class GanttRow extends PropertiesListHandler {

		private static final long serialVersionUID = 20110323173910L;
		
		/**
		 * Add a label in the chart table.
		 * 
		 * @return ChartProperties
		 */
		public ChartProperties createLabel(String label) {
			ChartPropertiesImpl props = new ChartPropertiesImpl();
			props.addProperty("label", label);
			return super.createProperties(props);
		}

		/**
		 * Return the properties of cell.
		 * 
		 * @param index
		 * @return ChartProperties
		 */
		public ChartProperties getCellProperties(int index) {
			return super.getProperties(index);
		}

		/**
		 * Remove the label in the chart table.
		 * 
		 * @param label
		 */
		public void removeLabel(ChartProperties label) {
			super.removeProperties(label);
		}

		/**
		 * Remove the label in the specified position of the chart table.
		 * 
		 * @param index
		 */
		public void removeLabel(int index) {
			super.removeProperties(index);
		}
		
		/**
		 * Clear all of label.
		 */
		public void clearAllLabel() {
			super.clearAllProperties();
		}
	}
}
