package com.oop.lab3;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.oop.lab3.dtw.DTW;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /* Constants */
    private static final int    LENGTH_CHART_HISTORY  = 1000;
    private static final int    AVERAGE_WINDOW_LENGTH = 1;
    private static final int    DELAY_SENSOR          = SensorManager.SENSOR_DELAY_FASTEST;
    private static final double EPS                   = 1;
    private static final Description DESCRIPTION_NULL = new Description() { { this.setText(""); }};

    /* Member Variables */
    private EMode         mMode;
    private boolean       mResponsive;
    private SensorManager mSensorManager;

    /* Feedback */
    private RelativeLayout mFeedbackLayout;
    private ImageView      mFeedbackView;
    private RelativeLayout mObscureLayout;
    private TextView       mModeTitle;
    private TextView       mModeDescription;
    private TextView       mXResult;
    private TextView       mYResult;
    private TextView       mZResult;
    private TextView       mResult;
    private Switch         mModeSwitch;
    private Button         mStartButton;

    /* Graphs */
    private LineChart mLineAcc;
    private LineChart mLineTrain;
    private LineChart mLineRecognition;

    /* Data */
    private LineData  mAccData;
    private LineData  mTrainData;
    private LineData  mRecognitionData;

    /* Datasets */
    private LineDataSet[] mAcceleration;
    private LineDataSet[] mTraining;
    private LineDataSet[] mRecognition;

    /* Chart Managers */
    private LineChartManager mAccChartManager;
    private LineChartManager mTrainChartManager;
    private LineChartManager mRecognitionChartManager;

    /* History Lists */
    private List<List<Float>> mTrainingHistory;
    private List<List<Float>> mRecognitionHistory;


    /** Mode Definition. */
    private enum EMode {
        /** Defines when the app is recording motion data. */
        TRAINING,
        /** Defines when the app is attempting to recognize motion data. */
        RECOGNITION
    }

    /** Abstracts graph updates. */
    private static class LineChartManager {

        /* Member Variables. */
        private final LineChart     mLineChart;
        private final LineDataSet[] mDataSets;
        private final int           mWindow;
        private final float[]       mBuffer;
        private       int           mOffset;

        /** Constructor. */
        public LineChartManager(final LineChart pLineChart,
                                final int pWindow,
                                final LineDataSet ... pDataSets) {
            this.mLineChart = pLineChart;
            this.mDataSets  = pDataSets;
            this.mWindow    = pWindow;
            this.mBuffer    = new float[pDataSets.length];
            this.mOffset    = 0;
        }

        /** Updates the Chart. */
        public final void onUpdateChart(final float ... pVertical) {
            this.setOffset(this.getOffset() + 1);
            for(int i = 0; i < pVertical.length; i++) {
                this.getBuffer()[i] += pVertical[i];
            }
            if(this.getOffset() % this.getWindow() == 0) {
                this.onAggregateUpdate(this.getBuffer());
                Arrays.fill(this.getBuffer(), 0.0f);
            }
        }

        /** Called when the number of samples displayed on the graph have satisfied the window size. */
        public void onAggregateUpdate(final float[] pAggregate) {
            for(int i = 0; i < this.getDataSets().length; i++) {
                float       lAverage      = this.getBuffer()[i] / this.getWindow();
                LineDataSet lLineDataSet  = this.getDataSets()[i];
                pAggregate[i] = lAverage;
                lLineDataSet.removeFirst();
                lLineDataSet.addEntry(new Entry(this.getOffset(), lAverage));
            }
            this.getLineChart().invalidate();
        }

        /* Getters. */
        private LineChart getLineChart() {
            return this.mLineChart;
        }

        private LineDataSet[] getDataSets() {
            return this.mDataSets;
        }

        private int getWindow() {
            return this.mWindow;
        }

        private float[] getBuffer() {
            return this.mBuffer;
        }

        public void setOffset(final int pOffset) {
            this.mOffset = pOffset;
        }

        private int getOffset() {
            return this.mOffset;
        }

    }

    /** Converts a List of Floats into a primitive equivalent. */
    private static float[] primitive(final List<Float> pList) {
        final float[] lT = new float[pList.size()];
        for(int i = 0; i < pList.size(); i++) {
            lT[i] = pList.get(i);
        }
        return lT;
    }

    /** Colors an Array of DataSets. */
    private static void color(final LineDataSet[] pLineDataSets, final int[] pColor) {
        for(int i = 0; i < pLineDataSets.length; i++) {
            MainActivity.color(pLineDataSets[i], pColor[i]);
        }
    }

    /** Colors a LineDataSet. */
    private static void color(final LineDataSet pLineDataSet, final int pColor) {
        // Update the Colors.
        pLineDataSet.setColor(Color.TRANSPARENT);
        pLineDataSet.setCircleHoleColor(pColor);
        pLineDataSet.setCircleColor(pColor);
    }


    /** Handle Creation of the Activity. */
    @SuppressLint("ClickableViewAccessibility")
    @Override protected final void onCreate(final Bundle pSavedInstanceState) {
        // Implement the Parent Definition
        super.onCreate(pSavedInstanceState);
        // Set the Content View
        this.setContentView(R.layout.activity_main);
        // Initialize Graphs
        this.mLineAcc         = $(R.id.lc_acc);
        this.mLineTrain       = $(R.id.lc_train);
        this.mLineRecognition = $(R.id.lc_recognize);
        // Initialize Feedback
        this.mFeedbackLayout  = $(R.id.rl_feedback);
        this.mFeedbackView    = $(R.id.iv_feedback);
        this.mObscureLayout   = $(R.id.rl_obscure);
        this.mStartButton     = $(R.id.start_button);
        // Initialize Result
        this.mXResult         = $(R.id.xDTW);
        this.mYResult         = $(R.id.yDTW);
        this.mZResult         = $(R.id.zDTW);
        this.mResult          = $(R.id.result);
        // Initialize UI
        this.mModeTitle       = $(R.id.tv_mode);
        this.mModeDescription = $(R.id.tv_mode_desc);
        this.mModeSwitch      = $(R.id.sw_mode);
        // Prepare data
        this.mAccData         = new LineData();
        this.mTrainData       = new LineData();
        this.mRecognitionData = new LineData();
        // Allocate the histories
        this.mTrainingHistory    = new ArrayList<>(Arrays.asList( new ArrayList<>(), new ArrayList<>(), new ArrayList<>() ));
        this.mRecognitionHistory = new ArrayList<>(Arrays.asList( new ArrayList<>(), new ArrayList<>(), new ArrayList<>() ));

        // Allocate the Acceleration
        this.mAcceleration = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };
        this.mTraining     = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };
        this.mRecognition  = new LineDataSet[] { new LineDataSet(null, "X"), new LineDataSet(null, "Y"), new LineDataSet(null, "Z") };

        // Register the Line Data Sources.
        this.getLineAcc().setData(this.getAccData());
        this.getLineTrain().setData(this.getTrainData());
        this.getLineRecognition().setData(this.getRecognitionData());

        // Enable AutoScaling.
        this.getLineAcc().setAutoScaleMinMaxEnabled(true);
        this.getLineTrain().setAutoScaleMinMaxEnabled(true);
        this.getLineRecognition().setAutoScaleMinMaxEnabled(true);
        this.getLineTrain().setScaleXEnabled(true);
        this.getLineRecognition().setScaleXEnabled(true);

        // Hide the right axis for training and recognition.
        this.getLineAcc().getXAxis().setDrawLabels(false);
        this.getLineAcc().getAxisRight().setDrawLabels(false);
        this.getLineTrain().getAxisRight().setDrawLabels(false);
        this.getLineRecognition().getAxisRight().setDrawLabels(false);

        this.getLineAcc().setDescription(MainActivity.DESCRIPTION_NULL);
        this.getLineTrain().setDescription(MainActivity.DESCRIPTION_NULL);
        this.getLineRecognition().setDescription(MainActivity.DESCRIPTION_NULL);

        // Initialize chart data.
        MainActivity.this.onInitializeData(this.getAcceleration());
        MainActivity.this.onInitializeData(this.getTraining());
        MainActivity.this.onInitializeData(this.getRecognition());

        // Register the LineDataSets.
        for(LineDataSet lLineDataSet : this.getAcceleration()) {         this.getAccData().addDataSet(lLineDataSet); }
        for(LineDataSet lLineDataSet : this.getTraining())     {       this.getTrainData().addDataSet(lLineDataSet); }
        for(LineDataSet lLineDataSet : this.getRecognition())  { this.getRecognitionData().addDataSet(lLineDataSet); }

        // Assert that the DataSet has changed, and that we'll be using threse three sources.
        this.getAccData().notifyDataChanged();
        this.getTrainData().notifyDataChanged();
        this.getRecognitionData().notifyDataChanged();

        // Color the DataSets.
        MainActivity.color(this.getAcceleration(), new int[]{ Color.RED, Color.GREEN, Color.BLUE });
        MainActivity.color(this.getTraining(),     new int[]{ Color.RED, Color.GREEN, Color.BLUE });
        MainActivity.color(this.getRecognition(),  new int[]{ Color.RED, Color.GREEN, Color.BLUE });

        this.mAccChartManager         = new LineChartManager(this.getLineAcc(),         MainActivity.AVERAGE_WINDOW_LENGTH, this.getAcceleration());
        this.mTrainChartManager       = new LineChartManager(this.getLineTrain(),       MainActivity.AVERAGE_WINDOW_LENGTH, this.getTraining()) { @Override public final void onAggregateUpdate(final float[] pAggregate) {
            // Update the graph. (This actually manipulates the buffer to compensate for averaging.)
            super.onAggregateUpdate(pAggregate);
            for(int i = 0; i < pAggregate.length; i++) {
                MainActivity.this.getTrainingHistory().get(i).add(pAggregate[i]);
            }
        } };
        // Declare Recognition Handling.
        this.mRecognitionChartManager = new LineChartManager(this.getLineRecognition(), MainActivity.AVERAGE_WINDOW_LENGTH, this.getRecognition()) { @Override public final void onAggregateUpdate(final float[] pAggregate) {
            // Update the graph. (Compute the averages and store the result in the aggregate.)
            super.onAggregateUpdate(pAggregate);
            for(int i = 0; i < pAggregate.length; i++) {
                MainActivity.this.getRecognitionHistory().get(i).add(pAggregate[i]);
            }
        } };

        this.mMode          = EMode.TRAINING;
        this.mResponsive    = false;
        this.mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        // Listen for clicks on the Mode switch.
        this.getModeSwitch().setOnCheckedChangeListener((pCompoundButton, pIsChecked) -> {
            MainActivity.this.setMode(pIsChecked ? EMode.RECOGNITION : EMode.TRAINING);
            MainActivity.this.getModeTitle().setText(pIsChecked ? R.string.mode_recognition      : R.string.mode_training);
            MainActivity.this.getModeDescription().setText(pIsChecked ? R.string.mode_recognition_desc : R.string.mode_training_desc);
        });

        this.getObscureLayout().setOnTouchListener((pView, pMotionEvent) -> {
            return MainActivity.this.getObscureLayout().getVisibility() == View.VISIBLE;
        });

        // Listen for Touch Events on Button.
        this.getStartButton().setOnTouchListener((pView, pMotionEvent) -> {
            // Handle the MotionEvent.
            switch(pMotionEvent.getActionMasked()) {
                /* When the user touches down on the graphs... */
                case MotionEvent.ACTION_DOWN : {
                    MainActivity.this.getModeSwitch().setEnabled(false);
                    switch(MainActivity.this.getMode()) {
                        case TRAINING     : {
                            for(final List<Float> lTraining : MainActivity.this.getTrainingHistory()) {
                                lTraining.clear();
                            }
                            MainActivity.this.getTrainChartManager().setOffset(0);
                            MainActivity.this.onInitializeData(MainActivity.this.getTraining());
                            MainActivity.this.onFeedbackRecording();
                        } break;
                        case RECOGNITION  : {
                            // Reset the Recognition History.
                            for(final List<Float> lRecognition : MainActivity.this.getRecognitionHistory()) {
                                lRecognition.clear();
                            }
                            MainActivity.this.getRecognitionChartManager().setOffset(0);
                            MainActivity.this.onInitializeData(MainActivity.this.getRecognition());
                            MainActivity.this.onFeedbackRecognition();
                        } break;
                    }
                    MainActivity.this.setResponsive(true);
                } break;
                /* Once the user has stopped touching the graphs... */
                case MotionEvent.ACTION_UP   : {
                    MainActivity.this.setResponsive(false);
                    MainActivity.this.onHideFeedback();
                    switch(MainActivity.this.getMode()) {
                        case TRAINING     : {

                        } break;
                        case RECOGNITION  : {
                            MainActivity.this.getObscureLayout().setVisibility(View.VISIBLE);
                            @SuppressLint("SetTextI18n") Runnable lAsyncTask = () -> {
                                final double[] lAverages = new double[3];
                                // Declare the Dynamic Time Warping Algorithm.
                                final DTW      lDTW      = new DTW();
                                for(int i = 0; i < 3; i++) {
                                    final float[] lTraining    = MainActivity.primitive(MainActivity.this.getTrainingHistory().get(i));
                                    final float[] lRecognition = MainActivity.primitive(MainActivity.this.getRecognitionHistory().get(i));
                                    // Calculate the distance using Dynamic Time Warping.
                                    lAverages[i] = lDTW.compute(lRecognition, lTraining).getDistance();
                                }

                                MainActivity.this.runOnUiThread(() -> {
                                    MainActivity.this.getObscureLayout().setVisibility(View.GONE);
                                    // Print the Result.
                                    boolean isNice = true;
                                    for (int i=0;i<3;i++){
                                        if (Double.isNaN(lAverages[i]) || lAverages[i] > EPS) {
                                            isNice = false;
                                            break;
                                        }
                                    }
                                    if (isNice) { getResult().setText("Recognized");}
                                    else {getResult().setText("Not recognized");}
                                    getXResult().setText(String.valueOf(lAverages[0]));
                                    getYResult().setText(String.valueOf(lAverages[1]));
                                    getZResult().setText(String.valueOf(lAverages[2]));
                                });
                            };
                            ExecutorService executor = Executors.newFixedThreadPool(4);
                            executor.execute(lAsyncTask);
                        } break;
                    }
                    // Re-enable the Switch.
                    MainActivity.this.getModeSwitch().setEnabled(true);
                } break;
            }
            return true;
        });
        this.onHideFeedback();
    }

    /* Resets a Chart. */
    private void onInitializeData(final LineDataSet[] pDataSet) {
        // Ensure the DataSets are empty.
        for(final LineDataSet lLineDataSet : pDataSet) {
            lLineDataSet.clear();
        }

        for(int i = 0; i < MainActivity.LENGTH_CHART_HISTORY; i++) {
            final Entry lEntry = new Entry(i, 0);
            for(final LineDataSet lLineDataSet : pDataSet) {
                lLineDataSet.addEntry(lEntry);
            }
        }
    }

    /* Hides the Feedback View. */
    private void onHideFeedback() {
        this.getFeedbackView().setVisibility(View.GONE);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent));
    }

    /* Asserts that we're recording. */
    private void onFeedbackRecording() {
        this.getFeedbackView().setVisibility(View.VISIBLE);
        this.getFeedbackView().setImageResource(R.drawable.ic_train_circle);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorMature));
    }

    /* Asserts that we're recognizing. */
    private void onFeedbackRecognition() {
        this.getFeedbackView().setVisibility(View.VISIBLE);
        this.getFeedbackView().setImageResource(R.drawable.ic_record_circle);
        this.getFeedbackLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.colorMature));
    }

    /* Simple return for a resource reference. */
    private <T extends View> T $(int pId) {
        // Return the type-casted View.
        return (T)this.findViewById(pId);
    }

    /* Handle a change to sensor data. */
    @Override public final void onSensorChanged(final SensorEvent pSensorEvent) {
        if(pSensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Update the LineChartManager.
            this.getAccChartManager().onUpdateChart(pSensorEvent.values);
            if(MainActivity.this.isResponsive()) {
                switch(MainActivity.this.getMode()) {
                    case TRAINING    : {
                        // Update the Training Chart.
                        this.getTrainChartManager().onUpdateChart(pSensorEvent.values);
                    } break;
                    case RECOGNITION : {
                        // Update the Training Chart.
                        this.getRecognitionChartManager().onUpdateChart(pSensorEvent.values);
                    } break;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    /* When the Activity is resumed. */
    @Override protected final void onResume() {
        super.onResume();
        // Register for updates on the SensorManager
        this.getSensorManager().registerListener(this, this.getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER), MainActivity.DELAY_SENSOR);
    }

    /* When the Activity is paused. */
    @Override protected final void onPause() {
        super.onPause();
        // Stop listening for accelerometer data
        this.getSensorManager().unregisterListener(this);
    }

    /* Getters and Setters */
    private void setMode(final EMode pMode) {
        this.mMode = pMode;
    }

    private EMode getMode() {
        return this.mMode;
    }

    private void setResponsive(final boolean pIsResponsive) {
        this.mResponsive = pIsResponsive;
    }

    private boolean isResponsive() {
        return this.mResponsive;
    }

    private SensorManager getSensorManager() {
        return this.mSensorManager;
    }

    private LineChart getLineAcc() {
        return this.mLineAcc;
    }

    private LineChart getLineTrain() {
        return this.mLineTrain;
    }

    private LineChart getLineRecognition() {
        return this.mLineRecognition;
    }

    private RelativeLayout getFeedbackLayout() {
        return this.mFeedbackLayout;
    }

    private ImageView getFeedbackView() {
        return this.mFeedbackView;
    }

    private RelativeLayout getObscureLayout() {
        return this.mObscureLayout;
    }

    private TextView getModeTitle() {
        return this.mModeTitle;
    }

    private TextView getXResult() {
        return this.mXResult;
    }

    private TextView getYResult() {
        return this.mYResult;
    }

    private TextView getZResult() {
        return this.mZResult;
    }

    private TextView getResult() {
        return this.mResult;
    }

    private TextView getModeDescription() {
        return this.mModeDescription;
    }

    private Button getStartButton() {
        return this.mStartButton;
    }

    private Switch getModeSwitch() {
        return this.mModeSwitch;
    }

    private LineData getAccData() {
        return this.mAccData;
    }

    private LineData getTrainData() {
        return this.mTrainData;
    }

    private LineData getRecognitionData() {
        return this.mRecognitionData;
    }

    private LineDataSet[] getAcceleration() {
        return this.mAcceleration;
    }

    private LineDataSet[] getTraining() {
        return this.mTraining;
    }

    private LineDataSet[] getRecognition() {
        return this.mRecognition;
    }

    private LineChartManager getAccChartManager() {
        return this.mAccChartManager;
    }

    private LineChartManager getTrainChartManager() {
        return this.mTrainChartManager;
    }

    private LineChartManager getRecognitionChartManager() {
        return this.mRecognitionChartManager;
    }

    private List<List<Float>> getTrainingHistory() {
        return this.mTrainingHistory;
    }

    private List<List<Float>> getRecognitionHistory() {
        return this.mRecognitionHistory;
    }

}
