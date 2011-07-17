/* ChartTypeConverter.java

	Purpose:
		
	Description:
		
	History:
		Jul 17, 2011 4:35:10 PM, Created by jimmyshiau

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under LGPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.zkfusionchartdemo;

import java.util.Iterator;

import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

/**
 * @author jimmy
 *
 */
public class ChartTypeConverter implements TypeConverter {
	public Object coerceToBean(java.lang.Object val,
			org.zkoss.zk.ui.Component comp) {
		return ((Listbox) comp).getSelectedItem().getValue();
	}

	public Object coerceToUi(java.lang.Object val,
			org.zkoss.zk.ui.Component comp) {
		
		Listbox lb = (Listbox) comp;
		
		int index = 0;
		for (Iterator it = lb.getItems().iterator(); it.hasNext(); index++) {
			Listitem item = (Listitem) it.next();
			if (val.equals(item.getValue())) {
				item.setSelected(true);
				break;
			}
		}
		
		return "";
	}
}