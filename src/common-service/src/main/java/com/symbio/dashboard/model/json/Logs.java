package com.symbio.dashboard.model.json;

public class Logs {
    private String source;
    private String image;
    private String message;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Logs [source=" + source + ", image=" + image + ", message=" + message + "]";
    }
}
