package com.example.contactsv015;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.List;
import java.util.Map;

/**
 * Created by Александр on 23.02.2017.
 */

public class MyExListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<GroupData> groups ;
    //Map<String,List<String>> topics;

    public MyExListAdapter(Context context, List<GroupData> groups) {
        this.context = context;
        this.groups = groups;
        //this.topics = topics;
    }
    @Override
    public int getGroupCount() { // col-vo group;
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getContacts().size();
    }

    @Override
    public GroupData getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getContacts().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String lang =   getGroup(groupPosition).getName();
        if ((convertView==null)){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent,null);
        }
        TextView txtParent = (TextView) convertView.findViewById(R.id.lblParent);
        txtParent.setText(lang);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String topic =  getChild(groupPosition,childPosition);
        if ((convertView==null)){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child,null);
        }

        TextView txtChild = (TextView) convertView.findViewById(R.id.txtChild);
        txtChild.setText(topic);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
