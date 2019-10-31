package inft3032.math;


public class Quaternion {
	
	private final float w,x,y,z;
	
	
	public Quaternion() {
		x = 0;
		y = 0;
		z = 0;
		w = 1;
	}
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector3 axis, float angle) {
		Vector3 v= axis.unit();
		float scale = (float) Math.sin(angle/2.0);
		w = (float) Math.cos(angle/2.0);
		x = v.getX() * scale;
		y = v.getY() * scale;
		z = v.getZ() * scale;
	}
	
	public Quaternion(Matrix4 m) {
		this.w = (float) Math.sqrt(Math.max(0.0, 1 + m.get(0, 0) + m.get(1,1) + m.get(2, 2)) / 2.0);
		this.x = (float) Math.copySign(Math.sqrt(Math.max(0.0, 1 + m.get(0, 0) - m.get(1,1) - m.get(2, 2)) / 2.0), m.get(2, 1) - m.get(1, 2));
		this.y = (float) Math.copySign(Math.sqrt(Math.max(0.0, 1 - m.get(0, 0) + m.get(1,1) - m.get(2, 2)) / 2.0), m.get(0, 2) - m.get(2, 0));
		this.z = (float) Math.copySign(Math.sqrt(Math.max(0.0, 1 - m.get(0, 0) - m.get(1,1) + m.get(2, 2)) / 2.0), m.get(1, 0) - m.get(0, 1));
	}
	
	
	public Matrix4 getRotation() {
		float s, xs, ys, zs, wx, wy, wz, xx, xy, xz, yy, yz, zz;
		s = 2.0f/(x*x + y*y + z*z + w*w);

		xs = s*x;	ys = s*y;	zs = s*z;
		wx = w*xs;	wy = w*ys;	wz = w*zs;
		xx = x*xs;	xy = x*ys;	xz = x*zs;
		yy = y*ys;	yz = y*zs;	zz = z*zs;

		Matrix4 mat = new Matrix4(1.0f - (yy+zz), xy - wz, xz + wy, 0,
				                  xy + wz, 1.0f - (xx + zz), yz - wx, 0,
				                  xz - wy, yz + wx, 1.0f - (xx + yy), 0,
				                  0,0,0,1);

		return mat;
	}
	
	
	
	public Quaternion normalise() {
		float imag = (float)  (1.0 / Math.sqrt(w*w + x*x + y*y + z*z));
		return new Quaternion(x * imag, y * imag, z * imag, w * imag);
	}
}

