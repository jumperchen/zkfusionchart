/* PieChartRenderer.java

	Purpose:
		
	Description:
		
	History:
		Jun 12, 2011 10:54:56 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.renderer;

import org.zkoss.fusionchart.config.CategoriesConfig;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.config.PieChartConfig;
import org.zkoss.fusionchart.config.PropertiesRenderProxy;
import org.zkoss.fusionchart.impl.Utils;
import org.zkoss.zul.CategoryModel;

/**
 * The CategoryFusionchartRenderer was used to render the Fusionchart Free XML
 * String with {@link CategoryModel}.
 * <p/>
 * 
 * All property of chart, please refer to the following reference document. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie2D.html">Pie
 * 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie3D.html">Pie
 * 3D Chart</a>.
 * 
 * @author jimmy
 * @see org.zkoss.zul.PieModel
 */
public abstract class PieChartRenderer extends AbstractFusionchartRenderer {
	private static final long serialVersionUID = 20110612224710L;

	private final PieChartConfig _chartConfig;

	public PieChartRenderer() {
		defineChartConfig(_chartConfig = new PieChartConfig());
		addPropertyListener((ChartInfoNotifier)_chartConfig);
	}

	public abstract void defineChartConfig(final PieChartConfig chartConfig);

	public void renderChildTages(StringBuffer sb) {
	}

	public void renderCategoryProperty(StringBuffer sb, int index,
			Comparable category) {
		CategoriesConfig config = 
			PropertiesRenderProxy.getCategoryConfig(_chartConfig);
		if (config == null) return;
		
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getProperties(config, index, category));
	}

	public ChartConfig getChartConfig() {
		return _chartConfig;
	}
}
