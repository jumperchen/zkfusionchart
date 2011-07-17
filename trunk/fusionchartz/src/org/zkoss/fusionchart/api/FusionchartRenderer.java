/* FusionchartRenderer.java

	Purpose:
		
	Description:
		
	History:
		Fri Mar 11 11:53:36 TST 2011, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
*/
package org.zkoss.fusionchart.api;

import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.zul.AbstractChartModel;

/**
 * For render chart properties 
 * @author jimmyshiau
 */
public interface FusionchartRenderer {
	public void renderChartProperty(StringBuffer sb);
	public void renderChildTages(StringBuffer sb);
	public ChartConfig getChartConfig();
}
