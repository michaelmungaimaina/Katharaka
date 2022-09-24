package mich.gwan.katharaka.activities.spare;

import android.annotation.SuppressLint;
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
import mich.gwan.katharaka.databinding.ActivitySpareRegisterBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.spare.Spare;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class SpareRegister extends AppCompatActivity implements View.OnClickListener {
    private NestedScrollView nestedScrollView;

    private TextInputLayout layoutSpareId;
    private TextInputLayout layoutSpareCtgry;
    private TextInputLayout layoutSpareQnty;
    private TextInputLayout layoutSpareBp;
    private TextInputLayout layoutSpareSp;

    private TextInputEditText editSpareId;
    private TextInputEditText editSpareCtgry;
    private TextInputEditText editSpareQnty;
    private TextInputEditText editSpareBp;
    private TextInputEditText editSparesp;

    private TextView textClear;
    private TextView textSubmit;

    private ActivitySpareRegisterBinding binding;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Spare spare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpareRegisterBinding.inflate(getLayoutInflater());
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
        spare = new Spare();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtSpareSubmit:
                postDataToOilsTable();
                //finish();
                break;
            case R.id.txtSpareClear:
                emptyTextFields();
                //finish();
                break;
        }
    }

    private void initViews(){
        layoutSpareCtgry = binding.laySpareCtgry;
        layoutSpareBp = binding.laySpareBp;
        layoutSpareSp = binding.laySpareSp;

        editSpareCtgry = binding.editSpareCtgry;
        editSpareBp = binding.editSpareBp;
        editSparesp = binding.editSpareSp;

        textClear = binding.txtSpareClear;
        textSubmit = binding.txtSpareSubmit;

        nestedScrollView = binding.nestedScrollView;

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpareRegister.this.finish();
            }
        });
    }

    private void initListners(){
        textSubmit.setOnClickListener(this);
        textClear.setOnClickListener(this);
    }

    private void postDataToOilsTable(){
        if (!inputValidation.isInputEditTextFilled(editSpareCtgry, layoutSpareCtgry, getString(R.string.error_spare_category))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editSpareBp, layoutSpareBp, getString(R.string.error_oil_bp))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editSparesp, layoutSpareSp, getString(R.string.error_oil_sp))) {
            return;
        }

        // if (!databaseHelper.checkOil(editOilId.getText().toString().trim())) {
        if (!databaseHelper.ifExistsSpare(editSpareCtgry.getText().toString().trim().toUpperCase())){
            try {
                spare.setSpare_category(editSpareCtgry.getText().toString().trim().toUpperCase());
                //spare.setSpare_qnty(editSpareQnty.getText().toString().trim());
                spare.setSpare_bp(Integer.parseInt(editSpareBp.getText().toString().trim()));
                spare.setSpare_sp(Integer.parseInt(editSparesp.getText().toString().trim()));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            databaseHelper.addSpare(spare);
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
        editSpareCtgry.setText(null);
        editSpareBp.setText(null);
        editSparesp.setText(null);
    }
}
