package com.tatcha.jscripts.login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.tatcha.utils.BrowserDriver;


public class LoginAuth {

		/*final String authorization = httpRequest.getHeader("Authorization");
		
		public void testAuth(){		
			if (authorization != null && authorization.startsWith("Basic")) {
			    // Authorization: Basic base64credentials
			    String base64Credentials = authorization.substring("Basic".length()).trim();
			    String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials),
			            Charset.forName("UTF-8")); 
			    // credentials = username:password
			    final String[] values = credentials.split(":",2);
			}
		}*/
		
	
		
		public void base64Encoding(){
			
			String uname = "storefront";
			String pword = "Tatcha123";
					
			byte[] encodedBytes = Base64.encodeBase64((uname+":"+pword).getBytes());
			System.out.println("encodedBytes " + new String(encodedBytes));
			byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
			System.out.println("decodedBytes " + new String(decodedBytes));

		}
		
		@Test
		public void basicAuth(){
			try {
				String webPage = BrowserDriver.DEV_URL;
				
//				String webPage = "http://192.168.1.1";
				String name = BrowserDriver.USERNAME;
				String password = BrowserDriver.PASSWORD;

				String authString = name + ":" + password;
				System.out.println("auth string: " + authString);
				byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
				String authStringEnc = new String(authEncBytes);
				System.out.println("Base64 encoded auth string: " + authStringEnc);

				URL url = new URL(webPage);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				
		/*
				InputStream is = urlConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				String result = sb.toString();

				System.out.println("*** BEGIN ***");
				System.out.println(result);
				System.out.println("*** END ***");
		 */				
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
		}
		
//		function logout(url){
//		    var str = url.replace("http://", "http://" + new Date().getTime() + "@");
//		    var xmlhttp;
//		    if (window.XMLHttpRequest) xmlhttp=new XMLHttpRequest();
//		    else xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
//		    xmlhttp.onreadystatechange=function()
//		    {
//		        if (xmlhttp.readyState==4) location.reload();
//		    }
//		    xmlhttp.open("GET",str,true);
//		    xmlhttp.setRequestHeader("Authorization","Basic YXNkc2E6")
//		    xmlhttp.send();
//		    return false;
//		}
		
}

