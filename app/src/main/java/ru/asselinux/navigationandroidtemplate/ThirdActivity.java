package ru.asselinux.navigationandroidtemplate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class ThirdActivity extends BaseActivity {

    private static final NumberFormat currencyFormatValue = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue = NumberFormat.getPercentInstance();

    private double totalSalary = 0.0;
    private double savingPercent = 0.25;
    private TextView savePercent;
    private TextView txtMoneySaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_third, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_slideshow);

        savePercent = findViewById(R.id.savePercent);
        txtMoneySaved = findViewById(R.id.txtMoneySaved);
        txtMoneySaved.setText(currencyFormatValue.format(0));

        EditText edtSalary = findViewById(R.id.edtSalary);
        edtSalary.addTextChangedListener(tipEdtSalaryTextWatcher);

        SeekBar seekBarSave = findViewById(R.id.seekBarSave);
        seekBarSave.setOnSeekBarChangeListener(tipSeekBarSaveChangeListener);
    }

    private final TextWatcher tipEdtSalaryTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                totalSalary = Double.parseDouble(s.toString());
                calculatorSavings();
            } catch (NumberFormatException e) {
                totalSalary = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private final SeekBar.OnSeekBarChangeListener tipSeekBarSaveChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            savingPercent = progress / 100.0;
            calculatorSavings();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void calculatorSavings() {
        savePercent.setText(percentFormatValue.format(savingPercent));
        double savePercent = totalSalary * savingPercent;
        txtMoneySaved.setText(currencyFormatValue.format(savePercent));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_third, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menu_second) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


