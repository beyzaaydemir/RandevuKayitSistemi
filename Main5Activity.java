package com.example.asus.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    EditText editrandevuid,editTC,editad,editsoyad,edittarih;
    Spinner spinner,spinnerdoktor;
    List<String> list;

    Context context = this;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        editrandevuid=(EditText)findViewById(R.id.editrandevuid);
        editTC=(EditText)findViewById(R.id.editTC);
        editad=(EditText)findViewById(R.id.editad);
        editsoyad=(EditText)findViewById(R.id.editsoyad);
        edittarih=(EditText)findViewById(R.id.edittarih);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinnerdoktor=(Spinner)findViewById(R.id.spinnerdoktor);

        init();
        init2();
    }

    public void RandevuIslem(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_randevuolustur:
                String TC=editTC.getText().toString();
                String ad=editad.getText().toString();
                String soyad=editsoyad.getText().toString();
                String tarih=edittarih.getText().toString();
                String klinik=spinner.getSelectedItem().toString();
                String doktor=spinnerdoktor.getSelectedItem().toString();

                try
                {
                    if(TC.isEmpty() || ad.isEmpty() || soyad.isEmpty() || tarih.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Alanlar bo?? ge??ilemez!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Randevu rndv=new Randevu(TC,ad,soyad,tarih,klinik,doktor);
                        VeriTabani db=new VeriTabani(getApplicationContext());

                        long id=db.RandevuOlustur(rndv);
                        if(id == -1)
                        {
                            Toast.makeText(getApplicationContext(),"Randevu olu??turulamad??.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Randevu olu??turuldu.",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Bilinmeyen Hata!\n" + e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_randevulistele:
                VeriTabani db=new VeriTabani(this);
                Cursor res=db.RandevuListele();
                if(res.getCount() == 0)
                {
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("ID:" +res.getInt(0) + "\n");
                    buffer.append("TC:" +res.getString(1)+ "\n");
                    buffer.append("AD:" +res.getString(2)+ "\n");
                    buffer.append("SOYAD:" +res.getString(3)+ "\n");
                    buffer.append("TAR??H:" +res.getString(4)+ "\n");
                    buffer.append("KL??N??K:" +res.getString(5)+ "\n");
                    buffer.append("DOKTOR:" +res.getString(6)+ "\n\n\n");
                }
                showMessage("RANDEVULAR",buffer.toString());
                break;
            case R.id.btn_randevusil:
                VeriTabani myDb=new VeriTabani(this);
                Integer deletedRows=myDb.RandevuSil(editrandevuid.getText().toString());
                if(deletedRows>0)
                {
                    Toast.makeText(Main5Activity.this, "Kisi silindi.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Main5Activity.this, "Kisi silinemedi", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_randevuguncelle:
                VeriTabani db1=new VeriTabani(this);
                boolean isUpdate=db1.RandevuGuncelle(editrandevuid.getText().toString(),editTC.getText().toString(),editad.getText().toString(),editsoyad.getText().toString(),edittarih.getText().toString(),spinner.getSelectedItem().toString(),spinnerdoktor.getSelectedItem().toString());
                if(isUpdate==true)
                    Toast.makeText(Main5Activity.this,"Randevular guncellendi.",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Main5Activity.this,"Randevular guncellenemedi.",Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void init() {
        list = new ArrayList<>();

        list.add("Estetik Di?? Hekimli??i");
        list.add("Ortadonti");
        list.add("??mplantoloji");
        list.add("Protez");
        list.add("Di?? Beyazlatma");
        list.add("Endodonti");
        list.add("Dolgu Tedavisi");
        list.add("Perrodontoloji");

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

    }

    public void init2() {
        list = new ArrayList<>();

        list.add("Sena Y??lmaz");
        list.add("Kenan Ay");
        list.add("Serap Yal????n");
        list.add("Ahmet G??ne??");
        list.add("Yaren Parlak");
        list.add("K??bra Deniz");
        list.add("??ule Demir");
        list.add("Ali G??n");

        spinnerdoktor = (Spinner) findViewById(R.id.spinnerdoktor);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, list);
        spinnerdoktor.setAdapter(adapter);

    }
}