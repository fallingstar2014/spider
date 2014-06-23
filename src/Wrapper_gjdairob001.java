import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.qunar.qfwrapper.util.QFGetMethod;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjdairob001 implements QunarCrawler{
	 
	public BookingResult getBookingInfo(FlightSearchParam arg0) {

		String bookingUrlPre = "https://open.maxitours.be/SelectLowFareCalendar.aspx";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map.put("__EVENTTARGET			","DepartureRadioClick	");
		map.put("__EVENTARGUMENT","19.07.2014|0B~8002~~~CND~07/19/201400:10~OTP~07/19/201403:40^0B~131~~~OTP~07/19/201408:25~LTN~07/19/201409:50|0~S~WEB06~0001~~~X");
		map.put("__LASTFOCUS","");
		map.put("__VIEWSTATE","/wEPDwUKLTE0NDAxNTI5OQ9kFgJmD2QWAgIBD2QWHgIBDw8WAh4LTmF2aWdhdGVVcmwFDn4vRGVmYXVsdC5hc3B4ZGQCAg8WAh4EVGV4dAVAPGRpdiBjbGFzcz0iZW4tVVMiPiZuYnNwOzwvZGl2PjxkaXYgY2xhc3M9ImxhbmdzZXBhcmF0b3IiPjwvZGl2PmQCBQ9kFgJmDxYCHglpbm5lcmh0bWwFjgI8ZGl2IGNsYXNzPSJuZXdzc2xpZGVyY29udGVudCIgaWQ9Im5ld3NzbGlkZXIiPjxkaXYgaWQ9Im5ld3NzZWN0aW9uLTEiIGNsYXNzPSJuZXdzc2VjdGlvbiB1cHBlciI+PHN0cm9uZz4xMy4wNi4yMDE0PC9zdHJvbmc+IC0gPGEgdGFyZ2V0PSJfdG9wIiBjbGFzcz0iYmx1ZTEyIiBocmVmPSJodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1V0aWwtSW5mb3MvTGF0ZXN0LU5ld3MiPkJsdWUgQmlzdHJvIC0gY3VsaW5hcnkgZGVsaWdodHMgb25ib2FyZDwvYT48L2Rpdj48L2Rpdj5kAggPZBZCZg8PFgQfAAUlaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9GaXJzdC1QYWdlLx4GVGFyZ2V0BQRfdG9wZGQCAg8PFgQfAAU3aHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9EZXN0aW5hdGlvbnMvRGVzdGluYXRpb25zLU1hcB8DBQRfdG9wZGQCAw8PFgQfAAU3aHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9EZXN0aW5hdGlvbnMvRmxpZ2h0cy1TY2hlZHVsZR8DBQRfdG9wZGQCBA8PFgQfAAVAaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9EZXN0aW5hdGlvbnMvRGVzdGluYXRpb25zLUFuZC1BaXJwb3J0cx8DBQRfdG9wZGQCBg8PFgQfAAU0aHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9TZXJ2aWNlcy9TbWFydC1DaGVjay1pbi1FTh8DBQRfdG9wZGQCBw8PFgQfAAU1aHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9TZXJ2aWNlcy9Hcm91cC1SZXNlcnZhdGlvbnMfAwUEX3RvcGRkAggPDxYEHwAFKmh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vU2VydmljZXMvQ2hhcnRlch8DBQRfdG9wZGQCCQ8PFgIfAAUtaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9TZXJ2aWNlcy9FLVNlcnZpY2VzZGQCCg8PFgQfAAUoaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9TZXJ2aWNlcy9DYXJnbx8DBQRfdG9wZGQCCw8PFgQfAAUwaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9TZXJ2aWNlcy9BaXItQW1idWxhbmNlHwMFBF90b3BkZAIMDw8WBB8ABStodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1NlcnZpY2VzL0UtQm9yZGVyHwMFBF90b3BkZAIODw8WBB8ABS5odHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1NlcnZpY2VzL0FkdmVydGlzaW5nHwMFBF90b3BkZAIPDw8WAh8ABTVodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1V0aWwtSW5mb3MvVHJhdmVsLUluc3VyYW5jZWRkAhAPDxYCHwAFKWh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vU2VydmljZXMvQ29sZXRlZGQCEQ8PFgQfAAUvaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9Db3Jwb3JhdGUtUHJvZ3JhbS9OZXcfAwUEX3RvcGRkAhIPDxYEHwAFKGh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vQWdlbmNpZXMvSW5kZXgfAwUEX3RvcGRkAhQPDxYEHwAFMGh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vVXRpbC1JbmZvcy9MYXRlc3QtTmV3cx8DBQRfdG9wZGQCFQ8PFgQfAAUpaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9PZmZlcnMtSGlzdG9yeS8fAwUEX3RvcGRkAhYPDxYEHwAFMGh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vVXRpbC1JbmZvcy9DYWxsLUNlbnRlch8DBQRfdG9wZGQCFw8PFgQfAAUxaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9VdGlsLUluZm9zL1RyYXZlbC1HdWlkZR8DBQRfdG9wZGQCGA8PFgQfAAUuaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9DYXJyaWFnZS1Db25kaXRpb25zLx8DBQRfdG9wZGQCGQ8PFgQfAAUvaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9VdGlsLUluZm9zL05ld3NsZXR0ZXIfAwUEX3RvcGRkAhoPDxYEHwMFBl9ibGFuax8ABSBodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL3Jzcy1lbmRkAhsPDxYCHwAFKGh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vVXRpbC1JbmZvcy9Cb3hkZAIdDw8WBB8ABSpodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL0NvbXBhbnkvQWJvdXQtVXMfAwUEX3RvcGRkAh4PDxYEHwAFJ2h0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vQ29tcGFueS9DcmV3cx8DBQRfdG9wZGQCHw8PFgQfAAUnaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9Db21wYW55L0ZsZWV0HwMFBF90b3BkZAIgDw8WBB8ABShodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL0NvbXBhbnkvU2FmZXR5HwMFBF90b3BkZAIhDw8WBB8ABSJodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL0NhcmVlcnMvHwMFBF90b3BkZAIiDw8WBB8ABSpodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1V0aWwtSW5mb3MvUHJlc3MfAwUEX3RvcGRkAiMPDxYEHwAFM2h0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vVXRpbC1JbmZvcy9Ub3AtTWFuYWdlbWVudB8DBQRfdG9wZGQCJA8PFgQfAAUiaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9Db250YWN0Lx8DBQRfdG9wZGQCJQ8PFgIfAAUtaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9vbmxpbmUvSG90ZWwtSG9zdGVsZGQCCQ9kFgQCAQ9kFgJmD2QWBAIBDw8WBB4ORmxleGlibGVEYXlzTm8CAh4KX3JvdW5kdHJpcGhkFgJmD2QWAmYPZBYcZg8PFgIfAAUtaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9vbmxpbmUvSG90ZWwtSG9zdGVsZGQCAQ8PFgIfAAUxaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9vbmxpbmUvQ2FyLVJlc2VydmF0aW9uc2RkAgIPDxYCHwAFIGh0dHA6Ly9ibHVlYWlyLnBhcmt2aWEuY29tL2VuLUdCZGQCAw8QDxYCHgdDaGVja2VkaGRkZGQCBA8QDxYCHwZnZGRkZAIGDxAPFgYeDkRhdGFWYWx1ZUZpZWxkBQVWYWx1ZR4NRGF0YVRleHRGaWVsZAUDS2V5HgtfIURhdGFCb3VuZGdkEBUjCVNlbGVjdC4uLidBbnRhbHlhIC0gQW50YWx5YSBJbnRlcm5hdGlvbmFsIEFpcnBvcnQdQmFjYXUgLSBHZW9yZ2UgRW5lc2N1IEFpcnBvcnQgQmFyY2Vsb25hIC0gRWwgUHJhdCAtIFRlcm1pbmFsIDIVQm9kcnVtIC0gTWlsYXMgQm9kcnVtEUJvbG9nbmEgLSBNYXJjb25pGUJyYXNvdiAtIFRyYW5zZmVyIGF1dG9jYXIpQnJ1c3NlbHMgLSBCcnV4ZWxsZXMgQWlycG9ydCAtIFRlcm1pbmFsIEITQnVjaGFyZXN0IC0gT3RvcGVuaRZDYXRhbmlhIC0gRm9udGFuYXJvc3NhGkNsdWogLSBBdnJhbSBJYW5jdSBBaXJwb3J0GENvbnN0YW50YSAtIEJ1cyBUcmFuc2ZlcjJDb3JmdSAtIElvYW5uaXMgS2Fwb2Rpc3RyaWFzIEludGVybmF0aW9uYWwgQWlycG9ydCREdWJsaW4gLSBEdWJsaW4gQWlycG9ydCAtIFRlcm1pbmFsIDElSGVyYWtsaW9uIC0gTmlrb3MgS2F6YW50emFraXMgQWlycG9ydBNJYXNpIC0gSWFzaSBBaXJwb3J0DUliaXphIC0gSWJpemEQS29sbiAtIEtvbG4gQm9ubhlMYXJuYWNhIC0gTGFybmFjYSBBaXJwb3J0IUxlZmthZGEgLSBBa3Rpb24gTmF0aW9uYWwgQWlycG9ydBZMb25kb24gKEx1dG9uKSAtIEx1dG9uG01hZHJpZCAtIEJhcmFqYXMgVGVybWluYWwgMSVNYWxhZ2EgIC0gTWFsYWdhIEFpcnBvcnQgLSBUZXJtaW5hbCAyH01pbGFuIChCZXJnYW1vKSAtIE9yaW8gYWwgU2VyaW8XTmFwbGVzIC0gTmFwb2xpIEFpcnBvcnQgTmljZSAtIEPDtHRlIGQnQXp1ciAtIFRlcm1pbmFsIDEbUGFyaXMgKEJlYXV2YWlzKSAtIEJlYXV2YWlzG1JvbWUgLSBGaXVtaWNpbm8gVGVybWluYWwgMhZTYWxvbmljIC0gVGhlc3NhbG9uaWtpDVNpYml1IC0gU2liaXUqU3R1dHRnYXJ0IC0gU3R1dHRnYXJ0IEFpcnBvcnQgLSBUZXJtaW5hbCA0HlRlbCBBdml2IC0gQmVuIEd1cml0b24gQWlycG9ydBZUdXJpbiBDdW5lbyAtIE9MSU1QSUNBH1ZhbGVuY2lhIC0gTWFuaXNlcyAtIFRlcm1pbmFsIDIlWmFreW50aG9zIC0gRGlvbnlzaW9zIFNvbG9tb3MgQWlycG9ydBUjAi0xA0FZVANCQ00DQkNOA0JKVgNCTFEDQlJWA0JSVQNPVFADQ1RBA0NMSgNDTkQDQ0ZVA0RVQgNIRVIDSUFTA0lCWgNDR04DTENBA1BWSwNMVE4DTUFEA0FHUANCR1kDTkFQA05DRQNCVkEDRkNPA1NLRwNTQloDU1RSA1RMVgNDVUYDVkxDA1pUSBQrAyNnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZxYBAgtkAggPEA8WBh8HBQVWYWx1ZR8IBQNLZXkfCWdkEBUTCVNlbGVjdC4uLiBCYXJjZWxvbmEgLSBFbCBQcmF0IC0gVGVybWluYWwgMhFCb2xvZ25hIC0gTWFyY29uaSlCcnVzc2VscyAtIEJydXhlbGxlcyBBaXJwb3J0IC0gVGVybWluYWwgQhZDYXRhbmlhIC0gRm9udGFuYXJvc3NhJER1YmxpbiAtIER1YmxpbiBBaXJwb3J0IC0gVGVybWluYWwgMRBLb2xuIC0gS29sbiBCb25uGUxhcm5hY2EgLSBMYXJuYWNhIEFpcnBvcnQWTG9uZG9uIChMdXRvbikgLSBMdXRvbhtNYWRyaWQgLSBCYXJhamFzIFRlcm1pbmFsIDElTWFsYWdhICAtIE1hbGFnYSBBaXJwb3J0IC0gVGVybWluYWwgMh9NaWxhbiAoQmVyZ2FtbykgLSBPcmlvIGFsIFNlcmlvF05hcGxlcyAtIE5hcG9saSBBaXJwb3J0IE5pY2UgLSBDw7R0ZSBkJ0F6dXIgLSBUZXJtaW5hbCAxG1BhcmlzIChCZWF1dmFpcykgLSBCZWF1dmFpcxtSb21lIC0gRml1bWljaW5vIFRlcm1pbmFsIDIqU3R1dHRnYXJ0IC0gU3R1dHRnYXJ0IEFpcnBvcnQgLSBUZXJtaW5hbCA0FlR1cmluIEN1bmVvIC0gT0xJTVBJQ0EfVmFsZW5jaWEgLSBNYW5pc2VzIC0gVGVybWluYWwgMhUTAi0xA0JDTgNCTFEDQlJVA0NUQQNEVUIDQ0dOA0xDQQNMVE4DTUFEA0FHUANCR1kDTkFQA05DRQNCVkEDRkNPA1NUUgNDVUYDVkxDFCsDE2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dkZAINDw8WAh4HVmlzaWJsZWhkFgYCAw8PFgIeB0VuYWJsZWRoZGQCBQ8PFgIfC2hkZAIHDw8WAh8BBQoyNC4wNi4yMDE0ZGQCDg8QZA8WCmYCAQICAgMCBAIFAgYCBwIIAgkWChAFATEFATFnEAUBMgUBMmcQBQEzBQEzZxAFATQFATRnEAUBNQUBNWcQBQE2BQE2ZxAFATcFATdnEAUBOAUBOGcQBQE5BQE5ZxAFAjEwBQIxMGdkZAIPDxBkDxYGZgIBAgICAwIEAgUWBhAFATAFATBnEAUBMQUBMWcQBQEyBQEyZxAFATMFATNnEAUBNAUBNGcQBQE1BQE1Z2RkAhEPEGQPFgZmAgECAgIDAgQCBRYGEAUBMAUBMGcQBQExBQExZxAFATIFATJnEAUBMwUBM2cQBQE0BQE0ZxAFATUFATVnZGQCFA8PFgIfAAUvaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9UZXJtcy1BbmQtQ29uZGl0aW9ucy9kZAIcDxAPFgYfBwUFVmFsdWUfCAUDS2V5HwlnZBAVIwlTZWxlY3QuLi4nQW50YWx5YSAtIEFudGFseWEgSW50ZXJuYXRpb25hbCBBaXJwb3J0HUJhY2F1IC0gR2VvcmdlIEVuZXNjdSBBaXJwb3J0IEJhcmNlbG9uYSAtIEVsIFByYXQgLSBUZXJtaW5hbCAyFUJvZHJ1bSAtIE1pbGFzIEJvZHJ1bRFCb2xvZ25hIC0gTWFyY29uaRlCcmFzb3YgLSBUcmFuc2ZlciBhdXRvY2FyKUJydXNzZWxzIC0gQnJ1eGVsbGVzIEFpcnBvcnQgLSBUZXJtaW5hbCBCE0J1Y2hhcmVzdCAtIE90b3BlbmkWQ2F0YW5pYSAtIEZvbnRhbmFyb3NzYRpDbHVqIC0gQXZyYW0gSWFuY3UgQWlycG9ydBhDb25zdGFudGEgLSBCdXMgVHJhbnNmZXIyQ29yZnUgLSBJb2FubmlzIEthcG9kaXN0cmlhcyBJbnRlcm5hdGlvbmFsIEFpcnBvcnQkRHVibGluIC0gRHVibGluIEFpcnBvcnQgLSBUZXJtaW5hbCAxJUhlcmFrbGlvbiAtIE5pa29zIEthemFudHpha2lzIEFpcnBvcnQTSWFzaSAtIElhc2kgQWlycG9ydA1JYml6YSAtIEliaXphEEtvbG4gLSBLb2xuIEJvbm4ZTGFybmFjYSAtIExhcm5hY2EgQWlycG9ydCFMZWZrYWRhIC0gQWt0aW9uIE5hdGlvbmFsIEFpcnBvcnQWTG9uZG9uIChMdXRvbikgLSBMdXRvbhtNYWRyaWQgLSBCYXJhamFzIFRlcm1pbmFsIDElTWFsYWdhICAtIE1hbGFnYSBBaXJwb3J0IC0gVGVybWluYWwgMh9NaWxhbiAoQmVyZ2FtbykgLSBPcmlvIGFsIFNlcmlvF05hcGxlcyAtIE5hcG9saSBBaXJwb3J0IE5pY2UgLSBDw7R0ZSBkJ0F6dXIgLSBUZXJtaW5hbCAxG1BhcmlzIChCZWF1dmFpcykgLSBCZWF1dmFpcxtSb21lIC0gRml1bWljaW5vIFRlcm1pbmFsIDIWU2Fsb25pYyAtIFRoZXNzYWxvbmlraQ1TaWJpdSAtIFNpYml1KlN0dXR0Z2FydCAtIFN0dXR0Z2FydCBBaXJwb3J0IC0gVGVybWluYWwgNB5UZWwgQXZpdiAtIEJlbiBHdXJpdG9uIEFpcnBvcnQWVHVyaW4gQ3VuZW8gLSBPTElNUElDQR9WYWxlbmNpYSAtIE1hbmlzZXMgLSBUZXJtaW5hbCAyJVpha3ludGhvcyAtIERpb255c2lvcyBTb2xvbW9zIEFpcnBvcnQVIwItMQNBWVQDQkNNA0JDTgNCSlYDQkxRA0JSVgNCUlUDT1RQA0NUQQNDTEoDQ05EA0NGVQNEVUIDSEVSA0lBUwNJQloDQ0dOA0xDQQNQVksDTFROA01BRANBR1ADQkdZA05BUANOQ0UDQlZBA0ZDTwNTS0cDU0JaA1NUUgNUTFYDQ1VGA1ZMQwNaVEgUKwMjZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2cWAWZkAh0PEA8WBh8HBQVWYWx1ZR8IBQNLZXkfCWdkEBUjCVNlbGVjdC4uLidBbnRhbHlhIC0gQW50YWx5YSBJbnRlcm5hdGlvbmFsIEFpcnBvcnQdQmFjYXUgLSBHZW9yZ2UgRW5lc2N1IEFpcnBvcnQgQmFyY2Vsb25hIC0gRWwgUHJhdCAtIFRlcm1pbmFsIDIVQm9kcnVtIC0gTWlsYXMgQm9kcnVtEUJvbG9nbmEgLSBNYXJjb25pGUJyYXNvdiAtIFRyYW5zZmVyIGF1dG9jYXIpQnJ1c3NlbHMgLSBCcnV4ZWxsZXMgQWlycG9ydCAtIFRlcm1pbmFsIEITQnVjaGFyZXN0IC0gT3RvcGVuaRZDYXRhbmlhIC0gRm9udGFuYXJvc3NhGkNsdWogLSBBdnJhbSBJYW5jdSBBaXJwb3J0GENvbnN0YW50YSAtIEJ1cyBUcmFuc2ZlcjJDb3JmdSAtIElvYW5uaXMgS2Fwb2Rpc3RyaWFzIEludGVybmF0aW9uYWwgQWlycG9ydCREdWJsaW4gLSBEdWJsaW4gQWlycG9ydCAtIFRlcm1pbmFsIDElSGVyYWtsaW9uIC0gTmlrb3MgS2F6YW50emFraXMgQWlycG9ydBNJYXNpIC0gSWFzaSBBaXJwb3J0DUliaXphIC0gSWJpemEQS29sbiAtIEtvbG4gQm9ubhlMYXJuYWNhIC0gTGFybmFjYSBBaXJwb3J0IUxlZmthZGEgLSBBa3Rpb24gTmF0aW9uYWwgQWlycG9ydBZMb25kb24gKEx1dG9uKSAtIEx1dG9uG01hZHJpZCAtIEJhcmFqYXMgVGVybWluYWwgMSVNYWxhZ2EgIC0gTWFsYWdhIEFpcnBvcnQgLSBUZXJtaW5hbCAyH01pbGFuIChCZXJnYW1vKSAtIE9yaW8gYWwgU2VyaW8XTmFwbGVzIC0gTmFwb2xpIEFpcnBvcnQgTmljZSAtIEPDtHRlIGQnQXp1ciAtIFRlcm1pbmFsIDEbUGFyaXMgKEJlYXV2YWlzKSAtIEJlYXV2YWlzG1JvbWUgLSBGaXVtaWNpbm8gVGVybWluYWwgMhZTYWxvbmljIC0gVGhlc3NhbG9uaWtpDVNpYml1IC0gU2liaXUqU3R1dHRnYXJ0IC0gU3R1dHRnYXJ0IEFpcnBv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Jz5JU1JBRUw8L2E+PC9saT48bGk+PGEgaHJlZj0namF2YXNjcmlwdDonIHJlbD0nY3RsMDBfZHJvcG1lbnUxNCcgY2xhc3M9J2Rpcic+SVRBTFk8L2E+PC9saT48bGk+PGEgaHJlZj0namF2YXNjcmlwdDonIHJlbD0nY3RsMDBfZHJvcG1lbnUxOScgY2xhc3M9J2Rpcic+Uk9NQU5JQTwvYT48L2xpPjxsaT48YSBocmVmPSdqYXZhc2NyaXB0OicgcmVsPSdjdGwwMF9kcm9wbWVudTIwJyBjbGFzcz0nZGlyJz5TUEFJTjwvYT48L2xpPjxsaT48YSBocmVmPSdqYXZhc2NyaXB0OicgcmVsPSdjdGwwMF9kcm9wbWVudTIzJyBjbGFzcz0nZGlyJz5UVVJLRVk8L2E+PC9saT5kAgwPDxYCHwAFLmh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vQ2FycmlhZ2UtQ29uZGl0aW9ucy9kZAINDw8WAh8ABS9odHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1Rlcm1zLUFuZC1Db25kaXRpb25zL2RkAg4PDxYCHwAFImh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vQ2FyZWVycy9kZAIPDw8WAh8ABSpodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1V0aWwtSW5mb3MvUHJlc3NkZAIQDw8WAh8ABS9odHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL1V0aWwtSW5mb3MvTmV3c2xldHRlcmRkAhEPDxYCHwAFImh0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vQ29udGFjdC9kZAISDw8WAh8ABSNodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL09waW5pb25zL2RkAhMPDxYCHwAFI2h0dHA6Ly93d3cuYmx1ZWFpcndlYi5jb20vU2l0ZS1NYXAvZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgQFMmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQm9va2luZ0JveDEkcmRvUm91bmRUcmlwBTJjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJEJvb2tpbmdCb3gxJHJkb1JvdW5kVHJpcAUvY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRCb29raW5nQm94MSRyZG9PbmVXYXkFJmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkY2tiQWdyZWVtZW50vpQfWT4aYnrl68TP5i9UdjAjkNI=");
		map.put("__SCROLLPOSITIONX","0");
		map.put("__SCROLLPOSITIONY","0");
		map.put("__EVENTVALIDATION","/wEW9AECr4jshQ0C+NPa6QICk734/gwCrqaWlAcCyY+0qQECjK/jlAoCp5iBqgQCwoGfvw4C3eq81AgCxOTC9AIC/Z/gjgICpsv9jgEC5tu2jAcCuNykjgwCu7PCYwL9g9WVCAKPzrLUAwLqp5fhBQLC7c3LBALL/+jVCQLC7a3IBALnlM++AgLj5urIBwKa6+2hBALdivyUDQLLrt7gCgLmlL++AgKE/MfKDgKg0eL+AwKKqteLBgKHn8fIAgLpp4fhBQKR66mhBAKrnKq+BwLgp8vhBQLBrsrgCgLh5qbIBwKjhpW/CALk5r7IBwKrgKCKDQKb6+WhBALRuPWKCALs8sSgCQL9n8fIAgKl0a7/AwKw7sXLBAKRmf6XBwLd1cFXAvDYmX4Cg86OywoC0trbyQ8C84Kk/QMC3+mDlggCopahiQ4CvIGL4gQC0drLyQ8CqZbliQ4C2NqHyQ8C+dOGSALZm+rgDQKb+9mXAgLcm/LgDQKT/eyiBwKjlqmJDgLpxbmiAgKdrOLXCQKp5LK/DQLlqI3/CgKws7qVBwLIl9DPAwLJl9DPAwLKl9DPAwLLl9DPAwLMl9DPAwLNl9DPAwLOl9DPAwLfl9DPAwLQl9DPAwLIl5DMAwKTjsWUCgKMjsWUCgKNjsWUCgKOjsWUCgKPjsWUCgKIjsWUCgLA3ay1BQLf3ay1BQLe3ay1BQLd3ay1BQLc3ay1BQLb3ay1BQKEqsFDAozIod8DAtT/segHAuW+5MEFAoa2gcEKAq2Z9MgFAqfbnIgMAqzVwvUKAuCPip8GAqzVhmQC+JTD+A8CyuHQeALE9N3kCwLHm7uJBwKBq6z/DwLz5su+BAKWj+6LAgK+xbShAwK315G/DgK+xdSiAwKbvLbUBQKfzpMiAubDlMsDAqGihf4KAreGp4oNApq8xtQFAvjUvqAJAtz5m5QEAvaCruEBAvu3vqIFApWP/osCAu3D0MsDAte001QCnI+yiwICvYazig0Cnc7fIgLfruzVDwKYzsciAteo2eAKAufDnMsDAq2QjOAPApDavcoOAoG3vqIFAtn515UEAszGvKEDAu2xh30Cof24vQcCjPDglAcC0/yokAcClcy/5g8C54HYpwQCguj9kgICqqKnuAMCo7CCpg4CqqLHuwMCj9ulzQUCi6mAOwLypIfSAwK1xZbnCgKj4bSTDQKO29XNBQLss625CQLInoiNBALi5b34AQLv0K27BQKB6O2SAgL5pMPSAwLD08BNAojooZICAqnhoJMNAompzDsCy8n/zA8CjKnUOwLDz8r5CgLzpI/SAwK595/5DwKEva7TDgKV0K27BQLNnsSMBALYoa+4AwL51pRkArWaq6QHApiX840HAs7eiqMPAszvgakJAuT0iNUBAoGf5KwDAoGf6KwDAoGf3KwDAoGf4KwDAoGf9KwDAoGf+KwDAp3o2pQNAvPyyJYOAo7YtdQIAo7YqTMCjtidngsCjtjx+gICjtjlwQ0CjtiZ+QgCjtiNxAMCw7vMsgYCw7ugmQECw7uU5AgCw7uIwwMCw7v8rwsCw7vQigICw7vE0Q0Cw7u4vAUCw7vs1QMCw7vAsAsCpoLSqQwCpoLG9AcCpoK60w4CpoKuvgYCpoKChQECpoL24QgCpoLqzAMCpoLeqwsCpoLywAkCpoLmrwECvZXwhAICvZXk4w0CvZXYzgQCvZXMlQwCvZWg8AcCvZWU3w4CvZWIugYCvZX8hgECvZWQvgwCvZWEhQcCkPyX8ggCkPyL2QMCkPz/pQsCkPzTgAICkPzH7w0Cnazv7AcCnsOJgQsCj7TEvgIC1caJkA4CsK2miwgC/v/r/gMCwsO9nAsCnubEoAQCruOM1QQCy5jc9QwCw8P5nAsC68PNnAsC8sOhnAsC/pqE+AUC0KmdxQ0Ct+fOPQKa0+u2CNddDC8gR/d12D9uNVM2dLD08h9I");
		map.put("ctl00$txtSearch","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$TripType","rdoOneWay");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlFrom",arg0.getDep());
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlTo",arg0.getArr());
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtDepartureDate",arg0.getDepDate());
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlAdult","1");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlChildren","0");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlInfant","0");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlCurrency","RON");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtConfirmationNumber1","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtContactEmail","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtConfirmationNumber2","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtFirstName","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$txtLastName","");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlOriginCity","-1");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$ddlDestinationCity","-1");
		map.put("ctl00$ContentPlaceHolder1$BookingBox1$hButton2","");
		map.put("DepartureCalendar","19.07.201400:10");
		map.put("ctl00$ContentPlaceHolder1$ddlPaymentType","-1");
		map.put("ctl00$hButton2Master","");
		
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
		//httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		//Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));
		
		//首次请求，94 提取__VIEWSTATE、__EVENTVALIDATION
		QFGetMethod get2 = new QFGetMethod("https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		get2.setRequestHeader("Referer", "http://www.blueairweb.com/first-page/");
		get2.getParams().setContentCharset("UTF-8");
		httpClient.executeMethod(get2);
		String html2 = get2.getResponseBodyAsString();
		String __VIEWSTATE2 = StringUtils.substringBetween(html2,"id=\"__VIEWSTATE\" value=\"", "\" />");
		String __EVENTVALIDATION = StringUtils.substringBetween(html2,"id=\"__EVENTVALIDATION\" value=\"", "\" />");
		
		// 208  追加请求，获取cookie及二次__VIEWSTATE、__EVENTVALIDATION
		QFPostMethod post208 = new QFPostMethod("https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		NameValuePair[] names208 = {
				new NameValuePair("",""),
				new NameValuePair("__ASYNCPOST","true"),
				new NameValuePair("__EVENTARGUMENT",""),
				new NameValuePair("__EVENTTARGET","BookingBox1$rdoOneWay"),
				//new NameValuePair("__EVENTVALIDATION","/wEWuAECotPnuwkC2+KZzwcCm/LSzQECzerPKwLOhanGDAKItb6wBAL6+NnxDwKfkfzECQK326buCAK+yYPwBQK328btCAKSoqSbDgKW0IHtCwLv3YaECAKovJexAQK+mLXFBgKTotSbDgLxyqzvAgLV54nbDwL/nLyuCgLyqaztDgKckezECQLk3cKECALeqsGbCwKVkaDECQK0mKHFBgKU0M3tCwLWsP6aBAKR0NXtCwLetsuvAQLu3Y6ECAKkjp6vBAKZxK+FBQKIqaztDgLQ58XaDwLF2K7uCALkr5WyCwKo46ryDAKF7vLbDAKav7hfAtyPr6kIAq7CyOgDAsur7d0FAuPht/cEAurzkukJAuPh1/QEAsaYtYICAsLqkPQHArvnl50EAvyGhqgNAuqipNwKAseYxYICAqXwvfYOAoHdmMIDAqumrbcGAqaTvfQCAsir/d0FArDn050EAoqQ0IIHAsGrsd0FAuCisNwKAsDq3PQHAoKK74MIAsXqxPQHAoqM2rYNArrnn50EAvC0j7YIAs3+vpwJAtyTvfQCAoTd1MMDApHiv/cEArCVhKsHAvzZu2sC0dTjQgLrrriGDQKMv82JDALP7tzZCgLO7tzZCgLN7tzZCgLM7tzZCgLL7tzZCgLK7tzZCgLJ7tzZCgLY7tzZCgLX7tzZCgLP7pzaCgKhhsKkBQK+hsKkBQK/hsKkBQK8hsKkBQK9hsKkBQK6hsKkBQKskpvmCQKzkpvmCQKykpvmCQKxkpvmCQKwkpvmCQK3kpvmCQKwhKiYCwK45siECALg0dizDALRkI2aDgKymOiaAQLpvIvRBwKGw5+VCgKlzeWhAQLjtPuRCAKlzeGhAQKnwPHbBQL3yILGDwKd153cAQKeuPuxDQLYiOzHBQKqxYuGDgLPrK6zCALn5vSZCQLu9NGHBALn5pSaCQLCn/bsDwLG7dOaCgK/4NTzCQL4gcVGAu6l57IHAsOfhuwPAqH3/pgDAoXa26wOAq+h7tkLAqKU/poPAsysvrMIArTgkPMJAo6Xk+wKAsWs8rMIAuSl87IHAsTtn5oKAoaNrO0FAsHth5oKAo6LmVgCvuDc8wkC9LPM2AUCyfn98gQC2JT+mg8CgNqXrQ4CleX8mQkCtJLHxQoC+N74hQ0C1dOgrA0CoKCP3AwC5pCYqgQClN3/6w8C8bTa3gkC2f6A9AgC0Oyl6gUC2f7g9wgC/IeCgQ4C+PWn9wsCgfignggCxpmxqwEC0L2T3wYC/YfygQ4Cn++K9QICu8KvwQ8CkbmatAoCnIyK9w4C8rTK3gkCivjknggCsI/ngQsC+7SG3gkC2r2H3wYC+vXr9wsCuJXYgAQC//Xz9wsCsJPttQECgPionggCyqu4tQQC9+GJnwUC5oyK9w4CvsLjwA8Cq/2I9AgCioqzqAsCxsaM6AwC68vUwQwC7dLikwICif7+lAMCqYKe2wKZac14tFpinOiq8OrUxmyqMnRAWg=="),

				new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION),
				new NameValuePair("__LASTFOCUS",""),
				//new NameValuePair("__VIEWSTATE","/wEPDwUKMTU4ODI1MDUwMg9kFgICAw9kFgICAw8PFgIeDkZsZXhpYmxlRGF5c05vAgJkFgJmD2QWAmYPZBYYZg8PFgIeC05hdmlnYXRlVXJsBS1odHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL29ubGluZS9Ib3RlbC1Ib3N0ZWxkZAIBDw8WAh8BBTFodHRwOi8vd3d3LmJsdWVhaXJ3ZWIuY29tL29ubGluZS9DYXItUmVzZXJ2YXRpb25zZGQCAg8PFgIfAQUgaHR0cDovL2JsdWVhaXIucGFya3ZpYS5jb20vZW4tR0JkZAIDDxAPFgIeB0NoZWNrZWRnZGRkZAIGDxAPFgYeDkRhdGFWYWx1ZUZpZWxkBQVWYWx1ZR4NRGF0YVRleHRGaWVsZAUDS2V5HgtfIURhdGFCb3VuZGdkEBUjCVNlbGVjdC4uLidBbnRhbHlhIC0gQW50YWx5YSBJbnRlcm5hdGlvbmFsIEFpcnBvcnQdQmFjYXUgLSBHZW9yZ2UgRW5lc2N1IEFpcnBvcnQgQmFyY2Vsb25hIC0gRWwgUHJhdCAtIFRlcm1pbmFsIDIVQm9kcnVtIC0gTWlsYXMgQm9kcnVtEUJvbG9nbmEgLSBNYXJjb25pGUJyYXNvdiAtIFRyYW5zZmVyIGF1dG9jYXIpQnJ1c3NlbHMgLSBCcnV4ZWxsZXMgQWlycG9ydCAtIFRlcm1pbmFsIEITQnVjaGFyZXN0IC0gT3RvcGVuaRZDYXRhbmlhIC0gRm9udGFuYXJvc3NhGkNsdWogLSBBdnJhbSBJYW5jdSBBaXJwb3J0GENvbnN0YW50YSAtIEJ1cyBUcmFuc2ZlcjJDb3JmdSAtIElvYW5uaXMgS2Fwb2Rpc3RyaWFzIEludGVybmF0aW9uYWwgQWlycG9ydCREdWJsaW4gLSBEdWJsaW4gQWlycG9ydCAtIFRlcm1pbmFsIDElSGVyYWtsaW9uIC0gTmlrb3MgS2F6YW50emFraXMgQWlycG9ydBNJYXNpIC0gSWFzaSBBaXJwb3J0DUliaXphIC0gSWJpemEQS29sbiAtIEtvbG4gQm9ubhlMYXJuYWNhIC0gTGFybmFjYSBBaXJwb3J0IUxlZmthZGEgLSBBa3Rpb24gTmF0aW9uYWwgQWlycG9ydBZMb25kb24gKEx1dG9uKSAtIEx1dG9uG01hZHJpZCAtIEJhcmFqYXMgVGVybWluYWwgMSVNYWxhZ2EgIC0gTWFsYWdhIEFpcnBvcnQgLSBUZXJtaW5hbCAyH01pbGFuIChCZXJnYW1vKSAtIE9yaW8gYWwgU2VyaW8XTmFwbGVzIC0gTmFwb2xpIEFpcnBvcnQgTmljZSAtIEPDtHRlIGQnQXp1ciAtIFRlcm1pbmFsIDEbUGFyaXMgKEJlYXV2YWlzKSAtIEJlYXV2YWlzG1JvbWUgLSBGaXVtaWNpbm8gVGVybWluYWwgMhZTYWxvbmljIC0gVGhlc3NhbG9uaWtpDVNpYml1IC0gU2liaXUqU3R1dHRnYXJ0IC0gU3R1dHRnYXJ0IEFpcnBvcnQgLSBUZXJtaW5hbCA0HlRlbCBBdml2IC0gQmVuIEd1cml0b24gQWlycG9ydBZUdXJpbiBDdW5lbyAtIE9MSU1QSUNBH1ZhbGVuY2lhIC0gTWFuaXNlcyAtIFRlcm1pbmFsIDIlWmFreW50aG9zIC0gRGlvbnlzaW9zIFNvbG9tb3MgQWlycG9ydBUjAi0xA0FZVANCQ00DQkNOA0JKVgNCTFEDQlJWA0JSVQNPVFADQ1RBA0NMSgNDTkQDQ0ZVA0RVQgNIRVIDSUFTA0lCWgNDR04DTENBA1BWSwNMVE4DTUFEA0FHUANCR1kDTkFQA05DRQNCVkEDRkNPA1NLRwNTQloDU1RSA1RMVgNDVUYDVkxDA1pUSBQrAyNnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZxYBZmQCCA8QDxYGHwMFBVZhbHVlHwQFA0tleR8FZ2QQFSMJU2VsZWN0Li4uJ0FudGFseWEgLSBBbnRhbHlhIEludGVybmF0aW9uYWwgQWlycG9ydB1CYWNhdSAtIEdlb3JnZSBFbmVzY3UgQWlycG9ydCBCYXJjZWxvbmEgLSBFbCBQcmF0IC0gVGVybWluYWwgMhVCb2RydW0gLSBNaWxhcyBCb2RydW0RQm9sb2duYSAtIE1hcmNvbmkZQnJhc292IC0gVHJhbnNmZXIgYXV0b2NhcilCcnVzc2VscyAtIEJydXhlbGxlcyBBaXJwb3J0IC0gVGVybWluYWwgQhNCdWNoYXJlc3QgLSBPdG9wZW5pFkNhdGFuaWEgLSBGb250YW5hcm9zc2EaQ2x1aiAtIEF2cmFtIElhbmN1IEFpcnBvcnQYQ29uc3RhbnRhIC0gQnVzIFRyYW5zZmVyMkNvcmZ1IC0gSW9hbm5pcyBLYXBvZGlzdHJpYXMgSW50ZXJuYXRpb25hbCBBaXJwb3J0JER1YmxpbiAtIER1YmxpbiBBaXJwb3J0IC0gVGVybWluYWwgMSVIZXJha2xpb24gLSBOaWtvcyBLYXphbnR6YWtpcyBBaXJwb3J0E0lhc2kgLSBJYXNpIEFpcnBvcnQNSWJpemEgLSBJYml6YRBLb2xuIC0gS29sbiBCb25uGUxhcm5hY2EgLSBMYXJuYWNhIEFpcnBvcnQhTGVma2FkYSAtIEFrdGlvbiBOYXRpb25hbCBBaXJwb3J0FkxvbmRvbiAoTHV0b24pIC0gTHV0b24bTWFkcmlkIC0gQmFyYWphcyBUZXJtaW5hbCAxJU1hbGFnYSAgLSBNYWxhZ2EgQWlycG9ydCAtIFRlcm1pbmFsIDIfTWlsYW4gKEJlcmdhbW8pIC0gT3JpbyBhbCBTZXJpbxdOYXBsZXMgLSBOYXBvbGkgQWlycG9ydCBOaWNlIC0gQ8O0dGUgZCdBenVyIC0gVGVybWluYWwgMRtQYXJpcyAoQmVhdXZhaXMpIC0gQmVhdXZhaXMbUm9tZSAtIEZpdW1pY2lubyBUZXJtaW5hbCAyFlNhbG9uaWMgLSBUaGVzc2Fsb25pa2kNU2liaXUgLSBTaWJpdSpTdHV0dGdhcnQgLSBTdHV0dGdhcnQgQWlycG9ydCAtIFRlcm1pbmFsIDQeVGVsIEF2aXYgLSBCZW4gR3VyaXRvbiBBaXJwb3J0FlR1cmluIEN1bmVvIC0gT0xJTVBJQ0EfVmFsZW5jaWEgLSBNYW5pc2VzIC0gVGVybWluYWwgMiVaYWt5bnRob3MgLSBEaW9ueXNpb3MgU29sb21vcyBBaXJwb3J0FSMCLTEDQVlUA0JDTQNCQ04DQkpWA0JMUQNCUlYDQlJVA09UUANDVEEDQ0xKA0NORANDRlUDRFVCA0hFUgNJQVMDSUJaA0NHTgNMQ0EDUFZLA0xUTgNNQUQDQUdQA0JHWQNOQVADTkNFA0JWQQNGQ08DU0tHA1NCWgNTVFIDVExWA0NVRgNWTEMDWlRIFCsDI2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQCDg8QZA8WCmYCAQICAgMCBAIFAgYCBwIIAgkWChAFATEFATFnEAUBMgUBMmcQBQEzBQEzZxAFATQFATRnEAUBNQUBNWcQBQE2BQE2ZxAFATcFATdnEAUBOAUBOGcQBQE5BQE5ZxAFAjEwBQIxMGdkZAIPDxBkDxYGZgIBAgICAwIEAgUWBhAFATAFATBnEAUBMQUBMWcQBQEyBQEyZxAFATMFATNnEAUBNAUBNGcQBQE1BQE1Z2RkAhEPEGQPFgZmAgECAgIDAgQCBRYGEAUBMAUBMGcQBQExBQExZxAFATIFATJnEAUBMwUBM2cQBQE0BQE0ZxAFATUFATVnZGQCFA8PFgIfAQUvaHR0cDovL3d3dy5ibHVlYWlyd2ViLmNvbS9UZXJtcy1BbmQtQ29uZGl0aW9ucy9kZAIcDxAPFgYfAwUFVmFsdWUfBAUDS2V5HwVnZBAVIwlTZWxlY3QuLi4nQW50YWx5YSAtIEFudGFseWEgSW50ZXJuYXRpb25hbCBBaXJwb3J0HUJhY2F1IC0gR2VvcmdlIEVuZXNjdSBBaXJwb3J0IEJhcmNlbG9uYSAtIEVsIFByYXQgLSBUZXJtaW5hbCAyFUJvZHJ1bSAtIE1pbGFzIEJvZHJ1bRFCb2xvZ25hIC0gTWFyY29uaRlCcmFzb3YgLSBUcmFuc2ZlciBhdXRvY2FyKUJydXNzZWxzIC0gQnJ1eGVsbGVzIEFpcnBvcnQgLSBUZXJtaW5hbCBCE0J1Y2hhcmVzdCAtIE90b3BlbmkWQ2F0YW5pYSAtIEZvbnRhbmFyb3NzYRpDbHVqIC0gQXZyYW0gSWFuY3UgQWlycG9ydBhDb25zdGFudGEgLSBCdXMgVHJhbnNmZXIyQ29yZnUgLSBJb2FubmlzIEthcG9kaXN0cmlhcyBJbnRlcm5hdGlvbmFsIEFpcnBvcnQkRHVibGluIC0gRHVibGluIEFpcnBvcnQgLSBUZXJtaW5hbCAxJUhlcmFrbGlvbiAtIE5pa29zIEthemFudHpha2lzIEFpcnBvcnQTSWFzaSAtIElhc2kgQWlycG9ydA1JYml6YSAtIEliaXphEEtvbG4gLSBLb2xuIEJvbm4ZTGFybmFjYSAtIExhcm5hY2EgQWlycG9ydCFMZWZrYWRhIC0gQWt0aW9uIE5hdGlvbmFsIEFpcnBvcnQWTG9uZG9uIChMdXRvbikgLSBMdXRvbhtNYWRyaWQgLSBCYXJhamFzIFRlcm1pbmFsIDElTWFsYWdhICAtIE1hbGFnYSBBaXJwb3J0IC0gVGVybWluYWwgMh9NaWxhbiAoQmVyZ2FtbykgLSBPcmlvIGFsIFNlcmlvF05hcGxlcyAtIE5hcG9saSBBaXJwb3J0IE5pY2UgLSBDw7R0ZSBkJ0F6dXIgLSBUZXJtaW5hbCAxG1BhcmlzIChCZWF1dmFpcykgLSBCZWF1dmFpcxtSb21lIC0gRml1bWljaW5vIFRlcm1pbmFsIDIWU2Fsb25pYyAtIFRoZXNzYWxvbmlraQ1TaWJpdSAtIFNpYml1KlN0dXR0Z2FydCAtIFN0dXR0Z2FydCBBaXJwb3J0IC0gVGVybWluYWwgNB5UZWwgQXZpdiAtIEJlbiBHdXJpdG9uIEFpcnBvcnQWVHVyaW4gQ3VuZW8gLSBPTElNUElDQR9WYWxlbmNpYSAtIE1hbmlzZXMgLSBUZXJtaW5hbCAyJVpha3ludGhvcyAtIERpb255c2lvcyBTb2xvbW9zIEFpcnBvcnQVIwItMQNBWVQDQkNNA0JDTgNCSlYDQkxRA0JSVgNCUlUDT1RQA0NUQQNDTEoDQ05EA0NGVQNEVUIDSEVSA0lBUwNJQloDQ0dOA0xDQQNQVksDTFROA01BRANBR1ADQkdZA05BUANOQ0UDQlZBA0ZDTwNTS0cDU0JaA1NUUgNUTFYDQ1VGA1ZMQwNaVEgUKwMjZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2cWAWZkAh0PEA8WBh8DBQVWYWx1ZR8EBQNLZXkfBWdkEBUjCVNlbGVjdC4uLidBbnRhbHlhIC0gQW50YWx5YSBJbnRlcm5hdGlvbmFsIEFpcnBvcnQdQmFjYXUgLSBHZW9yZ2UgRW5lc2N1IEFpcnBvcnQgQmFyY2Vsb25hIC0gRWwgUHJhdCAtIFRlcm1pbmFsIDIVQm9kcnVtIC0gTWlsYXMgQm9kcnVtEUJvbG9nbmEgLSBNYXJjb25pGUJyYXNvdiAtIFRyYW5zZmVyIGF1dG9jYXIpQnJ1c3NlbHMgLSBCcnV4ZWxsZXMgQWlycG9ydCAtIFRlcm1pbmFsIEITQnVjaGFyZXN0IC0gT3RvcGVuaRZDYXRhbmlhIC0gRm9udGFuYXJvc3NhGkNsdWogLSBBdnJhbSBJYW5jdSBBaXJwb3J0GENvbnN0YW50YSAtIEJ1cyBUcmFuc2ZlcjJDb3JmdSAtIElvYW5uaXMgS2Fwb2Rpc3RyaWFzIEludGVybmF0aW9uYWwgQWlycG9ydCREdWJsaW4gLSBEdWJsaW4gQWlycG9ydCAtIFRlcm1pbmFsIDElSGVyYWtsaW9uIC0gTmlrb3MgS2F6YW50emFraXMgQWlycG9ydBNJYXNpIC0gSWFzaSBBaXJwb3J0DUliaXphIC0gSWJpemEQS29sbiAtIEtvbG4gQm9ubhlMYXJuYWNhIC0gTGFybmFjYSBBaXJwb3J0IUxlZmthZGEgLSBBa3Rpb24gTmF0aW9uYWwgQWlycG9ydBZMb25kb24gKEx1dG9uKSAtIEx1dG9uG01hZHJpZCAtIEJhcmFqYXMgVGVybWluYWwgMSVNYWxhZ2EgIC0gTWFsYWdhIEFpcnBvcnQgLSBUZXJtaW5hbCAyH01pbGFuIChCZXJnYW1vKSAtIE9yaW8gYWwgU2VyaW8XTmFwbGVzIC0gTmFwb2xpIEFpcnBvcnQgTmljZSAtIEPDtHRlIGQnQXp1ciAtIFRlcm1pbmFsIDEbUGFyaXMgKEJlYXV2YWlzKSAtIEJlYXV2YWlzG1JvbWUgLSBGaXVtaWNpbm8gVGVybWluYWwgMhZTYWxvbmljIC0gVGhlc3NhbG9uaWtpDVNpYml1IC0gU2liaXUqU3R1dHRnYXJ0IC0gU3R1dHRnYXJ0IEFpcnBvcnQgLSBUZXJtaW5hbCA0HlRlbCBBdml2IC0gQmVuIEd1cml0b24gQWlycG9ydBZUdXJpbiBDdW5lbyAtIE9MSU1QSUNBH1ZhbGVuY2lhIC0gTWFuaXNlcyAtIFRlcm1pbmFsIDIlWmFreW50aG9zIC0gRGlvbnlzaW9zIFNvbG9tb3MgQWlycG9ydBUjAi0xA0FZVANCQ00DQkNOA0JKVgNCTFEDQlJWA0JSVQNPVFADQ1RBA0NMSgNDTkQDQ0ZVA0RVQgNIRVIDSUFTA0lCWgNDR04DTENBA1BWSwNMVE4DTUFEA0FHUANCR1kDTkFQA05DRQNCVkEDRkNPA1NLRwNTQloDU1RSA1RMVgNDVUYDVkxDA1pUSBQrAyNnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2RkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYDBRhCb29raW5nQm94MSRyZG9Sb3VuZFRyaXAFFUJvb2tpbmdCb3gxJHJkb09uZVdheQUVQm9va2luZ0JveDEkcmRvT25lV2F5HdKgO8tvoZPsmcrx5yuNtZ3eAvo="),
				new NameValuePair("__VIEWSTATE",__VIEWSTATE2),
				
				new NameValuePair("BookingBox1$ddlAdult","1"),
				new NameValuePair("BookingBox1$ddlChildren","0"),
				new NameValuePair("BookingBox1$ddlCurrency","EUR"),
				new NameValuePair("BookingBox1$ddlDestinationCity","-1"),
				new NameValuePair("BookingBox1$ddlFrom","-1"),
				new NameValuePair("BookingBox1$ddlInfant","0"),
				new NameValuePair("BookingBox1$ddlOriginCity","-1"),
				new NameValuePair("BookingBox1$ddlTo","-1"),
				new NameValuePair("BookingBox1$hButton2",""),
				new NameValuePair("BookingBox1$TripType","rdoOneWay"),
				new NameValuePair("BookingBox1$txtConfirmationNumber1",""),
				new NameValuePair("BookingBox1$txtConfirmationNumber2",""),
				new NameValuePair("BookingBox1$txtContactEmail",""),
				new NameValuePair("BookingBox1$txtDepartureDate","20.06.2014"),
				new NameValuePair("BookingBox1$txtFirstName",""),
				new NameValuePair("BookingBox1$txtLastName",""),
				new NameValuePair("BookingBox1$txtReturnDate","27.06.2014"),
				new NameValuePair("ScriptManager1","BookingBox1$updAJAX|BookingBox1$rdoOneWay")
	    };
	 	
		httpClient.getState().clearCookies();
		post208.setRequestBody(names208);
		post208.getParams().setContentCharset("UTF-8");
		post208.setRequestHeader("x-microsoftajax", "Delta=true");
		post208.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post208.setRequestHeader("Connection", "Keep-Alive");
		post208.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		httpClient.executeMethod(post208);
		String html208 = post208.getResponseBodyAsString();
		String __VIEWSTATE208 = StringUtils.substringBetween(html208,"__VIEWSTATE|", "|");
		String __EVENTVALIDATION208 = StringUtils.substringBetween(html208,"__EVENTVALIDATION|", "|");
		String cookie = StringUtils.join(httpClient.getState().getCookies(),"; ");
		
		// 212   追加请求，获取三次__VIEWSTATE、__EVENTVALIDATION
		QFPostMethod post212 = new QFPostMethod("https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		NameValuePair[] names212 = {
				new NameValuePair("",""),
				new NameValuePair("__AjaxControlToolkitCalendarCssLoaded",""),
				new NameValuePair("__ASYNCPOST","true"),
				new NameValuePair("__EVENTARGUMENT",""),
				new NameValuePair("__EVENTTARGET","BookingBox1$ddlFrom"),
				new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION208),
				new NameValuePair("__LASTFOCUS",""),
				new NameValuePair("__VIEWSTATE",__VIEWSTATE208),
				new NameValuePair("BookingBox1$ddlAdult","1"),
				new NameValuePair("BookingBox1$ddlChildren","0"),
				new NameValuePair("BookingBox1$ddlCurrency","EUR"),
				new NameValuePair("BookingBox1$ddlDestinationCity","-1"),
				new NameValuePair("BookingBox1$ddlFrom",arg0.getDep()),
				new NameValuePair("BookingBox1$ddlInfant","0"),
				new NameValuePair("BookingBox1$ddlOriginCity","-1"),
				new NameValuePair("BookingBox1$ddlTo","-1"),
				new NameValuePair("BookingBox1$hButton2",""),
				new NameValuePair("BookingBox1$TripType","rdoOneWay"),
				new NameValuePair("BookingBox1$txtConfirmationNumber1",""),
				new NameValuePair("BookingBox1$txtConfirmationNumber2",""),
				new NameValuePair("BookingBox1$txtContactEmail",""),
				new NameValuePair("BookingBox1$txtDepartureDate","20.06.2014"),
				new NameValuePair("BookingBox1$txtFirstName",""),
				new NameValuePair("BookingBox1$txtLastName",""),
				//new NameValuePair("BookingBox1$txtReturnDate","26.06.2014"),
				new NameValuePair("ScriptManager1","BookingBox1$updAJAX|BookingBox1$ddlFrom")
	    };
	 	
		post212.setRequestBody(names212);
		post212.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post212.getParams().setContentCharset("UTF-8");
		httpClient.getState().clearCookies();
		post212.addRequestHeader("Cookie",cookie);
		post212.setRequestHeader("x-microsoftajax", "Delta=true");
		post212.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post212.setRequestHeader("Connection", "Keep-Alive");
		post212.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		httpClient.executeMethod(post212);
		String html212 = post212.getResponseBodyAsString();
		String __VIEWSTATE212 = StringUtils.substringBetween(html212,"__VIEWSTATE|", "|");
		String __EVENTVALIDATION212 = StringUtils.substringBetween(html212,"__EVENTVALIDATION|", "|");
		
		// 填写查询表单，提交请求
		post = new QFPostMethod("https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post.setFollowRedirects(false);	//302
		NameValuePair[] names = {
				new NameValuePair("BookingBox1$ddlFrom",	arg0.getDep()),
				new NameValuePair("BookingBox1$ddlTo",	arg0.getArr()),
				new NameValuePair("BookingBox1$txtDepartureDate",	arg0.getDepDate()),
				new NameValuePair("__EVENTVALIDATION",__EVENTVALIDATION212),
				new NameValuePair("__VIEWSTATE",__VIEWSTATE212),
				new NameValuePair("ScriptManager1","BookingBox1$updAJAX|BookingBox1$btnSearchFlights"),
				new NameValuePair("__EVENTTARGET","BookingBox1$btnSearchFlights"),
				new NameValuePair("__EVENTARGUMENT",""),
				new NameValuePair("__LASTFOCUS",""),
				new NameValuePair("BookingBox1$TripType","rdoOneWay"),
				new NameValuePair("BookingBox1$ddlAdult","1"),
				new NameValuePair("BookingBox1$ddlChildren","0"),
				new NameValuePair("BookingBox1$ddlInfant","0"),
				new NameValuePair("BookingBox1$ddlCurrency","RON"),
				new NameValuePair("BookingBox1$txtConfirmationNumber1",""),
				new NameValuePair("BookingBox1$txtContactEmail",""),
				new NameValuePair("BookingBox1$txtConfirmationNumber2",""),
				new NameValuePair("BookingBox1$txtFirstName",""),
				new NameValuePair("BookingBox1$txtLastName",""),
				new NameValuePair("BookingBox1$ddlOriginCity","-1"),
				new NameValuePair("BookingBox1$ddlDestinationCity","-1"),
				new NameValuePair("BookingBox1$hButton2",""),
				new NameValuePair("__ASYNCPOST","true"),
				new NameValuePair("__AjaxControlToolkitCalendarCssLoaded",""),
				new NameValuePair("","")
	    };
		
	    post.setRequestBody(names);
	 	post.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post.getParams().setContentCharset("UTF-8");
		
		httpClient.getState().clearCookies();
		post.addRequestHeader("Cookie",cookie);
		post.setRequestHeader("x-microsoftajax", "Delta=true");
		post.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		httpClient.executeMethod(post);
		
		// 查询表单提交追加请求，获取最终查询结果
		QFGetMethod post2 = new QFGetMethod("https://open.maxitours.be/SelectLowFareCalendar.aspx");
		post2.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post2.getParams().setContentCharset("UTF-8");
		httpClient.getState().clearCookies();
		post2.setRequestHeader("Referer", "https://open.maxitours.be/blueairsitesearch.aspx?culture=en-US");
		post2.addRequestHeader("Cookie",cookie);
		post2.setRequestHeader("Connection", "Keep-Alive");
		httpClient.executeMethod(post2);	
		
		return post2.getResponseBodyAsString();
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
		String html2 = StringUtils.substringBetween(html,"<td class=\"calendar_defaultdayformat\" align=\"center\" style=\"color:White;background-color:#F6F6F6;width:14%;\">" ,"<td class=\"calendar_defaultdayformat\" align=\"center\" style=\"width:14%;\">");
		String[] flights = html2.split("<div class=\"tt\"");
		
		try {			
			List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			for (int i = 1; i < flights.length; i++){
				// 预先处理Html
				String flightHtml = flights[i];
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 过滤文章内容中的html
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 去除空格、制表符、回车换行
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 去除空格
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 开始解析...
				String[] flightTxts = flightHtml.split("Arrival");
				int jtcs = flightTxts.length - 1;	//	经停次数
				String DepartureFlight = StringUtils.substringBetween(flightHtml, "DepartureFlight", "Departure");
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
				
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
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
			result.setStatus("<textarea>" + html + "<textarea/>" + "<textarea>" + e.getMessage() + "<textarea/>");
			
			return result;
		}
	}
	
	public static void main(String[] args) {
		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("CND");
		searchParam.setArr("LTN");
		searchParam.setDepDate("17.07.2014");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		searchParam.setWrapperid("Wrapper_gjdairob001");
		
		Wrapper_gjdairob001 gjdairob001 = new  Wrapper_gjdairob001();
		String html = gjdairob001.getHtml(searchParam);

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
