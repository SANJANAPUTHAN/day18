package XMLToPDFSAXParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ListIterator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Utilities 
{
	public static void main(String[] args) throws Exception {
//		Object obj=new Utilities();
//		obj=Proxy.newProxyInstance(Utilities.class.getClassLoader(),new Class[] {PDFConverter.class} , new MyInvocationHandler(new Object[] {new XMLToPdfConverter()}));
//		PDFConverter pdf=(PDFConverter)obj;
//		pdf.convert();
		
		new XMLToPdfConverter().convert();
	}
}
class MyInvocationHandler implements InvocationHandler
{
	Object ob[];
	public MyInvocationHandler(Object ob[]) {
		this.ob=ob;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
interface PDFConverter
{
	public void convert() throws Exception;
	public void insertCell(PdfPTable table, String text, int align, int colspan, Font font);
}
class XMLToPdfConverter implements PDFConverter
{
	@Override
	public void convert() throws Exception 
	{
		String FILE = "FirstPdf.pdf";
		Document document=new Document();
		PdfWriter.getInstance(document, new FileOutputStream(new File(FILE)));
		Invoice invoice=Invoice.getInvoice();
	    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
	    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	    document.open();
	    Paragraph preface=new Paragraph();
	    preface.add(new Paragraph("CUSTOMER DETAILS",catFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph("INVNO  :  "+invoice.getInvno(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph("INVDATE  :  "+invoice.getInvdate(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph("CUSTOMERNAME  :  "+invoice.getCutomername(),redFont));
	    document.add(preface);
	    preface.clear();
	    preface.add(new Paragraph(" "));
	    document.add(preface);
	    float[] columnWidths = {1.5f, 5f, 2f, 2f};
		PdfPTable table = new PdfPTable(columnWidths);
		table.setWidthPercentage(90f);		
		insertCell(table, "Item No", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "Item Name", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "Price", Element.ALIGN_LEFT, 1, subFont);
		insertCell(table, "Qty", Element.ALIGN_LEFT, 1, subFont);
		table.setHeaderRows(1);
		List<Items> list= invoice.getItem();
		for(int i=0; i<list.size(); i++) {
			insertCell(table, String.valueOf(list.get(i).getItemno()), Element.ALIGN_RIGHT, 1,smallBold);
		    insertCell(table, list.get(i).getItemname(), Element.ALIGN_LEFT, 1, smallBold);
		    insertCell(table, String.valueOf(list.get(i).getItemprice()), Element.ALIGN_LEFT, 1, smallBold);
		    insertCell(table, String.valueOf(list.get(i).getItemquantity()), Element.ALIGN_LEFT, 1, smallBold);
		}
		document.add(table);
		preface.add(new Paragraph("GST"+" "+invoice.getGst(),redFont));
	    document.add(preface);
	    preface.clear();
	    document.add(preface);
	    preface.add(new Paragraph("NET"+" "+invoice.getNet(),redFont));
	    document.add(preface);
	    preface.clear();
	    document.close();
	    
	    
	}
	public void insertCell(PdfPTable table, String text, int align, int colspan, Font font)
	{
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  cell.setHorizontalAlignment(align);
		  cell.setColspan(colspan);
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  table.addCell(cell);		  
	}
}