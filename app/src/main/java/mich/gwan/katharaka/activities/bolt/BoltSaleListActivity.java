package mich.gwan.katharaka.activities.bolt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.activities.admin.ui.gallery.GalleryFragment;
import mich.gwan.katharaka.activities.spare.SpareSaleListActivity;
import mich.gwan.katharaka.adapters.bolt.BoltSaleRecyclerAdapter;
import mich.gwan.katharaka.adapters.pdf.PDFBolt;
import mich.gwan.katharaka.adapters.pdf.PDFUtility;
import mich.gwan.katharaka.databinding.ActivityBoltssalesListBinding;
import mich.gwan.katharaka.helpers.RecyclerTouchListener;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.bolt.BoltSale;
import mich.gwan.katharaka.sql.DatabaseHelper;

public class BoltSaleListActivity extends AppCompatActivity implements PDFBolt.OnDocumentClose{
    private RecyclerView recyclerView;
    private AppCompatActivity activity = BoltSaleListActivity.this;
    private List<BoltSale> list;
    private DatabaseHelper databaseHelper;
    private BoltSaleRecyclerAdapter recyclerAdapter;
    private ActivityBoltssalesListBinding binding;
    DatePickerDialog froDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    @SuppressLint("StaticFieldLeak")
    private TextView froDatePicker;
    private TextView toDatePicker;
    private TextView textNetTotal;
    private TextView textNetProfit;
    private TextView textPdf;
    private TextView textExcel;
    private TextInputEditText textInputEditText;
    private CardView retrieve;
    private CardView pdf;
    private CardView excel;
    private String m;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivityBoltssalesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().setTitle("Gas Transactions");
        ActivityCompat.requestPermissions(BoltSaleListActivity.this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        initViews();
        initobjects();
        buttonEvents();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.sale_list));
    }

    private  void initViews(){

        recyclerView = binding.recyclerViewBoltSale;
        toDatePicker = binding.toDatePicker;
        froDatePicker = binding.froDatePicker;
        retrieve = binding.cardRerieve;
        pdf = binding.cardPdf;
        excel = binding.cardExcel;
        textNetProfit = binding.textBoltSaleNetProfit;
        textNetTotal = binding.textBoltSaleNetTotal;
        textInputEditText = binding.catTextInputEditText;

        froDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                froDatePickerDialog = new DatePickerDialog(BoltSaleListActivity.this, R.style.CustomDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (dayOfMonth < 10 ){
                            String strDate = year + "-" + (month + 1) + "-" +  "0" + dayOfMonth;
                            froDatePicker.setText(strDate);
                        }
                        if (month < 9){
                            String strDate = year + "-0" + (month + 1) + "-" + dayOfMonth;
                            froDatePicker.setText(strDate);
                        }
                        if (month < 9 && dayOfMonth <10){
                            String strDate = year + "-0" + (month + 1) + "-" + "0" + dayOfMonth;
                            froDatePicker.setText(strDate);
                        }
                        else {
                            String strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                            froDatePicker.setText(strDate);
                        }
                    }
                },mYear,mMonth,mDay);
                froDatePickerDialog.show();
            }
        });

        toDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                toDatePickerDialog = new DatePickerDialog(BoltSaleListActivity.this, R.style.CustomDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (dayOfMonth < 10 ){
                            String strDate = year + "-" + (month + 1) + "-" +  "0" + dayOfMonth;
                            toDatePicker.setText(strDate);
                        }
                        if (month < 9){
                            String strDate = year + "-0" + (month + 1) + "-" + dayOfMonth;
                            toDatePicker.setText(strDate);
                        }
                        if (month < 9 && dayOfMonth <10){
                            String strDate = year + "-0" + (month + 1) + "-" + "0" + dayOfMonth;
                            toDatePicker.setText(strDate);
                        }
                        else {
                            String strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                            toDatePicker.setText(strDate);
                        }
                    }
                },mYear,mMonth,mDay);
                toDatePickerDialog.show();
            }
        });
        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m =  path();
                // Make sure the path directory exists.
                try
                {
                    PDFBolt.createPdf(v.getContext(), BoltSaleListActivity.this, getSampleData(),path(),true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    //Log.e(TAG,"Error Creating Pdf");
                    Toast.makeText(BoltSaleListActivity.this,"Error Creating Pdf", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPDFDocumentClose(File file)
    {
        Toast.makeText(BoltSaleListActivity.this,"Data processed successfully",Toast.LENGTH_SHORT).show();
        // Get the File location and file name.
        Log.d("pdfFIle", "" + m);

        // Get the URI Path of file.
        Uri uriPdfPath = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", new File(m));
        Log.d("pdfPath", "" + uriPdfPath);

        // Start Intent to View PDF from the Installed Applications.
        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            startActivity(pdfOpenIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(this,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

        }
    }

    private String path(){
        String path = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/"  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+"_BoltSales.pdf";
        }
        else
        {
            path = Environment.getExternalStorageDirectory() + "/Sarensa/Katharaka/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+"_BoltSales.pdf";
        }
        return path;
    }

    private List<String[]> getSampleData()
    {
        int count = list.size();

        List<String[]> temp = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            BoltSale sales = list.get(i);
            temp.add(new String[] {sales.getBoltSaleDate(),sales.getBoltSaleTime(), "   " + sales.getBoltSalecategory(),
                    String.valueOf(sales.getBoltSaleQnty()), String.valueOf(sales.getBoltSaleUprice()) + ".00",
                    String.valueOf(sales.getBoltSaleTotal()) + ".00", String.valueOf(sales.getBoltSaleProfit()) + ".00"});
        }
        temp.add(new String[] {"TOTAL", "", "", "", "", String.valueOf(tot() + ".00"), String.valueOf(prof() + ".00")});
        return  temp;
    }

    public int tot() {
        int total = 0;
        for(int i = 0; i <list.size(); i++) {
            total += list.get(i).getBoltSaleTotal();
        }
        return total;
    }
    public int prof() {
        int profit = 0;
        for(int i = 0; i <list.size(); i++) {
            profit += list.get(i).getBoltSaleProfit();
        }
        return profit;
    }

    /**
     * Delete the item from SQLite and remove
     * it from the list
     */
    private void deleteItem(int position) {
        // deleting the note from db
        databaseHelper.deleteBoltSale(list.get(position));
        AllSales allSales = new AllSales();
        BoltSale sale = list.get(position);
        allSales.setSaleDate(sale.getBoltSaleDate());
        allSales.setSaleTime(sale.getBoltSaleTime());

        databaseHelper.deleteAllSale(allSales);
        // removing the note from the list
        list.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }
    /**
     * Opens dialog with edit - Delete options
     * Delete - 0
     * Edit - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Delete Selected Transaction");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    deleteItem(position);
                } else {
                    deleteItem(position);
                }
            }
        });
        builder.show();
    }

    private void initobjects() {
        list = new ArrayList<>();
        recyclerAdapter = new BoltSaleRecyclerAdapter(list);

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        databaseHelper = new DatabaseHelper(this);
        getDataFromSQLite();
    }

    private void buttonEvents(){
        retrieve.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if(Objects.equals(textInputEditText.getText().toString(), "")) {
                    if (froDatePicker.getText() == "" && toDatePicker.getText() == ""){
                        //textNetProfit.setText("mommy");
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                                list.clear();
                                list.addAll(databaseHelper.getAllBoltSale());
                                return null;
                            }

                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                recyclerAdapter.notifyDataSetChanged();
                                textNetProfit.setText(String.valueOf(prof()));
                                textNetTotal.setText(String.valueOf(tot()));
                            }
                        }.execute();
                    }
                    else if(froDatePicker.getText() != "" && toDatePicker.getText() == "") {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                                    list.clear();
                                    list.addAll(databaseHelper.getAllBoltSale((String) froDatePicker.getText()));
                                    return null;
                                }

                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    recyclerAdapter.notifyDataSetChanged();
                                    textNetProfit.setText(String.valueOf(prof()));
                                    textNetTotal.setText(String.valueOf(tot()));
                                }
                            }.execute();
                    }

                    else if(froDatePicker.getText() != "" && toDatePicker.getText() != "") {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                                    list.clear();
                                    list.addAll(databaseHelper.getAllBoltSaleBetween((String) froDatePicker.getText(), (String) toDatePicker.getText()));
                                    return null;
                                }

                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    recyclerAdapter.notifyDataSetChanged();
                                    textNetProfit.setText(String.valueOf(prof()));
                                    textNetTotal.setText(String.valueOf(tot()));
                                }
                            }.execute();
                    }

                }
                else if (!Objects.equals(textInputEditText.getText().toString(), "")) {
                  if(froDatePicker.getText() != "" && toDatePicker.getText() != "" ) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                                list.clear();
                                list.addAll(databaseHelper.getAllBoltSaleBetween((String) froDatePicker.getText(), (String) toDatePicker.getText(), textInputEditText.getText().toString().toUpperCase()));
                                return null;
                            }

                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                recyclerAdapter.notifyDataSetChanged();
                                textNetProfit.setText(String.valueOf(prof()));
                                textNetTotal.setText(String.valueOf(tot()));                            }
                        }.execute();
                    }

                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                list.clear();
                list.addAll(databaseHelper.getAllBoltSale());
                return null;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recyclerAdapter.notifyDataSetChanged();
                textNetProfit.setText(String.valueOf(prof()));
                textNetTotal.setText(String.valueOf(tot()));

            }
        }.execute();
    }

}
