package demo_dead_look_synchronize;

public class Eployee implements Runnable{
    private String fullName;
    private Tool keep;
    private Tool need;

    public Eployee(String fullName, Tool keep, Tool need) {
        this.fullName = fullName;
        this.keep = keep;
        this.need = need;
    }

    public Eployee() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Tool getKeep() {
        return keep;
    }

    public void setKeep(Tool keep) {
        this.keep = keep;
    }

    public Tool getNeed() {
        return need;
    }

    public void setNeed(Tool need) {
        this.need = need;
    }

    @Override
    public void run() {
        synchronized (keep){
            System.out.println(fullName + "dang cam dung cu" + keep.getName());
            try{
                Thread.sleep(1000);
            }catch (InterruptedException IE){
                System.err.println();
            }
        }
    }
}
