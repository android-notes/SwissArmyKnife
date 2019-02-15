package com.wanjian.sak.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wanjian.sak.ILayer;
import com.wanjian.sak.R;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.layerview.AbsLayerView;

import java.util.ArrayList;
import java.util.List;

public class OptPanelView extends LinearLayout {
    private GridView function;
    private NumberPicker startRange;
    private NumberPicker endRange;
    private RadioGroup unitGroup;
    private CheckBox clipDraw;
    private Config config;
    private OnClickListener confirmListener;

    public OptPanelView(@NonNull Context context) {
        this(context, null);
    }

    public OptPanelView(@NonNull final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.sak_opt_panel_view, this);

        function = (GridView) findViewById(R.id.function);
        startRange = (NumberPicker) findViewById(R.id.startRange);
        endRange = (NumberPicker) findViewById(R.id.endRange);
        unitGroup = (RadioGroup) findViewById(R.id.unitGroup);
        clipDraw = (CheckBox) findViewById(R.id.clipDraw);

        findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                }
            }
        });
    }

    public void attachConfig(Config config) {
        this.config = config;
        setFunctions();
        setSizeConverter();
        setRange();
        setClipDraw();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void setRange() {
        config.setStartRange(0);
        config.setEndRange(config.getMaxRange() / 2);
        startRange.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        startRange.setMinValue(config.getMinRange());
        startRange.setMaxValue(config.getMaxRange());
        startRange.setValue(config.getStartRange());
        startRange.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                config.setStartRange(newVal);
            }
        });

        endRange.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        endRange.setMinValue(config.getMinRange());
        endRange.setMaxValue(config.getMaxRange());
        endRange.setValue(config.getEndRange());
        endRange.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                config.setEndRange(newVal);
            }
        });

    }

    private void setClipDraw() {
        clipDraw.setChecked(config.isClipDraw());
        clipDraw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                config.setClipDraw(isChecked);
            }
        });
    }

    private void setSizeConverter() {
        List<ISizeConverter> sizeConverters = config.getSizeConverters();
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final ISizeConverter converter : sizeConverters) {
            RadioButton button = (RadioButton) inflater.inflate(R.layout.sak_radiobutton, unitGroup, false);
            unitGroup.addView(button);
            button.setText(converter.desc());
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ISizeConverter.CONVERTER = converter;
                    }
                }
            });
        }
        ((RadioButton) unitGroup.getChildAt(0)).setChecked(true);
    }

    private void setFunctions() {
        List<AbsLayer> layers = config.getLayers();
        List<AbsLayerView> layerViews = config.getLayerViews();
        final List<ILayer> iLayers = new ArrayList<>(layers.size() + layerViews.size());
        iLayers.addAll(layers);
        iLayers.addAll(layerViews);

        setAdapter(iLayers);
        function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ILayer layer = iLayers.get(position);
                layer.enable(!layer.isEnable());
                //GridScroller 不复用控件，可以直接修改view
                Holder holder = (Holder) view.getTag(R.layout.sak_function_item);
                if (layer.isEnable()) {
                    holder.check.setVisibility(VISIBLE);
                } else {
                    holder.check.setVisibility(GONE);
                }
            }
        });
    }

    private void setAdapter(final List<ILayer> iLayers) {

        final LayoutInflater inflater = LayoutInflater.from(getContext());

        function.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return iLayers.size();
            }

            @Override
            public ILayer getItem(int position) {
                return iLayers.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.sak_function_item, function, false);
                    Holder holder = new Holder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.check = convertView.findViewById(R.id.check);
                    convertView.setTag(R.layout.sak_function_item, holder);
                }
                Holder holder = (Holder) convertView.getTag(R.layout.sak_function_item);
                ILayer layer = getItem(position);
                holder.title.setText(layer.description());
                holder.icon.setImageDrawable(layer.icon());
                if (layer.isEnable()) {
                    holder.check.setVisibility(VISIBLE);
                } else {
                    holder.check.setVisibility(GONE);
                }
                return convertView;
            }
        });

    }

    public void setOnConfirmClickListener(OnClickListener listener) {
        confirmListener = listener;
    }

    class Holder {
        ImageView icon;
        TextView title;
        View check;

    }
}
