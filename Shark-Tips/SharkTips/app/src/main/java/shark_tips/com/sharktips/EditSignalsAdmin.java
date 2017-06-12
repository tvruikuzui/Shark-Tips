package shark_tips.com.sharktips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by liranelyadumi on 5/12/17.
 */

public class EditSignalsAdmin extends DialogFragment {

    private Signal signal;
    private EditText txtEditSignalCurrency
            ,txtEditSignalPrice,txtEditSignalSellStop,txtEditSignalTp1
            ,txtEditSignalTp2,txtEditSignalSl,txtEditSignalNote;
    private Button btnUpdateSignal;
    private UpdateSignalAlertListener listener;
    private CheckBox chkOpen,chkBuy;



    public void setListener(UpdateSignalAlertListener listener) {
        this.listener = listener;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_signal_panel,container,false);

        chkOpen = (CheckBox) view.findViewById(R.id.chkOpen);
        chkBuy = (CheckBox) view.findViewById(R.id.chkBuy);
        txtEditSignalCurrency = (EditText) view.findViewById(R.id.txtEditSignalCurrency);
        txtEditSignalPrice = (EditText) view.findViewById(R.id.txtEditSignalPrice);
        txtEditSignalSellStop = (EditText) view.findViewById(R.id.txtEditSignalSellStop);
        txtEditSignalTp1 = (EditText) view.findViewById(R.id.txtEditSignalTp1);
        txtEditSignalTp2 = (EditText) view.findViewById(R.id.txtEditSignalTp2);
        txtEditSignalSl = (EditText) view.findViewById(R.id.txtEditSignalSl);
        txtEditSignalNote = (EditText) view.findViewById(R.id.txtEditSignalNote);

        chkOpen.setChecked(signal.isOpen());
        chkBuy.setChecked(signal.isBuy());
        txtEditSignalCurrency.setText(signal.getCurrency());
        txtEditSignalPrice.setText(String.valueOf(signal.getPrice()));
        txtEditSignalSellStop.setText(String.valueOf(signal.getSellStop()));
        txtEditSignalTp1.setText(String.valueOf(signal.getTp1()));
        txtEditSignalTp2.setText(String.valueOf(signal.getTp2()));
        txtEditSignalSl.setText(String.valueOf(signal.getSl()));
        txtEditSignalNote.setText(signal.getNote());

        btnUpdateSignal = (Button) view.findViewById(R.id.btnUpdateSignal);
        btnUpdateSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSignal(signal);
                SignalsAsyncTask signalsAsyncTask = new SignalsAsyncTask();
                signalsAsyncTask.setC(getContext());
                signalsAsyncTask.execute(signal);
                if (listener != null){
                    listener.signalUpdate();
                }
                dismiss();
            }
        });
        return view;
    }

    private void updateSignal(Signal signal) {

        signal.setNote(txtEditSignalNote.getText().toString());
        signal.setTp1(convertValues(txtEditSignalTp1.getText().toString()));
        signal.setCurrency(txtEditSignalCurrency.getText().toString());
        signal.setBuy(chkBuy.isChecked());
        signal.setOpen(chkOpen.isChecked());
        signal.setPrice(convertValues(txtEditSignalPrice.getText().toString()));
        signal.setSellStop(convertValues(txtEditSignalSellStop.getText().toString()));
        signal.setTp1(convertValues(txtEditSignalTp1.getText().toString()));
        signal.setSl(convertValues(txtEditSignalSl.getText().toString()));


    }

    public interface UpdateSignalAlertListener{
        void signalUpdate();
    }

    private double convertValues(String value){
        if (value != null && value.isEmpty()){
            return -1.0;
        }
        return Double.parseDouble(value);
    }
}
