package com.joybar.librouter.routerservice.inters;

public interface IBaseService {

	Object execute(String cmd, Object... args);

	void executeAsync(String cmd, IServiceCallBack iServiceCallBack, Object... args);

	String moduleServiceName();

}
