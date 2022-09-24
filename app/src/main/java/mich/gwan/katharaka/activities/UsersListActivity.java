package mich.gwan.katharaka.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.UsersRecyclerAdapter;
import mich.gwan.katharaka.helpers.RecyclerTouchListener;
import mich.gwan.katharaka.model.User;
import mich.gwan.katharaka.sql.DatabaseHelper;


/**
 * Created by ownrestech on 07/01/2022.
 */

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private LinearLayout layout;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private  LoginActivity loginActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        //getSupportActionBar().setTitle("");
        initViews();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.list));
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        layout = (LinearLayout) findViewById(R.id.layout);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        loginActivity = new LoginActivity();
        databaseHelper = new DatabaseHelper(this);


        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);
        getDataFromSQLite();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         */
        recyclerViewUsers.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerViewUsers, new RecyclerTouchListener.ClickListener() {
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
        databaseHelper.deleteUser(listUsers.get(position));
        // removing the note from the list
        listUsers.remove(position);
        usersRecyclerAdapter.notifyItemRemoved(position);
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
                    showNoteDialog(true, listUsers.get(position), position);
                } else {
                    //deleteItem(position);
                    Snackbar snackbar = Snackbar.make(layout, "Invalid Operation", Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    view.setBackgroundColor(Color.RED);
                    FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP | Gravity.CENTER;
                    params.setMargins(0,0,0,0);
                    view.setLayoutParams(params);
                    snackbar.setTextColor(Color.WHITE);
                    (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    snackbar.show();
                }
            }
        });
        builder.show();
    }
    /**
     * Update Bp and Sp
     */
    private void updatePrice(String name, String email, String pass, int position) {
        User n = listUsers.get(position);
        // updating details text
        n.setName(name);
        n.setEmail(email);
        n.setPassword(pass);

        // updating note in db
        databaseHelper.updateUser(n);
        // refreshing the list
        listUsers.set(position, n);
        usersRecyclerAdapter.notifyItemChanged(position);
    }
    /**
     * Inserting new user in db
     * and refreshing the list
     */
    private void createNote(String name, String email, String passwrd) {
        // inserting note in db and getting
        // newly inserted note id
        //databaseHelper.addUser(note);

        // get the newly inserted note from db
        //User n = databaseHelper.getAllUser(id);

        //if (n != null) {
            // adding new note to array list at 0 position
            //notesList.add(0, n);

            // refreshing the list
            //mAdapter.notifyDataSetChanged();

           // toggleEmptyNotes();
        //}
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * a note.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    private void showNoteDialog(final boolean shouldUpdate, final User user, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.user_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
        alertDialogBuilderUserInput.setView(view);

        final EditText name = view.findViewById(R.id.name);
        final EditText email = view.findViewById(R.id.note);
        final EditText passwrd = view.findViewById(R.id.sell_p);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? "New Registration" : "Update Details");

        if (shouldUpdate && user != null) {
            name.setText(user.getName());
            email.setText(user.getEmail());
            passwrd.setText(user.getPassword());
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
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(activity, "Insert a Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(activity, "Insert an Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwrd.getText().toString())) {
                    Toast.makeText(activity, "Insert a Password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && name != null) {
                    // update note by it's id
                    updatePrice(name.getText().toString(),email.getText().toString(), passwrd.getText().toString(), position);
                } else {
                    // create new note
                    createNote(name.getText().toString(),email.getText().toString(),passwrd.getText().toString());
                }
            }
        });
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
            // AsyncTask is used that SQLite operation not blocks the UI Thread.
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    listUsers.clear();
                    listUsers.addAll(databaseHelper.getAllUser());
                    return null;
                }
                @SuppressLint("NotifyDataSetChanged")
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    usersRecyclerAdapter.notifyDataSetChanged();
                }
            }.execute();
        }

}
