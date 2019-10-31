package inft3032.math;


/**
 * A 4 element vector suitable for multiplying with Matrix4 objects.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public final class Vector4 {
	
	private final float x,y,z,w;
	
	/**
	 * Constructs a vector with all values equal to zero.
	 */
	public Vector4() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}
	
	/**
	 * Constructs a vector using the values specified
	 * 
	 * @param x The X component 
	 * @param y The Y component
	 * @param z The Z component
	 */
	public Vector4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Constructs a vector with the same values as those in the parameter.
	 * 
	 * If this was C++ we would call this a copy constructor.
	 * 
	 * @param v The vector to copy.
	 */
	public Vector4(final Vector4 v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}
	
	
	/**
	 * Adds two vectors together.
	 * 
	 * @param rhs The vector to add to this one
	 * @return A new vector equal to (this + rhs)
	 */
	public Vector4 plus(final Vector4 rhs) {
		return new Vector4(this.x + rhs.x,
				           this.y + rhs.y,
				           this.z + rhs.z,
				           this.w + rhs.w);
	}

	
	/**
	 * Calculates the dot product of two vectors.
	 * 
	 * @param rhs The vector to dot with this one.
	 * @return The dot product of the two vectors.
	 */
	public float dot(final Vector4 rhs) {
		return (this.x * rhs.x + this.y * rhs.y + this.z * rhs.z + this.w * rhs.w);
	}
	
	
	/**
	 * Compares two vectors for equality.
	 * 
	 * @param rhs The vector to compare to this vector.
	 * @return True if the two vectors are equal (all components equal)
	 */
	public boolean equals(final Vector4 rhs) {
		if (this == rhs) {
			return true;
		}
		return this.x == rhs.x && this.y == rhs.y && this.z == rhs.z && this.w == rhs.w;
	}


	public float angle(Vector4 rhs) {
		Vector4 v1 = this.unit();
		Vector4 v2 = rhs.unit();
		return (float) Math.acos(v1.dot(v2));
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
	 * Returns the W component of the vector.
	 * @return The W component of the vector.
	 */
	public float getW() {
		return w;
	}
	

	/**
	 * Calculates the length of this vector.
	 * @return The length of the vector.
	 */
	public float length() {
		return (float) Math.sqrt(x*x + y*y + z*z + w*w);
	}

	
	/**
	 * Multiplies this vector with the parameter.
	 * @param rhs The vector to multiply.
	 * @return A new vector containing the result.
	 */
	public Vector4 mult(final Vector4 rhs) {
		return new Vector4(this.x * rhs.x,
				          this.y * rhs.y,
				          this.z * rhs.z,
				          this.w * rhs.w);
	}
	
	/**
	 * Returns the negated version of this vector (reverses the direction).
	 * @return A new vector with all values negated.
	 */
	public Vector4 negate() {
		return this.mult(-1);
	}
	
	
	/**
	 * Multiplies this vector by a scale value.
	 * @param s The scalar to multiply
	 * @return A new vector which has been scaled.
	 */
	public Vector4 mult(float s) {
		return new Vector4(x * s, y * s, z * s, w * s);
	}
	
	/**
	 * Subtracts rhs from this vector and returns the result as a new vector.
	 * @param rhs The vector to subtract.
	 * @return A new vector containing the result of the subtraction.
	 */
	public Vector4 subtract(final Vector4 rhs) {
		return new Vector4(this.x - rhs.x,
				           this.y - rhs.y,
				           this.z - rhs.z,
				           this.w - rhs.w);
	}
	
	/**
	 * Returns a new vector of length=1
	 * @return a new vector of length=1
	 */
	public Vector4 unit() {
		float length = this.length();
		return this.mult(1.0f/length);
	}
	
	/**
	 * Returns a Vector3 representation of this vector.
	 * Will divide the x,y,z values by 0 before returning.
	 */
	public Vector3 toVector3() {
		if (w != 0)
			return new Vector3(x/w, y/w, z/w);
		else
			return new Vector3(x, y, z);
	}
	
}
