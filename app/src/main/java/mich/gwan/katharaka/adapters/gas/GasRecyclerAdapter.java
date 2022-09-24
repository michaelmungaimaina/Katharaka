package mich.gwan.katharaka.adapters.gas;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.gas.Gas;

public class GasRecyclerAdapter extends RecyclerView.Adapter<GasRecyclerAdapter.GasViewHolder>{

    private final List<Gas> list;
    private int index = RecyclerView.NO_POSITION;

    public GasRecyclerAdapter(List<Gas> list) {
        this.list = list;
    }

    @Override
    public GasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gas_recycler, parent, false);

        return new GasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GasViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getGas_id()));
        holder.ctgry.setText(list.get(position).getGas_category());
        //holder.qnty.setText(list.get(position).getGas_qnty());
        holder.bp.setText(String.valueOf(list.get(position).getGas_bp()));
        holder.sp.setText(String.valueOf(list.get(position).getGas_sp()));

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
    public class GasViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView ctgry;
        public TextView qnty;
        public TextView bp;
        public TextView sp;

        public GasViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textgasId);
            ctgry = (TextView) view.findViewById(R.id.textGasCtgry);
            qnty = (TextView) view.findViewById(R.id.textGasQnty);
            bp = (TextView) view.findViewById(R.id.textGasBp);
            sp = (TextView) view.findViewById(R.id.textGasSp);
        }
    }
}
