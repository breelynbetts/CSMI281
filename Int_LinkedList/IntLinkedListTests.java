 package int_linkedlist;
  
  public class IntLinkedListTests {
  
      public static void main (String[] args) {
          IntLinkedList llCoolJ = new IntLinkedList();
          llCoolJ.prepend(3);
          llCoolJ.prepend(2);
          llCoolJ.prepend(1);
          llCoolJ.removeAt(1);
          IntLinkedList.Iterator it = llCoolJ.getIteratorAt(0);
          System.out.println(it.getCurrentInt()); // 1
          it.next();
          System.out.println(it.getCurrentInt()); // 3
      }
  
  }