package com.thinking.machines.cms;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import java.util.*;
import com.thinking.machines.cms.services.model.*;
import org.springframework.beans.factory.annotation.*;
import javax.servlet.*;

@SpringBootApplication
public class CmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}
	@Autowired
	ServletContext servletContext;
	@Bean
	public CMSModel getCMSModel()
	{
		System.out.println("get cms model invoked");
		CMSModel cmsModel=new CMSModel();
		List<com.thinking.machines.cms.dl.pojo.Administrator> dlAdministrators=new com.thinking.machines.cms.dl.AdministratorDAO().getAll();
		List<Administrator> administrators=new LinkedList<>();
		dlAdministrators.forEach((administrator)->{
			Administrator a=new Administrator();
			a.setUsername(administrator.getUsername());
			a.setFirstName(administrator.getFirstName());
			a.setLastName(administrator.getLastName());
			a.setPwd(administrator.getPwd());
			a.setPwdKey(administrator.getPwdKey());
			administrators.add(a);
		});
		cmsModel.setAdministrators(administrators);
		List<com.thinking.machines.cms.dl.pojo.Drone> dlDrones=new com.thinking.machines.cms.dl.DroneDAO().getAll();
		List<Drone> drones=new LinkedList<>();
		dlDrones.forEach((drone)->{
			Drone d=new Drone();
			d.setId(drone.getId());
			d.setModelName(drone.getModelName());
			d.setModelNumber(drone.getModelNumber());
			d.setBrand(drone.getBrand());
			d.setDroneKey(drone.getDroneKey());
			d.setFlightTime(drone.getFlightTime());
			d.setLoadCapacity(drone.getLoadCapacity());
			d.setSpeed(drone.getSpeed());
			d.setAltitude(drone.getAltitude());
			drones.add(d);
		});
		System.out.println("Servlet Context"+servletContext);
		servletContext.setAttribute("drones",drones);
		cmsModel.setDrones(drones);
		List<com.thinking.machines.cms.dl.pojo.Member> dlMembers=new com.thinking.machines.cms.dl.MemberDAO().getAll();
		List<Member> members=new LinkedList<>();
		dlMembers.forEach((member)->{
			Member m=new Member();
			m.setUsername(member.getUsername());
			m.setFirstName(member.getFirstName());
			m.setLastName(member.getLastName());
			m.setMobileNumber(member.getMobileNumber());
			m.setPwd(member.getPwd());
			m.setPwdKey(member.getPwdKey());
			members.add(m);
		});
		servletContext.setAttribute("members",members);
		cmsModel.setMembers(members);
		return cmsModel;
	}
}
