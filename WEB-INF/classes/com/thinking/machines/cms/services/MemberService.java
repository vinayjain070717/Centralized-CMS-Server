//check for mobile number when adding a member
//make a method named isUsernameExists

package com.thinking.machines.cms.services;
import com.thinking.machines.cms.services.model.*;
import com.thinking.machines.cms.services.pojo.*;
import java.util.*;
import com.thinking.machines.cms.dl.exceptions.*;
import com.thinking.machines.cms.dl.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.thinking.machines.cms.services.utility.*;
import javax.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;

@PropertySource("classpath:application.properties")
@RestController
public class MemberService
{
    @Autowired
    private CMSModel cmsModel;
    @Autowired
    private ServletContext servletContext;
    @Value("${smsService.apiKey.value}")
	private String apiKey;
	@Value("${smsService.secretKey.value}")
	private String secretKey;
    @GetMapping("cms/getMembers")
	public List<Member> getAll()
    {
        return cmsModel.getMembers();
    }
    
    @PostMapping("/cms/addMember")
    public ServiceResponse add(@RequestBody Member member) {
        ServiceResponse serviceResponse = new ServiceResponse();
        if (member.getUsername() == null || member.getFirstName() == null || member.getLastName() == null
                || member.getMobileNumber() == null || member.getPwd() == null || member.getPwdKey() == null
                || member == null) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Member Required";
            return serviceResponse;
        }
        if (cmsModel.hasMember(member)) {
            serviceResponse.success = false;
            serviceResponse.isException = true;
            serviceResponse.exception = "Member exists";
            return serviceResponse;
        }
        try {
            com.thinking.machines.cms.dl.pojo.Member dlMember;
            dlMember=new com.thinking.machines.cms.dl.pojo.Member();
            dlMember.setUsername(member.getUsername());
            dlMember.setFirstName(member.getFirstName());
            dlMember.setLastName(member.getLastName());
            dlMember.setMobileNumber(member.getMobileNumber());
            dlMember.setPwd(member.getPwd());
            dlMember.setPwdKey(member.getPwdKey());
            MemberDAO memberDAO=new MemberDAO();
            memberDAO.add(dlMember);
            cmsModel.addMember(member);
            serviceResponse.hasResult=true;
            serviceResponse.result=member;
        } catch (DAOException daoException) {
            serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.error=daoException.getMessage();
        } catch (Throwable throwable) {
            serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.error=throwable.getMessage();
        } finally {
            return serviceResponse;
        }
    }
    @PostMapping("/cms/getMemberByUsername")
    public ServiceResponse getMemberByUsername(@RequestParam(name="username") String username)
    {
        ServiceResponse serviceResponse=new ServiceResponse();
        if(username.length()==0)
        {
            serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.exception="Invalid Username";
            return serviceResponse;
        }
        Member member=cmsModel.getMemberByUsername(username);
        if(member==null)
        {
            serviceResponse.success=false;
            serviceResponse.isException=true;
            serviceResponse.exception="Member does not exists";
            return serviceResponse;
        }
        serviceResponse.result=member;
        serviceResponse.hasResult=true;
        return serviceResponse;
    }

    @PostMapping("cms/sendSMS")
    public void sendSMS(@RequestParam(name="username") String username,@RequestParam(name="mobileNumber") String mobileNumber)
    {
    	try
    	{
    	System.out.println("send sms service invoked");
	    Random rnd = new Random();
	    int n = 1000 + rnd.nextInt(9000);
	    servletContext.setAttribute(username,String.valueOf(n));
	    System.out.println("api key "+apiKey);
	    System.out.println("secret key"+secretKey);
	    String response=SMSUtility.sendCampaign(apiKey,secretKey,"stage",mobileNumber,String.valueOf(n),"senderId");
	    // stage word for testing and prod for production
	    System.out.println("response "+response);
	    // return response;
		}catch(Exception exception)
		{
			System.out.println("send sms service exception "+exception);
		}
    }
    @PostMapping("cms/verifyOtp")
    public ServiceResponse verifyOtp(@RequestParam(name="username") String username,@RequestParam(name="otpValue") String otpValue)
    {
		ServiceResponse serviceResponse=new ServiceResponse();
    	try
		{
		System.out.println("otp verification service invoked");
		String realOtpValue=(String)servletContext.getAttribute(username);
		if(realOtpValue.equalsIgnoreCase(otpValue))
		{
			serviceResponse.success=true;
		}
		else
		{
			serviceResponse.success=false;
		}
		}catch(Exception exception)
		{
			System.out.println("otp verification service exception "+exception);
			serviceResponse.success=false;
		}
		finally
		{
			return serviceResponse;
		}

    }
}