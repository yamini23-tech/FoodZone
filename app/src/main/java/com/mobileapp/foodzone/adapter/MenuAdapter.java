package com.mobileapp.foodzone.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodzone.R;
import com.foodzone.model.MenuDO;

import java.util.List;

public class MenuAdapter extends BaseAdapter
{

	private Context context;
	private List<MenuDO> listMenu;

	public MenuAdapter(Context context, List<MenuDO> listMenu)
	{
		this.context       = context;
		this.listMenu    = listMenu;
	}
	
	@Override
	public int getCount() 
	{
		return listMenu.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listMenu.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
    
	public void refreshAdapter(List<MenuDO> listMenu)
	{
		this.listMenu = listMenu;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder viewHolder;
		MenuDO menuDO = listMenu.get(position);
		if(convertView==null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.menu_list_cell, null);
			viewHolder.ivMenuIcon 	    = (ImageView)convertView.findViewById(R.id.ivMenuIcon);
			viewHolder.tvMenuTitle 	    = (TextView)convertView.findViewById(R.id.tvMenuTitle);
			viewHolder.tvMenuTitle 	    = (TextView)convertView.findViewById(R.id.tvMenuTitle);

			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.ivMenuIcon.setImageResource(menuDO.icon);
		viewHolder.tvMenuTitle.setText(menuDO.name);

		return convertView;
	}
   public class ViewHolder
   {
	   TextView tvMenuTitle;
       ImageView ivMenuIcon;
	   
   }

}