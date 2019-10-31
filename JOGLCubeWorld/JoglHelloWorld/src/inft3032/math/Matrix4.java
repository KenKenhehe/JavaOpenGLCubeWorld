package inft3032.math;



/**
 * A class for 4x4 matrices for afine transformations
 * <p/>
 * Created by a.sobey on 28/03/2004 at 16:33:09
 *
 * @author a.sobey
 * @version 1.0
 */
public final class Matrix4 {

	/*
	 First rowa: m00 m01 m02 m03
	 Second row: m10 m11 m12 m13
	 Third row:  m20 m21 m22 m23
	 Fourth row: m30 m31 m32 m33
	 */
	private final float m00, m10, m20, m30,
			            m01, m11, m21, m31,
			            m02, m12, m22, m32,
			            m03, m13, m23, m33;


	/**
	 * Default constructor - creates a 4x4 identity matrix.
	 */
	public Matrix4() {
		// column 1
		m00 = 1.0f;
		m10 = 0.0f;
		m20 = 0.0f;
		m30 = 0.0f;
		// column 2
		m01 = 0.0f;
		m11 = 1.0f;
		m21 = 0.0f;
		m31 = 0.0f;
		// column 3
		m02 = 0.0f;
		m12 = 0.0f;
		m22 = 1.0f;
		m32 = 0.0f;
		// column 4
		m03 = 0.0f;
		m13 = 0.0f;
		m23 = 0.0f;
		m33 = 1.0f;
	}


	/**
	 * A 'copy' constructor - make a new Matrix object that is a copy of an existing Matrix object.
	 *
	 * @param m The Matrix object to copy
	 */
	public Matrix4(Matrix4 m) {
		m00 = m.m00; // row 1
		m01 = m.m01;
		m02 = m.m02;
		m03 = m.m03;
		m10 = m.m10; // row 2
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m20 = m.m20; // row 3
		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
		m30 = m.m30; // row 4
		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
	}
	
	public Matrix4(Vector4 row0, Vector4 row1, Vector4 row2, Vector4 row3) {
		m00 = row0.getX();
		m01 = row0.getY();
		m02 = row0.getZ();
		m03 = row0.getW();

		m10 = row1.getX();
		m11 = row1.getY();
		m12 = row1.getZ();
		m13 = row1.getW();

		m20 = row2.getX();
		m21 = row2.getY();
		m22 = row2.getZ();
		m23 = row2.getW();

		m30 = row3.getX();
		m31 = row3.getY();
		m32 = row3.getZ();
		m33 = row3.getW();
	}

	/**
	 * A constructor to create a new Matrix object from 16 float values.
	 *
	 * @param m00 value for row 0 column 0
	 * @param m01 value for row 0 column 1
	 * @param m02 value for row 0 column 2
	 * @param m03 value for row 0 column 3
	 * @param m10 value for row 1 column 0
	 * @param m11 value for row 1 column 1
	 * @param m12 value for row 1 column 2
	 * @param m13 value for row 1 column 3
	 * @param m20 value for row 2 column 0
	 * @param m21 value for row 2 column 1
	 * @param m22 value for row 2 column 2
	 * @param m23 value for row 2 column 3
	 * @param m30 value for row 3 column 0
	 * @param m31 value for row 3 column 1
	 * @param m32 value for row 3 column 2
	 * @param m33 value for row 3 column 3
	 */
	public Matrix4(float m00, float m01, float m02, float m03,
			       float m10, float m11, float m12, float m13,
			       float m20, float m21, float m22, float m23,
			       float m30, float m31, float m32, float m33) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03; // row 1
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13; // row 2
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23; // row 3
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33; // row 4
	}


	/**
	 * A method to return the string 'value' for this Matrix object.
	 *
	 * @return A string representation of the matrix
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();

		buf.append(m00).append(' ').append(m01).append(' ').append(m02).append(' ').append(m03).append(
				'\n');
		buf.append(m10).append(' ').append(m11).append(' ').append(m12).append(' ').append(m13).append(
				'\n');
		buf.append(m20).append(' ').append(m21).append(' ').append(m22).append(' ').append(m23).append(
				'\n');
		buf.append(m30).append(' ').append(m31).append(' ').append(m32).append(' ').append(m33).append(
				'\n');
		return buf.toString();
	}


	/**
	 * Retun the value of the matrix at a specified row and column
	 * 
	 * @param row
	 *            The row index (in range 0 .. 3).;
	 * @param column
	 *            The column index (in range 0 .. 3).
	 * @return The value at given row and column indices.
	 */
	public float get(int row, int column) {
		if (row == 0) {
			if (column == 0) {
				return m00;
			} else if (column == 1) {
				return m01;
			} else if (column == 2) {
				return m02;
			} else if (column == 3) {
				return m03;
			}
		} 
		else if (row == 1) {
			if (column == 0) {
				return m10;
			} else if (column == 1) {
				return m11;
			} else if (column == 2) {
				return m12;
			} else if (column == 3) {
				return m13;
			}
		} 
		else if (row == 2) {
			if (column == 0) {
				return m20;
			} else if (column == 1) {
				return m21;
			} else if (column == 2) {
				return m22;
			} else if (column == 3) {
				return m23;
			}
		} 
		else if (row == 3) {
			if (column == 0) {
				return m30;
			} else if (column == 1) {
				return m31;
			} else if (column == 2) {
				return m32;
			} else if (column == 3) {
				return m33;
			}
		}
		throw new MathsException("row must be 0 to 3 and is " + row);
	}


	/**
	 * Add a matrix to this and return this.
	 *
	 * @param right The right matrix in the addition.
	 * @return the sum of this and the matrix right.
	 */
	public Matrix4 plus(Matrix4 rhs) {
		return new Matrix4 (m00 + rhs.m00,
		                    m01 + rhs.m01,
		                    m02 + rhs.m02,
		                    m03 + rhs.m03,
		                    m10 + rhs.m10,
		                    m11 + rhs.m11,
		                    m12 + rhs.m12,
		                    m13 + rhs.m13,
		                    m20 + rhs.m20,
		                    m21 + rhs.m21,
		                    m22 + rhs.m22,
		                    m23 + rhs.m23,
		                    m30 + rhs.m30,
		                    m31 + rhs.m31,
		                    m32 + rhs.m32,
		                    m33 + rhs.m33);
	}


	/**
	 * Return the this matrix after subtracting the right matrix from this.
	 *
	 * @param right The matrix to subtract from this.
	 * @return The matrix equal to this minus right.
	 */
	public Matrix4 subtract(Matrix4 rhs) {
		return new Matrix4 (m00 - rhs.m00,
		                    m01 - rhs.m01,
		                    m02 - rhs.m02,
		                    m03 - rhs.m03,
		                    m10 - rhs.m10,
		                    m11 - rhs.m11,
		                    m12 - rhs.m12,
		                    m13 - rhs.m13,
		                    m20 - rhs.m20,
		                    m21 - rhs.m21,
		                    m22 - rhs.m22,
		                    m23 - rhs.m23,
		                    m30 - rhs.m30,
		                    m31 - rhs.m31,
		                    m32 - rhs.m32,
		                    m33 - rhs.m33);
	}


	/**
	 * Multiply this by a matrix, store in this and return this
	 *
	 * @param right The right hand matrix in the multiplication
	 * @return the matrix after multiplication by right
	 */
	public Matrix4 mult(Matrix4 rhs) {
		return new Matrix4 (
				m00 * rhs.m00 + m01 * rhs.m10 + m02 * rhs.m20 + m03 * rhs.m30,
				m00 * rhs.m01 + m01 * rhs.m11 + m02 * rhs.m21 + m03 * rhs.m31,
				m00 * rhs.m02 + m01 * rhs.m12 + m02 * rhs.m22 + m03 * rhs.m32,
				m00 * rhs.m03 + m01 * rhs.m13 + m02 * rhs.m23 + m03 * rhs.m33,

				m10 * rhs.m00 + m11 * rhs.m10 + m12 * rhs.m20 + m13 * rhs.m30,
				m10 * rhs.m01 + m11 * rhs.m11 + m12 * rhs.m21 + m13 * rhs.m31,
				m10 * rhs.m02 + m11 * rhs.m12 + m12 * rhs.m22 + m13 * rhs.m32,
				m10 * rhs.m03 + m11 * rhs.m13 + m12 * rhs.m23 + m13 * rhs.m33,

				m20 * rhs.m00 + m21 * rhs.m10 + m22 * rhs.m20 + m23 * rhs.m30,
				m20 * rhs.m01 + m21 * rhs.m11 + m22 * rhs.m21 + m23 * rhs.m31,
				m20 * rhs.m02 + m21 * rhs.m12 + m22 * rhs.m22 + m23 * rhs.m32,
				m20 * rhs.m03 + m21 * rhs.m13 + m22 * rhs.m23 + m23 * rhs.m33,

				m30 * rhs.m00 + m31 * rhs.m10 + m32 * rhs.m20 + m33 * rhs.m30,
				m30 * rhs.m01 + m31 * rhs.m11 + m32 * rhs.m21 + m33 * rhs.m31,
				m30 * rhs.m02 + m31 * rhs.m12 + m32 * rhs.m22 + m33 * rhs.m32,
				m30 * rhs.m03 + m31 * rhs.m13 + m32 * rhs.m23 + m33 * rhs.m33);
	}


	/**
	 * Return a Point4f that is the transform of a Point4f using the 'this' matrix.
	 *
	 * @param right The vector to tranform.
	 * @return The transformed vector.
	 */
	public Vector4 mult(Vector4 rhs) {
		return new Vector4(
				m00 * rhs.getX() + m01 * rhs.getY() + m02 * rhs.getZ() + m03 * rhs.getW(),
				m10 * rhs.getX() + m11 * rhs.getY() + m12 * rhs.getZ() + m13 * rhs.getW(),
				m20 * rhs.getX() + m21 * rhs.getY() + m22 * rhs.getZ() + m23 * rhs.getW(),
				m30 * rhs.getX() + m31 * rhs.getY() + m32 * rhs.getZ() + m33 * rhs.getW());
	}


	/**
	 * Returns the transpose this matrix. This matrix un-changed.
	 *
	 * @return The transpose this matrix
	 */
	public Matrix4 transpose() {
		return new Matrix4(m00, m10, m20, m30,
				           m01, m11, m21, m31,
				           m02, m12, m22, m32,
				           m03, m13, m23, m33);
	}


	/**
	 * @return the determinant of the matrix
	 */
	public float determinant() {
		return
				(m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
				- (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)
				+ (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
				+ (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
				- (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30)
				+ (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
	}


	/**
	 * Invert this matrix
	 *
	 * @return this if successful, null otherwise
	 */
	public Matrix4 invert() {
		// float det = determinant();
		// if (Math.abs(det) < SMALL)
		// return null;
		float det = 1.0f / determinant();
		Matrix4 m = new Matrix4(
				m12 * m23 * m31 - m13 * m22 * m31 + m13 * m21 * m32 - m11 * m23 * m32
				- m12 * m21 * m33 + m11 * m22 * m33,
				m03 * m22 * m31 - m02 * m23 * m31 - m03 * m21 * m32 + m01 * m23 * m32
				+ m02 * m21 * m33 - m01 * m22 * m33,
				m02 * m13 * m31 - m03 * m12 * m31 + m03 * m11 * m32 - m01 * m13 * m32
				- m02 * m11 * m33 + m01 * m12 * m33,
				m03 * m12 * m21 - m02 * m13 * m21 - m03 * m11 * m22 + m01 * m13 * m22
				+ m02 * m11 * m23 - m01 * m12 * m23,
				m13 * m22 * m30 - m12 * m23 * m30 - m13 * m20 * m32 + m10 * m23 * m32
				+ m12 * m20 * m33 - m10 * m22 * m33,
				m02 * m23 * m30 - m03 * m22 * m30 + m03 * m20 * m32 - m00 * m23 * m32
				- m02 * m20 * m33 + m00 * m22 * m33,
				m03 * m12 * m30 - m02 * m13 * m30 - m03 * m10 * m32 + m00 * m13 * m32
				+ m02 * m10 * m33 - m00 * m12 * m33,
				m02 * m13 * m20 - m03 * m12 * m20 + m03 * m10 * m22 - m00 * m13 * m22
				- m02 * m10 * m23 + m00 * m12 * m23,
				m11 * m23 * m30 - m13 * m21 * m30 + m13 * m20 * m31 - m10 * m23 * m31
				- m11 * m20 * m33 + m10 * m21 * m33,
				m03 * m21 * m30 - m01 * m23 * m30 - m03 * m20 * m31 + m00 * m23 * m31
				+ m01 * m20 * m33 - m00 * m21 * m33,
				m01 * m13 * m30 - m03 * m11 * m30 + m03 * m10 * m31 - m00 * m13 * m31
				- m01 * m10 * m33 + m00 * m11 * m33,
				m03 * m11 * m20 - m01 * m13 * m20 - m03 * m10 * m21 + m00 * m13 * m21
				+ m01 * m10 * m23 - m00 * m11 * m23,
				m12 * m21 * m30 - m11 * m22 * m30 - m12 * m20 * m31 + m10 * m22 * m31
				+ m11 * m20 * m32 - m10 * m21 * m32,
				m01 * m22 * m30 - m02 * m21 * m30 + m02 * m20 * m31 - m00 * m22 * m31
				- m01 * m20 * m32 + m00 * m21 * m32,
				m02 * m11 * m30 - m01 * m12 * m30 - m02 * m10 * m31 + m00 * m12 * m31
				+ m01 * m10 * m32 - m00 * m11 * m32,
				m01 * m12 * m20 - m02 * m11 * m20 + m02 * m10 * m21 - m00 * m12 * m21
				- m01 * m10 * m22 + m00 * m11 * m22);

		return m.mult(det);

	}

	/**
	 * Negate this matrix and returns the result.
	 *
	 * @return the negated matrix
	 */
	public Matrix4 negate() {
		return this.mult(-1);
	}

	public Matrix4 mult(float scalar) {
		return new Matrix4(m00 * scalar,
		                   m01 * scalar,
		                   m02 * scalar,
		                   m03 * scalar,
		                   m10 * scalar,
		                   m11 * scalar,
		                   m12 * scalar,
		                   m13 * scalar,
		                   m20 * scalar,
		                   m21 * scalar,
		                   m22 * scalar,
		                   m23 * scalar,
		                   m30 * scalar,
		                   m31 * scalar,
		                   m32 * scalar,
		                   m33 * scalar);
	}

	public float[] toOpenGL() {
		final float[] matrix = new float[16];

		matrix[0] = m00;
		matrix[1] = m10;
		matrix[2] = m20;
		matrix[3] = m30;
		matrix[4] = m01;
		matrix[5] = m11;
		matrix[6] = m21;
		matrix[7] = m31;
		matrix[8] = m02;
		matrix[9] = m12;
		matrix[10] = m22;
		matrix[11] = m32;
		matrix[12] = m03;
		matrix[13] = m13;
		matrix[14] = m23;
		matrix[15] = m33;
		return matrix;
	}
	
    public Ray transform(Ray r) {
        return new Ray(mult(r.start().toPos()).toVector3(), 
        		       mult(r.direction().toDir()).toVector3());
    }
}
