package org.zkoss.fusionchart.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.fusionchart.config.GanttChartConfig;
import org.zkoss.lang.Strings;

public class Utils {
	private static SimpleDateFormat df = new SimpleDateFormat(
			GanttChartConfig.DEFAULT_DATE_FORMAT);
	
	/*render*/
	public static final StringBuffer renderChartProperties(StringBuffer sb,
			ChartProperties properties) {
		if (properties == null) return sb;
		
		for (Iterator it = properties.getAllProperties().entrySet().iterator(); it
				.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sb.append(" ").append(entry.getKey()).append("='")
					.append(entry.getValue()).append("'");
		}
		return sb;
	}
	
	public static final String renderFusionchartAttr(String name, Object value) {
		String val = String.valueOf(value);
		if ("null".equals(val))
			return "";

		if (!Strings.isBlank(val))
			return new StringBuffer(" ").append(name).append("='").append(val)
					.append("'").toString();

		return "";
	}
	
	public static final String renderFusionchartBoolean(String name, boolean value) {
		return new StringBuffer(" ").append(name).append("='")
				.append(value ? "1'" : "0'").toString();
	}
	
	public static final String renderFusionchartDate(String name, Date value) {
		if (value == null)
			return "";

		return new StringBuffer(" ").append(name).append("='")
				.append(df.format(value)).append("'").toString();
	}

	/*transfer*/
	public static final String toFusionchartColor(String color) {
		if ("null".equals(color) || Strings.isBlank(color))
			return null;

		if (color.startsWith("#"))
			return color.substring(1);
		return color;
	}
	
	public static final String toFusionchartBoolean(boolean value) {
		return value ? "1" : "0";
	}

	public static final String toFusionchartDate(Date value) {
		return df.format(value);
	}
	
	/*getter*/
	public static boolean getBoolean(String value) {
		return "1".equals(value);
	}
	
	public static int getInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public static Number getNumber(String value) {
		try {
			return NumberFormat.getInstance().parse(value);
		} catch (ParseException e) {
			return new Integer(0);
		}
	}
	
	/*chart*/
	public static int stringToInt(String str) {
		int j = str.lastIndexOf("px");
		if (j > 0) {
			final String num = str.substring(0, j);
			return Integer.parseInt(num);
		}
		
		j = str.lastIndexOf("pt");
		if (j > 0) {
			final String num = str.substring(0, j);
			return (int) (Integer.parseInt(num) * 1.3333);
		}

		j = str.lastIndexOf("em");
		if (j > 0) {
			final String num = str.substring(0, j);
			return (int) (Integer.parseInt(num) * 13.3333);
		}
		return Integer.parseInt(str);
	}
	
//	public static final void addClientEventInvok(String uuid, StringBuffer sb,
//	int seriIndex, int cateIndex) {
//sb.append(" link=\"JavaScript:zk.Widget.$('").append(uuid)
//		.append("').clickChart('").append(seriIndex).append("','")
//		.append(cateIndex).append("');\"");
//}
//
//public static final void addClientEventInvok(String uuid, StringBuffer sb,
//	int index, int seriIndex, int cateIndex) {
//sb.append(" link=\"JavaScript:zk.Widget.$('").append(uuid)
//		.append("').clickChart('").append(seriIndex).append("','")
//		.append(cateIndex).append("','").append(index).append("');\"");
//}
}