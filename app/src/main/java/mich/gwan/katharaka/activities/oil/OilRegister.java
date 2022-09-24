package mich.gwan.katharaka.activities.oil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.databinding.ActivityOilRegisterBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.oil.Oil;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class OilRegister extends AppCompatActivity implements View.OnClickListener {
    private NestedScrollView nestedScrollView;

    private TextInputLayout layoutOilId;
    private TextInputLayout layoutOilCtgry;
    private TextInputLayout layoutOilQnty;
    private TextInputLayout layoutOilBp;
    private TextInputLayout layoutOilSp;

    private TextInputEditText editOilId;
    private TextInputEditText editOilCtgry;
    private TextInputEditText editOilQnty;
    private TextInputEditText editOilBp;
    private TextInputEditText editOilsp;

    private TextView textClear;
    private TextView textSubmit;

    private ActivityOilRegisterBinding binding;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Oil oil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOilRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initObjects();
        initViews();
        initListners();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.register));

    }

    private void initObjects() {
        inputValidation = new InputValidation(this);
                databaseHelper = new DatabaseHelper(this);
        oil = new Oil();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtOilSubmit:
                postDataToOilsTable();
                //finish();
                break;
            case R.id.txtOilClear:
                emptyTextFields();
                //finish();
                break;
        }
    }

    private void initViews(){
        layoutOilCtgry = binding.layOilCtgry;
        layoutOilBp = binding.layOilBp;
        layoutOilSp = binding.layOilSp;

        editOilCtgry = binding.editOilCtgry;
        editOilBp = binding.editOilBp;
        editOilsp = binding.editOilSp;

        textClear = binding.txtOilClear;
        textSubmit = binding.txtOilSubmit;

        nestedScrollView = binding.nestedScrollView;

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OilRegister.this.finish();
            }
        });
    }

    private void initListners(){
        textSubmit.setOnClickListener(this);
        textClear.setOnClickListener(this);
    }

    private void postDataToOilsTable(){
            if (!inputValidation.isInputEditTextFilled(editOilCtgry, layoutOilCtgry, getString(R.string.error_oil_category))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(editOilBp, layoutOilBp, getString(R.string.error_oil_bp))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(editOilsp, layoutOilSp, getString(R.string.error_oil_sp))) {
                return;
            }

            // if (!databaseHelper.checkOil(editOilId.getText().toString().trim())) {
            if (!databaseHelper.ifExists(editOilCtgry.getText().toString().trim().toUpperCase())){
                try {
                    oil.setOil_category(editOilCtgry.getText().toString().trim().toUpperCase());
                    //oil.setOil_qnty(editOilQnty.getText().toString().trim());
                    oil.setOil_bp(Integer.parseInt(editOilBp.getText().toString().trim()));
                    oil.setOil_sp(Integer.parseInt(editOilsp.getText().toString().trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                databaseHelper.addOil(oil);
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.oil_data_success), Snackbar.LENGTH_LONG);
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
                emptyTextFields();

            } else {
                Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_oil_category_exists), Snackbar.LENGTH_LONG);
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
    private void emptyTextFields(){
        editOilCtgry.setText(null);
        //editOilQnty.setText(null);
        editOilBp.setText(null);
        editOilsp.setText(null);
    }
}
