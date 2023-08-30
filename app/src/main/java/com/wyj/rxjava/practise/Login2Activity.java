package com.wyj.rxjava.practise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import com.wyj.rxjava.R;
import com.wyj.rxjava.databinding.ActivityLogin2Binding;

public class Login2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ActivityLogin2Binding login2Binding = DataBindingUtil.setContentView(this, R.layout.activity_login2);
    }
}