package mich.gwan.katharaka.adapters.gas;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.gas.GasSaleListActivity;
import mich.gwan.katharaka.model.gas.GasSale;

public class GasSaleRecyclerAdapter extends RecyclerView.Adapter<GasSaleRecyclerAdapter.GasSaleViewHolder>{


    private final List<GasSale> list;
    private int index = RecyclerView.NO_POSITION;

    public GasSaleRecyclerAdapter(List<GasSale> list) {
        this.list = list;
    }

    @Override
    public GasSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gassale_recycler, parent, false);

        return new GasSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GasSaleViewHolder holder, int position) {
        holder.saleDate.setText(list.get(position).getGasSaleDate());
        holder.saleTime.setText(list.get(position).getGasSaleTime());
        holder.saleCategory.setText(list.get(position).getGasSalecategory());
        holder.saleQnty.setText(String.valueOf(list.get(position).getGasSaleQnty()));
        holder.saleUprice.setText(String.valueOf(list.get(position).getGasSaleUprice()));
        holder.saleTotal.setText(String.valueOf(list.get(position).getGasSaleTotal()));
        holder.saleProfit.setText(String.valueOf(list.get(position).getGasSaleProfit()));

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
        Log.v(GasSaleRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class GasSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView saleDate;
        public TextView saleTime;
        public TextView saleCategory;
        public TextView saleQnty;
        public TextView saleUprice;
        public TextView saleTotal;
        public TextView saleProfit;
        private GasSaleListActivity saleActivity;

        public GasSaleViewHolder(View view) {
            super(view);
            saleDate = (TextView) view.findViewById(R.id.textGasSaleDate);
            saleTime = (TextView) view.findViewById(R.id.textGasSaleTime);
            saleCategory = (TextView) view.findViewById(R.id.textGasSaleCategory);
            saleQnty = (TextView) view.findViewById(R.id.textGasSaleQnty);
            saleUprice = (TextView) view.findViewById(R.id.textGasSaleUprice);
            saleTotal = (TextView) view.findViewById(R.id.textGasSaleTotal);
            saleProfit = (TextView) view.findViewById(R.id.textGasSaleProfit);
            saleActivity = new GasSaleListActivity();
        }
    }

}
