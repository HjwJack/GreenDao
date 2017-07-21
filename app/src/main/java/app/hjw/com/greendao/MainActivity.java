package app.hjw.com.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import app.hjw.com.greendao.bean.User;
import app.hjw.com.greendao.gen.DaoSession;
import app.hjw.com.greendao.gen.UserDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private UserDao userDao;
    private DaoSession daoSession;
    private Button btn_insert, btn_update, btn_delete, btn_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_query = (Button) findViewById(R.id.btn_query);

        btn_insert.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        daoSession = MyApplication.getInstances().getmDaoSession();
        userDao = daoSession.getUserDao();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                insertData();
                break;
            case R.id.btn_update:
                updateData(1);
                break;
            case R.id.btn_delete:
                deleteData(10);
                break;
            case R.id.btn_query:
                queryAll();
                break;
        }
    }

    private void queryByName() {
        User hjw = userDao.queryBuilder().where(UserDao.Properties.Name.eq("hjw")).unique();
        Log.d(TAG, "queryByName: " + "  name" + hjw.getName() + "  age" + hjw.getAge() + "  id" + hjw.getId());
    }


    private void queryAll() {
        try {
            List<User> users = userDao.loadAll();
            for (User u : users) {
                Log.d(TAG, "queryAll: "
                        + "  id:" + u.getId()
                        + "  name" + u.getName()
                        + "  age" + u.getAge());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteDataAll() {
        userDao.deleteAll();
    }


    private void deleteDataById(long id) {
        userDao.deleteByKey(id);
    }

    private void deleteData(long id) {
        List<User> userList = null;
        try {
            userList = (List<User>) userDao.queryBuilder().where(UserDao.Properties.Id.le(id)).build().list();
            for (User user : userList) {
                userDao.delete(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateData(long id) {

        try {
            User user = new User(id, "hxn", 18);
            userDao.update(user);
            Log.d(TAG, "updateData: " + user.getName());
        } catch (Exception e) {
            Log.d(TAG, "updateData failed" + e.getMessage());
        }

    }


    private void insertData() {
        User user = new User();
        try {
            user.setName("hjw");
            user.setAge(23);
            userDao.insert(user);
            Log.d(TAG, "insertData: " + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
