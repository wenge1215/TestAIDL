// AidlPersonServerManage.aidl
package wenge.com.testaidl;
import wenge.com.testaidl.PersonBean;

interface AidlPersonServerManage {
    PersonBean getObj();

    void setObjIn(in PersonBean p);     //客户端->服务端
    PersonBean setObjOut(out PersonBean p);  //服务端->客户端


}
