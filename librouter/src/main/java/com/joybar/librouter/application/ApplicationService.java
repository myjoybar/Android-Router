package com.joybar.librouter.application;

import android.app.Application;

/**
 * Created by joybar on 03/12/2017.
 */

public interface ApplicationService {
    void loadModuleApplicationService(); //初始化工作
    Application getApplication(); //获取主APP的Application或者module在debug时自己的application
}
