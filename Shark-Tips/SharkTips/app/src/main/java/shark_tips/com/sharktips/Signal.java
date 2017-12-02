package shark_tips.com.sharktips;

import android.support.annotation.NonNull;

/**
 * Created by liranelyadumi on 4/13/17.
 */

public class Signal implements Comparable<Signal> {


    public boolean isExpanded;

    // Signal

    String currency,nameOfSl;
    boolean isBuy;
    String price;
    String sellStop;
    String tp1;
    String tp2;
    String note;
    boolean isOpen;
    Integer id;
    int time;

    long ts;
    String huersDays;


    public Signal() {
    }

    public Signal(boolean isOpen, int time,int id, String currency, boolean isBuy, String price, String sellStop, String tp1, String tp2, String note, String nameOfSl) {
        this.isOpen = isOpen;
        this.time = time;
        this.id = id;
        this.currency = currency;
        this.isBuy = isBuy;
        this.price = price;
        this.sellStop = sellStop;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
        this.nameOfSl = nameOfSl;
    }

    public long getTs() {
        return ts;
    }

    public String getHuersDays() {
        return huersDays;
    }

    public void setHuersDays(String huersDays) {
        this.huersDays = huersDays;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellStop() {
        return sellStop;
    }

    public void setSellStop(String sellStop) {
        this.sellStop = sellStop;
    }

    public String getTp1() {
        return tp1;
    }

    public void setTp1(String tp1) {
        this.tp1 = tp1;
    }

    public String getTp2() {
        return tp2;
    }

    public void setTp2(String tp2) {
        this.tp2 = tp2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameOfSl() {
        return nameOfSl;
    }

    public void setNameOfSl(String nameOfSl) {
        this.nameOfSl = nameOfSl;
    }


    @Override
    public int compareTo(@NonNull Signal o) {
        if (this.ts == o.getTs())
            return 0;
        else if (this.ts < o.getTs())
            return 1;
        else
            return -1;
    }
}
