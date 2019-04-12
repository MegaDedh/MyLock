package ru.asshands.softwire.simplephonebook;

public final class PhonePersone {
    private final String name;
    private final String surName;
    private final String telNum;

    public PhonePersone(String name, String surName, String telNum) {
        this.name = name;
        this.surName = surName;
        this.telNum = telNum;
    }

    public final String getName() {
        return name;
    }
    public final String getSurName() {
        return surName;
    }
    public final String getTelNum() {
        return telNum;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (!(obj instanceof PhonePersone))
            return false;
        PhonePersone testedPhonePersone = (PhonePersone) obj;
        return telNum!=null && testedPhonePersone.telNum.equals(telNum);
    }

    @Override
    public int hashCode() {
        int result = (telNum == null ? 0 : telNum.hashCode());
        return result;
    }
}