package com.example.administrator.optionmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

//import android.support.v7.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    //视图
    ListView listView;

    //数据
    String[] data = {"a","b","v","k","m"};

    //适配器
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setMultiChoiceModeListener(new McListener());
    }

    /**
     * ListView的
     * 多选模式--监听器
     */
    class McListener implements AbsListView.MultiChoiceModeListener{

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            int count = listView.getCheckedItemCount();
            mode.setTitle(String.valueOf(count));
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.file_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            //获取选中的项，返回一个稀疏数组
            SparseBooleanArray arr = listView.getCheckedItemPositions();
            Toast.makeText(MainActivity.this,arr.toString(),Toast.LENGTH_SHORT);

            switch (item.getItemId()){
                case R.id.action_remove:
                    mode.finish();
                    break;
                case R.id.action_edit:

                    break;
                case R.id.action_copy:
                    break;
                default:break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    }
    //****************************************************************************


    /**
     * 创建可选菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载[选项菜单的]布局文件
        getMenuInflater().inflate(R.menu.main_opt_menu, menu);

        return true;
    }


    /**
     * 菜单被选中
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getGroupId() == R.id.group_sort){
            //单选--排序组
            item.setChecked(true);
        }

        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(MainActivity.this,"Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:

                break;

            default:break;
        }
        return true;
    }


    /**
     * 预处理
     * 设置某些菜单项的属性：可见性，是否可选中
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        menu.findItem(R.id.action_about).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(isShowSort);

        //此处注释，测试时support.v7.widget.SearchView不可用
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
////        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint("Search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    boolean isShowSort = true;

    /**
     * Button按钮事件
     * 改变菜单--点击Button时执行
     * @param view
     */
    public void changeMenu(View view){
        isShowSort = !isShowSort;

        //重新绘制
        invalidateOptionsMenu();
    }



    /**
     * 显示弹出菜单
     * 点击ImageButton时执行
     * @param view
     */
    public void showPopMenu(View view){
        //点击ImageButton---显示弹出菜单
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);

        popupMenu.inflate(R.menu.file_action);//加载布局
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /**
             *
             * @param item
             * @return true 事件处理完毕（已消废）  false 继续传递
             */
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_remove:
                        doRemove();
                        break;
                    case R.id.action_edit:
                        doEdit();
                        break;
                    case R.id.action_copy:
                        doCopy();
                        break;
                }
                return true;
            }

            private void doCopy() {
                showToast("copy");
            }

            private void doEdit() {
                showToast("edit");
            }

            private void doRemove() {
                showToast("remove");
            }

        });
        popupMenu.show();
    }


    /**
     * 不中断用户操作
     * 将Toast.makeText()参数化
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
