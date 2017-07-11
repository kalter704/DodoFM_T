package ru.dodofm_test.dodofm_t.listners;

public interface OnRadioListener {
    void onRadioStarted();

    void onRadioPaused();

    void onRadioStopped();

    void onRadioLoading();

    void onRadioMetadata(String s, String s2);

    void onRadioError();
}
