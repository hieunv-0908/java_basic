package re.edu.score.bai2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
VẤN ĐỀ:

Trong hệ thống cyber game, mỗi máy trạm cần có một phiên chơi (PlaySession) riêng để tính thời gian.

Tuy nhiên, thực tế xảy ra lỗi:
- Máy 01 chơi thì máy 02 cũng bị cộng thời gian
- Tất cả máy dùng chung một bộ đếm


NGUYÊN NHÂN:

PlaySession được đánh dấu @Component nên Spring quản lý nó như một Bean.

Mặc định, Bean trong Spring có scope là Singleton:
- Chỉ tạo 1 object duy nhất trong toàn hệ thống
- Mọi nơi đều dùng chung object này

=> dẫn đến tất cả máy trạm cùng sử dụng chung biến playTime

*/

@Component
class PlaySession {

    private double playTime = 0;

    public void addTime(double time) {
        this.playTime += time;
    }

    public double getPlayTime() {
        return playTime;
    }
}

/*

GIẢI PHÁP:

Sử dụng Prototype Scope để mỗi lần sử dụng sẽ tạo một object mới.

CODE SAU KHI SỬA:
*/

@Component
@Scope("prototype")
class PlaySessionFixed {

    private double playTime = 0;

    public void addTime(double time) {
        this.playTime += time;
    }

    public double getPlayTime() {
        return playTime;
    }
}

/*

GIẢI THÍCH GIẢI PHÁP:

Prototype scope giúp:
- Mỗi lần cần sử dụng PlaySession, Spring sẽ tạo object mới
- Mỗi máy trạm sẽ có một instance riêng
- Không còn chia sẻ biến playTime giữa các máy

=> đảm bảo mỗi người dùng có dữ liệu độc lập

KẾT LUẬN:

Không nên dùng Singleton cho dữ liệu có trạng thái riêng của từng user,
vì sẽ gây chia sẻ dữ liệu và dẫn đến sai logic.
*/