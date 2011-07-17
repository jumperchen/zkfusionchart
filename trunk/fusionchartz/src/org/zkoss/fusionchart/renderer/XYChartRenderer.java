/* XYChartRenderer.java

	Purpose:
		
	Description:
		
	History:
		Jun 13, 2011 10:47:20 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.renderer;

import org.zkoss.fusionchart.config.CategoryChartConfig;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.config.PropertiesRenderProxy;
import org.zkoss.fusionchart.config.XYChartConfig;
import org.zkoss.fusionchart.config.XYChartConfig.XAxisConfig;
import org.zkoss.fusionchart.impl.Utils;

/**
 * @author jimmy
 * 
 */
public abstract class XYChartRenderer extends CategoryChartRenderer {
	private static final long serialVersionUID = 20110613224710L;

	private final XYChartConfig _chartConfig;

	public XYChartRenderer() {
		defineChartConfig(_chartConfig = new XYChartConfig());
		addPropertyListener((ChartInfoNotifier)_chartConfig);
	}
	
	public void defineChartConfig(CategoryChartConfig chartConfig) {
	}
	
	public abstract void defineChartConfig(final XYChartConfig chartConfig);

	public ChartConfig getChartConfig() {
		return _chartConfig;
	}
	
	protected CategoryChartConfig getCategoryChartConfig() {
		return _chartConfig;
	}
	
	public void renderCategoryProperty(StringBuffer sb, int index,
			Comparable category) {
		 XAxisConfig config = 
			 PropertiesRenderProxy.getXAxisConfig(_chartConfig);
		if (config == null) return;
		
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getProperties(config, index, category));
	}
}
