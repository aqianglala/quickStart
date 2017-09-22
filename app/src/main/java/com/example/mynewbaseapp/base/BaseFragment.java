package com.example.mynewbaseapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mynewbaseapp.mvp.IView;
import com.example.mynewbaseapp.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment implements
        IView {
    public String TAG;
    protected View mContentView;
    protected Activity mActivity;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        mActivity = getActivity();
        mPresenter = loadPresenter();
        attachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != getArguments()) {
            handleArguments(getArguments());
        }
        // 避免多次从xml中加载布局文件
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, mContentView);
            initInjector();
            initView(savedInstanceState);
            setListener();
            doBusiness(savedInstanceState);
        } else {
            toast(TAG + "多次加载布局了");
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        return mContentView;
    }

    protected void handleArguments(Bundle arguments) {
    }

    private void attachView() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int getLayoutId();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P loadPresenter();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务
     *
     * @param savedInstanceState
     */
    protected abstract void doBusiness(Bundle savedInstanceState);

    public void toast(String str) {
        ToastUtils.showShort(mActivity, str);
    }

    public void toast(int contentId) {
        ToastUtils.showShort(mActivity, contentId);
    }

    public void logI(String str) {
        Logger.t(TAG).i(str);
    }

    /********************************* rxlifecycle ********************************/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();// rx 生命周期管理
            mPresenter.detachView();
        }
    }
}