public class b5 {
}


//📌 TỔNG HỢP REGEX CƠ BẢN (DỄ NHỚ)
//1️⃣ Ký tự đại diện (Metacharacters)
//Ký tự	Ý nghĩa	Ví dụ	Khớp
//.	Bất kỳ ký tự nào (trừ xuống dòng)	a.c	abc, a@c, a c
//^	Bắt đầu chuỗi	^A	An, Apple
//$	Kết thúc chuỗi	z$	buzz, quiz
//`	`	Hoặc (OR)	`(a
//
//📌 ^ + $ hay dùng để khóa chặt toàn bộ chuỗi
//
//2️⃣ Lớp ký tự (Character Classes)
//Cú pháp	Ý nghĩa
//[abc]	a hoặc b hoặc c
//[^abc]	Bất kỳ ký tự nào không phải a, b, c
//[a-z]	Chữ thường a → z
//[A-Z]	Chữ hoa A → Z
//[0-9]	Số 0 → 9
//
//Ví dụ:
//
//        [A-Z][a-z]+
//
//
//        👉 Tên riêng: Nam, Hieu
//
//3️⃣ Lớp ký tự định sẵn (Predefined Classes)
//
//⚠️ Trong Java phải viết \
//
//Regex	Ý nghĩa	Tương đương
//\\d	Chữ số	[0-9]
//        \\w	Chữ + số + _	[a-zA-Z0-9_]
//        \\s	Khoảng trắng	space, tab, newline
//
//Ví dụ Java:
//
//        "2026".matches("\\d+"); // true
//
//4️⃣ Định lượng (Quantifiers)
//Ký hiệu	Ý nghĩa
//*	0 hoặc nhiều
//+	1 hoặc nhiều
//?	0 hoặc 1
//        {n}	Đúng n lần
//{n,m}	Từ n đến m lần
//
//Ví dụ:
//
//        \\d{4}
//
//
//👉 Đúng 4 chữ số
//
//[a-zA-Z]+
//
//
//        👉 Ít nhất 1 chữ cái
//
//5️⃣ Kết hợp TẤT CẢ (rất hay ra bài)
//🔹 Mã thẻ thư viện
//^TV-\\d{5}$
//
//
//✔ TV-12345
//        ❌ TV-1234
//
//        🔹 Email đơn giản
//^[\\w.]+@[a-zA-Z]+\\.com$
//
//6️⃣ Câu thần chú để nhớ Regex
//
//Lớp ký tự → Định lượng → Khóa đầu/cuối
//
//7️⃣ Mẫu code Java CHUẨN
//Pattern p = Pattern.compile("^TV-\\d{5}$");
//Matcher m = p.matcher(input);
//
//if (m.matches()) {
//        System.out.println("Hợp lệ");
//} else {
//        System.out.println("Không hợp lệ");
//}