/* CategoryFusionchartRenderer.java

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
package org.zkoss.fusionchart.renderer;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.config.CategoriesConfig;
import org.zkoss.fusionchart.config.CategoryChartConfig;
import org.zkoss.fusionchart.config.ChartConfig;
import org.zkoss.fusionchart.config.ChartInfoNotifier;
import org.zkoss.fusionchart.config.PropertiesRenderProxy;
import org.zkoss.fusionchart.config.SeriesConfig;
import org.zkoss.fusionchart.config.SeriesConfig.SeriesProperties;
import org.zkoss.fusionchart.config.TrendLineConfig;
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
 * @see CategoryModel
 */
public abstract class CategoryChartRenderer extends AbstractFusionchartRenderer {

	private static final long serialVersionUID = 20110311203310L;

	private final CategoryChartConfig _chartConfig;

	public CategoryChartRenderer() {
		defineChartConfig(_chartConfig = new CategoryChartConfig());
		addPropertyListener((ChartInfoNotifier) _chartConfig);
	}

	public ChartConfig getChartConfig() {
		return _chartConfig;
	}
	
	protected CategoryChartConfig getCategoryChartConfig() {
		return _chartConfig;
	}

	public abstract void defineChartConfig(final CategoryChartConfig chartConfig);

	public void renderChildTages(StringBuffer sb) {
		renderTrendLine(sb,
				PropertiesRenderProxy.getTrendLineConfig(getCategoryChartConfig()));
	}

	public void renderCategoriesProperty(StringBuffer sb) {
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getCategoryConfig(getCategoryChartConfig()));
	}

	public void renderCategoryProperty(StringBuffer sb, int index,
			Comparable category) {
		CategoriesConfig config = 
			PropertiesRenderProxy.getCategoryConfig(getCategoryChartConfig());
		if (config != null)
			Utils.renderChartProperties(sb, 
					PropertiesRenderProxy.getProperties(config, index, category));
	}

	public void renderSeriesProperty(StringBuffer sb, int index,
			Comparable series) {
		SeriesConfig config = 
			PropertiesRenderProxy.getSeriesConfig(getCategoryChartConfig());
		if (config == null) return;
		
		Utils.renderChartProperties(sb, 
				PropertiesRenderProxy.getProperties(config, index, series));
	}

	public void renderDatasetProperty(StringBuffer sb, int seriesIndex,
			int categoryIndex, Comparable series, Comparable category) {
		SeriesConfig config = 
			PropertiesRenderProxy.getSeriesConfig(getCategoryChartConfig());
		if (config == null) return;
		
		ChartProperties sProperties = 
			PropertiesRenderProxy.getProperties(config, seriesIndex, series);
		
		if (sProperties instanceof SeriesProperties)
			Utils.renderChartProperties(sb, 
					PropertiesRenderProxy.getProperties(
							(SeriesProperties) sProperties, categoryIndex, series));
	}

	protected static void renderTrendLine(StringBuffer sb,
			TrendLineConfig config) {
		if (config == null) return;
		
		int size = config.size();
		if (size == 0) return;
		
		Utils.renderChartProperties(
				sb.append("<trendlines"), config).append(">");
		for (int i = 0; i < size; i++)
			Utils.renderChartProperties(
					sb.append("<line"), config.getTrendLine(i))
					.append("/>");
		sb.append("</trendlines>");
	}
}
