package com.collage.goddessofskin.api;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Sample demo
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. 占쎌쥙?占썰펺?占쎌삕占쏙옙?占썲뜝?占쏙옙?占쎈벨?占썽넫臾믪굲2:51:19
 */
public class ApiMain {

	private static ApiMain Instance;

	public ApiMain() {
		// TODO Auto-generated constructor stub
	}

	public static ApiMain getInstance() {

		if (Instance == null) {
			Instance = new ApiMain();
		}

		return Instance;
	}

	public static IndexModel model = new IndexModel();

	public static Weather_VO vo = new Weather_VO();

	// Server settings
	private static final String SERVER_SCHEME = HttpHost.DEFAULT_SCHEME_NAME;
	private static final Integer SERVER_PORT = 80;

	private static final String SERVER_ENCODING = HTTP.UTF_8;
	private static final int HTTP_CONNECTION_TIMEOUT = 1000 * 30;
	private static final int HTTP_SOCKET_TIMEOUT = 1000 * 60;

	// KEY :: Life weather index search
	private static final String SERVICE_KEY_LIFE = "F325SMTAiagfOUZln1xw35wjiOw6bhVaiFa2HYndzBtO2bgaWsBh6vVIxsyReOTxLggq6kb/H1sVaQL+Qlwcjw==";

	// URI :: Life weather index search
	private static final String SERVER_HOST_LIFE = "203.247.66.146";
	private static final String SERVICE_URI_LIFE = "/iros/RetrieveLifeIndexService/";
	
	// URI :: Life weather index search :: Ultraviolet rays
	private static final String URI_LIFE_UV = SERVICE_URI_LIFE
			+ "getUltrvLifeList";//자외선
	private static final String URI_LIFE_DS = SERVICE_URI_LIFE
			+ "getDsplsLifeList";//불퀘지수
	private static final String URI_LIFE_SE = SERVICE_URI_LIFE
			+ "getSensorytemLifeList";//체감온도
	private static final String URI_LIFE_DU = SERVICE_URI_LIFE
			+ "getDustLifeList";//황사영향지수
	private static final String URI_LIFE_AIR = SERVICE_URI_LIFE
			+ "getAirpollutionLifeList";//대기오염지수
	private static final String URI_LIFE_INF = SERVICE_URI_LIFE
			+ "getInflWhoList";//감기지수

	// PARAMETER
	private static final String PARAM_SERVICE_KEY = "ServiceKey";
	private static final String PARAM_AREA_NO = "AreaNo";

	private String mSpotCode = "1111000000";

	/**
	 * Request click listener
	 */

	public void UltraApi() {

		if (mSpotCode != null) {
			// request
//			HttpGet request = new HttpGet(URI_LIFE_DS);
			HttpGet request = new HttpGet(URI_LIFE_UV);
//			HttpGet request = new HttpGet(URI_LIFE_UV);
//			HttpGet request = new HttpGet(URI_LIFE_UV);

			List<NameValuePair> params = new ArrayList<NameValuePair>(1);
			params.add(new BasicNameValuePair(PARAM_SERVICE_KEY,
					SERVICE_KEY_LIFE));
			params.add(new BasicNameValuePair(PARAM_AREA_NO, mSpotCode));

			doHttpTransaction(request, params);

			// �꾩튂瑜�Yh_AsyncWeather��static VO��Gps �뺣낫瑜��ｌ뼱以�떎.

		}
	}

	/**
	 * Do transaction
	 * 
	 * @param request
	 * @param params
	 */
	private void doHttpTransaction(HttpGet request, List<NameValuePair> params) {
		try {
			String path = request.getURI().toString();

			String query = (params == null || params.isEmpty()) ? null
					: URLEncodedUtils.format(params, SERVER_ENCODING);
			URI uri = URIUtils.createURI(SERVER_SCHEME, SERVER_HOST_LIFE,
					SERVER_PORT, path, query, null);
//			URI uri = new URI("http://203.247.66.146/iros/RetrieveLifeIndexService/getDsplsLifeList?ServiceKey=F325SMTAiagfOUZln1xw35wjiOw6bhVaiFa2HYndzBtO2bgaWsBh6vVIxsyReOTxLggq6kb%2FH1sVaQL%2BQlwcjw%3D%3D&AreaNo=1111000000");
			Log.v("URL", uri.toString());
			request.setURI(uri);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}

		SampleAsyncTask task = new SampleAsyncTask();
		task.execute(request); // 占쏙옙占쏙옙占쏙옙 START
	}

	/**
	 * 
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 9. 7. 占쎌쥙?占썰펺?占쎌삕占쏙옙?占썲뜝?占쏙옙?占쎈벨?占썽넫臾믪굲2:51:14
	 */

	private class SampleAsyncTask extends
			AsyncTask<HttpGet, Integer, WrapperResult> {
		private DefaultHttpClient mHttpClient;
		private HttpRequestBase mRequest;

		@Override
		protected WrapperResult doInBackground(HttpGet... params) {
			// register scheme
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", PlainSocketFactory
					.getSocketFactory(), 443));

			// http parameters
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.expect-continue", false);
			httpParams.setParameter("http.connection.timeout",
					HTTP_CONNECTION_TIMEOUT);
			httpParams.setParameter("http.socket.timeout", HTTP_SOCKET_TIMEOUT);

			HttpConnectionParams.setConnectionTimeout(httpParams,
					HTTP_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_SOCKET_TIMEOUT);

			// create connection manager
			ThreadSafeClientConnManager conman = new ThreadSafeClientConnManager(
					httpParams, schemeRegistry);

			// create HttpClient
			mHttpClient = new DefaultHttpClient(conman, httpParams);
			mRequest = params[0];

			HttpResponse response = null;
			try {
				// execute
				response = mHttpClient.execute(mRequest); // 占쏙옙占쏙옙占쏙옙 占쏙옙청占쏙옙
															// 占쏙옙

				// status code
				Log.d("ActMain", "doInBackground >>> " + "status code : "
						+ response.getStatusLine().getStatusCode());

				// SC_OK
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// response entity to string
					String responseToString = EntityUtils.toString(
							response.getEntity(), SERVER_ENCODING);

					return new WrapperResult().setObj(
							AbsResponse.fromXML(responseToString,
									UltraViolet.class))
							.setXml(responseToString);

				}
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(WrapperResult result) {
			try {
				if (result != null) {
					// Debug

//					model.setToday(((UltraViolet) result.getObj()).getBody()
//							.getIndexModel().getToday());
//
//					vo.setToday_UltraViolet(((UltraViolet) result.getObj())
//							.getBody().getIndexModel().getToday());
//
//					Log.v("dd", "자외선::" + model.getToday());
					Log.v("dd", "열지수1::" + model.getH15());
					Log.v("dd", "열지수2::" + model.getH3());
					Log.v("dd", "열지수3::" + model.getH9());
					Log.v("dd", "열지수4::" + model.getH15());
//					Log.v("dd","자외선::"+ (prettyPrint(result.getXml()).toString().concat(((UltraViolet) result
//											.getObj()).getBody()
//											.getIndexModel().getToday())));
//					Log.v("dd","열지수::"+ (prettyPrint(result.getXml()).toString().concat(((UltraViolet) result
//											.getObj()).getBody()
//											.getIndexModel().getH3())));
//					Log.d("dd", "자외선::" + (vo.getToday_UltraViolet()));

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}

	}

	/**
	 * Result wrapper
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 9. 7. 占쎌쥙?占썰펺?占쎌삕占쏙옙?占썲뜝?占쏙옙?占쎈벨?占썽넫臾믪굲:08:58
	 */
	private class WrapperResult {
		private Object obj;
		private String xml;

		public Object getObj() {
			return obj;
		}

		public WrapperResult setObj(Object obj) {
			this.obj = obj;
			return this;
		}

		public String getXml() {
			return xml;
		}

		public WrapperResult setXml(String xml) {
			this.xml = xml;
			return this;
		}
	}

	/**
	 * XML to string for debug
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private String prettyPrint(String in) throws Exception {
		// for debug
		Document xmlDoc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(in)));

		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		// create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(xmlDoc);
		trans.transform(source, result);

		return sw.toString();
	}
}
