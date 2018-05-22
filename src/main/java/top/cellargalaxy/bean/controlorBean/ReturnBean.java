package top.cellargalaxy.bean.controlorBean;

/**
 * 用于控制器返回的对象封装
 * Created by cellargalaxy on 17-12-14.
 */
public class ReturnBean {
	private final boolean result;
	private final Object data;

	public ReturnBean(boolean result, Object data) {
		this.result = result;
		this.data = data;
	}

	public boolean isResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return "ReturnBean{" +
				"result=" + result +
				", data=" + data +
				'}';
	}
}
