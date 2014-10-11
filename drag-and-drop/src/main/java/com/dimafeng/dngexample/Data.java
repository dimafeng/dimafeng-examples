package com.dimafeng.dngexample;

import java.util.Random;

class Data {
    private boolean booleanData;
    private int intData;

    public static Data createRandomData() {
        Data data = new Data();
        Random random = new Random();
        data.setBooleanData(random.nextBoolean());
        data.setIntData(random.nextInt());

        return data;
    }

    public boolean isBooleanData() {
        return booleanData;
    }

    public void setBooleanData(boolean booleanData) {
        this.booleanData = booleanData;
    }

    public int getIntData() {
        return intData;
    }

    public void setIntData(int intData) {
        this.intData = intData;
    }
}
