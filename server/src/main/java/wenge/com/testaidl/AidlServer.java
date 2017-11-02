package wenge.com.testaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by WENGE on 2017/11/2.
 * 备注：
 */


public class AidlServer extends Service {


    private String TAG = "AidlServer";
    private String name;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mPersonStub;
    }

    private AidlServerManage.Stub mStub = new AidlServerManage.Stub() {


        @Override
        public void getString(String s) throws RemoteException {
            AidlServer.this.name = s;
            AidlServer.this.logString(s);
        }

        @Override
        public String setString() throws RemoteException {
            return name + "-出去转了一圈";
        }
    };

    private AidlPersonServerManage.Stub  mPersonStub = new AidlPersonServerManage.Stub() {
        @Override
        public PersonBean getObj() throws RemoteException {
            PersonBean personBean = new PersonBean("张三",23);
            return personBean;
        }

        @Override
        public void setObjIn(PersonBean p) throws RemoteException {
            Log.e(TAG, p.getName() + ":" + p.age);
        }

        @Override
        public PersonBean setObjOut(PersonBean p) throws RemoteException {
            Log.e(TAG, p.getName() + ":" + p.age);
            p.setName("李四");
            p.setAge(24);
            return p;
        }
    };

    private void logString(String str) {
        Log.e(TAG, str);
    }



}
