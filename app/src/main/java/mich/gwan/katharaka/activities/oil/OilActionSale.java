package mich.gwan.katharaka.activities.oil;

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
import mich.gwan.katharaka.databinding.ActivityOilSaleBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.oil.Oil;
import mich.gwan.katharaka.model.oil.OilSale;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class OilActionSale  extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private TextInputLayout layoutOilSaleQnty;
    private EditText editTextOilSaleCat;
    private EditText editTextOilSaleQnty;
    private TextView textViewClear;
    private TextView textViewSale;
    private AppCompatSpinner spinner;

    private ActivityOilSaleBinding binding;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private OilSale oilSale;
    private AllSales allSales;
    private TextInputLayout layoutOilSaleCat;
    private List<Oil> listOil;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityOilSaleBinding.inflate(getLayoutInflater());
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
        listOil = new ArrayList<>();
        oilSale = new OilSale();
        allSales = new AllSales();
        getDataFromSQLite();
        }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtOilSale:
                performOilSale();
                break;
            case R.id.txtOilSaleClear:
                emptyTextFields();
                break;
        }
    }

    private void initViews(){
        layoutOilSaleCat = binding.layOilSaleCategory;
        layoutOilSaleQnty = binding.layOilSaleQnty;

        //editTextOilSaleCat = binding.editOilSaleCategory;
        editTextOilSaleQnty = binding.editOilSaleQnty;

        textViewClear = binding.txtOilSaleClear;
        textViewSale = binding.txtOilSale;

        relativeLayout = binding.relativeLayout;
        spinner = binding.spinner;

        List<String> categories = databaseHelper.getAllSaleOil();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OilActionSale.this.finish();
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
    public int getOilSelPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT oil_sp FROM oil WHERE oil_category =?";
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
    public int getOilBuyPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT oil_bp FROM oil WHERE oil_category =?";
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
       // if (!inputValidation.isInputEditTextOccupied(editTextOilSaleCat, layoutOilSaleCat, getString(R.string.error_oilsale_cat))) {
        //    return;
        //}
        if (!inputValidation.isInputEditTextOccupied(editTextOilSaleQnty, layoutOilSaleQnty, getString(R.string.error_oilsale_qnty))) {
            return;
        }

        if (databaseHelper.checkOilCategory(spinner.getSelectedItem().toString().toUpperCase())){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            int unitPrice = getOilSelPrice();
            int saleOilQnty = Integer.parseInt(editTextOilSaleQnty.getText().toString().trim());
            int saleOilTotal = saleOilQnty * unitPrice;
            int buyPrice = getOilBuyPrice();
            int profit = (unitPrice - buyPrice) * saleOilQnty;
            try {
                oilSale.setOilSaleDate(dateFormat.format(LocalDate.now()));
                oilSale.setOilSaleTime(timeFormat.format(java.time.LocalDateTime.now()));
                oilSale.setOilSalecategory(spinner.getSelectedItem().toString().toUpperCase(Locale.ROOT).trim());
                oilSale.setOilSaleUprice(unitPrice);
                oilSale.setOilSaleQnty(Integer.parseInt(editTextOilSaleQnty.getText().toString().trim()));
                oilSale.setOilSaleTotal(saleOilTotal);
                oilSale.setOilSaleProfit(profit);
                allSales.setSaleDate(oilSale.getOilSaleDate());
                allSales.setSaleTime(oilSale.getOilSaleTime());
                allSales.setSalecategory(oilSale.getOilSalecategory());
                allSales.setSaleUprice(oilSale.getOilSaleUprice());
                allSales.setSaleQnty(oilSale.getOilSaleQnty());
                allSales.setSaleTotal(oilSale.getOilSaleTotal());
                allSales.setSaleProfit(oilSale.getOilSaleProfit());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            databaseHelper.addOilSale(oilSale);
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
            //editTextOilSaleCat.requestFocus();
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
                listOil.clear();
                listOil.addAll(databaseHelper.getAllOil());
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
        editTextOilSaleQnty.setText(null);
    }
}
