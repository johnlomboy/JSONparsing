package com.example.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {

	private static String url = "http://api.androidhive.info/contacts/";

	private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_PHONE_MOBILE = "mobile";
	private static final String TAG_PHONE_HOME = "home";
	private static final String TAG_PHONE_OFFICE = "office";

	JSONArray contacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			contacts = json.getJSONArray(TAG_CONTACTS);

			for (int i = 0; i < contacts.length(); i++) {
				JSONObject c = contacts.getJSONObject(i);

				// Storing each json item in variable
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				String email = c.getString(TAG_EMAIL);
				String address = c.getString(TAG_ADDRESS);
				String gender = c.getString(TAG_GENDER);

				// Phone number is again JSON Object
				JSONObject phone = c.getJSONObject(TAG_PHONE);
				String mobile = phone.getString(TAG_PHONE_MOBILE);
				String home = phone.getString(TAG_PHONE_HOME);
				String office = phone.getString(TAG_PHONE_OFFICE);

				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				map.put(TAG_EMAIL, email);
				map.put(TAG_ADDRESS, address);
				map.put(TAG_GENDER, gender);
				map.put(TAG_PHONE_HOME, home);
				map.put(TAG_PHONE_MOBILE, mobile);
				map.put(TAG_PHONE_OFFICE, office);

				// adding HashList to ArrayList
				contactList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item, new String[] { TAG_ID, TAG_NAME, TAG_EMAIL,
						TAG_ADDRESS, TAG_GENDER, TAG_PHONE_HOME,
						TAG_PHONE_MOBILE, TAG_PHONE_OFFICE }, new int[] {
						R.id.id, R.id.name, R.id.email, R.id.address,
						R.id.gender, R.id.home, R.id.mobile, R.id.office });

		setListAdapter(adapter);
	}
}
