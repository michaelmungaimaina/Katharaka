package mich.gwan.katharaka.adapters.spanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.model.spanner.Spanner;

public class SpannerRecyclerAdapter extends RecyclerView.Adapter<SpannerRecyclerAdapter.SpannerViewHolder>{

    private final List<Spanner> list;
    private int index = RecyclerView.NO_POSITION;

    public SpannerRecyclerAdapter(List<Spanner> list) {
        this.list = list;
    }

    @Override
    public SpannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spanner_recycler, parent, false);

        return new SpannerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpannerViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getSpanner_id()));
        holder.ctgry.setText(list.get(position).getSpanner_category());
        //holder.qnty.setText(list.get(position).getSpanner_qnty());
        holder.bp.setText(String.valueOf(list.get(position).getSpanner_bp()));
        holder.sp.setText(String.valueOf(list.get(position).getSpanner_sp()));

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
    public class SpannerViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView ctgry;
        public TextView qnty;
        public TextView bp;
        public TextView sp;

        public SpannerViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.textSpannerId);
            ctgry = (TextView) view.findViewById(R.id.textSpannerCtgry);
            qnty = (TextView) view.findViewById(R.id.textSpannerQnty);
            bp = (TextView) view.findViewById(R.id.textSpannerBp);
            sp = (TextView) view.findViewById(R.id.textSpannerSp);
        }
    }
}
