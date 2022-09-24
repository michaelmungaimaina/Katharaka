package mich.gwan.katharaka.adapters.oil;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.oil.OilSaleListActivity;
import mich.gwan.katharaka.model.oil.OilSale;

public class OilSaleRecyclerAdapter extends RecyclerView.Adapter<OilSaleRecyclerAdapter.OilSaleViewHolder>{


    private final List<OilSale> listOilSale;
    private int index = RecyclerView.NO_POSITION;

    public OilSaleRecyclerAdapter(List<OilSale> listOilSale) {
        this.listOilSale = listOilSale;
    }

    @Override
    public OilSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_oilsale_recycler, parent, false);

        return new OilSaleViewHolder(itemView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(OilSaleViewHolder holder, int position) {
        holder.oilSaleDate.setText(listOilSale.get(position).getOilSaleDate());
        holder.oilSaleTime.setText(listOilSale.get(position).getOilSaleTime());
        holder.oilSaleCategory.setText(listOilSale.get(position).getOilSalecategory());
        holder.oilSaleQnty.setText(String.valueOf(listOilSale.get(position).getOilSaleQnty()));
        holder.oilSaleUprice.setText(String.valueOf(listOilSale.get(position).getOilSaleUprice()));
        holder.oilSaleTotal.setText(String.valueOf(listOilSale.get(position).getOilSaleTotal()));
        holder.oilSaleProfit.setText(String.valueOf(listOilSale.get(position).getOilSaleProfit()));

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
        Log.v(OilSaleRecyclerAdapter.class.getSimpleName(),""+listOilSale.size());
        return listOilSale.size();
    }


    /**
     * ViewHolder class
     */
    public class OilSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView oilSaleDate;
        public TextView oilSaleTime;
        public TextView oilSaleCategory;
        public TextView oilSaleQnty;
        public TextView oilSaleUprice;
        public TextView oilSaleTotal;
        public TextView oilSaleProfit;
        private OilSaleListActivity oilSale;

        public OilSaleViewHolder(View view) {
            super(view);
            oilSaleDate = (TextView) view.findViewById(R.id.textOilSaleDate);
            oilSaleTime = (TextView) view.findViewById(R.id.textOilSaleTime);
            oilSaleCategory = (TextView) view.findViewById(R.id.textOilSaleCategory);
            oilSaleQnty = (TextView) view.findViewById(R.id.textOilSaleQnty);
            oilSaleUprice = (TextView) view.findViewById(R.id.textOilSaleUprice);
            oilSaleTotal = (TextView) view.findViewById(R.id.textOilSaleTotal);
            oilSaleProfit = (TextView) view.findViewById(R.id.textOilSaleProfit);
            oilSale = new OilSaleListActivity();
        }
    }

}
