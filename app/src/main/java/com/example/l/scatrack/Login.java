package com.example.l.scatrack;

import java.io.Serializable;

/**
 * Created by l on 12/13/2017.
 */
public class Login implements Serializable {
    private String p_email;
    public String getP_email() {
        return p_email;
    }

    public void setP_email(String name) {
        this.p_email = p_email;
    }
}
