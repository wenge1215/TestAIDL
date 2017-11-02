package wenge.com.clent;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import wenge.com.testaidl.AidlPersonServerManage;
import wenge.com.testaidl.AidlServerManage;
import wenge.com.testaidl.PersonBean;

public class MainActivity extends AppCompatActivity {

    private AidlServerManage mServerManage;
    private AidlPersonServerManage mPersonServerManage;


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mServerManage = AidlServerManage.Stub.asInterface(service);
            mPersonServerManage = AidlPersonServerManage.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            mServerManage = null;
//            mPersonServerManage = null;
        }
    };
    private String TAG = "client_MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void bind(View view) {

        /**
         * android5.0以后不能使用隐式intent ：需要指定Intent的ComponentName信息：intent.setComponent(xxx)，
         * 或指定Intent的setPackage("包名")，如果两者都没有指定的话将会报以上错误。
         * 尤其在framework层启动APP层的service时，如果是隐式启动service，可能会导致系统进程挂掉，出现不断重启的现象.
         *
         * 可通过以下两种方式进行隐式启动
         */

        /**
         * 隐式启动方式一
         * 创建componentName（server所在的包，server的全类名）
         * 设置Component
         *
         */

//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName("wenge.com.testaidl", "wenge.com.testaidl.AidlServer");
//        intent.setComponent(componentName);
//        startService(intent);
//
//        bindService(intent, mConnection, BIND_AUTO_CREATE);


        /**
         * 隐式启动方式二
         * 1. 设置packageName（要启动的server所在的包名，不要通过getPackageName()获取）
         * 2. 设置Action （不要忘了在manifest文件中注册server，并添加action）
         */
        Intent intent = new Intent();
        intent.setAction("wenge.com.testaidl.action");
        intent.setPackage("wenge.com.testaidl");
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

//mServerManage
        if (mPersonServerManage == null) {
            Toast.makeText(this, "绑定失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "绑定成功", Toast.LENGTH_SHORT).show();
        }

    }


    public void welcome(View view) {
        Log.e(TAG, "welcome");
        wcObject();
//        wcString();
    }

    private void wcObject() {
        if (mPersonServerManage != null) {
            try {
                PersonBean obj = mPersonServerManage.getObj();
                Toast.makeText(this, obj.getName() + "大佬，年方" + obj.getAge(), Toast.LENGTH_SHORT).show();

                mPersonServerManage.setObjIn(new PersonBean("王五", 25));
                PersonBean personBean = mPersonServerManage.setObjOut(new PersonBean("赵六", 26));
                Log.e(TAG, "赵六出去转了一趟，回来就改名：" + personBean.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "创建连接失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "尚未创建连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void wcString() {
        if (mServerManage != null) {
            try {
                mServerManage.getString("张三");
                String s = mServerManage.setString();
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "创建连接失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "尚未创建连接", Toast.LENGTH_SHORT).show();
        }
    }
}
