package com.scube.crm.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.AccountBO;
import com.scube.crm.bo.CompanyBO;
import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.PaymentBO;
import com.scube.crm.bo.PriceBookBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.bo.SalesOrderProductsBO;
import com.scube.crm.dao.SalesOrderDAO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.HistoryVO;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.PaymentVO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderProductsVO;
import com.scube.crm.vo.SalesOrderVO;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(SalesOrderServiceImpl.class);

	@Autowired
	OpportunityService opportunityService;

	@Autowired
	SalesOrderDAO salesOrderDAO;
	
	@Autowired
	GstService gstService;

	String sNo = "check";
	double totalPriceWithGst;

	public List<SalesOrderBO> retriveSalesOrders(SalesOrderBO salesOrderBO) {

		SalesOrderVO salesOrdervO = new SalesOrderVO(); // Company
		
		List<SalesOrderBO> list = new ArrayList<>();

		try {
			if (salesOrderBO != null) {
				if(null != salesOrderBO.getCompanyId()&& 0< salesOrderBO.getCompanyId()) {
				salesOrdervO.setCompanyId(salesOrderBO.getCompanyId());
				}
				List<SalesOrderVO> salesOrderVO = salesOrderDAO.retriveSalesOrders(salesOrderBO);
				if (salesOrderVO != null && salesOrderVO.size() > 0 && !salesOrderVO.isEmpty()) {
					long count = salesOrderBO.getRecordIndex();
					for (SalesOrderVO x : salesOrderVO) {

						AccountBO aBO = new AccountBO();
						SalesOrderBO SalesOrderbo = new SalesOrderBO();
						SalesOrderbo.setsNo(++count);
						SalesOrderbo.setSalesOrderNo(x.getSalesOrderNo());
						SalesOrderbo.setSalesOrderId(x.getSalesOrderId());
						SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
						String startDate = date.format((x.getCreated()));
						SalesOrderbo.setDate(startDate);

						if (x.getAccountVO() != null && x.getAccountVO().getAccountName() != null) {
							aBO.setAccountName(x.getAccountVO().getAccountName());
						} else {
							aBO.setAccountName("N/A");

						}

						SalesOrderbo.setAccountBO(aBO);
						list.add(SalesOrderbo);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public SalesOrderBO getSalesOrder(long salesOrderId) {
		SalesOrderBO salesOrderBO = new SalesOrderBO();
		SalesOrderVO salesOrderVO;
		try {
			salesOrderVO = salesOrderDAO.getSalesOrder(salesOrderId);

			if (null != salesOrderVO) {

				AccountBO aBO = new AccountBO();
				aBO.setAccountId(salesOrderVO.getAccountVO().getAccountId());
				aBO.setAccountName(salesOrderVO.getAccountVO().getAccountName());
				aBO.setCity(salesOrderVO.getAccountVO().getBillingCity());
				aBO.setEmail(salesOrderVO.getAccountVO().getEmail());
				aBO.setContactNo(salesOrderVO.getAccountVO().getPhone());
				aBO.setState(salesOrderVO.getAccountVO().getBillingState());
				aBO.setCountry(salesOrderVO.getAccountVO().getBillingCountry());
				aBO.setType(salesOrderVO.getAccountVO().getType());
				aBO.setIndustry(salesOrderVO.getAccountVO().getIndustry());
				salesOrderBO.setAccountBO(aBO);
			}
			salesOrderBO.setSalesOrderNo(salesOrderVO.getSalesOrderNo());
			salesOrderBO.setSalesOrderId(salesOrderVO.getSalesOrderId());
			salesOrderBO.setInvoiceName(salesOrderVO.getInvoiceName());
			salesOrderBO.setsNo(1);
			salesOrderBO.setQuantity(salesOrderVO.getQuantity());
			salesOrderBO.setGrandTotal(salesOrderVO.getGrandTotal());
			salesOrderBO.setTotalInvoice(salesOrderVO.getTotalInvoice());
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String startDate = df.format((salesOrderVO.getCreated()));
			salesOrderBO.setDate(startDate);

			List<SalesOrderProductsBO> salesOrderProductsBOList = new ArrayList<SalesOrderProductsBO>();
			int count=1;
			for (SalesOrderProductsVO salesOrderProductVO : salesOrderVO.getSalesOrderProductsVO()) {
				SalesOrderProductsBO salesOrderProductsBO = new SalesOrderProductsBO();
				salesOrderProductsBO.setsNo(count);
				salesOrderProductsBO.setSalesOderProductsId(salesOrderProductVO.getSalesOderProductsId());
				salesOrderProductsBO.setQuantity(salesOrderProductVO.getQuantity());
				salesOrderProductsBO.setQuantityPrice(salesOrderProductVO.getQuantityPrice());
				salesOrderProductsBO.setPrice(salesOrderProductVO.getPrice());
				GstBO gstBO = new GstBO();
				InventoryBO inventoryBO = new InventoryBO();
				PriceBookBO priceBookBO = new PriceBookBO();
				if (null != salesOrderProductVO.getGstVO() && 0 < salesOrderProductVO.getGstVO().getGstId()) {
					gstBO.setGstId(salesOrderProductVO.getGstVO().getGstId());
//				gstBO.setCgstValue(salesOrderProductVO.getGstVO().getCgst());
					salesOrderProductsBO.setGstBO(gstBO);
				}
				if (null != salesOrderProductVO.getPriceBookVo()
						&& 0 < salesOrderProductVO.getPriceBookVo().getPriceBookId()) {
					priceBookBO.setPriceBookId(salesOrderProductVO.getPriceBookVo().getPriceBookId());
					priceBookBO.setPriceBookName(salesOrderProductVO.getPriceBookVo().getPriceBookName());
					priceBookBO.setPrice(salesOrderProductVO.getPriceBookVo().getPrice());
					salesOrderProductsBO.setPriceBookBo(priceBookBO);
				}

				if (null != salesOrderProductVO.getProduct() && 0 < salesOrderProductVO.getProduct().getServiceId()) {
					inventoryBO.setServiceId(salesOrderProductVO.getProduct().getServiceId());
					inventoryBO.setServiceName(salesOrderProductVO.getProduct().getServiceName());
					salesOrderProductsBO.setProduct(inventoryBO);
				}
				salesOrderProductsBOList.add(salesOrderProductsBO);
				count++;
			}

			salesOrderBO.setSalesOrderProductBO(salesOrderProductsBOList);
		} catch (MySalesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return salesOrderBO;

	}

	public List<SalesOrderBO> getSalesOrderList(SalesOrderBO salesOrders) {
		List<SalesOrderVO> salesOrderVO = salesOrderDAO.getSalesOrderList(salesOrders);
		AtomicInteger ref = new AtomicInteger(0); // Why are we using atomic integer here?
		List<SalesOrderBO> list = new ArrayList<>();
		if (null != salesOrderVO && !salesOrderVO.isEmpty() && salesOrderVO.size() > 0) {
			int listSize = salesOrderVO.size();
			SalesOrderVO lastSales = salesOrderVO.get(listSize - 1);
			salesOrderVO.forEach(salesOrder -> {

				if (null != salesOrder.getSalesOrderNo()) {
					sNo = salesOrder.getSalesOrderNo();
					// ClientBO client = new ClientBO();
					AccountBO aBO = new AccountBO();
					//GstBO gstBO = new GstBO();
					SalesOrderBO salesOrderbo = new SalesOrderBO();
					if (null != salesOrder && null != salesOrder.getAccountVO()) {
						aBO.setAccountId(salesOrder.getAccountVO().getAccountId());
						aBO.setAccountName(salesOrder.getAccountVO().getAccountName());
						aBO.setCity(salesOrder.getAccountVO().getBillingCity());
						aBO.setEmail(salesOrder.getAccountVO().getEmail());
						aBO.setContactNo(salesOrder.getAccountVO().getPhone());
						aBO.setState(salesOrder.getAccountVO().getBillingState());
						aBO.setCountry(salesOrder.getAccountVO().getBillingCountry());
						aBO.setType(salesOrder.getAccountVO().getType());
						aBO.setIndustry(salesOrder.getAccountVO().getIndustry());
					}
					aBO.setSalesOrder(sNo);
					salesOrderbo.setAccountBO(aBO);

					salesOrderbo.setSalesOrderId(salesOrder.getSalesOrderId());
					// SalesOrderbo.setClientBO(client);
					salesOrderbo.setInvoiceName(salesOrder.getInvoiceName());
					salesOrderbo.setsNo(ref.incrementAndGet());
					salesOrderbo.setQuantity(salesOrder.getQuantity());
					salesOrderbo.setTotalInvoice(salesOrder.getTotalInvoice());
					salesOrderbo.setGrandTotal(salesOrder.getGrandTotal());
					SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					String startDate = df.format((salesOrder.getCreated()));
					salesOrderbo.setDate(startDate);
					
					List<SalesOrderProductsBO> salesOrderProductsBOList = new ArrayList<SalesOrderProductsBO>();
					int count=1;
					for (SalesOrderProductsVO salesOrderProductVO : salesOrder.getSalesOrderProductsVO()) {
						SalesOrderProductsBO salesOrderProductsBO = new SalesOrderProductsBO();
						salesOrderProductsBO.setsNo(count);
						salesOrderProductsBO.setSalesOderProductsId(salesOrderProductVO.getSalesOderProductsId());
						salesOrderProductsBO.setQuantity(salesOrderProductVO.getQuantity());
						salesOrderProductsBO.setQuantityPrice(salesOrderProductVO.getQuantityPrice());
						salesOrderProductsBO.setPrice(salesOrderProductVO.getPrice());
						GstBO gstBO = new GstBO();
						InventoryBO inventoryBO = new InventoryBO();
						PriceBookBO priceBookBO = new PriceBookBO();
						if (null != salesOrderProductVO.getGstVO() && 0 < salesOrderProductVO.getGstVO().getGstId()) {
							gstBO.setGstId(salesOrderProductVO.getGstVO().getGstId());
//						gstBO.setCgstValue(salesOrderProductVO.getGstVO().getCgst());
							salesOrderProductsBO.setGstBO(gstBO);
						}
						if (null != salesOrderProductVO.getPriceBookVo()
								&& 0 < salesOrderProductVO.getPriceBookVo().getPriceBookId()) {
							priceBookBO.setPriceBookId(salesOrderProductVO.getPriceBookVo().getPriceBookId());
							priceBookBO.setPriceBookName(salesOrderProductVO.getPriceBookVo().getPriceBookName());
							priceBookBO.setPrice(salesOrderProductVO.getPriceBookVo().getPrice());
							salesOrderProductsBO.setPriceBookBo(priceBookBO);
						}

						if (null != salesOrderProductVO.getProduct() && 0 < salesOrderProductVO.getProduct().getServiceId()) {
							inventoryBO.setServiceId(salesOrderProductVO.getProduct().getServiceId());
							inventoryBO.setServiceName(salesOrderProductVO.getProduct().getServiceName());
							salesOrderProductsBO.setProduct(inventoryBO);
						}
						salesOrderProductsBOList.add(salesOrderProductsBO);
						count++;
					}
					salesOrderbo.setSalesOrderProductBO(salesOrderProductsBOList);
					
					// Product
					InventoryBO productService = new InventoryBO();
//					productService.setServiceName(salesOrder.getProduct().getServiceName());
					PriceBookBO priceBook = new PriceBookBO();
//					long price = salesOrder.getPricebookvo().getPrice();
//					productService.setFees(price);
//					salesOrderbo.setProduct(productService);
//					priceBook.setPrice(price);
//					salesOrderbo.setPricebookbo(priceBook);
					// PriceBook

					// priceBook.setPriceBookId((salesOrderbo.getPricebookbo().getPriceBookId()));
					// long price1=(long) salesOrderbo.getPrice();
					// priceBook.setPrice(price1);
//					pricebookvo.setPrice(salesOrderBo.getPricebookbo().getPrice());
//					priceBook.setPriceBookName(salesOrder.getPricebookvo().getPriceBookName());
//					salesOrderbo.setPricebookbo(priceBook);

				//	Double quantity = Double.valueOf(salesOrder.getQuantity());
//					double overAllgrandTotal = price * quantity;
					// gstBO=salesOrderDAO.getGstById(salesOrder.getGstVO().getGstId());

//					salesOrderbo.setTotalInvoice((long) (price * quantity));

//					totalPriceWithGst = overAllgrandTotal + totalPriceWithGst;
					// double grandTotal=totalPriceWithGst
					/*
					 * SalesOrderbo.setTotalInvoice(overAllTotal); }
					 */

					if (lastSales.getSalesOrderId() == salesOrder.getSalesOrderId()) {
					//	salesOrderbo.setGrandTotal((long) totalPriceWithGst);
						if (0.0 < totalPriceWithGst) {
							double percentage = 100;
//								String removepercentageCgst = salesOrder.getGstVO().getCgst().replace("%", "");
//								double cGst = Double.valueOf(removepercentageCgst);
							double cGstValue;
							// double TotalGstFinalRate;
							/*
							 * String sgst = salesOrder.getGstVO().getSgst(); String cgst =
							 * salesOrder.getGstVO().getCgst(); gstBO.setCgst(cgst); gstBO.setSgst(sgst);
							 * SalesOrderbo.setGstBO(gstBO);
							 */
//								cGstValue = cGst / percentage;

							double sGstValue;
							// double percentage = 100;
//									String removepercentageSgst = salesOrder.getGstVO().getSgst().replace("%", "");
//									double sGst = Double.valueOf(removepercentageSgst);
							// double sGstValue;
							// String sgst = salesOrder.getGstVO().getSgst();
							// String cgst = salesOrder.getGstVO().getCgst();
							// gstBO.setCgst(cgst);
							// gstBO.setSgst(sgst);
							// SalesOrderbo.setGstBO(gstBO);
//									sGstValue = sGst / percentage;
							/*
							 * sGstFinalRate=sGstValue*totalPrice;
							 * //gstBO.setSgst(String.valueOf(sGstFinalRate));
							 * 
							 * overAllgrandTotal=totalPrice+sGstFinalRate+cGstFinalRate; double
							 * value=overAllgrandTotal;
							 */

//									double sGstFinalRate = sGstValue * totalPriceWithGst;
//									double cGstFinalRate = cGstValue * totalPriceWithGst;
//									totalPriceWithGst=totalPriceWithGst+sGstFinalRate+cGstFinalRate;
//									String sgst = salesOrder.getGstVO().getSgst();
//									String cgst = salesOrder.getGstVO().getCgst();
//									gstBO.setCgstValue(cGstFinalRate);
//									gstBO.setSgstValue(sGstFinalRate);
//									gstBO.setCgst(cgst);
//									gstBO.setSgst(sgst);
//							salesOrderbo.setGstBO(gstBO);
							salesOrderbo.setTotalInvoice((long) totalPriceWithGst);
							// gstBO.setSgst(String.valueOf(sGstFinalRate));

						}
					}

//					productService.setFees(salesOrder.getProduct().getFees());
//					salesOrderbo.setProduct(productService);
					salesOrderbo.setSalesOrderNo(salesOrder.getSalesOrderNo());

					salesOrderbo.setSalesOrderId(salesOrder.getSalesOrderId());
					if (null != salesOrder && null != salesOrder.getAccountVO()
							&& null != salesOrder.getAccountVO().getAccountName()) {
						aBO.setAccountName(salesOrder.getAccountVO().getAccountName());
					}
					salesOrderbo.setSalesOrderNo(salesOrder.getSalesOrderNo());
					salesOrderbo.setAccountBO(aBO);
					list.add(salesOrderbo);

				}
			});
		}
		totalPriceWithGst = 0.0;

		return list;

	}

	@Override
	public PaymentBO savePayment(PaymentBO paymentbo) {

		PaymentVO paymentvo = new PaymentVO();

		paymentvo.setPaymentmode(paymentbo.getPaymentmode());
		paymentvo.setDate(paymentbo.getDate());
		paymentvo.setTransactionId(paymentbo.getTransactionId());
		paymentvo.setCheckNo(paymentbo.getCheckNo());
		paymentvo.setSalesOrderNo(paymentbo.getSalesOrderNo());
		paymentvo.setSalesOrderId(paymentbo.getSalesOrderId());

		paymentvo = salesOrderDAO.savePayment(paymentvo);

		return paymentbo;
	}

	@Override
	public long salesCount(SalesOrderBO salesOrderBO) throws Exception {
		SalesOrderServiceImpl.LOGGER.entry();
		long count = 0;
		SalesOrderVO salesOrderVO = new SalesOrderVO();
		try {
		if(null != salesOrderBO.getCompanyId()&& 0< salesOrderBO.getCompanyId()) {
		salesOrderVO.setCompanyId(salesOrderBO.getCompanyId());

		}
		if (null != salesOrderBO.getSalesOrderNo()&&!salesOrderBO.getSalesOrderNo().isEmpty()) {
			salesOrderVO.setSalesOrderNo(salesOrderBO.getSalesOrderNo());
		}
		
			if (salesOrderVO != null && salesOrderVO.getCreated() != null) {
				salesOrderVO.setCreated(salesOrderBO.getCreated());
			}
			count = salesOrderDAO.salesCount(salesOrderVO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving count has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving count has failed: " + ex.getMessage());
		} finally {
			SalesOrderServiceImpl.LOGGER.exit();
		}
		return count;
	}

	@Override
	public long searchCount(SalesOrderBO salesOrderBO) throws Exception {
		SalesOrderServiceImpl.LOGGER.entry();
		long count = 0;
		try {
			if (salesOrderBO != null) {
				SalesOrderVO vo = new SalesOrderVO();
				vo.setSalesOrderNo(salesOrderBO.getSalesOrderNo());
				vo.setCompanyId(salesOrderBO.getCompanyId()); // Company

				if (salesOrderBO != null && 0 < salesOrderBO.getAccountBO().getAccountId()) {
					AccountVO aVO = new AccountVO();
					aVO.setAccountId(salesOrderBO.getAccountBO().getAccountId());
					vo.setAccountVO(aVO);
				}
				if (null != salesOrderBO.getSalesOrderNo()&&!salesOrderBO.getSalesOrderNo().isEmpty()) {
					vo.setSalesOrderNo(salesOrderBO.getSalesOrderNo());
				}

				if (!salesOrderBO.getDate().isEmpty() && salesOrderBO.getDate() != "") {
//					String sDate = salesOrderBO.getDate();
//					Date toDate = 	new SimpleDateFormat("MM/dd/yyyy").parse(sDate);	
					SimpleDateFormat formatterInput = new SimpleDateFormat("MM/dd/yyyy");
					Date date = formatterInput.parse(salesOrderBO.getDate());
					vo.setCreated(date);
				}

				count = salesOrderDAO.searchCount(vo, salesOrderBO.getDate());
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving count has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving count has failed: " + ex.getMessage());
		} finally {
			SalesOrderServiceImpl.LOGGER.exit();
		}
		return count;
	}

	@Override
	public List<SalesOrderBO> search(SalesOrderBO salesOrderBO) throws Exception {
		SalesOrderServiceImpl.LOGGER.entry();
		List<SalesOrderBO> salesOrderBOList = new ArrayList<SalesOrderBO>();
		List<SalesOrderVO> salesOrderVOList = new ArrayList<SalesOrderVO>();
		try {
			if (salesOrderBO != null) {
				SalesOrderVO vo = new SalesOrderVO();
				SalesOrderVO bo = new SalesOrderVO();
				vo.setSalesOrderNo(salesOrderBO.getSalesOrderNo());
				vo.setMaxRecord(salesOrderBO.getMaxRecord());
				vo.setRecordIndex(salesOrderBO.getRecordIndex());
				vo.setCompanyId(salesOrderBO.getCompanyId()); // Company

				if (salesOrderBO != null && 0 < salesOrderBO.getAccountBO().getAccountId()) {
					AccountVO aVO = new AccountVO();
					aVO.setAccountId(salesOrderBO.getAccountBO().getAccountId());
					vo.setAccountVO(aVO);
				}
				if (vo != null && vo.getSalesOrderNo() != null && !vo.getSalesOrderNo().isEmpty()) {
					vo.setSalesOrderNo(salesOrderBO.getSalesOrderNo());
				}
				if (vo != null && vo.getCreated() != null) {
					vo.setCreated(bo.getCreated());
				}
				if (!salesOrderBO.getDate().isEmpty() && salesOrderBO.getDate() != "") {
					String sDate = salesOrderBO.getDate();
					Date toDate = new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
					vo.setCreated(toDate);
				}

				salesOrderVOList = salesOrderDAO.search(vo, salesOrderBO.getDate());
				if (salesOrderVOList != null && salesOrderVOList.size() > 0 && !salesOrderVOList.isEmpty()) {
					long count = salesOrderBO.getRecordIndex();
					for (SalesOrderVO x : salesOrderVOList) {
						// ClientBO client = new ClientBO();
						AccountBO aBO = new AccountBO();
						SalesOrderBO SalesOrderbo = new SalesOrderBO();
						SalesOrderbo.setsNo(++count);
						SalesOrderbo.setSalesOrderNo(x.getSalesOrderNo());
						SalesOrderbo.setSalesOrderId(x.getSalesOrderId());
						// String date = x.getCreated().toString();
						// SalesOrderbo.setDate(date);
						aBO.setAccountId(x.getAccountVO().getAccountId());
						aBO.setAccountName(x.getAccountVO().getAccountName());
						aBO.setCity(x.getAccountVO().getBillingCity());
						aBO.setEmail(x.getAccountVO().getEmail());
						aBO.setContactNo(x.getAccountVO().getPhone());
						aBO.setState(x.getAccountVO().getBillingState());
						aBO.setCountry(x.getAccountVO().getBillingCountry());
						aBO.setType(x.getAccountVO().getType());
						aBO.setIndustry(x.getAccountVO().getIndustry());
						aBO.setSalesOrder(sNo);
						SalesOrderbo.setAccountBO(aBO);
						SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
						String startDate = date.format((x.getCreated()));
						SalesOrderbo.setDate(startDate);

						/*
						 * if (null != x.getAccountDO() && null != x.getAccountDO().getAccountName()) {
						 * aBO.setAccountName(x.getAccountDO().getAccountName()); } else {
						 * aBO.setAccountName("N/A");
						 * 
						 * } SalesOrderbo.setAccountBO(aBO);
						 */
						salesOrderBOList.add(SalesOrderbo);
					}
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving count has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving count has failed: " + ex.getMessage());
		} finally {
			SalesOrderServiceImpl.LOGGER.exit();
		}
		return salesOrderBOList;
	}

	@Override
	public Map<Integer, String> retriveAccounts(AccountBO accountBO) throws Exception {
		SalesOrderServiceImpl.LOGGER.entry();

		Map<Integer, String> dOMap = new HashMap<Integer, String>();
		SalesOrderVO vo = new SalesOrderVO();
		SalesOrderBO bo = new SalesOrderBO();
		try {
			if (vo != null && vo.getSalesOrderNo() != null && !vo.getSalesOrderNo().isEmpty()) {
				vo.setSalesOrderNo(bo.getSalesOrderNo());
			}
			if (vo != null && vo.getCreated() != null) {
				vo.setCreated(bo.getCreated());
			}
			vo.setCompanyId(bo.getCompanyId());
			dOMap = opportunityService.retriveAccounts(accountBO);
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving account has failed: " + ex.getMessage());
		} finally {
			SalesOrderServiceImpl.LOGGER.exit();
		}
		return dOMap;
	}

	@Override
	public AccountBO getProfile(int accId) throws Exception {
		SalesOrderServiceImpl.LOGGER.entry();
		AccountBO profile = new AccountBO();
		AccountVO doProfile = new AccountVO();
		try {
			if (accId > 0) {
				doProfile = salesOrderDAO.getProfile(accId);
				if (doProfile != null) {
					profile.setAccountId(doProfile.getAccountId());
					profile.setContactNo(doProfile.getPhone());
					profile.setType(doProfile.getType());
					profile.setEmail(doProfile.getEmail());
					profile.setIndustry(doProfile.getIndustry());
					profile.setCity(doProfile.getBillingCity());
					profile.setState(doProfile.getBillingState());
					profile.setCountry(doProfile.getBillingCountry());
				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieving Account has been failed: " + ex.getMessage());
			}
			LOGGER.info("Retrieving account has failed: " + ex.getMessage());
		} finally {
			SalesOrderServiceImpl.LOGGER.exit();
		}
		return profile;
	}

	@Override
	public void saveInvoice(SalesOrderBO salesOrder) {
		SalesOrderVO salesOrderVo = new SalesOrderVO();
		salesOrderVo.setSalesOrderId(salesOrder.getSalesOrderId());
		salesOrderVo.setInvoiceName(salesOrder.getInvoiceName());
		salesOrderDAO.saveInvoice(salesOrderVo);
	}

	@Override
	public SalesOrderVO getSalesOrderById(long salesId) {
		// TODO Auto-generated method stub
		return salesOrderDAO.getSalesOrderById(salesId);
	}

	@Override
	public List<PriceBookVO> getPricebook(long serviceId) {
		PriceBookVO priceBookVO = new PriceBookVO();
		InventoryVO inventory = new InventoryVO();
		inventory.setServiceId(serviceId);
		priceBookVO.setProductservicevo(inventory);
		priceBookVO.setIsDeleted(false);
		priceBookVO.setActive(true);
		return salesOrderDAO.getPricebook(priceBookVO);
	}

	@Override
	public boolean getPaymentStatus(SalesOrderBO salesOrder) {
		boolean status = salesOrderDAO.getPaymentStatus(salesOrder);

		return status;
	}

	@Override
	public CompanyBO viewCompanyDetails(CompanyBO companyBO) {
		LOGGER.entry();
		try {
			Company company = new Company();
			company.setCompanyId(companyBO.getCompanyId());
			company = salesOrderDAO.viewCompanyDetails(company);
			if (company != null) {
				companyBO.setCompanyId(company.getCompanyId());
				companyBO.setCompanyName(company.getCompanyName());
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view of company has failed:" + ex.getMessage());
			}
			LOGGER.info("view of company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return companyBO;
	}

	@Override
	public SalesOrderBO calculateInvoiceValues(SalesOrderBO salesOrder) {
		double totalPriceWithGst = 0.0;
        double cgstTotal = 0.0;
        double sgstTotal = 0.0;

        // Calculate total price with GST, CGST, and SGST
        for (SalesOrderProductsBO product : salesOrder.getSalesOrderProductBO()) {
            double price = product.getPrice();
            double quantity = product.getQuantity();
            double total = price * quantity;
            
            // Assuming you have GST values stored in the product's GSTBO
            double cgst = product.getGstBO().getCgstValue();
            double sgst = product.getGstBO().getSgstValue();

            totalPriceWithGst += total + cgst + sgst;
            cgstTotal += cgst;
            sgstTotal += sgst;
        }

        // Update salesOrder with calculated values
        salesOrder.setGrandTotal((long) totalPriceWithGst);
      // salesOrder.setCgstValue(cgstTotal);
      //  salesOrder.setSgstValue(sgstTotal);
        salesOrder.setTotalInvoice((long) totalPriceWithGst);

        return salesOrder;
    }

	@Override
	public double calculateCGST(SalesOrderBO salesOrder) {
		double cgstTotal = 0.0;

        for (SalesOrderProductsBO product : salesOrder.getSalesOrderProductBO()) {
        	GstBO gstBo=product.getGstBO();
        	if(0<gstBo.getGstId()) {
				GstBO gst = gstService.getGst(gstBo.getGstId());
				if(null!=gst) {
					String removepercentageCgst = gst.getCgst().replace("%", "");
					double cgst = Double.valueOf(removepercentageCgst);
					cgstTotal += cgst;
				}
			}
        }

        return cgstTotal;
	}

	@Override
	public double calculateSGST(SalesOrderBO salesOrder) {
		double sgstTotal = 0.0;

        for (SalesOrderProductsBO product : salesOrder.getSalesOrderProductBO()) {
        	GstBO gstBo=product.getGstBO();
        	if(0<gstBo.getGstId()) {
				GstBO gst = gstService.getGst(gstBo.getGstId());
				if(null!=gst) {
					String removepercentageSgst = gst.getSgst().replace("%", "");
					double sgst = Double.valueOf(removepercentageSgst);
					sgstTotal += sgst;
				}
			}
        }

        return sgstTotal;
	}
	

}
