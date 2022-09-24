package mich.gwan.katharaka.activities.bolt;

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
import mich.gwan.katharaka.databinding.ActivityBoltRegisterBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.bolt.Bolts;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class BoltRegister extends AppCompatActivity implements View.OnClickListener {
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

    private ActivityBoltRegisterBinding binding;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Bolts obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBoltRegisterBinding.inflate(getLayoutInflater());
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
        obj = new Bolts();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txtBoltSubmit:
                postDataToOilsTable();
                //finish();
                break;
            case R.id.txtBoltClear:
                emptyTextFields();
                //finish();
                break;
        }
    }

    private void initViews(){
        layoutCtgry = binding.layBoltCtgry;
        layoutBp = binding.layBoltBp;
        layoutSp = binding.layBoltSp;

        editCtgry = binding.editBoltCtgry;
        editBp = binding.editGasBp;
        editsp = binding.editBoltSp;

        textClear = binding.txtBoltClear;
        textSubmit = binding.txtBoltSubmit;

        nestedScrollView = binding.nestedScrollView;
        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoltRegister.this.finish();
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

        if (!databaseHelper.ifExistsBolt(editCtgry.getText().toString().trim().toUpperCase())){
            try {
                obj.setBolt_category(editCtgry.getText().toString().trim().toUpperCase());
                //obj.setBolt_qnty(editQnty.getText().toString().trim());
                obj.setBolt_bp(Integer.parseInt(editBp.getText().toString().trim()));
                obj.setBolt_sp(Integer.parseInt(editsp.getText().toString().trim()));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            databaseHelper.addBolt(obj);
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
