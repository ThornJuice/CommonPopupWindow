package com.wxj.library_androidx;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.FloatRange;

/**
 * @author: wxj
 * @date: 2020/10/21
 * @description:
 */
public final class CommonPopupWindow {

    private static final String TAG = "CommonPopupWindow";

    private Context mContext;
    private int mWidth = -2;//默认wrapContent
    private int mHeight = -2;//默认wrapContent
    private View mView;
    private PopupWindow mPopupWindow;
    private OnDismissListener mOnDismissListener;
    private boolean mEnableBackGroundAlpha;//是否背景变暗
    private float mBgAlpha = 0.7f;
    private Window mWindow;
    private int mAnimationStyle = -1;
    private boolean mTouchable = true;//默认PopupWindow子view可点击
    private boolean mFocusable = true;//默认PopupWindow抢夺焦点，外部点击事件无效,只会执行dismiss
    private boolean mOutsideTouchable = false;//默认外部不获取点击事件,为true的时候，需同时将mFocusable设置为false

    public interface OnDismissListener {
        void onDismiss();
    }

    private CommonPopupWindow() {

    }

    private CommonPopupWindow(Context context) {
        this.mContext = context;
    }

    public void init() {

        if (mView == null) return;

        mPopupWindow = new PopupWindow();
        mPopupWindow.setContentView(mView);
        mPopupWindow.setWidth(mWidth);
        mPopupWindow.setHeight(mHeight);
        mPopupWindow.setTouchable(mTouchable);
        mPopupWindow.setFocusable(mFocusable);
        mPopupWindow.setOutsideTouchable(mOutsideTouchable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());


        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        mPopupWindow.setOnDismissListener(() -> {

            if (mWindow != null) {
                setBackgroundAlpha(1f);
            }

            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }


        });
    }

    public static class Builder {

        private CommonPopupWindow commonPopupWindow;

        public Builder(Context context) {
            commonPopupWindow = new CommonPopupWindow(context);
        }

        public CommonPopupWindow create() {

            return commonPopupWindow.create();
        }

        /**
         * 设置ContentView 传入View,需要inflate
         *
         * @param view
         */
        public Builder setContentView(View view) {
            if (commonPopupWindow != null) {
                commonPopupWindow.setContentView(view);
            }
            return this;
        }

        /**
         * 设置ContentView 传入layoutId即可,无需inflate
         *
         * @param layoutId
         */
        public Builder setContentView(int layoutId) {
            if (commonPopupWindow != null) {
                commonPopupWindow.setContentView(layoutId);
            }
            return this;
        }

        /**
         * 设置宽度
         *
         * @param width
         */
        public Builder setWidth(int width) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mWidth = width;
            }
            return this;
        }

        /**
         * 设置高度
         *
         * @param height
         */
        public Builder setHeight(int height) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mHeight = height;
            }
            return this;
        }

        /**
         * 设置view属性及点击事件
         *
         * @param callBack
         */
        public Builder setOnCreateView(onViewCallBack callBack) {
            if (commonPopupWindow != null) {
                commonPopupWindow.setViewListener(callBack);
            }
            return this;
        }

        /**
         * 设置动画
         *
         * @param animationStyle
         */
        public Builder setAnimationStyle(int animationStyle) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mAnimationStyle = animationStyle;
            }
            return this;
        }

        /**
         * 设置背景透明度
         *
         * @param enable
         */
        public Builder enableBackgroundAlpha(boolean enable) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mEnableBackGroundAlpha = enable;

            }
            return this;
        }

        /**
         * 设置背景透明度
         *
         * @param enable
         * @param bgAlpha
         */
        public Builder enableBackgroundAlpha(boolean enable, @FloatRange(from = 0.0, to = 1.0) float bgAlpha) {

            if (commonPopupWindow != null) {
                commonPopupWindow.mEnableBackGroundAlpha = enable;
                commonPopupWindow.mBgAlpha = bgAlpha;
            }
            return this;
        }

        /**
         * 设置外部点击事件是否生效
         *
         * @param outsideTouchable
         */
        public Builder setOutsideTouchable(boolean outsideTouchable) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mOutsideTouchable = outsideTouchable;
            }
            return this;
        }

        /**
         * 设置popupwindow里面的控件是否可点击,默认可点击
         *
         * @param touchable
         */
        public Builder setTouchable(boolean touchable) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mTouchable = touchable;
            }
            return this;
        }

        /**
         * 设置点击外部是否消失 true消失   false不消失
         *
         * @param focusable
         */
        public Builder setFocusable(boolean focusable) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mFocusable = focusable;
            }
            return this;
        }

        /**
         * 设置PopupWindow dismiss回调
         *
         * @param onDismissListener
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            if (commonPopupWindow != null) {
                commonPopupWindow.mOnDismissListener = onDismissListener;
            }
            return this;
        }

    }

    private void setContentView(int layoutId) {
        if (layoutId != -1) {
            mView = View.inflate(mContext, layoutId, null);
        }
    }

    private void setContentView(View view) {
        mView = view;
    }

    private CommonPopupWindow create() {
        init();
        return this;
    }


    public void setViewListener(onViewCallBack callBack) {
        callBack.setView(mView);
    }

    public interface onViewCallBack {
        void setView(View view);
    }


    /**
     * 设置PopupWindow 所在activity的alpha
     */
    private void setBackgroundAlpha(float alpha) {
        if (mContext == null) return;
        if (mContext instanceof Activity) {
            mWindow = ((Activity) mContext).getWindow();
            if (mWindow != null) {
                WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
                layoutParams.alpha = alpha;
                mWindow.setAttributes(layoutParams);
            }
        }

    }


    /**
     * 下拉弹出showAsDropDown
     *
     * @param anchor
     * @param xoff
     * @param yoff
     */
    public CommonPopupWindow showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            if (mEnableBackGroundAlpha) {
                setBackgroundAlpha(mBgAlpha);
            }
        }
        return this;
    }

    /**
     * showAtLocation
     *
     * @param parent
     * @param xoff
     * @param yoff
     */
    public CommonPopupWindow showAtLocation(View parent, int gravity, int xoff, int yoff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, xoff, yoff);
            if (mEnableBackGroundAlpha) {
                setBackgroundAlpha(mBgAlpha);
            }
        }
        return this;
    }


    /**
     * 底部弹出PopupWindow
     *
     * @param parent
     * @param xoff
     * @param yoff
     */
    public CommonPopupWindow showAtBottom(View parent, int xoff, int yoff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, Gravity.BOTTOM, xoff, yoff);
            if (mEnableBackGroundAlpha) {
                setBackgroundAlpha(mBgAlpha);
            }
        }
        return this;
    }
}
