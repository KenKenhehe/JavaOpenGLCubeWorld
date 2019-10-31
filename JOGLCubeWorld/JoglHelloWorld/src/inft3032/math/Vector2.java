package inft3032.math;


/**
 * A 2 element vector.
 * 
 * @author Michael Marner <michael.marner@unisa.edu.au>
 *
 */
public final class Vector2 {
	
	private final float x,y;
	
	/**
	 * Constructs a vector with all values equal to zero.
	 */
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructs a vector using the values specified
	 * 
	 * @param x The X component 
	 * @param y The Y component
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	/**
	 * Constructs a vector with the same values as those in the parameter.
	 * 
	 * If this was C++ we would call this a copy constructor.
	 * 
	 * @param v The vector to copy.
	 */
	public Vector2(final Vector2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	
	/**
	 * Adds two vectors together.
	 * 
	 * @param rhs The vector to add to this one
	 * @return A new vector equal to (this + rhs)
	 */
	public Vector2 plus(final Vector2 rhs) {
		return new Vector2(this.x + rhs.x,
				           this.y + rhs.y);
	}

	
	/**
	 * Calculates the dot product of two vectors.
	 * 
	 * @param rhs The vector to dot with this one.
	 * @return The dot product of the two vectors.
	 */
	public float dot(final Vector2 rhs) {
		return (this.x * rhs.x + this.y * rhs.y);
	}
	
	
	/**
	 * Compares two vectors for equality.
	 * 
	 * @param rhs The vector to compare to this vector.
	 * @return True if the two vectors are equal (all components equal)
	 */
	public boolean equals(final Vector2 rhs) {
		if (this == rhs) {
			return true;
		}
		return this.x == rhs.x && this.y == rhs.y;
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
	 * Calculates the length of this vector.
	 * @return The length of the vector.
	 */
	public float length() {
		return (float) Math.sqrt(x*x + y*y);
	}

	public float lengthSquared() {
		return x*x + y*y;
	}
	
	/**
	 * Multiplies this vector with the parameter.
	 * @param rhs The vector to multiply.
	 * @return A new vector containing the result.
	 */
	public Vector2 mult(final Vector2 rhs) {
		return new Vector2(this.x * rhs.x,
				          this.y * rhs.y);
	}
	
	/**
	 * Returns the negated version of this vector (reverses the direction).
	 * @return A new vector with all values negated.
	 */
	public Vector2 negate() {
		return this.mult(-1);
	}
	
	
	/**
	 * Multiplies this vector by a scale value.
	 * @param s The scalar to multiply
	 * @return A new vector which has been scaled.
	 */
	public Vector2 mult(float s) {
		return new Vector2(x * s, y * s);
	}
	
	/**
	 * Subtracts rhs from this vector and returns the result as a new vector.
	 * @param rhs The vector to subtract.
	 * @return A new vector containing the result of the subtraction.
	 */
	public Vector2 subtract(final Vector2 rhs) {
		return new Vector2(this.x - rhs.x,
				           this.y - rhs.y);
	}
	
	/**
	 * Returns a new vector of length=1
	 * @return a new vector of length=1
	 */
	public Vector2 unit() {
		float length = this.length();
		return this.mult(1.0f/length);
	}
	
	
	/**
	 * Returns the angle between this vector and rhs
	 * 
	 * @param rhs The other vector.
	 * @return The angle between the vectors, in radians.
	 */
	public float angle(Vector2 rhs) {
		Vector2 v1 = this.unit();
		Vector2 v2 = rhs.unit();
		return (float) Math.acos(v1.dot(v2));
	}
}
