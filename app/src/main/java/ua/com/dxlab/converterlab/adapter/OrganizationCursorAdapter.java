package ua.com.dxlab.converterlab.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.dxlab.converterlab.DetailActivity;
import ua.com.dxlab.converterlab.R;
import ua.com.dxlab.converterlab.util.PhoneUtils;

/**
 * Created by Dima on 16.09.2015.
 */
public class OrganizationCursorAdapter extends CursorRecyclerViewAdapter<OrganizationCursorAdapter.ViewHolder> implements View.OnClickListener {

    private Integer mSelectedPosition =-1;

    public OrganizationCursorAdapter(Context _context, Cursor _cursor) {
        super(_context, _cursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_organization, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor,int pos) {
        OrganizationListItem item = OrganizationListItem.fromCursor(cursor);
        viewHolder.title.setText(item.getTitle());
        viewHolder.region.setText(item.getRegion());
        viewHolder.address.setText(item.getAddress());
        viewHolder.city.setText(item.getCity());
        viewHolder.phone.setText(PhoneUtils.formatPhoneNumber(item.getPhone()));
        viewHolder.cardView.setTag(item.getId());
        viewHolder.cardView.setTag(R.id.titleText,pos);
        viewHolder.cardView.setOnClickListener(this);
        boolean selected = mSelectedPosition == pos;
        if (selected){
            viewHolder.contentLayout.setBackgroundResource(android.R.color.holo_blue_light);
        }else{
            viewHolder.contentLayout.setBackgroundResource(android.R.color.white);
        }

    }

    @Override
    public void onClick(View v) {
        Integer pos = (Integer) v.getTag(R.id.titleText);
        mSelectedPosition = pos;
        notifyItemChanged(pos);
        String id = (String) v.getTag();
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.ID, id);
        mContext.startActivity(intent);
    }

    public void clear() {
        int pos = mSelectedPosition;
        mSelectedPosition = -1;
        notifyItemChanged(pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView region;
        public TextView city;
        public TextView phone;
        public TextView address;
        public View cardView;
        public View contentLayout;

        public ViewHolder(View _view) {
            super(_view);
            _view.setClickable(true);
            title = (TextView) _view.findViewById(R.id.titleText);
            region = (TextView) _view.findViewById(R.id.regionText);
            city = (TextView) _view.findViewById(R.id.cityText);
            phone = (TextView) _view.findViewById(R.id.phoneText);
            address = (TextView) _view.findViewById(R.id.addressText);
            cardView = _view.findViewById(R.id.card_view);
            contentLayout = _view.findViewById(R.id.contentLayout);
        }
    }
}
