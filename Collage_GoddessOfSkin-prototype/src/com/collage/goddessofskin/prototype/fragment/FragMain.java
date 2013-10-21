package com.collage.goddessofskin.prototype.fragment;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.collage.goddessofskin.prototype.R;
import com.collage.goddessofskin.prototype.db.AreaNoDB;
import com.collage.goddessofskin.prototype.db.DBUtils;
import com.collage.goddessofskin.prototype.location.LocationUtils;
import com.collage.goddessofskin.prototype.model.response.AbsResponse;
import com.collage.goddessofskin.prototype.model.response.life.UltraViolet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class FragMain extends Fragment
{
	private static final String TAG = FragMain.class.getSimpleName();
	
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
	
	private FragmentActivity mContext;
	
	private ProgressBar mActivityIndicator;
    private TextView mLatLng;
    private TextView mAddress;
	private TextView mConnectionState;
    private TextView mConnectionStatus;
	private Button mCurrentBtn;
	private EditText mAreaCodeTV;
	private Button mAreaBtn;
	private TextView mConsole;
	
	private AreaNoDB mAreaNoDB;
	
	private LocationRequest mLocationRequest;
	// Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    
    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;
	
	private View.OnClickListener onCurrentClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			doRequestUltraVioletWithAreaNo();
		}
	};
	
	private View.OnClickListener onAreaClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			doRequestUltraViolet();
		}
	};
	
	private LocationListener mLocationListener = new LocationListener()
	{
		@Override
		public void onLocationChanged(Location location)
		{
			 // Report to the UI that the location was updated
	        mConnectionStatus.setText(R.string.location_updated);

	        // In the UI, set the latitude and longitude to the value received
	        mLatLng.setText(LocationUtils.getLatLng(mContext, location));
		}
	};
	
	private GooglePlayServicesClient.ConnectionCallbacks mConnectionCallbacks = new GooglePlayServicesClient.ConnectionCallbacks()
	{
		@Override
		public void onDisconnected()
		{
			mConnectionStatus.setText(R.string.disconnected);
		}
		
		@Override
		public void onConnected(Bundle bundle)
		{
			mConnectionStatus.setText(R.string.connected);
			
	        if (mUpdatesRequested) {
	            startPeriodicUpdates();
	        }
		}
	};
	
	private GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener = new GooglePlayServicesClient.OnConnectionFailedListener()
	{
		@Override
		public void onConnectionFailed(ConnectionResult connectionResult)
		{
			 /*
	         * Google Play services can resolve some errors it detects.
	         * If the error has a resolution, try sending an Intent to
	         * start a Google Play services activity that can resolve
	         * error.
	         */
			if (connectionResult.hasResolution())
			{
				try
				{
	                // Start an Activity that tries to resolve the error
					connectionResult.startResolutionForResult(mContext, LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

	                /*
	                * Thrown if Google Play services canceled the original
	                * PendingIntent
	                */
				}
				catch (IntentSender.SendIntentException e)
				{
					// Log the error
					e.printStackTrace();
				}
			}
			else
			{
	            // If no resolution is available, display a dialog to the user with the error.
	            showErrorDialog(connectionResult.getErrorCode());
	        }
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.frag_main, container, false);
		
		mContext = (FragmentActivity) getActivity();
		
		mActivityIndicator = (ProgressBar) rootView.findViewById(R.id.frag_main_progress);
        mLatLng = (TextView) rootView.findViewById(R.id.frag_main_lat_lng);
        mAddress = (TextView) rootView.findViewById(R.id.frag_main_address);
		mConnectionState = (TextView) rootView.findViewById(R.id.frag_main_connection_state);
		mConnectionStatus = (TextView) rootView.findViewById(R.id.frag_main_connection_status);
		mCurrentBtn = (Button) rootView.findViewById(R.id.frag_main_btn_current);
		mAreaCodeTV = (EditText) rootView.findViewById(R.id.frag_main_et_area_code);
		mAreaBtn = (Button) rootView.findViewById(R.id.frag_main_btn_area);
		mConsole = (TextView) rootView.findViewById(R.id.frag_main_console); 
		
		mCurrentBtn.setOnClickListener(onCurrentClickListener);
		mAreaBtn.setOnClickListener(onAreaClickListener);
		
		// DB 복사
		try
		{
			// DB가 있는지?			
			boolean bResult = DBUtils.isCheckDB(mContext);
			// DB가 없으면 복사
			if (!bResult) DBUtils.copyDB(mContext);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	      
		mAreaNoDB = new AreaNoDB(mContext);
		
		// Location
		// Create a new global location parameters object
        mLocationRequest = LocationRequest.create();
		
        /*
         * Set the update interval
         */
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;

        // Open Shared Preferences
        mPrefs = mContext.getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(mContext, mConnectionCallbacks, onConnectionFailedListener);
        
		return rootView;
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		/*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();
	}
	
	@Override
	public void onStop()
	{
		 // If the client is connected
        if (mLocationClient.isConnected()) stopPeriodicUpdates();

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();
        
		super.onStop();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		// If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

        // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }
	}
	
	@Override
	public void onPause()
	{
		// Save the current setting for updates
        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
        mEditor.commit();
        
		super.onPause();
	}
	
	/*
     * Handle results returned to this Activity by other Activities started with
     * startActivityForResult(). In particular, the method onConnectionFailed() in
     * LocationUpdateRemover and LocationUpdateRequester may call startResolutionForResult() to
     * start an Activity that handles Google Play services problems. The result of this
     * call returns here, to onActivityResult.
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		// Choose what to do based on the request code
		switch (requestCode)
		{
			// If the request code matches the code sent in onConnectionFailed
			case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :
			{
				switch (resultCode)
				{
					// If Google Play services resolved the problem
					case Activity.RESULT_OK:
					{
						// Log the result
						Log.d(LocationUtils.APPTAG, getString(R.string.resolved));

						// Display the result
						mConnectionState.setText(R.string.connected);
						mConnectionStatus.setText(R.string.resolved);
					}
					break;

					// If any other result was returned by Google Play services
					default:
					{
						// Log the result
						Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));

						// Display the result
						mConnectionState.setText(R.string.disconnected);
						mConnectionStatus.setText(R.string.no_resolution);
					}
					break;
				}
			}
			break;
			
			// If any other request code was received
			default:
			{
				// Report that this Activity received an unknown requestCode
				Log.d(LocationUtils.APPTAG, getString(R.string.unknown_activity_request_code, requestCode));
			}
			break;
		}
	}
	
	/**********************************************************************************************
	 * LIFE ***************************************************************************************
	 **********************************************************************************************/
	
	private void doRequestUltraViolet()
	{
		if (mConsole != null) mConsole.setText("");
		if (!TextUtils.isEmpty(mAreaCodeTV.getText()))
		{
			// request
			HttpGet request = new HttpGet(URI_LIFE_UV);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>(1);
			params.add(new BasicNameValuePair(PARAM_SERVICE_KEY, SERVICE_KEY_LIFE));
			params.add(new BasicNameValuePair(PARAM_AREA_NO, mAreaCodeTV.getText().toString()));
			
			doHttpTransaction(request, params);
		}
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

		SimpleAsyncTask task = new SimpleAsyncTask();
		task.execute(request);
	}

	/**
	 * 
	 * 
	 * @author jungho.song@kodeglam.com (threeword)
	 * @since 2013. 10. 21. 오후 8:05:28
	 */
	private class SimpleAsyncTask extends AsyncTask<HttpGet, Integer, WrapperResult>
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
					
					return new WrapperResult()
					.setObj(AbsResponse.fromXML(responseToString, UltraViolet.class))
					.setXml(responseToString);
				}
			}
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
	 * @since 2013. 10. 21. 오후 8:05:15
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
	
	/**********************************************************************************************
	 * LOCATION SERVICE ***************************************************************************
	 **********************************************************************************************/
	/**
     * Invoked by the "Get Address" button.
     * Get the address of the current location, using reverse geocoding. This only works if
     * a geocoding service is available.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    // For Eclipse with ADT, suppress warnings about Geocoder.isPresent()
    @SuppressLint("NewApi")
    private void doRequestUltraVioletWithAreaNo() 
    {
        // In Gingerbread and later, use Geocoder.isPresent() to see if a geocoder is available.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && !Geocoder.isPresent())
        {
            // No geocoder is present. Issue an error message
            Toast.makeText(mContext, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
            return;
        }

        if (servicesConnected()) 
        {
            // Get the current location
            Location currentLocation = mLocationClient.getLastLocation();
            
            // Display the current location in the UI
            mLatLng.setText(LocationUtils.getLatLng(mContext, currentLocation));

            // Turn the indefinite activity indicator on
            mActivityIndicator.setVisibility(View.VISIBLE);

            // Start the background task
            (new GetAddressTask(mContext)).execute(currentLocation);
        }
    }
    
    /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
	private boolean servicesConnected()
	{
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode)
		{
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

			// Continue
			return true;
			// Google Play services was not available for some reason
		}
		else
		{
			// Display an error dialog
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, mContext, 0);
			if (dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(mContext.getSupportFragmentManager(), LocationUtils.APPTAG);
			}
			return false;
		}
	}
    
	 /**
     * In response to a request to start updates, send a request
     * to Location Services
     */
	private void startPeriodicUpdates()
	{
		mLocationClient.requestLocationUpdates(mLocationRequest, mLocationListener);
		mConnectionState.setText(R.string.location_requested);
	}

    /**
     * In response to a request to stop updates, send a request to
     * Location Services
     */
	private void stopPeriodicUpdates()
	{
		mLocationClient.removeLocationUpdates(mLocationListener);
		mConnectionState.setText(R.string.location_updates_stopped);
	}
    
	/**
     * An AsyncTask that calls getFromLocation() in the background.
     * The class uses the following generic types:
     * Location - A {@link android.location.Location} object containing the current location,
     *            passed as the input parameter to doInBackground()
     * Void     - indicates that progress units are not used by this subclass
     * String   - An address passed to onPostExecute()
     */
    protected class GetAddressTask extends AsyncTask<Location, Void, String> {

        // Store the context passed to the AsyncTask when the system instantiates it.
        Context localContext;

        // Constructor called by the system to instantiate the task
        public GetAddressTask(Context context) {

            // Required by the semantics of AsyncTask
            super();

            // Set a Context for the background task
            localContext = context;
        }

        /**
         * Get a geocoding service instance, pass latitude and longitude to it, format the returned
         * address, and return the address to the UI thread.
         */
        @Override
        protected String doInBackground(Location... params) {
            /*
             * Get a new geocoding service instance, set for localized addresses. This example uses
             * android.location.Geocoder, but other geocoders that conform to address standards
             * can also be used.
             */
            Geocoder geocoder = new Geocoder(localContext, Locale.getDefault());

            // Get the current location from the input parameter list
            Location location = params[0];

            // Create a list to contain the result address
            List <Address> addresses = null;

            // Try to get an address for the current location. Catch IO or network problems.
            try {

                /*
                 * Call the synchronous getFromLocation() method with the latitude and
                 * longitude of the current location. Return at most 1 address.
                 */
                addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1
                );

                // Catch network or other I/O problems.
                } catch (IOException exception1) {

                    // Log an error and return an error message
                    Log.e(LocationUtils.APPTAG, getString(R.string.IO_Exception_getFromLocation));

                    // print the stack trace
                    exception1.printStackTrace();

                    // Return an error message
                    return (getString(R.string.IO_Exception_getFromLocation));

                // Catch incorrect latitude or longitude values
                } catch (IllegalArgumentException exception2) {

                    // Construct a message containing the invalid arguments
                    String errorString = getString(
                            R.string.illegal_argument_exception,
                            location.getLatitude(),
                            location.getLongitude()
                    );
                    // Log the error and print the stack trace
                    Log.e(LocationUtils.APPTAG, errorString);
                    exception2.printStackTrace();

                    //
                    return errorString;
                }
                // If the reverse geocode returned an address
                if (addresses != null && addresses.size() > 0) {

                    // Get the first address
                    Address address = addresses.get(0);

                    /*
                    // Format the first line of address
                    String addressText = getString(R.string.address_output_string,

                            // If there's a street address, add it
                            address.getMaxAddressLineIndex() > 0 ?
                                    address.getAddressLine(0) : "",

                            // Locality is usually a city
                            address.getLocality(),

                            // The country of the address
                            address.getCountryName()
                    );
                    */
                    
                    String addressText = address.getAddressLine(0);

                    // Return the text
                    return addressText;

                // If there aren't any addresses, post a message
                } else {
                  return getString(R.string.no_address_found);
                }
        }

        /**
         * A method that's called once doInBackground() completes. Set the text of the
         * UI element that displays the address. This method runs on the UI thread.
         */
        @Override
        protected void onPostExecute(String address) {

            // Turn off the progress bar
            mActivityIndicator.setVisibility(View.GONE);
        	
        	// Set the address in the UI
            mAddress.setText(address);
            
            // TODO
            String [] addressSplits = address.split(" ");
            int areaNo = mAreaNoDB.getAreaNo(addressSplits[1], addressSplits[2], addressSplits[3]);
            
            // Set the address in the UI
            mAreaCodeTV.setText(String.valueOf(areaNo));
            
            doRequestUltraViolet();
        }
    }

    /**
     * Show a dialog returned by Google Play services for the
     * connection error code
     *
     * @param errorCode An error code returned from onConnectionFailed
     */
    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            mContext,
            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(mContext.getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }

    /**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
