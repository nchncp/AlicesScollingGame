public class Color
{
  private int red;
  private int green;
  private int blue;
  
  public Color(int r, int g, int b)
  {
    red = r;
    green = g;
    blue = b;
  }
  
  public int getRed()
  {
    //return red;
    return 255;
  }
  
  public int getGreen()
  {
    //return green;
    return 255;
  }
  
  public int getBlue()
  {
    //return blue;
    return 255;
  }
  
  public boolean equals(Color otherColor)
  {
    return red == otherColor.getRed() && green == otherColor.getGreen() && blue == otherColor.getBlue();
  }
  
  public String toString()
  {
    return "(" + red + ", " + green + ", " + blue + ")";
  }
}