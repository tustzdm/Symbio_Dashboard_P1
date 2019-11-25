package com.symbio.dashboard.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Progress implements Serializable {
    private int total;
    private int done;
    private String progress;

    public Progress(int done, int total) {
        this.done = done;
        this.total = total;
        if (total > 0) {
            if (done == total) {
                this.progress = "100%";
            } else {
                this.progress = String.format("%d%%", Integer.valueOf(done * 100 / total));
            }
        } else {
            this.progress = "";
        }
    }
}
