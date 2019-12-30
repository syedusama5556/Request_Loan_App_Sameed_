package com.infusiblecoder.loanappsameed.ModelClasses;

public class DataPart {
    String fileName;
    byte[] content;
    String type;

    public DataPart() {
    }

    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

}