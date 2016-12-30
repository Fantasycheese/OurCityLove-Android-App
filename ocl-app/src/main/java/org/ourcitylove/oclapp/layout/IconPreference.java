package org.ourcitylove.oclapp.layout;

/**
 * Created by Vegetable on 2016/12/30.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.ourcitylove.oclapp.R;

/**
 * 左側にアイコンを表示するPreference.
 *
 * @author sora (shinpei.okamura@insprout.com)
 */
public class IconPreference extends Preference {

    private Drawable icon = null;

    /**
     * iconプロパティからリソースを読み込む.
     *
     * {@inheritDoc}
     * @see Preference#IconPreference(Context,AttributeSet,int)
     */
    public IconPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.icon_preference);

        TypedArray ta = context.obtainStyledAttributes(
                attrs, R.styleable.IconPreference, defStyle, 0);
        icon = ta.getDrawable(R.styleable.IconPreference_icon);
    }

    /**
     * {@inheritDoc}
     * @see Preference#IconPreference(Context,AttributeSet)
     */
    public IconPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * アイコンのImageViewを設定する.
     *
     * {@inheritDoc}
     * @see Preference#onBindView(View)
     */
    protected void onBindView(View view) {
        super.onBindView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        if (imageView != null) {
            if (icon != null) {
                imageView.setImageDrawable(icon);
            } else {
                imageView.setVisibility(View.GONE);
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
