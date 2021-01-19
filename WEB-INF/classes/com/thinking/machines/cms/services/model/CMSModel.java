package com.thinking.machines.cms.services.model;
import java.util.*;
public class CMSModel implements java.io.Serializable
{
    private List<Administrator> administrators=Collections.synchronizedList(new LinkedList<>());
    private List<Drone> drones=Collections.synchronizedList(new LinkedList<>());
    private List<Member> members=Collections.synchronizedList(new LinkedList<>());
    public CMSModel()
    {
    }
    public void setAdministrators(List<Administrator> administrators)
    {
        this.administrators=administrators;
    }
    public void addAdministrator(Administrator administrator)
    {
        if(hasAdministrator(administrator)) return;
        this.administrators.add(administrator);
    }
    public boolean hasAdministrator(Administrator administrator)
        {
        for(Administrator a: administrators)
        {
            if(a.getUsername().trim().equalsIgnoreCase(administrator.getUsername().trim())) return true;
        }
        return false;
    }
    public Administrator getAdministratorByUsername(String username)
    {
        for(Administrator a:administrators)
        {
            if(a.getUsername().equalsIgnoreCase(username)) return a;
        }
        return null;
    }
    public void updateAdministrator(Administrator administrator)
    {
        for(Administrator a: administrators)
        {
            if(a.getUsername().equalsIgnoreCase(administrator.getUsername()))
            {
                System.out.println("update administrator invoked");
                a.setUsername(administrator.getUsername());
                a.setFirstName(administrator.getFirstName());
                a.setLastName(administrator.getLastName());
                a.setPwd(administrator.getPwd());
                a.setPwdKey(administrator.getPwdKey());
                return;
            }
        }
    }
    public void removeAdministrator(String username)
    {
        int i=0;
        System.out.println("removeAdministrator administrator invoked");
        for(Administrator a:administrators)
        {
            System.out.println(a.getUsername().equalsIgnoreCase(username));
            if(a.getUsername().equalsIgnoreCase(username))
            {
                administrators.remove(i);
                return;
            }
            i++;
        }
    }
    public List<Administrator> getAdministrators()
    {
        return this.administrators;
    }
    public void setDrones(List<Drone> drones)
    {
        this.drones=drones;
    }
    public void addDrone(Drone drone)
    {
        if(hasDrone(drone)) return;
        this.drones.add(drone);
    }
    public boolean hasDrone(Drone drone)
    {
        for(Drone d: drones)
        {
            if(d.getDroneKey()==drone.getDroneKey()) return true;
        }
        return false;
    }
    public Drone getDroneById(Integer id)
    {
        for(Drone d:drones)
        {
            if(d.getId()==id) return d;
        }
        return null;
    }
    public Drone getDroneByDroneKey(String droneKey)
    {
        for(Drone d:drones)
        {
            if(d.getDroneKey().equalsIgnoreCase(droneKey)) return d;
        }
        return null;
    }   
    public void updateDrone(Drone drone)
    {
        for(Drone d: drones)
        {
            if(d.getDroneKey().equalsIgnoreCase(drone.getDroneKey()))
            {
                d.setModelName(drone.getModelName());
                d.setModelNumber(drone.getModelNumber());
                d.setBrand(drone.getBrand());
                d.setDroneKey(drone.getDroneKey());
                d.setFlightTime(drone.getFlightTime());
                d.setLoadCapacity(drone.getLoadCapacity());
                d.setSpeed(drone.getSpeed());
                d.setAltitude(drone.getAltitude());
                return;
            }
        }
    }
    public void removeDrone(Integer id)
    {
        int i=0;
        for(Drone d: drones)
        {
            if(d.getId()==id)
            {
                drones.remove(i);
                return;
            }
            i++;
        }
    }
    public List<Drone> getDrones()
    {
        return this.drones;
    }
    public void setMembers(List<Member> members)
    {
        this.members=members;
    }
    public void addMember(Member member)
    {
        if(hasMember(member)) return;
        this.members.add(member);
    }
    public boolean hasMember(Member member)
    {
        for(Member m:members)
        {
            if(m.getUsername().trim().equalsIgnoreCase(member.getUsername().trim())) return true;
        }
        return false;
    }
    public Member getMemberByUsername(String username)
    {
        for(Member m:members)
        {
            if(m.getUsername().equalsIgnoreCase(username)) return m;
        }
        return null;
    }   
    public void updateMember(Member member)
    {
        for(Member m:members)
        {
            if(m.getUsername().equalsIgnoreCase(member.getUsername()))
            {
                m.setUsername(member.getUsername());
                m.setFirstName(member.getFirstName());
                m.setLastName(member.getLastName());
                m.setMobileNumber(member.getMobileNumber());
                m.setPwd(member.getPwd());
                m.setPwdKey(member.getPwdKey());
                return;
            }
        }
    }
    public void removeMember(String username)
    {
        int i=0;
        for(Member m:members)
        {
            if(m.getUsername().equalsIgnoreCase(username))
            {
                members.remove(i);
                return;
            }
            i++;
        }
    }
    public List<Member> getMembers()
    {
        return this.members;
    }
}