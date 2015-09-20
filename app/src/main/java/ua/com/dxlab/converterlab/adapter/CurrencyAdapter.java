package ua.com.dxlab.converterlab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.com.dxlab.converterlab.R;
import ua.com.dxlab.converterlab.model.OrganizationCurrancy;

/**
 * Created by Dima on 17.09.2015.
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.RecyclerViewHolder> {

    private Context mContext;

    private List<OrganizationCurrancy> mCurrencies = new ArrayList<>();

    public CurrencyAdapter(Context _context) {
        super();
        this.mContext = _context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_currency, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        OrganizationCurrancy currency = mCurrencies.get(position);
        holder.title.setText(currency.getCurrency().getName());
        holder.ask.setText(currency.getAsk());
        holder.bid.setText(currency.getBid());
        formatImage(currency.getBid(), currency.getOldBid(), holder.bidImage);
        formatImage(currency.getAsk(), currency.getOldAsk(), holder.askImage);
    }

    private void formatImage(String _bid1, String _oldBid1, ImageView _bidImage) {
        if (_oldBid1 !=null){
            Double oldBid = Double.valueOf(_oldBid1);
            Double bid = Double.valueOf(_bid1);
            if (bid>oldBid){
                _bidImage.setImageResource(R.drawable.ic_red_arrow_down);
            }else if (bid<oldBid){
                _bidImage.setImageResource(R.drawable.ic_green_arrow_up);
            }else{
                _bidImage.setImageDrawable(null);
            }
        }else{
            _bidImage.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return mCurrencies.size();
    }

    public void setCurrencies(List<OrganizationCurrancy> _currencies) {
        this.mCurrencies = _currencies;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView ask;
        public ImageView askImage;
        public TextView bid;
        public ImageView bidImage;

        public RecyclerViewHolder(View _itemView) {
            super(_itemView);
            title = (TextView) _itemView.findViewById(R.id.currencyTitleText);
            ask = (TextView) _itemView.findViewById(R.id.curencyAskText);
            bid = (TextView) _itemView.findViewById(R.id.curencyBidText);
            askImage = (ImageView) _itemView.findViewById(R.id.curencyAskImage);
            bidImage = (ImageView) _itemView.findViewById(R.id.curencyBidImage);
        }
    }
}
