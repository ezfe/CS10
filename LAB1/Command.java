import java.awt.*;
/**
 * Command.java
 * Superclass for commands.
 * Provides empty definitions for the methods executeClick, executePress,
 * and executeDrag. Each subclass should override some of the methods.
 * 
 * Written by THC for CS 10 Lab Assigment 1.
 * 
 * @author Tom Cormen
 */
public class Command {
  public void executeClick(Point p, Drawing dwg) { }
  public void executePress(Point p, Drawing dwg) { }
  public void executeDrag(Point p, Drawing dwg) { }
}
