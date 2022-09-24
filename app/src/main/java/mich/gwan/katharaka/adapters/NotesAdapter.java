package mich.gwan.katharaka.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.model.pdf.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context context;
    private final List<Note> notesList;
    private int index = RecyclerView.NO_POSITION;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView dot;
        public TextView name;
        public TextView paid;
        public TextView unpaid;
        public TextView total;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
            name = view.findViewById(R.id.name);
            paid = view.findViewById(R.id.paid);
            unpaid = view.findViewById(R.id.unpaid);
            total = view.findViewById(R.id.total);
        }
    }


    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = notesList.get(position);

        holder.note.setText(note.getNote());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss");
        ZonedDateTime ldt = ZonedDateTime.parse(note.getTimestamp());
        holder.timestamp.setText(dtf.format(ldt));
        holder.name.setText(note.getName());
        holder.paid.setText(String.valueOf(note.getPaid()));
        holder.unpaid.setText(String.valueOf(note.getUnpaid()));
        holder.total.setText(String.valueOf(note.getTotal()));

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
        return notesList.size();
    }

}


