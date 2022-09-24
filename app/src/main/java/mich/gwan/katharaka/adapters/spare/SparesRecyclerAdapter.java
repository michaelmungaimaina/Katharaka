package mich.gwan.katharaka.adapters.spare;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.model.spare.Spare;

public class SparesRecyclerAdapter extends RecyclerView.Adapter<SparesRecyclerAdapter.SpareViewHolder>{

    private final List<Spare> listSpares;
    private int index = RecyclerView.NO_POSITION;

    public SparesRecyclerAdapter(List<Spare> listSpares) {
        this.listSpares = listSpares;
    }

    @Override
    public SpareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spare_recycler, parent, false);

        return new SpareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpareViewHolder holder, int position) {
        holder.spareId.setText(String.valueOf(listSpares.get(position).getSpare_id()));
        holder.spareCtgry.setText(listSpares.get(position).getSpare_category());
        //holder.spareQnty.setText(listSpares.get(position).getSpare_qnty());
        holder.spareBp.setText(String.valueOf(listSpares.get(position).getSpare_bp()));
        holder.spareSp.setText(String.valueOf(listSpares.get(position).getSpare_sp()));

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
        Log.v(SparesRecyclerAdapter.class.getSimpleName(),""+listSpares.size());
        return listSpares.size();
    }


    /**
     * ViewHolder class
     */
    public class SpareViewHolder extends RecyclerView.ViewHolder {

        public TextView spareId;
        public TextView spareCtgry;
        public TextView spareQnty;
        public TextView spareBp;
        public TextView spareSp;

        public SpareViewHolder(View view) {
            super(view);
            spareId = (TextView) view.findViewById(R.id.textOilId);
            spareCtgry = (TextView) view.findViewById(R.id.textOilCtgry);
            spareQnty = (TextView) view.findViewById(R.id.textOilQnty);
            spareBp = (TextView) view.findViewById(R.id.textOilBp);
            spareSp = (TextView) view.findViewById(R.id.textOilSp);
        }
    }
}
