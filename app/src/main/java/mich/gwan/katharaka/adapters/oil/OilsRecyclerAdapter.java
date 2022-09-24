package mich.gwan.katharaka.adapters.oil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.oil.Oil;

public class OilsRecyclerAdapter extends RecyclerView.Adapter<OilsRecyclerAdapter.OilViewHolder>{

    private final List<Oil> listOils;
    private int index = RecyclerView.NO_POSITION;

    public OilsRecyclerAdapter(List<Oil> listOils) {
        this.listOils = listOils;
    }

    @Override
    public OilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_oil_recycler, parent, false);

        return new OilViewHolder(itemView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(OilViewHolder holder, int position) {
        holder.oilId.setText(String.valueOf(listOils.get(position).getOil_id()));
        holder.oilCtgry.setText(listOils.get(position).getOil_category());
        holder.oilQnty.setText("");
        holder.oilBp.setText(String.valueOf(listOils.get(position).getOil_bp()));
        holder.oilSp.setText(String.valueOf(listOils.get(position).getOil_sp()));
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
        /**holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if (index == position && holder.itemView.isSelected()){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }**/

    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listOils.size());
        return listOils.size();
    }


    /**
     * ViewHolder class
     */
    public static class OilViewHolder extends RecyclerView.ViewHolder {

        public TextView oilId;
        public TextView oilCtgry;
        public TextView oilQnty;
        public TextView oilBp;
        public TextView oilSp;

        public OilViewHolder(View view) {
            super(view);
            oilId = (TextView) view.findViewById(R.id.textOilId);
            oilCtgry = (TextView) view.findViewById(R.id.textOilCtgry);
            oilQnty = (TextView) view.findViewById(R.id.textOilQnty);
            oilBp = (TextView) view.findViewById(R.id.textOilBp);
            oilSp = (TextView) view.findViewById(R.id.textOilSp);
        }
    }
}
