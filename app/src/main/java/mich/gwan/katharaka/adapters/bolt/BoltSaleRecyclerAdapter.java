package mich.gwan.katharaka.adapters.bolt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.bolt.BoltSaleListActivity;
import mich.gwan.katharaka.model.bolt.BoltSale;

public class BoltSaleRecyclerAdapter extends RecyclerView.Adapter<BoltSaleRecyclerAdapter.BoltSaleViewHolder>{


    private final List<BoltSale> list;
    private int index = RecyclerView.NO_POSITION;

    public BoltSaleRecyclerAdapter(List<BoltSale> list) {
        this.list = list;
    }

    @Override
    public BoltSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_boltsale_recycler, parent, false);

        return new BoltSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BoltSaleViewHolder holder, int position) {
        holder.saleDate.setText(list.get(position).getBoltSaleDate());
        holder.saleTime.setText(list.get(position).getBoltSaleTime());
        holder.saleCategory.setText(list.get(position).getBoltSalecategory());
        holder.saleQnty.setText(String.valueOf(list.get(position).getBoltSaleQnty()));
        holder.saleUprice.setText(String.valueOf(list.get(position).getBoltSaleUprice()));
        holder.saleTotal.setText(String.valueOf(list.get(position).getBoltSaleTotal()));
        holder.saleProfit.setText(String.valueOf(list.get(position).getBoltSaleProfit()));

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
        Log.v(BoltSaleRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class BoltSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView saleDate;
        public TextView saleTime;
        public TextView saleCategory;
        public TextView saleQnty;
        public TextView saleUprice;
        public TextView saleTotal;
        public TextView saleProfit;
        private BoltSaleListActivity saleActivity;

        public BoltSaleViewHolder(View view) {
            super(view);
            saleDate = (TextView) view.findViewById(R.id.textBoltSaleDate);
            saleTime = (TextView) view.findViewById(R.id.textBoltSaleTime);
            saleCategory = (TextView) view.findViewById(R.id.textBoltSaleCategory);
            saleQnty = (TextView) view.findViewById(R.id.textBoltSaleQnty);
            saleUprice = (TextView) view.findViewById(R.id.textBoltSaleUprice);
            saleTotal = (TextView) view.findViewById(R.id.textBoltSaleTotal);
            saleProfit = (TextView) view.findViewById(R.id.textBoltSaleProfit);
            saleActivity = new BoltSaleListActivity();
        }
    }

}
