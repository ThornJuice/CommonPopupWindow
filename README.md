# CommonPopupWindow
一个简洁通用的PopupWindow
使用方法

```
 CommonPopupWindow bottomPopupWindow = new CommonPopupWindow.Builder(MainActivity.this)
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
                })
                .create()
                .showAtBottom(findViewById(R.id.ll_root), 0, 0);
```
