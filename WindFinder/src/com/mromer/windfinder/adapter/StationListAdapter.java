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
import com.mromer.windfinder.bean.Station;

public class StationListAdapter extends BaseAdapter implements OnClickListener{

	private Context context;

	private List<Station> listStation;

	public StationListAdapter(Context context, List<Station> listStation) {
		this.context = context;
		this.listStation = listStation;
	}


	@Override
	public int getCount() {
		return listStation.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listStation.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Station station = listStation.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(station.getName());        
		
		
		return convertView;
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}

}
