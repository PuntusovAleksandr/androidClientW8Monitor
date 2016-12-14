/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothServerSocket
 *  android.bluetooth.BluetoothSocket
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.util.UUID
 */
package com.lefu.es.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.lefu.es.constant.BTConstant;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothChatService {
    private static final boolean D = true;
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String TAG = "BluetoothChatService";
    public static int mState;
    private final BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private final Handler mHandler;
    private AcceptThread mInsecureAcceptThread;
    private AcceptThread mSecureAcceptThread;

    public BluetoothChatService(Context context, Handler handler) {
        mState = 0;
        this.mHandler = handler;
    }

    static /* synthetic */ void access$2(BluetoothChatService bluetoothChatService, ConnectThread connectThread) {
        bluetoothChatService.mConnectThread = connectThread;
    }

    private void connectionFailed() {
        Message message = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString("toast", "Unable to connect device");
        message.setData(bundle);
        this.mHandler.sendMessage(message);
        this.start();
    }

    private void connectionLost() {
        Message message = this.mHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putString("toast", "Device connection was lost");
        message.setData(bundle);
        this.mHandler.sendMessage(message);
        this.start();
    }

    private void setState(int n) {
        BluetoothChatService bluetoothChatService = this;
        synchronized (bluetoothChatService) {
            mState = n;
            if (this.mHandler != null) {
                this.mHandler.obtainMessage(1, n, -1).sendToTarget();
            }
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void connect(BluetoothDevice bluetoothDevice, boolean bl) {
        BluetoothChatService bluetoothChatService = this;
        synchronized (bluetoothChatService) {
            Log.d((String)"BluetoothChatService", (String)("connect to: " + (Object)bluetoothDevice));
            try {
                if (mState == 2 && this.mConnectThread != null) {
                    this.mConnectThread.cancel();
                    this.mConnectThread = null;
                }
                if (this.mConnectedThread != null) {
                    this.mConnectedThread.cancel();
                    this.mConnectedThread = null;
                }
                this.mConnectThread = new ConnectThread(bluetoothDevice, bl);
                this.mConnectThread.start();
                this.setState(2);
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Exception var5_4) {
                try {
                    this.connectionFailed();
                    return;
                }
                catch (Throwable var3_5) {
                    throw var3_5;
                }
                finally {
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean connectDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        try {
            BluetoothSocket bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(BTConstant.MY_UUID_INSECURE);
            if (bluetoothSocket != null) {
                bluetoothSocket.connect();
                bluetoothSocket.close();
                return true;
            }
        }
        catch (Exception var2_3) {
            var2_3.printStackTrace();
        }
        this.connectionFailed();
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void connected(BluetoothSocket bluetoothSocket, BluetoothDevice bluetoothDevice, String string2) {
        BluetoothChatService bluetoothChatService = this;
        synchronized (bluetoothChatService) {
            Log.d((String)"BluetoothChatService", (String)("connected, Socket Type:" + string2));
            try {
                if (this.mConnectThread != null) {
                    this.mConnectThread.cancel();
                    this.mConnectThread = null;
                }
                if (this.mConnectedThread != null) {
                    this.mConnectedThread.cancel();
                    this.mConnectedThread = null;
                }
                if (this.mSecureAcceptThread != null) {
                    this.mSecureAcceptThread.cancel();
                    this.mSecureAcceptThread = null;
                }
                if (this.mInsecureAcceptThread != null) {
                    this.mInsecureAcceptThread.cancel();
                    this.mInsecureAcceptThread = null;
                }
                this.mConnectedThread = new ConnectedThread(bluetoothSocket, string2);
                this.mConnectedThread.start();
                Message message = this.mHandler.obtainMessage(4);
                Bundle bundle = new Bundle();
                bundle.putString("device_name", bluetoothDevice.getName());
                message.setData(bundle);
                this.mHandler.sendMessage(message);
                this.setState(3);
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Exception var6_7) {
                try {
                    this.connectionFailed();
                    return;
                }
                catch (Throwable var4_8) {
                    throw var4_8;
                }
                finally {
                }
            }
        }
    }

    public int getState() {
        BluetoothChatService bluetoothChatService = this;
        synchronized (bluetoothChatService) {
            int n = mState;
            return n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void start() {
        BluetoothChatService bluetoothChatService = this;
        synchronized (bluetoothChatService) {
            Log.d((String)"BluetoothChatService", (String)"start");
            try {
                if (this.mConnectThread != null) {
                    this.mConnectThread.cancel();
                    this.mConnectThread = null;
                }
                if (this.mConnectedThread != null) {
                    this.mConnectedThread.cancel();
                    this.mConnectedThread = null;
                }
                this.setState(1);
                if (this.mSecureAcceptThread == null) {
                    this.mSecureAcceptThread = new AcceptThread(true);
                    this.mSecureAcceptThread.start();
                }
                if (this.mInsecureAcceptThread == null) {
                    this.mInsecureAcceptThread = new AcceptThread(false);
                    this.mInsecureAcceptThread.start();
                }
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Exception var3_2) {
                try {
                    var3_2.printStackTrace();
                    return;
                }
                catch (Throwable var1_3) {
                    throw var1_3;
                }
                finally {
                }
            }
        }
    }

    /*
     * Exception decompiling
     */
    public boolean write(byte[] var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.util.ConcurrentModificationException
        // java.util.LinkedList$ReverseLinkIterator.next(LinkedList.java:217)
        // org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.extractLabelledBlocks(Block.java:212)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement$LabelledBlockExtractor.transform(Op04StructuredStatement.java:485)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.transform(Op04StructuredStatement.java:639)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.insertLabelledBlocks(Op04StructuredStatement.java:649)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:816)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
        // java.lang.Thread.run(Thread.java:841)
        throw new IllegalStateException("Decompilation failed");
    }

    private class AcceptThread
    extends Thread {
        private String mSocketType;
        private final BluetoothServerSocket mmServerSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public AcceptThread(boolean bl) {
            String string2 = true ? "Secure" : "Insecure";
            this.mSocketType = string2;
            BluetoothServerSocket bluetoothServerSocket = null;
            if (true) {
                try {
                    BluetoothAdapter bluetoothAdapter = BluetoothChatService.this.mAdapter;
                    bluetoothServerSocket = null;
                    if (bluetoothAdapter != null) {
                        boolean bl2 = BluetoothChatService.this.mAdapter.isEnabled();
                        bluetoothServerSocket = null;
                        if (bl2) {
                            BluetoothServerSocket bluetoothServerSocket2;
                            bluetoothServerSocket = bluetoothServerSocket2 = BluetoothChatService.this.mAdapter.listenUsingRfcommWithServiceRecord("BluetoothChatSecure", BTConstant.MY_UUID_SECURE);
                        }
                    }
                }
                catch (IOException var5_8) {
                    Log.e((String)"BluetoothChatService", (String)("Socket Type: " + this.mSocketType + "listen() failed"), (Throwable)var5_8);
                    bluetoothServerSocket = null;
                }
            }
            this.mmServerSocket = bluetoothServerSocket;
        }

        public void cancel() {
            try {
                if (this.mmServerSocket != null) {
                    this.mmServerSocket.close();
                }
                return;
            }
            catch (Exception var1_1) {
                Log.e((String)"BluetoothChatService", (String)("Socket Type" + this.mSocketType + "close() of server failed"), (Throwable)var1_1);
                return;
            }
        }
    }

    private class ConnectThread
    extends Thread {
        private String mSocketType;
        private final BluetoothDevice mmDevice;
        private final BluetoothSocket mmSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ConnectThread(BluetoothDevice bluetoothDevice, boolean bl) {
            this.mmDevice = bluetoothDevice;
            String string2 = true ? "Secure" : "Insecure";
            this.mSocketType = string2;
            BluetoothSocket bluetoothSocket = null;
            if (true) {
                try {
                    BluetoothSocket bluetoothSocket2;
                    bluetoothSocket = bluetoothSocket2 = bluetoothDevice.createRfcommSocketToServiceRecord(BTConstant.MY_UUID_SECURE);
                }
                catch (Exception var6_7) {
                    Log.e((String)"BluetoothChatService", (String)("Socket Type: " + this.mSocketType + "create() failed"), (Throwable)var6_7);
                    bluetoothSocket = null;
                }
            }
            this.mmSocket = bluetoothSocket;
        }

        public void cancel() {
            try {
                if (this.mmSocket != null) {
                    this.mmSocket.close();
                }
                return;
            }
            catch (IOException var1_1) {
                Log.e((String)"BluetoothChatService", (String)("close() of connect " + this.mSocketType + " socket failed"), (Throwable)var1_1);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            BluetoothChatService bluetoothChatService;
            Log.i((String)"BluetoothChatService", (String)("BEGIN mConnectThread SocketType:" + this.mSocketType));
            this.setName("ConnectThread" + this.mSocketType);
            try {
                BluetoothChatService bluetoothChatService2;
                if (BluetoothChatService.this.mAdapter != null) {
                    BluetoothChatService.this.mAdapter.cancelDiscovery();
                }
                if (this.mmSocket != null) {
                    this.mmSocket.connect();
                }
                bluetoothChatService = bluetoothChatService2 = BluetoothChatService.this;
            }
            catch (IOException var2_3) {
                try {
                    Log.e((String)"BluetoothChatService", (String)this.mmDevice.getName());
                    if (this.mmSocket != null) {
                        this.mmSocket.close();
                    }
                }
                catch (IOException var3_4) {
                    Log.e((String)"BluetoothChatService", (String)("unable to close() " + this.mSocketType + " socket during connection failure"), (Throwable)var3_4);
                }
                BluetoothChatService.this.connectionFailed();
                return;
            }
            synchronized (bluetoothChatService) {
                BluetoothChatService.access$2(BluetoothChatService.this, null);
            }
            BluetoothChatService.this.connected(this.mmSocket, this.mmDevice, this.mSocketType);
        }
    }

    private class ConnectedThread
    extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ConnectedThread(BluetoothSocket bluetoothSocket, String string2) {
            OutputStream outputStream;
            Log.d((String)"BluetoothChatService", (String)("create ConnectedThread: " + string2));
            this.mmSocket = bluetoothSocket;
            InputStream inputStream = null;
            try {
                OutputStream outputStream2;
                inputStream = bluetoothSocket.getInputStream();
                outputStream = outputStream2 = bluetoothSocket.getOutputStream();
            }
            catch (IOException var6_7) {
                Log.e((String)"BluetoothChatService", (String)"temp sockets not created", (Throwable)var6_7);
                outputStream = null;
            }
            this.mmInStream = inputStream;
            this.mmOutStream = outputStream;
        }

        public void cancel() {
            try {
                if (this.mmSocket != null) {
                    this.mmSocket.close();
                }
                return;
            }
            catch (IOException var1_1) {
                Log.e((String)"BluetoothChatService", (String)"close() of connect socket failed", (Throwable)var1_1);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void run() {
            Log.i((String)"BluetoothChatService", (String)"BEGIN mConnectedThread");
            byte[] arrby = new byte[1024];
            try {
                do {
                    if (this.mmInStream == null) {
                        continue;
                    }
                    int n = this.mmInStream.read(arrby);
                    if (BluetoothChatService.this.mHandler == null) continue;
                    BluetoothChatService.this.mHandler.obtainMessage(2, n, -1, (Object)arrby).sendToTarget();
                } while (true);
            }
            catch (IOException var3_3) {
                Log.e((String)"BluetoothChatService", (String)"disconnected", (Throwable)var3_3);
                BluetoothChatService.this.connectionLost();
                try {
                    if (this.mmInStream != null) {
                        this.mmInStream.close();
                    }
                }
                catch (Exception var5_4) {
                    var5_4.printStackTrace();
                }
                try {
                    if (this.mmOutStream != null) {
                        this.mmOutStream.close();
                    }
                }
                catch (Exception var6_5) {
                    var6_5.printStackTrace();
                }
                try {
                    if (this.mmSocket != null) {
                        this.mmSocket.close();
                    }
                }
                catch (Exception var7_6) {
                    var7_6.printStackTrace();
                }
                BluetoothChatService.this.start();
                return;
            }
        }

        public void write(byte[] arrby) {
            try {
                if (this.mmOutStream != null) {
                    this.mmOutStream.write(arrby);
                    if (BluetoothChatService.this.mHandler != null) {
                        BluetoothChatService.this.mHandler.obtainMessage(3, -1, -1, (Object)arrby).sendToTarget();
                    }
                }
                return;
            }
            catch (IOException var2_2) {
                Log.e((String)"BluetoothChatService", (String)"Exception during write", (Throwable)var2_2);
                return;
            }
        }
    }

}

