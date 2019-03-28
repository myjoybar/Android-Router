package com.joybar.librouter.routerservice.inters;

import com.joybar.librouter.routerservice.exception.RouterServiceException;

public interface IServiceCallBack {

	void onSuccess(Object result);
	void onFailure(RouterServiceException routerServiceException);

}
