package mich.gwan.katharaka.adapters.welding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.welding.Welding;

public class WeldingRecyclerAdapter extends RecyclerView.Adapter<WeldingRecyclerAdapter.WeldingViewHolder>{

    private final List<Welding> list;
    private int index = RecyclerView.NO_POSITION;

    public WeldingRecyclerAdapter(List<Welding> list) {
        this.list = list;
    }

    @Override
    public WeldingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_welding_recycler, parent, false);

        return new WeldingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeldingViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getWelding_id()));
        holder.ctgry.setText(list.get(position).getWelding_category());
        //holder.qnty.setText(list.get(position).getWelding_qnty());
        holder.bp.setText(String.valueOf(list.get(position).getWelding_bp()));
        holder.sp.setText(String.valueOf(list.get(position).getWelding_sp()));

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
    public class WeldingViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView ctgry;
        public TextView qnty;
        public TextView bp;
        public TextView sp;

        public WeldingViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textWeldingId);
            ctgry = (TextView) view.findViewById(R.id.textWeldingCtgry);
            qnty = (TextView) view.findViewById(R.id.textWeldingQnty);
            bp = (TextView) view.findViewById(R.id.textWeldingBp);
            sp = (TextView) view.findViewById(R.id.textWeldingSp);
        }
    }
}
