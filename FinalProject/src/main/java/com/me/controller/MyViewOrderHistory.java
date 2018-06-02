package com.me.controller;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.me.dao.CartDAO;
import com.me.pojo.Order;

public class MyViewOrderHistory extends AbstractPdfView{

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfwriter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Font  helvetica_18_blue = new Font(Font.HELVETICA, 18, Font.BOLDITALIC, Color.BLUE);
		Paragraph title = new Paragraph("Order Details", helvetica_18_blue);

		Phrase firstPhrase = new Phrase("Details of the Order");
		
		@SuppressWarnings("unchecked")
		List<Order> list = (List<Order>)model.get("orderlist");
		document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(5);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[]{1, 1, 1, 1, 1});
		PdfPCell cell;

		table.addCell(new Paragraph("Product Name",FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD)));
		table.addCell(new Paragraph("Quantity",FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD)));
		table.addCell(new Paragraph("Product Price",FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD)));
		table.addCell(new Paragraph("Seller Name",FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD)));
		table.addCell(new Paragraph("Total Price",FontFactory.getFont(FontFactory.TIMES, 15,Font.BOLD)));

		double totalprice = 0;
		for(Order order:list)
		{
			table.addCell(new Paragraph(order.getProduct().getName(),FontFactory.getFont(FontFactory.TIMES, 11,Font.BOLD)));
			table.addCell(String.valueOf(order.getQuantity()));
			table.addCell(String.valueOf(order.getProduct().getPrice()));
			table.addCell(String.valueOf((order.getProduct().getSeller().getName())));
			table.addCell(String.valueOf((order.getQuantity()) * Double.parseDouble((order.getProduct().getPrice()))));
			totalprice = totalprice + (order.getQuantity()) * Double.parseDouble((order.getProduct().getPrice()));
		}

		document.add(table);

		Paragraph totalPrice = new Paragraph("Total Order Cost"+totalprice,FontFactory.getFont(FontFactory.TIMES, 10,Font.UNDERLINE));
		document.add(totalPrice);
	}
}