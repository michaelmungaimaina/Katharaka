package mich.gwan.katharaka.adapters.powersaw;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.powersaw.Powersaw;

public class PowersawRecyclerAdapter extends RecyclerView.Adapter<PowersawRecyclerAdapter.PowersawViewHolder>{

    private final List<Powersaw> list;
    private int index = RecyclerView.NO_POSITION;

    public PowersawRecyclerAdapter(List<Powersaw> list) {
        this.list = list;
    }

    @Override
    public PowersawViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_powersaw_recycler, parent, false);

        return new PowersawViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PowersawViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getPowersaw_id()));
        holder.ctgry.setText(list.get(position).getPowersaw_category());
        //holder.qnty.setText(list.get(position).getPowersaw_qnty());
        holder.bp.setText(String.valueOf(list.get(position).getPowersaw_bp()));
        holder.sp.setText(String.valueOf(list.get(position).getPowersaw_sp()));

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
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+list.size());
        return list.size();
    }


    /**
     * ViewHolder class
     */
    public class PowersawViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView ctgry;
        public TextView qnty;
        public TextView bp;
        public TextView sp;

        public PowersawViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textPowersawId);
            ctgry = (TextView) view.findViewById(R.id.textPowersawCtgry);
            qnty = (TextView) view.findViewById(R.id.textPowersawQnty);
            bp = (TextView) view.findViewById(R.id.textPowersawBp);
            sp = (TextView) view.findViewById(R.id.textPowersawSp);
        }
    }
}
