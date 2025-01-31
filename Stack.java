/**
	2.1 Stack Practice
 	@author Devin Y
	@since 9/21/23
*/

//LIFO
class Stack<T> {
  private Object[] stack = new Object[10];
  private int pointer = 0;

  public void push(T n) {
    System.out.println("" + n + ": n");
    if(pointer >= stack.length) {
      grow();
      stack[pointer] = (T)n;
      pointer++;
    } else {
      stack[pointer] = (T)n;
      pointer++;
    }
  }
  public T pop() {
    if(isEmpty()) {
      return (T)null;
    }
    pointer--;
    T remove = (T) stack[pointer];
    stack[pointer] = 0;
    return remove;
  }
  
  public T peek() {
    if(isEmpty()) {
      return (T) null;
    }
    if(pointer >= stack.length) {
      grow();
    }
    return (T) stack[pointer-1];
  }
  
  public boolean isEmpty() {
    return (pointer == 0);
  }
  
  public int size() {
    if(pointer == 0) {
      return pointer;
    }
    return pointer-1;
  }
  
  public int search(T val) {
    for(int i = 0; i < pointer; i++) {
      T s = (T)(stack[i]);
      if(s.equals(val)) {
        return i;
      }
    }
    return -1;
  }
  //test this
  public void grow() {
    Object[] temp = stack;
    int newSize = (int)(stack.length*1.5);
    stack = new Object[newSize];
    for(int i = 0; i < temp.length; i++) {
      stack[i] = temp[i];
    }
    pointer = temp.length;
  }
}