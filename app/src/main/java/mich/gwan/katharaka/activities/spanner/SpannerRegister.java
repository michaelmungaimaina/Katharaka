package mich.gwan.katharaka.activities.spanner;

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
import mich.gwan.katharaka.databinding.ActivitySpannerRegisterBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.spanner.Spanner;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class SpannerRegister extends AppCompatActivity implements View.OnClickListener {
    private NestedScrollView nestedScrollView;

    private TextInputLayout layoutId;
    private TextInputLayout layoutCtgry;
    private TextInputLayout layoutQnty;
    private TextInputLayout layoutBp;
    private TextInputLayout layoutSp;

    private TextInputEditText editId;
    private TextInputEditText editCtgry;
    private TextInputEditText editQnty;
    private TextInputEditText editBp;
    private TextInputEditText editsp;

    private TextView textClear;
    private TextView textSubmit;

    private ActivitySpannerRegisterBinding binding;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Spanner obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpannerRegisterBinding.inflate(getLayoutInflater());
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
        obj = new Spanner();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtSpannerSubmit:
                postDataToOilsTable();
                //finish();
                break;
            case R.id.txtSpannerClear:
                emptyTextFields();
                //finish();
                break;
        }
    }

    private void initViews(){
        layoutCtgry = binding.laySpannerCtgry;
        layoutBp = binding.laySpannerBp;
        layoutSp = binding.laySpannerSp;

        editCtgry = binding.editSpannerCtgry;
        editBp = binding.editSpannerBp;
        editsp = binding.editSpannerSp;

        textClear = binding.txtSpannerClear;
        textSubmit = binding.txtSpannerSubmit;

        nestedScrollView = binding.nestedScrollView;

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannerRegister.this.finish();
            }
        });
    }

    private void initListners(){
        textSubmit.setOnClickListener(this);
        textClear.setOnClickListener(this);
    }

    private void postDataToOilsTable(){
        if (!inputValidation.isInputEditTextFilled(editCtgry, layoutCtgry, getString(R.string.error_oil_category))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editBp, layoutBp, getString(R.string.error_oil_bp))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editsp, layoutSp, getString(R.string.error_oil_sp))) {
            return;
        }

       // if (!databaseHelper.checkOil(editOilId.getText().toString().trim())) {
        if (!databaseHelper.ifExistsSpanner(editCtgry.getText().toString().trim().toUpperCase())){
            try {
                obj.setSpanner_category(editCtgry.getText().toString().trim().toUpperCase());
                //obj.setSpanner_qnty(editQnty.getText().toString().trim());
                obj.setSpanner_bp(Integer.parseInt(editBp.getText().toString().trim()));
                obj.setSpanner_sp(Integer.parseInt(editsp.getText().toString().trim()));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            databaseHelper.addSpanner(obj);
            Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.oil_data_success), Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(Color.BLUE);
            FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP | Gravity.CENTER;
            params.setMargins(0,0,0,0);
            view.setLayoutParams(params);
            snackbar.setTextColor(Color.WHITE);
            (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            snackbar.show();
            emptyTextFields();

        }
       else {
           Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_oil_category_exists), Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(Color.BLUE);
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
    private void emptyTextFields(){
        editCtgry.setText(null);
        editBp.setText(null);
        editsp.setText(null);
    }
}
