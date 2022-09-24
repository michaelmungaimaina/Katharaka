package mich.gwan.katharaka.activities.spanner;

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
import mich.gwan.katharaka.databinding.ActivitySpannerSaleBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.spanner.Spanner;
import mich.gwan.katharaka.model.spanner.SpannerSale;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class SpannerActionSale extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private TextInputLayout layoutQnty;
    private EditText editTextCat;

    private EditText editTextQnty;
    private TextView textViewClear;

    private TextView textViewSale;
    private AppCompatSpinner spinner;

    private ActivitySpannerSaleBinding binding;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private SpannerSale sale;
    private TextInputLayout layoutCat;
    private List<Spanner> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySpannerSaleBinding.inflate(getLayoutInflater());
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
        list = new ArrayList<>();
        sale = new SpannerSale();
        getDataFromSQLite();
        }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSpannerSale:
                performOilSale();
                break;
            case R.id.txtSpannerSaleClear:
                emptyTextFields();
                break;
        }
    }

    private void initViews(){
        layoutCat = binding.laySpannerSaleCategory;
        layoutQnty = binding.laySpannerSaleQnty;

        editTextQnty = binding.editSpannerSaleQnty;

        textViewClear = binding.txtSpannerSaleClear;
        textViewSale = binding.txtSpannerSale;

        relativeLayout = binding.relativeLayout;

        spinner = binding.spinner;

        List<String> categories = databaseHelper.getAllSaleSpanner();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannerActionSale.this.finish();
            }
        });

    }

    private void initListeners(){
        textViewClear.setOnClickListener(this);
        textViewSale.setOnClickListener(this);
    }

    /**
     * Method to get the selling price
     * returns the selling price
     * @return
     */
    public int getSelPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT spanner_sp FROM spanners WHERE spanner_category =?";
        Cursor catCu = dbg.rawQuery(dbSp,new String[]{category});
        int buyPrice = 0;
        while (catCu.moveToNext()){
            buyPrice = catCu.getInt(0);
        }
        catCu.close();
        dbg.close();
        return buyPrice;
    }

    /**
     * method to get the buying price
     * it returns the buying price
     * @return
     */
    public int getBuyPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT spanner_bp FROM spanners WHERE spanner_category =?";
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
        if (!inputValidation.isInputEditTextOccupied(editTextQnty, layoutQnty, getString(R.string.error_oilsale_qnty))) {
            return;
        }

        if (databaseHelper.checkSpannerCategory(spinner.getSelectedItem().toString().trim().toUpperCase())){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            int unitPrice = getSelPrice();
            int saleQnty = Integer.parseInt(editTextQnty.getText().toString().trim());
            int saleTotal = saleQnty * unitPrice;
            int buyPrice = getBuyPrice();
            int profit = (unitPrice - buyPrice) * saleQnty;
            try {
                sale.setSpannerSaleDate(dateFormat.format(LocalDate.now()));
                sale.setSpannerSaleTime(timeFormat.format(java.time.LocalDateTime.now()));
                sale.setSpannerSalecategory(spinner.getSelectedItem().toString().toUpperCase(Locale.ROOT).trim());
                sale.setSpannerSaleUprice(unitPrice);
                sale.setSpannerSaleQnty(Integer.parseInt(editTextQnty.getText().toString().trim()));
                sale.setSpannerSaleTotal(saleTotal);
                sale.setSpannerSaleProfit(profit);
                allSales.setSaleDate(sale.getSpannerSaleDate());
                allSales.setSaleTime(sale.getSpannerSaleTime());
                allSales.setSalecategory(sale.getSpannerSalecategory());
                allSales.setSaleUprice(sale.getSpannerSaleUprice());
                allSales.setSaleQnty(sale.getSpannerSaleQnty());
                allSales.setSaleTotal(sale.getSpannerSaleTotal());
                allSales.setSaleProfit(sale.getSpannerSaleProfit());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            databaseHelper.addSpannerSale(sale);
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
                list.clear();
                list.addAll(databaseHelper.getAllSpanner());

                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void emptyTextFields(){
        editTextQnty.setText(null);
    }
}
