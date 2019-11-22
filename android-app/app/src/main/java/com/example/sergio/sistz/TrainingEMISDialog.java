package com.example.sergio.sistz;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.DBUtility;
import com.example.sergio.sistz.util.FileUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TrainingEMISDialog extends Dialog {

    @BindView(R.id.dialog_emis_value)
    EditText txtEmis;
    private SharedPreferences p;
    private SharedPreferences.Editor edit;

    public TrainingEMISDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_training_emis);
        ButterKnife.bind(this);
         p = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit =  p.edit();
    }

    @OnClick({R.id.dialog_emis_cancel, R.id.dialog_emis_continue})
    public void onClickButton (View v) {
        if (v.getId() == R.id.dialog_emis_continue) {
            int emis = Integer.parseInt(txtEmis.getText().toString());

            if (emis >= 99990 && emis <= 100019 ) {
                FileUtility.changeDataBaseFile(txtEmis.getText().toString(), DBUtility.DB_NAME, "dbsis.sqlite.bak", getContext());
                this.dismiss();
                edit.putInt("training", 1);
                edit.commit();
                ((main_v3)this.getContext()).finish();
            } else {

                edit.putInt("training", 0);
                edit.commit();
                this.dismiss();
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.str_not_valid_emis_code), Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.dialog_emis_cancel) {
            this.dismiss();
        }
    }
}
