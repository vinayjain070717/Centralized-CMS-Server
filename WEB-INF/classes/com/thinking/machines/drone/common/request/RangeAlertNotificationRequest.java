package com.thinking.machines.drone.common.request;
public class RangeAlertNotificationRequest
{
	private double communicationRangeLeft;
	public RangeAlertNotificationRequest()
	{
		this.communicationRangeLeft=0.0;
	}
	public void setCommunicationRangeLeft(double range)
	{
		this.communicationRangeLeft=range;
	}
	public double getCommunicationRangeLeft()
	{
		return this.communicationRangeLeft;
	}
}