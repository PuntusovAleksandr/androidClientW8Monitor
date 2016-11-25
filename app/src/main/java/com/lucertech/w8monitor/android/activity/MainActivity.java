package com.lucertech.w8monitor.android.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lucertech.w8monitor.android.R;
import com.lucertech.w8monitor.android.activity.interfaces.presentts.MainActivityPresenter;
import com.lucertech.w8monitor.android.activity.interfaces.views.MainView;
import com.lucertech.w8monitor.android.api.model.UserApi;
import com.lucertech.w8monitor.android.api.service.ApiService;
import com.lucertech.w8monitor.android.ble.BluetoothHandler;
import com.lucertech.w8monitor.android.d_base.model.ParamsBody;
import com.lucertech.w8monitor.android.fragments.main.CircleGraphFragment;
import com.lucertech.w8monitor.android.fragments.main.CircleGraphView;
import com.lucertech.w8monitor.android.fragments.main.LinerGraphFragment;
import com.lucertech.w8monitor.android.google.fit.GoogleFitApp;
import com.lucertech.w8monitor.android.presents.main.impl.MainActivityPresenterImpl;
import com.lucertech.w8monitor.android.utils.STATICS_PARAMS;
import com.lucertech.w8monitor.android.utils.SetLocaleApp;
import com.lucertech.w8monitor.android.utils.SetThemeDark;
import com.lucertech.w8monitor.android.utils.SettingsApp;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.lucertech.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.lucertech.w8monitor.android.api.constant.ApiConstants.ALL_MEASUREMENTS_TIME;
import static com.lucertech.w8monitor.android.api.constant.ApiConstants.MEASUREMENTS;
import static com.lucertech.w8monitor.android.api.constant.ApiConstants.MEASUREMENTS_MASS;
import static com.lucertech.w8monitor.android.api.constant.ApiConstants.USER_SUNS;
import static com.lucertech.w8monitor.android.fragments.FragmentMapker.CIRCLE_GRAPH_FRAGMENT;
import static com.lucertech.w8monitor.android.fragments.FragmentMapker.LINER_GRAPH_FRAGMENT;
import static com.lucertech.w8monitor.android.utils.InternetUtils.checkInternetConnection;
import static com.lucertech.w8monitor.android.utils.LoggerApp.logger;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.EXTRA_TIMESTAMP;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.EXTRA_TIME_CREATE;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.KEI_CONNECTION;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.REQUEST_ENABLE_BT;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.REQUEST_OAUTH;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.lucertech.w8monitor.android.utils.ShowMesages.showMessageToast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity implements MainView,
        BluetoothHandler.onResultScanDevice {

    public static final String TAG_GOOGLE_FIT = "GOOGLE_FIT";

    private MainActivityPresenter mPresenter;
    private FragmentManager mFragmentManager;

    private CircleGraphFragment mCircleGraphFragment;
    private LinerGraphFragment mLinerGraphFragment;

    private boolean isShowLineFragment = false;
    private boolean isThemeForStarDark;

    private boolean supportExist = false;

    private String localString;

    // for disable double answer from BLE by connection
    private boolean firstConnection = false;

    @Bind(R.id.rl_main_register)
    RelativeLayout rl_main_register;

    private GoogleFitApp mFitApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetThemeDark.getInstance().setTheme(this);
        SetLocaleApp.setLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mPresenter = new MainActivityPresenterImpl(this, this);
        mFragmentManager = getSupportFragmentManager();

        setUi();

        mFitApp = new GoogleFitApp(this);
    }

    private void getAllMeasurements() {
        mPresenter.getAllMeasurements(this);
    }

    private void setUi() {
        setCircleFragment();
        isThemeForStarDark = SettingsApp.getInstance().isThemeDark();
        localString = SettingsApp.getInstance().getLanguages();

        checkBluetooth();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        mFitApp.buildFitnessClient();


        if (checkInternetConnection()) {
            mPresenter.sendProfileData();
        }

        if (isThemeForStarDark != SettingsApp.getInstance().isThemeDark()) {
            finish();
            startActivity(getIntent());
        }
        if (!localString.equalsIgnoreCase(SettingsApp.getInstance().getLanguages())) {
            finish();
            startActivity(getIntent());
        }
        checkSupportBLE();

        mPresenter.registerEvenBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unregisterEvenBus();
    }

    public void checkSupportBLE() {
        if (!isConnected) {
            bluetoothHandler.checkSupport();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // This ensures that if the user denies the permissions then uses Settings to re-enable
        // them, the app will start working.
        mFitApp.connect();
    }

    public void disconnectBLE(boolean isScan) {
//        if (isConnected) {
        isConnected = false;
        bluetoothHandler.onPause();
        bluetoothHandler.onDestroy(isScan);
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && !firstConnection) {
            firstConnection = true;
            if (resultCode == RESULT_OK) {
                callListBluetooth();
            } else {
                showMessageToast(getString(R.string.yuo_did_not_enable_ble));
                mCircleGraphView.setDefaultUI();
                supportExist = false;
            }
            removeFirstConnection();
        }
        if (requestCode == REQUEST_OAUTH) {
            mFitApp.requestOauth(requestCode);
        }
    }

    private void removeFirstConnection() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstConnection = false;
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        bluetoothHandler.disConnect();
        mFitApp.onDestroy();
        super.onDestroy();
    }


    public void setCircleFragment() {
        mCircleGraphFragment = (CircleGraphFragment) getSupportFragmentManager()
                .findFragmentByTag(CIRCLE_GRAPH_FRAGMENT);
        if (mCircleGraphFragment == null) {
            mCircleGraphFragment = new CircleGraphFragment(this, mPresenter);
        }
        isShowLineFragment = false;
        setFragment(mCircleGraphFragment, CIRCLE_GRAPH_FRAGMENT);
    }

    private void setLinerFragment(int mValue) {
        mLinerGraphFragment = (LinerGraphFragment) getSupportFragmentManager()
                .findFragmentByTag(LINER_GRAPH_FRAGMENT);
        if (mLinerGraphFragment == null) {
            mLinerGraphFragment = new LinerGraphFragment(this, mPresenter, mValue);
        }
        isShowLineFragment = true;
        setFragment(mLinerGraphFragment, LINER_GRAPH_FRAGMENT);
    }


    private void setFragment(Fragment mFragment, String tag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.rl_container_for_graph, mFragment, tag)
                .commit();
    }


    //    =====================================================================
//    SET BLE
//    =====================================================================

    private BluetoothHandler bluetoothHandler;
    private boolean isConnected;


    /**
     * check bluetooth on device
     */
    private void checkBluetooth() {
        logger("checkBluetooth");
        if (bluetoothHandler == null) {
            bluetoothHandler = new BluetoothHandler(this, this);
            bluetoothHandler.checkPermission(this);
            bluetoothHandler.setOnScanListener(new BluetoothHandler.OnScanListener() {
                @Override
                public void onScanFinished() {
                    System.out.println("onScanFinished");
                    logger("OnScanListener isConnected = " + isConnected);
//                    if (!isConnected) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                logger("OnScanListener start new scan ");
//                                callListBluetooth();
//                            }
//                        }, SCAN_PERIOD * 2);        // запуск следующего сканера
//                    }
                }

                @Override
                public void onScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    System.out.println("onScan device" + device.getName());
                    logger("onScan device" + device.getName());

                }
            });
            bluetoothHandler.setOnConnectedListener(new BluetoothHandler.OnConnectedListener() {

                @Override
                public void onConnected(boolean isConnected) {
                    logger("OnConnectedListener isConnected = " + isConnected);
                    setConnectStatus(isConnected);
                }

                @Override
                public void disconnectBLEMain() {
                    disconnectBLE(true);
                }
            });

            // show data from BLE
            bluetoothHandler.setOnRecievedDataListener(new BluetoothHandler.OnRecievedDataListener() {
                @Override
                public void onRecievedData(byte[] bytes) {
                    if (bytes != null) {
                        parseAndUpdateDB(bytes);
                        logger("OnRecievedDataListener bytes = \n" + bytes.toString());
//                Toast.makeText(MainActivity.this, bytes, Toast.LENGTH_SHORT).show();
                        System.out.println("Answer  " + bytes.toString() + "<-");
                    } else {
                        Toast.makeText(MainActivity.this, "Failed Test", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
//        bluetoothHandler.checkSupport();
    }

    // parse data from scale and save it in db
    private void parseAndUpdateDB(byte[] mBytes) {
        System.out.println("ANSWER FROM ble " + mBytes);
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < mBytes.length; i++) {
            int v = mBytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        final String result = stringBuilder.toString();
        System.out.println("last  result " + result);

        Receive receive = new Receive();
        receive.content = result;
        receive.date = new Date();
        logger("******************`Результаты испытаний" + result);
        if (!result.startsWith("fd33") && (result.length() >= 32 || result.startsWith("fd31"))) {
            parse(result);
        }
    }

    int count = 0;
    private DecimalFormat dfc = new DecimalFormat("#.#");
    private boolean isShow = false;

    // parse result from BLE
    private void parse(String content) {
        logger("****************** Результаты испытаний parse :" + content);
        if (0 == content.compareToIgnoreCase("fd31000000000031")) {
            if (count < 3) {
//                sendDate();
                count++;
            } else {
                count = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Fat Failed Test");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
            return;
        }
        count = 0;
        byte[] data = hexStringToBytes(content);

        // Тип устройства
        int v = data[0] & 0xFF;
        String typeRec = "Fat Scale";
        if (v == 0xcf) {
            typeRec = "Fat Scale";
        } else if (v == 0xce) {
            typeRec = "Весы для тела";
        } else if (v == 0xcb) {
            typeRec = "Весы для новорожденных";
        } else if (v == 0xca) {
            typeRec = "Кухонные весы";
        }

        // Рейтинги и номер группы
        int level = (data[1] >> 4) & 0xf;
        int group = data[1] & 0xf;

        String levelRec = "普通";
        if (level == 0) {
            levelRec = "обычный";
        } else if (level == 1) {
            levelRec = "любительский";
        } else if (level == 2) {
            levelRec = "специальность";
        }

        // пол
        int sex = (data[2] >> 7) & 0x1;
        String secRec = "";
        if (sex == 1) {
            secRec = "мужчина";
        } else {
            secRec = "женщина";
        }
        // возраст
        int age = data[2] & 0x7f;

        // рост
        int height = data[3] & 0xFF;

        // вес
        int weight = (data[4] << 8) | (data[5] & 0xff);
        float scale = (float) 0.1;
        if (v == 0xcf) {
            scale = (float) 0.1;
        } else if (v == 0xce) {
            scale = (float) 0.1;
        } else if (v == 0xcb) {
            scale = (float) 0.01;
        } else if (v == 0xca) {
            scale = (float) 0.001;
        }

        float weightRec = scale * weight;

        if (weightRec < 0) {
            weightRec *= -1;
        }
        String formatweightRec = dfc.format(weightRec).replaceAll(",", ".");
        weightRec = Float.parseFloat(formatweightRec);

        // жир
        int zhifang = (data[6] << 8) | (data[7] & 0xff);
        float zhifangRate = (float) (zhifang * 0.1);
        String formatzhifangRate = dfc.format(zhifangRate).replaceAll(",", ".");
        zhifangRate = Float.parseFloat(formatzhifangRate);

        // скелет
        int guge = data[8] & 0xff;
        float gugeRate = (float) ((guge * 0.1) / weightRec) * 100;
        String format = dfc.format(gugeRate).replaceAll(",", ".");
        gugeRate = Float.parseFloat(format);

        // содержание мышц
        int jirou = (data[9] << 8) | (data[10] & 0xff);
        float jirouRate = (float) (jirou * 0.1);
        String formatjirouRate = dfc.format(jirouRate).replaceAll(",", ".");
        jirouRate = Float.parseFloat(formatjirouRate);

        // Висцеральный жир рейтинг
        int neizang = data[11] & 0xff;
        int neizanglevel = neizang * 1;

        // влагосодержание
        int water = data[12] << 8 | data[13];
//        float waterRate = (float) (water * 0.1);
        float waterRate = getWater(weightRec, height, sex);
        String formatwaterRate = dfc.format(waterRate).replaceAll(",", ".");
        waterRate = Float.parseFloat(formatwaterRate);

        // Калорийность
        int hot = data[14] << 8 | (data[15] & 0xff);

        int physicalAge = -1;
        if (data.length > 16) {
            physicalAge = data[16] & 0xFF;
            ;
        }


//        String ageBody = "Тело Возраст:" + (physicalAge < 0 ? ".расчетное Тело Возраст:" : physicalAge);
        String ageBody = physicalAge < 0 ? "0" : String.valueOf(physicalAge);

        if (!isShow) {
            isShow = true;
//            showResult(rec);
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    playSound();
                }
            }).start();
        }

        float v1 = (float) height / 100;
        float bmi = weightRec / (v1 * v1);
        String formatbmi = dfc.format(bmi).replaceAll(",", ".");
        bmi = Float.parseFloat(formatbmi);

        String answerFromBLE = "ПОЛУЧЕННЫЕ ___ " +
                "\nТип устройства " + typeRec +
                "\ngroup " + group +
                "\nlevelRec " + levelRec +
                "\nweight " + weightRec +
                "\nsex " + secRec +
                "\nвозраст " + age +
                "\nрост " + height +
                "\ngugeBody " + gugeRate +
                "\nfat " + zhifangRate +
                "\nmuscle Mass " + jirouRate +
                "\nwater " + waterRate +
                "\nlevel fat " + neizanglevel +
                "\nEMR " + hot +
                "\nBMI " + bmi +
                "\nphysical_age " + (physicalAge < 0 ? 0 : physicalAge);
        logger(answerFromBLE);

//        for (int i = 0; i < 3; i++) {
//            Toast.makeText(this, answerFromBLE, Toast.LENGTH_LONG).show();
//        }

        long time = new Date().getTime() / 1000;
        // save in DB
        mPresenter.addParamsBody(
                weightRec < 0 ? -weightRec : weightRec,
                gugeRate < 0 ? -gugeRate : gugeRate,
                zhifangRate < 0 ? -zhifangRate : zhifangRate,
                jirouRate < 0 ? -jirouRate : jirouRate,
                waterRate < 0 ? -waterRate : waterRate,
                neizanglevel < 0 ? -neizanglevel : neizanglevel,
                hot < 0 ? -hot : hot,
                physicalAge < 0 ? 0 : physicalAge,
//                BMI
                bmi,
                time,
                mCircleGraphView,
                false
        );

    }


//    ===================================================
//            for TEST
//    ===================================================

    public void testAddData() {

        int random = (int) (Math.random() * 10);
        long time = new Date().getTime() / 1000;
        mPresenter.addParamsBody(
                random * 10,
                random * 7,
                random * 7,
                random * 10,
                random * 8,
                random * 5,
                random * 2000,
                random * 1,
                random * 8,
                time,
                mCircleGraphView,
                false
        );
    }

//    ===================================================


    private float getWater(float mWeightRec, int mHeight, int mSex) {
        float answer = 0;
        if (mSex == 1) {        // мужик
            if (mHeight > 132) {        // 132.7
                answer = 0.79f * (-21.993f + 0.406f * mWeightRec + 0.209f * (mHeight));
            } else {
                answer = 1.927f + 0.465f * mWeightRec + 0.045f * (mHeight);
            }
        } else {
            if (mHeight > 110) {        // 110,8
                answer = -10.313f + 0.252f * mWeightRec + 0.154f * (mHeight);
            } else {
                answer = 0.076f + 0.507f * mWeightRec + 0.013f * (mHeight);
            }
        }
        return answer;
    }


    void showResult(String[] result) {
        new AlertDialog.Builder(this).setTitle("Вес").setItems(result, null)
                .setNegativeButton("Ок", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        isShow = false;
                    }
                }).show();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    private byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public void setConnectStatus(boolean isConnected) {
        this.isConnected = isConnected;
        logger("ConnectStatus isConnected = " + isConnected);
        if (isConnected) {
            showMessage(getString(R.string.connection_successful));
            mCircleGraphView.hideTextEnableBLE();
            deleteValuesInTextShows();
        } else {
            callListBluetooth();
        }
    }

    private void deleteValuesInTextShows() {
        mCircleGraphView.deleteValuesInTextShows();
    }


    public void callListBluetooth() {
        supportExist = true;
        mCircleGraphView.showTextConnectionBLE();   // show text about enable support

        logger("callListBluetooth isConnected = " + isConnected);
        System.out.println("callListBluetooth isConnected = " + isConnected);
        if (!isConnected) {
            bluetoothHandler.onPause();
            bluetoothHandler.onDestroy(true);
            bluetoothHandler.scanLeDevice(true);
        } else {
            setConnectStatus(false);
        }
    }

    // from onResultScanDevice
    @Override
    public void scanOk(boolean mEnabled) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onActivityResult(REQUEST_ENABLE_BT, RESULT_OK, null);
            }
        }, 250);
    }

    private void showMessage(String str) {
        logger("CshowMessage = " + str);
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    //    ====================================================================
//     Start        MainView
//    ====================================================================

    @Override
    public void goToSettings() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(STATICS_PARAMS.INNER_MARKER_PROFILE, MARKER_MAIN);
        intent.putExtra(KEI_CONNECTION, supportExist);
        startActivity(intent);
    }

    @Override
    public void setLineGraph(int mValue) {
        setLinerFragment(mValue);
    }

    @Override
    public void sendProfileToServer() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, USER_SUNS);
        startService(serviceIntent);

    }

    @Override
    public void makeUpdateUserSync(UserApi mEvent) {
        mPresenter.makeUpdateUserDb(this, mEvent);
    }

    @Override
    public void makeRequestUpdateMeasurement() {
        getAllMeasurements();
    }

    @Override
    public void makeAllUpdateUi() {
        // TODO: 19.11.2016 нужно сделать обновление всех данных на жкране
    }

    @Override
    public void getAllMeasurementsFromServer(RealmResults<ParamsBody> mAllSorted) {
        if (checkInternetConnection()) {
            String time = "";
            if (mAllSorted != null && mAllSorted.size() > 0) {
                ParamsBody paramsBody = mAllSorted.get(mAllSorted.size() - 1);
                time = Long.toString(paramsBody.getDate_time());
            }
            Intent serviceIntent = new Intent(this, ApiService.class);
            serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, ALL_MEASUREMENTS_TIME);
            serviceIntent.putExtra(EXTRA_TIMESTAMP, time);
            startService(serviceIntent);
        }
    }

    @Override
    public void addParamBody(float weight, float fat, float gugeBody,
                             float muscle, int level_fat, float waterRate,
                             int emr, int mPhysicalAge, float mBmi, int profileId, long mCreated_at) {

        mPresenter.addParamBody(weight, gugeBody, fat, muscle, waterRate, level_fat,
                emr, mPhysicalAge, mBmi, mCreated_at, mCircleGraphView, true);

    }

    @Override
    public void semdMeasurementToServer(long mTime) {
        if (checkInternetConnection()) {
            Intent serviceIntent = new Intent(this, ApiService.class);
            serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, MEASUREMENTS);
            serviceIntent.putExtra(EXTRA_TIME_CREATE, mTime);
            startService(serviceIntent);
        }
    }

    @Override
    public void updateUi(ParamsBody mData) {
        mCircleGraphView.updateUi(mData);
    }

    //    ====================================================================
//     END        MainView
//    ====================================================================


    @Override
    public void onBackPressed() {
        if (isShowLineFragment) {
            setCircleFragment();
        } else {

            if (bluetoothHandler.isBroadcastRegistered()) {
                unregisterReceiver(bluetoothHandler.getGattUpdateReceiver());
            }
            super.onBackPressed();
        }
    }

    // init CircleFragmentView
    private CircleGraphView mCircleGraphView;

    public void initListenerCircleFragment(CircleGraphView mCircleGraphView) {
        this.mCircleGraphView = mCircleGraphView;
    }

    public void setSyncMeasurements() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, MEASUREMENTS_MASS);
        startService(serviceIntent);
    }

    public class Receive {

        public String content;
        public Date date;
    }


//    ===========================================================
//            GOOGLE FIT
//    ===========================================================



}
