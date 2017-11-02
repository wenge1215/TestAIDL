// AidlPersonServerManage.aidl
package wenge.com.testaidl;
import wenge.com.testaidl.PersonBean;

interface AidlPersonServerManage {
    PersonBean getObj();

    void setObjIn(in PersonBean p);
    PersonBean setObjOut(out PersonBean p);


}
