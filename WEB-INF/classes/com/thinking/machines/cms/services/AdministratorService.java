package com.thinking.machines.cms.services;
import com.thinking.machines.cms.services.model.*;
import com.thinking.machines.cms.services.pojo.*;
import java.util.*;
import com.thinking.machines.cms.dl.exceptions.*;
import com.thinking.machines.cms.dl.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import javax.servlet.http.*;
import java.sql.*;
import com.google.gson.*;
import javax.servlet.*;
@RestController
public class AdministratorService
{
    @Autowired
    private CMSModel cmsModel;
    @Autowired
    private ServletContext servletContext;
    @GetMapping("/cms/getAdministrators")
    public List<Administrator> getAll(HttpSession session) {
        return cmsModel.getAdministrators();
    }

    @PostMapping(path = "/cms/administratorLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse login(@RequestParam(name = "username") String username,
            @RequestParam(name = "pwd") String pwd, HttpSession session) {
        ServiceResponse serviceResponse = new ServiceResponse();
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = new JsonObject();
 
        if(servletContext.getAttribute("admin")!=null)
        {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Already Logged in";
            //logs start
            p.user = username;
            p.activity = "Login";
            p.nature = "response";
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            //logs ends
            return serviceResponse;            
        }
        p.user = username;
        p.activity = "Login";
        p.nature = "request";
        jo.addProperty("username", username);
        jo.addProperty("password", pwd);
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        if (username.length() == 0 || pwd.length() == 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Username/Password required";
            p.user = username;
            p.activity = "Login";
            p.nature = "response";
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Administrator administrator = cmsModel.getAdministratorByUsername(username);
        if (administrator == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Invalid username";
            p.user = username;
            p.activity = "Login";
            p.nature = "response";
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        } else if (!(administrator.getPwd().equals(pwd))) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Invalid password";
            p.user = username;
            p.activity = "Login";
            p.nature = "response";
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        session.setAttribute("admin", username);
        servletContext.setAttribute("admin",username);
        p.user = username;
        p.activity = "Login";
        p.nature = "response";
        jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        return serviceResponse;
    }

    @PostMapping("/cms/addAdministrator")
    public ServiceResponse add(@RequestBody Administrator administrator, HttpSession session) {
        System.out.println("add service invoked");
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = (String) session.getAttribute("admin");
        p.activity = "Add Administrator";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jo = (JsonObject) jsonParser.parse(gson.toJson(administrator));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        System.out.println(administrator);
        System.out.println(administrator.getUsername());
        if (administrator == null || administrator.getUsername() == null || administrator.getFirstName() == null
                || administrator.getLastName() == null || administrator.getPwd() == null
                || administrator.getPwdKey() == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Administrator required";
            p.user = (String) session.getAttribute("admin");
            p.activity = "Add Administrator";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        if (cmsModel.hasAdministrator(administrator)) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Administrator exists";
            p.user = (String) session.getAttribute("admin");
            p.activity = "Add Administrator";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            com.thinking.machines.cms.dl.pojo.Administrator dlAdministrator;
            dlAdministrator = new com.thinking.machines.cms.dl.pojo.Administrator();
            dlAdministrator.setUsername(administrator.getUsername());
            dlAdministrator.setFirstName(administrator.getFirstName());
            dlAdministrator.setLastName(administrator.getLastName());
            dlAdministrator.setPwd(administrator.getPwd());
            dlAdministrator.setPwdKey(administrator.getPwdKey());
            AdministratorDAO administratorDAO = new AdministratorDAO();
            administratorDAO.add(dlAdministrator);
            cmsModel.addAdministrator(administrator);
            serviceResponse.hasResult = true;
            serviceResponse.result = administrator;
            // return serviceResponse;
        } catch (DAOException daoException) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = daoException.getMessage();
            // return serviceResponse;
        } catch (Throwable throwable) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = throwable.getMessage();
            // return serviceResponse;
        } finally {
            p.user = (String) session.getAttribute("admin");
            p.activity = "Add Administrators";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }

    @PostMapping("/cms/updateAdministrator")
    public ServiceResponse updateUsername(@RequestBody Administrator administrator, HttpSession session) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = (String) session.getAttribute("admin");
        p.activity = "Update Administrator";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jo = (JsonObject) jsonParser.parse(gson.toJson(administrator));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (administrator == null || administrator.getUsername() == null || administrator.getFirstName() == null
                || administrator.getLastName() == null || administrator.getPwd() == null
                || administrator.getPwdKey() == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Administrator required";
            p.user = (String) session.getAttribute("admin");
            p.activity = "Update Administrators";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        if (administrator.getUsername().length() == 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Administrator username is invalid";
            p.user = (String) session.getAttribute("admin");
            p.activity = "Update Administrators";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Administrator oldAdministrator = cmsModel.getAdministratorByUsername(session.getAttribute("admin").toString());
        if (oldAdministrator == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Administrator with does not exists";
            p.user = (String) session.getAttribute("admin");
            p.activity = "Update Administrators";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            com.thinking.machines.cms.dl.pojo.Administrator dlAdministrator;
            dlAdministrator = new com.thinking.machines.cms.dl.pojo.Administrator();
            AdministratorDAO administratorDAO = new AdministratorDAO();
            administratorDAO.delete(oldAdministrator.getUsername());
            cmsModel.removeAdministrator(oldAdministrator.getUsername());
            dlAdministrator.setUsername(administrator.getUsername());
            dlAdministrator.setFirstName(administrator.getFirstName());
            dlAdministrator.setLastName(administrator.getLastName());
            dlAdministrator.setPwd(administrator.getPwd());
            dlAdministrator.setPwdKey(administrator.getPwdKey());
            administratorDAO.add(dlAdministrator);
            cmsModel.addAdministrator(administrator);
            serviceResponse.hasResult = true;
            serviceResponse.result = administrator;
            session.removeAttribute("admin");
            // req.setAttribute("update","Administrator updated");
            session.setAttribute("admin", administrator.getUsername());
            // return serviceResponse;
        } catch (DAOException daoException) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = daoException.getMessage();
            // return serviceResponse;
        } catch (Throwable throwable) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = throwable.getMessage();
            // return serviceResponse;
        } finally {
            p.user = (String) session.getAttribute("admin");
            p.activity = "Update Administrators";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }

    @PostMapping("/cms/deleteAdministrator")
    public ServiceResponse delete(@RequestParam(name = "username") String username) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = username;
        p.activity = "Delete Administrator";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        jo.addProperty("username", username);
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (username.length() == 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Invalid username";
            p.user = username;
            p.activity = "Delete Administrator";
            p.nature = "response";
            JsonParser jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Administrator administrator = cmsModel.getAdministratorByUsername(username);
        if (administrator == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "User does not exists";
            p.user = username;
            p.activity = "Delete Administrator";
            p.nature = "response";
            JsonParser jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            AdministratorDAO administratorDAO = new AdministratorDAO();
            administratorDAO.delete(username);
            cmsModel.removeAdministrator(username);
            serviceResponse.hasResult = true;
            serviceResponse.result = administrator;
            // return serviceResponse;
        } catch (DAOException daoException) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = daoException.getMessage();
            // return serviceResponse;
        } catch (Throwable throwable) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = throwable.getMessage();
            // return serviceResponse;
        } finally {
            p.user = username;
            p.activity = "Delete Administrator";
            p.nature = "response";
            JsonParser jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }

    @PostMapping("/cms/getAdministratorByUsername")
    public ServiceResponse getAdministratorByUsername(@RequestParam(name = "username") String username) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = username;
        p.activity = "Get Administrators";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        jo.addProperty("username", username);
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (username.length() == 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Invalid username";
            p.user = username;
            p.activity = "Get Administrators";
            p.nature = "response";
            JsonParser jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Administrator administrator = cmsModel.getAdministratorByUsername(username);
        if (administrator == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "User does not exists";
            p.user = username;
            p.activity = "Get Administrators";
            p.nature = "response";
            JsonParser jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        serviceResponse.result = administrator;
        serviceResponse.hasResult = true;
        p.user = username;
        p.activity = "Get Administrators";
        p.nature = "response";
        JsonParser jsonParser = new JsonParser();
        jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        return serviceResponse;
    }
    @GetMapping("/cms/logout")
    public ServiceResponse logoutService(HttpSession session)
    {
        ServiceResponse serviceResponse=new ServiceResponse();
        JsonParser jsonParser = new JsonParser();
        Payload p=new Payload();
        LoggingService l=new LoggingService();
        Gson gson=new Gson();
        try
        {
            p.user = (String)session.getAttribute("admin");
            p.activity = "Logout";
            p.nature = "request";
            p.argument=null;
            l.forwardRequestToLoggingServer(gson.toJson(p));

            session.removeAttribute("admin");
            servletContext.removeAttribute("admin");
            session.invalidate();
 
            serviceResponse.success = true;
            serviceResponse.isException = false;
            serviceResponse.exception = "";
            
            JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            // System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }catch(Exception exception)
        {
            System.out.println("Logout service exception "+exception);
            serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.exception=exception.getMessage();
            JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(serviceResponse));
            p.user="";
            p.activity="logout";
            p.nature="response";
            p.argument=jo;
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }
}