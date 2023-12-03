package top.qwwq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userID;
    private String loginName;
    private String password;
    private String sex;
    private  String mobile;
    private  String email;
}
