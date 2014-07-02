import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;

import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.bean.search.OneWayFlightInfo;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.constants.Constants;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFGetMethod;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjdairlf001 implements QunarCrawler{
	 
	public BookingResult getBookingInfo(FlightSearchParam arg0) {
		String bookingUrlPre = "http://booking.flylaocentral.com/ttidotnet/transport/transportnetfo/lca/searchresult.aspx";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = null;
		try {
			depDate = format.parse(arg0.getDepDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(depDate);
		c.add(c.DATE, 1);
		String strAfterDate = format.format(c.getTime());
		String curDateStr[] = arg0.getDepDate().split("-");
		String aftDateStr[] = strAfterDate.split("-");
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
		String strDateDepDate = sdf.format(depDate);
		String strAftDate = sdf.format(c.getTime());
		
		map.put("id_langue","2");
		map.put("annee_aller",curDateStr[0]);
		map.put("mois_aller",curDateStr[1]);
		map.put("jour_aller",curDateStr[2]);
		map.put("annee_retour",aftDateStr[0]);
		map.put("mois_retour",aftDateStr[1]);
		map.put("jour_retour",aftDateStr[2]);
		map.put("choixmoteur","1");
		map.put("id_pointvente","1");
		map.put("TypeSearch","1");
		map.put("typetrajet","0");
		map.put("id_depart",this.airToSelCode(arg0.getDep()));
		map.put("id_arrivee",this.airToSelCode(arg0.getArr()));
		map.put("RETURNDATE",strAftDate);
		map.put("DEPARTDATE",strDateDepDate);
		map.put("TypePassager1u1000r0b1","1");
		map.put("TypePassager2u1000r0b1","0");
		map.put("TypePassager3u0r0b1","0");
		map.put("CABINCLASS","3");
		map.put("CodeIsoDeviseClient","USD");
		
		bookingInfo.setContentType("UTF-8");
		bookingInfo.setInputs(map);		
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;
	}

	public String getHtml(FlightSearchParam arg0) {
		QFPostMethod post = null;
		try
		{
		// get all query parameters from the url set by wrapperSearchInterface
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		//httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		// 通过代理访问
		httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = format.parse(arg0.getDepDate());
		Calendar c = Calendar.getInstance();
		c.setTime(depDate);
		c.add(c.DATE, 1);
		String strAfterDate = format.format(c.getTime());
		String curDateStr[] = arg0.getDepDate().split("-");
		String aftDateStr[] = strAfterDate.split("-");
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
		String strDateDepDate = sdf.format(depDate);
		String strAftDate = sdf.format(c.getTime());
		
		//首次请求
		//QFGetMethod post81 = new QFGetMethod("http://booking.flylaocentral.com/ttidotnet/transport/transportnetfo/lca/searchresult.aspx");
		StringBuffer sbUrl = new StringBuffer("http://booking.flylaocentral.com/ttidotnet/transport/transportnetfo/lca/searchresult.aspx?");
		NameValuePair[] names81 = {
				new NameValuePair("id_langue","2"),
				new NameValuePair("annee_aller",curDateStr[0]),
				new NameValuePair("mois_aller",curDateStr[1]),
				new NameValuePair("jour_aller",curDateStr[2]),
				new NameValuePair("annee_retour",aftDateStr[0]),
				new NameValuePair("mois_retour",aftDateStr[1]),
				new NameValuePair("jour_retour",aftDateStr[2]),
				new NameValuePair("choixmoteur","1"),
				new NameValuePair("id_pointvente","1"),
				new NameValuePair("TypeSearch","1"),
				new NameValuePair("typetrajet","0"),
				new NameValuePair("id_depart",this.airToSelCode(arg0.getDep())),
				new NameValuePair("id_arrivee",this.airToSelCode(arg0.getArr())),
				new NameValuePair("RETURNDATE",strAftDate),
				new NameValuePair("DEPARTDATE",strDateDepDate),
				new NameValuePair("TypePassager1u1000r0b1","1"),
				new NameValuePair("TypePassager2u1000r0b1","0"),
				new NameValuePair("TypePassager3u0r0b1","0"),
				new NameValuePair("CABINCLASS","3"),
				new NameValuePair("CodeIsoDeviseClient","USD")
	    };
		//post81.setRequestBody(names81);
		for (int i = 0;i < names81.length;i++) {
			sbUrl.append(names81[i].getName() + "=" + names81[i].getValue());
			if (i < names81.length - 1) {
				sbUrl.append("&");
			}
		}
		QFGetMethod post81 = new QFGetMethod(sbUrl.toString());
		post81.setFollowRedirects(false);
		post81.setRequestHeader("Referer", "http://www.flylaocentral.com/flightsearch.aspx");
		httpClient.executeMethod(post81);
		String cookie = StringUtils.join(httpClient.getState().getCookies(),"; ");
		String html83 = post81.getResponseBodyAsString();
		
		// 处理302及cookie
		int code = post81.getStatusCode();
		String url = "";
		if(code == HttpStatus.SC_MOVED_TEMPORARILY || code == HttpStatus.SC_MOVED_PERMANENTLY){
			Header location = post81.getResponseHeader("Location");
			if(location !=null){
				url = location.getValue();
				if(!url.startsWith("http")){
					url = post81.getURI().getScheme() + "://" + post81.getURI().getHost() + (post81.getURI().getPort()==-1?"":(":"+post81.getURI().getPort())) + url;
				}
			}else{
				//return;
			}
		}
		
		QFGetMethod get = new QFGetMethod(url);
		//get.setFollowRedirects(false);
		httpClient.getState().clearCookies();
		get.addRequestHeader("Cookie",cookie);
		get.setRequestHeader("Referer", "http://www.flylaocentral.com/flightsearch.aspx");
		httpClient.executeMethod(get);
		return get.getResponseBodyAsString();	
		
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
		String html = arg0;
		
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
		
		// 取出航班信息
		try {
			SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
			Date depDate = sdf0.parse(arg1.getDepDate());
			SimpleDateFormat sdfbz = new SimpleDateFormat("yyyy/MM/dd"); 
			String flightbz = sdfbz.format(depDate);
			String html2 = StringUtils.substringBetween(html,flightbz ,"</li>");
			String[] flights = html2.split("<div class=\"flightStatus\">");
			
			List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			for (int i = 1; i < flights.length; i++){
				// 预先处理Html
				String flightHtml = flights[i];
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 过滤文章内容中的html
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 去除空格、制表符、回车换行
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 去除空格
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 开始解析...
				if (flightHtml.contains("Noflight")) {
					result.setRet(true);
					result.setStatus(Constants.NO_RESULT);
					return result;
				}
				
				String[] flightTxts = flightHtml.split("Arrival");
				int jtcs = flightTxts.length - 1;	//	经停次数
				String DepartureFlight = StringUtils.substringBetween(flightHtml, "DepartureFlight", "Departure");
				DepartureFlight = "0B" + DepartureFlight;	// 补齐航空公司二字码
				
				String priceTxt = StringUtils.substringAfterLast(flightHtml,"Price");
				String Price =  priceTxt.substring(0, priceTxt.length() - 3);
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
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(arg1.getDepDate());
				flightDetail.setDepdate(date);
				flightDetail.setMonetaryunit(moneyUnit);
				
				flightDetail.setPrice(Double.parseDouble(Price));
				flightDetail.setDepcity(arg1.getDep());
				flightDetail.setArrcity(arg1.getArr());
				flightDetail.setWrapperid(arg1.getWrapperid());
				baseFlight.setDetail(flightDetail);
				
				// 设置FlightSegement
				String depairports[] = new String[jtcs];
				String deptimes[] = new String[jtcs];
				String arrairports[] = new String[jtcs];
				String arrtimes[] = new String[jtcs];
				for (int zz = 0;zz < flightTxts.length;zz++) {	//设置Departures、Arrivals
					if (zz == 0) {	//第一段，取出首飞机场、时间
						String temp = StringUtils.substringAfterLast(flightTxts[zz],"Departure");
						depairports[zz] = temp.substring(0, 3);
						deptimes[zz] = temp.substring(3);
					} else if (zz == flightTxts.length - 1) {	//最后一段，取出最终到达机场、时间
						String temp = StringUtils.substringBefore(flightTxts[zz],"Tripduration");
						arrairports[zz - 1] = temp.substring(0, 3);
						arrtimes[zz - 1] = temp.substring(3);
					} else {	// 中间段，取出中转到达、起飞机场、时间
						String temp1 = StringUtils.substringBefore(flightTxts[zz],"Departure");
						String temp2 = StringUtils.substringAfter(flightTxts[zz],"Departure");
						arrairports[zz - 1] = temp1.substring(0, 3);
						arrtimes[zz - 1] = temp1.substring(3);
						depairports[zz] = temp2.substring(0, 3);
						deptimes[zz] = temp2.substring(3);
					}
				}
				
				// 经停不处理
				/*for (int zz = 0;zz < jtcs;zz++) {
					FlightSegement seg = new FlightSegement();
					seg.setFlightno(DepartureFlight);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
					String strDate = sdf.format(date);  
					seg.setDepDate(strDate);
					seg.setArrDate(strDate);
					seg.setDepairport(depairports[zz]);
					seg.setArrairport(arrairports[zz]);
					seg.setDeptime(deptimes[zz]);
					seg.setArrtime(arrtimes[zz]);
					segs.add(seg);
				}*/
				
				FlightSegement seg = new FlightSegement();
				seg.setFlightno(DepartureFlight);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				String strDate = sdf.format(date);  
				seg.setDepDate(strDate);
				seg.setArrDate(strDate);
				seg.setDepairport(arg1.getDep());
				seg.setArrairport(arg1.getArr());
				seg.setDeptime(deptimes[0]);
				seg.setArrtime(arrtimes[jtcs - 1]);
				segs.add(seg);
				
				baseFlight.setInfo(segs);
				flightList.add(baseFlight);
			}	
			result.setRet(true);
			result.setStatus(Constants.SUCCESS);
			result.setData(flightList);
			return result;
		} catch(Exception e){
			e.printStackTrace();
			result.setRet(false);
			//result.setStatus(Constants.PARSING_FAIL);
			result.setStatus("<textarea>" + html + "<textarea>" + "<textarea>" + e.toString() + "<textarea>");
			
			return result;
		}
	}
	
	private static String airToSelCode(String air) {
		HashMap selCodeMap = new HashMap();
		selCodeMap.put("BKK","5866");
		selCodeMap.put("LPQ","9165");
		selCodeMap.put("VTE","12796");
		String code = selCodeMap.get(air).toString(); 
		
		return code;
	}
	
	public static void main(String[] args) {
		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("VTE");
		searchParam.setArr("LPQ");
		searchParam.setDepDate("2014-07-14");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		searchParam.setWrapperid("Wrapper_gjdairob001");
		
		Wrapper_gjdairlf001 gjdairob001 = new  Wrapper_gjdairlf001();
		String html = gjdairob001.getHtml(searchParam);
		//System.out.println(html);

		ProcessResultInfo result = new ProcessResultInfo();
		result = gjdairob001.process(html,searchParam);
		if(result.isRet() && result.getStatus().equals(Constants.SUCCESS))
		{
			List<OneWayFlightInfo> flightList = (List<OneWayFlightInfo>) result.getData();
			for (OneWayFlightInfo in : flightList){
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
