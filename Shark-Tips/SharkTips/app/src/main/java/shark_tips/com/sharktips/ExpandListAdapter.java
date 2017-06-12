package shark_tips.com.sharktips;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
        LinearLayout table;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Row row;
        if (convertView == null){
            row = new Row();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_signal,parent,false);
            row.table = (LinearLayout) convertView.findViewById(R.id.table);
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
            row.lblStatus.setTextColor(Color.parseColor("#48AD34"));
            row.lblStatus.setText("active");
        }else {
            row.lblStatus.setTextColor(Color.parseColor("#D60300"));
            row.lblStatus.setText("close");
          //  row.lblStatus.setPaintFlags(row.lblStatus.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        //check the action value and paint the color - green = buy / red = sell
        if (item.isBuy() == true){
            row.lblAction.setTextColor(Color.parseColor("#48AD34"));
            row.lblAction.setText("buy");
        }else {
            row.lblAction.setTextColor(Color.parseColor("#D60300"));
            row.lblAction.setText("sell");
        }



        row.lblTime.setText(String.valueOf(item.getTime()) + " " + item.getHuersDays() + " ago");
        row.lblCurrency.setText(item.getCurrency());
        row.lblPrice.setText(String.valueOf(item.getPrice()));
        row.lblSellStop.setText(convertValues(item.getSellStop()));
        row.lblsl.setText(item.getNameOfSl() +" "+convertValues(item.getSl()));
        row.lbltp1.setText(convertValues(item.getTp1()));
        row.lbltp2.setText(convertValues(item.getTp2()));
        row.lblNote.setText(item.getNote());



        return convertView;
    }

    private String convertValues(double value){
        if (value == -1.0){
            return "none";
        }
        return String.valueOf(value);
    }
}
