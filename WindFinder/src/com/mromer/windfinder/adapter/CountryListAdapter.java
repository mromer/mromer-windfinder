package com.mromer.windfinder.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.Country;

public class CountryListAdapter extends BaseAdapter implements OnClickListener{

	private Context context;

	private List<Country> listCountry;

	public CountryListAdapter(Context context, List<Country> listContinent) {
		this.context = context;
		this.listCountry = listContinent;
	}


	@Override
	public int getCount() {
		return listCountry.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listCountry.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Country countryName = listCountry.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(countryName.getName());        
		
		
		return convertView;
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}

}
