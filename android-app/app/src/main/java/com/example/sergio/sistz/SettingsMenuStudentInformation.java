package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStudentInformation extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String Fullname = "";
    public static final String tmp_code="";
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2; // ************ FrameLayout ***************
    EditText _col0, _col1, _col2, _col3, _col5, _col7, _col9, _col11, _col13, _col22;
    TextView tv_col0;
    TextView form_leyend; // 20180525
    RadioButton _col5a, _col5b, _col6a, _col6b, _col6c, _col6d,  _col8a, _col8b, _col10a, _col10b, _col12a, _col12b, _col12aa, _col12bb;  // 20180504 add _col6d
    RadioButton _col14, _col14a, _col14b, _col15, _col16, _col17, _col18, _col19, _col20;
    RadioButton  _col23, _col24, _col25, _col26, _col27, _col28, _col29, _col30, _col31; // new changes... From checkBoxs to radioButton
    DatePicker _col4, _col21;
    Button unselectDisability, unselectForExit;
    ListView lv_list;
    String _IU="U", Dedo="0";
    FloatingActionButton add_reg, save_reg, erase_reg;
    RadioGroup rgStudentDisability,rgForExit;
    String code;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView mImageView;
    private Bitmap mImageBitmap;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    DBUtility conn;

    private String mCurrentPhotoPath;

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile(String code) throws IOException {

        File albumF = getAlbumDir();
        File imageF = new File(albumF, code +".jpg");
        return imageF;
    }

    private File setUpPhotoFile(String code) throws IOException {

        File f = createImageFile(code);
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {


        int targetW = 200;
        int targetH = 178;


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(mCurrentPhotoPath, bitmap);
		/* Associate the Bitmap to the ImageView */

        mImageView.setImageBitmap(orientedBitmap);
        mImageView.setVisibility(View.VISIBLE);
    }

    private void load_pic(String code){
        int targetW = 200;
        int targetH = 178;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        File albumF = getAlbumDir();

        BitmapFactory.decodeFile(albumF.toString()+"/"+code+".jpg", bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(albumF.toString()+"/"+code+".jpg", bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(albumF.toString()+"/"+code+".jpg", bitmap);
		/* Associate the Bitmap to the ImageView */
        if(bitmap!=null) {
            mImageView.setImageBitmap(orientedBitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode, String Code) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        switch(actionCode) {
            case 1:


                try {
                    f = setUpPhotoFile(code);
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }



    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

        } // switch
    }

    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);

        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));

        super.onSaveInstanceState(outState);
    }



    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            if (!isVisibleToUser) {
                Dedo = "1";
                aceptar(1);
                if (ReportS.S_report_enable != "1") {aceptar(1);}
                Dedo = "0";
                // TODO stop audio playback
            }
        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_student_information,
                container, false);

        // ********************** Global vars ******************
        _col0 = (EditText) mLinearLayout.findViewById(R.id._col0);
        tv_col0 = (TextView) mLinearLayout.findViewById(R.id.tv_col0);
        _col1 = (EditText) mLinearLayout.findViewById(R.id._col1);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3 = (EditText) mLinearLayout.findViewById(R.id._col3);
        _col4 = (DatePicker) mLinearLayout.findViewById(R.id._col4);
        _col5a = (RadioButton) mLinearLayout.findViewById(R.id._col5a);
        _col5b = (RadioButton) mLinearLayout.findViewById(R.id._col5b);
        _col5 = (EditText) mLinearLayout.findViewById(R.id._col5);
        _col6a = (RadioButton) mLinearLayout.findViewById(R.id._col6a);
        _col6b = (RadioButton) mLinearLayout.findViewById(R.id._col6b);
        _col6c = (RadioButton) mLinearLayout.findViewById(R.id._col6c);
        _col6d = (RadioButton) mLinearLayout.findViewById(R.id._col6d);  // 20180504 add for Intellectual disability
        _col7 = (EditText) mLinearLayout.findViewById(R.id._col7);
        _col8a = (RadioButton) mLinearLayout.findViewById(R.id._col8a);
        _col8b = (RadioButton) mLinearLayout.findViewById(R.id._col8b);
        _col9 = (EditText) mLinearLayout.findViewById(R.id._col9);
        _col10a = (RadioButton) mLinearLayout.findViewById(R.id._col10a);
        _col10b = (RadioButton) mLinearLayout.findViewById(R.id._col10b);
        _col11 = (EditText) mLinearLayout.findViewById(R.id._col11);
        _col12a = (RadioButton) mLinearLayout.findViewById(R.id._col12a);
        _col12b = (RadioButton) mLinearLayout.findViewById(R.id._col12b);
        _col12aa = (RadioButton) mLinearLayout.findViewById(R.id._col12aa);
        _col12bb = (RadioButton) mLinearLayout.findViewById(R.id._col12bb);
        _col13 = (EditText) mLinearLayout.findViewById(R.id._col13);
        _col14a = (RadioButton) mLinearLayout.findViewById(R.id._col14a);
        _col14b = (RadioButton) mLinearLayout.findViewById(R.id._col14b);
        _col14 = (RadioButton) mLinearLayout.findViewById(R.id._col14);
        _col15 = (RadioButton) mLinearLayout.findViewById(R.id._col15);
        _col16 = (RadioButton) mLinearLayout.findViewById(R.id._col16);
        _col17 = (RadioButton) mLinearLayout.findViewById(R.id._col17);
        _col18 = (RadioButton) mLinearLayout.findViewById(R.id._col18);
        _col19 = (RadioButton) mLinearLayout.findViewById(R.id._col19);
        _col20 = (RadioButton) mLinearLayout.findViewById(R.id._col20);
        _col21 = (DatePicker) mLinearLayout.findViewById(R.id._col21);
        _col22 = (EditText) mLinearLayout.findViewById(R.id._col22);

        rgStudentDisability = (RadioGroup) mLinearLayout.findViewById(R.id.rg_student_disability);
        unselectDisability = (Button) mLinearLayout.findViewById(R.id.btn_unselect_disability);
        rgForExit = (RadioGroup) mLinearLayout.findViewById(R.id.rg_for_exit);
        unselectForExit = (Button) mLinearLayout.findViewById(R.id.btn_unselect_for_exit);
        _col23 = (RadioButton) mLinearLayout.findViewById(R.id._col23);
        _col24 = (RadioButton) mLinearLayout.findViewById(R.id._col24);
        _col25 = (RadioButton) mLinearLayout.findViewById(R.id._col25);
        _col26 = (RadioButton) mLinearLayout.findViewById(R.id._col26);
        _col27 = (RadioButton) mLinearLayout.findViewById(R.id._col27);
        _col28 = (RadioButton) mLinearLayout.findViewById(R.id._col28);
        _col29 = (RadioButton) mLinearLayout.findViewById(R.id._col29);
        _col30 = (RadioButton) mLinearLayout.findViewById(R.id._col30);
        _col31 = (RadioButton) mLinearLayout.findViewById(R.id._col31);

        form_leyend = (TextView) mLinearLayout.findViewById(R.id.form_leyend);

        lv_list = (ListView) mLinearLayout.findViewById(R.id.lv_list);


        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);
        fl_part2 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.add_reg);
        save_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.save_reg);
        erase_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.erase_reg);


        // **************** CLICK ON BUTTONS ********************
        unselectDisability.setOnClickListener(this);
        unselectForExit.setOnClickListener(this);
        save_reg.setOnClickListener(this);
        erase_reg.setOnClickListener(this);
        _col0.setOnClickListener(this);

        save_reg.setVisibility(View.VISIBLE);
        erase_reg.setVisibility(View.VISIBLE);
        add_reg.setVisibility(View.GONE);

        // ***************** LOCAD INFORMATION *************************
        _col4.setMinDate(DateUtility.setDateMinMax(25));
        _col4.setMaxDate(DateUtility.setDateMinMax(3));
        _col5a.setChecked(true); // Change 20190822 to defalult define MALE.
        initialize_upgrade();
        loadRecord(SettingsMenuStudent.TS_code);

        mImageView = (ImageView) mLinearLayout.findViewById(R.id.imageView4);
        Button picSBtn = (Button) mLinearLayout.findViewById(R.id.student_photo);
        picSBtn.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());
        load_pic(SettingsMenuStudent.TS_code);

        if (ReportS.S_report_enable == "1") {
            form_leyend.setVisibility(View.GONE);
            save_reg.setVisibility(View.GONE);
            erase_reg.setVisibility(View.GONE);
            picSBtn.setEnabled(false);
            _col1.setFocusable(false);
            _col2.setFocusable(false);
            _col3.setFocusable(false);
            _col4.setEnabled(false);
            _col5a.setEnabled(false);
            _col5b.setEnabled(false);
            _col5.setFocusable(false);
            _col6a.setEnabled(false);
            _col6b.setEnabled(false);
            _col6c.setEnabled(false);
            _col6d.setEnabled(false); // ADD 20180504 for Intellectual disability
            _col7.setFocusable(false);
            _col8a.setEnabled(false);
            _col8b.setEnabled(false);
            _col9.setFocusable(false);
            _col10a.setEnabled(false);
            _col10b.setEnabled(false);
            _col11.setFocusable(false);
            _col12a.setEnabled(false);
            _col12b.setEnabled(false);
            _col12aa.setEnabled(false);
            _col12bb.setEnabled(false);
            _col13.setEnabled(false);
            _col14a.setEnabled(false);
            _col14b.setEnabled(false);
            _col14.setEnabled(false);
            _col15.setEnabled(false);
            _col16.setEnabled(false);
            _col17.setEnabled(false);
            _col18.setEnabled(false);
            _col19.setEnabled(false);
            _col20.setEnabled(false);
            _col21.setEnabled(false);
            _col22.setEnabled(false);

            _col23.setEnabled(false);
            _col24.setEnabled(false);
            _col25.setEnabled(false);
            _col26.setEnabled(false);
            _col27.setEnabled(false);
            _col28.setEnabled(false);
            _col29.setEnabled(false);
            _col30.setEnabled(false);
            _col31.setEnabled(false);

        }

        // Este es el focus para buscar un registro existente
        _col0.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (!_col0.getText().toString().isEmpty()) {
                        code = _col0.getText().toString();
                        loadRecord(code);
                    }
                }
                else
                {

                }
            }
        });

        return mLinearLayout;
    }


    // **************** Load DATA *************************
    public void loadRecord(String code) {
        if (code.equals("")) {
            _IU = "I";
        } else {
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT _id, surname, givenname, family, yearob, sex, disability, handicap, pre_sch_att, pre_sch_name, readiness, location, pri_sch_prev, pri_sch_name, std_I, std_II, std_III, std_IV, std_V, std_VI, std_VII, sch_exit_date, duration, reason_exit_1, reason_exit_2, reason_exit_3, reason_exit_4, reason_exit_5, reason_do_1, reason_do_2, reason_do_3, reason_do_4, contact flag, orphan, yeari, yearii FROM student WHERE _id=" + code, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {
                _col0.setText(cur_data.getString(0));
                tv_col0.setText(cur_data.getString(0));
                _col0.setVisibility(View.GONE);
                tv_col0.setVisibility(View.VISIBLE);
                _col1.setText(cur_data.getString(1));
                _col2.setText(cur_data.getString(2));
                _col3.setText(cur_data.getString(3));
                //_col4.setMaxDate(System.currentTimeMillis());
                _col4.updateDate(Integer.parseInt(cur_data.getString(4).substring(0, 4)), Integer.parseInt(cur_data.getString(4).substring(5, 7)) - 1, Integer.parseInt(cur_data.getString(4).substring(8, 10)));
                switch (cur_data.getInt(5)) {
                    case 1: _col5a.setChecked(true); break;
                    case 2: _col5b.setChecked(true); break;
                }
                switch (cur_data.getInt(6)) {
                    case 1: _col6a.setChecked(true); break;
                    case 2: _col6b.setChecked(true); break;
                    case 3: _col6c.setChecked(true); break;
                    case 4: _col6d.setChecked(true); break;
                }
                _col7.setText(cur_data.getString(7));
                switch (cur_data.getInt(8)) {
                    case 1: _col8a.setChecked(true); break;
                    case 2: _col8b.setChecked(true); break;
                }
                _col9.setText(cur_data.getString(9));
                switch (cur_data.getInt(10)) {
                    case 1: _col10a.setChecked(true); break;
                    case 2: _col10b.setChecked(true); break;
                }
                _col11.setText(cur_data.getString(11));
                switch (cur_data.getInt(12)) {
                    case 1: _col12a.setChecked(true); break;
                    case 2: _col12b.setChecked(true); break;
                }
                _col13.setText(cur_data.getString(13));

                if (cur_data.getInt(14) == 1 ) {_col14.setChecked(true);}
                if (cur_data.getInt(15) == 1 ) {_col15.setChecked(true);}
                if (cur_data.getInt(16) == 1 ) {_col16.setChecked(true);}
                if (cur_data.getInt(17) == 1 ) {_col17.setChecked(true);}
                if (cur_data.getInt(18) == 1 ) {_col18.setChecked(true);}
                if (cur_data.getInt(19) == 1 ) {_col19.setChecked(true);}
                if (cur_data.getInt(20) == 1 ) {_col20.setChecked(true);}

                _col21.setMaxDate(System.currentTimeMillis());
                _col22.setText(cur_data.getString(22));

                if (cur_data.getInt(23) == 1 ) {_col23.setChecked(true);}
                if (cur_data.getInt(24) == 1 ) {_col24.setChecked(true);}
                if (cur_data.getInt(25) == 1 ) {_col25.setChecked(true);}
                if (cur_data.getInt(26) == 1 ) {_col26.setChecked(true);}
                if (cur_data.getInt(27) == 1 ) {_col27.setChecked(true);}

                if (cur_data.getInt(28) == 1 ) {_col28.setChecked(true);}
                if (cur_data.getInt(29) == 1 ) {_col29.setChecked(true);}
                if (cur_data.getInt(30) == 1 ) {_col30.setChecked(true);}
                if (cur_data.getInt(31) == 1 ) {_col31.setChecked(true);}

                _col5.setText(cur_data.getString(32));

                switch (cur_data.getInt(33)) {
                    case 1: _col12aa.setChecked(true); break;
                    case 2: _col12bb.setChecked(true); break;
                }
                if (cur_data.getInt(34) == 1 ) {_col14a.setChecked(true);}
                if (cur_data.getInt(35) == 1 ) {_col14b.setChecked(true);}
                _IU = "U";
                _col0.requestFocus();
                erase_reg.setVisibility(View.VISIBLE);
            }
            cur_data.close();
            dbSET.close();
            cnSET.close();
        }

    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="|", tableName="student", dateofbirth="";
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (!_col0.getText().toString().isEmpty()){
            SettingsMenuStudent.TS_code = _col0.getText().toString();

            reg.put("flag", _IU); sql = sql + tableName + delimit + getEMIS_code() + delimit;;
            reg.put("_id",Integer.parseInt(_col0.getText().toString())); sql = sql + _col0.getText().toString() + delimit;
            if (!_col1.getText().toString().isEmpty()){reg.put("surname", _col1.getText().toString());} sql = sql + _col1.getText().toString() + delimit;
            if (!_col2.getText().toString().isEmpty()){reg.put("givenname", _col2.getText().toString());} sql = sql + _col2.getText().toString() + delimit;
            if (!_col3.getText().toString().isEmpty()){reg.put("family", _col3.getText().toString());} sql = sql + _col3.getText().toString() + delimit;

            // ******************* Date Of Birth
            int day = _col4.getDayOfMonth();
            int month = _col4.getMonth();
            int year = _col4.getYear();
            calendar.set(year, month, day);
            String strDate = formatDatabase.format(calendar.getTime());
            reg.put("yearob", strDate); sql = sql + strDate + delimit;

            if (_col5a.isChecked()==true) {reg.put("sex",1); sql = sql + "1" + delimit;}
            else if (_col5b.isChecked()==true) {reg.put("sex",2); sql = sql + "2" + delimit;} else {sql = sql + "" + delimit;}

            if (!_col5.getText().toString().isEmpty()){reg.put("contact", _col5.getText().toString());} sql = sql + _col5.getText().toString() + delimit;

            if (_col6a.isChecked()==true) {reg.put("disability",1); sql = sql + "1" + delimit;}
            else if (_col6b.isChecked()==true) {reg.put("disability",2); sql = sql + "2" + delimit;}
            else if (_col6c.isChecked()==true) {reg.put("disability",3); sql = sql + "3" + delimit;} //else {sql = sql + "" + delimit;}
            else if (_col6d.isChecked()==true) {reg.put("disability",4); sql = sql + "4" + delimit;}else {reg.put("disability",0); sql = sql + "" + delimit;} // ADD for Intellectual disability

            if (!_col7.getText().toString().isEmpty()){reg.put("handicap", _col7.getText().toString());} sql = sql + _col7.getText().toString() + delimit;

            if (_col8a.isChecked()==true) {reg.put("pre_sch_att",1); sql = sql + "1" + delimit;}
            else if (_col8b.isChecked()==true) {reg.put("pre_sch_att",2); sql = sql + "2" + delimit;} else {sql = sql + "" + delimit;}

            if (!_col9.getText().toString().isEmpty()){reg.put("pre_sch_name", _col9.getText().toString());} sql = sql + _col9.getText().toString() + delimit;

            if (_col10a.isChecked()==true) {reg.put("readiness",1); sql = sql + "1" + delimit;}
            else if (_col10b.isChecked()==true) {reg.put("readiness",2); sql = sql + "2" + delimit;} else {sql = sql + "" + delimit;}

            if (!_col11.getText().toString().isEmpty()){reg.put("location", _col11.getText().toString());} sql = sql + _col11.getText().toString() + delimit;

            if (_col12a.isChecked()==true) {reg.put("pri_sch_prev",1); sql = sql + "1" + delimit;}
            else if (_col12b.isChecked()==true) {reg.put("pri_sch_prev",2); sql = sql + "2" + delimit;} else {sql = sql + "" + delimit;}

            if (!_col13.getText().toString().isEmpty()){reg.put("pri_sch_name", _col13.getText().toString());} sql = sql + _col13.getText().toString() + delimit;

            if (_col14.isChecked()==true) {reg.put("std_I",1); sql = sql + "1" + delimit;} else {reg.put("std_I",0); sql = sql + "0" + delimit;}
            if (_col15.isChecked()==true) {reg.put("std_II",1); sql = sql + "1" + delimit;} else {reg.put("std_II",0); sql = sql + "0" + delimit;}
            if (_col16.isChecked()==true) {reg.put("std_III",1); sql = sql + "1" + delimit;} else {reg.put("std_III",0); sql = sql + "0" + delimit;}
            if (_col17.isChecked()==true) {reg.put("std_IV",1); sql = sql + "1" + delimit;} else {reg.put("std_IV",0); sql = sql + "0" + delimit;}
            if (_col18.isChecked()==true) {reg.put("std_V",1); sql = sql + "1" + delimit;} else {reg.put("std_V",0); sql = sql + "0" + delimit;}
            if (_col19.isChecked()==true) {reg.put("std_VI",1); sql = sql + "1" + delimit;} else {reg.put("std_VI",0); sql = sql + "0" + delimit;}
            if (_col20.isChecked()==true) {reg.put("std_VII",1); sql = sql + "1" + delimit;} else {reg.put("std_VII",0); sql = sql + "0" + delimit;}

            int day1 = _col21.getDayOfMonth();
            int month1 = _col21.getMonth();
            int year1 = _col21.getYear();
            calendar.set(year1, month1, day1);
            String strDate1 = formatDatabase.format(calendar.getTime());
            reg.put("sch_exit_date", strDate1); sql = sql + strDate1 + delimit;

            if (!_col22.getText().toString().isEmpty()){reg.put("duration", Integer.parseInt(_col22.getText().toString()));} sql = sql + _col22.getText().toString() + delimit;

            if (_col23.isChecked()==true) {reg.put("reason_exit_1",1); sql = sql + "1" + delimit;} else {reg.put("reason_exit_1",0); sql = sql + "0" + delimit;}
            if (_col24.isChecked()==true) {reg.put("reason_exit_2",1); sql = sql + "1" + delimit;} else {reg.put("reason_exit_2",0); sql = sql + "0" + delimit;}
            if (_col25.isChecked()==true) {reg.put("reason_exit_3",1); sql = sql + "1" + delimit;} else {reg.put("reason_exit_3",0); sql = sql + "0" + delimit;}
            if (_col26.isChecked()==true) {reg.put("reason_exit_4",1); sql = sql + "1" + delimit;} else {reg.put("reason_exit_4",0); sql = sql + "0" + delimit;}
            if (_col27.isChecked()==true) {reg.put("reason_exit_5",1); sql = sql + "1" + delimit;} else {reg.put("reason_exit_5",0); sql = sql + "0" + delimit;}

            if (_col28.isChecked()==true) {reg.put("reason_do_1",1); sql = sql + "1" + delimit;} else {reg.put("reason_do_1",0); sql = sql + "0" + delimit;}
            if (_col29.isChecked()==true) {reg.put("reason_do_2",1); sql = sql + "1" + delimit;} else {reg.put("reason_do_2",0); sql = sql + "0" + delimit;}
            if (_col30.isChecked()==true) {reg.put("reason_do_3",1); sql = sql + "1" + delimit;} else {reg.put("reason_do_3",0); sql = sql + "0" + delimit;}
            if (_col31.isChecked()==true) {reg.put("reason_do_4",1); sql = sql + "1" + delimit;} else {reg.put("reason_do_4",0); sql = sql + "0" + delimit;}

            if (_col12aa.isChecked()==true) {reg.put("orphan",1); sql = sql + "1" + delimit;}
            else if (_col12bb.isChecked()==true) {reg.put("orphan",2); sql = sql + "2" + delimit;} else {sql = sql + "" + delimit;}

            if (_col14a.isChecked()==true) {reg.put("yeari",1); sql = sql + "1" + delimit;} else {reg.put("yeari",0); sql = sql + "0" + delimit;}
            if (_col14b.isChecked()==true) {reg.put("yearii",1); sql = sql + "1" + delimit;} else {reg.put("yearii",0); sql = sql + "0" + delimit;}

            sql = sql + _IU;
            try {
                if (_IU=="I") {dbSET.insert("student", null, reg); _IU="U";}
                else {dbSET.update("student", reg, "_id=" + _col0.getText().toString(), null);}
                if (Dedo == "0") {
                    toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
                }
                _col0.setVisibility(View.GONE);
                tv_col0.setVisibility(View.VISIBLE);
            }catch (Exception e) {
            }
        }
        dbSET.close();
        cnSET.close();
    }

    public void initialize_upgrade() {
        _col0.setText("");
        _col1.setText("");
        _col2.setText("");
        _col3.setText("");
        _col5.setText("");
        _col7.setText("");
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                fl_part1.setVisibility(View.GONE);
                fl_part2.setVisibility(View.VISIBLE);
                dialogAlert(1);
                break;
            case R.id.erase_reg:
                dialogAlert(3);
                break;
            case R.id.save_reg:
                //updateRecord();
                dialogAlert(1);
                break;
            case R.id.btn_exit:
                dialogAlert(2);
                break;
            case R.id._col0:
                if (!_col0.getText().toString().isEmpty()) {
                    code = _col0.getText().toString();
                    loadRecord(code);
                }
                break;
            case R.id.student_photo:
                if (_col0.getText().toString().isEmpty()) {
                    dialogAlert2(1);
                }
                else{
                    code = _col0.getText().toString();
                    dispatchTakePictureIntent(1, code);
                }
                break;
            case R.id.btn_unselect_disability: //R.id.btn_unselect_student_disability:
                rgStudentDisability.clearCheck();
                break;
            case R.id.btn_unselect_for_exit: //R.id.btn_unselect_student_for_exit:
                rgForExit.clearCheck();
                break;
        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("Are you sure you want to save?");}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        if (v == 3){dialogo1.setMessage("Are you sure to delete record?");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar(val);
            }
        });
        dialogo1.show();
    }
    public void dialogAlert2(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Important");
        if (v == 1){dialogo1.setMessage("You need put a Student ID ");}
        if (v == 4){dialogo1.setMessage("Teacher has assigned subjects ");}
        if (v == 5){dialogo1.setMessage("Empty");}
        if (v == 6){dialogo1.setMessage("Empty");}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    public void aceptar(int action) {
        switch (action) {
            case 1:
                updateRecord();
                break;
            case 3:
                if (eraseTeacherAssing(SettingsMenuStudent.TS_code) == "0") {
                    try {
                        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
                        SQLiteDatabase dbSET = cnSET.getWritableDatabase();
                        dbSET.delete("student", "_id=" + String.valueOf(_col0.getText()), null);
                        dbSET.close();
                        cnSET.close();
                        SettingsMenuStudent.TS_code="";
                    }catch (Exception e) {}
                    getActivity().finish();
                } else {
                    dialogAlert2(4);
                }
                break;
        }
    }
    public void cancelar() {
    }

    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }

    public String eraseTeacherAssing(String code){
        String getcode="0";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis, tc, shift,level,grade,section,subject, subject_assigned, year_ta FROM _sa WHERE tc=" + code, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getcode = "1";}
        } catch (Exception e){}

        return getcode;
    }

}
