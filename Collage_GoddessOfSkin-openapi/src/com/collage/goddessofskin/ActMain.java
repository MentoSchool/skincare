package com.collage.goddessofskin;

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

import com.collage.goddessofskin.model.response.AbsResponse;
import com.collage.goddessofskin.model.response.life.UltraViolet;

/**
 * Sample demo
 * 
 * @author jungho.song@kodeglam.com (threeword)
 * @since 2013. 9. 7. 占썬끋��12:51:19
 */
public class ActMain extends Activity
{
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
	private static final String URI_LIFE_UV = SERVICE_URI_LIFE + "getUltrvLifeList";
    
	// PARAMETER
	private static final String PARAM_SERVICE_KEY = "ServiceKey";
	private static final String PARAM_AREA_NO = "AreaNo";
	
	private EditText mSpotCodeView;
	private TextView mConsole;

	/**
	 * Request click listener
	 */
	View.OnClickListener reqeustOnClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (mConsole != null) mConsole.setText("");
			
			if (mSpotCodeView != null)
			{
				// request
				HttpGet request = new HttpGet(URI_LIFE_UV);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>(1);
				params.add(new BasicNameValuePair(PARAM_SERVICE_KEY, SERVICE_KEY_LIFE));
				params.add(new BasicNameValuePair(PARAM_AREA_NO, mSpotCodeView.getText().toString()));
				
				doHttpTransaction(request, params);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		mSpotCodeView = (EditText) findViewById(R.id.act_main_et_spot_code);
		mConsole = (TextView) findViewById(R.id.act_main_console);
		findViewById(R.id.act_main_btn_request).setOnClickListener(reqeustOnClickListener);
	}
	
	/**
	 * Do transaction
	 * 
	 * @param request
	 * @param params
	 */
	private void doHttpTransaction(HttpGet request, List<NameValuePair> params)
	{
		try
		{
			String path = request.getURI().toString();
			 
			String query = (params == null || params.isEmpty()) ? null : URLEncodedUtils.format(params, SERVER_ENCODING);
            URI uri = URIUtils.createURI(SERVER_SCHEME, SERVER_HOST_LIFE, SERVER_PORT, path, query, null);
            request.setURI(uri);;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		SampleAsyncTask task = new SampleAsyncTask();
		task.execute(request);
	}

	/**
	 * 
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 9. 7. 占썬끋��12:51:14
	 */
	private class SampleAsyncTask extends AsyncTask<HttpGet, Integer, WrapperResult>
	{
		private DefaultHttpClient mHttpClient;
		private HttpRequestBase mRequest;

		@Override
		protected WrapperResult doInBackground(HttpGet... params)
		{
			// register scheme
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));

			// http parameters
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.expect-continue", false);
			httpParams.setParameter("http.connection.timeout", HTTP_CONNECTION_TIMEOUT);
			httpParams.setParameter("http.socket.timeout", HTTP_SOCKET_TIMEOUT);

			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_SOCKET_TIMEOUT);

			// create connection manager
			ThreadSafeClientConnManager conman = new ThreadSafeClientConnManager(httpParams, schemeRegistry);

			// create HttpClient
			mHttpClient = new DefaultHttpClient(conman, httpParams);
			mRequest = params[0];

			HttpResponse response = null;
			try
			{
				// execute
				response = mHttpClient.execute(mRequest);

				// status code
				Log.d("ActMain", "doInBackground >>> " + "status code : " + response.getStatusLine().getStatusCode());
				
				// SC_OK
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					// response entity to string
					String responseToString = EntityUtils.toString(response.getEntity(), SERVER_ENCODING);
					
					// TODO :: XML 占쎈슣��占쏙옙占썬끇��占쎈챶諭띰옙占쏙옙�덈뮉 占쎈Ŧ��굜遺얜굡占쏙옙占쏙옙釉�占쎈뜆��筌ｌ꼶��(�⑤벀�э옙怨쀬뵠占쏙옙占싼뗪쉭占쏙옙占쎈벡��占쎌꼷堉깍옙�덈뮉 占쎈Ŧ���꾨뗀諭�
					return new WrapperResult()
					.setObj(AbsResponse.fromXML(responseToString, UltraViolet.class))
					.setXml(responseToString);
				}
			}
				// TODO :: 疫꿸퀡��http 占쎄낱源�占쎈Ŧ��筌ｌ꼶��			}
			catch (ClientProtocolException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(WrapperResult result)
		{
			try
			{
				if(result != null)
				{
					// Debug
					mConsole.setText(prettyPrint(result.getXml()));
					mConsole.setText(mConsole.getText().toString().concat("\n\ntoday :: " + ((UltraViolet)result.getObj()).getBody().getIndexModel().getToday()));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}
	}
	
	/**
	 * Result wrapper
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 9. 7. 占썬끋��2:08:58
	 */
	private class WrapperResult
	{
		private Object obj;
		private String xml;
		
		public Object getObj()
		{
			return obj;
		}
		public WrapperResult setObj(Object obj)
		{
			this.obj = obj;
			return this;
		}
		public String getXml()
		{
			return xml;
		}
		public WrapperResult setXml(String xml)
		{
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
	private String prettyPrint(String in) throws Exception
	{
		// for debug
		Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(in)));

		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");

		//create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(xmlDoc);
		trans.transform(source, result);
				
		return sw.toString();
	}
}
