package com.thinking.machines.drone.common.request;
public class TechnicalProblemRequest
{
    private String problem;
    public TechnicalProblemRequest()
    {
        this.problem="";
    }
    public void setProblem(String problem)
    {
        this.problem=problem;
    }
    public String getProblem()
    {
        return this.problem;
    }
}