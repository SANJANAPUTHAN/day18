package SendMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
public class SendEmailTLS {
	public static void main(String[] args) throws Exception {

        final String username = "sanjanaps007@gmail.com";
        final String password = "oqsncftmcudiwupu";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the recipient mail id");
            String to=sc.next();
            message.setFrom(new InternetAddress("sanjanaps007@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)
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

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
