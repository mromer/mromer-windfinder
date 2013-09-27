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
import com.mromer.windfinder.bean.Region;

public class RegionListAdapter extends BaseAdapter implements OnClickListener{

	private Context context;

	private List<Region> listRegion;

	public RegionListAdapter(Context context, List<Region> listRegion) {
		this.context = context;
		this.listRegion = listRegion;
	}


	@Override
	public int getCount() {
		return listRegion.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listRegion.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Region region = listRegion.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(region.getName());        
		
		
		return convertView;
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}

}
