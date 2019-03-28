package com.joybar.compiler.data;

public class RouterModule {
	public String classFullName = "";
	public String module = "";
	public String path = "";
	public boolean isAutoRegistered = false;

	public RouterModule(String classFullName, String module, String path, boolean isAutoRegistered) {
		this.classFullName = classFullName;
		this.module = module;
		this.path = path;
		this.isAutoRegistered = isAutoRegistered;
	}

	@Override
	public String toString() {
		return "RouterModule{" + "classFullName='" + classFullName + '\'' + ", module='" + module + '\'' + ", path='" + path + '\'' + ", " +
				"isAutoRegistered=" + isAutoRegistered + '}';
	}
}
