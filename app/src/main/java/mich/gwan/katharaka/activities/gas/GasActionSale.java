package mich.gwan.katharaka.activities.gas;

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
import mich.gwan.katharaka.databinding.ActivityGasSaleBinding;
import mich.gwan.katharaka.helpers.InputValidation;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.gas.Gas;
import mich.gwan.katharaka.model.gas.GasSale;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class GasActionSale extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout;
    private TextInputLayout layoutQnty;
    private EditText editTextCat;

    private EditText editTextQnty;
    private TextView textViewClear;

    private TextView textViewSale;
    private AppCompatSpinner spinner;

    private ActivityGasSaleBinding binding;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private GasSale sale;
    private TextInputLayout layoutCat;
    private List<Gas> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityGasSaleBinding.inflate(getLayoutInflater());
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
        sale = new GasSale();
        getDataFromSQLite();
        }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtGasSale:
                performOilSale();
                break;
            case R.id.txtGasSaleClear:
                emptyTextFields();
                break;
        }
    }

    private void initViews(){
        layoutCat = binding.layGasSaleCategory;
        layoutQnty = binding.layGasSaleQnty;

        editTextQnty = binding.editGasSaleQnty;

        textViewClear = binding.txtGasSaleClear;
        textViewSale = binding.txtGasSale;

        relativeLayout = binding.relativeLayout;

        spinner = binding.spinner;

        List<String> categories = databaseHelper.getAllSaleGas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasActionSale.this.finish();
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
        String dbSp = "SELECT gas_sp FROM gas WHERE gas_category =?";
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
     * method to get the buying price
     * it returns the buying price
     * @return
     */
    public int getBuyPrice(){
        String category = spinner.getSelectedItem().toString().trim().toUpperCase(Locale.ROOT);
        SQLiteDatabase dbg = this.databaseHelper.getReadableDatabase();
        String dbSp = "SELECT gas_bp FROM gas WHERE gas_category =?";
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

        if (databaseHelper.checkGasCategory(spinner.getSelectedItem().toString().trim().toUpperCase())){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            int unitPrice = getSelPrice();
            int saleQnty = Integer.parseInt(editTextQnty.getText().toString().trim());
            int saleTotal = saleQnty * unitPrice;
            int buyPrice = getBuyPrice();
            int profit = (unitPrice - buyPrice) * saleQnty;
            try {
                sale.setGasSaleDate(dateFormat.format(LocalDate.now()));
                sale.setGasSaleTime(timeFormat.format(java.time.LocalDateTime.now()));
                sale.setGasSalecategory(spinner.getSelectedItem().toString().toUpperCase(Locale.ROOT).trim());
                sale.setGasSaleUprice(unitPrice);
                sale.setGasSaleQnty(Integer.parseInt(editTextQnty.getText().toString().trim()));
                sale.setGasSaleTotal(saleTotal);
                sale.setGasSaleProfit(profit);
                allSales.setSaleDate(sale.getGasSaleDate());
                allSales.setSaleTime(sale.getGasSaleTime());
                allSales.setSalecategory(sale.getGasSalecategory());
                allSales.setSaleUprice(sale.getGasSaleUprice());
                allSales.setSaleQnty(sale.getGasSaleQnty());
                allSales.setSaleTotal(sale.getGasSaleTotal());
                allSales.setSaleProfit(sale.getGasSaleProfit());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            databaseHelper.addGasSale(sale);
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
                list.addAll(databaseHelper.getAllGas());

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
        editTextQnty.setText(null);
    }
}
