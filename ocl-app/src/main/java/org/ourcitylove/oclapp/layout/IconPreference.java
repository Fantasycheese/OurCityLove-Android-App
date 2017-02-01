package org.ourcitylove.oclapp.layout;

/**
 * Created by Vegetable on 2016/12/30.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.ourcitylove.oclapp.R;

/**
 * 左側にアイコンを表示するPreference.
 *
 * @author sora (shinpei.okamura@insprout.com)
 */
public class IconPreference extends Preference {
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;

    private Drawable icon = null;
    private Integer place = null;

    /**
     * iconプロパティからリソースを読み込む.
     * <p>
     * {@inheritDoc}
     *
     * @see Preference#IconPreference(Context, AttributeSet, int)
     */
    public IconPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.icon_preference);

        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.IconPreference, defStyle, 0);
        icon = ta.getDrawable(R.styleable.IconPreference_icon);
        place = ta.getInt(R.styleable.IconPreference_place, LEFT);
    }

    /**
     * {@inheritDoc}
     *
     * @see Preference#IconPreference(Context, AttributeSet)
     */
    public IconPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * アイコンのImageViewを設定する.
     * <p>
     * {@inheritDoc}
     *
     * @see Preference#onBindView(View)
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onBindView(View view) {
        super.onBindView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        if (imageView != null) {
            if (icon != null) {
                imageView.setImageDrawable(icon);
            } else {
                imageView.setVisibility(View.GONE);
            }
            LinearLayout rootLayout = (LinearLayout) imageView.getParent();
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            float ratio = (float) width / (float) icon.getIntrinsicWidth();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imageView.getLayoutParams();
            params.height = (int)(ratio * icon.getIntrinsicHeight());
            switch (place) {
                case LEFT:
                    rootLayout.setOrientation(LinearLayout.HORIZONTAL);
                    rootLayout.removeView(imageView);
                    params.width = -1;
                    rootLayout.addView(imageView, 0, params);
                    break;
                case TOP:
                    rootLayout.setOrientation(LinearLayout.VERTICAL);
                    rootLayout.removeView(imageView);
                    rootLayout.addView(imageView, 0, params);
                    break;
                case RIGHT:
                    rootLayout.setOrientation(LinearLayout.HORIZONTAL);
                    rootLayout.removeView(imageView);
                    params.width = -1;
                    rootLayout.addView(imageView, rootLayout.getChildCount() - 2, params);
                    break;
                case BOTTOM:
                    rootLayout.setOrientation(LinearLayout.VERTICAL);
                    rootLayout.removeView(imageView);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    rootLayout.addView(imageView, rootLayout.getChildCount() - 2, params);
                    break;
            }
        }
    }

    /**
     * アイコンを設定.
     *
     * @param icon
     */
    public void setIcon(Drawable icon) {
        if (this.icon == null && icon != null
                || icon != null && !icon.equals(this.icon)) {
            this.icon = icon;
            notifyChanged();
        }
    }
}
