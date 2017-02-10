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

public class ConverterPlatformBK {

	private static final Logger log = LoggerFactory.getLogger(ConverterPlatformBK.class);
	
	//private List<String> convertedNodes;
	private Tosca2CampPlatform toscaPlatform;
	private OCampPlatform campPlatform;
	//private ServiceCatalog serviceCatalog;
	private T2CATLConverter converter;
	
	//private Matcher matcher;
	//private Map<String, Service> convertedServices;
	
	protected ConverterPlatformBK(){
		//convertedNodes = new ArrayList<String>();
	}
	
	public static ConverterPlatformBK newConverterPlatform(){
		ConverterPlatformBK platform = new ConverterPlatformBK();
		return platform;
	}
	
	public ConverterPlatformBK converter(T2CATLConverter converter){
		this.converter = converter;
		return this;
	}
	
	public ConverterPlatformBK toscaPlatform(Tosca2CampPlatform toscaPlatform){
		this.toscaPlatform = toscaPlatform;
		return this;
	}
	
	public ConverterPlatformBK campPlatform(oCampPlatform campPlatform){
		this.campPlatform = campPlatform;
		return this;
	}
	
//	public void loadServiceTemplate(String stPath){
//		ServiceTemplate st = toscaPlatform.createServiceTemplate(stPath);
//		st.resolve();
//	}
	
//	public void loadServiceCatalog(){
//		serviceCatalog = (ServiceCatalog) ((BaseEntityManager) campPlatform.getBrooklynManagementContext()).getServiceCatalog();
//	}
	
//	public NodeDef findRoot(ServiceTemplate st){
//		
//		TopologyTemplate tt = st.getTopologyTemplate();
//		for(NodeDef node: tt.getNodes()){
//			//if 
//		}
//		
//		return null;
//	}
//	
//	
//	public void convertServiceTemplate(String templateName){
//		ServiceTemplate st = toscaPlatform.getServiceTemplate(templateName);
//		List<Service> services = new ArrayList<Service>();
//		if (st == null){
//			log.error(st+" is Null");
//		}
//		 
//		for(NodeDef node: st.getTopologyTemplate().getNodes()){
//			String nodeType = node.getTypeName();
//			
//			String[] serviceNames = ArrayUtils.addAll(Normalizer.normalizeServiceName(node.getTypeName()), Normalizer.normalizeServiceName(node.getDerived_from()));
//			String[] configs = new String[]{}; Map<String, Object> characteristicMap = new LinkedHashMap<String, Object>();
//			for (PropertyDef prop: node.getProperties()){
//				if (prop.hasValue()){
//					String[] normalized = Normalizer.normalizeProperty(prop.getName());
//					if (normalized != null){
//						configs = ArrayUtils.addAll(configs, normalized);
//						Object value;
//						if(Utilities.isaFunction(prop.getPropertyValue().getTypeName()))
//							value = st.evaluateFunction((Function) prop.getPropertyValue().getFunction());
//						else if(Utilities.isPrimative(prop.getPropertyValue().getTypeName())!=null)
//							value = prop.getPropertyValue();
//						else value = "get the cmplex value";
//						characteristicMap.put(prop.getName(), value);// = ArrayUtils.add(propNames, prop.getName());
//					}
//				}
//			}
//
//			FilterMask filter = new FilterMask().of().serviceNames(serviceNames)
//													 .mode(Matcher.FULL)
//													 .configs(configs);
//			
//			SortedSet<OCampEntityType> nameMatches = matchByType(filter.getServiceNames(), filter.getMode());
//			SortedSet<OCampEntityType> configMatches = matchByConfig(filter.getConfigs());
//			SortedSet<OCampEntityType> sensorMatches = matchBySensors(filter.getSensors());
//			SortedSet<OCampEntityType> effectorMatches = matchByEffectors(filter.getEffectors());
//			
//			SortedSet<OCampEntityType> results = Reducer.of().set(nameMatches)
//												 .set(configMatches)
//												 .set(sensorMatches)
//												 .set(effectorMatches)
//												 .reduceSets();
//			OCampEntityType result = results.first();
//			
//			Map<String, Object> serviceMap = new LinkedHashMap<String, Object>();
//			//Map<String, Object> characteristicMap = new LinkedHashMap<String, Object>();
//			
//			serviceMap.put("id", node.getTypeName());
//			characteristicMap.put("type", result.getName());
//			//for(String prop: propNames.keySet())
//			//	characteristicMap.put(prop, "Value");
//			//TODO put the configs and values here
//			List<Object> characteristics = new ArrayList<Object>();
//			characteristics.add(characteristicMap);
//			serviceMap.put("characteristics", characteristics);
//			
//			//OCampEntityType service = matchBestService(filter);
//
//			//populateService(service, node);
//			
//			
//			//OCampEntityType match = nodeToService(node);
//			
////			OCampEntityType match = matchBestService(FilterMask.of().serviceNames(new String[]{"Tomcat"})
////															   .mode(Matcher.SIMPLE)
////															   .configs(new String[]{"RUN_DIR"})
////															   .sensors(new String[]{"INSTALL_DIR"})
////															   .effectors(new String[]{"START"}));
////			log.info("Matched to "+match.toString());
//			
//		}
//	}
//	
////	public OCampEntityType nodeToService(NodeDef template){
////		String[] serviceNames = ArrayUtils.addAll(Normalizer.normalizeServiceName(template.getTypeName()), Normalizer.normalizeServiceName(template.getDerived_from()));
////		String[] configs = new String[]{}; Map<String, Object> characteristicMap = new LinkedHashMap<String, Object>();
////		for (PropertyDef prop: template.getProperties()){
////			if (prop.hasValue()){
////				String[] normalized = Normalizer.normalizeProperty(prop.getName());
////				if (normalized != null){
////					configs = ArrayUtils.addAll(configs, normalized);
////					Object value;
////					if(Utilities.isaFunction(prop.getPropertyValue().getTypeName()))
////						value = template.evaluateFunction((Function) prop.getPropertyValue());
////					else if(Utilities.isPrimative(prop.getPropertyValue().getTypeName())!=null)
////						value = prop.getPropertyValue();
////					else value = "get the cmplex value";
////					characteristicMap.put(prop.getName(), value);// = ArrayUtils.add(propNames, prop.getName());
////				}
////			}
////		}
////
////		FilterMask filter = new FilterMask().of().serviceNames(serviceNames)
////												 .mode(Matcher.FULL)
////												 .configs(configs);
////		
////		SortedSet<OCampEntityType> nameMatches = matchByType(filter.getServiceNames(), filter.getMode());
////		SortedSet<OCampEntityType> configMatches = matchByConfig(filter.getConfigs());
////		SortedSet<OCampEntityType> sensorMatches = matchBySensors(filter.getSensors());
////		SortedSet<OCampEntityType> effectorMatches = matchByEffectors(filter.getEffectors());
////		
////		SortedSet<OCampEntityType> results = Reducer.of().set(nameMatches)
////											 .set(configMatches)
////											 .set(sensorMatches)
////											 .set(effectorMatches)
////											 .reduceSets();
////		OCampEntityType result = results.first();
////		
////		Map<String, Object> serviceMap = new LinkedHashMap<String, Object>();
////		//Map<String, Object> characteristicMap = new LinkedHashMap<String, Object>();
////		
////		serviceMap.put("id", template.getTypeName());
////		characteristicMap.put("type", result.getName());
////		//for(String prop: propNames.keySet())
////		//	characteristicMap.put(prop, "Value");
////		//TODO put the configs and values here
////		List<Object> characteristics = new ArrayList<Object>();
////		characteristics.add(characteristicMap);
////		serviceMap.put("characteristics", characteristics);
////		
////		OCampEntityType service = matchBestService(filter);
////
////		populateService(service, template);
////		
////		
////		
////		return matchBestService(filter);
////		
////	}
//	
//	public OCampEntityType matchBestService(FilterMask filterMask){
//		//String serviceName = (String) filterMask.getServiceName();
//		//Matcher matcher = (Matcher) filterMask.getMode();
//		
//		SortedSet<OCampEntityType> nameMatches = matchByType(filterMask.getServiceNames(), filterMask.getMode());
//		SortedSet<OCampEntityType> configMatches = matchByConfig(filterMask.getConfigs());
//		SortedSet<OCampEntityType> sensorMatches = matchBySensors(filterMask.getSensors());
//		SortedSet<OCampEntityType> effectorMatches = matchByEffectors(filterMask.getEffectors());
//		
//		SortedSet<OCampEntityType> results = Reducer.of().set(nameMatches)
//											 .set(configMatches)
//											 .set(sensorMatches)
//											 .set(effectorMatches)
//											 .reduceSets();
//
//			return results.first();
//
//	}
//	
//	private SortedSet<OCampEntityType> matchByType(String[] nodeTypes, Matcher matcher){
//			switch (matcher) {
//			case FULL:
//				return fullMatching(nodeTypes);
//			case SIMPLE:
//				return fullMatching(nodeTypes);//simpleMatching(nodeTypes);
//			default:
//				return fullMatching(nodeTypes);
//			}
//	}
//	
//	private SortedSet <OCampEntityType> matchByConfig(String[] configs){
//		SortedSet<OCampEntityType> matches = new TreeSet<OCampEntityType>();
//		Class clazz = oCampConfigKeys.class;
//		Set<ConfigKey<?>> configSet = new LinkedHashSet<ConfigKey<?>>();
//		if ( configs != null){
//			for(String configName: configs){
//				try{
//					if (clazz.getField(configName).get(null) instanceof ConfigKey<?>)
//						configSet.add((ConfigKey<?>) clazz.getField(configName).get(null));
//					else
//						configSet.add(((ConfigKey.HasConfigKey<?>) clazz.getField(configName).get(null)).getConfigKey());	
//				}catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//					e.printStackTrace();
//				}
//			}
//			for (OCampEntityType entity: ((BaseEntityManager)campPlatform.getBrooklynManagementContext()).getServiceCatalog().getServices()){
//				if (entity.getConfigKeys().containsAll(configSet))
//					matches.add(entity);
//			}
//		}
//		return matches;
//	}
//	
//	private SortedSet<OCampEntityType> matchBySensors(String[] sensors){
//		SortedSet<OCampEntityType> matches = new TreeSet<OCampEntityType>();
//		Class clazz = oCampSensors.class;
//		Set<Sensor<?>> sensorSet = new LinkedHashSet<Sensor<?>>();
//		if ( sensors != null){
//			for(String sensorName: sensors){
//				try{
//					sensorSet.add((Sensor<?>) clazz.getField(sensorName).get(null));	
//				}catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//					e.printStackTrace();
//				}
//			}
//			for (OCampEntityType entity: ((BaseEntityManager)campPlatform.getBrooklynManagementContext()).getServiceCatalog().getServices()){
//				if (entity.getSensors().containsAll(sensorSet))
//					matches.add(entity);
//			}		
//		}
//		return matches;
//	}
//	
//	private SortedSet<OCampEntityType> matchByEffectors(String[] effectors){
//		SortedSet<OCampEntityType> matches = new TreeSet<OCampEntityType>();
//		Class clazz = oCampEffectors.class;
//		Set<Effector<?>> effectorSet = new LinkedHashSet<Effector<?>>();
//		if ( effectors != null){
//			for(String effectorName: effectors){
//				try{
//					effectorSet.add((Effector<?>) clazz.getField(effectorName).get(null));	
//				}catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//					e.printStackTrace();
//				}
//			}
//			for (OCampEntityType entity: ((BaseEntityManager)campPlatform.getBrooklynManagementContext()).getServiceCatalog().getServices()){
//				if (entity.getEffectors().containsAll(effectorSet))
//					matches.add(entity);
//			}	
//		}
//		return matches;
//		
//	}
//	
//	private SortedSet<OCampEntityType> fullMatching(String[] nodeTypes){
//		SortedSet<OCampEntityType> matches = new TreeSet<OCampEntityType>();
//		List<String> nodesList = Arrays.asList(nodeTypes);
//		for (OCampEntityType entity: ((BaseEntityManager)campPlatform.getBrooklynManagementContext()).getServiceCatalog().getServices()){
//			if (nodesList.contains(entity.getName()))
//				matches.add(entity);
//		}
//		return matches;
//	}
//	
////	private SortedSet<OCampEntityType> simpleMatching(String[] nodeTypes){
////		SortedSet<OCampEntityType> matches = new TreeSet<OCampEntityType>();
////		List<String> nodesList = Arrays.asList(nodeTypes);
////		for (OCampEntityType entity: ((BaseEntityManager)campPlatform.getBrooklynManagementContext()).getServiceCatalog().getServices()){
////			if (entity.getName().contains(nodeType))
////				matches.add(entity);
////		}
////		return matches;
////	}
//	
//	
//	private void populateService(OCampEntityType match, NodeDef template){
//		Map<String, Object> serviceMap = new LinkedHashMap<String, Object>();
//		Map<String, Object> characteristicMap = new LinkedHashMap<String, Object>();;
//		
//		serviceMap.put("id", match.getName());
//		
//		//characteristicMap.put("type", match.g)
//		//ServiceCharacteristic characteristic = 
//		//Service service =  
//	}
//	
//	private static class Reducer{
//		List<SortedSet<OCampEntityType>> sets;
//		//List<Map<>>
//		
//		
//		private Reducer(){ sets = new ArrayList<SortedSet<OCampEntityType>>();}
//		
//		public static Reducer of(){
//			return new Reducer();
//		}
//		
//		public Reducer set(SortedSet<OCampEntityType> set){
//			sets.add(set);
//			return this;
//		}
//		
//		public List<SortedSet<OCampEntityType>> getSets(){
//			return sets;
//		}
//		
//		public SortedSet<OCampEntityType> reduceSets(){
//			OCampEntityType smallest;
//			SortedSet<OCampEntityType> results = new TreeSet<OCampEntityType>();
//			List<Tracker> trackers = new ArrayList<Tracker>();
//			for(SortedSet<OCampEntityType> set: sets){
//				Iterator iter = set.iterator();
//				Tracker tracker; OCampEntityType current;
//				if (iter.hasNext()){
//					current = (OCampEntityType) iter.next();
//					tracker = new Tracker(iter,current,null);
//					trackers.add(tracker);
//				}
//			}
//			Collections.sort(trackers);
//			Boolean canStop = false;
//			while(! canStop){
//				smallest = (OCampEntityType) trackers.iterator().next().getCurrent();
//				Boolean intersection = true;
//				for(Tracker tracker: trackers){
//					
//					if (tracker.getCurrent().equals(smallest)){
//	
//						tracker.setPrevious(tracker.getCurrent());
//						if (tracker.getIterator().hasNext())
//							tracker.setCurrent((OCampEntityType) tracker.getIterator().next());
//						else
//							canStop = true;
//					}else
//						intersection = false;
//				}
//				Collections.sort(trackers);
//				if (intersection) results.add(smallest);
//			}
//			
//			
//			//smallest = entityName.
//			return results;
//			
//		}	
//		
//	}
//	
//	
//    private static class Tracker implements Comparable{
//		Iterator  iterator;
//		OCampEntityType current;
//		OCampEntityType previous;
//		
//		protected Tracker(Iterator i, OCampEntityType curr, OCampEntityType next ){
//			this.iterator = i; this.current = curr; this.previous = next;
//		}
//
//		public OCampEntityType getCurrent() {
//			return current;
//		}
//
//		public void setCurrent(OCampEntityType current) {
//			this.current = current;
//		}
//
//		public OCampEntityType getPrevious() {
//			return previous;
//		}
//
//		public void setPrevious(OCampEntityType next) {
//			this.previous = next;
//		}
//
//		public Iterator getIterator() {
//			return iterator;
//		}
//		
//		public int compareTo(Object obj){
//			Tracker tracker = (Tracker) obj;
//			return current.compareTo(tracker.current);
//		}
//	}
//	
//	
//	private static class FilterMask{
//		String[] serviceNames;
//		Matcher mode;
//		String[] configs;
//		String[] sensors;
//		String[] effectors;
//		
//		private FilterMask(){}
//		
//		public static FilterMask of(){
//			return new FilterMask();
//		}
//		
//		public FilterMask serviceNames(String[] names){
//			this.serviceNames = names;
//			return this;
//		}
//		
//		public FilterMask mode(Matcher mode){
//			this.mode = mode;
//			return this;
//		}
//		
//		public FilterMask configs(String[] configs){
//			this.configs = configs;
//			return this;
//		}
//		
//		public FilterMask sensors(String[] sensors){
//			this.sensors = sensors;
//			return this;
//		}
//		
//		public FilterMask effectors(String[] effectors){
//			this.effectors = effectors;
//			return this;
//		}
//
//		public String[] getServiceNames() {
//			return serviceNames;
//		}
//
//		public Matcher getMode() {
//			return mode;
//		}
//
//		public String[] getConfigs() {
//			return configs;
//		}
//
//		public String[] getSensors() {
//			return sensors;
//		}
//
//		public String[] getEffectors() {
//			return effectors;
//		}
//		
// 	}
//	
	
}
