package kr.ac.hanyang.T2CConverter.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Normalizer{
	
	public Normalizer(){}
	
	private Map<String, String[]> propMap = new LinkedHashMap<String, String[]>(){{
		//root attributes
		this.put("private_address",null);
		this.put("public_address",null);
		this.put("networks",null);
		this.put("ports",null);
		//software component
		this.put("admin_credential",null);
		this.put("component_version",new String[]{"SUGGESTED_VERSION","VERSION"});
		//webapplication
		this.put("context_root",new String[]{"TARGET"});
		//dbms
		this.put("root_password",new String[]{"MYSQL_PASSWORD"});
		this.put("port",new String[]{"MYSQL_PORT"});
		//database
		this.put("name",null);
		this.put("port",null);
		//objectstorage
		this.put("name",null);
		this.put("size",null);
		this.put("maxsize",null);
		//blockstorage
		this.put("size",null);
		this.put("volume_id",null);
		this.put("snapshot_id",null);
		//loadbalancer
		this.put("algorithm",null);
		
	}};
	private Map<String, String[]> serviceMap = new LinkedHashMap<String, String[]>(){{
		this.put("tosca.nodes.Root",new String[]{""});
		this.put("tosca.nodes.Compute",new String[]{""});
		this.put("tosca.nodes.WebServer",new String[]{"kr.ac.hanyang.oCamp.entities.services.web.tomcat.Tomcat7",
													  "kr.ac.hanyang.oCamp.entities.services.web.tomcat.Tomcat8"});
		this.put("tosca.nodes.WebApplication",new String[]{"kr.ac.hanyang.oCamp.entities.artifacts.War"});
		this.put("tosca.nodes.SoftwareComponent",new String[]{"kr.ac.hanyang.oCamp.entities.artifacts.War",
															  "kr.ac.hanyang.oCamp.entities.artifacts.Software",
															  "kr.ac.hanyang.oCamp.entities.artifacts.Script"});
		this.put("tosca.nodes.ObjectStorage",new String[]{""});
		this.put("tosca.nodes.LoadBalancer",new String[]{"kr.ac.hanyang.oCamp.entities.services.web.WebAppCluster"});
		this.put("tosca.nodes.DBMS",new String[]{"kr.ac.hanyang.oCamp.entities.services.database.mysql.MySQL"});
		this.put("tosca.nodes.Database",new String[]{"kr.ac.hanyang.oCamp.entities.artifacts.Script"});
		this.put("tosca.nodes.Container.Runtime",new String[]{""}); //Docker
		this.put("tosca.nodes.Container.Application",new String[]{""}); //Karaf
		this.put("tosca.nodes.BlockStorage",new String[]{""});
	}};
	
	
	public static String[] normalizeProperty(String property){
		String[] toReturn = new Normalizer().propMap.get(property);
		return toReturn;
	}
	
	public static String[] normalizeServiceName(String name){
		String[] toReturn = new Normalizer().serviceMap.get(name);
		return toReturn;
	}
}
