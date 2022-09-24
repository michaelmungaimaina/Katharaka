package mich.gwan.katharaka.activities.spare;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.databinding.ActivitySpareSaleBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.spare.Spare;
import mich.gwan.katharaka.model.spare.SpareSale;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class SpareActionSale extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout relativeLayout;
    private TextInputLayout layoutSpareSaleQnty;
    private EditText editTextSpareSaleCat;

    private EditText editTextSpareSaleQnty;
    private TextView textViewClear;

    private TextView textViewSale;
    private AppCompatSpinner spinner;

    private ActivitySpareSaleBinding binding;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private SpareSale spareSale;
    private TextInputLayout layoutSpareSaleCat;
    private List<Spare> listSpare;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySpareSaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initObjects();
        initViews();
        initListeners();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.sale));
    }

    private void initObjects(){
        inputValidation = new InputValidation(this);
                databaseHelper = new DatabaseHelper(this);
        listSpare = new ArrayList<>();
        spareSale = new SpareSale();
        getDataFromSQLite();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSpareSale:
                performOilSale();
                break;
            case R.id.txtSpareSaleClear:
                emptyTextFields();
                break;
        }
    }

    private void initViews(){
        layoutSpareSaleCat = binding.laySpareSaleCategory;
        layoutSpareSaleQnty = binding.laySpareSaleQnty;

        editTextSpareSaleQnty = binding.editSpareSaleQnty;

        textViewClear = binding.txtSpareSaleClear;
        textViewSale = binding.txtSpareSale;

        relativeLayout = binding.relativeLayout;

        spinner = binding.spinner;

        List<String> categories = databaseHelper.getAllSaleSpare();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpareActionSale.this.finish();
            }
        });

    }

    private void initListeners(){
        textViewClear.setOnClickListener(this);
        textViewSale.setOnClickListener(this);
    }

    /**
     * Method to get the oil selling price
     * returns the oil selling price
     * @return
     */
    public int getSpareSelPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT spare_sp FROM spare WHERE spare_category =?";
        Cursor catCu = dbg.rawQuery(dbSp,new String[]{category});
        int sellPrice = 0;
        while (catCu.moveToNext()){
            sellPrice = catCu.getInt(0);
        }
        catCu.close();
        dbg.close();
        return sellPrice;
    }

    /**
     * method to get the oil buying price
     * it returns the buying price
     * @return
     */
    public int getSpareBuyPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT spare_bp FROM spare WHERE spare_category =?";
        Cursor catCu = dbg.rawQuery(dbSp,new String[]{category});
        int buyPrice = 0;
        while (catCu.moveToNext()){
            buyPrice = catCu.getInt(0);
        }
        catCu.close();
        dbg.close();
        return buyPrice;
    }

    private void performOilSale(){
        AllSales allSales = new AllSales();
        if (!inputValidation.isInputEditTextOccupied(editTextSpareSaleQnty, layoutSpareSaleQnty, getString(R.string.error_oilsale_qnty))) {
            return;
        }

        if (databaseHelper.checkSpareCategory(spinner.getSelectedItem().toString().toUpperCase())){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            int unitPrice = getSpareSelPrice();
            int saleSpareQnty = Integer.parseInt(editTextSpareSaleQnty.getText().toString().trim());
            int saleSpareTotal = saleSpareQnty * unitPrice;
            int buyPrice = getSpareBuyPrice();
            int profit = (unitPrice - buyPrice) * saleSpareQnty;
            try {
                spareSale.setSpareSaleDate(dateFormat.format(LocalDate.now()));
                spareSale.setSpareSaleTime(timeFormat.format(java.time.LocalDateTime.now()));
                spareSale.setSpareSalecategory(spinner.getSelectedItem().toString().toUpperCase(Locale.ROOT).trim());
                spareSale.setSpareSaleUprice(unitPrice);
                spareSale.setSpareSaleQnty(Integer.parseInt(editTextSpareSaleQnty.getText().toString().trim()));
                spareSale.setSpareSaleTotal(saleSpareTotal);
                spareSale.setSpareSaleProfit(profit);
                allSales.setSaleDate(spareSale.getSpareSaleDate());
                allSales.setSaleTime(spareSale.getSpareSaleTime());
                allSales.setSalecategory(spareSale.getSpareSalecategory());
                allSales.setSaleUprice(spareSale.getSpareSaleUprice());
                allSales.setSaleQnty(spareSale.getSpareSaleQnty());
                allSales.setSaleTotal(spareSale.getSpareSaleTotal());
                allSales.setSaleProfit(spareSale.getSpareSaleProfit());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            databaseHelper.addSpareSale(spareSale);
            databaseHelper.addAllSale(allSales);
            Snackbar snackbar = Snackbar.make(relativeLayout, getString(R.string.oilsale_transact_success), Snackbar.LENGTH_LONG);
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
            Snackbar snackbar = Snackbar.make(relativeLayout, getString(R.string.oilsale_transact_error), Snackbar.LENGTH_LONG);
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

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                listSpare.clear();
                listSpare.addAll(databaseHelper.getAllSpare());
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //  oilRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void emptyTextFields(){
        editTextSpareSaleQnty.setText(null);
    }
}
