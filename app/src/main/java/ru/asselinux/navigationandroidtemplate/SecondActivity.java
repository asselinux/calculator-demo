package ru.asselinux.navigationandroidtemplate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

public class SecondActivity extends BaseActivity {

    private static final NumberFormat currencyFormatValue = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double tipPercent = 0.25;
    private TextView txtBillMoney;
    private TextView txtPercent;
    private TextView txtTip;
    private TextView txtTotalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_second, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_gallery);

        txtBillMoney = findViewById(R.id.txtBillMoney);
        txtPercent = findViewById(R.id.txtPercent);
        txtTip = findViewById(R.id.txtTip);
        txtTotalBill = findViewById(R.id.txtTotalBill);

        txtTip.setText(currencyFormatValue.format(0));
        txtTotalBill.setText(currencyFormatValue.format(0));

        EditText edtMoney = findViewById(R.id.edtMoney);
        edtMoney.addTextChangedListener(tipEdtMoneyTextWatcher);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(tipSeekBarChangeListener);
    }

    private final TextWatcher tipEdtMoneyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                txtBillMoney.setText(currencyFormatValue.format(billAmount));
            } catch (NumberFormatException e) {
                txtBillMoney.setText("");
                billAmount = 0.0;
            }
            calculateTip();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private final SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            tipPercent = progress/100.0;
            calculateTip();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void calculateTip () {
        txtPercent.setText(percentFormatValue.format(tipPercent));

        double tipValue = billAmount * tipPercent;
        double totalValue = billAmount + tipValue;

        txtTip.setText(currencyFormatValue.format(tipValue));
        txtTotalBill.setText(currencyFormatValue.format(totalValue));
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
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
