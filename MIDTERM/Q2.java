import java.util.ArrayList;

class Q2 {

    private ArrayList<Integer> data = new ArrayList<Integer>();

    public void insert(int e) {
        data.add(e);
    }

    public void delete(int e) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == e) {
                data.remove(i);
                return;
            }
        }
    }
}
