package com.example.raza.total_logix_customer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.raza.total_logix_customer.R;
import com.example.raza.total_logix_customer.support_classes.BaseCardFormActivity;

public class CreditCardActivity extends BaseCardFormActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (mCardForm.isCardScanningAvailable()) {
            getMenuInflater().inflate(R.menu.card_io, menu);
        }

        return true;
    }

}
