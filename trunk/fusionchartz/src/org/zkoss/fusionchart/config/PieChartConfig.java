/* PieChartConfig.java

	Purpose:
		
	Description:
		
	History:
		Jun 12, 2011 11:52:58 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.impl.Utils;

/**
 * The pie chart property lets you control a variety of functional elements on
 * the chart with {link org.zkoss.zul.PieModel}.
 * <p/>
 * All property of chart, please refer to the following reference document. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie2D.html">Pie
 * 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie3D.html">Pie
 * 3D Chart</a>. <br/>
 * 
 * @author jimmy
 * 
 */
public class PieChartConfig extends ChartConfig {
	private static final long serialVersionUID = 20110312194840L;

	private CategoriesConfig _categoryConfig;

	/**
	 * Returns the alpha for all the pies on the chart.
	 */
	public int getPieFillAlpha() {
		return Utils.getInt(getProperty("pieFillAlpha"));
	}

	/**
	 * Sets the alpha for all the pies on the chart.
	 * @param pieFillAlpha
	 */
	public void setPieFillAlpha(int pieFillAlpha) {
		addProperty("pieFillAlpha", String.valueOf(pieFillAlpha));
	}

	/**
	 * Return the category config.
	 */
	public CategoriesConfig getCategoryConfig() {
		if (_categoryConfig == null) {
			_categoryConfig = new CategoriesConfig();
			addPropertyListener((ChartInfoNotifier)_categoryConfig);
		}
		return _categoryConfig;
	}

	/* package */CategoriesConfig categoryConfig() {
		return _categoryConfig;
	}
}
