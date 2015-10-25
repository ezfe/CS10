import java.util.ArrayList;

class Q3 {

    private Element head;
    private Element tail;

    private class Element {
        private Integer data;
        private Element next;

        public Element(Integer data) {
            this.data = data;
        }
    }

    public Q3() {
        Element newElement = new Element(null);
        head = newElement;
        tail = newElement;
    }

    /**
     * Runtime is θ(1)
     */
    public void insert(int e) {
        Element newElement = new Element(e);
        tail.next = newElement;
        tail = newElement;
    }

    /**
     * Runtime is θ(n)
     */
    public Integer minimum() {
        if (head.next == null) return null;
        int min = head.next.data;
        for (Element a = head; a != null; a = a.next) {
            if (a.data != null && a.data < min) {
                min = a.data;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Q3 list = new Q3();
        list.insert(-41);
        list.insert(3);
        list.insert(9);
        list.insert(10);
        list.insert(-12);
        list.insert(-40);
        System.out.println(list.minimum());
    }
}
