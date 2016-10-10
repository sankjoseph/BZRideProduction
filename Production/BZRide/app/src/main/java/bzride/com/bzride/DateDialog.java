package bzride.com.bzride;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Santhosh.Joseph on 07-07-2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText txtDate;
    public DateDialog()
    {

    }
    @SuppressLint("ValidFragment")
    public DateDialog(View view){
        txtDate = (EditText) view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceSate){
        final Calendar c= Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
    public void onDateSet (DatePicker view, int year, int month, int day){
        String date = (month+1)  + "/" + day + "/" + year;
        txtDate.setText(date);
    }
}
