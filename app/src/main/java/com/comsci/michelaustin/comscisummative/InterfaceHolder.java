package com.comsci.michelaustin.comscisummative;

/**
 * Created by Austin on 2018-01-14.
 */

public class InterfaceHolder {
    private static OpenDialog openDialog;

    public void set(OpenDialog openDialog) {
        this.openDialog = openDialog;
    }

    public OpenDialog get() {
        return openDialog;
    }
}