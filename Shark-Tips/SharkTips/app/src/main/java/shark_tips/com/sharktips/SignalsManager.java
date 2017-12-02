package shark_tips.com.sharktips;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignalsManager extends Fragment {

    private Spinner spnAction,spinnerMarket;
    private EditText txtCurrency,txtPrice,txtSellStop,txtTp1,txtTp2,txtNote;
    private ArrayAdapter<CharSequence> actionAdapter,marketAdapter;
    private Button btnSendSignal;
    private String setPrice,setSellStop,setTp1,setTp2;
    private String setNote,setCurrency,marketExecution;
    private boolean action;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals_manager, container, false);
        spnAction = (Spinner) view.findViewById(R.id.spnAction);
        actionAdapter = ArrayAdapter.createFromResource(getContext(),R.array.action,android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAction.setAdapter(actionAdapter);
        spnAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        action = true;
                        break;
                    case 2:
                        action = false;
                        break;

                    default:

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMarket = (Spinner) view.findViewById(R.id.spinnerMarket);
        marketAdapter = ArrayAdapter.createFromResource(getContext(),R.array.market,android.R.layout.simple_spinner_item);
        marketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarket.setAdapter(marketAdapter);
        spinnerMarket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        marketExecution = "Buy Limit";
                        break;
                    case 2:
                        marketExecution = "Sell Limit";
                        break;
                    case 3:
                        marketExecution = "Buy Stop";
                        break;
                    case 4:
                        marketExecution = "Sell Stop";
                        default:
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtCurrency = (EditText) view.findViewById(R.id.txtCurrency);
        txtPrice = (EditText) view.findViewById(R.id.txtPrice);
        txtSellStop = (EditText) view.findViewById(R.id.txtSellStop);
        txtTp1 = (EditText) view.findViewById(R.id.txtTp1);
        txtTp2 = (EditText) view.findViewById(R.id.txtTp2);
        txtNote = (EditText) view.findViewById(R.id.txtNote);



        btnSendSignal = (Button) view.findViewById(R.id.btnSendSignal);
        btnSendSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrency = txtCurrency.getText().toString();
                setPrice = txtPrice.getText().toString();
                setSellStop = txtSellStop.getText().toString();
                setTp1 = txtTp1.getText().toString();
                setTp2 = txtTp2.getText().toString();
                setNote = txtNote.getText().toString();

                Signal signal = new Signal();
                signal.setOpen(true);
                signal.setBuy(action);
                signal.setCurrency(setCurrency);
                signal.setPrice(setPrice);
                signal.setSellStop(setSellStop);
                signal.setNameOfSl(marketExecution);
                signal.setTp1(setTp1);
                signal.setTp2(setTp2);
                signal.setNote(setNote);

                SignalsAsyncTask signalsAsyncTask = new SignalsAsyncTask();
                signalsAsyncTask.setC(getContext());
                signalsAsyncTask.execute(signal);


                txtCurrency.setText("");
                txtPrice.setText("");
                txtSellStop.setText("");
                txtTp1.setText("");
                txtTp2.setText("");
                txtNote.setText("");

            }

        });



        return view;
    }

}

