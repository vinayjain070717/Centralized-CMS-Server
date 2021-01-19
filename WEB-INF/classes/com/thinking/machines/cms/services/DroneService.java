package com.thinking.machines.cms.services;
import com.thinking.machines.cms.services.model.*;
import com.thinking.machines.cms.services.pojo.*;
import java.util.*;
import com.thinking.machines.cms.dl.exceptions.*;
import com.thinking.machines.cms.dl.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import java.sql.*;
import com.google.gson.*;

@RestController
public class DroneService {
    @Autowired
    private CMSModel cmsModel;

    @GetMapping("cms/getDrones")
    public List<Drone> getAll() {
        return cmsModel.getDrones();
    }

    @PostMapping("/cms/addDrone")
    public ServiceResponse add(@RequestBody Drone drone) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = drone.getDroneKey();
        p.activity = "Add Drone";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jo = (JsonObject) jsonParser.parse(gson.toJson(drone));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (drone == null || drone.getModelName() == null || drone.getModelNumber() == null || drone.getBrand() == null
                || drone.getDroneKey() == null || drone.getFlightTime() == null || drone.getLoadCapacity() == null
                || drone.getSpeed() == null || drone.getAltitude() == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Drone required";
            p.user = drone.getDroneKey();
            p.activity = "Add Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        if (cmsModel.hasDrone(drone)) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Drone exists";
            p.user = drone.getDroneKey();
            p.activity = "Add Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            com.thinking.machines.cms.dl.pojo.Drone dlDrone;
            dlDrone = new com.thinking.machines.cms.dl.pojo.Drone();
            dlDrone.setModelName(drone.getModelName());
            dlDrone.setModelNumber(drone.getModelNumber());
            dlDrone.setBrand(drone.getBrand());
            dlDrone.setDroneKey(drone.getDroneKey());
            dlDrone.setFlightTime(drone.getFlightTime());
            dlDrone.setLoadCapacity(drone.getLoadCapacity());
            dlDrone.setSpeed(drone.getSpeed());
            dlDrone.setAltitude(drone.getAltitude());
            DroneDAO droneDAO = new DroneDAO();
            droneDAO.add(dlDrone);
            drone.setId(droneDAO.getResult());
            System.out.println(droneDAO.getResult());
            cmsModel.addDrone(drone);
            serviceResponse.hasResult = true;
            serviceResponse.result = drone;
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
        } finally{
            p.user = drone.getDroneKey();
            p.activity = "Add Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }

    @PostMapping("/cms/updateDrone")
    public ServiceResponse update(@RequestBody Drone drone) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        p.user = drone.getDroneKey();
        p.activity = "Update Drone";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jo = (JsonObject) jsonParser.parse(gson.toJson(drone));
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (drone == null || drone.getModelName() == null || drone.getModelNumber() == null || drone.getBrand() == null
                || drone.getDroneKey() == null || drone.getFlightTime() == null || drone.getLoadCapacity() == null
                || drone.getSpeed() == null || drone.getAltitude() == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Drone required";
            p.user = drone.getDroneKey();
            p.activity = "Update Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        if (drone.getDroneKey().length() == 0 || drone.getId() <= 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Invalid drone";
            p.user = drone.getDroneKey();
            p.activity = "Update Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Drone d = cmsModel.getDroneById(drone.getId());
        if (d == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Drone not exists";
            p.user = drone.getDroneKey();
            p.activity = "Update Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Drone dd = cmsModel.getDroneByDroneKey(drone.getDroneKey());
        System.out.println(dd);
        if (dd != null && (dd.getId() != d.getId())) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Drone Key already exists enter a unique key";
            p.user = drone.getDroneKey();
            p.activity = "Update Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            com.thinking.machines.cms.dl.pojo.Drone dlDrone;
            dlDrone = new com.thinking.machines.cms.dl.pojo.Drone();
            DroneDAO droneDAO = new DroneDAO();
            cmsModel.removeDrone(drone.getId());
            dlDrone.setId(drone.getId());
            dlDrone.setModelName(drone.getModelName());
            dlDrone.setModelNumber(drone.getModelNumber());
            dlDrone.setBrand(drone.getBrand());
            dlDrone.setDroneKey(drone.getDroneKey());
            dlDrone.setFlightTime(drone.getFlightTime());
            dlDrone.setLoadCapacity(drone.getLoadCapacity());
            dlDrone.setSpeed(drone.getSpeed());
            dlDrone.setAltitude(drone.getAltitude());
            droneDAO.update(dlDrone);
            cmsModel.addDrone(drone);
            serviceResponse.hasResult = true;
            serviceResponse.result = drone;
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
        } finally{
            p.user = drone.getDroneKey();
            p.activity = "Update Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }

    @PostMapping("/cms/deleteDrone")
    public ServiceResponse delete(@RequestParam(name = "droneId") Integer id) {
        LoggingService l = new LoggingService();
        Gson gson = new Gson();
        Payload p = new Payload();
        String key=cmsModel.getDroneById(id).getDroneKey();
        p.user = cmsModel.getDroneById(id).getDroneKey();
        p.activity = "Delete Drone";
        p.nature = "request";
        JsonObject jo = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        jo.addProperty("droneId",id);
        p.argument = jo;
        System.out.println(gson.toJson(p));
        l.forwardRequestToLoggingServer(gson.toJson(p));
        ServiceResponse serviceResponse = new ServiceResponse();
        if (id <= 0) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = "Invalid Drone Key";
            p.user = cmsModel.getDroneById(id).getDroneKey();
            p.activity = "Delete Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        Drone d = cmsModel.getDroneById(id);
        if (d == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.error = "Drone does not exists";
            p.user = cmsModel.getDroneById(id).getDroneKey();
            p.activity = "Delete Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
        try {
            DroneDAO droneDAO = new DroneDAO();
            droneDAO.delete(id);
            cmsModel.removeDrone(id);
            serviceResponse.hasResult = true;
            serviceResponse.result = d;
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
        } finally{
            p.user = key;
            p.activity = "Delete Drone";
            p.nature = "response";
            jsonParser = new JsonParser();
            jo = (JsonObject) jsonParser.parse(gson.toJson(serviceResponse));
            p.argument = jo;
            System.out.println(gson.toJson(p));
            l.forwardRequestToLoggingServer(gson.toJson(p));
            return serviceResponse;
        }
    }
}