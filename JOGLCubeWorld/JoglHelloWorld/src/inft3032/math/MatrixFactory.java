package inft3032.math;


public class MatrixFactory {
	
	/**
	 * Constructs a perspective projection matrix.
	 * 
	 * This re-implements gluPerspective which has been deprecated.
	 * 
	 * @param fovY   Vertical field of view, in degrees (DEGREES)
	 * @param aspect Aspect ratio of the display
	 * @param zNear  The distance of the near plane
	 * @param zFar   The distance of the far plane
	 * 
	 * @return A perspective projection matrix.
	 */
	public static Matrix4 perspective(float fovY, float aspect, float zNear, float zFar) {
		
		float f = (float) (1.0 / Math.tan(Math.toRadians(fovY)/2.0));
		float m22 = (zFar + zNear) / (zNear - zFar);
		float m23 = (2 * zFar * zNear) / (zNear - zFar);

		Matrix4 m = new Matrix4(f/aspect, 0,   0,   0,
				                       0, f,   0,   0,
				                       0, 0, m22, m23,
				                       0, 0,  -1,   0);
		
		return m;
	}


	/**
	 * Returns a view matrix. This is equivalent to gluLookAt.
	 * 
	 * @param eye The position of the eye
	 * @param poi The point of interest (what we are looking at)
	 * @param up The up vector.
	 * @return A view matrix.
	 */
	public static Matrix4 lookInDirection(Vector3 eye, Vector3 viewDir, Vector3 up) {
		Vector3 viewUp = up.subtract(viewDir.mult(up.dot(viewDir))).unit();
		Vector3 viewSide = viewDir.cross(viewUp);
		Vector4 zeros = new Vector4(0,0,0,1);
		
		Matrix4 rotation = new Matrix4(viewSide.toDir(), viewUp.toDir(), viewDir.negate().toDir(), zeros);
		
		Vector4 eyeInv = rotation.mult(eye.toPos()).negate();
		
		Matrix4 m = new Matrix4(viewSide.getX(), viewSide.getY(), viewSide.getZ(), eyeInv.getX(),
		                          viewUp.getX(),   viewUp.getY(),   viewUp.getZ(), eyeInv.getY(),
		                         -viewDir.getX(),  -viewDir.getY(),  -viewDir.getZ(), eyeInv.getZ(),
		                                      0,               0,               0,             1);
		
		return m;
	}
	
	/**
	 * Returns a view matrix. This is equivalent to gluLookAt.
	 * 
	 * @param eye The position of the eye
	 * @param poi The point of interest (what we are looking at)
	 * @param up The up vector.
	 * @return A view matrix.
	 */
	public static Matrix4 lookAt(Vector3 eye, Vector3 poi, Vector3 up) {
		Vector3 viewDir = poi.subtract(eye).unit();
		Vector3 viewUp = up.subtract(viewDir.mult(up.dot(viewDir))).unit();
		Vector3 viewSide = viewDir.cross(viewUp);
		Vector4 zeros = new Vector4(0,0,0,1);
		
		Matrix4 rotation = new Matrix4(viewSide.toDir(), viewUp.toDir(), viewDir.negate().toDir(), zeros);
		
		Vector4 eyeInv = rotation.mult(eye.toPos()).negate();
		
		Matrix4 m = new Matrix4(viewSide.getX(), viewSide.getY(), viewSide.getZ(), eyeInv.getX(),
		                          viewUp.getX(),   viewUp.getY(),   viewUp.getZ(), eyeInv.getY(),
		                         -viewDir.getX(),  -viewDir.getY(),  -viewDir.getZ(), eyeInv.getZ(),
		                                      0,               0,               0,             1);
		
		return m;
	}
	
	
	public static Matrix4 translate(Vector3 translation) {
		return new Matrix4(1, 0, 0, translation.getX(),
		                   0, 1, 0, translation.getY(),
		                   0, 0, 1, translation.getZ(),
		                   0, 0, 0, 1);
		
	}
}
