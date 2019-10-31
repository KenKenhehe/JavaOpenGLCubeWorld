package inft3032.math;


/**
 * A 3 element vector.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public final class Vector3 {
	
	private final float x,y,z;
	
	/**
	 * Constructs a vector with all values equal to zero.
	 */
	public Vector3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * Constructs a vector using the values specified
	 * 
	 * @param x The X component 
	 * @param y The Y component
	 * @param z The Z component
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public Vector3(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}
	

	/**
	 * Constructs a vector with the same values as those in the parameter.
	 * 
	 * If this was C++ we would call this a copy constructor.
	 * 
	 * @param v The vector to copy.
	 */
	public Vector3(final Vector3 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	
	/**
	 * Adds two vectors together.
	 * 
	 * @param rhs The vector to add to this one
	 * @return A new vector equal to (this + rhs)
	 */
	public Vector3 plus(final Vector3 rhs) {
		return new Vector3(this.x + rhs.x,
				           this.y + rhs.y,
				           this.z + rhs.z);
	}

	/**
	 * Performs a cross product on two vectors and returns the result
	 * 
	 * @param rhs The vector to cross product with this one.
	 * @return A new vector equal to (this X rhs)
	 */
	public Vector3 cross(final Vector3 rhs) {
		return new Vector3(this.y * rhs.z - rhs.y * this.z,
				           this.z * rhs.x - rhs.z * this.x,
				           this.x * rhs.y - rhs.x * this.y);
	}
	
	
	/**
	 * Calculates the dot product of two vectors.
	 * 
	 * @param rhs The vector to dot with this one.
	 * @return The dot product of the two vectors.
	 */
	public float dot(final Vector3 rhs) {
		return (this.x * rhs.x + this.y * rhs.y + this.z * rhs.z);
	}
	
	
	/**
	 * Compares two vectors for equality.
	 * 
	 * @param rhs The vector to compare to this vector.
	 * @return True if the two vectors are equal (all components equal)
	 */
	public boolean equals(final Vector3 rhs) {
		if (this == rhs) {
			return true;
		}
		return this.x == rhs.x && this.y == rhs.y && this.z == rhs.z;
	}
	
	
	
	/**
	 * Returns the X component of the vector.
	 * @return The X component of the vector.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the Y component of the vector.
	 * @return The Y component of the vector.
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Returns the Z component of the vector.
	 * @return The Z component of the vector.
	 */
	public float getZ() {
		return z;
	}
	

	/**
	 * Calculates the length of this vector.
	 * @return The length of the vector.
	 */
	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}

	
	/**
	 * Multiplies this vector with the parameter.
	 * @param rhs The vector to multiply.
	 * @return A new vector containing the result.
	 */
	public Vector3 mult(final Vector3 rhs) {
		return new Vector3(this.x * rhs.x,
				          this.y * rhs.y,
				          this.z * rhs.z);
	}
	
	/**
	 * Returns the negated version of this vector (reverses the direction).
	 * @return A new vector with all values negated.
	 */
	public Vector3 negate() {
		return this.mult(-1);
	}
	
	
	/**
	 * Multiplies this vector by a scale value.
	 * @param s The scalar to multiply
	 * @return A new vector which has been scaled.
	 */
	public Vector3 mult(float s) {
		return new Vector3(x * s, y * s, z * s);
	}
	
	/**
	 * Subtracts rhs from this vector and returns the result as a new vector.
	 * @param rhs The vector to subtract.
	 * @return A new vector containing the result of the subtraction.
	 */
	public Vector3 subtract(final Vector3 rhs) {
		return new Vector3(this.x - rhs.x,
				           this.y - rhs.y,
				           this.z - rhs.z);
	}
	
	/**
	 * Returns a new vector of length=1
	 * @return a new vector of length=1
	 */
	public Vector3 unit() {
		float length = this.length();
		return this.mult(1.0f/length);
	}
	
	
	/**
	 * Returns the angle between this vector and rhs
	 * 
	 * @param rhs The other vector.
	 * @return The angle between the vectors, in radians
	 */
	public float angle(Vector3 rhs) {
		Vector3 v1 = this.unit();
		Vector3 v2 = rhs.unit();
		return (float) Math.acos(v1.dot(v2));
	}
	
	/**
	 * Creates a Vector4 from this vector, with w=0.
	 */
	public Vector4 toDir() {
		return new Vector4(x,y,z,0);
	}

	/**
	 * Creates a Vector4 from this vector, with w=1.
	 */
	public Vector4 toPos() {
		return new Vector4(x,y,z,1);
	}

	/**
	 * Returns the squared length of the vector
	 */
	public float lengthSquared() {
		return x*x + y*y + z*z;
	}
	
	public String toString() {
		return new String("(" + x + ", " + y + ", " + z + ")");
	}
	
}
