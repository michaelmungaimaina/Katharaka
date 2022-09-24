package mich.gwan.katharaka.adapters.welding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.gas.GasSaleListActivity;
import mich.gwan.katharaka.model.welding.WeldingSale;

public class WeldingSaleRecyclerAdapter extends RecyclerView.Adapter<WeldingSaleRecyclerAdapter.WeldingSaleViewHolder>{


    private final List<WeldingSale> list;
    private int index = RecyclerView.NO_POSITION;

    public WeldingSaleRecyclerAdapter(List<WeldingSale> list) {
        this.list = list;
    }

    @Override
    public WeldingSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weldingsale_recycler, parent, false);

        return new WeldingSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeldingSaleViewHolder holder, int position) {
        holder.saleDate.setText(list.get(position).getWeldingSaleDate());
        holder.saleTime.setText(list.get(position).getWeldingSaleTime());
        holder.saleCategory.setText(list.get(position).getWeldingSalecategory());
        holder.saleQnty.setText(String.valueOf(list.get(position).getWeldingSaleQnty()));
        holder.saleUprice.setText(String.valueOf(list.get(position).getWeldingSaleUprice()));
        holder.saleTotal.setText(String.valueOf(list.get(position).getWeldingSaleTotal()));
        holder.saleProfit.setText(String.valueOf(list.get(position).getWeldingSaleProfit()));

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
        Log.v(WeldingSaleRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class WeldingSaleViewHolder extends RecyclerView.ViewHolder {

        public TextView saleDate;
        public TextView saleTime;
        public TextView saleCategory;
        public TextView saleQnty;
        public TextView saleUprice;
        public TextView saleTotal;
        public TextView saleProfit;
        private GasSaleListActivity saleActivity;

        public WeldingSaleViewHolder(View view) {
            super(view);
            saleDate = (TextView) view.findViewById(R.id.textWeldingSaleDate);
            saleTime = (TextView) view.findViewById(R.id.textWeldingSaleTime);
            saleCategory = (TextView) view.findViewById(R.id.textWeldingSaleCategory);
            saleQnty = (TextView) view.findViewById(R.id.textWeldingSaleQnty);
            saleUprice = (TextView) view.findViewById(R.id.textWeldingSaleUprice);
            saleTotal = (TextView) view.findViewById(R.id.textWeldingSaleTotal);
            saleProfit = (TextView) view.findViewById(R.id.textWeldingSaleProfit);
            saleActivity = new GasSaleListActivity();
        }
    }

}
