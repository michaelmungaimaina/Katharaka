package mich.gwan.katharaka.activities.bolt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.bolt.BoltRecyclerAdapter;
import mich.gwan.katharaka.databinding.ActivityBoltListBinding;
import mich.gwan.katharaka.helpers.RecyclerTouchListener;
import mich.gwan.katharaka.model.bolt.Bolts;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class BoltListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final AppCompatActivity activity = BoltListActivity.this;
    private List<Bolts> list;
    private BoltRecyclerAdapter recyclerAdapter;
    private DatabaseHelper databaseHelper;
    private ActivityBoltListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBoltListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().setTitle("");
        initViews();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.list));

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoltListActivity.this.finish();
            }
        });
    }

    private void initViews(){
        recyclerView = binding.recyclerViewBolt;
    }

    private void initObjects(){
        list = new ArrayList<>();
        recyclerAdapter = new BoltRecyclerAdapter(list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
                databaseHelper = new DatabaseHelper(this);
        getDataFromSQLite();


        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

    }

    /**
     * Delete the item from SQLite and remove
     * it from the list
     */
    private void deleteItem(int position) {
        // deleting the note from db
        databaseHelper.deleteBolt(list.get(position));
        // removing the note from the list
        list.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }
    /**
     * Update Bp and Sp
     */
    private void updatePrice(int note,int sp, int position) {
        Bolts n = list.get(position);
        // updating note text
        n.setBolt_bp(note);
        n.setBolt_sp(sp);

        // updating note in db
        databaseHelper.updateBolt(n);
        // refreshing the list
        list.set(position, n);
        recyclerAdapter.notifyItemChanged(position);
    }

    /**
     * Opens dialog with edit - Delete options
     * Delete - 0
     * Edit - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, list.get(position), position);
                } else {
                    deleteItem(position);
                }
            }
        });
        builder.show();
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * a note.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    private void showNoteDialog(final boolean shouldUpdate, final Bolts note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.bolt_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        final EditText sp = view.findViewById(R.id.sell_p);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView dialogItem = view.findViewById(R.id.dialog_item);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : "Update Prices");

        if (shouldUpdate && note != null) {
            inputNote.setText(String.valueOf(note.getBolt_bp()));
            sp.setText(String.valueOf(note.getBolt_sp()));
            dialogItem.setText(note.getBolt_category());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "UPDATE" : "SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputNote.getText().toString())) {
                    Toast.makeText(BoltListActivity.this, "Record a Petty Transaction!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updatePrice(Integer.parseInt(inputNote.getText().toString()),Integer.parseInt(sp.getText().toString()), position);
                } else {
                    // create new note
                    ///createNote(inputNote.getText().toString());
                }
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                list.clear();
                list.addAll(databaseHelper.getAllBolts());
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
