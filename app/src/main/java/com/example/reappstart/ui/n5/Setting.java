package com.example.reappstart.ui.n5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.reappstart.MainActivity;
import com.example.reappstart.databinding.SettingBinding;

public class Setting extends Activity {
    private SettingBinding binding;
    DBHelper db = new DBHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("알림").setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("member", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor spe = sp.edit();
                        spe.clear();
                        spe.commit();
                        Toast.makeText(Setting.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Setting.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("알림").setMessage("탈퇴하시겠습니까? 모든 개인정보는 즉시 파기 됩니다");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("member", Activity.MODE_PRIVATE);
                        String id = sp.getString("id", null);
                        String name = sp.getString("name", null);

                        db.memberDelete(id);

                        SharedPreferences.Editor spe = sp.edit();
                        spe.clear();
                        spe.commit();

                        Toast.makeText(Setting.this, "탈퇴되었습니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Setting.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }
}
