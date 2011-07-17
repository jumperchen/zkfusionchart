/* OrientConverter.java

	Purpose:
		
	Description:
		
	History:
		Jul 17, 2011 4:25:29 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.zkfusionchartdemo;

import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Radiogroup;

/**
 * @author jimmy
 * 
 */
public class OrientConverter implements TypeConverter {
	public Object coerceToBean(java.lang.Object val,
			org.zkoss.zk.ui.Component comp) {
		return ((Radiogroup) comp).getSelectedItem().getLabel();
	}

	public Object coerceToUi(java.lang.Object val,
			org.zkoss.zk.ui.Component comp) {
		
		((Radiogroup) comp).setSelectedIndex("vertical".equals(val) ? 0 : 1);
		return "";
	}
}