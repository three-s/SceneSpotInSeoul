package com.threes.scenespotinseoul.ui.map;

import android.content.Context;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

public class Hashtag extends ClickableSpan {
    public interface ClickEventListener{
        void onClickEvent(String data);
    }
    private ClickEventListener mClickEventListener = null;
    private Context context;
    private TextPaint textPaint;
    public Hashtag(Context ctx){ super(); context = ctx; }
    public void setOnClickEventListener(ClickEventListener listener){
        mClickEventListener = listener;
    }
    @Override public void updateDrawState(TextPaint ds) {
        textPaint = ds; ds.setColor(ds.linkColor);
        ds.setARGB(255, 30, 144, 255);
    }
    @Override public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String theWord = s.subSequence(start + 1, end).toString();
        mClickEventListener.onClickEvent(theWord);
    }
}

