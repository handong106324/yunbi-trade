package org.bitcoin.market.bean;

public class SymbolPair {
    final private Symbol first;
    final private Symbol second;

    public SymbolPair(Symbol first, Symbol second) {
        this.first = first;
        this.second = second;
    }

    public Symbol getSecond() {
        return second;
    }


    public Symbol getFirst() {
        return first;
    }

    public String getDesc(boolean haveUnderline) {
        if (haveUnderline) {
            return first.name() + "_" + second.name();
        } else {
            return first.name() + second.name();

        }
    }
}
