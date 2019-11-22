package com.example.sergio.sistz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Sergio on 2/25/2016.
 */
public class UP_SettingsMenuInfra_1 extends Activity{
    private TabHost TbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_settings_menu_infra_1);

        TbH = (TabHost) findViewById(R.id.tabHost); //llamamos al Tabhost
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tab1 = TbH.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = TbH.newTabSpec("tab2");
        TabHost.TabSpec tab3 = TbH.newTabSpec("tab3");
        TabHost.TabSpec tab4 = TbH.newTabSpec("tab4");

        tab1.setIndicator("Students");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.ll_uno); //definimos el id de cada Tab (pestaña)

        tab2.setIndicator("Repeaters");
        tab2.setContent(R.id.ll_dos);

        tab3.setIndicator("Intakes");
        tab3.setContent(R.id.ll_tres);

        tab4.setIndicator("Bind");
        tab4.setContent(R.id.ll_cuatro);

        TbH.addTab(tab1); //añadimos los tabs ya programados
        TbH.addTab(tab2);
        TbH.addTab(tab3);
        TbH.addTab(tab4);
    }
}
