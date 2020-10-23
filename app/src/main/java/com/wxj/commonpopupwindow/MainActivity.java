package com.wxj.commonpopupwindow;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wxj.library_androidx.CommonPopupWindow;

public class MainActivity extends AppCompatActivity {
    CommonPopupWindow bottomPopupWindow;
    CommonPopupWindow dropDownPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomPopupWindow();
        initDropDownPopupWindow();
        findViewById(R.id.tv_bottom).setOnClickListener(v -> {
            if (bottomPopupWindow != null) {
                bottomPopupWindow.showAtBottom(findViewById(R.id.ll_root), 0, 0);
            }
        });
        findViewById(R.id.tv_drop_down).setOnClickListener(v -> {
            if (dropDownPopupWindow != null) {
                dropDownPopupWindow.showAsDropDown(findViewById(R.id.tv_drop_down), 0, 0);
            }
        });
    }

    private void initBottomPopupWindow() {
        bottomPopupWindow = new CommonPopupWindow.Builder(MainActivity.this)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentView(R.layout.layout_popup)
                .setAnimationStyle(R.style.popwindow_bottom_anim)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .enableBackgroundAlpha(true)
                .setOnCreateView(new CommonPopupWindow.onViewCallBack() {
                    @Override
                    public void setView(View view) {
                        TextView textView = view.findViewById(R.id.textView);
                        textView.setText("点击我");
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setOnDismissListener(new CommonPopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Toast.makeText(MainActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                    }
                }).create();

    }

    private void initDropDownPopupWindow() {
        dropDownPopupWindow = new CommonPopupWindow.Builder(MainActivity.this)
                .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentView(R.layout.layout_drop_down)
                .setAnimationStyle(R.style.popwindow_drop_down_anim)
                .setFocusable(true)
                .setOutsideTouchable(false)
                .enableBackgroundAlpha(true)
                .setOnCreateView(new CommonPopupWindow.onViewCallBack() {
                    @Override
                    public void setView(View view) {

                    }
                }).setOnDismissListener(new CommonPopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                }).create();

    }
}