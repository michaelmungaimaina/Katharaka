package mich.gwan.katharaka.adapters.bolt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.bolt.Bolts;

public class BoltRecyclerAdapter extends RecyclerView.Adapter<BoltRecyclerAdapter.BoltViewHolder>{

    private final List<Bolts> list;
    private int index = RecyclerView.NO_POSITION;

    public BoltRecyclerAdapter(List<Bolts> list) {
        this.list = list;
    }

    @Override
    public BoltViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bolt_recycler, parent, false);

        return new BoltViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BoltViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getBolt_id()));
        holder.ctgry.setText(list.get(position).getBolt_category());
        //holder.qnty.setText(list.get(position).getBolt_qnty());
        holder.bp.setText(String.valueOf(list.get(position).getBolt_bp()));
        holder.sp.setText(String.valueOf(list.get(position).getBolt_sp()));

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
    public class BoltViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView ctgry;
        public TextView qnty;
        public TextView bp;
        public TextView sp;

        public BoltViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textBoltId);
            ctgry = (TextView) view.findViewById(R.id.textBoltCtgry);
            qnty = (TextView) view.findViewById(R.id.textBoltQnty);
            bp = (TextView) view.findViewById(R.id.textBoltBp);
            sp = (TextView) view.findViewById(R.id.textBoltSp);
        }
    }
}
