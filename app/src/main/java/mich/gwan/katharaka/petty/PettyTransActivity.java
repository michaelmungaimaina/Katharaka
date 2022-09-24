package mich.gwan.katharaka.petty;


import static java.time.ZonedDateTime.now;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.NotesAdapter;
import mich.gwan.katharaka.helpers.MyDividerItemDecoration;
import mich.gwan.katharaka.helpers.RecyclerTouchListener;
import mich.gwan.katharaka.model.pdf.Note;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class PettyTransActivity extends AppCompatActivity {
    private NotesAdapter mAdapter;
    private List<Note> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private TextView pettyText;
    private EditText searchText;
    private TextInputLayout laySearch;
    private ImageView imageBack;
    private ImageView imageSearch;


    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petty_transaction);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noNotesView = findViewById(R.id.empty_notes_view);
        pettyText = findViewById(R.id.pettyText);
        searchText = findViewById(R.id.editSearch);
        laySearch = findViewById(R.id.laySearch);
        imageBack = findViewById(R.id.iconBack);
        imageSearch = findViewById(R.id.iconSearch);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.sale));

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PettyTransActivity.this.finish();
            }
        });

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pettyText.setWidth(0);
                searchText.setWidth(160);
                searchText.setHint("Search");

            }
        });

                db = new DatabaseHelper(this);

        notesList.addAll(db.getAllNotes());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        mAdapter = new NotesAdapter(this, notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
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
     * Inserting new note in db
     * and refreshing the list
     */
    @SuppressLint("NotifyDataSetChanged")
    private void createNote(String note, String name, String paid, String unpaid, String total) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertNote(note, name, paid, unpaid, total);

        // get the newly inserted note from db
        Note n = db.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);
            // refreshing the list
            mAdapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }

    /**
     * Updating note in db and updating
     * item in the list by its position
     */
    private void updateNote(String note, String name, int paid, int unpaid, int total, int position) {
        Note n = notesList.get(position);
        // updating note text
        n.setNote(note);
        n.setName(name);
        n.setPaid(paid);
        n.setUnpaid(unpaid);
        n.setTotal(total);
        n.setTimestamp(String.valueOf(now()));

        // updating note in db
        db.updateNote(n);
        // refreshing the list
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyNotes();
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the note from db
        db.deleteNote(notesList.get(position));
        // removing the note from the list
        notesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
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
    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(PettyTransActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNote = view.findViewById(R.id.note);
        final EditText name = view.findViewById(R.id.name);
        final EditText paid = view.findViewById(R.id.paid);
        final EditText unpaid = view.findViewById(R.id.unpaid);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        TextView total = view.findViewById(R.id.total);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNote.setText(note.getNote());
            name.setText(note.getName());
            paid.setText(String.valueOf(note.getPaid()));
            unpaid.setText(String.valueOf(note.getUnpaid()));
            total.setText(String.valueOf(note.getTotal()));
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
                    Toast.makeText(PettyTransActivity.this, "Record a Petty Transaction!", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's
                    int tot = Integer.parseInt(paid.getText().toString()) + Integer.parseInt(unpaid.getText().toString());
                    updateNote(inputNote.getText().toString(),name.getText().toString(),
                            Integer.parseInt(paid.getText().toString()),Integer.parseInt(unpaid.getText().toString()),
                            tot, position);
                } else {
                    // create new note
                    if (!paid.getText().toString().equals("") && !unpaid.getText().toString().equals("")) {
                        String tot = String.valueOf(Integer.parseInt(paid.getText().toString()) + Integer.parseInt(unpaid.getText().toString()));
                        createNote(inputNote.getText().toString(), name.getText().toString(), paid.getText().toString(), unpaid.getText().toString(), tot);
                    }
                    if (!paid.getText().toString().equals("") && unpaid.getText().toString().equals("")){
                        String tot = String.valueOf(Integer.parseInt(paid.getText().toString()));
                        createNote(inputNote.getText().toString(), name.getText().toString(), paid.getText().toString(), unpaid.getText().toString(), tot);
                    }
                    if (paid.getText().toString().equals("") && !unpaid.getText().toString().equals("")){
                        String tot = String.valueOf(Integer.parseInt(unpaid.getText().toString()));
                        createNote(inputNote.getText().toString(), name.getText().toString(), paid.getText().toString(), unpaid.getText().toString(), tot);
                    }
                    else {
                        String tot = "";
                        createNote(inputNote.getText().toString(), name.getText().toString(), paid.getText().toString(), unpaid.getText().toString(), tot);
                    }
                }
            }
        });
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        }
        else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}

