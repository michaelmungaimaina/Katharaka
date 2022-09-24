package mich.gwan.katharaka.activities.admin.ui.gallery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.adapters.AllSale.AllsaleRecyclerAdapter;
import mich.gwan.katharaka.adapters.pdf.PDFUtility;
import mich.gwan.katharaka.databinding.FragmentGalleryBinding;
import mich.gwan.katharaka.helpers.RecyclerTouchListener;
import mich.gwan.katharaka.model.pdf.PDF;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.sql.DatabaseHelper;
public class GalleryFragment extends Fragment implements PDFUtility.OnDocumentClose {

    private RecyclerView recyclerView;
    private List<AllSales> list;
    private DatabaseHelper databaseHelper;
    private AllsaleRecyclerAdapter recyclerAdapter;
    DatePickerDialog froDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    @SuppressLint("StaticFieldLeak")
    private TextView froDatePicker;
    private TextView toDatePicker;
    private TextView textNetTotal;
    private TextView textNetProfit;
    private TextView textPdf;
    private TextView textExcel;
    private AppCompatTextView title;
    private TextInputEditText textInputEditText;
    private CardView retrieve;
    private CardView pdf;
    private CardView excel;
    private FragmentGalleryBinding binding;
    private Context mContext;
    private LinearLayout lap;
    private String m;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        initViews();
        initobjects();
        buttonEvents();

        return root;
    }

    private  void initViews(){

        recyclerView = binding.recyclerViewGasSale;
        toDatePicker = binding.toDatePicker;
        froDatePicker = binding.froDatePicker;
        retrieve = binding.cardRerieve;
        title = binding.tittle;
        pdf = binding.cardPdf;
        excel = binding.cardExcel;
        textNetProfit = binding.textGasSaleNetProfit;
        textNetTotal = binding.textGasSaleNetTotal;
        textInputEditText = binding.catTextInputEditText;
        lap = binding.lap;

        froDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                froDatePickerDialog = new DatePickerDialog(mContext, R.style.CustomDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

                toDatePickerDialog = new DatePickerDialog(mContext, R.style.CustomDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = path();
                // Make sure the path directory exists.
                try
                {
                    PDFUtility.createPdf(v.getContext(),GalleryFragment.this, getSampleData(),path(),true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    //Log.e(TAG,"Error Creating Pdf");
                    Toast.makeText(mContext,"Error Creating Pdf", Toast.LENGTH_SHORT).show();
                }
                    //generatePDF();
            }
        });
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    generateEXCEL();
            }
        });

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         **/
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

    }

    /**
     * Delete the item from SQLite and remove
     * it from the list
     **/
   // private void deleteItem(int position) {
        // deleting the note from db
        //databaseHelper.deleteGasSale(list.get(position));

        // removing the note from the list
        //list.remove(position);
        //recyclerAdapter.notifyItemRemoved(position);
    //}
    /**
     * Opens dialog with edit - Delete options
     * Delete - 0
     * Edit - 0
     **/
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getView().getContext());
        builder.setTitle("Delete Selected Transaction");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //deleteItem(position);getString(R.string.oilsale_transact_success)


                    Snackbar snackbar = Snackbar.make(lap, "Invalid Operation, Delete on Individual Transactions", Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    view.setBackgroundColor(Color.RED);
                    CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP | Gravity.CENTER;
                    params.setMargins(0,0,0,0);
                    view.setLayoutParams(params);
                    snackbar.setTextColor(Color.WHITE);
                    (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    (snackbar.getView()).getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    snackbar.show();

                } else {
                    //deleteItem(position);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onPDFDocumentClose(File file)
    {
        Toast.makeText(mContext,"Data processed successfully",Toast.LENGTH_SHORT).show();
        // Get the File location and file name.
        Log.d("pdfFIle", "" + m);

        // Get the URI Path of file.
        Uri uriPdfPath = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", new File(m));
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
            Toast.makeText(mContext,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

        }
    }


    private String path(){
        String path = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/"  + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+"_AllSales.pdf";
        }
        else
        {
            path = Environment.getExternalStorageDirectory() + "/Sarensa/Katharaka/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+"_AllSales.pdf";
        }
        return path;
    }

    private List<String[]> getSampleData()
    {
        int count = list.size();

        List<String[]> temp = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            AllSales sales = list.get(i);
            temp.add(new String[] {sales.getSaleDate(), sales.getSalecategory(),
                    String.valueOf(sales.getSaleQnty()), String.valueOf(sales.getSaleUprice()) + ".00",
                    String.valueOf(sales.getSaleTotal()) + ".00", String.valueOf(sales.getSaleProfit()) + ".00"});
        }
        temp.add(new String[] {"TOTAL", "", "", "", String.valueOf(tot() + ".00"), String.valueOf(prof() + ".00")});
        return  temp;
    }

    public int tot() {
        int total = 0;
        for(int i = 0; i <list.size(); i++) {
            total += list.get(i).getSaleTotal();
        }
        return total;
    }
    public int prof() {
        int profit = 0;
        for(int i = 0; i <list.size(); i++) {
            profit += list.get(i).getSaleProfit();
        }
        return profit;
    }

    public void changeCellBackgroundColor(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        if(cellStyle == null) {
            cellStyle = cell.getSheet().getWorkbook().createCellStyle();
        }
        //cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
    }

    public void generateEXCEL(){
        HSSFWorkbook xssfWorkbook = new HSSFWorkbook();

        HSSFFont font= xssfWorkbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        font.setItalic(false);

        HSSFFont defaultFon= xssfWorkbook.createFont();
        defaultFon.setFontHeightInPoints((short)14);
        defaultFon.setFontName("Arial");
        defaultFon.setColor(IndexedColors.WHITE.getIndex());
        defaultFon.setBold(true);
        defaultFon.setItalic(false);

        HSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet 1");
        xssfSheet.createFreezePane(0,0,7,2 );
        xssfSheet.createFreezePane(0,0,1, list.size() + 2 );
        HSSFRow firstRo = xssfSheet.createRow(0);
        firstRo.createCell(0).setCellValue("SARENSA ENTERPRISES LIMITED");
        changeCellBackgroundColor(firstRo.getCell(0));

        HSSFFont defaultFont= xssfWorkbook.createFont();
        defaultFont.setFontHeightInPoints((short)10);
        defaultFont.setFontName("Arial");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        defaultFont.setBold(true);
        defaultFont.setItalic(false);

        xssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        xssfSheet.addMergedRegion(new CellRangeAddress(list.size() + 2,list.size() + 2,0,4));

        HSSFRow firstRow = xssfSheet.createRow(1);

        firstRow.createCell(0).setCellValue("DATE");
        firstRow.createCell(1).setCellValue("TIME");
        firstRow.createCell(2).setCellValue("CATEGORY");
        firstRow.createCell(3).setCellValue("QUANTITY");
        firstRow.createCell(4).setCellValue("UNIT PRICE");
        firstRow.createCell(5).setCellValue("TOTAL");
        firstRow.createCell(6).setCellValue("PROFIT");
        // }
        for (int row = 0; row < list.size(); row++) {
            HSSFRow xssfRow = xssfSheet.createRow(row + 2);
            AllSales sales = list.get(row);
            xssfRow.createCell(0).setCellValue(sales.getSaleDate());
            xssfRow.createCell(1).setCellValue(sales.getSaleTime());
            xssfRow.createCell(2).setCellValue(sales.getSalecategory());
            xssfRow.createCell(3).setCellValue(Double.parseDouble(String.valueOf(Double.valueOf(sales.getSaleQnty()))));
            xssfRow.createCell(4).setCellValue(Double.parseDouble(String.valueOf(Double.valueOf(sales.getSaleUprice()))));
            xssfRow.createCell(5).setCellValue(Double.parseDouble(String.valueOf(Double.valueOf(sales.getSaleTotal()))));
            xssfRow.createCell(6).setCellValue(Double.parseDouble(String.valueOf(Double.valueOf(sales.getSaleProfit()))));
        }
        HSSFRow xssfRow = xssfSheet.createRow(list.size() + 2);
        xssfRow.createCell(0).setCellValue("TOTAL");
        xssfRow.createCell(5).setCellValue(tot());
        xssfRow.createCell(6).setCellValue(prof());

        DateTimeFormatter m = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime t = LocalDateTime.now();
        String filename = m.format(t) + "_katharaka_allsale_data.xls";
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Sarensa/Katharaka/" + filename;
        File file = new File(filePath);
        //if (file.exists() || file.mkdirs()) {
            try {
                xssfWorkbook.write(new FileOutputStream(file));
                xssfWorkbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
       // }
        AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
        builder.setTitle("SUCCESS")
                .setMessage("File Generated Successfully.")
                .setIcon(R.drawable.icons8_ok_96px_1)
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        File dir = new File(Environment.getExternalStorageDirectory(), "/Sarensa/Katharaka");
                        File imgFile = new File(dir, "0.xls");
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setType("Katharaka/*");
                        sendIntent.setAction(Intent.ACTION_VIEW);
                        sendIntent.putExtra(Intent.ACTION_VIEW, Uri.parse("file://" + imgFile));
                        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(sendIntent, "Open with..."));

                    }

                }).show();

    }


    private void initobjects() {
        list = new ArrayList<>();
        recyclerAdapter = new AllsaleRecyclerAdapter(list);

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
                databaseHelper = new DatabaseHelper(mContext);
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
                                list.addAll(databaseHelper.getAllSale());
                                return null;
                            }

                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                recyclerAdapter.notifyDataSetChanged();
                                textNetProfit.setText((String.valueOf(prof())));
                                textNetTotal.setText(String.valueOf(tot()));
                            }
                        }.execute();
                    }
                    else if(froDatePicker.getText() != "" && toDatePicker.getText() == "") {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(@SuppressLint("StaticFieldLeak") Void... params) {
                                list.clear();
                                list.addAll(databaseHelper.getAllSale((String) froDatePicker.getText()));
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
                                list.addAll(databaseHelper.getAllSaleBetween((String) froDatePicker.getText(), (String) toDatePicker.getText()));
                                toDatePicker.setText("");
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
                                list.addAll(databaseHelper.getAllSaleBetween((String) froDatePicker.getText(), (String) toDatePicker.getText(), textInputEditText.getText().toString().toUpperCase()));
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
                list.addAll(databaseHelper.getAllSale());
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}