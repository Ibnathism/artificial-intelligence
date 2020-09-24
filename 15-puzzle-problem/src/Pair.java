public class Pair {
    private int index;
    private int value;

    public Pair(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "("+ this.getIndex()+","+this.getValue()+")";
    }

    @Override
    public boolean equals(Object obj) {
        Pair temp;
        if (obj instanceof Pair){
            temp = (Pair) obj;
            if (temp.getIndex()==this.getIndex() && temp.getValue()==this.getValue()) return true;
            else return false;
        }
        else return false;
    }
}
