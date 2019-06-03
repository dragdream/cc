package sense.Living1;

public class Living1 {
	// 错误码
		public static int LIV_SUCCESS = 0; // 成功
		public static int LIV_OPEN_DEVICE_FAILED = 1; // 打开设备失败
		public static int LIV_FIND_DEVICE_FAILED = 2; // 未找到符合条件的设备
		public static int LIV_INVALID_PARAMETER = 3; // 参数错误
		public static int LIV_INVALID_BLOCK_NUMBER = 4; // 块号错误
		public static int LIV_HARDWARE_COMMUNICATE_ERROR = 5; // 与硬件通信错误
		public static int LIV_INVALID_PASSWORD = 6; // 密码错误
		public static int LIV_ACCESS_DENIED = 7; // 没有权限
		public static int LIV_ALREADY_OPENED = 8; // 设备已经打开
		public static int LIV_ALLOCATE_MEMORY_FAILED = 9; // 内存分配失败
		public static int LIV_INVALID_UPDATE_PACKAGE = 10; // 不合法的升级包
		public static int LIV_SYN_ERROR = 11; // 线程同步错误
		public static int LIV_OTHER_ERROR = 12; // 其它未知异常、错误
		public static int LIV_OPERATION_NOT_SUPPORTED = 13; // 不支持的操作
		public static int LIV_DEVICE_BLOCKED = 14 ;// 设备被锁

		// API 函数
		public native int LIV_open(int vendor, int index, int[] handle);

		public native int LIV_close(int handle);

		public native int LIV_passwd(int handle, int type, byte[] passwd);

		public native int LIV_read(int handle, int block, byte[] buffer);

		public native int LIV_write(int handle, int block, byte[] buffer);

		public native int LIV_encrypt(int handle, byte[] plaintext,
				byte[] ciphertext);

		public native int LIV_decrypt(int handle, byte[] ciphertext,
				byte[] plaintext);

		public native int LIV_set_passwd(int handle, int type, byte[] newpasswd,
				int retries);

		public native int LIV_change_passwd(int handle, int type, byte[] oldpasswd,
				byte[] newpasswd);

		public native int LIV_get_hardware_info(int handle, LIV_hardware_info info);

		public native int LIV_get_software_info(LIV_software_info info);
		
		public native int LIV_get_version_info(int handle ,LIV_version_info info);

		public native int LIV_hmac(int handle, byte[] text, int textlen,
				byte[] digest);

		public native int LIV_hmac_software(byte[] text, int textlen, byte[] key,
				byte[] digest);

		static {
			try {
				System.loadLibrary("living1_java_pkg");
			} catch (UnsatisfiedLinkError e) {
				System.err.println("Cannot find library living1_java_pkg.dll");
				e.printStackTrace();
			}
		}
}
