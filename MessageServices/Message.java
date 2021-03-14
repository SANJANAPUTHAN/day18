package MessageServices;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Message {
	public static void main(String[] args) {
			Object message=new Message();
			message=Proxy.newProxyInstance(Message.class.getClassLoader(), new Class[] {messageservice.class}, new MyInvocationHandler(new Object[] {new XMLToMsg()}));
			messageservice msg=(messageservice)message;
			msg.sendMessage();

		}
    
}
class MyInvocationHandler implements InvocationHandler
{
	Object ob[];
	public MyInvocationHandler(Object ob[]) {
		this.ob=ob;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		Object role=null;
		for(Object o:ob)
		{
			Method m[]=o.getClass().getDeclaredMethods();
			for(Method mm:m)
			{
				mm.setAccessible(true);
				role=mm.invoke(o, args);
			}
		}
		return role;
	}
}
interface messageservice
{
	public void sendMessage();
}
class XMLToMsg implements messageservice
{
	@Override
	public void sendMessage() {
		try
		{
					String recipient = "+919943111574";
					String message = " Greetings from DENNIS departments!";
					Invoice invoice=Invoice.getInvoice();
					message = message+" "+invoice.getCutomername()+" of bill id "+invoice.getInvno()+" dated "+invoice.getInvdate();
					String username = "admin";
					String password = "abc123";
					String originator = "+917904518140";
			
					String requestUrl  = "http://127.0.0.1:9501/api?action=sendmessage&" +
					 "username=" + URLEncoder.encode(username, "UTF-8") +
					 "&password=" + URLEncoder.encode(password, "UTF-8") +
					 "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
					 "&messagetype=SMS:TEXT" +
					 "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
					 "&originator=" + URLEncoder.encode(originator, "UTF-8") +
					 "&serviceprovider=GSMModem1" +
					 "&responseformat=html";
			
			
			
					URL url = new URL(requestUrl);
					HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			
					System.out.println(uc.getResponseMessage());
			
					uc.disconnect();

		} 
		catch(Exception ex)
		{
					System.out.println(ex.getMessage());
		
		}
		
	}
}
