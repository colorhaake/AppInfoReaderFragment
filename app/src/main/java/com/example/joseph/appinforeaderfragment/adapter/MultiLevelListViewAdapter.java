package com.example.joseph.appinforeaderfragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.appinforeaderfragment.R;
import com.example.joseph.appinforeaderfragment.data.Item;
import com.example.joseph.appinforeaderfragment.data.ItemGroup;

import java.util.List;
import java.util.ListIterator;

public class MultiLevelListViewAdapter extends ArrayAdapter<Item> {

    private class ViewHolder {
        ImageView expand_indicator_image;
        TextView multi_level_text;
        LinearLayout expand_layout;
    }

    int layoutResource;
    public MultiLevelListViewAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.expand_indicator_image = (ImageView)convertView.findViewById(R.id.expand_indicator_image);
            viewHolder.multi_level_text = (TextView)convertView.findViewById(R.id.multi_level_text);
            viewHolder.expand_layout = (LinearLayout)convertView.findViewById(R.id.expand_layout);

            // add children data
            View view = getChildrenExpandView(item);
            viewHolder.expand_layout.addView(view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (item.isGroup()) {
            item.icon = ContextCompat.getDrawable(getContext(), (item.isExpanded) ? R.mipmap.ic_action_navigation_arrow_drop_down : R.mipmap.ic_action_navigation_arrow_right);
            viewHolder.expand_indicator_image.setImageDrawable(item.icon);
        }
        viewHolder.multi_level_text.setText(item.label);

        viewHolder.expand_layout.setVisibility((item.isExpanded)? View.VISIBLE : View.GONE);
        return convertView;
    }

    private View getChildrenExpandView(Item parent) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setDuplicateParentStateEnabled(true);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 0, 0);
        //layout.setOnClickListener(onClickListener);

        ListIterator listIterator = ((ItemGroup)parent).iterateItems();
        while (listIterator.hasNext()) {
            Item child_item = (Item)listIterator.next();
            layout.addView(getExpandView(child_item), params);
        }

        return layout;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Click layout", Toast.LENGTH_SHORT).show();
        }
    };
    private View getExpandView(Item item) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(this.layoutResource, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.expand_indicator_image);
        TextView textView = (TextView)view.findViewById(R.id.multi_level_text);
        textView.setText(item.label);

        imageView.setVisibility(View.INVISIBLE);
        if (!item.isGroup()) {
            return view;
        }

        if (!item.isExpanded) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_action_navigation_arrow_right));

            return view;
        }

        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_action_navigation_arrow_drop_down));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = (LinearLayout)view.findViewById(R.id.expand_layout);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 0, 0);

        ListIterator listIterator = ((ItemGroup)item).iterateItems();
        while (listIterator.hasNext()) {
            Item child_item = (Item)listIterator.next();
            layout.addView(getExpandView(child_item), params);
        }

        return view;
    }
}
