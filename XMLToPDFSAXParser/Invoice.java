package XMLToPDFSAXParser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Invoice extends DefaultHandler{
	

	public int invno;
	public String invdate;
	public String cutomername;
	public List<Items> item;
	public int gst;
	public int net;
	
	public String string;
	public final int getInvno() {
		return invno;
	}
	public final void setInvno(int invno) {
		this.invno = invno;
	}
	public final String getInvdate() {
		return invdate;
	}
	public final void setInvdate(String invdate) {
		this.invdate = invdate;
	}
	public final String getCutomername() {
		return cutomername;
	}
	public final void setCutomername(String cutomername) {
		this.cutomername = cutomername;
	}
	public final List<Items> getItem() {
		return item;
	}
	public final void setItem(List<Items> item) {
		this.item = item;
	}
	public final int getGst() {
		return gst;
	}
	public final void setGst(int gst) {
		this.gst = gst;
	}
	public final int getNet() {
		return net;
	}
	public final void setNet(int net) {
		this.net = net;
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			
			if(qName.equalsIgnoreCase("items"))
			{
				this.item=new ArrayList<Items>();
			}
			if(qName.equalsIgnoreCase("item"))
			{
				 this.item.add(new Items());
			 }
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equals("invno"))
		{
			this.setInvno(Integer.parseInt(string));
		}
		if(qName.equals("invdate"))
		{
			this.setInvdate(string);
		}
		if(qName.equals("customername"))
		{
			this.setCutomername(string);
		}
		if(qName.equalsIgnoreCase("itemno"))
		{
			getLatestItem().setItemno(Integer.parseInt(string));
		}	
		if(qName.equalsIgnoreCase("itemname"))
		{
			getLatestItem().setItemname(string);
		}
		if(qName.equalsIgnoreCase("itemprice"))
		{
			getLatestItem().setItemprice(Float.parseFloat(string));
		}
		if(qName.equalsIgnoreCase("itemqty"))
		{
			getLatestItem().setItemquantity(Integer.parseInt(string));
		}
		if(qName.equals("gst"))
		{
			this.setGst(Integer.parseInt(string));
		}
		if(qName.equals("net"))
		{
			this.setNet(Integer.parseInt(string));
		}
		
		 
	}
	private Items getLatestItem() 
	{
		 List<Items> list = this.item;
		 int latest = list.size()-1;
		 return list.get(latest);
	 }
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		string=new String(ch,start,length);
	}
	public static Invoice getInvoice() throws Exception
	{
		SAXParserFactory spf=SAXParserFactory.newInstance();
		SAXParser sp=spf.newSAXParser();
		Invoice Invoice;
		sp.parse("invoice.xml", Invoice=new Invoice());
		return Invoice;
	}
	
}
class Items 
{
	public int itemno;
	public String itemname;
	public float itemprice;
	public int itemquantity;
	public int getItemno() {
		return itemno;
	}
	public void setItemno(int itemno) {
		this.itemno = itemno;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public float getItemprice() {
		return itemprice;
	}
	public void setItemprice(float itemprice) {
		this.itemprice = itemprice;
	}
	public int getItemquantity() {
		return itemquantity;
	}
	public void setItemquantity(int itemquantity) {
		this.itemquantity = itemquantity;
	}
	@Override
	public String toString() {
		return itemno+" "+itemname+" "+itemprice+" "+itemquantity;
	}
	
}

