public class Triangle implements GeomShape {
	int baseY, baseX, baseWidth, topX, topY;

	public Triangle(int bX, int bY, int bW, int tX, int tY) {
		baseX = bX;
		baseY = bY;
		baseWidth = bW;
		topX = tX;
		topY = tY;
	}

	public void move(int deltaX, int deltaY) {
		baseX += deltaX;
		baseY += deltaY;
		topX += deltaX;
		topY += deltaY;
	}

	public void scale(double factor) {
		baseWidth *= factor;

		int height = triangleHeight();
		height *= (int) factor;
		topY = baseY + height;
	}

	public double areaOf() {
		double area = 0;

		area = (baseWidth * triangleHeight()) / 2.0;

		return area;
	}

	private int triangleHeight() {
		return topY - baseY;
	}

	public String toString() {
		return "Triangle with vertices (" + baseX + ", " + baseY + "), (" + (baseX + baseWidth) + ", " + baseY + "), (" + topX + ", " + topY + ")";
	}
}
