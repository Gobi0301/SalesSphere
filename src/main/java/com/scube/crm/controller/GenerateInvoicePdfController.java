package com.scube.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.CompanyBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.security.MySalesUser;
import com.scube.crm.service.CompanyService;
import com.scube.crm.service.SalesOrderService;
import com.scube.crm.utils.PdfGenerator;
import com.scube.crm.vo.SalesOrderVO;

@Controller
public class GenerateInvoicePdfController extends PdfGenerator{
	public static final String invoiceFileUploads = "C:\\usr\\local\\mysales\\invoice\\";
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(GenerateInvoicePdfController.class);
	@Autowired
	CompanyService companyService;
	@Autowired
	SalesOrderService salesOrderService;
	@RequestMapping(value = "generate-invoice", method = RequestMethod.GET)
	public String generateInviocePdf(HttpServletRequest request, ModelMap model, HttpServletResponse response)
			throws Exception {
		CompanyBO companyBO = new CompanyBO();
		LOGGER.entry();
		long salesOrderId = 0;
		SalesOrderBO overAllValue = null;
		List<SalesOrderBO> salesOrderlist = new ArrayList<>();
		String salesno = request.getParameter("salesNo");
		salesOrderId = Long.parseLong(salesno);
		
		SalesOrderBO salesOrderBo = new SalesOrderBO();
		try {
		if (null != salesno) {
			salesOrderBo.setSalesOrderNo(salesno);
		}
		salesOrderBo.setSalesOrderId(salesOrderId);
		MySalesUser user=null;
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		if(null!=auth.getPrincipal() && !auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")){
		user=(MySalesUser) auth.getPrincipal();
		}
		if (0 < user.getCompanyId()) { // CompanyId
			long CompanyId =user.getCompanyId();
			salesOrderBo.setCompanyId(CompanyId);
			companyBO.setCompanyId(CompanyId);
			//companyBO = salesOrderService.viewCompanyDetails(companyBO);
			companyBO = companyService.viewCompanyDetails(companyBO);
		} 
			 
		salesOrderlist = salesOrderService.getSalesOrderList(salesOrderBo);
		if (null != salesOrderlist && !salesOrderlist.isEmpty() && salesOrderlist.size() > 0) {

			SalesOrderBO sales = salesOrderlist.get(0);
			long size = salesOrderlist.size();
			overAllValue = salesOrderlist.get((int) (size - 1));
			if (null == overAllValue.getInvoiceName()) {  //Check pdf already generated
				model.addAttribute("salesOrderNo", overAllValue.getSalesOrderNo());
				model.addAttribute("overAlltotal", overAllValue.getTotalInvoice());
				model.addAttribute("grandTotal", overAllValue.getGrandTotal());
//				model.addAttribute("cgstValue", overAllValue.getGstBO().getCgstValue());
//				model.addAttribute("sgstValue", overAllValue.getGstBO().getSgstValue());
//				model.addAttribute("sgst", overAllValue.getGstBO().getSgst());
//				model.addAttribute("cgst", overAllValue.getGstBO().getCgst());
				// model.addAttribute("client", sales.getClientBO());
				model.addAttribute("accountBO", overAllValue.getAccountBO());
				model.addAttribute("date", sales.getDate());

				model.addAttribute("particularSalesList", salesOrderlist);
				model.addAttribute("companyBO", companyBO);// For we want companyName ..
				GenerateInvoicePdfController generateInvoice = new GenerateInvoicePdfController();
				generateInvoice.renderMergedOutputModel(model, request, response);// document generate method call
			
				SalesOrderBO salesOrder = new SalesOrderBO();
				if (null != request.getAttribute("fileName")) {
					System.out.println(request.getAttribute("fileName"));
					salesOrder.setInvoiceName((String) request.getAttribute("fileName"));
				}
				salesOrder.setSalesOrderId(overAllValue.getSalesOrderId());
				salesOrderService.saveInvoice(salesOrder);

			} else {
				model.addAttribute("errorMessage", "Invoice already generated!");
				model.addAttribute("salesno", salesno);
				return "redirect:/view-sales-order-list?salesno" + salesno;
			
			}
			}
			}catch (Exception ex) {
				if (logger.isDebugEnabled()) {
					logger.debug("generate-invoice has failed:" + ex.getMessage());
				}
				logger.info("generate-invoice has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}

		return null;

	}
		

	@Override
	protected void generateInvoice(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LOGGER.entry();
		AccountBO accountBo = (AccountBO) model.get("accountBO");
		CompanyBO companyBO = (CompanyBO) model.get("companyBO");
		String fileName = null;
		List<SalesOrderBO> salesOrderBo = (List<SalesOrderBO>) model.get("particularSalesList");
		SalesOrderBO sales = salesOrderBo.get(0);
		long size = salesOrderBo.size();
		SalesOrderBO overAllValue = salesOrderBo.get((int) (size - 1));

		String sgst = (String) model.get("sgst");
		String cgst = (String) model.get("cgst");
		String salesOrderNo = (String) model.get("salesOrderNo");
		long grandTotal = (long) model.get("grandTotal");
		long overAlltotal = (long) model.get("overAlltotal");
		try {

			response.setContentType("application/pdf");
			DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");

			String headerkey = "Content-Disposition";
			String headervalue = "attachment; filename=" + salesOrderNo + "_invoice.pdf";
			fileName = salesOrderNo + "_invoice.pdf"; // SONo1_invoice.pdf
			request.setAttribute("fileName", fileName);

			String downloadPath = invoiceFileUploads + fileName;// C:\\usr\\local\\mysales\\invoice\\SONo1_invoice.pdf
			response.setHeader(headerkey, headervalue);
			File file = new File(downloadPath);
			FileOutputStream fileout = new FileOutputStream(file);  //document save or upload in location
			PdfWriter.getInstance(document, fileout);  

			BaseColor colorBlock = WebColors.getRGBColor("#000");// color and font set area
			BaseColor colorWhite = WebColors.getRGBColor("#fff");
			BaseColor colorBlue = WebColors.getRGBColor("#2a3f54");

			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
			Font bodyHeaderFont = new Font(bf, 10f, Font.BOLD);
			bodyHeaderFont.setColor(BaseColor.WHITE);
			Font bodyContentFont = new Font(bf, 8f, Font.NORMAL, colorBlock);
			bodyContentFont.setColor(BaseColor.WHITE);
			Font bodyContentBoldFont = new Font(FontFamily.HELVETICA, 9f, Font.BOLD, colorBlock);
			Font bodyContentBlueBoldFont = new Font(FontFamily.HELVETICA, 10f, Font.BOLD, colorWhite);
			Font bodyContentYellowBoldFont = new Font(FontFamily.HELVETICA, 20f, Font.BOLD, colorBlock);

			Font bodyContentBoldNormal = new Font(FontFamily.HELVETICA, 9f, Font.NORMAL, colorBlock);

			Font headerFont = new Font(FontFamily.HELVETICA, 20f, Font.BOLD, colorBlock);
			Font bodyContentBoldFonts = new Font(FontFamily.HELVETICA, 12f, Font.BOLD, colorBlock);

			document.open(); // document open

			PdfPTable tables = new PdfPTable(1); // Overall table start

			tables.setWidthPercentage(100.0f);
			tables.setSpacingBefore(10);
			tables.getHeader();
			tables.setHeaderRows(2);

			PdfPTable tabTable = new PdfPTable(2); // innertable
			tabTable.setSplitLate(false);
			tabTable.setWidthPercentage(100);

			Phrase softtwig = new Phrase(companyBO.getCompanyName(), headerFont);
			PdfPCell softtwigCell = new PdfPCell(softtwig);
			softtwigCell.setPaddingBottom(5);
			softtwigCell.setPaddingTop(5);
			softtwigCell.setBorder(Rectangle.NO_BORDER);
			softtwigCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			softtwigCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase invoice = new Phrase("INVOICE", headerFont);
			PdfPCell invoiceCell = new PdfPCell(invoice);
			invoiceCell.setPaddingBottom(5);
			invoiceCell.setPaddingTop(5);
			invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			invoiceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			invoiceCell.setBorder(Rectangle.NO_BORDER);

			Phrase emptyLine = new Phrase(
					"_______________________________________________________________________________________________________",
					bodyContentBoldFont);
			PdfPCell emptyLineCell = new PdfPCell(emptyLine);
			emptyLineCell.setColspan(2);
			emptyLineCell.setPaddingBottom(10);
			emptyLineCell.setPaddingTop(5);
			emptyLineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			emptyLineCell.setBorder(Rectangle.NO_BORDER);
			emptyLineCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase billTo = new Phrase("BILL TO:", bodyContentBoldFonts);
			PdfPCell billToCell = new PdfPCell(billTo);
			billToCell.setPaddingBottom(1);
			billToCell.setPaddingTop(5);
			billToCell.setBorder(Rectangle.NO_BORDER);
			billToCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			billToCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase invoiceNo = new Phrase("INVOICE NO: " + salesOrderNo, bodyContentBoldNormal);
			PdfPCell invoiceNoCell = new PdfPCell(invoiceNo);
			invoiceNoCell.setPaddingBottom(5);
			invoiceNoCell.setPaddingTop(5);
			invoiceNoCell.setPaddingRight(64);
			invoiceNoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			invoiceNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			invoiceNoCell.setBorder(Rectangle.NO_BORDER);

			tabTable.addCell(softtwigCell);
			tabTable.addCell(invoiceCell);
			tabTable.addCell(emptyLineCell);
			tabTable.addCell(billToCell);
			tabTable.addCell(invoiceNoCell);

			if (null != accountBo.getAccountName()) {
				PdfPCell accountNameValueCell = new PdfPCell(
						new Paragraph("       " + accountBo.getAccountName(), bodyContentBoldNormal));
				accountNameValueCell.setPaddingBottom(1);
				accountNameValueCell.setPaddingTop(1);
				accountNameValueCell.setBorder(Rectangle.NO_BORDER);
				accountNameValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				accountNameValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

				tabTable.addCell(accountNameValueCell);

			}

			Phrase phrase22 = new Phrase();
			phrase22.add(new Chunk("DATE: " + new Date(), bodyContentBoldNormal));
			PdfPCell dateCell = new PdfPCell(phrase22);
			dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			dateCell.setBackgroundColor(colorWhite);
			dateCell.setBorderColor(BaseColor.WHITE);
			dateCell.setBorder(Rectangle.NO_BORDER);
			tabTable.addCell(dateCell);

			if (null != accountBo.getEmail()) {
				PdfPCell accountEmailValueCell = new PdfPCell(
						new Paragraph("       " + accountBo.getEmail(), bodyContentBoldNormal));

				accountEmailValueCell.setPaddingBottom(1);
				accountEmailValueCell.setPaddingTop(1);
				accountEmailValueCell.setBorder(Rectangle.NO_BORDER);
				accountEmailValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				accountEmailValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				accountEmailValueCell.setColspan(2);
				tabTable.addCell(accountEmailValueCell);

			}
			if (0 < accountBo.getContactNo()) {
				PdfPCell accountContactNoCell = new PdfPCell(
						new Paragraph("       " + accountBo.getContactNo(), bodyContentBoldNormal));

				accountContactNoCell.setPaddingBottom(1);
				accountContactNoCell.setPaddingTop(1);
				accountContactNoCell.setBorder(Rectangle.NO_BORDER);
				accountContactNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				accountContactNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				accountContactNoCell.setColspan(2);
				tabTable.addCell(accountContactNoCell);

			}
			if (null != accountBo.getCity() && null != accountBo.getState() && null != accountBo.getCountry()) {
				PdfPCell addressCell = new PdfPCell(new Paragraph(
						"       " + accountBo.getCity() + ", " + accountBo.getState() + ", " + accountBo.getCountry(),
						bodyContentBoldNormal));

				addressCell.setPaddingBottom(10);
				addressCell.setPaddingTop(1);
				addressCell.setBorder(Rectangle.NO_BORDER);
				addressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				addressCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				addressCell.setColspan(2);
				tabTable.addCell(addressCell);

			}

			PdfPCell tableMainCell = new PdfPCell(tabTable);
			tableMainCell.setBorder(PdfPCell.NO_BORDER);
			tableMainCell.setUseBorderPadding(true);
			tableMainCell.setPaddingLeft(2);
			tableMainCell.setPaddingLeft(0);
			tableMainCell.setPaddingTop(5);
			tableMainCell.setPaddingRight(2);
			tableMainCell.setPaddingBottom(20f);

			// document.add(tableMainCell);
			// tables.addCell(tableMainCell);

			tables.addCell(tableMainCell);

			/********************** Product Table Starts *****************/
			PdfPTable productTable = new PdfPTable(new float[] { 1f, 1.6f, 1.5f, 1.3f, 1.3f }); // table---4 starts
			productTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell sNoLabelCell = new PdfPCell(new Phrase("S.No", bodyContentBlueBoldFont));
			sNoLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sNoLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			sNoLabelCell.setBackgroundColor(colorBlue);
			sNoLabelCell.setPaddingBottom(5);
			sNoLabelCell.setPaddingTop(5);

			productTable.addCell(sNoLabelCell);

			PdfPCell productNameLabelCell = new PdfPCell(new Phrase("ProductName", bodyContentBlueBoldFont));
			productNameLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			productNameLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			productNameLabelCell.setBackgroundColor(colorBlue);
			productNameLabelCell.setPaddingBottom(5);
			productNameLabelCell.setPaddingTop(5);

			productTable.addCell(productNameLabelCell);

			PdfPCell quantityLabelCell = new PdfPCell(new Phrase("Quantity", bodyContentBlueBoldFont));
			quantityLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			quantityLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			quantityLabelCell.setBackgroundColor(colorBlue);
			quantityLabelCell.setPaddingBottom(5);
			quantityLabelCell.setPaddingTop(5);

			productTable.addCell(quantityLabelCell);

			PdfPCell unitPriceLabelCell = new PdfPCell(new Phrase("Unit Price(INR)", bodyContentBlueBoldFont));
			unitPriceLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			unitPriceLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			unitPriceLabelCell.setBackgroundColor(colorBlue);
			unitPriceLabelCell.setPaddingBottom(5);
			unitPriceLabelCell.setPaddingTop(5);

			productTable.addCell(unitPriceLabelCell);

			PdfPCell amountLabelCell = new PdfPCell(new Phrase("Amount(INR)", bodyContentBlueBoldFont));
			amountLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			amountLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			amountLabelCell.setBackgroundColor(colorBlue);
			amountLabelCell.setPaddingBottom(5);
			amountLabelCell.setPaddingTop(5);

			productTable.addCell(amountLabelCell);

			// bigtable.setHeaderRows(1);
			productTable.setSpacingAfter(0f);
			productTable.setSpacingBefore(0f);
			productTable.getRowspanHeight(5, 5);

			int sno = 1;
			for (SalesOrderBO s : salesOrderBo) {
				String sNo = String.valueOf(sno);
				sno++;
				PdfPCell sNoValueCell = new PdfPCell(new Phrase(sNo, bodyContentBoldNormal));
				sNoValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				sNoValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				sNoValueCell.setPaddingBottom(5);
				sNoValueCell.setPaddingTop(5);

				PdfPCell productNameValueCell = new PdfPCell(
						new Phrase(""/*s.getProduct().getServiceName()*/, bodyContentBoldNormal));
				productNameValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				productNameValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				productNameValueCell.setPaddingBottom(5);
				productNameValueCell.setPaddingTop(5);

				PdfPCell quantityValueCell = new PdfPCell(
						new Phrase(String.valueOf(s.getQuantity()), bodyContentBoldNormal));
				quantityValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				quantityValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				quantityValueCell.setPaddingBottom(5);
				quantityValueCell.setPaddingTop(5);

				PdfPCell unitPriceValueCell = new PdfPCell(
						new Phrase(String.valueOf(" "/* + s.getPricebookbo().getPrice() */), bodyContentBoldNormal));
				unitPriceValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				unitPriceValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				unitPriceValueCell.setPaddingBottom(5);
				unitPriceValueCell.setPaddingTop(5);

				PdfPCell amountValueCell = new PdfPCell(
						new Phrase(String.valueOf("" + s.getTotalInvoice()), bodyContentBoldNormal));
				amountValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				amountValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				amountValueCell.setPaddingBottom(5);
				amountValueCell.setPaddingTop(5);

				productTable.addCell(sNoValueCell);
				productTable.addCell(productNameValueCell);
				productTable.addCell(quantityValueCell);
				productTable.addCell(unitPriceValueCell);
				productTable.addCell(amountValueCell);
			}

			PdfPCell tableProductMainCell = new PdfPCell(productTable);
			tableProductMainCell.setBorder(PdfPCell.NO_BORDER);
			tableProductMainCell.setUseBorderPadding(true);
			tableProductMainCell.setPaddingLeft(2);
			tableProductMainCell.setPaddingLeft(0);
			tableProductMainCell.setPaddingTop(5);
			tableProductMainCell.setPaddingRight(2);
			tableProductMainCell.setPaddingBottom(20f);

			tables.addCell(tableProductMainCell);

			/******************* Total part starts ************************/
			PdfPTable tab = new PdfPTable(2);
			tab.setSplitLate(false);
			tab.setWidthPercentage(100);
			tab.setHorizontalAlignment(Element.ALIGN_LEFT);
			Phrase grandTotalPhrase = new Phrase("Grand Total(INR)", bodyContentBoldFont);
			PdfPCell grandTotalLabelCell = new PdfPCell(grandTotalPhrase);
			grandTotalLabelCell.setPaddingBottom(5);
			grandTotalLabelCell.setPaddingTop(5);
			grandTotalLabelCell.setBorder(Rectangle.NO_BORDER);
			grandTotalLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			grandTotalLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase grandTotalAmountPhrase = new Phrase(String.valueOf(grandTotal), bodyContentBoldNormal);
			PdfPCell grandTotalAmountCell = new PdfPCell(grandTotalAmountPhrase);
			grandTotalAmountCell.setPaddingBottom(5);
			grandTotalAmountCell.setPaddingTop(5);
			grandTotalAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			grandTotalAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			grandTotalAmountCell.setBorder(Rectangle.NO_BORDER);

			tab.addCell(grandTotalLabelCell);
			tab.addCell(grandTotalAmountCell);

			Phrase sgstPhrase = new Phrase("SGST(%)", bodyContentBoldFont);
			PdfPCell sgstLabelCell = new PdfPCell(sgstPhrase);
			sgstLabelCell.setPaddingBottom(5);
			sgstLabelCell.setPaddingTop(5);
			sgstLabelCell.setBorder(Rectangle.NO_BORDER);
			sgstLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sgstLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Phrase sgstAmountPhrase = new Phrase(sgst, bodyContentBoldNormal);
			PdfPCell sgstAmountCell = new PdfPCell(sgstAmountPhrase);
			sgstAmountCell.setPaddingBottom(5);
			sgstAmountCell.setPaddingTop(5);
			sgstAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			sgstAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			sgstAmountCell.setBorder(Rectangle.NO_BORDER);

			tab.addCell(sgstLabelCell);
			tab.addCell(sgstAmountCell);

			Phrase cgstPhrase = new Phrase("CGST(%)", bodyContentBoldFont);
			PdfPCell cgstLabelCell = new PdfPCell(cgstPhrase);
			cgstLabelCell.setPaddingBottom(5);
			cgstLabelCell.setPaddingTop(5);
			cgstLabelCell.setBorder(Rectangle.NO_BORDER);
			cgstLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cgstLabelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Phrase cgstAmountPhrase = new Phrase(cgst, bodyContentBoldNormal);
			PdfPCell cgstAmountCell = new PdfPCell(cgstAmountPhrase);
			cgstAmountCell.setPaddingBottom(5);
			cgstAmountCell.setPaddingTop(5);
			cgstAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cgstAmountCell.setBorder(Rectangle.NO_BORDER);
			cgstAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tab.addCell(cgstLabelCell);
			tab.addCell(cgstAmountCell);

			Phrase empty = new Phrase("-----------------------------------------", bodyContentBoldFont);
			PdfPCell emptyCell = new PdfPCell(empty);
			emptyCell.setColspan(2);
			emptyCell.setPaddingBottom(5);
			emptyCell.setPaddingTop(5);
			emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			emptyCell.setBorder(Rectangle.NO_BORDER);
			emptyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tab.addCell(emptyCell);

			Phrase invoiceTotalPhrase = new Phrase("INVOICETOTAL", bodyContentBoldNormal);
			PdfPCell invoiceTotalCell = new PdfPCell(invoiceTotalPhrase);
			invoiceTotalCell.setColspan(2);
			invoiceTotalCell.setPaddingBottom(1);
			invoiceTotalCell.setPaddingTop(3);
			invoiceTotalCell.setBorder(Rectangle.NO_BORDER);
			invoiceTotalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			invoiceTotalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase overAlltotalAmountPhrase = new Phrase("" + String.valueOf(overAlltotal), bodyContentYellowBoldFont);
			PdfPCell overAlltotalAmountCell = new PdfPCell(overAlltotalAmountPhrase);
			overAlltotalAmountCell.setColspan(2);
			overAlltotalAmountCell.setPaddingBottom(5);
			overAlltotalAmountCell.setPaddingTop(1);
			overAlltotalAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			overAlltotalAmountCell.setBorder(Rectangle.NO_BORDER);
			overAlltotalAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			// overAlltotalAmountCell.setBackgroundColor(colorBlue);

			tab.addCell(invoiceTotalCell);
			tab.addCell(overAlltotalAmountCell);

			PdfPCell invoiceTotalMainCell = new PdfPCell(tab);
			invoiceTotalMainCell.setBorder(PdfPCell.NO_BORDER);
			invoiceTotalMainCell.setUseBorderPadding(true);
			invoiceTotalMainCell.setPaddingLeft(2);
			invoiceTotalMainCell.setPaddingLeft(320);
			invoiceTotalMainCell.setPaddingTop(5);
			invoiceTotalMainCell.setPaddingRight(2);
			invoiceTotalMainCell.setPaddingBottom(75f);
			tables.addCell(invoiceTotalMainCell);

			/************************** Footer Table starts ****************************/
			PdfPTable footerTable = new PdfPTable(1);

			footerTable.setSplitLate(false);
			footerTable.setWidthPercentage(100);// Code 2

			Phrase termsLine = new Phrase("Disclaimer:Terms And Conditions Apply", bodyContentBoldNormal);
			PdfPCell termsLineCell = new PdfPCell(termsLine);

			termsLineCell.setPaddingBottom(1);
			termsLineCell.setPaddingLeft(4);
			termsLineCell.setPaddingTop(5);
			termsLineCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			termsLineCell.setBorder(Rectangle.NO_BORDER);
			termsLineCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			Phrase emptyFooterLine = new Phrase(
					"______________________________________________________________________________________________________",
					bodyContentBoldFont);
			PdfPCell emptyFooterLineCell = new PdfPCell(emptyFooterLine);

			emptyFooterLineCell.setPaddingBottom(1);
			emptyFooterLineCell.setPaddingTop(5);
			emptyFooterLineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			emptyFooterLineCell.setBorder(Rectangle.NO_BORDER);
			emptyFooterLineCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell footerThankCell = new PdfPCell(new Phrase(String.format("Thank You For Visiting Us!"),
					new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

			footerThankCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footerThankCell.setBorder(Rectangle.NO_BORDER);
			footerThankCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell footerAddressCell = new PdfPCell(new Phrase(
					String.format(companyBO.getStreet()+ ", " + companyBO.getCity()),
					new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
			footerAddressCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footerAddressCell.setBorder(Rectangle.NO_BORDER);
			footerAddressCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			PdfPCell footerAddressCell2 = new PdfPCell(
					new Phrase(String.format(companyBO.getDistrict()+ "- " + companyBO.getPostalCode()+ ". " + companyBO.getMobileNumber() + ". " ),
							new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
			/*
			 * PdfPCell footerAddressCell3 = new PdfPCell( new
			 * Phrase(String.format(companyBO.getDistrict(),"-" +
			 * companyBO.getPostalCode(),"." + companyBO.getMobileNumber()), new
			 * Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
			 */
			footerAddressCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			footerAddressCell2.setBorder(Rectangle.NO_BORDER);
			footerAddressCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell websiteCell = new PdfPCell(
					new Phrase(String.format(companyBO.getWebsite()), new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL)));
			websiteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			websiteCell.setBorder(Rectangle.NO_BORDER);
			websiteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			footerTable.addCell(termsLineCell);
			footerTable.addCell(emptyFooterLineCell);
			footerTable.addCell(footerThankCell);
			footerTable.addCell(footerAddressCell);
			footerTable.addCell(footerAddressCell2);
			footerTable.addCell(websiteCell);

			PdfPCell footerMainTable = new PdfPCell(footerTable);
			footerMainTable.setBorder(PdfPCell.NO_BORDER);
			footerMainTable.setUseBorderPadding(true);
			footerMainTable.setPaddingLeft(2);
			footerMainTable.setPaddingTop(5);
			footerMainTable.setPaddingRight(2);

			tables.addCell(footerMainTable);

			document.add(tables); // add tables to document
			document.close();

		}catch (Exception ex) {
			if (logger.isDebugEnabled()) {
				logger.debug("generate-invoice has failed:" + ex.getMessage());
			}
			logger.info("generate-invoice has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}


	}

	@RequestMapping(value = "download-invoice", method = RequestMethod.GET)
	public String getResumeDownload(@RequestParam("salesId") long salesId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
LOGGER.entry();
		ModelAndView model = new ModelAndView();
		String filelocation = null;
		SalesOrderVO salesOrderVO = salesOrderService.getSalesOrderById(salesId);
	try {
		if (null != salesOrderVO) {
			filelocation = "C:\\usr\\local\\mysales\\invoice\\" + salesOrderVO.getInvoiceName();
		}

		String downloadPath = filelocation;
		File file = new File(downloadPath);
		InputStream is = new FileInputStream(file);
		response.setContentType("application/pdf");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");

		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();
		model.addObject("errorMessage", "file Download Successfully!");
	}catch (Exception ex) {
		if (logger.isDebugEnabled()) {
			logger.debug("download-invoice has failed:" + ex.getMessage());
		}
		logger.info("download-invoice has failed:" + ex.getMessage());
	} finally {
		LOGGER.exit();
	}

		return "redirect:/view-sales-order-list?salesno" + salesOrderVO.getSalesOrderNo();

	}

}
