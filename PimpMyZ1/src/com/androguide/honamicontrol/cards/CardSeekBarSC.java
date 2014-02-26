/**   Copyright (C) 2013  Louis Teboul (a.k.a Androguide)
 *
 *    admin@pimpmyrom.org  || louisteboul@gmail.com
 *    http://pimpmyrom.org || http://androguide.fr
 *    71 quai Clémenceau, 69300 Caluire-et-Cuire, FRANCE.
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License along
 *      with this program; if not, write to the Free Software Foundation, Inc.,
 *      51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 **/

package com.androguide.honamicontrol.cards;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;
import com.androguide.honamicontrol.R;

public class CardSeekBarSC extends Card {

    public CardSeekBarSC(String title, String desc, String color, String unit, String prop, int seekBarMax, int seekBarProgress, ActionBarActivity fa, ActionMode.Callback callback) {
        super(title, desc, color, unit, prop, seekBarMax, seekBarProgress, fa, callback);
    }

    @Override
    public View getCardContent(Context context) {
        final View v = LayoutInflater.from(context).inflate(R.layout.card_seekbar_sc, null);

        assert v != null;
        final TextView value = (TextView) v.findViewById(R.id.unit);
        final SeekBar seekBar = (SeekBar) v.findViewById(R.id.seek);
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color.parseColor(color));
        ((TextView) v.findViewById(R.id.desc)).setText(desc);

        seekBar.setMax(seekBarMax);
        seekBar.setProgress(seekBarProgress);
        value.setText(seekBarProgress - 30 + unit);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarProgress = i;
                value.setText(i - 30 + unit);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                fa.startSupportActionMode(callback);
                SharedPreferences p = fa.getSharedPreferences(prop.replaceAll("/", ""), 0);
                int scProgress = seekBar.getProgress() - 30;
                if (scProgress < 0)
                    scProgress += 256;

                p.edit().putInt("VALUE", scProgress).commit();

            }
        });

        return v;
    }

    public int getCardContentId() {
        return R.layout.card_seekbar_sc;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }
}
