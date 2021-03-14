package SendMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
public class SendEmail {
	public static void main(String[] args) throws Exception {

        Object object=new SendEmail();
        object=Proxy.newProxyInstance(SendEmail.class.getClassLoader(), new Class[] {MailServices.class}, new MyInvocationHandler(new Object[] {new XMLToMail()}));
        MailServices mail=(MailServices)object;
        mail.sendMail();
	
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
interface MailServices
{
	public void sendMail() throws Exception;
}
class XMLToMail implements MailServices
{
	@Override
	public void sendMail() throws Exception {
		final String username = "sanjanaps007@gmail.com";
        final String password = "oqsncftmcudiwupu";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); 
        
        Session session = Session.getInstance(prop,new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            Scanner sc=new Scanner(System.in);
            System.out.println("enter mail id recipient");
            String s=sc.next();
            message.setFrom(new InternetAddress("sanjanaps007@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(s)
            );
            Invoice invoice=Invoice.getInvoice();
            int invoicenumber=invoice.getInvno();
            String invoicedate=invoice.getInvdate();
            String cutomername=invoice.getCutomername();
            
            message.setSubject("Bill details");
            message.setHeader("Dear,"+cutomername+ "\n\n The bill has been generated for your invoice number"+" "+invoicenumber+" dated "+invoicedate, cutomername);
            List<Items> list=invoice.getItem();
            for(int i=0;i<list.size();i++)
            {
            	String text=list.toString();
            	message.setText("\n"+text);
            }
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}