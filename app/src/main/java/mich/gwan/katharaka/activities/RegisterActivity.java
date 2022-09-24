package mich.gwan.katharaka.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.User;
import mich.gwan.katharaka.sql.DatabaseHelper;


/**
 * Created by ownrestech on 8/01/2022.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutSpinner;

    private TextInputEditText textInputEditTextName;
    private AppCompatSpinner spinner;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initListeners();
        initObjects();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
       /** spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });**/

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        //textInputLayoutSpinner = (TextInputLayout) findViewById(R.id.textInputLayoutSpinner);

        //spinner = (AppCompatSpinner) findViewById(R.id.spinner);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

       /** List<String> categories = new ArrayList<String>();
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
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                    postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                return;
            }
            if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                    textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
                return;
            }

            if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

                user.setName(textInputEditTextName.getText().toString().trim());
                user.setEmail(textInputEditTextEmail.getText().toString().trim());
                user.setPassword(textInputEditTextPassword.getText().toString().trim());
                //user.setStation(spinner.getPrompt().toString().trim());

                databaseHelper.addUser(user);

                // Snack Bar to show success message that record saved successfully
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG);
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
                emptyInputEditText();

            } else {
                // Snack Bar to show error message that record already exists
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG);
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
        //}
    //}

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
       // spinner.setPromptId(0);
    }
}
