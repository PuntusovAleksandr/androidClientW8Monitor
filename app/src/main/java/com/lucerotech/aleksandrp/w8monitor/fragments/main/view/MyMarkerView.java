
package com.lucerotech.aleksandrp.w8monitor.fragments.main.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.lucerotech.aleksandrp.w8monitor.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRobotoLight;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    private DecimalFormat format = new DecimalFormat("##0");

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tv_marker);
        tvContent.setTypeface(getFontRobotoLight());
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String nameLabes = "";
        if (e.getData() != null) {
            ArrayList<Object> data = (ArrayList<Object>) e.getData();
            String text = (String) data.get(2);
            nameLabes = text.replaceAll(" ", "");
        }
        tvContent.setText(format.format(e.getY()) + nameLabes);

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 10);
    }
}
