
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
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
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjdairkk001 implements QunarCrawler{

	/**
	 * @param args
	 */
public String getHtml(FlightSearchParam arg0) {
		
		QFPostMethod post = null;
		try
		{
		// get all query parameters from the url set by wrapperSearchInterface
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		// 通过代理访问
		httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));		
				
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = format.parse(arg0.getDepDate());
		Date retDate = format.parse(arg0.getRetDate());
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");  
		String strDateDepDate = sdf.format(depDate);
		String strDateRetDate = sdf.format(retDate);

		
		//String depdate=arg0.getDepDate().replaceAll("-","")+"0000";	
		//String retdate=arg0.getRetDate().replaceAll("-","")+"0000";	
		post = new QFPostMethod("https://online.atlasjet.com/AtlasOnline/availability.kk");		
		NameValuePair[] names = {
				//new NameValuePair("FromCode","ADA"),
				new NameValuePair("from",arg0.getDep()),
				new NameValuePair("to",arg0.getArr()),
				new NameValuePair("lang","EN"),
				new NameValuePair("direction","1"),
				new NameValuePair("depdate",strDateDepDate),
				new NameValuePair("retdate",strDateRetDate),
				new NameValuePair("adult","1"),
				new NameValuePair("yp","0"),
				new NameValuePair("chd","0"),
				new NameValuePair("inf","0"),
				new NameValuePair("sc","0"),
				new NameValuePair("stu","0"),
				new NameValuePair("tsk","0"),
				new NameValuePair("refid","www.atlasjet.com"),
				new NameValuePair("paramstatus","1"),
				new NameValuePair("openjaw",""),
				new NameValuePair("bannerSize","200x200")
		};
		post.setRequestBody(names);
		//String cookie = StringUtils.join(httpClient.getState().getCookies(),"; ");	
		//httpClient.getState().clearCookies();
		//post.addRequestHeader("Cookie","superT_v1=1404095351754.181863%3A1%3A1%3A1; superT_s1=1404095351757.26231; __utma=166957934.233118461.1404095355.1404095355.1404095355.1; __utmb=166957934.12.8.1404095421275; __utmc=166957934; __utmz=166957934.1404095355.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
		post.setRequestHeader("Referer", "http://www.atlasjet.com/MainPage");
		//post.getParams().setContentCharset("UTF-8");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		httpClient.executeMethod(post);	
		
		
		//System.out.println(post.getResponseBodyAsString().toString());
		return post.getResponseBodyAsString();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (post != null) {
				post.releaseConnection();
			}
		}
		return "Exception";
		    
	}

	@Override
	public BookingResult getBookingInfo(FlightSearchParam arg0) {
		String bookingUrlPre = "https://online.atlasjet.com/AtlasOnline/passenger.kk";
		BookingResult bookingResult = new BookingResult();
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = null;
		Date retDate = null;
		try {
			depDate = format.parse(arg0.getDepDate());
			retDate = format.parse(arg0.getRetDate());
		}	catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");  
		String strDateDepDate = sdf.format(depDate);
		//String strDateRetDate = sdf.format(retDate);
		
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("historyCookieId			","																						");
		map.put("selectedPlan0Class","20140719ATLJETADAADBKK93128#YT#09:05#119.0");
		map.put("selectedPlan1Class","");
		map.put("selectedPlan2Class","");
		map.put("selectedPlan3Class","");
		map.put("selectedPlan4Class","");
		map.put("selectedPlan5Class","");
		map.put("flightPlan0","20140719ATLJETADAADBKK93128#YT#09:05#119.0");
		map.put("isAwardJetmilPage","false");
		map.put("direction","1");
		map.put("from",arg0.getDep());
		map.put("to",arg0.getArr());
		map.put("depdate",strDateDepDate);
		map.put("retdate",strDateDepDate);
		map.put("adult","1");
		map.put("yp","0");
		map.put("chd","0");
		map.put("inf","0");
		map.put("stu","0");
		map.put("tsk","0");
		map.put("sc","0");
		map.put("wherefrom","searchpage");
		map.put("selectedTo",arg0.getArr());
		map.put("selectedOpenJaw","");
		map.put("currentDeptDate",strDateDepDate);
		map.put("currentRetDate",strDateDepDate);
		map.put("fromDesc",arg0.getDep() + "NA");
		map.put("toDesc",arg0.getArr());
		map.put("openjawDesc","");
		map.put("curr","null");
		map.put("totalBasePrice","115TL");
		map.put("totalBasePriceAsTL","115");
		map.put("totalTaxPrice","54TL");
		map.put("totalServiceFeePrice","10TL");
		map.put("totalPrice","179TL");
		map.put("totalPriceAsTL","(179TL)");
		map.put("totalPassengerCount","1");
		map.put("totalJetmilPoint","");
		
		bookingInfo.setContentType("UTF-8");
		bookingInfo.setInputs(map);
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;
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
		
		// 鍙栧嚭鑸彮淇℃伅
		//String html2 = StringUtils.substringBetween(html,"<tr class=\"showContent\">" ,"</tr>");
		String html2 = StringUtils.substringBetween(html,"<tbody>" ,"</tbody>");
		String[] flights = html2.split("<tr class=\"showContent\">");
		
		try {			
			List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			String Price = "";
			String moneyUnit =  "";
			for (int i = 1; i < flights.length; i++){
				// 棰勫厛澶勭悊Html
				String flightHtml = flights[i];
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 杩囨护鏂囩珷鍐呭涓殑html
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 鍘婚櫎绌烘牸銆佸埗琛ㄧ銆佸洖杞︽崲琛?
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 鍘婚櫎绌烘牸
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 寮€濮嬭В鏋?..
				String DepartureFlight = StringUtils.substringBetween(flights[i], "<td  style=\"\" title=\"\">", "</td>");
				DepartureFlight = DepartureFlight.replaceAll("\\s*|\t|\r|\n", "");
				int zzbz = StringUtils.indexOf(flights[i], "rowspan=\"");	// 涓浆鏍囧織
				if (zzbz >= 0) {
					String temp = StringUtils.substringBetween(flights[i], "rowspan=\"", "\"");
					zzbz = Integer.parseInt(temp);
				}
				if (zzbz > 0) {	// 鏂拌埅鐝?
					String flightHtmlPrice = StringUtils.substringBetween(flights[i], "<label", "label"); // 鎴彇浠锋牸
					flightHtmlPrice = StringUtils.substringBetween(flightHtmlPrice, ">","<");
					Pattern pt=Pattern.compile("([0-9]|\\.|\\-)*");
					Matcher m=pt.matcher(flightHtmlPrice);
					m.find();
					Price=m.group();
					moneyUnit =  flightHtmlPrice.substring(Price.length());
					moneyUnit = moneyUnit.replaceAll("\\s*|\t|\r|\n", "");
				} else {	// 涓浆鑸彮
					// 閲囩敤涓婁竴鐝环鏍?
				}
				
				// 璁剧疆baseFlight
				if (zzbz > 0) {
					OneWayFlightInfo baseFlight = new OneWayFlightInfo();
					FlightDetail flightDetail = new FlightDetail();
					List<FlightSegement> segs = new ArrayList<FlightSegement>();
					
					//	璁剧疆flightDetail
					List<String> flightNoList = new ArrayList<String>();
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
					
					// 璁剧疆FlightSegement
					String deptimes = flightHtml.substring(0, 5);
					String arrtimes = flightHtml.substring(5, 10);
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
				} else {	// 涓浆鑸彮
					OneWayFlightInfo baseFlight = flightList.get(flightList.size() - 1);
					FlightDetail flightDetail = baseFlight.getDetail();
					List<FlightSegement> segs = baseFlight.getInfo();
					
					//	璁剧疆flightDetail
					List<String> flightNoList = flightDetail.getFlightno();
					flightNoList.add(DepartureFlight);
					flightDetail.setFlightno(flightNoList);
					baseFlight.setDetail(flightDetail);
					
					// 璁剧疆FlightSegement
					String deptimes = flightHtml.substring(0, 5);
					String arrtimes = flightHtml.substring(5, 10);
					FlightSegement seg = new FlightSegement();
					seg.setFlightno(DepartureFlight);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = format.parse(arg1.getDepDate());
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
				}
				
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

	/*public static void main(String[] args) {
		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("ADA");
		searchParam.setArr("IST");
		searchParam.setDepDate("2014-07-24");
		searchParam.setRetDate("2014-07-24");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		searchParam.setWrapperid("Wrapper_gjdairkk001");
		
		Wrapper_gjdairkk001 gjdairkk001 = new  Wrapper_gjdairkk001();
		String html = gjdairkk001.getHtml(searchParam);
	    System.out.println(html);

		ProcessResultInfo result = new ProcessResultInfo();
		result = gjdairkk001.process(html,searchParam);
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
	}*/	
}