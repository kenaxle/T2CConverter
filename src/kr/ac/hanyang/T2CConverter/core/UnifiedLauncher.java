package kr.ac.hanyang.T2CConverter.core;

import kr.ac.hanyang.oCamp.camp.platform.oCampPlatform;
import kr.ac.hanyang.oCamp.camp.platform.oCampPlatformLauncher;
import kr.ac.hanyang.oCamp.core.mgmt.BaseEntityManager;
import kr.ac.hanyang.oCamp.launcher.NewLauncher;
import kr.ac.hanyang.tosca2camp.Tosca2CampPlatform;

public class UnifiedLauncher {
	
	
	public static void main( String[] args ) throws Exception{
		
	Tosca2CampPlatform toscaPlatform = Tosca2CampPlatform.newPlatform();
	oCampPlatform campPlatform = new oCampPlatformLauncher()
            .useManagementContext(new BaseEntityManager())
            .launch()
            .getCampPlatform();
	
	ConverterPlatform converter = ConverterPlatform.newConverterPlatform()
													.toscaPlatform(toscaPlatform)
													.campPlatform(campPlatform)
													.matcher(Matcher.SIMPLE);
	
	converter.loadServiceTemplate("WebappExample.yml");
	
	converter.convertServiceTemplate("ServiceTemplate");
	}
}
