/* FusionchartEngine.java

	Purpose:
		
	Description:
		
	History:
		Mar 9, 2011 2:13:47 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.api;

/**
 * Fusionchart engine is an engine that do the fusionchart XML building.
 *
 * This interface defines the chart engine for components like {@link org.zkoss.fusionchart.Fusionchart}
 * use to get the value of each data and the size of the chart data.
 *
 * @author jimmyshiau
 * @see org.zkoss.fusionchart.Fusionchart
 * @see org.zkoss.zul.ChartModel
 */
public interface FusionchartEngine {
	/**
	 * Generate the XML String for Fusionchart
	 * @param data the data used in drawing a chart; depends on implementation.
	 */
	public String createChartXML(Object data);
}
