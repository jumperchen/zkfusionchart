/* AbstractFusionchartRenderer.java

	Purpose:
		
	Description:
		
	History:
		Mar 21, 2011 6:02:31 PM, Created by jimmy

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

This program is distributed under GPL Version 3.0 in the hope that
it will be useful, but WITHOUT ANY WARRANTY.
 */
package org.zkoss.fusionchart.renderer;

import org.zkoss.fusionchart.api.FusionchartRenderer;
import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.impl.Utils;

/**
 * A skeletal implementation for a Fusionchart XML Renderer.
 * 
 * @author jimmy
 * 
 */
abstract class AbstractFusionchartRenderer extends ChartInfoNotifier implements
		FusionchartRenderer {
	private static final long serialVersionUID = 20110321180310L;

	public void renderChartProperty(StringBuffer sb) {
		Utils.renderChartProperties(sb, getChartConfig());
	}
}
