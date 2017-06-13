package com.moggot.commonalarmclock.mvp.settings;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

public interface SettingsPresenter {

    void initialize(long id);

    void onClickSave(View view);

    void timerPickerOnClick();

    void onClickDay(View v, int dayCode);

    void onCheckedChangedCheckBox(CompoundButton buttonView, boolean isChecked);

    void onCheckedChangedRadioGroup(RadioGroup radioGroup, int checkedID);

    void onClickButtonMusic(View view, int radioButtonID);

    void afterTextChanged(Editable s);

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}