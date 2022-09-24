package mich.gwan.katharaka.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.admin.ControlActivity;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutSpinner;
    private TextInputLayout textInputLayoutPassword;

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatImageView icon;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        initViews();
        initListeners();
        initObjects();

        /**List<String> categories = new ArrayList<String>();
        categories.add("Select Station");
        categories.add("Kithimu");
        categories.add("Iriga");
        categories.add("Igoji");
        categories.add("Baraka");
        categories.add("Kithimu_Auto");
        categories.add("UserManager");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);**/
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(android.R.color.holo_green_dark));

    }
    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        //textInputLayoutSpinner = (TextInputLayout) findViewById(R.id.textInputLayoutSpinner);

        textInputEditTextEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

         //spinner = (Spinner)findViewById(R.id.spinner);
         icon = (AppCompatImageView) findViewById(R.id.icon);

         /**spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });**/

    }

     public void dbManagement(){     }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
       // spinnerStations.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(this);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                    verifyFromSQLite();
                    //emptyInputEditText();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), mich.gwan.katharaka.activities.RegisterActivity.class);
                startActivity(intentRegister);
                //emptyInputEditText();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                return;
            }
            if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim())) {

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                startActivity(new Intent(LoginActivity.this, ControlActivity.class));
                            }
                        },
                        1000
                );
                Snackbar snackbar = Snackbar.make(nestedScrollView,  "Katharaka Session Opened Successfully", Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                view.setBackgroundColor(Color.MAGENTA);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.TOP | Gravity.CENTER;
                params.setMargins(0, 0, 0, 0);
                view.setLayoutParams(params);
                snackbar.setTextColor(Color.WHITE);
                (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                snackbar.show();
                emptyInputEditText();

            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                view.setBackgroundColor(Color.BLUE);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.TOP | Gravity.CENTER;
                params.setMargins(0, 0, 0, 0);
                view.setLayoutParams(params);
                snackbar.setTextColor(Color.WHITE);
                (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                snackbar.show();
            }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
