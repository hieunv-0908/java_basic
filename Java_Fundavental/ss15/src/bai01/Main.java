package bai01;

public class Main {
    public static void main(String[] args) {
        MedicalRecordHistory history = new MedicalRecordHistory();

        history.addEdit(new EditAction("Thêm chẩn đoán viêm họng", "20:00"));
        history.displayHistory();

        history.addEdit(new EditAction("Cập nhật đơn thuốc", "20:05"));
        history.displayHistory();

        System.out.println("\nChỉnh sửa gần nhất:");
        System.out.println(history.getLatestEdit());

        System.out.println("\nThực hiện Undo:");
        history.undoEdit();
        history.displayHistory();
    }
}
