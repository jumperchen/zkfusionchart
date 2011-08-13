package org.zkoss.fusionchart.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.fusionchart.api.ChartProperties;
import org.zkoss.lang.Objects;
import org.zkoss.zul.event.ChartDataEvent;

public class ChartPropertiesImpl extends ChartInfoNotifier implements
		ChartProperties {
	private static final long serialVersionUID = 20110311151010L;
	private Map configMap = new HashMap(3);

	public ChartProperties addProperty(String key, String value) {
		if (!Objects.equals(getProperty(key), value)) {
			configMap.put(key, value);
			fireEvent(ChartDataEvent.CHANGED, null);
		}
		return this;
	}

	public ChartProperties removeProperty(String key) {
		Object o = configMap.remove(key);
		if (o != null)
			fireEvent(ChartDataEvent.CHANGED, null);
		return this;
	}

	public String getProperty(String key) {
		return (String) configMap.get(key);
	}

	public Map getAllProperties() {
		return Collections.unmodifiableMap(configMap);
	}

	public void clear() {
		if (!configMap.isEmpty()) {
			configMap.clear();
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}

	public void addAllProperties(Map config) {
		if (!config.isEmpty()) {
			configMap.putAll(config);
			fireEvent(ChartDataEvent.CHANGED, null);
		}
	}
}
