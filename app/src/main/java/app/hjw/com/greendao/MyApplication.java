package app.hjw.com.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import app.hjw.com.greendao.gen.DaoMaster;
import app.hjw.com.greendao.gen.DaoSession;

/**
 * 作者: hjw on 2017/7/20 15:36
 * 邮箱: 18910207853@163.com
 */

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static MyApplication instances;

    public static MyApplication getInstances() {
        return instances;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDataBase();

    }

    private void setDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        //通过DaoMaster.DevOpenHelper得到SQLiteOpenHelper对象
        db = mHelper.getWritableDatabase();
        //该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
