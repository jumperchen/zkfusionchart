/* ChartConfig.java

	Purpose:
		
	Description:
		
	History:
		Mar 9, 2011 11:47:47 AM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.impl.Utils;

/**
 * The Fusionchart config lets you control a variety of functional elements on
 * the chart. For example, you can opt to show/hide data labels, data values,
 * y-axis values. You can also set chart limits and extended properties.
 * <p/>
 * All property of chart, please refer to the following reference document. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie2D.html">Pie
 * 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Pie3D.html">Pie
 * 3D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_MSColumn2D.html"
 * >Multi-series Column 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_MSColumn3D.html"
 * >Multi-series Column 3D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_MSBar2D.html"
 * >Multi-series Bar 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_StCol2D.html"
 * >Stacked Column 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_StCol3D.html"
 * >Stacked Column 3D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_StBar2D.html"
 * >Stacked Bar 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_MSLine2D.html"
 * >Multi-Series Line 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_MSArea2D.html"
 * >Multi-Series Area 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_StArea2D.html"
 * >Stacked Area 2D Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Col2DLineDY.html"
 * >Multi-Series Column 2D Line Dual Y Chart</a>. <br/>
 * <a href=
 * "http://www.fusioncharts.com/free/docs/Contents/ChartSS/XML_Col3DLineDY.html"
 * >Multi-Series Column 3D Line Dual Y Chart</a>.
 * 
 * @author jimmy
 * 
 */
public class ChartConfig extends ChartPropertiesImpl {
	private static final long serialVersionUID = 201103211163340L;

	/**
	 * Returns whether to perform animation.
	 * 
	 * @return boolean
	 */
	public boolean isAnimation() {
		return Utils.getBoolean(getProperty("animation"));
	}

	/**
	 * Sets whether to perform animation.
	 * 
	 * @param animation
	 */
	public void setAnimation(boolean animation) {
		addProperty("animation", Utils.toFusionchartBoolean(animation));
	}

	/**
	 * Returns the background color for the chart. You can set any hex color
	 * code as the value of this attribute. To specify a gradient as background
	 * color, separate the hex color codes of each color in the gradient using
	 * comma. <br/>
	 * Example: FF5904,FFFFFF.
	 * 
	 * @return String
	 */
	public String getBgColor() {
		return getProperty("bgColor");
	}

	/**
	 * Sets the background color for the chart. You can set any hex color code
	 * as the value of this attribute. To specify a gradient as background
	 * color, separate the hex color codes of each color in the gradient using
	 * comma. <br/>
	 * Example: FF5904,FFFFFF.
	 * 
	 * @param bgColor
	 */
	public void setBgColor(String bgColor) {
		addProperty("bgColor", Utils.toFusionchartColor(bgColor));
	}

	/**
	 * Returns the alpha (transparency) for the background.
	 * 
	 * @return Number
	 */
	public int getBgAlpha() {
		return Utils.getInt(getProperty("bgAlpha"));
	}

	/**
	 * Sets the alpha (transparency) for the background.
	 * 
	 * @param bgAlpha
	 */
	public void setBgAlpha(int bgAlpha) {
		addProperty("bgAlpha", String.valueOf(bgAlpha));
	}
}
