package mich.gwan.katharaka.adapters.spare;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.spare.SpareSaleListActivity;
import mich.gwan.katharaka.model.spare.SpareSale;

public class SpareSaleRecyclerAdapter extends RecyclerView.Adapter<SpareSaleRecyclerAdapter.SpareSaleViewHolder>{


    private final List<SpareSale> listSpareSale;
    private int index = RecyclerView.NO_POSITION;

    public SpareSaleRecyclerAdapter(List<SpareSale> listSpareSale) {
        this.listSpareSale = listSpareSale;
    }

    @Override
    public SpareSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sparesale_recycler, parent, false);

        return new SpareSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpareSaleViewHolder holder, int position) {
        holder.spareSaleDate.setText(listSpareSale.get(position).getSpareSaleDate());
        holder.spareSaleTime.setText(listSpareSale.get(position).getSpareSaleTime());
        holder.spareSaleCategory.setText(listSpareSale.get(position).getSpareSalecategory());
        holder.spareSaleQnty.setText(String.valueOf(listSpareSale.get(position).getSpareSaleQnty()));
        holder.spareSaleUprice.setText(String.valueOf(listSpareSale.get(position).getSpareSaleUprice()));
        holder.spareSaleTotal.setText(String.valueOf(listSpareSale.get(position).getSpareSaleTotal()));
        holder.spareSaleProfit.setText(String.valueOf(listSpareSale.get(position).getSpareSaleProfit()));

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

    public int getTotalProfit(){
        int profit = 0;
        for (int i = 0; i < listSpareSale.size(); i++){
            profit += listSpareSale.get(i).getSpareSaleProfit();
        }
        return profit;
    }

    public  int getSumProfit(){
        int totalProfit = 0;
        for (int i = 0; i < listSpareSale.size(); i++){
            SpareSale profit = listSpareSale.get(i);
            int tprofit = profit.getSpareSaleProfit();
            totalProfit += tprofit;
        }
        return totalProfit;
    }

    @Override
    public int getItemCount() {
        Log.v(SpareSaleRecyclerAdapter.class.getSimpleName(),""+listSpareSale.size());
        return listSpareSale.size();
    }


    /**
     * ViewHolder class
     */
    public class SpareSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView spareSaleDate;
        public TextView spareSaleTime;
        public TextView spareSaleCategory;
        public TextView spareSaleQnty;
        public TextView spareSaleUprice;
        public TextView spareSaleTotal;
        public TextView spareSaleProfit;
        private SpareSaleListActivity spareSale;

        public SpareSaleViewHolder(View view) {
            super(view);
            spareSaleDate = (TextView) view.findViewById(R.id.textOilSaleDate);
            spareSaleTime = (TextView) view.findViewById(R.id.textOilSaleTime);
            spareSaleCategory = (TextView) view.findViewById(R.id.textOilSaleCategory);
            spareSaleQnty = (TextView) view.findViewById(R.id.textOilSaleQnty);
            spareSaleUprice = (TextView) view.findViewById(R.id.textOilSaleUprice);
            spareSaleTotal = (TextView) view.findViewById(R.id.textOilSaleTotal);
            spareSaleProfit = (TextView) view.findViewById(R.id.textOilSaleProfit);
            spareSale = new SpareSaleListActivity();
        }
    }

}
