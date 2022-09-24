package mich.gwan.katharaka.adapters.spanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.spanner.SpannerSaleListActivity;
import mich.gwan.katharaka.model.spanner.SpannerSale;

public class SpannerSaleRecyclerAdapter extends RecyclerView.Adapter<SpannerSaleRecyclerAdapter.SpannerSaleViewHolder>{


    private final List<SpannerSale> list;
    private int index = RecyclerView.NO_POSITION;

    public SpannerSaleRecyclerAdapter(List<SpannerSale> list) {
        this.list = list;
    }

    @Override
    public SpannerSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spannersale_recycler, parent, false);

        return new SpannerSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpannerSaleViewHolder holder, int position) {
        holder.saleDate.setText(list.get(position).getSpannerSaleDate());
        holder.saleTime.setText(list.get(position).getSpannerSaleTime());
        holder.saleCategory.setText(list.get(position).getSpannerSalecategory());
        holder.saleQnty.setText(String.valueOf(list.get(position).getSpannerSaleQnty()));
        holder.saleUprice.setText(String.valueOf(list.get(position).getSpannerSaleUprice()));
        holder.saleTotal.setText(String.valueOf(list.get(position).getSpannerSaleTotal()));
        holder.saleProfit.setText(String.valueOf(list.get(position).getSpannerSaleProfit()));

        holder.itemView.setSelected(index == position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v(SpannerSaleRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class SpannerSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView saleDate;
        public TextView saleTime;
        public TextView saleCategory;
        public TextView saleQnty;
        public TextView saleUprice;
        public TextView saleTotal;
        public TextView saleProfit;
        private SpannerSaleListActivity saleActivity;

        public SpannerSaleViewHolder(View view) {
            super(view);
            saleDate = (TextView) view.findViewById(R.id.textSpannerSaleDate);
            saleTime = (TextView) view.findViewById(R.id.textSpannerSaleTime);
            saleCategory = (TextView) view.findViewById(R.id.textSpannerSaleCategory);
            saleQnty = (TextView) view.findViewById(R.id.textSpannerSaleQnty);
            saleUprice = (TextView) view.findViewById(R.id.textSpannerSaleUprice);
            saleTotal = (TextView) view.findViewById(R.id.textSpannerSaleTotal);
            saleProfit = (TextView) view.findViewById(R.id.textSpannerSaleProfit);
            saleActivity = new SpannerSaleListActivity();
        }
    }

}
