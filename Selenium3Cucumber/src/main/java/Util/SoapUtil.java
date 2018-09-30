package Util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SoapUtil {


	private String  respMsg;

	private int respCode;

	private final String USER_AGENT = "Mozilla/5.0";

	public boolean isServiceAccessible(String url)

	{
		boolean flag = false;
		try {
			URL urlObj = new URL(url);
			HttpURLConnection connect =(HttpURLConnection)urlObj.openConnection();
			respCode=connect.getResponseCode();
			respMsg=connect.getResponseMessage();
			if (respCode == 200) {
				flag= true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


		return flag;

	}

	// HTTP GET request
	public void sendGet(String ServiceURL) throws Exception {

		String url = ServiceURL;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}



	// HTTP POST request
	public  void sendPost(String serviceURL,String xmlPath) throws Exception {

		String url = serviceURL;


		BufferedReader br = new BufferedReader(new FileReader(new File(xmlPath)));
		String soapXml;
		StringBuilder sb = new StringBuilder();

		while((soapXml=br.readLine())!= null){
			sb.append(soapXml.trim());
		}

		String finalXml= sb.toString();
		URL obj = new URL(url);
		//HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = xmlPath;

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(finalXml);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + finalXml);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	public void readWebServiceResponse(String serviceURL,String xmlPath) throws Exception
	{

		//filename is filepath string
		BufferedReader br = new BufferedReader(new FileReader(new File(xmlPath)));
		String soapXml;
		StringBuilder sb = new StringBuilder();

		while((soapXml=br.readLine())!= null){
			sb.append(soapXml.trim());
		}

		String finalXml= sb.toString();

		//String soapXml = "" ;// jEdit: = buffer.getText(0,buffer.getLength())
		URL url = new URL(serviceURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// Set the necessary header fields
		conn.setRequestProperty("SOAPAction",serviceURL );
		conn.setDoOutput(true);
		// Send the request
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(finalXml);
		wr.flush();
		// Read the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) { 
			System.out.println(line);
			/*jEdit: print(line); */ }
	}

	public  void sendSoapRequest(String serviceURL,String xmlPath) throws Exception {
		//use this if you need proxy to connect
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("YOUR PROXY", PORT NUMBER));
		String SOAPUrl = serviceURL;
		String xmlFile2Send = xmlPath;
		String responseFileName = ".\\src\\response.xml";

		// Create the connection with http
		URL url = new URL(SOAPUrl);
		//		        URLConnection connection = url.openConnection(proxy);
		//		        HttpURLConnection httpConn = (HttpURLConnection) connection;
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		FileInputStream fin = new FileInputStream(xmlFile2Send);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		this.copy(fin, bout);
		fin.close();

		byte[] b = bout.toByteArray();
		//   StringBuffer buf=new StringBuffer();
		String s=new String(b);

		//replacing a sample value in Request XML
		//    s=s.replaceAll("VALUE", value);
		//  b=s.getBytes();

		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length", String.valueOf(s.length()));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", "");
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);

		// send the XML that was read in to b.
		OutputStream out = httpConn.getOutputStream();
		out.write(b);
		out.close();

		// Read the response.
		httpConn.connect();
		System.out.println("http connection status :"+ httpConn.getResponseMessage());
		try {
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			FileOutputStream fos=new FileOutputStream(responseFileName);
			copy(httpConn.getInputStream(),fos);
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public  void copy(InputStream in, OutputStream out)
			throws IOException {

		synchronized (in) {
			synchronized (out) {
				byte[] buffer = new byte[256];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1)
						break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}

	public void FianlTest() throws Exception
	{
		
		//The first step is to create HttpURLConnection object with the end point URL. Remember to use the openConnection() method on a URL object create a HttpURLConnection object, because HttpURLConnection offers no constructors.
		URL oUrl=new URL("http://www.webservicex.net/CurrencyConvertor.asmx?wsdl");
		HttpURLConnection con= (HttpURLConnection)oUrl.openConnection();
		// Use the setRequestMethod() method to set the HTTP POST command:
		con.setRequestMethod("POST");
		//Use the setRequestProperty() method to set header lines:
		con.setRequestProperty("Content-type","text/xml; charset=utf-8");
		con.setRequestProperty("SOAPAction", "http://www.webservicex.com/globalweather.asmx/GetWeather");
		con.setDoOutput(true);
		//Prepare the entire SOAP request XML message as a string by using a DOM object, reading from a file, or simply as:
		String reqXML="<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://www.webserviceX.NET\"><soap:Header/><soap:Body><web:GetWeather><!--Optional:--><web:CityName></web:CityName><!--Optional:--><web:CountryName>China</web:CountryName></web:GetWeather></soap:Body></soap:Envelope>";
	// Then write the XML message to the connetion. reading from a file, or simply as:
		try {
			OutputStream  reqStream= con.getOutputStream();
			reqStream.write(reqXML.getBytes());
		//Finally read the SOAP response XML message from the connection reading from a file, or simply as	
			InputStream resStream = con.getInputStream();
			   byte[] byteBuf = new byte[10240];
			   int len = resStream.read(byteBuf);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}
