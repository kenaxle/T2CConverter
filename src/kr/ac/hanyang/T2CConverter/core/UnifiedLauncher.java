package kr.ac.hanyang.T2CConverter.core;

import org.eclipse.m2m.atl.sample.files.T2CATLConverter;

import kr.ac.hanyang.oCamp.camp.pdp.DeploymentPlan;
import kr.ac.hanyang.oCamp.camp.platform.oCampPlatform;
import kr.ac.hanyang.oCamp.camp.platform.oCampPlatformLauncher;
import kr.ac.hanyang.oCamp.core.mgmt.BaseEntityManager;
import kr.ac.hanyang.oCamp.launcher.NewLauncher;
import kr.ac.hanyang.tosca2camp.Tosca2CampPlatform;

public class UnifiedLauncher {
	
	
	public static void main( String[] args ) throws Exception{
		
	Tosca2CampPlatform toscaPlatform = Tosca2CampPlatform.newPlatform();
	toscaPlatform.createServiceTemplate("WebAppExample.yml");
	oCampPlatform campPlatform = new oCampPlatformLauncher()
            .useManagementContext(new BaseEntityManager())
            .launch()
            .getCampPlatform();
	
	T2CATLConverter converter = T2CATLConverter.newT2CATLConverter()
											   .serviceTemplate(toscaPlatform.getServiceTemplate("ServiceTemplate"));
	
	converter.runConversion();
	
	
	campPlatform.oCampPdp().registerDeploymentPlan(converter.getDeploymentPlan());
	
	
	
//	ConverterPlatform converterPlatform = ConverterPlatform.newConverterPlatform()
//													.toscaPlatform(toscaPlatform)
//													.campPlatform(campPlatform)
//													.converter(converter);
	
	//converter.convert(converter);
	//converterPlatform.loadServiceTemplate("WebappExample.yml");
	
	//converterPlatform.convertServiceTemplate("ServiceTemplate");
	}
}
