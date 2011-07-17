/* CategoryFusionchartProperty.java

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
 * The categories chart property lets you control a variety of functional
 * elements on the chart with {link CategoryModel}.
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
public class CategoryChartConfig extends ChartConfig {
	private static final long serialVersionUID = 20110312194840L;

	private SeriesConfig _seriesConfig;
	private CategoriesConfig _categoryConfig;
	private TrendLineConfig _trendLineConfig;

	/**
	 * Returns the lower limit of the chart.
	 * 
	 * @return Number
	 */
	public Number getYAxisMinValue() {
		return Utils.getNumber(getProperty("yAxisMinValue"));
	}

	/**
	 * Sets the lower limit of the chart.
	 * 
	 * @param yAxisMinValue
	 */
	public void setYAxisMinValue(Number yAxisMinValue) {
		addProperty("yAxisMinValue", String.valueOf(yAxisMinValue));
	}

	/**
	 * Returns the upper limit of the chart.
	 * 
	 * @return Number
	 */
	public Number getYAxisMaxValue() {
		return Utils.getNumber(getProperty("yAxisMaxValue"));
	}

	/**
	 * Sets the upper limit of the chart.
	 * 
	 * @param yAxisMaxValue
	 */
	public void setYAxisMaxValue(Number yAxisMaxValue) {
		addProperty("yAxisMaxValue", String.valueOf(yAxisMaxValue));
	}

	/**
	 * Returns the canvas background color.
	 * 
	 * @return String
	 */
	public String getCanvasBgColor() {
		return getProperty("canvasBgColor");
	}

	/**
	 * Sets the canvas background color.
	 * 
	 * @param canvasBgColor
	 */
	public void setCanvasBgColor(String canvasBgColor) {
		addProperty("canvasBgColor", Utils.toFusionchartColor(canvasBgColor));
	}

	/**
	 * Returns the alpha for Canvas Background.
	 * 
	 * @return Number
	 */
	public int getCanvasBgAlpha() {
		return Utils.getInt(getProperty("canvasBgAlpha"));
	}

	/**
	 * Sets the alpha for Canvas Background.
	 * 
	 * @param canvasBgAlpha
	 */
	public void setCanvasBgAlpha(int canvasBgAlpha) {
		addProperty("canvasBgAlpha", String.valueOf(canvasBgAlpha));
	}

	/**
	 * Return the series config.
	 */
	public SeriesConfig getSeriesConfig() {
		if (_seriesConfig == null) {
			_seriesConfig = new SeriesConfig();
			addPropertyListener(_seriesConfig);
		}
		return _seriesConfig;
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

	/**
	 * Return the trend line config.
	 */
	public TrendLineConfig getTrendLineConfig() {
		if (_trendLineConfig == null) {
			_trendLineConfig = new TrendLineConfig();
			addPropertyListener((ChartInfoNotifier)_trendLineConfig);
		}
		return _trendLineConfig;
	}

	/*package*/ SeriesConfig seriesConfig() {
		return _seriesConfig;
	}

	/*package*/ CategoriesConfig categoryConfig() {
		return _categoryConfig;
	}

	/*package*/ TrendLineConfig trendLineConfig() {
		return _trendLineConfig;
	}
}