package shark_tips.com.sharktips;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liranelyadumi on 4/13/17.
 */

public class ExpandListAdapter extends BaseAdapter {

    Context context;
    List<Signal> signals;


    public ExpandListAdapter(Context context, List<Signal> signals) {
        this.context = context;
        this.signals = signals;
    }

    @Override
    public int getCount() {
        return signals.size();
    }

    @Override
    public Object getItem(int position) {
        return signals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Row {
        TextView lblStatus,lblTime,lblCurrency,lblAction,lblPrice,lblSellStop,lblsl,lbltp1,lbltp2,lblNote;
        TableLayout table;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Row row;
        if (convertView == null){
            row = new Row();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_signal,parent,false);
            row.table = (TableLayout) convertView.findViewById(R.id.table);
            row.lblStatus = (TextView) convertView.findViewById(R.id.lblStatus);
            row.lblTime = (TextView) convertView.findViewById(R.id.lblTime);
            row.lblCurrency = (TextView) convertView.findViewById(R.id.lblCurrency);
            row.lblAction = (TextView) convertView.findViewById(R.id.lblAction);
            row.lblPrice = (TextView) convertView.findViewById(R.id.lblPrice);
            row.lblSellStop = (TextView) convertView.findViewById(R.id.lblSellStop);
            row.lblsl = (TextView) convertView.findViewById(R.id.lblsl);
            row.lbltp1 = (TextView) convertView.findViewById(R.id.lbltp1);
            row.lbltp2 = (TextView) convertView.findViewById(R.id.lbltp2);
            row.lblNote = (TextView) convertView.findViewById(R.id.lblNote);

            convertView.setTag(row);
        }else {
            row = (Row) convertView.getTag();
        }

        // Update the view.
        Signal item = signals.get(position);
        if (item.isExpanded){
            row.table.setVisibility(View.VISIBLE);
        }else {
            row.table.setVisibility(View.GONE);
        }

        //check the status value and paint the color - green = open / red = close
        if (item.isOpen() == true){
            row.lblStatus.setTextColor(Color.argb(255,67,206,01));
            row.lblStatus.setText("open");
        }else {
            row.lblStatus.setTextColor(Color.argb(255,450,11,15));
            row.lblStatus.setText("close");
        }

        //check the action value and paint the color - green = buy / red = sell
        if (item.isBuy() == true){
            row.lblAction.setTextColor(Color.argb(255,67,206,01));
            row.lblAction.setText("buy");
        }else {
            row.lblAction.setTextColor(Color.argb(255,450,11,15));
            row.lblAction.setText("sell");
        }



        row.lblTime.setText(String.valueOf(item.getTime()));
        row.lblCurrency.setText(item.getCurrency());
        row.lblPrice.setText(String.valueOf(item.getPrice()));
        row.lblSellStop.setText("SellStop "+item.getSellStop());
        row.lblsl.setText("Sl "+item.getSl());
        row.lbltp1.setText("Tp1 "+item.getTp1());
        row.lbltp2.setText("Tp2 "+item.getTp2());
        row.lblNote.setText(item.getNote());



        return convertView;
    }
}
