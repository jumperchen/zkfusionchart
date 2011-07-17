package org.zkoss.fusionchart.config;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.impl.Utils;

public class TrendLineConfig extends PropertiesListProperties {
	private static final long serialVersionUID = 20110612141730L;

	/**
	 * Add a trend lines on the chart.
	 * 
	 * @return Trend
	 */
	public ChartProperties createTrendLine(Number startValue, Number endValue,
			String displayValue, String color) {
		ChartPropertiesImpl point = new ChartPropertiesImpl();
		point.addProperty("startValue", String.valueOf(startValue));
		point.addProperty("endValue", String.valueOf(endValue));
		point.addProperty("displayValue", displayValue);
		point.addProperty("color", Utils.toFusionchartColor(color));
		return super.createProperties(point);
	}

	/**
	 * Return the trend line.
	 * 
	 * @param index
	 * @return Trend
	 */
	public ChartProperties getTrendLine(int index) {
		return super.getProperties(index);
	}

	/**
	 * Remove the trend line on the chart.
	 * 
	 * @param point
	 */
	public void removeTrendLine(ChartProperties point) {
		super.removeProperties(point);
	}

	/**
	 * Remove the trend line on the chart in the specified position.
	 * 
	 * @param index
	 */
	public void removeTrendLine(int index) {
		super.removeProperties(index);
	}
	
	/**
	 * Clear all of trend lines.
	 */
	public void clearAllTrendLine() {
		super.clearAllProperties();
	}
}
