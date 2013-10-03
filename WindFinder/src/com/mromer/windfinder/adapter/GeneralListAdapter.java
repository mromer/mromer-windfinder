package com.mromer.windfinder.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mromer.windfinder.R;
import com.mromer.windfinder.bean.DataType;

public class GeneralListAdapter extends BaseAdapter implements Filterable {

	private Context context;

	private List<DataType> listData;
	private List<DataType> listDataFiltered;	

	public GeneralListAdapter(Context context, List<DataType> listData) {
		this.context = context;
		
		this.listData = listData;		
		
		listDataFiltered = listData;
	}


	@Override
	public int getCount() {
		return listDataFiltered.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listDataFiltered.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		DataType data = listDataFiltered.get(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		//due to italic text style				
		name.setText(data.getName() + " ");		

		return convertView;
	}
	

	@Override
	public Filter getFilter() {

		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {

				listDataFiltered = (List<DataType>) results.values;
				notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				FilterResults results = new FilterResults();
				ArrayList<DataType> filteredData = new ArrayList<DataType>();

				// perform your search here using the searchConstraint String.

				constraint = constraint.toString().toLowerCase(Locale.getDefault());

				for (int i = 0; i < listData.size(); i++) {
					DataType data = listData.get(i);
					if (data.getName().toLowerCase(Locale.getDefault()).contains(constraint.toString())) {
						filteredData.add(data);
					}
				}

				results.count = filteredData.size();
				results.values = filteredData;

				return results;				
			}			
		};

		return filter;
	}


}
