import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;

import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.BaseFlightInfo;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.bean.search.OneWayFlightInfo;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.bean.search.RoundTripFlightInfo;
import com.qunar.qfwrapper.constants.Constants;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFGetMethod;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjsairdc001 implements QunarCrawler{
	 
	public BookingResult getBookingInfo(FlightSearchParam arg0) {

		String bookingUrlPre = "http://www.sverigeflyg.se/sv/bokning";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		bookingInfo.setInputs(map);		
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;
	}
	
	private static String HTML_SPLIT_BZ = "HTML_SPLIT_BZ"; 

	public String getHtml(FlightSearchParam arg0) {
		StringBuffer resultHtml = new StringBuffer();
		QFPostMethod post = null;
		try
		{
		// get all query parameters from the url set by wrapperSearchInterface
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		//httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		// 通过代理访问
		//httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		//Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));
		
		//首次请求，164 提取__VIEWSTATE、__EVENTVALIDATION
		StringBuffer sbUrl = new StringBuffer("https://book.sverigeflyg.se/GeneralSearch/SearchFromExternal?");
		NameValuePair[] names164 = {
				new NameValuePair("NumberOfAdults","1"),
				new NameValuePair("NumberOfInfants","0"),
				new NameValuePair("NumberOfUm","0"),
				new NameValuePair("NumberOfSeniorCitizens","0"),
				new NameValuePair("NumberOfChildren","0"),
				new NameValuePair("NumberOfStudents","0"),
				new NameValuePair("DepartureDate",arg0.getDepDate() + "%2000:00:00"),
				new NameValuePair("ReturnDate",arg0.getDepDate() + "%2000:00:00"),
				new NameValuePair("SearchRoundTrip","True"),
				new NameValuePair("OriginAiportCode",arg0.getDep()),
				new NameValuePair("DestinationAirportCode",arg0.getArr()),
				new NameValuePair("CampignCode","")
	    };
		for (int i = 0;i < names164.length;i++) {
			sbUrl.append(names164[i].getName() + "=" + names164[i].getValue());
			if (i < names164.length - 1) {
				sbUrl.append("&");
			}
		}
		QFGetMethod get = new QFGetMethod(sbUrl.toString());
		get.setFollowRedirects(false);
		get.setRequestHeader("Referer", "http://www.sverigeflyg.se/sv/bokning");
		httpClient.getState().clearCookies();
		get.addRequestHeader("Cookie","__utma=67750099.725323937.1404527773.1404527773.1404527773.1; __utmb=67750099.1.10.1404527773; __utmc=67750099; __utmz=67750099.1404527773.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
		httpClient.executeMethod(get);
		String cookie = StringUtils.join(httpClient.getState().getCookies(),"; ");
		
		// 处理302及cookie
		int code = get.getStatusCode();
		String url = "";
		if(code == HttpStatus.SC_MOVED_TEMPORARILY || code == HttpStatus.SC_MOVED_PERMANENTLY){
			Header location = get.getResponseHeader("Location");
			if(location !=null){
				url = location.getValue();
				if(!url.startsWith("http")){
					url = get.getURI().getScheme() + "://" + get.getURI().getHost() + (get.getURI().getPort()==-1?"":(":"+get.getURI().getPort())) + url;
				}
			}else{
				//return;
			}
		}		
		
		// 165  追加请求，获取cookie及二次__VIEWSTATE、__EVENTVALIDATION
		QFGetMethod get2 = new QFGetMethod(url);
		get2.setRequestHeader("Referer", "http://www.sverigeflyg.se/sv/bokning");
		httpClient.getState().clearCookies();
		get2.addRequestHeader("Cookie",cookie);
		httpClient.executeMethod(get2);
		String html165 = get2.getResponseBodyAsString();
		resultHtml.append(html165).append(this.HTML_SPLIT_BZ);
		
		// 追加请求，获取航班号
		int hbs = html165.split("firstfoldout foldout\" style=\"display: none").length - 1;
		String[] TicketGUIDs = new String[hbs];
		int hbh_dc = -1;
		int hbh_wf = -1;
		int hbh_hb = 0;
		for (int hbh = 0;hbh < hbs;hbh++) {
			String wfbz = "ORIGIN";
			if (html165.indexOf("Class=\"Itinerary" + hbh + "TicketNumber0DirectionORIGINPassengerNumer0") >= 0) {
				wfbz = "ORIGIN";
				hbh_dc++;
				hbh_hb = hbh_dc; 
			} else {
				wfbz = "DESTINATION";
				hbh_wf++; 
				hbh_hb = hbh_wf;
			}
			String hbbz = "Class=\"Itinerary" + hbh_hb + "TicketNumber0Direction" + wfbz + "PassengerNumer0";
			String TicketGUID = StringUtils.substringBetween(html165, hbbz, "/>");
			TicketGUIDs[hbh] = StringUtils.substringBetween(TicketGUID, "Value=\"", "\"");
			
			// 231  追加请求，获取航班号
			QFPostMethod post2 = new QFPostMethod("https://book.sverigeflyg.se/SearchDeparture/SelectDefaultTickets");
			post2.setRequestHeader("Content-Type", "application/json");
			String json = "{\"DefaultSelectedTickets\":[{\"PassengerIndex\":\"0\",\"Direction\":\"" + wfbz + "\",\"TicketGUID\":\"" + TicketGUIDs[hbh] + "\",\"PassengerType\":\"Adult\",\"TicketSelector\":\"Itinerary0TicketNumber0Direction" + wfbz + "PassengerNumer0\"}]}";
			post2.setRequestBody(json);
			post2.setRequestHeader("Referer", "https://book.sverigeflyg.se/SearchDeparture/SelectTickets");
			post2.setRequestHeader("x-requested-with", "XMLHttpRequest");
			httpClient.getState().clearCookies();
			String new_cookie = StringUtils.substringBetween(cookie, "ASP.NET_SessionId", ";");
			new_cookie = "__utma=67750099.725323937.1404527773.1404527773.1404527773.1; __utmb=67750099.2.10.1404527773; __utmc=67750099; __utmz=67750099.1404527773.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);" + "ASP.NET_SessionId" + new_cookie + ";" + "RefreshFilter=https://book.sverigeflyg.se/Receipt/GetReceiptOngoingBooking";
	 		post2.addRequestHeader("Cookie",new_cookie);
			post2.setRequestHeader("Connection", "Keep-Alive");
			httpClient.executeMethod(post2);
			post2.releaseConnection();
			
			// 232  追加请求，获取航班号
			QFPostMethod post3 = new QFPostMethod("https://book.sverigeflyg.se/Receipt/GetReceiptOngoingBooking");
			post3.setRequestHeader("Content-Type", "application/json");
			post3.setRequestHeader("Referer", "https://book.sverigeflyg.se/SearchDeparture/SelectTickets");
			post3.setRequestHeader("x-requested-with", "XMLHttpRequest");
			httpClient.getState().clearCookies();
			String new_cookie2 = StringUtils.substringBetween(cookie, "ASP.NET_SessionId", ";");
			new_cookie = "__utma=67750099.725323937.1404527773.1404527773.1404527773.1; __utmb=67750099.2.10.1404527773; __utmc=67750099; __utmz=67750099.1404527773.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);" + "ASP.NET_SessionId" + new_cookie + ";" + "RefreshFilter=https://book.sverigeflyg.se/SearchDeparture/SelectDefaultTickets";
	 		post3.addRequestHeader("Cookie",new_cookie);
			post3.setRequestHeader("Connection", "Keep-Alive");
			httpClient.executeMethod(post3);
			
			String html232 =  post3.getResponseBodyAsString();
			html232 = html232 + "$" + wfbz + "$";
			resultHtml.append(html232).append(this.HTML_SPLIT_BZ);
			post3.releaseConnection();
		}
		
		return resultHtml.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (post != null) {
				post.releaseConnection();
			}
		}
		return "Exception";
	}

	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		String[] flights = arg0.split(this.HTML_SPLIT_BZ);
		String html = flights[0];
		
		ProcessResultInfo result = new ProcessResultInfo();
		if ("Exception".equals(html)) {
			result.setRet(false);
			result.setStatus(Constants.CONNECTION_FAIL);
			return result;	
		}
		if (html.contains("\"Result\":false")){
			result.setRet(true);
			result.setStatus(Constants.NO_RESULT);
			return result;	
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date retDate = format.parse(arg1.getRetDate());
			Date depDate = format.parse(arg1.getDepDate());
			List flights_qc = new ArrayList();
			List flights_fc = new ArrayList();
			
			for (int js = 1;js < flights.length;js++) {
				if (flights[js].indexOf("$ORIGIN$") > 0) {
					flights_qc.add(flights[js]);
				} else if (flights[js].indexOf("$DESTINATION") > 0) {
					flights_fc.add(flights[js]);
				}
			}
			
			// 取出去程航班信息
			List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			for (int i = 0; i < flights_qc.size(); i++){
				// 预先处理Html
				String sFlightInfo = (String)flights_qc.get(i);
				String flightHtml = sFlightInfo;
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 过滤文章内容中的html
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 去除空格、制表符、回车换行
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 去除空格
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 开始解析...
				String DepartureFlight = null;
				if (sFlightInfo.indexOf("Flight&nbsp;") >= 0) {
					DepartureFlight = StringUtils.substringBetween(sFlightInfo, "Flight&nbsp;", "</label>");
					DepartureFlight = StringUtils.substringAfter(DepartureFlight, ">");
				} else if (sFlightInfo.indexOf("Flight&nbsp") >= 0) {
					DepartureFlight = StringUtils.substringBetween(sFlightInfo, "Flight&nbsp", "</label>");
					DepartureFlight = StringUtils.substringAfter(DepartureFlight, ">");
				} else if (sFlightInfo.indexOf("Flight") >= 0) {
					DepartureFlight = sFlightInfo.substring(sFlightInfo.indexOf("Flight") + 6, sFlightInfo.indexOf("Flight") + 11);
				}
				
				String priceTxt = StringUtils.substringBetween(flightHtml,"Skatter/Avgifter:", "Moms");
				String Price = priceTxt.substring(0,priceTxt.length() - 3);
				String taxTxt = StringUtils.substringBetween(flightHtml,"Moms:", "(");
				String tax = taxTxt.substring(0,taxTxt.length() - 3);
				String moneyUnit = priceTxt.substring(priceTxt.length() - 3, priceTxt.length());
				
				// 设置baseFlight
				OneWayFlightInfo baseFlight = new OneWayFlightInfo();
				FlightDetail flightDetail = new FlightDetail();
				List<FlightSegement> segs = new ArrayList<FlightSegement>();
				
				//	设置flightDetail
				List<String> flightNoList = new ArrayList<String>();
				/*for (int zz = 0;zz < jtcs;zz++) {	//设置flightNo
					flightNoList.add(DepartureFlight);
				}*/
				flightNoList.add(DepartureFlight);
				flightDetail.setFlightno(flightNoList);
				Date date = format.parse(arg1.getDepDate());
				flightDetail.setDepdate(date);
				flightDetail.setMonetaryunit(moneyUnit);
				flightDetail.setPrice(Double.parseDouble(Price));
				flightDetail.setTax(Double.parseDouble(tax));
				flightDetail.setDepcity(arg1.getDep());
				flightDetail.setArrcity(arg1.getArr());
				flightDetail.setWrapperid(arg1.getWrapperid());
				baseFlight.setDetail(flightDetail);
				
				String deptimesTxt = StringUtils.substringBetween(flightHtml,"ng:", "Ankomst");
				String deptimes = deptimesTxt.substring(deptimesTxt.length() - 5);
				String arrtimesTxt = StringUtils.substringBetween(flightHtml,"Ankomst:", "Restid");
				String arrtimes = arrtimesTxt.substring(arrtimesTxt.length() - 5);
				
				FlightSegement seg = new FlightSegement();
				seg.setFlightno(DepartureFlight);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				String strDate = sdf.format(date);  
				seg.setDepDate(strDate);
				seg.setArrDate(strDate);
				seg.setDepairport(arg1.getDep());
				seg.setArrairport(arg1.getArr());
				seg.setDeptime(deptimes);
				seg.setArrtime(arrtimes);
				segs.add(seg);
				
				baseFlight.setInfo(segs);
				flightList.add(baseFlight);
			}
			
			// 取出返程航班信息
			List<RoundTripFlightInfo> roundTripFlightList = new ArrayList<RoundTripFlightInfo>();
			for (int i = 0; i < flights_fc.size(); i++){
				// 预先处理Html
				String sFlightInfo = (String)flights_qc.get(i);
				String flightHtml = sFlightInfo;
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 过滤文章内容中的html
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 去除空格、制表符、回车换行
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 去除空格
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 开始解析...
				String DepartureFlight = null;
				if (sFlightInfo.indexOf("Flight&nbsp;") >= 0) {
					DepartureFlight = StringUtils.substringBetween(sFlightInfo, "Flight&nbsp;", "</label>");
					DepartureFlight = StringUtils.substringAfter(DepartureFlight, ">");
				} else if (sFlightInfo.indexOf("Flight&nbsp") >= 0) {
					DepartureFlight = StringUtils.substringBetween(sFlightInfo, "Flight&nbsp", "</label>");
					DepartureFlight = StringUtils.substringAfter(DepartureFlight, ">");
				} else if (sFlightInfo.indexOf("Flight") >= 0) {
					DepartureFlight = sFlightInfo.substring(sFlightInfo.indexOf("Flight") + 6, sFlightInfo.indexOf("Flight") + 11);
				}
				String priceTxt = StringUtils.substringBetween(flightHtml,"Skatter/Avgifter:", "Moms");
				String Price = priceTxt.substring(0,priceTxt.length() - 3);
				String taxTxt = StringUtils.substringBetween(flightHtml,"Moms:", "(");
				String tax = taxTxt.substring(0,taxTxt.length() - 3);
				String moneyUnit = priceTxt.substring(priceTxt.length() - 3, priceTxt.length());
				
				// 设置baseFlight
				RoundTripFlightInfo baseFlight = new RoundTripFlightInfo();
				FlightDetail flightDetail = new FlightDetail();
				List<FlightSegement> segs = new ArrayList<FlightSegement>();
				
				//	设置flightDetail
				List<String> flightNoList = new ArrayList<String>();
				/*for (int zz = 0;zz < jtcs;zz++) {	//设置flightNo
					flightNoList.add(DepartureFlight);
				}*/
				flightNoList.add(DepartureFlight);
				flightDetail.setFlightno(flightNoList);
				Date date = format.parse(arg1.getRetDate());
				flightDetail.setDepdate(date);
				flightDetail.setMonetaryunit(moneyUnit);
				flightDetail.setPrice(Double.parseDouble(Price));
				flightDetail.setTax(Double.parseDouble(tax));
				flightDetail.setDepcity(arg1.getDep());
				flightDetail.setArrcity(arg1.getArr());
				flightDetail.setWrapperid(arg1.getWrapperid());
				baseFlight.setDetail(flightDetail);
				
				String deptimesTxt = StringUtils.substringBetween(flightHtml,"ng:", "Ankomst");
				String deptimes = deptimesTxt.substring(deptimesTxt.length() - 5);
				String arrtimesTxt = StringUtils.substringBetween(flightHtml,"Ankomst:", "Restid");
				String arrtimes = arrtimesTxt.substring(arrtimesTxt.length() - 5);
				
				FlightSegement seg = new FlightSegement();
				seg.setFlightno(DepartureFlight);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				String strDate = sdf.format(date);  
				seg.setDepDate(strDate);
				seg.setArrDate(strDate);
				seg.setDepairport(arg1.getDep());
				seg.setArrairport(arg1.getArr());
				seg.setDeptime(deptimes);
				seg.setArrtime(arrtimes);
				segs.add(seg);
				
				baseFlight.setInfo(segs);
				roundTripFlightList.add(baseFlight);
			}
						
			// 组合结果
			List<RoundTripFlightInfo> allList = new ArrayList<RoundTripFlightInfo>();
			for (int qc = 0;qc < flightList.size();qc++) {
				for (int fc = 0;fc < roundTripFlightList.size();fc++) {
					OneWayFlightInfo qcFlightInfo = flightList.get(qc);
					RoundTripFlightInfo fcFlightInfo = roundTripFlightList.get(fc);
					
					// 组合 
					RoundTripFlightInfo roundTripFlightInfo = new RoundTripFlightInfo();
					roundTripFlightInfo.setRetdepdate(fcFlightInfo.getDetail().getDepdate());
					roundTripFlightInfo.setRetflightno(fcFlightInfo.getDetail().getFlightno());
					roundTripFlightInfo.setRetinfo(fcFlightInfo.getInfo());
					roundTripFlightInfo.setOutboundPrice(qcFlightInfo.getDetail().getPrice());
					roundTripFlightInfo.setReturnedPrice(fcFlightInfo.getDetail().getPrice());
					roundTripFlightInfo.setInfo(qcFlightInfo.getInfo());
					FlightDetail qcFlightDetail = qcFlightInfo.getDetail();
					qcFlightDetail.setPrice(qcFlightDetail.getPrice() + fcFlightInfo.getDetail().getPrice());	//合并总价
					qcFlightDetail.setTax(qcFlightDetail.getTax() + fcFlightInfo.getDetail().getTax());	//合并总价
					roundTripFlightInfo.setDetail(qcFlightDetail);
					allList.add(roundTripFlightInfo);
				}
			}
			
			result.setRet(true);
			result.setStatus(Constants.SUCCESS);
			result.setData(allList);
			return result;
		} catch(Exception e){
			e.printStackTrace();
			result.setRet(false);
			//result.setStatus(Constants.PARSING_FAIL);
			result.setStatus("<textarea>" + html + "<textarea>");
			return result;
		}
	}
	
	public static void main(String[] args) {
		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("AGH");
		searchParam.setArr("BMA");
		searchParam.setDepDate("2014-08-01");
		searchParam.setRetDate("2014-08-08");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		searchParam.setWrapperid("Wrapper_gjsairob001");
		
		Wrapper_gjsairdc001 gjsairob001 = new  Wrapper_gjsairdc001();
		String html = gjsairob001.getHtml(searchParam);

		ProcessResultInfo result = new ProcessResultInfo();
		result = gjsairob001.process(html,searchParam);
		if(result.isRet() && result.getStatus().equals(Constants.SUCCESS))
		{
			List<BaseFlightInfo> flightList = (List<BaseFlightInfo>) result.getData();
			for (BaseFlightInfo in : flightList){
				System.out.println("************" + in.getInfo().toString());
				System.out.println("++++++++++++" + in.getDetail().toString());
			}
		}
		else
		{
			System.out.println(result.getStatus());
		}
	}
}
