import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.user.pojo.*;
import com.thinking.machines.drone.pojo.*;
public class Initializer extends HttpServlet
{
	public void init(ServletConfig servletConfig)
	{
		try
		{
			super.init(servletConfig);
			System.out.println("Data Structure populated on websocket side");
			ServletContext servletContext=getServletContext();
			List<com.thinking.machines.cms.services.model.Drone> modelDrones=(List<com.thinking.machines.cms.services.model.Drone>)servletContext.getAttribute("drones");
			Drone webSocketDrone=null;
			List<Drone> drones=new LinkedList<>();
			for(com.thinking.machines.cms.services.model.Drone d:modelDrones)
			{
				webSocketDrone=new Drone();
				webSocketDrone.setId(String.valueOf(d.getId()));
				webSocketDrone.setName(d.getModelName());
				drones.add(webSocketDrone);
			}
			servletContext.setAttribute("webSocketDrones",drones);
			List<com.thinking.machines.cms.services.model.Member> modelMembers=(List<com.thinking.machines.cms.services.model.Member>)servletContext.getAttribute("members");
			User webSocketUser=null;
			List<User> users=new LinkedList<>();
			for(com.thinking.machines.cms.services.model.Member d:modelMembers)
			{
				webSocketUser=new User();
				webSocketUser.setUsername(d.getUsername());
				users.add(webSocketUser);
			}
			servletContext.setAttribute("webSocketUsers",users);

		}catch(Exception exception)
		{
			System.out.println("initializer exception "+exception);
		}
	}
}