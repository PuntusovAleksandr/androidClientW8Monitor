package  com.lucertech.w8monitor.android.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lucertech.w8monitor.android.R;


/**
 * Created by AleksandrP on 06.10.2016.
 */

public class DeleteAlarmDialog extends AlertDialog implements View.OnClickListener {

    private DeleteOk mOk;
    private RelativeLayout mItem_;

    public DeleteAlarmDialog(RelativeLayout mItem_, Context mContext, DeleteOk mOk) {
        super(mContext);
        this.mItem_ = mItem_;
        this.mOk = mOk;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.context_delete_dialog, null);

        initUi(view);
    }


    private void initUi(View mView) {
        mView.findViewById(R.id.bt_ok_delete).setOnClickListener(this);
        mView.findViewById(R.id.bt_cancel_delete).setOnClickListener(this);
        this.setView(mView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok_delete:
                mOk.deleteOk(true, mItem_);
                dismiss();
                break;
            case R.id.bt_cancel_delete:
                mOk.deleteOk(false, mItem_);
                dismiss();
                break;

        }
    }

    public interface DeleteOk {
        void deleteOk(boolean mItem, RelativeLayout mItem_);
    }

}
