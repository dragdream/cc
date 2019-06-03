package sense.Living1;

public class LIV_version_info {
	public byte[] reserved;//保留
	public byte[] version;//硬件版本
	public LIV_version_info() {
		version = new byte[3];
		reserved = new byte[4];
	}
}
