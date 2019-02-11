package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomException extends RuntimeException{

    //错误代码
    private ResultCode resultCode;

    public static CustomException newInstance(ResultCode resultCode) {
        return new CustomException(resultCode);
    }
}
