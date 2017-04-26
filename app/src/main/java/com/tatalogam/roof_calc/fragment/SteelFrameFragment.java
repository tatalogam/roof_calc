package com.tatalogam.roof_calc.fragment;

import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.tatalogam.roof_calc.R;
import com.tatalogam.roof_calc.Constant;
import com.tatalogam.roof_calc.activity.OrderActivity;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_steelframe)
public class SteelFrameFragment extends Fragment{
    private View rootView;
    public NestedScrollView root;

    //declare elements
    private LinearLayout container_gable;
    private LinearLayout container_hip;
    private LinearLayout container_shed;
    private LinearLayout container_dutch;
    private LinearLayout anakan_wrapper;

    private ImageView rooftype_gable;
    private ImageView rooftype_hip;
    private ImageView rooftype_shed;
    private ImageView rooftype_dutch;
    private ImageView iv_angle;
    private ImageView btn_add_anakan;

    private TextView selectorAnakan1ValueLength;
    private TextView selectorAnakan2ValueLength;
    private TextView selectorAnakan3ValueLength;

    private TextView selectorAnakan1ValueWidth;
    private TextView selectorAnakan2ValueWidth;
    private TextView selectorAnakan3ValueWidth;

    private EditText length;
    private EditText width;

    private SeekBar seekbar_degree;
    private TextView degree;

    private OrderActivity orderActivity;
    private Integer oldRooftype = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //check if rootView is not removed yet and remove it
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_steelframe, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        //can only set from here, because annotation doesn't work
        root=(NestedScrollView)rootView.findViewById(R.id.root);

        container_gable=(LinearLayout)rootView.findViewById(R.id.container_gable);
        container_hip=(LinearLayout)rootView.findViewById(R.id.container_hip);
        container_shed=(LinearLayout)rootView.findViewById(R.id.container_shed);
        container_dutch=(LinearLayout)rootView.findViewById(R.id.container_dutch);
        anakan_wrapper=(LinearLayout)rootView.findViewById(R.id.anakan_wrapper);

        rooftype_gable=(ImageView)rootView.findViewById(R.id.rooftype_gable);
        rooftype_hip=(ImageView)rootView.findViewById(R.id.rooftype_hip);
        rooftype_shed=(ImageView)rootView.findViewById(R.id.rooftype_shed);
        rooftype_dutch=(ImageView)rootView.findViewById(R.id.rooftype_dutch);
        iv_angle=(ImageView)rootView.findViewById(R.id.iv_angle);
        btn_add_anakan=(ImageView)rootView.findViewById(R.id.btn_add_anakan);

        length=(EditText)rootView.findViewById(R.id.length);
        width=(EditText)rootView.findViewById(R.id.width);
        seekbar_degree=(SeekBar)rootView.findViewById(R.id.seekbar_degree);
        degree=(TextView)rootView.findViewById(R.id.degree);

        //get parent activity
        orderActivity = (OrderActivity)getActivity();

        //set event listener
        rooftype_gable.setOnClickListener(new ImageView.OnClickListener() {
          public void onClick(View v) {
              resetContainerBackground();
              oldRooftype = 1;

              orderActivity.cb.setRoofType(1);
              orderActivity.cb.setBuffer(Constant.buffer[0]);
              container_gable.setBackground(getResources().getDrawable(R.drawable.shape_card_done));
          }
        });

        rooftype_hip.setOnClickListener(new ImageView.OnClickListener() {
          public void onClick(View v) {
              resetContainerBackground();
              oldRooftype = 2;

              orderActivity.cb.setRoofType(2);
              orderActivity.cb.setBuffer(Constant.buffer[1]);
              container_hip.setBackground(getResources().getDrawable(R.drawable.shape_card_done));
          }
        });

        rooftype_shed.setOnClickListener(new ImageView.OnClickListener() {
          public void onClick(View v) {
              resetContainerBackground();
              oldRooftype = 3;

              orderActivity.cb.setRoofType(3);
              orderActivity.cb.setBuffer(Constant.buffer[0]);
              container_shed.setBackground(getResources().getDrawable(R.drawable.shape_card_done));
          }
        });

        rooftype_dutch.setOnClickListener(new ImageView.OnClickListener() {
          public void onClick(View v) {
              resetContainerBackground();
              oldRooftype = 4;

              orderActivity.cb.setRoofType(4);
              orderActivity.cb.setBuffer(Constant.buffer[1]);
              container_dutch.setBackground(getResources().getDrawable(R.drawable.shape_card_done));
          }
        });

        length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(length.getText().toString().matches("")) orderActivity.cb.setLength(1);
                    else{
                        int num = Math.abs(Integer.parseInt(length.getText().toString()));
                        orderActivity.cb.setLength(num);
                    }
                } catch (NumberFormatException e) {
                    //Log.i("",text+" is not a number");
                }
            }
        });

        width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if(width.getText().toString().matches("")) orderActivity.cb.setWidth(1);
                    else{
                        int num = Math.abs(Integer.parseInt(width.getText().toString()));
                        orderActivity.cb.setWidth(num);
                    }
                } catch (NumberFormatException e) {
                    //Log.i("",text+" is not a number");
                }
            }
        });

        seekbar_degree.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int totalDeg = 15+progress;
                degree.setText(String.valueOf(totalDeg));
                orderActivity.cb.setDegree(totalDeg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn_add_anakan.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                //add porch
                if (orderActivity.cb.getJumlahAnakan() == 3) {
                    return;
                }

                int tmpx = orderActivity.cb.getJumlahAnakan();

                if (orderActivity.cb.getJumlahAnakan() < 3) {
                    tmpx++;
                }

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.card_anakan_size_selector, anakan_wrapper, false);
                TextView porch_no = (TextView)view.findViewById(R.id.porch_no);
                ImageView btn_delete_anakan = (ImageView)view.findViewById(R.id.btn_delete_anakan);

                if (tmpx == 1) {
                    selectorAnakan1ValueWidth = (TextView)view.findViewById(R.id.selector_value_width);
                    selectorAnakan1ValueLength = (TextView)view.findViewById(R.id.selector_value_length);
                    ImageView btn_dec_width1 = (ImageView) view.findViewById(R.id.btn_dec_width);
                    ImageView btn_add_width1 = (ImageView) view.findViewById(R.id.btn_add_width);
                    ImageView btn_dec_length1 = (ImageView) view.findViewById(R.id.btn_dec_length);
                    ImageView btn_add_length1 = (ImageView) view.findViewById(R.id.btn_add_length);

                    porch_no.setText(getResources().getString(R.string.header_1stporch));
                    btn_delete_anakan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAnakan(1);
                        }
                    });
                    btn_dec_width1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 1);
                            val2();
                        }
                    });
                    btn_dec_width1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 1);
                            val2();
                            return false;
                        }
                    });

                    btn_add_width1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 1);
                            val2();
                        }
                    });
                    btn_add_width1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 1);
                            val2();
                            return false;
                        }
                    });

                    btn_dec_length1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 1);
                            val2();
                        }
                    });
                    btn_dec_length1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 1);
                            val2();
                            return false;
                        }
                    });

                    btn_add_length1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 1);
                            val2();
                        }
                    });
                    btn_add_length1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 1);
                            val2();
                            return false;
                        }
                    });
                }
                else if (tmpx == 2) {
                    selectorAnakan2ValueWidth = (TextView)view.findViewById(R.id.selector_value_width);
                    selectorAnakan2ValueLength = (TextView)view.findViewById(R.id.selector_value_length);
                    ImageView btn_dec_width2 = (ImageView) view.findViewById(R.id.btn_dec_width);
                    ImageView btn_add_width2 = (ImageView) view.findViewById(R.id.btn_add_width);
                    ImageView btn_dec_length2 = (ImageView) view.findViewById(R.id.btn_dec_length);
                    ImageView btn_add_length2 = (ImageView) view.findViewById(R.id.btn_add_length);

                    porch_no.setText(getResources().getString(R.string.header_2ndporch));
                    btn_delete_anakan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAnakan(2);
                        }
                    });
                    btn_dec_width2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 2);
                            val2();
                        }
                    });
                    btn_dec_width2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 2);
                            val2();
                            return false;
                        }
                    });

                    btn_add_width2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 2);
                            val2();
                        }
                    });
                    btn_add_width2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 2);
                            val2();
                            return false;
                        }
                    });

                    btn_dec_length2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 2);
                            val2();
                        }
                    });
                    btn_dec_length2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 2);
                            val2();
                            return false;
                        }
                    });

                    btn_add_length2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 2);
                            val2();
                        }
                    });
                    btn_add_length2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 2);
                            val2();
                            return false;
                        }
                    });
                }
                else{
                    selectorAnakan3ValueWidth = (TextView)view.findViewById(R.id.selector_value_width);
                    selectorAnakan3ValueLength = (TextView)view.findViewById(R.id.selector_value_length);
                    ImageView btn_dec_width3 = (ImageView) view.findViewById(R.id.btn_dec_width);
                    ImageView btn_add_width3 = (ImageView) view.findViewById(R.id.btn_add_width);
                    ImageView btn_dec_length3 = (ImageView) view.findViewById(R.id.btn_dec_length);
                    ImageView btn_add_length3 = (ImageView) view.findViewById(R.id.btn_add_length);

                    porch_no.setText(getResources().getString(R.string.header_3rdporch));
                    btn_delete_anakan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAnakan(3);
                        }
                    });
                    btn_dec_width3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 3);
                            val2();
                        }
                    });
                    btn_dec_width3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanWidth(false, 3);
                            val2();
                            return false;
                        }
                    });

                    btn_add_width3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 3);
                            val2();
                        }
                    });
                    btn_add_width3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanWidth(false, 3);
                            val2();
                            return false;
                        }
                    });

                    btn_dec_length3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 3);
                            val2();
                        }
                    });
                    btn_dec_length3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.decAnakanLength(false, 3);
                            val2();
                            return false;
                        }
                    });

                    btn_add_length3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 3);
                            val2();
                        }
                    });
                    btn_add_length3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            orderActivity.cb.addAnakanLength(false, 3);
                            val2();
                            return false;
                        }
                    });
                }

                anakan_wrapper.addView(view);
                focusOnView(anakan_wrapper);
                //YoYo.with(Techniques.BounceIn).duration(1000).playOn(view);
                orderActivity.cb.setJumlahAnakan(tmpx);
            }
        });

        //reset value when activity opens
        resetRangkaBean();

        //YoYo.with(Techniques.RotateIn).duration(1000).playOn(rootView);
        return rootView;
    }

    private void resetRangkaBean(){
        orderActivity.cb.setRoofType(1);
        orderActivity.cb.setBuffer(Constant.buffer[0]);
        orderActivity.cb.setLength(1);
        orderActivity.cb.setWidth(1);
        orderActivity.cb.setDegree(15);
        orderActivity.cb.setJumlahAnakan(0);
        orderActivity.cb.setAnakan1Length(null);
        orderActivity.cb.setAnakan1Width(null);
        orderActivity.cb.setAnakan2Length(null);
        orderActivity.cb.setAnakan2Width(null);
        orderActivity.cb.setAnakan3Length(null);
        orderActivity.cb.setAnakan3Width(null);
    }

    public void resetContainerBackground(){
        switch(oldRooftype) {
            case 1 :
                container_gable.setBackground(getResources().getDrawable(R.drawable.shape_card));
                break;
            case 2 :
                container_hip.setBackground(getResources().getDrawable(R.drawable.shape_card));
                break;
            case 3 :
                container_shed.setBackground(getResources().getDrawable(R.drawable.shape_card));
                break;
            case 4 :
                container_dutch.setBackground(getResources().getDrawable(R.drawable.shape_card));
                break;
        }
    }

    private final void focusOnView(final View view){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                root.smoothScrollTo(0, view.getBottom());
            }
        });
    }

    public void val2() {
        if (orderActivity.cb == null) {
            return;
        }

        if (selectorAnakan1ValueLength != null) {
            if (orderActivity.cb.getAnakan1Length() != null) {
                selectorAnakan1ValueLength.setText(orderActivity.cb.getAnakan1Length().toString());
            } else {
                selectorAnakan1ValueLength.setText("0");
            }
        }

        if (selectorAnakan1ValueWidth!= null) {
            if (orderActivity.cb.getAnakan1Width() != null) {
                selectorAnakan1ValueWidth.setText(orderActivity.cb.getAnakan1Width().toString());
            } else {
                selectorAnakan1ValueWidth.setText("0");
            }
        }

        if (selectorAnakan2ValueLength != null) {
            if (orderActivity.cb.getAnakan2Length() != null) {
                selectorAnakan2ValueLength.setText(orderActivity.cb.getAnakan2Length().toString());
            } else {
                selectorAnakan2ValueLength.setText("0");
            }
        }

        if (selectorAnakan2ValueWidth!= null) {
            if (orderActivity.cb.getAnakan2Width() != null) {
                selectorAnakan2ValueWidth.setText(orderActivity.cb.getAnakan2Width().toString());
            } else {
                selectorAnakan2ValueWidth.setText("0");
            }
        }

        if (selectorAnakan3ValueLength != null) {
            if (orderActivity.cb.getAnakan3Length() != null) {
                selectorAnakan3ValueLength.setText(orderActivity.cb.getAnakan3Length().toString());
            } else {
                selectorAnakan3ValueLength.setText("0");
            }
        }

        if (selectorAnakan3ValueWidth!= null) {
            if (orderActivity.cb.getAnakan3Width() != null) {
                selectorAnakan3ValueWidth.setText(orderActivity.cb.getAnakan3Width().toString());
            } else {
                selectorAnakan3ValueWidth.setText("0");
            }
        }
    }

    public void deleteAnakan(int c) {
        if (c != orderActivity.cb.getJumlahAnakan()) {
            return;
        }

        anakan_wrapper.removeViewAt((c - 1));

        switch (c) {
            case 1 :
                orderActivity.cb.setAnakan1Length(null);
                orderActivity.cb.setAnakan1Width(null);
                break;
            case 2 :
                orderActivity.cb.setAnakan2Length(null);
                orderActivity.cb.setAnakan2Width(null);
                break;
            case 3 :
                orderActivity.cb.setAnakan3Length(null);
                orderActivity.cb.setAnakan3Width(null);
                break;
        }

        orderActivity.cb.setJumlahAnakan(orderActivity.cb.getJumlahAnakan() - 1);
        val2();
    }
}
