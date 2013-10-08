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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Sample demo
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. ï¿½ì¢?©ä¼Š?‹ì˜™ï¿½ï¿½?•å ?„í?ï¿½ëº§?™é†«ë¬’ì‚•2:51:19
 */
public class ActMain {

	public static Weather_VO vo = new Weather_VO();

	private static ActMain intance = new ActMain();

	public ActMain() {
		// TODO Auto-generated constructor stub
	}

	public static ActMain getInstance() {

		return intance;

	}

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
			+ "getUltrvLifeList";

	// PARAMETER
	private static final String PARAM_SERVICE_KEY = "ServiceKey";
	private static final String PARAM_AREA_NO = "AreaNo";

	private String mSpotCode = "1111000000";

	/**
	 * Request click listener
	 */

	public void onClick1() {

		if (mSpotCode != null) {
			// request
			HttpGet request = new HttpGet(URI_LIFE_UV);

			List<NameValuePair> params = new ArrayList<NameValuePair>(1);
			params.add(new BasicNameValuePair(PARAM_SERVICE_KEY,
					SERVICE_KEY_LIFE));
			params.add(new BasicNameValuePair(PARAM_AREA_NO, mSpotCode));

			doHttpTransaction(request, params);
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
			Log.v("URL", uri.toString());
			request.setURI(uri);
			;
		} catch (Exception e) {
			e.printStackTrace();
		}

		SampleAsyncTask task = new SampleAsyncTask();
		task.execute(request);
	}

	/**
	 * 
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 9. 7. ï¿½ì¢?©ä¼Š?‹ì˜™ï¿½ï¿½?•å ?„í?ï¿½ëº§?™é†«ë¬’ì‚•2:51:14
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
				response = mHttpClient.execute(mRequest);

				// status code
				Log.d("ActMain", "doInBackground >>> " + "status code : "
						+ response.getStatusLine().getStatusCode());

				// SC_OK
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// response entity to string
					String responseToString = EntityUtils.toString(
							response.getEntity(), SERVER_ENCODING);

					// TODO :: XML
					// ï¿½ì¢?©ä¼Š?‹ì˜™?ï¿½?‚ï¿½?œê³¸êµ²å ?Œì¥™ï¿½ëŒ?™ï¿½? ì‚•? ìˆ?†æ´ê¿¨ëœï¿½ëš¯ìª å ?¬êºˆï¿½ìˆ‹?™ï¿½?êµ²? ìŒì¥™ï¿½?ì˜™ï¿½ì– ?•å ?ˆë§¦ï¿½ã†?™è¢??•å ?Œë£Šï¿½ìˆ‹?™ï¿½?¹ë§¶ï¿½ì¢?©ï¿½?¸ì˜™ï§ì•¸?»å ?ˆëœ„ï¿½ìš…?ï§?ƒì‚•ï¿½ì¢?©ä¼Š?‹ì˜™é¤¨å«„??˜™?«ë¡«ì§—å ?ŒëŠ¸ï§Œê»“?™èª˜?£ï¿½ï¿½ì¢?©è£•ê¾¬ì˜™ï¿½ë†?•ï¿½?«âˆ¥ï§ì??ï¿½??‹²? ìŒë¹?¿½?‹ì˜™ï¿½ë¯?¡ï¿½ì¢ëŸ¥ï§ï¿½?™ï¿½??‚•ï¿½ãƒ«?¥ï¿½Îµ?ï¿½?ˆêº? ìŒ?¨ï¿½?¾ì˜™è«?¿½ë¸¸å£¤ê¹ì˜™ï¿½âˆ½?™ï¿½ï¿½ì•¾?™ï¿½?‰êµ²(? ìŒì¥™ï¿½ï¿½ë•Ÿ? ìˆ?›ï¿½ì¢ëŸ©ï¿½ëº?ï¿½?ˆì‚•? ì„?´ï¿½?¿ëœï¿½ì‡°ë§ªç‘—?©ëœï¿½ëš¯ìª ï¿½ë£¹ì‰»ï¿½ìˆ‹?™ï¿½?¹ë§¶ï¿½ì¢?©ï¿½ë½¬êµ¢ï¿½ë¥?•ï¿½ì¢ëŸ¥ï¿½ì‚£?ï¿½??“Œ? ìˆë²¨ï¿½?…ë„«æ¿¡ãƒ¬?…ï¿½ëº?»´ï¿½ìˆ‹?™ï¿½?©ì»? ìˆï¿½ï¿½?£ëœï¿½ë‰?? ?™ì˜™ï¿½ìˆ‹?™å ?ˆë¤ï¿½ì¢?©ï¿½ê·¨ì˜™ï¿½ë—­?•å ?ˆëœ†ï¿½ï¿½
					// // ï¿½ì¢?©ä¼Š?‹ì˜™é¤¨å«„??˜™?«ë¡«ì§—å ?Œëˆ¨ï¿½ì•¾?™è¢?¸ì³¥ï¿½ì¢ë£ï¿½?‹ì˜™? ìˆ?»ï¿½ì¢‘ì˜™
					return new WrapperResult().setObj(
							AbsResponse.fromXML(responseToString,
									UltraViolet.class))
							.setXml(responseToString);
				}
			}
			// TODO :: ï¿½ì¢?«çˆ°ê³»ì˜™ï¿½ì†?•å ?ŒëŸ©ï¿½ëº§?™é†«ë¤¿â?ttp
			// ï¿½ì¢?©ä¼Š?‹ì˜™ï¿½ìŒ?•ç–«??±¸ï¿½ë¨²?™é†«ë¡«ì‘•? ìŒë¹?¿½?‹ì˜™? ìŒ?•å ?Œëˆ¨ï¿½ì•¾?™è«­ï¿½ë¸¸å£¤ê¹?™ï¿½?½ì˜™ï¿½ï¿½?¾ì˜™ï¿½ë‰êµ?}
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
					IndexModel model = new IndexModel();
					model.setToday(((UltraViolet) result.getObj()).getBody()
							.getIndexModel().getToday());

					

					vo.setToday_UltraViolet(((UltraViolet) result.getObj())
							.getBody().getIndexModel().getToday());

					Log.v("item", "åª›ï¿½" + model.getToday());
					Log.v("item","åª›ï¿½"+ (prettyPrint(result.getXml()).toString().concat(((UltraViolet) result.getObj()).getBody().getIndexModel().getToday())));
					Log.d("ite", "åª›ï¿½ "+ (vo.getToday_UltraViolet()));

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
	 * @since 2013. 9. 7. ï¿½ì¢?©ä¼Š?‹ì˜™ï¿½ï¿½?•å ?„í?ï¿½ëº§?™é†«ë¬’ì‚•:08:58
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
