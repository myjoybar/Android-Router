package com.joybar.moduleshop;

import com.joybar.librouter.routerservice.exception.RouterServiceException;
import com.joybar.librouter.routerservice.inters.IBaseService;
import com.joybar.librouter.routerservice.inters.IServiceCallBack;

import java.util.Arrays;

public class ShopService implements IBaseService {
	private static final String SERVICE_CMD_TEST = "cmd_test";
	private static final String SERVICE_CMD_LOGIN = "cmd_login";

	@Override
	public Object execute(String cmd, Object... args) {
		Object obj[] = args;
		String msg = "";
		if (obj != null && obj.length != 0) {
			msg = Arrays.toString(obj);
		}
		if (SERVICE_CMD_TEST.equals(cmd)) {
			return "成功同步调用 Shop service " + SERVICE_CMD_TEST + "服务，返回值：" + msg;
		}
		return null;
	}

	@Override
	public void executeAsync(String cmd, IServiceCallBack iServiceCallBack, Object... args) {

		if (SERVICE_CMD_LOGIN.equals(cmd)) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if ("Tom".equals(args[0]) && "123456".equals(args[1])) {
				iServiceCallBack.onSuccess("登录成功");
			} else {
				iServiceCallBack.onFailure(new RouterServiceException("用户名或者密码错误"));
			}

		}
	}

	@Override
	public String moduleServiceName() {

		return "RSShopService";
	}
}
