package kr.ac.hanyang.T2CConverter.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.brooklyn.api.effector.Effector;
import org.apache.brooklyn.api.entity.EntityType;
import org.apache.brooklyn.api.sensor.Sensor;
import org.apache.brooklyn.camp.spi.pdp.Service;
import org.apache.brooklyn.config.ConfigKey;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.m2m.atl.sample.files.T2CATLConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterators;

import kr.ac.hanyang.oCamp.api.catalog.ServiceCatalog;
import kr.ac.hanyang.oCamp.api.platform.OCampPlatform;
import kr.ac.hanyang.oCamp.camp.platform.oCampPlatform;
import kr.ac.hanyang.oCamp.core.mgmt.BaseEntityManager;
import kr.ac.hanyang.oCamp.core.mgmt.OCampEntityType;
import kr.ac.hanyang.oCamp.core.mgmt.OCampEntityTypeSnapshot;
import kr.ac.hanyang.oCamp.core.mgmt.oCampConfigKeys;
import kr.ac.hanyang.oCamp.core.mgmt.oCampEffectors;
import kr.ac.hanyang.oCamp.core.mgmt.oCampSensors;
import kr.ac.hanyang.tosca2camp.Tosca2CampPlatform;
import kr.ac.hanyang.tosca2camp.Utilities;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;
import kr.ac.hanyang.tosca2camp.templates.Function;
import kr.ac.hanyang.tosca2camp.templates.ServiceTemplate;
import kr.ac.hanyang.tosca2camp.templates.TopologyTemplate;

public class ConverterPlatform {

	private static final Logger log = LoggerFactory.getLogger(ConverterPlatform.class);
	

	private Tosca2CampPlatform toscaPlatform;
	private OCampPlatform campPlatform;
	private T2CATLConverter converter;
	
	protected ConverterPlatform(){

	}
	
	public static ConverterPlatform newConverterPlatform(){
		ConverterPlatform platform = new ConverterPlatform();
		return platform;
	}
	
	public ConverterPlatform converter(T2CATLConverter converter){
		this.converter = converter;
		return this;
	}
	
	public ConverterPlatform toscaPlatform(Tosca2CampPlatform toscaPlatform){
		this.toscaPlatform = toscaPlatform;
		return this;
	}
	
	public ConverterPlatform campPlatform(oCampPlatform campPlatform){
		this.campPlatform = campPlatform;
		return this;
	}

	
}
